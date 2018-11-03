package com.wei.boot.controller.pc;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Company;
import com.wei.boot.model.DemandOrder;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.excel.ExcelData;
import com.wei.boot.model.excel.ExcelRow;
import com.wei.boot.service.OrderService;
import com.wei.boot.util.ExcelUtil;

@RestController
@RequestMapping("/order")
public class OrderController {

	public static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 列表
	 * @param page
	 * @param worker
	 * @return
	 */
	@GetMapping("/list")
	public Result list(Page<DemandOrder> page, DemandOrder order) {
		Result result = Result.SUCCESS;
		try {
			Page<DemandOrder> data = orderService.queryByPage(order, page);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 导出订单
	 * @param response
	 * @param order
	 */
	@GetMapping("/export")
	public void export(HttpServletResponse response, DemandOrder order) {
		try {
			Page<DemandOrder> page = new Page<DemandOrder>();
			page.setPageSize(20000);
			List<DemandOrder> list = orderService.queryByPage(order, page).getData();
			if(null != list && list.size() > 0) {
				ExcelRow headers = ExcelUtil.excelHeaders("企业名称","订单编号","总签订人数","总签订金额","客户负责人","创建人","备注","创建时间");
				ExcelData data = new ExcelData();
				for(DemandOrder info : list) {
					ExcelRow row = new ExcelRow();
					row.add(info.getCompanyName());
					row.add(info.getOrderNumber());
					row.add(info.getWorkerCount());
					row.add(info.getTotalIncome());
					row.add(info.getCustomer());
					row.add(info.getCreateUserName());
					row.add(info.getDescription());
					row.add(info.getCreateTime());
					data.add(row);
				}
				ExcelUtil.exportExcel(headers, data, "订单信息.xls", response);
			}
		} catch (Exception e) {
			log.error("导出失败", e);
		}
	}
	
	/**
	 * 查询订单下所有签订的人员信息
	 * @param orderId
	 * @return
	 */
	@GetMapping("/queryByOrderId")
	public Result queryByOrderId(int orderId) {
		Result result = Result.SUCCESS;
		try {
			List<OrderWorker> data = orderService.queryOrderWorkerDetail(orderId);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	@GetMapping("/export/workerList")
	public void exportWorkerList(HttpServletResponse response, int orderId) {
		try {
			List<OrderWorker> list = orderService.queryOrderWorkerDetail(orderId);
			if(null != list && list.size() > 0) {
				ExcelRow headers = ExcelUtil.excelHeaders("姓名","身份证号","签约工种","业务营收","签约薪资","备注","创建时间");
				ExcelData data = new ExcelData();
				for(OrderWorker info : list) {
					ExcelRow row = new ExcelRow();
					row.add(info.getName());
					row.add(info.getIdcard());
					row.add(info.getJobTypeName());
					row.add(info.getBusinessIncome());
					row.add(info.getSignSalary());
					row.add(info.getDescription());
					row.add(info.getCreateTime());
					data.add(row);
				}
				ExcelUtil.exportExcel(headers, data, "签订人员列表.xls", response);
			}
		} catch (Exception e) {
			log.error("导出失败", e);
		}
	}
}
