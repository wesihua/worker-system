package com.wei.boot.service;

import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandQuery;
import com.wei.boot.model.Page;

/**
 * 用工需求接口
 * @author xsy_2018
 *
 */
public interface DemandService {

	/**
	 * 新增
	 * @param demand
	 */
	public void saveDemand(Demand demand);

	/**
	 * 分页查询
	 * @param page
	 * @param demandQuery
	 * @return
	 */
	public Page<Demand> queryDemand(Page<Demand> page, DemandQuery demandQuery);
	
	
}
