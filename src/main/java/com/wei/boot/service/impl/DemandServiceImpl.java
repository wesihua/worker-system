package com.wei.boot.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.wei.boot.mapper.DemandOrderMapper;
import com.wei.boot.mapper.JobTypeMapper;
import com.wei.boot.mapper.OrderWorkerMapper;
import com.wei.boot.mapper.WorkerMapper;
import com.wei.boot.model.Area;
import com.wei.boot.model.Company;
import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandJob;
import com.wei.boot.model.DemandJobExample;
import com.wei.boot.model.DemandOrder;
import com.wei.boot.model.DemandOrderExample;
import com.wei.boot.model.DemandQuery;
import com.wei.boot.model.DemandStateStatistic;
import com.wei.boot.model.JobType;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.OrderWorkerExample;
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
	
	@Autowired
	private DemandOrderMapper demandOrderMapper;

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
		if (!CollectionUtils.isEmpty(demandJobList)) {
			demandJobList.stream().forEach(demandJob -> {
				translateDemandJob(demand, demandJob);

				// 计算已分派人数 assignCount
				int assignCount = getAssignCountByDemandJobId(demandJob.getId());
				demandJob.setAssignCount(assignCount);
				
				// 计算已签约人数 signingCount
				int signingCount = getSigningCountDemandJobId(demandJob.getId());
				demandJob.setSigningCount(signingCount);
			});
		}
		demand.setDemandJobList(demandJobList);
		
		return demand;
	}

	

	private int getSigningCountDemandJobId(Integer id) {
		OrderWorkerExample example = new OrderWorkerExample();
		example.createCriteria().andOrderIdIsNotNull().andDemandJobIdEqualTo(id);

		return orderWorkerMapper.countByExample(example);
	}

	private int getAssignCountByDemandJobId(Integer id) {
		OrderWorkerExample example = new OrderWorkerExample();
		example.createCriteria().andOrderIdIsNull().andDemandJobIdEqualTo(id);

		return orderWorkerMapper.countByExample(example);
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
			
			if(Objects.equals(GlobalConstant.DemandState.SIGNING, demand.getState())
					|| Objects.equals(GlobalConstant.DemandState.CLOSE, demand.getState())) {
				int signingCount = getSigningCountDemandId(demand.getId());
				//   已签人数
				demand.setSigningCount(signingCount );
				//   收入总额
				BigDecimal income = demandOrderMapper.selectIncomeByDemandId(demand.getId());
				if(Objects.nonNull(income)) {
					demand.setTotalIncome(income.toString());
				}
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

	private int getSigningCountDemandId(Integer demandId) {
		
		return demandOrderMapper.selectSigningCountByDemandId(demandId );
		
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
		
		if(!CollectionUtils.isEmpty(workers)) {
			// 生成订单
			for (OrderWorker orderWorker : workers) {
				orderWorkerMapper.insert(orderWorker);
			}
		}
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
			return demandMapper.selectCountByTime(map);
		}
		return 0;
	}

	@Override
	public int queryAllCount() {
		return demandMapper.selectAllCount();
	}
	
	@Transactional
	public void signing(Demand demand) {
		// 需求单状态修改
		Integer demandId = demand.getId();
		Demand demandDb = demandMapper.selectByPrimaryKey(demandId);
		demandDb.setState(GlobalConstant.DemandState.SIGNING);
		demandMapper.updateByPrimaryKey(demandDb);
		
		List<DemandJob> demandJobs = queryDemandJobByDemandId(demandId);
		
		List<Integer> demandJobIds = new ArrayList<>();
        for (DemandJob demandJob : demandJobs) {
        	demandJobIds.add(demandJob.getId());
		}

		
		// 生成订单
		DemandOrder demandOrder = new DemandOrder();
		demandOrder.setDemandId(demandId);
		// 订单号   = 需求号 + 序列号
		int countByDemandId = countByDemandId(demandId);
		String orderNo = demandDb.getDemandNumber() + "-0" + (countByDemandId+ 1);
		demandOrder.setOrderNumber(orderNo);
		
		int workerCount = countWorkerCountBydemandJobIds(demandJobIds);
		demandOrder.setWorkerCount(workerCount);
		demandOrder.setOperatorUser(demand.getUndertakeUser());
		
		// 计算收入总额
		BigDecimal totalIncome = orderWorkerMapper.selectIncomeByDemandJobIds(demandJobIds);
		demandOrder.setTotalIncome(totalIncome.toString());
		demandOrder.setCreateTime(new Date());
		demandOrder.setCreateUser(demand.getUndertakeUser());
		// 修改用工的orderId
		int demandOrderId = demandOrderMapper.insertSelective(demandOrder);
		Map<String, Object> map = new HashMap<>();
		map.put("demandJobIds", demandJobIds);
		map.put("orderId", demandOrderId);
		map.put("updateUser", demand.getUndertakeUser());
		map.put("updateTime", new Date());
		orderWorkerMapper.updateOrderIdByDemandJobIds(map);
		
	}

	private int countWorkerCountBydemandJobIds(List<Integer> demandJobIds) {
		OrderWorkerExample example = new OrderWorkerExample();
		example.createCriteria().andDemandJobIdIn(demandJobIds).andOrderIdIsNull();
		return orderWorkerMapper.countByExample(example );
	}

	public int countByDemandId(Integer demandId) {
		DemandOrderExample example = new DemandOrderExample();
		example.createCriteria().andDemandIdEqualTo(demandId);
		return demandOrderMapper.countByExample(example );
	}

}
