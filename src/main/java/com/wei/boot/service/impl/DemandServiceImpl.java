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
import com.wei.boot.contant.GlobalConstant.OrderWorkerState;
import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.DemandJobMapper;
import com.wei.boot.mapper.DemandMapper;
import com.wei.boot.mapper.DemandOrderMapper;
import com.wei.boot.mapper.JobTypeMapper;
import com.wei.boot.mapper.OrderWorkerMapper;
import com.wei.boot.mapper.UserMapper;
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
import com.wei.boot.model.OrderWorkerExample.Criteria;
import com.wei.boot.model.Page;
import com.wei.boot.model.User;
import com.wei.boot.model.Worker;
import com.wei.boot.model.signing.JobTypeModel;
import com.wei.boot.model.signing.OrderModel;
import com.wei.boot.service.CommonService;
import com.wei.boot.service.CompanyService;
import com.wei.boot.service.DemandService;
import com.wei.boot.service.UserService;
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
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public void saveDemand(Demand demand) {
		
		LOGGER.debug("enter saveDemand,demand={}",demand);
		Date createTime = new Date();

		// 保存需求信息
		demand.setCreateTime(createTime);
		demand.setState(0);
		
		demandMapper.insertSelective(demand);
		int demandId =  demand.getId();
		
        Integer companyId = demand.getCompanyId();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("createTime", DateUtils.getYearStart(new Date()));
		int demandCount =demandMapper.selectCampanyLastDemandNumber(map);
		//DateUtils.getCurYear() + "-00" +companyId + "-00" + (demandCount + 1);
		
		// 生成需求单号
		String demandNumber = DateUtils.getCurDate6Bit() + "" +demandId + "" + (demandCount + 1);
		demand.setDemandNumber(demandNumber);
		demandMapper.updateByPrimaryKeySelective(demand);
		
		// 保存需求用工信息
		List<DemandJob> demandJobList = demand.getDemandJobList();
		if(!CollectionUtils.isEmpty(demandJobList)) {
			demandJobList.stream().forEach(demandJob -> {
				demandJob.setDemandId(demandId);
				demandJob.setCreateTime(createTime);
				demandJob.setCreateUser(demand.getCreateUser());
				// 到岗时间
				demandJobMapper.insertSelective(demandJob);
			});
		}
		LOGGER.debug("exit saveDemand");
	}

	@Override
	public Page<Demand> queryDemand(Page<Demand> page, DemandQuery demandQuery) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 签约中管理员只能选自己的
		if(Objects.nonNull(demandQuery.getUserId()) 
				&& Objects.nonNull(demandQuery.getState()) 
				&& (Objects.equals(GlobalConstant.DemandState.SIGNING, demandQuery.getState()) 
						|| Objects.equals(GlobalConstant.DemandState.PROCESSING, demandQuery.getState())
						|| Objects.equals(GlobalConstant.DemandState.CLOSE, demandQuery.getState()))) {

			User user = userMapper.selectByPrimaryKey(demandQuery.getUserId());
			if (!Objects.equals(GlobalConstant.UserRole.ADMIN, user.getRoleId())) {
				map.put("undertakeUser", demandQuery.getUserId());
			}
		}
		
		if(!StringUtils.isEmpty(demandQuery.getCompanyName())) {
			map.put("companyName","%" + demandQuery.getCompanyName() + "%");
		}
		
		if(!StringUtils.isEmpty(demandQuery.getDemandNumber())) {
			map.put("demandNumber","%" + demandQuery.getDemandNumber() + "%");
		}
		
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
		if (!CollectionUtils.isEmpty(list)) {
			translateDemandList(list);

			for (Demand demand : list) {
				List<DemandJob> jobList = demandJobMapper.selectByDemandId(demand.getId());
				demand.setDemandJobList(jobList);
			}
			
			if (Objects.nonNull(demandQuery.getState()) && demandQuery.getState() >= GlobalConstant.DemandState.SIGNING) {
				for (Demand demand : list) {
					List<DemandOrder> demandOrders = demandOrderMapper.selectByDemandId(demand.getId());
					demand.setDemandOrderList(demandOrders);
				}
			}

		}
		
		page.pageData(list, totalCount);
		return page;
		
	}

	private void translateDemandList(List<Demand> list) {
		Map<Integer, String>  idNameMap = new HashMap<>();
		list.stream().forEach(demand -> {
			// 创建人
			demand.setCreateUserName(getUserName(idNameMap,demand.getCreateUser()));
			// 操作人员
			demand.setUndertakeUserName(getUserName(idNameMap,demand.getUndertakeUser()));
			// 关单人员
			demand.setCloseUserName(getUserName(idNameMap,demand.getCloseUser()));
			// 状态
			demand.setStateName(
					commonService.queryDicText(GlobalConstant.DictionaryType.DEMAND_STATE, demand.getState()));
		});

	}


	private String getUserName(Map<Integer, String> idNameMap, Integer userId) {
		if(!StringUtils.isEmpty(idNameMap.get(userId))) {
			return idNameMap.get(userId);
		}else {
			String userName = commonService.queryUserName(userId);
			idNameMap.put(userId, userName);
			return userName;
		}
	}

	@Override
	public Demand queryDemandById(Integer demandId) {
		
		// 需求单
		Demand demand = demandMapper.selectByPrimaryKey(demandId);
		
		// 翻译需求
		translateDemand(demand);
		
		// 公司
		Company company = companyService.queryById(demand.getCompanyId());
		demand.setCompanyName(company == null ? "":company.getName());
		
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
			if(null != demandJob.getWorkArea()) {
				// 用工地区
				Area area = commonService.queryAreaByCode(demandJob.getWorkArea());
				demandJob.setParentCode(area == null ? 0: area.getParentCode());
				demandJob.setWorkAreaName(area == null ? "": area.getName());
			}
			// 工种名字
			JobType jobType = jobTypeMapper.selectByPrimaryKey(demandJob.getJobTypeId());
			
			demandJob.setJobTypeName(jobType == null ? "": jobType.getName());
			demandJob.setParentJobTypeId(jobType == null ? null: jobType.getParentId());
			
			// 翻译性别要求
			if(null != demandJob.getGender()) {
				String genderName = commonService.queryDicText(GlobalConstant.DictionaryType.GENDER_DEMAND, demandJob.getGender());
				demandJob.setGenderName(genderName);			}
	
			// 翻译学历要求
			if(null != demandJob.getDegree()) {
				String degreeName = commonService.queryDicText(GlobalConstant.DictionaryType.DEGREE_DEMAND, demandJob.getDegree());
				demandJob.setDegreeName(degreeName);
			}
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
		if(Objects.nonNull(demand)) {
			
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
		}
	}

	private int getSigningCountDemandId(Integer demandId) {
		return demandOrderMapper.selectSigningCountByDemandId(demandId);
		
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
		
		if(Objects.nonNull(worker.getCreateUser())) {
			worker.setCreateUserName(commonService.queryUserName(worker.getCreateUser()));
		}
		
		if(Objects.nonNull(orderWorker.getConfirmState())) {
			String confirmStateName = commonService.queryDicText(GlobalConstant.DictionaryType.ORDER_CONFIRM_STATE, orderWorker.getConfirmState());
			orderWorker.setConfirmStateName(confirmStateName);
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
	public void editOrderWorker(OrderWorker orderWorker) throws NormalException {
		// 
		OrderWorker orderWorkerDb = orderWorkerMapper.selectByPrimaryKey(orderWorker.getId());
		DemandOrder demandOrder = null;
		if(Objects.nonNull(orderWorkerDb.getOrderId())) {
			demandOrder = demandOrderMapper.selectByPrimaryKey(orderWorkerDb.getOrderId());
			if(Objects.nonNull(demandOrder) && Objects.equals(GlobalConstant.OrderConfirmState.SUCCESS, demandOrder.getConfirmState())) {
				throw new NormalException("订单已确认不能进行修改！");
			}
		}
		
		String oldBusinessIncome = orderWorkerDb.getBusinessIncome();
		
		orderWorkerDb.setBusinessIncome(orderWorker.getBusinessIncome());
		orderWorkerDb.setUpdateTime(new Date());
		orderWorkerDb.setUpdateUser(orderWorker.getUpdateUser());
		orderWorkerDb.setArriveWorkTime(orderWorker.getArriveWorkTime());
		orderWorkerDb.setSignSalary(orderWorker.getSignSalary());
		orderWorkerDb.setCollectUserIncome(orderWorker.getCollectUserIncome());
		orderWorkerDb.setUndertakeUserIncome(orderWorker.getUndertakeUserIncome());
		orderWorkerMapper.updateByPrimaryKey(orderWorkerDb);
		
		if(Objects.nonNull(demandOrder) && !Objects.equals(oldBusinessIncome, orderWorker.getBusinessIncome())) {
			demandOrder.setTotalIncome((new BigDecimal(demandOrder.getTotalIncome()).add(new BigDecimal(orderWorker.getBusinessIncome())).subtract(new BigDecimal(oldBusinessIncome))).toString());
			demandOrderMapper.updateByPrimaryKeySelective(demandOrder);
		}
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
	public int signing(Demand demand) {
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
		int demandOrderCount = countByDemandId(demandId);
		String orderNo = demandDb.getDemandNumber() + "-" + (demandOrderCount + 1);
		demandOrder.setOrderNumber(orderNo);
		
		int workerCount = countWorkerCountBydemandJobIds(demandJobIds);
		demandOrder.setWorkerCount(workerCount);
		demandOrder.setOperatorUser(demand.getUndertakeUser());
		
		// 计算收入总额
		BigDecimal totalIncome = orderWorkerMapper.selectWaitingSignIncomeByDemandJobIds(demandJobIds);
		demandOrder.setTotalIncome(totalIncome.toString());
		demandOrder.setCreateTime(new Date());
		demandOrder.setCreateUser(demand.getUndertakeUser());
		// 修改用工的orderId
		demandOrderMapper.insertSelective(demandOrder);
		int demandOrderId = demandOrder.getId();
		
		Map<String, Object> map = new HashMap<>();
		map.put("demandJobIds", demandJobIds);
		map.put("orderId", demandOrderId);
		map.put("updateUser", demand.getUndertakeUser());
		map.put("updateTime", new Date());
		orderWorkerMapper.updateOrderIdByDemandJobIds(map);
		return demandOrderCount;
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

	@Override
	public JobTypeModel queryOrderWorkerList(int state, Integer demandJobId) {
		JobTypeModel jobTypeModel = new JobTypeModel();
		
		OrderWorkerExample example = new OrderWorkerExample();
		Criteria criteria = example.createCriteria().andDemandJobIdEqualTo(demandJobId);
		if(Objects.equals(OrderWorkerState.ASSIGN, state)) {
			criteria.andOrderIdIsNull();
		}
		if(Objects.equals(OrderWorkerState.SIGNING, state)) {
			criteria.andOrderIdIsNotNull();
		}
		List<OrderWorker> orderWorkerList = orderWorkerMapper.selectByExample(example);
		
		if(!CollectionUtils.isEmpty(orderWorkerList)) {
			// 翻译
			for (OrderWorker orderWorker : orderWorkerList) {
				translateOrderWorker(orderWorker);
			}
			
			// 订单状态
		}
		
		DemandJob demandJob = queryDemandJobById(demandJobId);
		jobTypeModel.setDemandJob(demandJob);
		jobTypeModel.setOrderWorkerList(orderWorkerList);
		
		return jobTypeModel;
	}

	@Override
	public Demand waitingSigningOrder(Integer demandId) {
	
		// 客户名称:
		Demand demandDb = demandMapper.selectByPrimaryKey(demandId);
		Company company = companyService.queryById(demandDb.getCompanyId());
		demandDb.setCompanyName(company == null ? "":company.getName());
		// 各工种人数和 要签约人数  签约总额
		List<DemandJob> demandJobs = queryDemandJobByDemandId(demandId);
		
		List<DemandJob> waitingSigningdemandJobs = new ArrayList<>();
		if(!CollectionUtils.isEmpty(demandJobs)) {
			for (DemandJob demandJob : demandJobs) {
				int assignCount = getAssignCountByDemandJobId(demandJob.getId());
				if(assignCount > 0) {
					waitingSigningdemandJobs.add(demandJob);
					demandJob.setAssignCount(assignCount);
					translateDemandJob(null, demandJob);
					BigDecimal incomeBd = orderWorkerMapper.selectWaitingSignIncomeByDemandJobId(demandJob.getId());
					if(Objects.nonNull(incomeBd)) {
						demandJob.setIncome(incomeBd.toString());
					}
				}
			}
		}
		
		demandDb.setDemandJobList(waitingSigningdemandJobs);
		return demandDb;
	}

	@Override
	public Demand waitingDemand(Integer demandId) {
		
		// 需求单
		Demand demand = demandMapper.selectByPrimaryKey(demandId);
		// 公司
		Company company = companyService.queryById(demand.getCompanyId());
		demand.setCompanyName(company == null ? "":company.getName());
		List<DemandJob> demandJobList = demandJobMapper.selectByDemandId(demand.getId());
		demand.setDemandJobList(demandJobList);
		
		return demand;
	
	}

	@Override
	@Transactional
	public void editDemand(Demand demand) throws NormalException {
		Demand demandDb = demandMapper.selectByPrimaryKey(demand.getId());

		if (GlobalConstant.DemandState.PENDING < demandDb.getState()) {
			throw new NormalException("需求单已接单不能进行修改！");
		}
		Date date = new Date();
		demandDb.setUpdateTime(date);
		demandDb.setUpdateUser(demand.getUpdateUser());
		demandDb.setDescription(demand.getDescription());
		demandDb.setCompanyId(demand.getCompanyId());

		demandMapper.updateByPrimaryKeySelective(demandDb);

		List<DemandJob> demandJobList = queryDemandJobByDemandId(demand.getId());
		if (!CollectionUtils.isEmpty(demandJobList)) {

			List<Integer> demandJobIds = new ArrayList<>();

			demand.getDemandJobList().stream().forEach(demandJob -> {
				demandJobIds.add(demandJob.getId());
			});
			
			// 删除
			demandJobList.stream().forEach(dj -> {
				if (!demandJobIds.contains(dj.getId())) {
					demandJobMapper.deleteByPrimaryKey(dj.getId());
				}
			});
			
			Map<Integer, DemandJob>  demandJobMap = new HashMap<>();
 			

			demand.getDemandJobList().stream().forEach(demandJob -> {
				if (demandJob.getId() == null) {
					// 新增
					demandJob.setDemandId(demand.getId());
					demandJob.setCreateTime(date);
					demandJob.setCreateUser(demand.getUpdateUser());
					demandJobMapper.insertSelective(demandJob);
				}  else {  // 修改
					demandJobMap.put(demandJob.getId(), demandJob);
				}
			});
			
			// 修改
			demandJobList.stream().forEach(djDb -> {
				if (demandJobMap.get(djDb.getId()) != null) {
					DemandJob editDj = demandJobMap.get(djDb.getId());
					djDb.setUpdateTime(date);
					djDb.setUpdateUser(demand.getUpdateUser());
					djDb.setJobTypeId(editDj.getJobTypeId());
					djDb.setWorkerCount(editDj.getWorkerCount());
					djDb.setRequireTime(editDj.getRequireTime());
					djDb.setSalary(editDj.getSalary());
					djDb.setWorkArea(editDj.getWorkArea());
					djDb.setGender(editDj.getGender());
					djDb.setDegree(editDj.getDegree());
					djDb.setAge(editDj.getAge());
					djDb.setRequirement(editDj.getRequirement());
					demandJobMapper.updateByPrimaryKey(djDb);
				}
			});
		}
	}

	@Override
	public Page<Demand> queryByPage4Close(Page<Demand> page, DemandQuery demandQuery) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", page.getOffset());
		map.put("pageSize", page.getPageSize());
		if(null != demandQuery.getState()) {
			map.put("state", demandQuery.getState());
		}
		if(!StringUtils.isEmpty(demandQuery.getBeginTime())) {
			map.put("beginTime", DateUtils.parseDate(demandQuery.getBeginTime()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(!StringUtils.isEmpty(demandQuery.getEndTime())) {
			map.put("endTime", DateUtils.parseDate(demandQuery.getEndTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if(!StringUtils.isEmpty(demandQuery.getCompanyName())) {
			map.put("companyName", "%" + demandQuery.getCompanyName() + "%");
		}
		if(!StringUtils.isEmpty(demandQuery.getDemandNumber())) {
			map.put("demandNumber", "%" + demandQuery.getDemandNumber() + "%");
		}
		if(!StringUtils.isEmpty(demandQuery.getUndertakeUserName())) {
			map.put("undertakeUserName", "%" + demandQuery.getUndertakeUserName() + "%");
		}
		if(!StringUtils.isEmpty(demandQuery.getCloseUserName())) {
			map.put("closeUserName", "%" + demandQuery.getCloseUserName() + "%");
		}
		List<Demand> list = demandMapper.selectByPage4Close(map);
		if(!CollectionUtils.isEmpty(list)) {
			for(Demand demand : list) {
				List<DemandJob> jobList = demandJobMapper.selectByDemandId(demand.getId());
				demand.setDemandJobList(jobList);
			}
		}
		int totalCount = demandMapper.selectCount4Close(map);
		page.pageData(list, totalCount);
		return page;
	}

	@Override
	public Demand queryDetail(int demandId) {
		Demand demand = demandMapper.selectDetail(demandId);
		if(null != demand) {
			List<DemandJob> jobList = demandJobMapper.selectByDemandId(demand.getId());
			demand.setDemandJobList(jobList);
			// 计算总要求人数
			int workerCount = 0;
			if(null != jobList && jobList.size() > 0) {
				for(DemandJob job : jobList) {
					workerCount += job.getWorkerCount();
				}
			}
			demand.setWorkCount(workerCount);
		}
		return demand;
	}

	@Override
	public Demand queryDetailWithOrder(int demandId) {
		Demand demand = demandMapper.selectDetail(demandId);
		if(null != demand) {
			List<DemandJob> jobList = demandJobMapper.selectByDemandId(demand.getId());
			// 计算总要求人数
			int workerCount = 0;
			if(null != jobList && jobList.size() > 0) {
				for(DemandJob job : jobList) {
					workerCount += job.getWorkerCount();
				}
			}
			demand.setWorkCount(workerCount);
			demand.setDemandJobList(jobList);
			List<DemandOrder> orderList = demandOrderMapper.selectByDemandId(demandId);
			// 计算总签订人数
			int signCount = 0;
			BigDecimal totalIncome = new BigDecimal(0);
			if(null != orderList && orderList.size() > 0) {
				for(DemandOrder order : orderList) {
					signCount += order.getWorkerCount();
					totalIncome = totalIncome.add(new BigDecimal(order.getTotalIncome()));
					// 翻译确认人
					if(null != order.getConfirmUser()) {
						User user = userService.queryById(order.getConfirmUser());
						order.setConfirmUserName(user.getRealName());
					}
				}
			}
			demand.setSigningCount(signCount);
			demand.setTotalIncome(totalIncome.toString());
			demand.setDemandOrderList(orderList);
		}
		return demand;
	}

	@Override
	public OrderModel demandAssignList(Integer demandId) {
		
		OrderModel orderModel = new OrderModel();

		List<DemandJob> demandJobs = queryDemandJobByDemandId(demandId);
		List<Integer> jobIds = new ArrayList<>();
		if (!CollectionUtils.isEmpty(demandJobs)) {
			for (DemandJob demandJob : demandJobs) {
				jobIds.add(demandJob.getId());
			}
		}

		OrderWorkerExample example = new OrderWorkerExample();
		Criteria criteria = example.createCriteria().andDemandJobIdIn(jobIds);
		criteria.andOrderIdIsNull();
		List<OrderWorker> orderWorkerList = orderWorkerMapper.selectByExample(example);

		if (!CollectionUtils.isEmpty(orderWorkerList)) {
			for (OrderWorker orderWorker : orderWorkerList) {
				Worker worker = workerMapper.selectByPrimaryKey(orderWorker.getWorkerId());
				worker.setCreateUserName(commonService.queryUserName(worker.getCreateUser()));
				orderWorker.setWorker(worker);
			}
			
			orderModel.setWorkerCount(orderWorkerList.size());
		}

		// 客户名称:
		Demand demandDb = demandMapper.selectByPrimaryKey(demandId);
		Company company = companyService.queryById(demandDb.getCompanyId());
		demandDb.setCompanyName(company == null ? "" : company.getName());
		
		// 本次签约人数，和签约金额
		BigDecimal incomeBd = orderWorkerMapper.selectWaitingSignIncomeByDemandJobIds(jobIds);
		
		if(Objects.nonNull(incomeBd)) {
			orderModel.setIncome(incomeBd.toString());
		}

		orderModel.setOrderWorkerList(orderWorkerList);
		orderModel.setDemand(demandDb);

		return orderModel;
	}

	@Override
	public boolean hasUnConfirmedDemand(Integer demandId) {
		List<Demand> list = demandMapper.selectUnConfirmedDemand(demandId);
		if(null != list && !list.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	
}
