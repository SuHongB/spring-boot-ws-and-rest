package com.quantdo.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.quantdo.market.entity.TaskInfo;

public interface TaskInfoMapper {

	/**
	 * 条件查询
	 * @param entity
	 * @return
	 */
	List<TaskInfo> findByCondition(@Param("taskInfo")TaskInfo entity);
	
}
