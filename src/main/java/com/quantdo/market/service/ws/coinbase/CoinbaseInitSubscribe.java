package com.quantdo.market.service.ws.coinbase;


import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.quantdo.market.entity.Product;
import com.quantdo.market.misc.GlobalData;
import com.quantdo.market.misc.MarketFormatEnum;
import com.quantdo.market.service.ws.InitSubscribeService;
import com.quantdo.market.util.DataUtil;

@Service
public class CoinbaseInitSubscribe implements InitSubscribeService{

	private final Logger logger = LogManager.getLogger(getClass());
	
	@Value("${coinbase.ws.channel}")
    private  String channel;
	
	@Value("${coinbase.ws.channel.ticker}")
    private  String ticker;
	
	/**
	 * 初始化订阅信息
	 * @return
	 */
	@Override
	public List<String> init(){
		List<Product> list = DataUtil.getCoinbase();
		try {
			if(!StringUtils.isEmpty(channel) && !StringUtils.isEmpty(ticker) && !CollectionUtils.isEmpty(list)){
				String symbols = "";
				for(Product product : list){
					String symbol = product.getSymbol();
					GlobalData.getCoinbasesymbollist().add(symbol);
					symbols += "\""+symbol+"\",";
				}
				symbols = symbols.substring(0, symbols.lastIndexOf(","));
				GlobalData.getCoinbasetopiclist().add(channel.replace(MarketFormatEnum.SYMBOL.getValue(),symbols).replace(MarketFormatEnum.CHANNEL.getValue(), ticker));
			}
		} catch (Exception e) {
			logger.error("Coinbase交易所初始化订阅信息失败",e);
			return null;
		}
		return GlobalData.getCoinbasetopiclist();
	}
	
}
