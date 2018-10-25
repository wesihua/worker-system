package com.wei.boot.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wei.boot.model.report.ReportInfo;

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
	
	/**
	 * 按时间查询收入
	 * @param flag
	 * @return
	 */
	BigDecimal queryIncomeByTime(String flag);
	
	/**
	 * 查询所有收入
	 * @return
	 */
	BigDecimal queryAllIncome();
	
	/**
	 * 按时间查询签订的工人人数
	 * @param flag
	 * @return
	 */
	int queryOrderWorkerCountByTime(String flag);
	
	/**
	 * 查询所有签订工人人数
	 * @return
	 */
	int queryAllOrderWorkerCount();
	
	/**
	 * 查询人才来源饼图
	 * @return
	 */
	List<ReportInfo> queryWorkerSourcePie(String beginDate, String endDate);
	
	/**
	 * 查询人才录入人饼图
	 * @return
	 */
	List<ReportInfo> queryWorkerCreateUserPie(String beginDate, String endDate);
	
	/**
	 * 查询人才学历饼图
	 * @return
	 */
	List<ReportInfo> queryWorkerDegreePie(String beginDate, String endDate);
	
	/**
	 * 查询月份录入人才数量按月柱状图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryWorkerMonthBar(String beginDate, String endDate);
	
	/**
	 * 人才数量按日柱状图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryWorkerDayBar(String beginDate, String endDate);
	
	/**
	 * 查询企业需求按月柱状图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryDemandMonthBar(String beginDate, String endDate);
	
	/**
	 * 企业需求接单人饼图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryDemandUnderTakerPie(String beginDate, String endDate);
	
	/**
	 * 企业需求状态饼图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryDemandStatePie(String beginDate, String endDate);
	
	/**
	 * 已签订订单按月数量柱状图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryOrderMonthBar(String beginDate, String endDate);
	
	/**
	 * 已签单接单人饼图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryOrderUndertakerPie(String beginDate, String endDate);
	
	/**
	 * 已签订订单按月数量柱状图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryOrderMemberMonthBar(String beginDate, String endDate);
	
	/**
	 * 已签单接单人饼图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryOrderMemberUndertakerPie(String beginDate, String endDate);
	
	/**
	 * 已签订订单按月数量柱状图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryOrderIncomeMonthBar(String beginDate, String endDate);
	
	/**
	 * 已签单接单人饼图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportInfo> queryOrderIncomeUndertakerPie(String beginDate, String endDate);
	
	/**
	 * 查询数量
	 * @return
	 */
	List<Map<String, Object>> querySomeCount();
	
}
