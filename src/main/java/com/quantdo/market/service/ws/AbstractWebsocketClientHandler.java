package com.quantdo.market.service.ws;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * hnadler事件处理
 * 2018年11月26日 下午4:57:08 
 * @author suhongbin
 */
@Sharable
public abstract class AbstractWebsocketClientHandler extends SimpleChannelInboundHandler<Object> {
	
    protected final Logger logger = LogManager.getLogger(getClass());
    private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public AbstractWebsocketClientHandler(AbstractWebsocketClient abstractWebsocketClient) {
        this.handshaker = WebSocketClientHandshakerFactory.newHandshaker(abstractWebsocketClient.getUri(), WebSocketVersion.V13, null, false, new DefaultHttpHeaders(), Integer.MAX_VALUE);
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.getStatus() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            onReceive(ch,textFrame.text());
        } else if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame binaryFrame = (BinaryWebSocketFrame) frame;
            onReceive(ch,decodeByteBuff(binaryFrame.content()));
//            ByteBuf content = binaryFrame.content();
//            byte[] bytes = new byte[content.readableBytes()];
//            content.readBytes(bytes);
//            onReceive(ch,GzipUtil.decompress(bytes));
        } else if (frame instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            System.out.println("WebSocket Client received closing");
            ch.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	logger.error("The handler throws an exception while handling the event",cause);
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }

    protected abstract void onReceive(Channel ch,String text);
    
    
    
    public String decodeByteBuff(ByteBuf buf) throws IOException, DataFormatException {
        
 	   byte[] temp = new byte[buf.readableBytes()];
 	   ByteBufInputStream bis = new ByteBufInputStream(buf);
		   bis.read(temp);
		   bis.close();
		   Inflater decompresser = new Inflater(true);
		   decompresser.setInput(temp, 0, temp.length);
		   StringBuilder sb = new StringBuilder();
		   byte[] result = new byte[1024];
		   while (!decompresser.finished()) {
				int resultLength = decompresser.inflate(result);
				sb.append(new String(result, 0, resultLength, "UTF-8"));
		   }
		   decompresser.end();
        return sb.toString();
	}

}