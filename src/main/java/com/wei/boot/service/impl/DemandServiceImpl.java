package com.wei.boot.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.wei.boot.mapper.DemandJobMapper;
import com.wei.boot.mapper.DemandMapper;
import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandJob;
import com.wei.boot.model.DemandQuery;
import com.wei.boot.model.Page;
import com.wei.boot.service.DemandService;

@Service
public class DemandServiceImpl implements DemandService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DemandServiceImpl.class);
	
	@Autowired
	private DemandMapper demandMapper;
	
	
	@Autowired
	private DemandJobMapper demandJobMapper;

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
				//demandJob.setCreateUser(createUser);
				// 到岗时间
				demandJob.setRequireTime(createTime);
				demandJobMapper.insertSelective(demandJob);
			});
		}
		
		LOGGER.debug("exit saveDemand");

	}

	@Override
	public Page<Demand> queryDemand(Page<Demand> page, DemandQuery demandQuery) {
		// TODO Auto-generated method stub
		return null;
	}


}
