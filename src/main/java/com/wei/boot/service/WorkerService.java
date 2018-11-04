package com.wei.boot.service;

import java.util.Date;
import java.util.List;

import com.wei.boot.exception.NormalException;
import com.wei.boot.model.JobType;
import com.wei.boot.model.Page;
import com.wei.boot.model.Worker;
import com.wei.boot.model.WorkerEducation;
import com.wei.boot.model.WorkerExperience;
import com.wei.boot.model.WorkerJobType;

/**
 * 人才信息接口
 * @author weisihua
 * 2018年8月30日 下午2:44:20
 */
public interface WorkerService {

	/**
	 * 分页查询
	 * @param page
	 * @param worker
	 * @return
	 */
	Page<Worker> queryByPage(Page<Worker> page, Worker worker);
	
	/**
	 * 根据身份证号查询
	 * @param idcard
	 * @return
	 */
	boolean queryByIdcard(String idcard);
	
	/**
	 * 新增人才
	 * @param worker
	 */
	void addWorker(Worker worker) throws NormalException;
	
	/**
	 * 更改人才信息
	 * @param worker
	 * @throws NormalException
	 */
	void updateWorker(Worker worker) throws NormalException;
	
	/**
	 * 删除人才信息，同时删除各种绑定关系
	 * @param workerId
	 */
	void deleteWorker(int workerId);
	
	/**
	 * 查询人才信息详情，可用于导出简历
	 * @param workerId
	 * @return
	 */
	Worker queryDetail(int workerId);
	
	/**
	 * 更新人才主体信息
	 * @param worker
	 */
	void updateWorkerBody(Worker worker) throws NormalException;
	
	/**
	 * 更新教育经历信息
	 * @param educationList
	 * @throws NormalException
	 */
	int updateEducation(WorkerEducation education) throws NormalException;
	
	/**
	 * 更新工作经历信息
	 * @param experienceList
	 * @throws NormalException
	 */
	int updateExperience(WorkerExperience experience) throws NormalException;
	
	void deleteEducation(int educationId);
	
	void deleteExperience(int experienceId);
	
	/**
	 * 更新工种信息
	 * @param jobTypeList
	 * @throws NormalException
	 */
	void updateJobType(int workerId, List<WorkerJobType> jobTypeList, String jobTypeName) throws NormalException;
	
	/**
	 * 查询该人的工种信息树
	 * @param workerId
	 * @return
	 */
	List<JobType> querySelectedJobType(int workerId);
	
	/**
	 * 更该人才信息-app特供
	 * @param worker
	 * @throws NormalException
	 */
	void updateWorker4App(Worker worker) throws NormalException;
	
	/**
	 * 分页查询-app特供
	 * @param page
	 * @param worker
	 * @return
	 */
	Page<Worker> queryByPage4App(Page<Worker> page, Worker worker);
	
	/**
	 * 批量插入
	 * @param workerList
	 */
	void insertBatch(List<Worker> workerList);
	
	int queryCountByTime(String type);
	
	int queryAllCount();

	Page<Worker> queryAssignByPage(Page<Worker> page, Worker worker, Integer demandId);
}
