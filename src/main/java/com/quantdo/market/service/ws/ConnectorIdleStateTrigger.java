package com.quantdo.market.service.ws;



import org.springframework.util.StringUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 当客户端的所有ChannelHandler中4s内没有write事件，则会触发userEventTriggered方法
 * <br>创建日期：2018年4月21日 下午4:57:08 <br>
 * <b>Copyright 2015 上海量投网络科技有限公司 All Rights Reserved</b>
 * 
 * @author 苏宏斌
 * @since 1.0
 * @version 1.0
 */
@Sharable
public class ConnectorIdleStateTrigger extends ChannelInboundHandlerAdapter {
	private String heartbeat;
	
	public ConnectorIdleStateTrigger(String heartbeat){
		this.heartbeat = heartbeat;
	}
	

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
            	if(!StringUtils.isEmpty(heartbeat)){
            		ctx.writeAndFlush(new TextWebSocketFrame(heartbeat));
            	}
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}

