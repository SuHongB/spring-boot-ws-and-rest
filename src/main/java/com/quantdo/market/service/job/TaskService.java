package com.quantdo.market.service.job;

import java.util.List;

import org.quartz.SchedulerException;

import com.quantdo.market.entity.TaskInfo;


public interface TaskService {

	List<TaskInfo> list();
	
	void addJob(TaskInfo info);
	
	void edit(TaskInfo info);
	
	void delete(String jobName, String jobGroup);
	
	void pause(String jobName, String jobGroup);
	
	void resume(String jobName, String jobGroup);
	
	boolean checkExists(String jobName, String jobGroup)throws SchedulerException;
}
