package com.quantdo.market.service.ws.bitmex;


import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.quantdo.market.misc.MarketFormatEnum;
import com.quantdo.market.entity.Product;
import com.quantdo.market.misc.AplTypeEnum;
import com.quantdo.market.misc.GlobalData;
import com.quantdo.market.misc.MarketTypeEnum;
import com.quantdo.market.service.ws.InitSubscribeService;
import com.quantdo.market.util.DataUtil;

@Service
public class BitmexInitSubscribe implements InitSubscribeService{

	private final Logger logger = LogManager.getLogger(getClass());
	
	@Value("${bitmex.ws.channel}")
    private  String channel;
	
	@Value("${bitmex.ws.channel.ticker}")
    private  String ticker;
	
	/**
	 * 初始化订阅信息
	 * @return
	 */
	@Override
	public List<String> init(){
		try {
			if(!StringUtils.isEmpty(channel)){
				for(Product product:DataUtil.getBitmex()){
					String symbol = product.getSymbol();
					//行情订阅类型#品种#合约#其他类型
					if(!StringUtils.isEmpty(ticker)){
						String channelStr = ticker.replace(MarketFormatEnum.SYMBOL.getValue(), symbol);
						GlobalData.getBitmextopicmap().put(AplTypeEnum.WEBSOCKET+"#"+MarketTypeEnum.TICKER+"#"+symbol+"##", symbol);
						GlobalData.getBitmextopiclist().add(channel.replace(MarketFormatEnum.CHANNEL.getValue(),channelStr ));
					}
				}
			}
		} catch (Exception e) {
			logger.error("Bitmex交易所初始化订阅信息失败",e);
			return null;
		}
		return GlobalData.getBitmextopiclist();
	}
	
	
}
