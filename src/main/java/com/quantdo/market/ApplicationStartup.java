package com.quantdo.market;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.quantdo.market.entity.TaskInfo;
import com.quantdo.market.mapper.TaskInfoMapper;
import com.quantdo.market.misc.JobStatusEnum;
import com.quantdo.market.service.job.TaskService;
import com.quantdo.market.service.ws.bitstamp.BitstampWebsocketClient;
import com.quantdo.market.service.ws.coinbase.CoinbaseWebsocketClient;


@Service
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent>{
	
	protected final Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private TaskInfoMapper taskInfoMapper;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private BitstampWebsocketClient bitstampWebsocketClient;
	
	@Autowired
	private CoinbaseWebsocketClient coinbaseWebsocketClient;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null ){
			List<TaskInfo> list = taskInfoMapper.findByCondition(null);
			list.forEach(a -> {if(JobStatusEnum.ACTIVE.getValue().equals(a.getJobStatus()))taskService.addJob(a);});
			try {
				bitstampWebsocketClient.call();
			} catch (Exception e) {
				logger.error("Bitstamp交易所Websoakcet启动失败",e);
			}
			try {
				coinbaseWebsocketClient.call();
			} catch (Exception e) {
				logger.error("Coinbase交易所Websoakcet启动失败",e);
			}
		}
	}



}
