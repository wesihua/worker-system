package com.wei.boot.service;

import java.util.List;

import com.wei.boot.exception.NormalException;
import com.wei.boot.model.JobType;

/**
 * 工种service
 * @author weisihua
 * 2018年8月8日 上午11:27:14
 */
public interface JobTypeService {

	/**
	 * 新增工种
	 * @param jobType
	 * @throws Exception
	 */
	void saveJobType(JobType jobType) throws NormalException;
	
	/**
	 * 删除工种
	 * @param id
	 * @throws Exception
	 */
	void deleteJobType(int id) throws NormalException;
	
	/**
	 * 查询所有父级工种
	 * @return
	 * @throws Exception
	 */
	List<JobType> selectAllRoot() throws NormalException;
	
	/**
	 * 根据parentId查询子集
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<JobType> selectByParentId(int parentId);
	
	/**
	 * 查询返回树形结构的全部工种
	 * @return
	 * @throws Exception
	 */
	List<JobType> selectAllTree();
	

	/**
	 *  查询所有二级节点
	 * @return
	 */
	List<JobType> selectChildJobType();
	
	/**
	 * 导入工种
	 * @param list
	 */
	void insertJobType(List<JobType> list);
	
}
