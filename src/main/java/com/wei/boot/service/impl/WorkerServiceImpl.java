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

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.JobTypeMapper;
import com.wei.boot.mapper.WorkerEducationMapper;
import com.wei.boot.mapper.WorkerExperienceMapper;
import com.wei.boot.mapper.WorkerJobTypeMapper;
import com.wei.boot.mapper.WorkerMapper;
import com.wei.boot.model.Area;
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
import com.wei.boot.service.CommonService;
import com.wei.boot.service.JobTypeService;
import com.wei.boot.service.WorkerService;
import com.wei.boot.util.DateUtils;

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
	
	@Autowired
	private CommonService commonService;
	
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
		if(null != worker.getSouce() && worker.getSouce() != 0) {
			map.put("source", worker.getSouce());
		}
		if(null != worker.getCreateUser() && worker.getCreateUser() != 0) {
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
					if(null != jobTypeInfoList && jobTypeInfoList.size() > 0) {
						String jobTypeName = jobTypeInfoList.stream().map(each -> each.getName()).collect(Collectors.toList()).toString();
						info.setJobTypeName(jobTypeName);
						//info.setJobTypeList(jobTypeList);
					}
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
		int workerId = worker.getId();
		workerMapper.updateByPrimaryKeySelective(worker);
		
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
		worker.setUpdateTime(new Date());
		workerMapper.updateByPrimaryKeySelective(worker);
	}

	@Override
	public void updateEducation(int workerId, List<WorkerEducation> educationList) throws NormalException {
		WorkerEducationExample eduExample = new WorkerEducationExample();
		eduExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerEducationMapper.deleteByExample(eduExample);
		if(null != educationList && educationList.size() > 0) {
			for(WorkerEducation edu : educationList) {
				edu.setCreateTime(new Date());
				edu.setWorkerId(workerId);
				workerEducationMapper.insertSelective(edu);
			}
		}
	}

	@Override
	public void updateExperience(int workerId, List<WorkerExperience> experienceList) throws NormalException {
		WorkerExperienceExample expExample = new WorkerExperienceExample();
		expExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerExperienceMapper.deleteByExample(expExample);
		if(null != experienceList && experienceList.size() > 0) {
			for(WorkerExperience exp : experienceList) {
				exp.setCreateTime(new Date());
				exp.setWorkerId(workerId);
				workerExperienceMapper.insertSelective(exp);
			}
		}
	}

	@Override
	public void updateJobType(int workerId, List<WorkerJobType> jobTypeList) throws NormalException {
		WorkerJobTypeExample jobTypeExample = new WorkerJobTypeExample();
		jobTypeExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerJobTypeMapper.deleteByExample(jobTypeExample);
		if(null != jobTypeList && jobTypeList.size() > 0) {
			for(WorkerJobType jobType : jobTypeList) {
				jobType.setWorkerId(workerId);
				workerJobTypeMapper.insert(jobType);
			}
		}
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
			// 翻译性别
			String sexName = commonService.queryDicText(GlobalConstant.DictionaryType.GENDER, worker.getSex());
			worker.setSexName(sexName);
			// 翻译出生日期
			String birthdayName = DateUtils.formatDate(worker.getBirthday(), "yyyy-MM-dd");
			worker.setBirthdayName(birthdayName);
			// 翻译出生地
			Area area = commonService.queryAreaByCode(worker.getBirthplaceCode());
			if(null != area) {
				worker.setBirthplaceName(area.getName());
			}
			// 翻译婚姻状况
			String maritalStatusName = commonService.queryDicText(GlobalConstant.DictionaryType.MARITAL_STATUS, worker.getMaritalStatus());
			worker.setMaritalStatusName(maritalStatusName);
			// 翻译工作地区
			Area area2 = commonService.queryAreaByCode(worker.getWorkplaceCode());
			if(null != area2) {
				worker.setWorkplaceName(area2.getName());
			}
			// 翻译期望薪资
			String expectSalaryName = commonService.queryDicText(GlobalConstant.DictionaryType.EXPECT_SALARY, worker.getExpectSalary());
			worker.setExpectSalaryName(expectSalaryName);
			// 翻译工作状态
			String workStatusName = commonService.queryDicText(GlobalConstant.DictionaryType.WORK_STATUS, worker.getWorkStatus());
			worker.setWorkStatusName(workStatusName);
			// 翻译民族
			String nationName = commonService.queryDicText(GlobalConstant.DictionaryType.NATION, worker.getNation());
			worker.setNationName(nationName);
			// 翻译外语能力
			String languageLevelName = commonService.queryDicText(GlobalConstant.DictionaryType.LANGUAGE_LEVEL, worker.getLanguageLevel());
			worker.setLanguageLevelName(languageLevelName);
			// 翻译是否夜班
			String nightWorkName = commonService.queryDicText(GlobalConstant.DictionaryType.NIGHT_WORK, worker.getNightWork());
			worker.setNightWorkName(nightWorkName);
			// 翻译来源
			String sourceName = commonService.queryDicText(GlobalConstant.DictionaryType.WORKER_SOUCE, worker.getSouce());
			worker.setSourceName(sourceName);
			// 翻译创建时间
			String createTimeName = DateUtils.formatDate(worker.getCreateTime(), "yyyy-MM-dd");
			worker.setCreateTimeName(createTimeName);
		}
	}
	
	private void translateWorker4App(Worker worker) {
		if(null != worker) {
			// 翻译性别
			String sexName = commonService.queryDicText(GlobalConstant.DictionaryType.GENDER, worker.getSex());
			worker.setSexName(sexName);
			// 翻译出生日期
			String birthdayName = DateUtils.formatDate(worker.getBirthday(), "yyyy-MM-dd");
			worker.setBirthdayName(birthdayName);
			// 翻译出生地
			Area area = commonService.queryAreaByCode(worker.getBirthplaceCode());
			if(null != area) {
				worker.setBirthplaceName(area.getName());
			}
			// 翻译婚姻状况
			//String maritalStatusName = commonService.queryDicText(GlobalConstant.DictionaryType.MARITAL_STATUS, worker.getMaritalStatus());
			//worker.setMaritalStatusName(maritalStatusName);
			// 翻译工作地区
			//Area area2 = commonService.queryAreaByCode(worker.getWorkplaceCode());
			//if(null != area2) {
			//	worker.setWorkplaceName(area2.getName());
			//}
			// 翻译期望薪资
			String expectSalaryName = commonService.queryDicText(GlobalConstant.DictionaryType.EXPECT_SALARY, worker.getExpectSalary());
			worker.setExpectSalaryName(expectSalaryName);
			// 翻译工作状态
			String workStatusName = commonService.queryDicText(GlobalConstant.DictionaryType.WORK_STATUS, worker.getWorkStatus());
			worker.setWorkStatusName(workStatusName);
			// 翻译民族
			//String nationName = commonService.queryDicText(GlobalConstant.DictionaryType.NATION, worker.getNation());
			//worker.setNationName(nationName);
			// 翻译外语能力
			//String languageLevelName = commonService.queryDicText(GlobalConstant.DictionaryType.LANGUAGE_LEVEL, worker.getLanguageLevel());
			//worker.setLanguageLevelName(languageLevelName);
			// 翻译是否夜班
			//String nightWorkName = commonService.queryDicText(GlobalConstant.DictionaryType.NIGHT_WORK, worker.getNightWork());
			//worker.setNightWorkName(nightWorkName);
			// 翻译来源
			//String sourceName = commonService.queryDicText(GlobalConstant.DictionaryType.WORKER_SOUCE, worker.getSouce());
			//worker.setSourceName(sourceName);
			// 翻译创建时间
			String createTimeName = DateUtils.formatDate(worker.getCreateTime(), "yyyy-MM-dd");
			worker.setCreateTimeName(createTimeName);
		}
	}

	@Override
	public void updateWorker4App(Worker worker) throws NormalException {
		worker.setUpdateTime(new Date());
		int workerId = worker.getId();
		workerMapper.updateByPrimaryKeySelective(worker);
		
		if(null != worker.getJobTypeList() && worker.getJobTypeList().size() > 0) {
			WorkerJobTypeExample jobTypeExample = new WorkerJobTypeExample();
			jobTypeExample.createCriteria().andWorkerIdEqualTo(workerId);
			workerJobTypeMapper.deleteByExample(jobTypeExample);
			for(WorkerJobType jobType : worker.getJobTypeList()) {
				jobType.setWorkerId(workerId);
				workerJobTypeMapper.insert(jobType);
			}
		}
	}

	@Override
	public Page<Worker> queryByPage4App(Page<Worker> page, Worker worker) {
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
		if(worker.getCreateUser() != 0) {
			map.put("createUser", worker.getCreateUser());
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
					if(null != jobTypeInfoList && jobTypeInfoList.size() > 0) {
						String jobTypeName = jobTypeInfoList.stream().map(each -> each.getName()).collect(Collectors.toList()).toString();
						info.setJobTypeName(jobTypeName);
						//info.setJobTypeList(jobTypeList);
					}
				}
				translateWorker4App(info);
			}
		}
		int totalCount = workerMapper.selectCount(map);
		return page.pageData(workerList, totalCount);
	}
}
