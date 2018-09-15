package com.wei.boot.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wei.boot.mapper.DemandMapper;
import com.wei.boot.model.Demand;
import com.wei.boot.service.DemandService;

@Service
public class DemandServiceImpl implements DemandService {
	@Autowired
	private DemandMapper demandMapper;

	@Override
	public void saveDemand(Demand demand) {
		// TODO Auto-generated method stub
		// 新增
		if(null == demand.getId() || demand.getId() == 0) {
			demand.setCreateTime(new Date());
			demandMapper.insertSelective(demand);
		} else {  // 编辑
			demand.setUpdateTime(new Date());
			demandMapper.updateByPrimaryKeySelective(demand);
		}
	}


}
