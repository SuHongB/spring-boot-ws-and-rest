package com.quantdo.market.service.ws.bitstamp;



import java.util.Iterator;
import io.netty.channel.Channel;
import org.springframework.util.StringUtils;
import com.quantdo.market.misc.AplTypeEnum;
import com.quantdo.market.misc.BitstampMessageEnum;
import com.quantdo.market.misc.CacheType;
import com.quantdo.market.misc.ExchangeEnum;
import com.quantdo.market.misc.GlobalData;
import com.quantdo.market.service.ws.AbstractWebsocketClient;
import com.quantdo.market.service.ws.AbstractWebsocketClientHandler;
import org.springframework.data.redis.core.HashOperations;


/**
 * Bitstamp交易所报文处理
 * @author suhongbin
 */
public class BitstampWebsocketClientHandler extends AbstractWebsocketClientHandler {
	
    private  String cacheType;
	
    private HashOperations<String,String,String> redis;
    
	
	public BitstampWebsocketClientHandler(AbstractWebsocketClient abstractWebsocketClient,HashOperations<String,String,String> redis,String cacheType) {
		super(abstractWebsocketClient);
		this.redis = redis;
		this.cacheType = cacheType;
	}

	@Override
	protected void onReceive(Channel ch, String text) {
		if(text.contains(BitstampMessageEnum.ERROR.getValue())){
			logger.error("Bitstamp交易所收到订阅失败消息:{}",text);
		}else if(text.contains(BitstampMessageEnum.SUCCEES.getValue())){
			logger.info("Bitstamp交易所收到订阅成功消息:{}",text);
		}else if(text.contains(BitstampMessageEnum.PING.getValue())){
			logger.info("Bitstamp交易所收到心跳消息:{}",BitstampMessageEnum.PONG.getValue());
		}else{
			logger.debug("Bitstamp交易所收到订阅消息:{}",text);
			for(Iterator<?> ite = GlobalData.getBitstamptopicmap().keySet().iterator();ite.hasNext();){
				String key = (String)ite.next();
				if(text.contains(GlobalData.getBitstamptopicmap().get(key))){
					if(StringUtils.isEmpty(cacheType) || CacheType.LOCAL.getValue().equals(cacheType)){
						GlobalData.getBitstampmarketmap().put(key, text);
					}else if(CacheType.REDIS.getValue().equals(cacheType)){
						redis.put(AplTypeEnum.WEBSOCKET+"#"+ExchangeEnum.BITSTAMP+"#"+key.split("#")[1], key, text);
					}
					break;
				}
			}
		}
	}

}
