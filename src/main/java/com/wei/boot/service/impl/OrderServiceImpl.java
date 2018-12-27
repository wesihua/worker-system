package com.wei.boot.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.wei.boot.mapper.DemandOrderMapper;
import com.wei.boot.mapper.OrderWorkerMapper;
import com.wei.boot.model.DemandOrder;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.Page;
import com.wei.boot.model.User;
import com.wei.boot.service.OrderService;
import com.wei.boot.service.UserService;
import com.wei.boot.util.DateUtils;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private DemandOrderMapper demandOrderMapper;
	
	@Autowired
	private OrderWorkerMapper orderWorkerMapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Page<DemandOrder> queryByPage(DemandOrder info, Page<DemandOrder> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageSize", page.getPageSize());
		map.put("offset", page.getOffset());
		if(!StringUtils.isEmpty(info.getOrderNumber())) {
			map.put("orderNumber", info.getOrderNumber());
		}
		if(!StringUtils.isEmpty(info.getCompanyName())) {
			map.put("companyName", info.getCompanyName()+"%");
		}
		if(!StringUtils.isEmpty(info.getCreateUserName())) {
			map.put("createUserName", info.getCreateUserName()+"%");
		}
		if(!StringUtils.isEmpty(info.getBeginTime())) {
			map.put("beginTime", DateUtils.parseDate(info.getBeginTime()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(!StringUtils.isEmpty(info.getEndTime())) {
			map.put("endTime", DateUtils.parseDate(info.getEndTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != info.getConfirmState()) {
			map.put("confirmState", info.getConfirmState());
		}
		if(!StringUtils.isEmpty(info.getDemandNumber())) {
			map.put("demandNumber", info.getDemandNumber());
		}
		List<DemandOrder> list = demandOrderMapper.selectByPage(map);
		// 翻译确认人
		for(DemandOrder order : list) {
			if(null != order.getConfirmUser()) {
				User user = userService.queryById(order.getConfirmUser());
				order.setConfirmUserName(user.getRealName());
			}
		}
		int totalCount = demandOrderMapper.selectCount(map);
		return page.pageData(list, totalCount);
	}

	@Override
	public List<OrderWorker> queryOrderWorkerDetail(int orderId) {
		List<OrderWorker> list = orderWorkerMapper.selectByOrderId(orderId);
		if(!CollectionUtils.isEmpty(list)) {
			for(OrderWorker info : list) {
				User user = userService.queryById(info.getWorkerCreateUser());
				info.setWorkerCreateUserName(user.getRealName());
			}
		}
		return list;
	}

	@Override
	public Page<DemandOrder> queryByPage4Confirm(DemandOrder info, Page<DemandOrder> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageSize", page.getPageSize());
		map.put("offset", page.getOffset());
		if(!StringUtils.isEmpty(info.getOrderNumber())) {
			map.put("orderNumber", info.getOrderNumber());
		}
		if(!StringUtils.isEmpty(info.getCompanyName())) {
			map.put("companyName", info.getCompanyName()+"%");
		}
		if(!StringUtils.isEmpty(info.getCreateUserName())) {
			map.put("createUserName", info.getCreateUserName()+"%");
		}
		if(!StringUtils.isEmpty(info.getBeginTime())) {
			map.put("beginTime", DateUtils.parseDate(info.getBeginTime()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(!StringUtils.isEmpty(info.getEndTime())) {
			map.put("endTime", DateUtils.parseDate(info.getEndTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != info.getConfirmState()) {
			map.put("confirmState", info.getConfirmState());
		}
		if(!StringUtils.isEmpty(info.getDemandNumber())) {
			map.put("demandNumber", info.getDemandNumber());
		}
		List<DemandOrder> list = demandOrderMapper.selectByPage(map);
		
		for(DemandOrder order : list) {
			// 查询签订人员列表
			List<OrderWorker> workerList = queryOrderWorkerDetail(order.getId());
			order.setOrderWorkerList(workerList);
			// 翻译确认人
			if(null != order.getConfirmUser()) {
				User user = userService.queryById(order.getConfirmUser());
				order.setConfirmUserName(user.getRealName());
			}
		}
		int totalCount = demandOrderMapper.selectCount(map);
		return page.pageData(list, totalCount);
	}

	@Override
	public void confirmOrder(int orderId, int userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		map.put("confirmUser", userId);
		map.put("confirmTime", new Date());
		map.put("updateTime", new Date());
		map.put("confirmState", 2);
		demandOrderMapper.confirmOrder(map);
	}

	@Override
	public void rejectOrder(int orderId, String rejectReason, int userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		map.put("confirmUser", userId);
		map.put("confirmTime", new Date());
		map.put("updateTime", new Date());
		map.put("rejectReason", rejectReason);
		map.put("confirmState", 1);
		demandOrderMapper.confirmOrder(map);
	}
}
