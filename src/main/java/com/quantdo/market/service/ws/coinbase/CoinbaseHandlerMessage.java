package com.quantdo.market.service.ws.coinbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.quantdo.market.entity.TickerEntity;
import com.quantdo.market.entity.vo.coinbase.WsCoinbaseTicker;
import com.quantdo.market.misc.AplTypeEnum;
import com.quantdo.market.misc.CacheType;
import com.quantdo.market.misc.ExchangeEnum;
import com.quantdo.market.misc.GlobalData;
import com.quantdo.market.misc.MarketTypeEnum;
import com.quantdo.market.util.HandlerMessageUtil;
import com.quantdo.market.util.TransUtil;

@Service
public class CoinbaseHandlerMessage{

	private final Logger logger = LogManager.getLogger(getClass());
	
	@Value("${cache.type}")
    private  String cacheType;
	
	@Autowired
	private HashOperations<String, String, String> redis;
	
	public List<TickerEntity> getTickerData(){
		List<TickerEntity> result = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
			if(StringUtils.isEmpty(cacheType) || CacheType.LOCAL.getValue().equals(cacheType)){
				map = GlobalData.getCoinbasemarketmap();
			}else if(CacheType.REDIS.getValue().equals(cacheType)){
				map = redis.entries(AplTypeEnum.WEBSOCKET+"#"+ExchangeEnum.COINBASE+"#"+MarketTypeEnum.TICKER);
			}
			List<WsCoinbaseTicker> list= HandlerMessageUtil.getMessgae(map,AplTypeEnum.WEBSOCKET+"#"+MarketTypeEnum.TICKER, WsCoinbaseTicker.class);
			if(!CollectionUtils.isEmpty(list)){
				result = new ArrayList<TickerEntity>();
				for(WsCoinbaseTicker wsCoinbaseTickerEntity : list){
					TickerEntity tickerEntity = new TickerEntity();
					tickerEntity.setTimeStamp(System.currentTimeMillis());
					tickerEntity.setSymbol(wsCoinbaseTickerEntity.getProduct_id());
					tickerEntity.setAskPrice(TransUtil.getDecimal(wsCoinbaseTickerEntity.getBest_ask()));
					tickerEntity.setBidPrice(TransUtil.getDecimal(wsCoinbaseTickerEntity.getBest_bid()));
					tickerEntity.setOpen(TransUtil.getDecimal(wsCoinbaseTickerEntity.getOpen_24h()));
					tickerEntity.setHigh(TransUtil.getDecimal(wsCoinbaseTickerEntity.getHigh_24h()));
					tickerEntity.setLow(TransUtil.getDecimal(wsCoinbaseTickerEntity.getLow_24h()));
					tickerEntity.setLastPrice(TransUtil.getDecimal(wsCoinbaseTickerEntity.getPrice()));
					tickerEntity.setVolumeToday(TransUtil.getDecimal(wsCoinbaseTickerEntity.getVolume_24h()));
					result.add(tickerEntity);
				}
			}
		} catch (Exception e) {
			logger.error("Coinbase交易所获取Ticker数据异常",e);
		}
		return result;
	}
}
