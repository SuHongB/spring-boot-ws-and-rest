package com.quantdo.market.service.ws.bitmex;

import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import com.quantdo.market.service.ws.AbstractWebsocketClient;
import com.quantdo.market.service.ws.AbstractWebsocketClientHandler;

/**
 * Bitmex交易所客户端
 * @author suhongbin
 */
@Service
public class BitmexWebsocketClient extends AbstractWebsocketClient implements Callable<Object> {

	@Value("${bitmex.ws.url}")
    private String url;
	
	@Value("${cache.type}")
    private  String cacheType;
	
	@Autowired(required=false)
	private HashOperations<String, String, String> redis;
	
	@Autowired
	private BitmexInitSubscribe bitmexInitSubscribe;
	
	@Override
	public AbstractWebsocketClientHandler getClientHandler() {
		return new BitmexWebsocketClientHandler(this,redis,cacheType);
	}

	@Override
	public Object call() throws Exception {
		Long startTime = System.currentTimeMillis();
		if(this.initWebsocket(url,"",bitmexInitSubscribe)){
			this.connect();
		}
		return  "任务返回运行结果,当前任务时间【" + (System.currentTimeMillis()-startTime) + "毫秒】"; 
	}
}
