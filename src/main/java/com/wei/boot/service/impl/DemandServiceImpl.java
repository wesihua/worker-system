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
import com.wei.boot.mapper.DemandJobMapper;
import com.wei.boot.mapper.DemandMapper;
import com.wei.boot.mapper.JobTypeMapper;
import com.wei.boot.mapper.OrderWorkerMapper;
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
import com.wei.boot.service.CommonService;
import com.wei.boot.service.CompanyService;
import com.wei.boot.service.DemandService;
import com.wei.boot.util.CheckUtils;
import com.wei.boot.util.DateUtils;
import com.wei.boot.util.ToolsUtil;

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

	@Override
	@Transactional
	public void saveDemand(Demand demand) {
		
		LOGGER.debug("enter saveDemand,demand={}",demand);
		Date createTime = new Date();

		// 保存需求信息
		demand.setCreateTime(createTime);
		demand.setState(0);
		// TODO 生成需求单号
		int demandId = demandMapper.insertSelective(demand);
		
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

	@Override
	public Page<Demand> queryDemand(Page<Demand> page, DemandQuery demandQuery) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(Objects.nonNull(demandQuery.getCompanyId())) {
			map.put("companyId", demandQuery.getCompanyId());
		}
		if(Objects.nonNull(demandQuery.getCreateBeginTime())) {
			map.put("createBeginTime", demandQuery.getCreateBeginTime());
		}
		if(Objects.nonNull(demandQuery.getCreateEndTime())) {
			map.put("createEndTime", demandQuery.getCreateEndTime());
		}
		if(Objects.nonNull(demandQuery.getState())) {
			map.put("state", demandQuery.getState());
		}
		int totalCount = demandMapper.selectCount(map);
		
		map.put("pageSize", page.getPageSize());
		map.put("offset", page.getOffset());
		List<Demand> list = demandMapper.selectByPage(map);
		page.pageData(list, totalCount);
		return page;
		
	}

	@Override
	public Demand queryDemandById(Integer demandId) {
		
		// 需求单
		Demand demand = demandMapper.selectByPrimaryKey(demandId);
		
		// 翻译需求
		translateDemand(demand);
		
		DemandJobExample example  = new DemandJobExample();
		example.createCriteria().andDemandIdEqualTo(demandId);
		// 需求单工种
		List<DemandJob> demandJobList = demandJobMapper.selectByExample(example );
		if(!CollectionUtils.isEmpty(demandJobList)) {
			demandJobList.stream().forEach(demandJob->{
				translateDemandJob(demand,demandJob);
			});
		}
		demand.setDemandJobList(demandJobList);
		
		return demand;
	}
	
	private void translateDemandJob(Demand demand, DemandJob demandJob) {
		if(null != demandJob) {
			// 用工地区
			Area area = commonService.queryAreaByCode(demandJob.getWorkArea());
			
			demandJob.setWorkAreaName(area == null ? "": area.getName());
			// 工种名字
			JobType jobType = jobTypeMapper.selectByPrimaryKey(demandJob.getWorkArea());
			demandJob.setJobTypeName(jobType == null ? "": jobType.getName());
			
			// TODO 修改签约人数
		}
		
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
			
		}
	}

	@Override
	public Page<OrderWorker> queryOrderWorker(Page<OrderWorker> page, Integer demandJobId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(Objects.nonNull(demandJobId)) {
			map.put("demandJobId", demandJobId);
		}
		int totalCount = orderWorkerMapper.selectCount(map);
		
		map.put("pageSize", page.getPageSize());
		map.put("offset", page.getOffset());
		List<OrderWorker> list = orderWorkerMapper.selectByPage(map);
		page.pageData(list, totalCount);
		return page;
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
		// TODO Auto-generated method stub
		Integer demandId = demand.getId();
		Demand demandDb = demandMapper.selectByPrimaryKey(demandId);
		demandDb.setUndertakeTime(new Date());
		demandDb.setState(GlobalConstant.DemandState.PROCESSING);
		demandDb.setUndertakeUser(demand.getUndertakeUser());
		demandMapper.updateByPrimaryKey(demandDb);
	}
	
	
	



}
