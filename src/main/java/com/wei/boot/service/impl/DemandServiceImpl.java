package com.wei.boot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.contant.GlobalConstant.DemandState;
import com.wei.boot.mapper.DemandJobMapper;
import com.wei.boot.mapper.DemandMapper;
import com.wei.boot.mapper.JobTypeMapper;
import com.wei.boot.mapper.OrderWorkerMapper;
import com.wei.boot.mapper.WorkerMapper;
import com.wei.boot.model.Area;
import com.wei.boot.model.Company;
import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandJob;
import com.wei.boot.model.DemandJobExample;
import com.wei.boot.model.DemandQuery;
import com.wei.boot.model.DemandStateStatistic;
import com.wei.boot.model.JobType;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.Page;
import com.wei.boot.model.Worker;
import com.wei.boot.model.signing.JobTypeModel;
import com.wei.boot.service.CommonService;
import com.wei.boot.service.CompanyService;
import com.wei.boot.service.DemandService;
import com.wei.boot.util.DateUtils;

@Service
public class DemandServiceImpl implements DemandService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DemandServiceImpl.class);
	
	@Autowired
	private DemandMapper demandMapper;
	
	@Autowired
	private DemandJobMapper demandJobMapper;
	
	@Autowired
	private OrderWorkerMapper orderWorkerMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private JobTypeMapper jobTypeMapper;
	
	@Autowired
	private WorkerMapper workerMapper;

	@Override
	@Transactional
	public void saveDemand(Demand demand) {
		
		LOGGER.debug("enter saveDemand,demand={}",demand);
		Date createTime = new Date();

		// 保存需求信息
		demand.setCreateTime(createTime);
		demand.setState(0);
		// 生成需求单号
		String demandNumber = createDemandNumber(demand);
		demand.setDemandNumber(demandNumber);
		
		demandMapper.insertSelective(demand);
		int demandId =  demand.getId();
		// 保存需求用工信息
		List<DemandJob> demandJobList = demand.getDemandJobList();
		if(!CollectionUtils.isEmpty(demandJobList)) {
			demandJobList.stream().forEach(demandJob -> {
				demandJob.setDemandId(demandId);
				demandJob.setCreateTime(createTime);
				demandJob.setCreateUser(demand.getCreateUser());
				// 到岗时间
				demandJob.setRequireTime(createTime);
				demandJobMapper.insertSelective(demandJob);
			});
		}
		
		LOGGER.debug("exit saveDemand");

	}

	private String createDemandNumber(Demand demand) {
		Integer companyId = demand.getCompanyId();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("createTime", DateUtils.getYearStart(new Date()));
		int demandCount =demandMapper.selectCampanyLastDemandNumber(map);
		return DateUtils.getCurYear() + "-00" +companyId + "-00" + (demandCount + 1);
	}


	@Override
	public Page<Demand> queryDemand(Page<Demand> page, DemandQuery demandQuery) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//处理时间
		timeTransform(demandQuery);
		if(Objects.nonNull(demandQuery.getCompanyId())) {
			map.put("companyId", demandQuery.getCompanyId());
		}
		if(Objects.nonNull(demandQuery.getCreateBeginTime())) {
			map.put("createBeginTime", demandQuery.getCreateBeginTime());
		}
		if(Objects.nonNull(demandQuery.getCreateEndTime())) {
			map.put("createEndTime", demandQuery.getCreateEndTime());
		}

		if(Objects.nonNull(demandQuery.getCloseBeginTime())) {
			map.put("closeBeginTime", demandQuery.getCloseBeginTime());
		}
		if(Objects.nonNull(demandQuery.getCloseEndTime())) {
			map.put("closeEndTime", demandQuery.getCloseEndTime());
		}
		
		if(Objects.nonNull(demandQuery.getState())) {
			map.put("state", demandQuery.getState());
		}
		int totalCount = demandMapper.selectCount(map);
		
		map.put("pageSize", page.getPageSize());
		map.put("offset", page.getOffset());
		List<Demand> list = demandMapper.selectByPage(map);
		if(!CollectionUtils.isEmpty(list)) {
			list.stream().forEach(demand->{
				translateDemand(demand);
			});
		}
		
		page.pageData(list, totalCount);
		return page;
		
	}

	private void timeTransform(DemandQuery demandQuery) {
		try {
			if (Objects.nonNull(demandQuery.getState()) || !StringUtils.isEmpty(demandQuery.getTimeStr())) {
				Date date = DateUtils.parseDate(demandQuery.getTimeStr());
				Date dayStart = DateUtils.getDayStart(date);
				Date dayEnd = DateUtils.getDayEnd(date);
				if (Objects.equals(DemandState.CLOSE, demandQuery.getState())) {
					demandQuery.setCloseBeginTime(dayStart);
					demandQuery.setCloseEndTime(dayEnd);
				} else {
					demandQuery.setCreateBeginTime(dayStart);
					demandQuery.setCreateEndTime(dayEnd);
				}
			}
		} catch (Exception e) {
			LOGGER.error("timeTransform error demandQuery={}", demandQuery);
		}

	}

	@Override
	public Demand queryDemandById(Integer demandId) {
		
		// 需求单
		Demand demand = demandMapper.selectByPrimaryKey(demandId);
		
		// 翻译需求
		translateDemand(demand);
		
		List<DemandJob> demandJobList = queryDemandJobByDemandId(demandId);
		if(!CollectionUtils.isEmpty(demandJobList)) {
			demandJobList.stream().forEach(demandJob->{
				translateDemandJob(demand,demandJob);
			});
		}
		demand.setDemandJobList(demandJobList);
		
		return demand;
	}

	private List<DemandJob> queryDemandJobByDemandId(Integer demandId) {
		DemandJobExample example  = new DemandJobExample();
		example.createCriteria().andDemandIdEqualTo(demandId);
		// 需求单工种
		List<DemandJob> demandJobList = demandJobMapper.selectByExample(example );
		return demandJobList;
	}
	
	private void translateDemandJob(Demand demand, DemandJob demandJob) {
		if(null != demandJob) {
			// 用工地区
			Area area = commonService.queryAreaByCode(demandJob.getWorkArea());
			
			demandJob.setWorkAreaName(area == null ? "": area.getName());
			
			// 工种名字
			String jobTypeName = queryJobTypeName(demandJob.getJobTypeId());
			
			demandJob.setJobTypeName(jobTypeName);
			
			// TODO 修改签约人数
		}
		
	}

	/**
	 * 查工种名字
	 * @param jobTypeId
	 * @return
	 */
	private String queryJobTypeName(Integer jobTypeId) {
		JobType jobType = jobTypeMapper.selectByPrimaryKey(jobTypeId);
		return jobType == null ? "": jobType.getName();
	}

	private void translateDemand(Demand demand) {
		if(null != demand) {
			// 公司名称
			Company company = companyService.queryById(demand.getCompanyId());
			demand.setCompanyName(company == null ? "":company.getName());
			// 关单人
			demand.setCloseUserName(commonService.queryUserName(demand.getCloseUser()));
			// 创建人
			demand.setCreateUserName(commonService.queryUserName(demand.getCreateUser()));
			// 操作人员
			demand.setUndertakeUserName(commonService.queryUserName(demand.getUndertakeUser()));
			
			if(Objects.equals(GlobalConstant.DemandState.SIGNING, demand.getState())) {
				// TODO  收入总额
				demand.setTotalIncome(0);
			}
			
			// 状态
			demand.setStateName(commonService.queryDicText(GlobalConstant.DictionaryType.DEMAND_STATE, demand.getState()));
			
			// 工种 // 用工人数
			List<DemandJob> demandJobList = queryDemandJobByDemandId(demand.getId());
			
			List<String> jobNames = new ArrayList<>();
			Integer workerCount = 0;
			if(!CollectionUtils.isEmpty(demandJobList)) {
				for (DemandJob job : demandJobList) {
					workerCount = workerCount + job.getWorkerCount();
					jobNames.add(queryJobTypeName(job.getJobTypeId()));
				}
				
				demand.setWorkCount(workerCount);
				demand.setJobTypeName(StringUtils.collectionToDelimitedString(jobNames, "、"));
			}
			// 
		}
	}

	@Override
	public JobTypeModel queryOrderWorker(Page<OrderWorker> page, Integer demandJobId) {
		
		JobTypeModel jobTypeModel = new JobTypeModel();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(Objects.nonNull(demandJobId)) {
			map.put("demandJobId", demandJobId);
		}
		int totalCount = orderWorkerMapper.selectCount(map);
		
		map.put("pageSize", page.getPageSize());
		map.put("offset", page.getOffset());
		List<OrderWorker> list = orderWorkerMapper.selectByPage(map);
		
		if(!CollectionUtils.isEmpty(list)) {
			for (OrderWorker orderWorker : list) {
				translateOrderWorker(orderWorker);
			}
		}
		page.pageData(list, totalCount);
		DemandJob demandJob = queryDemandJobById(demandJobId);
		jobTypeModel.setDemandJob(demandJob);
		jobTypeModel.setPageData(page);
		
		return jobTypeModel;
	}

	private void translateOrderWorker(OrderWorker orderWorker) {
		Worker worker = workerMapper.selectByPrimaryKey(orderWorker.getWorkerId());
		// 翻译出生地
		if(Objects.nonNull( worker.getBirthplaceCode())) {
			Area area = commonService.queryAreaByCode(worker.getBirthplaceCode());
			if(Objects.nonNull(area)) {
				worker.setBirthplaceName(area.getName());
			}
		}
		orderWorker.setWorker(worker);
	}

	private DemandJob queryDemandJobById(Integer demandJobId) {
		DemandJob demandJob = demandJobMapper.selectByPrimaryKey(demandJobId);
		String jobTypeName = queryJobTypeName(demandJob.getJobTypeId());
		demandJob.setJobTypeName(jobTypeName);
		return demandJob;
	}

	@Override
	public void closeDemand(Demand demand) {
		Integer demandId = demand.getId();
		Demand demandDb = demandMapper.selectByPrimaryKey(demandId);
		demandDb.setCloseTime(new Date());
		demandDb.setCloseReason(demand.getCloseReason());
		demandDb.setState(GlobalConstant.DemandState.CLOSE);
		demandDb.setCloseUser(demand.getCloseUser());
		demandMapper.updateByPrimaryKey(demandDb);
	}
	
	@Override
	public List<DemandStateStatistic> statisticsByState() {
		List<DemandStateStatistic> list = new ArrayList<>();
		try {
			List<Map<String, String>> baseList = demandMapper.statisticsByState();
			for (Map<String, String> map : baseList) {
				DemandStateStatistic readValue = objectMapper.readValue(objectMapper.writeValueAsString(map),
						DemandStateStatistic.class);
				list.add(readValue);
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public void undertakeDemand(Demand demand) {
		// 
		Integer demandId = demand.getId();
		Demand demandDb = demandMapper.selectByPrimaryKey(demandId);
		demandDb.setUndertakeTime(new Date());
		demandDb.setState(GlobalConstant.DemandState.PROCESSING);
		demandDb.setUndertakeUser(demand.getUndertakeUser());
		demandMapper.updateByPrimaryKey(demandDb);
	}

	@Override
	public void deleteOrderWorker(Integer orderWorkerId) {
		// 
		orderWorkerMapper.deleteByPrimaryKey(orderWorkerId);
	}

	@Override
	public void editOrderWorker(OrderWorker orderWorker) {
		// 
		OrderWorker orderWorkerDb = orderWorkerMapper.selectByPrimaryKey(orderWorker.getId());
		orderWorkerDb.setBusinessIncome(orderWorker.getBusinessIncome());
		orderWorkerDb.setUpdateTime(new Date());
		orderWorkerDb.setUpdateUser(orderWorker.getUpdateUser());
		orderWorkerDb.setArriveWorkTime(orderWorker.getArriveWorkTime());
		orderWorkerDb.setSignSalary(orderWorker.getSignSalary());
		orderWorkerMapper.updateByPrimaryKey(orderWorkerDb);
	}

	@Override
	public void addOrderWorker(Integer demandJobId, List<OrderWorker> workers) {
		// TODO Auto-generated method stub
		
		if(!CollectionUtils.isEmpty(workers)) {
			// 生成订单
			for (OrderWorker orderWorker : workers) {
				orderWorkerMapper.insert(orderWorker);
			}
		}
	}

	@Override
	public void signing(Demand demand) {
		// TODO Auto-generated method stub
		
	}

}
