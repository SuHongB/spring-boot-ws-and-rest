package com.quantdo.market.service.job.taskJob;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.quantdo.market.service.rest.RestBitstampService;
import com.quantdo.market.util.DataUtil;


@Component
public class BitstampTickerJob  implements Job {
	
	private Logger logger = LoggerFactory.getLogger(BitstampTickerJob.class);
	
	@Autowired
	private RestBitstampService restBitstampService;
	
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	logger.info("BitstampJob定时任务开始...");
    	Long time = System.currentTimeMillis();
    	restBitstampService.saveTicker(DataUtil.getBitstamp());
		logger.info("BitstampJob定时任务结束，耗时"+(System.currentTimeMillis()-time)+"毫秒");
    }

}