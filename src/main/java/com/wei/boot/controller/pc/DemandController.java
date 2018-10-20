package com.wei.boot.controller.pc;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandQuery;
import com.wei.boot.model.DemandStateStatistic;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.signing.JobTypeModel;
import com.wei.boot.service.DemandService;
import com.wei.boot.util.JsonUtil;
import com.wei.boot.util.ToolsUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用工需求
 * @author xsy_2018
 *
 */

@Api(value = "用工需求控制器")
@RestController
@RequestMapping("/demand")
public class DemandController {

	public static final Logger log = LoggerFactory.getLogger(DemandController.class);
	
	@Autowired
	private DemandService demandService;
	
	/**
	 * 保存企业用工需求信息
	 * @param demand
	 * @return
	 */
	@ApiOperation(value = "保存企业用工需求信息",notes = "")
	@PostMapping("/saveDemand")
	public Result saveDemand(@ApiParam(value = "用工需求",required = true) @RequestBody Demand demand,
			HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			demand.setCreateUser(userId);
			demandService.saveDemand(demand);
		} catch (Exception e) {
			log.error("保存失败", e);
			result = Result.fail("保存用工需求信息失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "根据条件查需求单列表",notes = "")
	@PostMapping("/queryDemand")
	public Result queryDemand(@ApiParam(value = "用工需求条件",required = true)  DemandQuery demandQuery,
			@ApiParam(value = "分页条件",required = true)   Page<Demand> page) {
		Result result = Result.SUCCESS;
		try {
			Page<Demand> data = demandService.queryDemand(page,demandQuery);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询需求单列表失败", e);
			result = Result.fail("查询需求单列表失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "需求单详情",notes = "")
	@GetMapping("/demandDetail")
	public Result demandDetail(@ApiParam(value = "需求单id",required = true) @RequestParam Integer demandId) {
		Result result = Result.SUCCESS;
		try {
			Demand demand = demandService.queryDemandById(demandId);
			result.setData(demand);
		} catch (Exception e) {
			log.error("查询需求单详情失败", e);
			result = Result.fail("查询需求单详情失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "需求单工种签约列表",notes = "")
	@PostMapping("/orderWorkerList")
	public Result orderWorkerList(@RequestParam Integer demandJobId,Page<OrderWorker> page) {
		Result result = Result.SUCCESS;
		try {
			JobTypeModel data = demandService.queryOrderWorker(page,demandJobId);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询需求单工种签约列表失败", e);
			result = Result.fail("查询需求单工种签约列表失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "删除签约用工",notes = "")
	@GetMapping("/deleteOrderWorker")
	public Result deleteOrderWorker(@RequestParam Integer orderWorkerId) {
		Result result = Result.SUCCESS;
		try {
			demandService.deleteOrderWorker(orderWorkerId);
		} catch (Exception e) {
			log.error("删除签约用工失败", e);
			result = Result.fail("删除签约用工失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "编辑签约用工",notes = "")
	@PostMapping("/editOrderWorker")
	public Result editOrderWorker(String json,HttpServletRequest request) {
		Result result = Result.SUCCESS;
		OrderWorker orderWorker = JsonUtil.json2Bean(json, OrderWorker.class);
		try {
			int userId = ToolsUtil.getUserId(request);
			orderWorker.setUpdateUser(userId);
			demandService.editOrderWorker(orderWorker);
		} catch (Exception e) {
			log.error("编辑签约用工失败", e);
			result = Result.fail("编辑签约用工失败！");
		}
		return result;
	}
	
	
	@ApiOperation(value = "接单",notes = "")
	@GetMapping("/undertake")
	public Result undertake(@ApiParam(value = "需求单id",required = true) @RequestParam Integer demandId,
			HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			Demand demand = new Demand();
			demand.setId(demandId);
			demand.setUndertakeUser(userId);
			demandService.undertakeDemand(demand);
		} catch (Exception e) {
			log.error("接单失败", e);
			result = Result.fail("接单失败！");
		}
		return result;
	}
	
	
	
	@ApiOperation(value = "关单",notes = "")
	@GetMapping("/closeDemand")
	public Result closeDemand(@ApiParam(value = "需求单id",required = true) @RequestParam Integer demandId,
			@ApiParam(value = "关单原因",required = true) @RequestParam String closeReason,
			HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			Demand demand = new Demand();
			demand.setId(demandId);
			demand.setCloseReason(closeReason);
			demand.setCloseUser(userId);
			demandService.closeDemand(demand);
		} catch (Exception e) {
			log.error("关单失败", e);
			result = Result.fail("关单失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "根据状态分组统计",notes = "")
	@GetMapping("/statisticsByState")
	public Result statisticsByState() {
		Result result = Result.SUCCESS;
		try {
			List<DemandStateStatistic> statisticsByState = demandService.statisticsByState();
			result.setData(statisticsByState);
		} catch (Exception e) {
			log.error("分组统计失败", e);
			result = Result.fail("分组统计失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "添加用工",notes = "")
	@PostMapping("/addOrderWorker")
	public Result addOrderWorker(String json, Integer demandJobId,
			HttpServletRequest request) {
		Result result = Result.SUCCESS;
		List<OrderWorker> workers = JsonUtil.json2List(json, OrderWorker.class);
		try {
			int userId = ToolsUtil.getUserId(request);
			if(!CollectionUtils.isEmpty(workers)) {
				// 生成订单
				for (OrderWorker orderWorker : workers) {
					orderWorker.setDemandJobId(demandJobId);
					orderWorker.setArriveWorkTime(new Date());
					orderWorker.setCreateTime(new Date());
					orderWorker.setCreateUser(userId);
				}
			}
			
			
			demandService.addOrderWorker(demandJobId,workers);
		} catch (Exception e) {
			log.error("添加用工失败", e);
			result = Result.fail("添加用工失败！");
		}
		return result;
	}
	
}
