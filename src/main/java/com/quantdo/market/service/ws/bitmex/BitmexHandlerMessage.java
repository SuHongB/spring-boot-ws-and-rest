package com.quantdo.market.service.ws.bitmex;

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
import com.alibaba.fastjson.JSON;
import com.quantdo.market.entity.TickerEntity;
import com.quantdo.market.entity.vo.Bitstamp.BitstampData;
import com.quantdo.market.entity.vo.Bitstamp.WsBitstampEntity;
import com.quantdo.market.misc.AplTypeEnum;
import com.quantdo.market.misc.CacheType;
import com.quantdo.market.misc.ExchangeEnum;
import com.quantdo.market.misc.GlobalData;
import com.quantdo.market.misc.MarketTypeEnum;
import com.quantdo.market.util.HandlerMessageUtil;

@Service
public class BitmexHandlerMessage{
	
	private final Logger logger = LogManager.getLogger(getClass());
	
	@Value("${cache.type}")
    private  String cacheType;
	
	@Autowired
	private HashOperations<String,String,String> hashOperations;
	

	public List<TickerEntity> getTickerData(){
		List<TickerEntity> result = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
			if(StringUtils.isEmpty(cacheType) || CacheType.LOCAL.getValue().equals(cacheType)){
				map = GlobalData.getBitstampmarketmap();
			}else if(CacheType.REDIS.getValue().equals(cacheType)){
				map = hashOperations.entries(AplTypeEnum.WEBSOCKET+"#"+ExchangeEnum.BITSTAMP+"#"+MarketTypeEnum.TICKER);
			}
			List<WsBitstampEntity> list= HandlerMessageUtil.getMessgae(map, AplTypeEnum.WEBSOCKET+"#"+MarketTypeEnum.TICKER, WsBitstampEntity.class);
			if(!CollectionUtils.isEmpty(list)){
				result = new ArrayList<>();
				for(WsBitstampEntity entity : list){
					if(null != entity){
						String data = entity.getData();
						if(!StringUtils.isEmpty(data)){
							BitstampData dataEntity = JSON.parseObject(data, BitstampData.class);
							//TODO
							System.out.println(dataEntity);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Bitstamp交易所获取Ticker数据异常",e);
		}
		return result;
	}
}
