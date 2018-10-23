package com.wei.boot.controller.pc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.DemandOrder;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.service.OrderService;

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
}
