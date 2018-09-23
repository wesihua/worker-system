package com.wei.boot.service;

import java.util.List;
import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandQuery;
import com.wei.boot.model.DemandStateStatistic;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.Page;
import com.wei.boot.model.signing.JobTypeModel;

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
	public JobTypeModel queryOrderWorker(Page<OrderWorker> page, Integer demandJobId);
	
	
	
	/**
	 * 关单
	 * @param demand
	 */
	public void closeDemand(Demand demand);
	
	/**
	 * 分组统计
	 * @return
	 */
	public List<DemandStateStatistic> statisticsByState();

	/**
	 * 接单
	 * @param demand
	 */
	public void undertakeDemand(Demand demand);
}
