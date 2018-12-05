package com.quantdo.market.service.ws.bitstamp;

import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import com.quantdo.market.service.ws.AbstractWebsocketClient;
import com.quantdo.market.service.ws.AbstractWebsocketClientHandler;

/**
 * Bitstamp交易所客户端
 * @author suhongbin
 */
@Service
public class BitstampWebsocketClient extends AbstractWebsocketClient implements Callable<Object> {

	
	@Value("${bitstamp.ws.url}")
    private String url;
	
	@Value("${cache.type}")
    private  String cacheType;
	
	@Autowired(required=false)
	private HashOperations<String, String, String> redis;
	
	@Autowired
	private BitstampInitSubscribe bitstampInitSubscribe;
	
	@Override
	public AbstractWebsocketClientHandler getClientHandler() {
		return new BitstampWebsocketClientHandler(this,redis,cacheType);
	}

	@Override
	public Object call() throws Exception {
		Long startTime = System.currentTimeMillis();
		if(this.initWebsocket(url,"",bitstampInitSubscribe)){
			this.connect();
		}
		return  "任务返回运行结果,当前任务时间【" + (System.currentTimeMillis()-startTime) + "毫秒】"; 
	}
}
