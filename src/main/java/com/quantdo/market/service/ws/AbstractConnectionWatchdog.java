package com.quantdo.market.service.ws;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.ssl.SslContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

/**
 * 重连检测狗，当发现当前的链路不稳定关闭之后，进行12次重连
 * 2018年11月26日 下午4:57:08 
 * @author suhongbin
 */
@Sharable
public abstract class AbstractConnectionWatchdog extends ChannelInboundHandlerAdapter implements TimerTask ,ChannelHandlerHolder{
    
    protected final Logger log = LogManager.getLogger(getClass());
    
    private final Bootstrap bootstrap;
    private final Timer timer;
    private final int port;
    private final String host;
    private  SslContext sslCtx;//ssl
    private volatile boolean reconnect = true;
    private int attempts;
    private AbstractWebsocketClient client;
    
    
    public AbstractConnectionWatchdog(Bootstrap bootstrap, Timer timer,SslContext sslCtx, int port,String host,AbstractWebsocketClient client,boolean reconnect) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.sslCtx = sslCtx;
        this.port = port;
        this.host = host;
        this.client = client;
        this.reconnect = reconnect;
    }
    
    /**
     * channel链路每次active的时候，将其连接的次数重新0
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	log.info("The current link has been activated and the reconnect attempts are reset to 0.");
        attempts = 0;
        ctx.fireChannelActive();
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接关闭");
        if(reconnect){
            System.out.println("链接关闭，将进行重连");
            if (attempts < 12) {
                attempts++;
                //重连的间隔时间会越来越长
                int timeout = 2 << attempts;
                timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
            }
        }
        ctx.fireChannelInactive();
    }
    

    public void run(Timeout timeout) throws Exception {
        
        ChannelFuture future;
        AbstractWebsocketClientHandler handler = client.getClientHandler();
        //bootstrap已经初始化好了，只需要将handler填入就可以了
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                	if (sslCtx != null) {  
               		 ch.pipeline().addLast(sslCtx.newHandler(ch.alloc(), host, port));  
                    } 
                    ch.pipeline().addLast(handlers());
                    ch.pipeline().addLast(handler);
                }
            });
            future = bootstrap.connect(host,port);
        }
        //future对象
        future.sync();
        //监听判断是否创建连接成功
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) throws Exception {
                //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，一直尝试12次，如果失败则不再重连
                if (!f.isSuccess()) {
                    System.out.println("重连失败");
                    f.channel().pipeline().fireChannelInactive();
                }
            }
        });
        handler.handshakeFuture().sync();
        //监听判断是否握手成功
        handler.handshakeFuture().addListener(new ChannelFutureListener (){
			@Override
			public void operationComplete(ChannelFuture f) throws Exception {
				boolean succeed = f.isSuccess();
				 //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，一直尝试12次，如果失败则不再重连
                if (succeed) {
                    log.info("--->>>"+host+":"+port+"连接成功");
                    List<String> topics = client.getTopic();
                    if(!CollectionUtils.isEmpty(topics)){
                    	topics.forEach(topic -> f.channel().writeAndFlush(new TextWebSocketFrame(topic)));
                    }
                }else{
                	log.info("--->>>"+host+":"+port+"连接失败");
                }
			}
        	
        });
        
    }

}
