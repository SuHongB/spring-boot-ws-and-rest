package com.quantdo.market.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.alibaba.fastjson.JSON;
import com.quantdo.market.entity.Product;
import com.quantdo.market.entity.TickerEntity;
import com.quantdo.market.entity.vo.Bitstamp.RestBitstampEntity;
import com.quantdo.market.misc.AplTypeEnum;
import com.quantdo.market.misc.CacheType;
import com.quantdo.market.misc.ExchangeEnum;
import com.quantdo.market.misc.GlobalData;
import com.quantdo.market.misc.MarketFormatEnum;
import com.quantdo.market.misc.MarketTypeEnum;
import com.quantdo.market.service.rest.RestBitstampService;
import com.quantdo.market.util.RestAplService;
import com.quantdo.market.util.TransUtil;

@Service
public class RestBitstampServiceImpl implements RestBitstampService {

	private final Logger logger = LogManager.getLogger(getClass());
	
	@Value("${cache.type}")
    private  String cacheType;
	
	@Value("${bitstamp.rest.url}")
    private  String url;
	
	@Value("${bitstamp.rest.ticker}")
    private  String ticker;
	
	@Autowired
	private HashOperations<String, String, String> redis;
	
	@Override
	public boolean saveTicker(List<Product> list) {
		try {
			if(!StringUtils.isEmpty(url) && !StringUtils.isEmpty(ticker) && !CollectionUtils.isEmpty(list)){
				for(Product product : list){
					String symbol = product.getSymbol();
					String value = RestAplService.doGet(url+ticker.replace(MarketFormatEnum.SYMBOL.getValue(),symbol),"UTF-8");
					if(StringUtils.isEmpty(cacheType) || CacheType.LOCAL.getValue().equals(cacheType)){
						GlobalData.getBitstampmarketmap().put(AplTypeEnum.REST+"#"+MarketTypeEnum.TICKER+"#"+symbol+"##", value);
					}else if(CacheType.REDIS.getValue().equals(cacheType)){
						redis.put(AplTypeEnum.REST+"#"+ExchangeEnum.BITSTAMP+"#"+MarketTypeEnum.TICKER,AplTypeEnum.REST+"#"+MarketTypeEnum.TICKER+"#"+symbol+"##", value);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Bitstamp交易所Rest请求Ticker数据存储异常",e);
			return false;
		}
		return true;
	}
	
	@Override
	public List<TickerEntity> getTicker() {
		List<TickerEntity> result = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
			if(StringUtils.isEmpty(cacheType) || CacheType.LOCAL.getValue().equals(cacheType)){
				map = GlobalData.getBitstampmarketmap();
			}else if(CacheType.REDIS.getValue().equals(cacheType)){
				map = redis.entries(AplTypeEnum.REST+"#"+ExchangeEnum.BITSTAMP+"#"+MarketTypeEnum.TICKER);
			}
			result = new ArrayList<>();
			for(Iterator<?> ite = map.keySet().iterator();ite.hasNext();){
				String key = (String)ite.next();
				String symbol = key.split("#")[2];
				RestBitstampEntity bitstampEntity =  JSON.parseObject(map.get(key), RestBitstampEntity.class);
				TickerEntity com = new TickerEntity();
				com.setSymbol(symbol);
				com.setTimeStamp(TransUtil.getLong(bitstampEntity.getTimestamp()));
				com.setAskPrice(TransUtil.getDecimal(bitstampEntity.getAsk()));
				com.setBidPrice(TransUtil.getDecimal(bitstampEntity.getBid()));
				com.setHigh(TransUtil.getDecimal(bitstampEntity.getHigh()));
				com.setLow(TransUtil.getDecimal(bitstampEntity.getLow()));
				com.setOpen(TransUtil.getDecimal(bitstampEntity.getOpen()));
				com.setLastPrice(TransUtil.getDecimal(bitstampEntity.getLast()));
				com.setVolumeToday(TransUtil.getDecimal(bitstampEntity.getVolume()));
				result.add(com);
			}
		} catch (Exception e) {
			logger.error("Bitstamp交易所获取Ticker数据异常",e);
		}
		return result;
	}


}
