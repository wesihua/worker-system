package com.wei.boot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.JobTypeMapper;
import com.wei.boot.mapper.WorkerEducationMapper;
import com.wei.boot.mapper.WorkerExperienceMapper;
import com.wei.boot.mapper.WorkerJobTypeMapper;
import com.wei.boot.mapper.WorkerMapper;
import com.wei.boot.model.JobType;
import com.wei.boot.model.JobTypeExample;
import com.wei.boot.model.Page;
import com.wei.boot.model.Worker;
import com.wei.boot.model.WorkerEducation;
import com.wei.boot.model.WorkerEducationExample;
import com.wei.boot.model.WorkerExperience;
import com.wei.boot.model.WorkerExperienceExample;
import com.wei.boot.model.WorkerJobType;
import com.wei.boot.model.WorkerJobTypeExample;
import com.wei.boot.service.JobTypeService;
import com.wei.boot.service.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private WorkerMapper workerMapper;
	
	@Autowired
	private WorkerEducationMapper workerEducationMapper;
	
	@Autowired
	private WorkerExperienceMapper workerExperienceMapper;
	
	@Autowired
	private WorkerJobTypeMapper workerJobTypeMapper;
	
	@Autowired
	private JobTypeMapper jobTypeMapper;
	
	@Autowired
	private JobTypeService jobTypeService;
	
	@Override
	public Page<Worker> queryByPage(Page<Worker> page, Worker worker) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", page.getOffset());
		map.put("pageSize", page.getPageSize());
		if(!StringUtils.isEmpty(worker.getIdcard())) {
			map.put("idcard", worker.getIdcard());
		}
		if(!StringUtils.isEmpty(worker.getTelephone())) {
			map.put("telephone", worker.getTelephone());
		}
		if(!StringUtils.isEmpty(worker.getName())) {
			map.put("name", worker.getName()+"%");
		}
		if(!StringUtils.isEmpty(worker.getBeginTime())) {
			map.put("beginTime", worker.getBeginTime());
		}
		if(!StringUtils.isEmpty(worker.getEndTime())) {
			map.put("endTime", worker.getEndTime());
		}
		if(worker.getSouce() != 0) {
			map.put("source", worker.getSouce());
		}
		if(worker.getCreateUser() != 0) {
			map.put("createUser", worker.getCreateUser());
		}
		if(worker.getFirstId() != 0) {
			map.put("firstId", worker.getFirstId());
		}
		if(worker.getSecondId() != 0) {
			map.put("secondId", worker.getSecondId());
		}
		
		List<Worker> workerList = workerMapper.selectByPage(map);
		if(null != workerList && workerList.size() > 0) {
			for(Worker info : workerList) {
				WorkerJobTypeExample jobTypeExample = new WorkerJobTypeExample();
				jobTypeExample.createCriteria().andWorkerIdEqualTo(info.getId()); 
				List<WorkerJobType> jobTypeList = workerJobTypeMapper.selectByExample(jobTypeExample);
				// 翻译工种
				if(null != jobTypeList && jobTypeList.size() > 0) {
					List<Integer> jobTypeIds = jobTypeList.stream().map(each -> each.getSecondId()).collect(Collectors.toList());
					JobTypeExample jtExample = new JobTypeExample();
					jtExample.createCriteria().andIdIn(jobTypeIds);
					List<JobType> jobTypeInfoList = jobTypeMapper.selectByExample(jtExample);
					String jobTypeName = jobTypeInfoList.stream().map(each -> each.getName()).collect(Collectors.toList()).toString();
					info.setJobTypeName(jobTypeName);
					//info.setJobTypeList(jobTypeList);
				}
				translateWorker(info);
			}
		}
		int totalCount = workerMapper.selectCount(map);
		return page.pageData(workerList, totalCount);
	}

	@Override
	@Transactional
	public void addWorker(Worker worker) throws NormalException {
		worker.setCreateTime(new Date());
		int workerId = workerMapper.insertSelective(worker);
		if(null != worker.getEducationList() && worker.getEducationList().size() > 0) {
			for(WorkerEducation edu : worker.getEducationList()) {
				edu.setCreateTime(new Date());
				edu.setWorkerId(workerId);
				workerEducationMapper.insertSelective(edu);
			}
		}
		if(null != worker.getExperienceList() && worker.getExperienceList().size() > 0) {
			for(WorkerExperience exp : worker.getExperienceList()) {
				exp.setCreateTime(new Date());
				exp.setWorkerId(workerId);
				workerExperienceMapper.insertSelective(exp);
			}
		}
		if(null != worker.getJobTypeList() && worker.getJobTypeList().size() > 0) {
			for(WorkerJobType jobType : worker.getJobTypeList()) {
				jobType.setWorkerId(workerId);
				workerJobTypeMapper.insert(jobType);
			}
		}
	}

	@Override
	@Transactional
	public void updateWorker(Worker worker) throws NormalException {

		worker.setUpdateTime(new Date());
		int workerId = workerMapper.updateByPrimaryKeySelective(worker);
		
		// 先删除绑定关系
		WorkerEducationExample eduExample = new WorkerEducationExample();
		eduExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerEducationMapper.deleteByExample(eduExample);
		WorkerExperienceExample expExample = new WorkerExperienceExample();
		expExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerExperienceMapper.deleteByExample(expExample);
		WorkerJobTypeExample jobTypeExample = new WorkerJobTypeExample();
		jobTypeExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerJobTypeMapper.deleteByExample(jobTypeExample);
		// 重新绑定关系
		if(null != worker.getEducationList() && worker.getEducationList().size() > 0) {
			for(WorkerEducation edu : worker.getEducationList()) {
				edu.setCreateTime(new Date());
				edu.setWorkerId(workerId);
				workerEducationMapper.insertSelective(edu);
			}
		}
		if(null != worker.getExperienceList() && worker.getExperienceList().size() > 0) {
			for(WorkerExperience exp : worker.getExperienceList()) {
				exp.setCreateTime(new Date());
				exp.setWorkerId(workerId);
				workerExperienceMapper.insertSelective(exp);
			}
		}
		if(null != worker.getJobTypeList() && worker.getJobTypeList().size() > 0) {
			for(WorkerJobType jobType : worker.getJobTypeList()) {
				jobType.setWorkerId(workerId);
				workerJobTypeMapper.insert(jobType);
			}
		}
	}

	@Override
	@Transactional
	public void deleteWorker(int workerId) {
		workerMapper.deleteByPrimaryKey(workerId);
		// 删除绑定关系
		WorkerEducationExample eduExample = new WorkerEducationExample();
		eduExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerEducationMapper.deleteByExample(eduExample);
		WorkerExperienceExample expExample = new WorkerExperienceExample();
		expExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerExperienceMapper.deleteByExample(expExample);
		WorkerJobTypeExample jobTypeExample = new WorkerJobTypeExample();
		jobTypeExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerJobTypeMapper.deleteByExample(jobTypeExample);
	}

	@Override
	public Worker queryDetail(int workerId) {
		Worker worker = workerMapper.selectByPrimaryKey(workerId);
		if(null != worker) {
			WorkerJobTypeExample jobTypeExample = new WorkerJobTypeExample();
			jobTypeExample.createCriteria().andWorkerIdEqualTo(workerId); 
			List<WorkerJobType> jobTypeList = workerJobTypeMapper.selectByExample(jobTypeExample);
			// 翻译工种
			if(null != jobTypeList && jobTypeList.size() > 0) {
				List<Integer> jobTypeIds = jobTypeList.stream().map(each -> each.getSecondId()).collect(Collectors.toList());
				JobTypeExample jtExample = new JobTypeExample();
				jtExample.createCriteria().andIdIn(jobTypeIds);
				List<JobType> jobTypeInfoList = jobTypeMapper.selectByExample(jtExample);
				String jobTypeName = jobTypeInfoList.stream().map(each -> each.getName()).collect(Collectors.toList()).toString();
				worker.setJobTypeName(jobTypeName);
				worker.setJobTypeList(jobTypeList);
			}
			WorkerEducationExample eduExample = new WorkerEducationExample();
			eduExample.createCriteria().andWorkerIdEqualTo(workerId);
			List<WorkerEducation> educationList = workerEducationMapper.selectByExample(eduExample);
			worker.setEducationList(educationList);
			
			WorkerExperienceExample expExample = new WorkerExperienceExample();
			expExample.createCriteria().andWorkerIdEqualTo(workerId);
			List<WorkerExperience> experienceList = workerExperienceMapper.selectByExample(expExample);
			worker.setExperienceList(experienceList);
		}
		translateWorker(worker);
		return worker;
	}

	@Override
	public void updateWorkerBody(Worker worker) throws NormalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEducation(List<WorkerEducation> educationList) throws NormalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateExperience(List<WorkerExperience> experienceList) throws NormalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateJobType(List<WorkerJobType> jobTypeList) throws NormalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<JobType> querySelectedJobType(int workerId) {
		// 工种全树
		List<JobType> jobTypeInfoList = jobTypeService.selectAllTree();
		// 收集该人所选工种id集合
		WorkerJobTypeExample jobTypeExample = new WorkerJobTypeExample();
		jobTypeExample.createCriteria().andWorkerIdEqualTo(workerId); 
		List<WorkerJobType> jobTypeList = workerJobTypeMapper.selectByExample(jobTypeExample);
		// 收集所有选中的工种，包括大类和小类
		List<Integer> jobTypeIds = new ArrayList<Integer>();
		if(null != jobTypeList && jobTypeList.size() > 0) {
			// 收集大类
			Set<Integer> firstIds = jobTypeList.stream().map(each -> each.getFirstId()).collect(Collectors.toSet());
			// 收集小类
			List<Integer> secondIds = jobTypeList.stream().map(each -> each.getSecondId()).collect(Collectors.toList());
			// 全部放在一个集合里
			jobTypeIds.addAll(new ArrayList<Integer>(firstIds));
			jobTypeIds.addAll(secondIds);
		}
		// 处理选中
		revalueJobTypeTree(jobTypeInfoList, jobTypeIds);
		return jobTypeInfoList;
	}
	
	/**
	 * 给选中的工种赋值
	 * @param jobTypeList
	 * @param jobTypeIds
	 */
	private void revalueJobTypeTree(List<JobType> jobTypeList, List<Integer> jobTypeIds) {
		if(null !=jobTypeIds && jobTypeIds.size() > 0) {
			for(JobType type : jobTypeList) {
				if(jobTypeIds.contains(type.getId())) {
					type.setSelected(1);
				}
				if(null != type.getChildren() && type.getChildren().size() > 0) {
					revalueJobTypeTree(type.getChildren(), jobTypeIds);
				}
			}
		}
	}
	
	/**
	 * 翻译字段信息
	 * @param worker
	 */
	private void translateWorker(Worker worker) {
		if(null != worker) {
			// 翻译时间
			
		}
	}
}
