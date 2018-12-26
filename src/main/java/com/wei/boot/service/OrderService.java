package com.wei.boot.service;

import java.util.List;

import com.wei.boot.model.DemandOrder;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.Page;

/**
 * 订单service
 * @author weisihua
 * 2018年10月23日 下午2:34:48
 */
public interface OrderService {

	/**
	 * 分页查询订单
	 * @param companyName
	 * @param orderNo
	 * @param createUser
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	Page<DemandOrder> queryByPage(DemandOrder info, Page<DemandOrder> page);
	
	/**
	 * 查询订单签订人员详情
	 * @param orderId
	 * @return
	 */
	List<OrderWorker> queryOrderWorkerDetail(int orderId);
	
	/**
	 * 分页查询订单-用于确认
	 * @param info
	 * @param page
	 * @return
	 */
	Page<DemandOrder> queryByPage4Confirm(DemandOrder info, Page<DemandOrder> page);
	
	/**
	 * 确认订单
	 * @param orderId
	 */
	void confirmOrder(int orderId, int userId);
	
	/**
	 * 驳回订单
	 * @param orderId
	 * @param rejectReason
	 */
	void rejectOrder(int orderId, String rejectReason, int userId);
}
