package com.wei.boot.controller.pc;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Company;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.report.ReportInfo;
import com.wei.boot.service.ReportService;
import com.wei.boot.util.DateUtils;

@RestController
@RequestMapping("/report")
public class ReportController {

	public static final Logger log = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * 人才柱状图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@GetMapping("/workerBar")
	public Result workerBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryWorkerMonthBar(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 来源饼图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@GetMapping("/workerSourcePie")
	public Result workerSourcePie(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryWorkerSourcePie(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 简历饼图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@GetMapping("/workerDegreePie")
	public Result workerDegreePie(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryWorkerDegreePie(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 录入人饼图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@GetMapping("/workerCreateUserPie")
	public Result workerCreateUserPie(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryWorkerCreateUserPie(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	@GetMapping("/demandBar")
	public Result demandBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryDemandMonthBar(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	private String getDefaultBeginDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -6);
		return DateUtils.formatDate(cal.getTime(), "yyyy-MM-dd");
	}
}
