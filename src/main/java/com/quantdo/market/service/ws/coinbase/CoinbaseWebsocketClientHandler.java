package com.quantdo.market.service.ws.coinbase;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.util.StringUtils;
import com.quantdo.market.misc.AplTypeEnum;
import com.quantdo.market.misc.CacheType;
import com.quantdo.market.misc.ExchangeEnum;
import com.quantdo.market.misc.CoinbaseMessageEnum;
import com.quantdo.market.misc.GlobalData;
import com.quantdo.market.misc.MarketTypeEnum;
import com.quantdo.market.service.ws.AbstractWebsocketClient;
import com.quantdo.market.service.ws.AbstractWebsocketClientHandler;
import io.netty.channel.Channel;

public class CoinbaseWebsocketClientHandler extends AbstractWebsocketClientHandler {

	private String cacheType;
	
	private HashOperations<String,String,String> redis;
	
	public CoinbaseWebsocketClientHandler(AbstractWebsocketClient abstractWebsocketClient,HashOperations<String,String,String> redis,String cacheType) {
		super(abstractWebsocketClient);
		this.cacheType = cacheType;
		this.redis = redis;
	}

	@Override
	protected void onReceive(Channel ch, String text) {
		if(!StringUtils.isEmpty(text)){
			if(text.contains(CoinbaseMessageEnum.ERROR.getValue())){
				logger.error("Coinbase交易所订阅失败消息:{}",text);
			}else if(text.contains(CoinbaseMessageEnum.SUCCEES.getValue())){
				logger.info("Coinbase交易所订阅成功消息:{}",text);
			}else if(text.contains(CoinbaseMessageEnum.PING.getValue())){
				logger.info("Coinbase交易所收到心跳消息:{}",CoinbaseMessageEnum.PONG.getValue());
			}else{
				logger.debug("Coinbase交易所收到订阅消息:{}",text);
				if(text.contains(CoinbaseMessageEnum.TICKER.getValue())){
					this.saveData(text, MarketTypeEnum.TICKER);
				}
			}
		}
	}
	
	private void saveData(String text,MarketTypeEnum typeEnum){
		try {
			for(String symbol: GlobalData.getCoinbasesymbollist()){
				if(text.contains(symbol)){
					String key = AplTypeEnum.WEBSOCKET+"#"+typeEnum+"#"+symbol+"##";
					if(StringUtils.isEmpty(cacheType) || CacheType.LOCAL.getValue().equals(cacheType)){
						GlobalData.getCoinbasemarketmap().put(key, text);
					}else if(CacheType.REDIS.getValue().equals(cacheType)){
						redis.put(AplTypeEnum.WEBSOCKET+"#"+ExchangeEnum.COINBASE+"#"+typeEnum,key, text);
					}
					break;
				}
			}
		} catch (Exception e) {
			logger.error("CoinBase交易所{}消息:{}解析异常",typeEnum.toString(),text,e);
		}
	}

}
