package com.quantdo.market.service.ws;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;


/**
 * 客户端socket
 * 2018年11月26日 下午4:57:08 
 * @author suhongbin
 */
public abstract class AbstractWebsocketClient {
	
	protected final Logger logger = LogManager.getLogger(getClass());
	
	private  SslContext sslCtx;//ssl
    
    private int port;//端口号
    
    private URI uri;//对应uri
    
    private String host;//地址
    
    protected final HashedWheelTimer timer = new HashedWheelTimer();
    
    private Channel channel; 
    
    private String heartbeat;
    
    private InitSubscribeService initSubscribeMessage;
    
    private List<String> topics;
    
	/**
     * 初始化方法
     * @param url
     * @throws URISyntaxException
     * @throws SSLException
     */
    public boolean initWebsocket(String url,String heartbeat,InitSubscribeService subscribe) {
    	try {
    		uri = new URI(url);
    		host = uri.getHost() == null? "127.0.0.1" : uri.getHost();
    		String scheme = uri.getScheme() == null? "ws" : uri.getScheme();  
    		if (uri.getPort() == -1) {  
                if ("ws".equalsIgnoreCase(scheme)) {  
                    port = 80;  
                } else if ("wss".equalsIgnoreCase(scheme)) {  
                    port = 443;  
                } else {  
                    port = -1;  
                }  
            } else {  
                port = uri.getPort();  
            }  
            if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {  
                return false;  
            }  
            if ("wss".equalsIgnoreCase(scheme)) {  
            	sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            } else {  
            	sslCtx = null;  
            }
            this.heartbeat = heartbeat;
            this.initSubscribeMessage = subscribe;
        	return true;
		} catch (Exception e) {
			logger.error("初始化参数异常",e);
			return false;
		}
		
    }
    
    /**
     * 获取uri
     * @return
     */
    public URI getUri(){return uri;}
    
    /**
     * 获取订阅主题
     * @return
     */
    public List<String> getTopic(){return topics;}
    /**
     * 创建不同的信息通道
     * @return
     */
    public abstract AbstractWebsocketClientHandler getClientHandler();
    /**
     * 链接方法
     */
    public void connect(){
        
        EventLoopGroup group = new NioEventLoopGroup();  
        Bootstrap boot = new Bootstrap();
        AbstractWebsocketClientHandler handler = getClientHandler();
        boot.group(group).option(ChannelOption.TCP_NODELAY, true).channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO));
        final AbstractConnectionWatchdog watchdog = new AbstractConnectionWatchdog(boot, timer, sslCtx,port,host,this,true) {
                public ChannelHandler[] handlers() {
                    return new ChannelHandler[] {
                            this,
                            new HttpClientCodec(),
                            new HttpObjectAggregator(1024),
                            new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                            new ConnectorIdleStateTrigger(heartbeat),
                    };
                }
            };
            
            ChannelFuture future;
            //进行连接
            try {
                synchronized (boot) {
                    boot.handler(new ChannelInitializer<Channel>() {

                        //初始化channel
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                        	 if (sslCtx != null) {  
                        		 ch.pipeline().addLast(sslCtx.newHandler(ch.alloc(), host, port));  
                             }  
                            ch.pipeline().addLast(watchdog.handlers());
                            ch.pipeline().addLast(handler);
                        }
                    });

                    future = boot.connect(host,port);
                }

                // 以下代码在synchronized同步块外面是安全的
                future.sync();
                handler.handshakeFuture().sync();
                handler.handshakeFuture().addListener(new ChannelFutureListener (){
					@Override
					public void operationComplete(ChannelFuture f) throws Exception {
						boolean succeed = f.isSuccess();
						 //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，一直尝试12次，如果失败则不再重连
		                if (succeed) {
		                	logger.info("--->>>"+host+":"+port+"连接成功");
		                    channel = f.channel();
		                    topics = initSubscribeMessage.init();
		                    if(!CollectionUtils.isEmpty(topics)){
		                    	topics.forEach(topic -> {
		                    		channel.writeAndFlush(new TextWebSocketFrame(topic));
		                    		logger.debug("订阅信息:{}",topic);
		                    	});
		                    }
		                }else{
		                	logger.error("--->>>"+host+":"+port+"连接失败");
		                }
					}
                	
                });
            } catch (Exception e) {
            	logger.error("--->>>"+host+":"+port+"连接异常",e);
                group.shutdownGracefully();
                Thread.currentThread().interrupt();
            }
    }

  
    /**
     * 连接成功 获取通道
     * @return
     */
    public Channel getChannel(){return channel;}

	
}

