package com.wei.boot.service;

import java.util.List;

import com.wei.boot.exception.NormalException;
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
	 * 完成签约
	 * @param demand
	 */
	public void signing(Demand demand);
	
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

	/**
	 * 删除签约用工
	 * @param orderWorkerId
	 */
	public void deleteOrderWorker(Integer orderWorkerId);

	/**
	 * 编辑签约用工
	 * @param orderWorker
	 */
	public void editOrderWorker(OrderWorker orderWorker);

	/**
	 * 
	 * @param demandJobId
	 * @param workers
	 */
	public void addOrderWorker(Integer demandJobId, List<OrderWorker> workers);
	
	int queryCountByTime(String type);
	
	int queryAllCount();

	public JobTypeModel queryOrderWorkerList(int signing, Integer demandJobId);
    /**
     * 
     * @param demandJobId
     * @return 
     */
	public Demand waitingSigningOrder(Integer demandJobId);

	public Demand waitingDemand(Integer demandId);

	/**
	 * 编辑
	 * @param demand
	 * @throws NormalException 
	 */
	public void editDemand(Demand demand) throws NormalException;
	
	/**
	 * 关单列表查询
	 * @param demand
	 * @return
	 */
	public Page<Demand> queryByPage4Close(Page<Demand> page, DemandQuery demandQuery);
	
	Demand queryDetail(int demandId);
	
	Demand queryDetailWithOrder(int demandId);
}
