package com.wei.boot.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.WorkerEducationMapper;
import com.wei.boot.mapper.WorkerExperienceMapper;
import com.wei.boot.mapper.WorkerJobTypeMapper;
import com.wei.boot.mapper.WorkerMapper;
import com.wei.boot.model.Area;
import com.wei.boot.model.JobType;
import com.wei.boot.model.Page;
import com.wei.boot.model.Worker;
import com.wei.boot.model.WorkerEducation;
import com.wei.boot.model.WorkerEducationExample;
import com.wei.boot.model.WorkerExample;
import com.wei.boot.model.WorkerExperience;
import com.wei.boot.model.WorkerExperienceExample;
import com.wei.boot.model.WorkerJobType;
import com.wei.boot.model.WorkerJobTypeExample;
import com.wei.boot.service.CommonService;
import com.wei.boot.service.JobTypeService;
import com.wei.boot.service.WorkerService;
import com.wei.boot.util.CheckUtils;
import com.wei.boot.util.DateUtils;
import com.wei.boot.util.ToolsUtil;

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
		if(null != worker.getSouce()) {
			map.put("source", worker.getSouce());
		}
		if(null != worker.getCreateUser() && worker.getCreateUser() != 0) {
			map.put("createUser", worker.getCreateUser());
		}
		if(!StringUtils.isEmpty(worker.getCompany())) {
			map.put("company", worker.getCompany()+"%");
		}
		map.put("firstId", worker.getFirstId());
		map.put("secondId", worker.getSecondId());
		
		List<Worker> workerList = workerMapper.selectByPage(map);
		if(null != workerList && workerList.size() > 0) {
			for(Worker info : workerList) {
				translateWorker4Page(info);
			}
		}
		int totalCount = workerMapper.selectCount(map);
		return page.pageData(workerList, totalCount);
	}

	@Override
	@Transactional
	public void addWorker(Worker worker) throws NormalException {
		worker.setCreateTime(new Date());
		validateWorker(worker);
		workerMapper.insertSelective(worker);
		int workerId = worker.getId();
		if(null != worker.getEducationList() && worker.getEducationList().size() > 0) {
			for(WorkerEducation edu : worker.getEducationList()) {
				edu.setCreateTime(new Date());
				edu.setCreateUser(worker.getCreateUser());
				edu.setWorkerId(workerId);
				workerEducationMapper.insertSelective(edu);
			}
		}
		if(null != worker.getExperienceList() && worker.getExperienceList().size() > 0) {
			for(WorkerExperience exp : worker.getExperienceList()) {
				exp.setCreateTime(new Date());
				exp.setCreateUser(worker.getCreateUser());
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
		validateWorker(worker);
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
			worker.setJobTypeList(jobTypeList);
			
			WorkerEducationExample eduExample = new WorkerEducationExample();
			eduExample.createCriteria().andWorkerIdEqualTo(workerId);
			List<WorkerEducation> educationList = workerEducationMapper.selectByExample(eduExample);
			if(null != educationList && educationList.size() > 0) {
				for(WorkerEducation education : educationList) {
					String degreeName = commonService.queryDicText("degree", education.getDegree());
					education.setDegreeName(degreeName);
				}
			}
			worker.setEducationList(educationList);
			
			WorkerExperienceExample expExample = new WorkerExperienceExample();
			expExample.createCriteria().andWorkerIdEqualTo(workerId);
			List<WorkerExperience> experienceList = workerExperienceMapper.selectByExample(expExample);
			if(null != experienceList && experienceList.size() > 0) {
				for(WorkerExperience experience : experienceList) {
					if(null != experience.getSalary()) {
						String salaryName = commonService.queryDicText("expect_salary", experience.getSalary());
						experience.setSalaryName(salaryName);
					}
				}
			}
			worker.setExperienceList(experienceList);
		}
		translateWorker(worker);
		return worker;
	}

	@Override
	public void updateWorkerBody(Worker worker) throws NormalException {
		validateWorker(worker);
		worker.setUpdateTime(new Date());
		//workerMapper.updateByPrimaryKeySelective(worker);
		
		Worker realWorker = workerMapper.selectByPrimaryKey(worker.getId());
		worker.setCreateTime(realWorker.getCreateTime());
		worker.setCreateUser(realWorker.getCreateUser());
		worker.setSouce(realWorker.getSouce());
		workerMapper.updateByPrimaryKey(worker);
	}

	@Override
	public int updateEducation(WorkerEducation education) throws NormalException {
		int id = 0;
		// 更改
		if(education.getId() != null && education.getId() != 0) {
			education.setUpdateTime(new Date());
			education.setCreateTime(new Date());
			workerEducationMapper.updateByPrimaryKey(education);
			id = education.getId();
		}
		// 新增
		else {
			education.setCreateTime(new Date());
			workerEducationMapper.insertSelective(education);
			id = education.getId();
		}
		return id;
	}

	@Override
	public int updateExperience(WorkerExperience experience) throws NormalException {
		int id = 0;
		// 更改
		if(experience.getId() != null && experience.getId() != 0) {
			experience.setUpdateTime(new Date());
			experience.setCreateTime(new Date());
			workerExperienceMapper.updateByPrimaryKey(experience);
			id = experience.getId();
		}
		// 新增
		else {
			experience.setCreateTime(new Date());
			workerExperienceMapper.insertSelective(experience);
			id = experience.getId();
		}
		return id;
	}

	@Override
	public void updateJobType(int workerId, List<WorkerJobType> jobTypeList, String jobTypeName) throws NormalException {
		WorkerJobTypeExample jobTypeExample = new WorkerJobTypeExample();
		jobTypeExample.createCriteria().andWorkerIdEqualTo(workerId);
		workerJobTypeMapper.deleteByExample(jobTypeExample);
		if(null != jobTypeList && jobTypeList.size() > 0) {
			for(WorkerJobType jobType : jobTypeList) {
				jobType.setWorkerId(workerId);
				workerJobTypeMapper.insert(jobType);
			}
		}
		// 更改工种名称
		Worker worker = new Worker();
		worker.setId(workerId);
		worker.setJobtypeName(jobTypeName);
		workerMapper.updateByPrimaryKeySelective(worker);
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
			if(null != worker.getSex()) {
				String sexName = commonService.queryDicText(GlobalConstant.DictionaryType.GENDER, worker.getSex());
				worker.setSexName(sexName);
			}
			// 翻译出生地
			if(null != worker.getBirthplaceCode()) {
				Area area = commonService.queryAreaByCode(worker.getBirthplaceCode());
				if(null != area) {
					worker.setBirthplaceName(area.getName());
				}
			}
			// 翻译婚姻状况
			if(null != worker.getMaritalStatus()) {
				String maritalStatusName = commonService.queryDicText(GlobalConstant.DictionaryType.MARITAL_STATUS, worker.getMaritalStatus());
				worker.setMaritalStatusName(maritalStatusName);
			}
			// 翻译工作地区
			if(null != worker.getWorkplaceCode()) {
				Area area2 = commonService.queryAreaByCode(worker.getWorkplaceCode());
				if(null != area2) {
					worker.setWorkplaceName(area2.getName());
				}
			}
			// 翻译期望薪资
			if(null != worker.getExpectSalary()) {
				String expectSalaryName = commonService.queryDicText(GlobalConstant.DictionaryType.EXPECT_SALARY, worker.getExpectSalary());
				worker.setExpectSalaryName(expectSalaryName);
			}
			// 翻译工作状态
			if(null != worker.getWorkStatus()) {
				String workStatusName = commonService.queryDicText(GlobalConstant.DictionaryType.WORK_STATUS, worker.getWorkStatus());
				worker.setWorkStatusName(workStatusName);
			}
			// 翻译民族
			if(null != worker.getNation()) {
				String nationName = commonService.queryDicText(GlobalConstant.DictionaryType.NATION, worker.getNation());
				worker.setNationName(nationName);
			}
			// 翻译外语能力
			if(null != worker.getLanguageLevel()) {
				String languageLevelName = commonService.queryDicText(GlobalConstant.DictionaryType.LANGUAGE_LEVEL, worker.getLanguageLevel());
				worker.setLanguageLevelName(languageLevelName);
			}
			// 翻译是否夜班
			if(null != worker.getNightWork()) {
				String nightWorkName = commonService.queryDicText(GlobalConstant.DictionaryType.NIGHT_WORK, worker.getNightWork());
				worker.setNightWorkName(nightWorkName);
			}
			// 翻译来源
			if(null != worker.getSouce()) {
				String sourceName = commonService.queryDicText(GlobalConstant.DictionaryType.WORKER_SOUCE, worker.getSouce());
				worker.setSourceName(sourceName);
			}
			// 翻译年龄
			if(null != worker.getDegree()) {
				String degreeName = commonService.queryDicText("degree", worker.getDegree());
				worker.setDegreeName(degreeName);
			}
			
			// 翻译年龄
			String birthday = null;
			if(!StringUtils.isEmpty(worker.getIdcard()) && CheckUtils.isIdCard(worker.getIdcard())) {
				birthday = DateUtils.formatDate(DateUtils.parseDate(worker.getIdcard().substring(6, 14)), "yyyy-MM-dd");
			}
			if(null != worker.getBirthday() && null == birthday) {
				birthday = DateUtils.formatDate(worker.getBirthday(), "yyyy-MM-dd");
			}
			if(null != birthday) {
				worker.setAge(ToolsUtil.getAgeFromBirthTime(birthday));
			}
		}
	}
	
	private void translateWorker4Page(Worker worker) {
		if(null != worker) {
			// 翻译性别
			String sexName = commonService.queryDicText(GlobalConstant.DictionaryType.GENDER, worker.getSex());
			worker.setSexName(sexName);
			// 翻译工作状态
			if(null != worker.getWorkStatus()) {
				String workStatusName = commonService.queryDicText(GlobalConstant.DictionaryType.WORK_STATUS, worker.getWorkStatus());
				worker.setWorkStatusName(workStatusName);
			}
			// 翻译来源
			if(null != worker.getSouce()) {
				String sourceName = commonService.queryDicText(GlobalConstant.DictionaryType.WORKER_SOUCE, worker.getSouce());
				worker.setSourceName(sourceName);
			}
			// 翻译年龄
			String birthday = null;
			if(!StringUtils.isEmpty(worker.getIdcard()) && CheckUtils.isIdCard(worker.getIdcard())) {
				birthday = DateUtils.formatDate(DateUtils.parseDate((worker.getIdcard().substring(6, 14))), "yyyy-MM-dd");
			}
			if(null != worker.getBirthday() && null == birthday) {
				birthday = DateUtils.formatDate(worker.getBirthday(), "yyyy-MM-dd");
			}
			if(null != birthday) {
				worker.setAge(ToolsUtil.getAgeFromBirthTime(birthday));
			}
		}
	}
	
	private void translateWorker4App(Worker worker) {
		if(null != worker) {
			// 翻译性别
			if(null != worker.getSex()) {
				String sexName = commonService.queryDicText(GlobalConstant.DictionaryType.GENDER, worker.getSex());
				worker.setSexName(sexName);
			}
			// 翻译出生地
			if(null != worker.getBirthplaceCode()) {
				Area area = commonService.queryAreaByCode(worker.getBirthplaceCode());
				if(null != area) {
					worker.setBirthplaceName(area.getName());
				}
			}
			if(null != worker.getExpectSalary()) {
				// 翻译期望薪资
				String expectSalaryName = commonService.queryDicText(GlobalConstant.DictionaryType.EXPECT_SALARY, worker.getExpectSalary());
				worker.setExpectSalaryName(expectSalaryName);
			}
			if(null != worker.getWorkStatus()) {
				// 翻译工作状态
				String workStatusName = commonService.queryDicText(GlobalConstant.DictionaryType.WORK_STATUS, worker.getWorkStatus());
				worker.setWorkStatusName(workStatusName);
			}
			// 翻译年龄
			String birthday = null;
			if(!StringUtils.isEmpty(worker.getIdcard()) && CheckUtils.isIdCard(worker.getIdcard())) {
				birthday = DateUtils.formatDate(DateUtils.parseDate((worker.getIdcard().substring(6, 14))), "yyyy-MM-dd");
			}
			if(null != worker.getBirthday() && null == birthday) {
				birthday = DateUtils.formatDate(worker.getBirthday(), "yyyy-MM-dd");
			}
			if(null != birthday) {
				worker.setAge(ToolsUtil.getAgeFromBirthTime(birthday));
			}
		}
	}
	
	
	
	private void translateWorker4Assign(Worker worker) {
		if(null != worker) {
			// 翻译期望薪资
			if(null != worker.getExpectSalary()) {
				String expectSalaryName = commonService.queryDicText(GlobalConstant.DictionaryType.EXPECT_SALARY, worker.getExpectSalary());
				worker.setExpectSalaryName(expectSalaryName);
			}
		}
	}

	@Override
	public void updateWorker4App(Worker worker) throws NormalException {
		validateWorker(worker);
		int workerId = worker.getId();
		Worker realWorker = workerMapper.selectByPrimaryKey(worker.getId());
		realWorker.setUpdateTime(new Date());
		realWorker.setName(worker.getName());
		realWorker.setSex(worker.getSex());
		realWorker.setTelephone(worker.getTelephone());
		realWorker.setIdcard(worker.getIdcard());
		realWorker.setBirthday(worker.getBirthday());
		realWorker.setBirthplaceCode(worker.getBirthplaceCode());
		realWorker.setWorkYear(worker.getWorkYear());
		realWorker.setWorkExpect(worker.getWorkExpect());
		realWorker.setJobtypeName(worker.getJobtypeName());
		realWorker.setWorkplaceCode(worker.getWorkplaceCode());
		realWorker.setExpectSalary(worker.getExpectSalary());
		realWorker.setWorkStatus(worker.getWorkStatus());
		realWorker.setDegree(worker.getDegree());
		workerMapper.updateByPrimaryKey(realWorker);
		
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
				//List<WorkerJobType> jobTypeList = workerJobTypeMapper.selectByExample(jobTypeExample);
				translateWorker4App(info);
			}
		}
		int totalCount = workerMapper.selectCount(map);
		return page.pageData(workerList, totalCount);
	}

	@Override
	public void deleteEducation(int educationId) {

		workerEducationMapper.deleteByPrimaryKey(educationId);
	}

	@Override
	public void deleteExperience(int experienceId) {
		workerExperienceMapper.deleteByPrimaryKey(experienceId);
	}
	
	private void validateWorker(Worker worker) throws NormalException {
		Objects.requireNonNull(worker.getName(), "请输入姓名！");
		Objects.requireNonNull(worker.getTelephone(), "请输入手机号！");
		Objects.requireNonNull(worker.getIdcard(), "请输入身份证号！");
		if(!CheckUtils.isPhone(worker.getTelephone()) && !CheckUtils.isMobile(worker.getTelephone())) {
			throw new NormalException("请输入正确的联系电话！");
		}
		// 检查身份证
		if(!CheckUtils.isIdCard(worker.getIdcard())) {
			throw new NormalException("请输入正确的身份证号！");
		}
	}

	@Override
	public boolean queryByIdcard(String idcard) {
		WorkerExample example = new WorkerExample();
		example.createCriteria().andIdcardEqualTo(idcard);
		List<Worker> list = workerMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void insertBatch(List<Worker> workerList) {
		workerMapper.insertBatch(workerList);
	}

	@Override
	public int queryCountByTime(String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(flag)) {
			Calendar cal = Calendar.getInstance();
			if("today".equals(flag)) {
				String beginTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd")+" 00:00:00";
				map.put("beginTime", DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm:ss"));
			}
			else if("week".equals(flag)) {
				cal.add(Calendar.DAY_OF_MONTH, -7);
				map.put("beginTime", cal.getTime());
			}
			else if("oneMonth".equals(flag)) {
				cal.add(Calendar.MONTH, -1);
				map.put("beginTime", cal.getTime());
			}
			else if("threeMonth".equals(flag)) {
				cal.add(Calendar.MONTH, -3);
				map.put("beginTime", cal.getTime());
			}
			else if("year".equals(flag)) {
				cal.add(Calendar.YEAR, -1);
				map.put("beginTime", cal.getTime());
			}
			
		}
		return workerMapper.selectCountByTime(map);
	}

	@Override
	public int queryAllCount() {
		return workerMapper.selectAllCount();
	}

	@Override
	public Page<Worker> queryAssignByPage(Page<Worker> page, Worker worker, Integer demandId) {

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
	
		if(Objects.nonNull(demandId)) {
			map.put("demandId", demandId);
		}
		List<Worker> workerList = workerMapper.selectAssignByPage(map);
		if(null != workerList && workerList.size() > 0) {
			for(Worker info : workerList) {
				translateWorker4Assign(info);
			}
		}
		int totalCount = workerMapper.selectAssignCount(map);
		return page.pageData(workerList, totalCount);
	
	}
}
