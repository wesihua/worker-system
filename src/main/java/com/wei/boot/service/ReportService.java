package com.wei.boot.service;

import java.math.BigDecimal;

/**
 * 报表service
 * @author weisihua
 * 2018年10月21日 下午12:49:53
 */
public interface ReportService {

	/**
	 * 查询订单数量
	 * @param type
	 * @return
	 */
	int queryOrderCountByTime(String type);
	
	/**
	 * 查询所有订单数量
	 * @return
	 */
	int queryAllOrderCount();
	
	BigDecimal queryIncomeByTime(String flag);
	
	BigDecimal queryAllIncome();
	
	int queryOrderWorkerCountByTime(String flag);
	
	int queryAllOrderWorkerCount();
}
