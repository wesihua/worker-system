package com.wei.boot.controller.pc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.DemandOrder;
import com.wei.boot.model.Result;
import com.wei.boot.model.excel.ExcelData;
import com.wei.boot.model.excel.ExcelRow;
import com.wei.boot.model.report.CompanyReportInfo;
import com.wei.boot.model.report.ReportInfo;
import com.wei.boot.service.ReportService;
import com.wei.boot.service.WorkerService;
import com.wei.boot.util.DateUtils;
import com.wei.boot.util.ExcelUtil;

@RestController
@RequestMapping("/report")
public class ReportController {

	public static final Logger log = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private WorkerService workerService;
	
	@GetMapping("/statistic")
	public Result statistic() {
		Result result = Result.SUCCESS;
		try {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			// 人才信息数量
			data = reportService.querySomeCount();
			result.setData(data);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 人才柱状图
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@GetMapping("/workerBar")
	public Result workerBar(String beginDate, String endDate, String type) {
		Result result = Result.SUCCESS;
		try {
			List<ReportInfo> list = new ArrayList<ReportInfo>();
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			if(!StringUtils.isEmpty(type) && "month".equals(type)) {
				list = reportService.queryWorkerMonthBar(beginDate, endDate);
			}
			else {
				list = reportService.queryWorkerDayBar(beginDate, endDate);
			}
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
	
	@GetMapping("/orderBar")
	public Result orderBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			Map<String, List<ReportInfo>> map = new HashMap<String, List<ReportInfo>>();
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> demandList = reportService.queryDemandMonthBar(beginDate, endDate);
			map.put("demandBar", demandList);
			List<ReportInfo> orderList = reportService.queryOrderMonthBar(beginDate, endDate);
			map.put("orderBar", orderList);
			List<ReportInfo> orderMemberList = reportService.queryOrderMemberMonthBar(beginDate, endDate);
			map.put("orderMemberBar", orderMemberList);
			List<ReportInfo> orderIncomeList = reportService.queryOrderIncomeMonthBar(beginDate, endDate);
			map.put("orderIncomeBar", orderIncomeList);
			result.setData(map);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	@GetMapping("/orderPie")
	public Result orderPie(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			Map<String, List<ReportInfo>> map = new HashMap<String, List<ReportInfo>>();
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> demandTakerList = reportService.queryDemandUnderTakerPie(beginDate, endDate);
			map.put("demandTakerPie", demandTakerList);
			//List<ReportInfo> demandStateList = reportService.queryDemandStatePie(beginDate, endDate);
			//map.put("demandStatePie", demandStateList);
			List<ReportInfo> orderTakerList = reportService.queryOrderUndertakerPie(beginDate, endDate);
			map.put("orderTakerPie", orderTakerList);
			List<ReportInfo> orderMemberTakerList = reportService.queryOrderMemberUndertakerPie(beginDate, endDate);
			map.put("orderMemberTakerPie", orderMemberTakerList);
			List<ReportInfo> orderIncomeTakerList = reportService.queryOrderIncomeUndertakerPie(beginDate, endDate);
			map.put("orderIncomeTakerPie", orderIncomeTakerList);
			result.setData(map);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	@GetMapping("/demandMonthBar")
	public Result demandMonthBar(String beginDate, String endDate) {
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
	@GetMapping("/demandDayBar")
	public Result demandDayBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryDemandDayBar(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	@GetMapping("/orderMonthBar")
	public Result orderMonthBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryOrderMonthBar(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	@GetMapping("/orderDayBar")
	public Result orderDayBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryOrderDayBar(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	@GetMapping("/orderMemberMonthBar")
	public Result orderMemberMonthBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryOrderMemberMonthBar(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	@GetMapping("/orderMemberDayBar")
	public Result orderMemberDayBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryOrderMemberDayBar(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	@GetMapping("/orderIncomeMonthBar")
	public Result orderIncomeMonthBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryOrderIncomeMonthBar(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	@GetMapping("/orderIncomeDayBar")
	public Result orderIncomeDayBar(String beginDate, String endDate) {
		Result result = Result.SUCCESS;
		try {
			if(StringUtils.isEmpty(beginDate)) {
				beginDate = getDefaultBeginDate();
			}
			List<ReportInfo> list = reportService.queryOrderIncomeDayBar(beginDate, endDate);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	@GetMapping("/companyReport")
	public Result companyReport(String beginDate, String endDate, String companyName) {
		Result result = Result.SUCCESS;
		try {
			List<CompanyReportInfo> list = reportService.queryCompanyOrderReport(beginDate, endDate, companyName);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	@GetMapping("/companyReport/export")
	public void companyReportExport(String beginDate, String endDate, String companyName, HttpServletResponse response) {
		try {
			List<CompanyReportInfo> list = reportService.queryCompanyOrderReport(beginDate, endDate, companyName);
			if(null != list && list.size() > 0) {
				ExcelRow headers = ExcelUtil.excelHeaders("企业名称","订单数量","已签订人数","已签订金额");
				ExcelData data = new ExcelData();
				for(CompanyReportInfo info : list) {
					ExcelRow row = new ExcelRow();
					row.add(info.getCompanyName());
					row.add(info.getOrderCount());
					row.add(info.getOrderMemberCount());
					row.add(info.getOrderIncome());
					data.add(row);
				}
				ExcelUtil.exportExcel(headers, data, "订单信息.xls", response);
			}
		} catch (Exception e) {
			log.error("导出失败", e);
		}
	}
	
	private String getDefaultBeginDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return DateUtils.formatDate(cal.getTime(), "yyyy-MM-dd");
	}
}
