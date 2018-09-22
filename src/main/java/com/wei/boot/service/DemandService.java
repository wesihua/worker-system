package com.wei.boot.service;

import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandQuery;
import com.wei.boot.model.OrderWorker;
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
    
	/**
	 * 根据id查订单详情
	 * @param demandId
	 * @return
	 */
	public Demand queryDemandById(Integer demandId);

	/**
	 * 需求单工种签约列表
	 * @param page
	 * @param demandJobId
	 * @return
	 */
	public Page<OrderWorker> queryOrderWorker(Page<OrderWorker> page, Integer demandJobId);
	
	public void closeDemand(Demand demand);
}
