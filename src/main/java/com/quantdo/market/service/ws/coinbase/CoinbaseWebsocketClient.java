package com.quantdo.market.service.ws.coinbase;

import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import com.quantdo.market.service.ws.AbstractWebsocketClient;
import com.quantdo.market.service.ws.AbstractWebsocketClientHandler;


@Service
public class CoinbaseWebsocketClient extends AbstractWebsocketClient implements Callable<Object> {

	@Value("${coinbase.ws.url}")
    private String url;
	
	@Value("${cache.type}")
    private  String cacheType;
	
	@Autowired
	private HashOperations<String, String, String> redis;
	
	@Autowired
	private CoinbaseInitSubscribe coinbaseInitSubscribe;
	
	@Override
	public AbstractWebsocketClientHandler getClientHandler() {
		return new CoinbaseWebsocketClientHandler(this,redis,cacheType);
	}
	
	@Override
	public Object call() throws Exception {
		Long startTime = System.currentTimeMillis();
		if(this.initWebsocket(url,"",coinbaseInitSubscribe)){
			this.connect();
		}
		return "任务返回运行结果,当前任务时间【" + (System.currentTimeMillis()-startTime) + "毫秒】"; 
	}
}
