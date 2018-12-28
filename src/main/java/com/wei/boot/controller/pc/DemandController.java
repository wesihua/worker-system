package com.wei.boot.controller.pc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.contant.GlobalConstant.OrderWorkerState;
import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandJob;
import com.wei.boot.model.DemandQuery;
import com.wei.boot.model.DemandStateStatistic;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.excel.ExcelData;
import com.wei.boot.model.excel.ExcelRow;
import com.wei.boot.model.signing.JobTypeModel;
import com.wei.boot.model.signing.OrderModel;
import com.wei.boot.service.DemandService;
import com.wei.boot.util.ExcelUtil;
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
	
	
	
	/**
	 * 编辑企业用工需求信息
	 * @param demand
	 * @return
	 */
	@ApiOperation(value = "编辑企业用工需求信息",notes = "")
	@PostMapping("/editDemand")
	public Result editDemand(@ApiParam(value = "用工需求",required = true) @RequestBody Demand demand,
			HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			demand.setUpdateUser(userId);
			demandService.editDemand(demand);
		} catch (Exception e) {
			log.error("保存失败", e);
			result = Result.fail("保存用工需求信息失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "根据条件查需求单列表",notes = "")
	@PostMapping("/queryDemand")
	public Result queryDemand(@ApiParam(value = "用工需求条件",required = true)  String demandQueryJson,
			@ApiParam(value = "分页条件",required = true)   Page<Demand> page,
			HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			DemandQuery demandQuery = JsonUtil.json2Bean(demandQueryJson, DemandQuery.class);
			demandQuery.setUserId(userId);
			Page<Demand> data = demandService.queryDemand(page,demandQuery);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询需求单列表失败", e);
			result = Result.fail("查询需求单列表失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "待处理需求单详情",notes = "")
	@GetMapping("/waitingDemand")
	public Result waitingDemand(@ApiParam(value = "需求单id",required = true) @RequestParam Integer demandId) {
		Result result = Result.SUCCESS;
		try {
			Demand demand = demandService.waitingDemand(demandId);
			result.setData(demand);
		} catch (Exception e) {
			log.error("查询需求单详情失败", e);
			result = Result.fail("查询需求单详情失败！");
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
	
	
	@ApiOperation(value = "需求单工种签约列表",notes = "")
	@GetMapping("/orderWorkerAssignList")
	public Result orderWorkerAssignList(@RequestParam Integer demandJobId) {
		Result result = Result.SUCCESS;
		try {
			JobTypeModel data = demandService.queryOrderWorkerList(OrderWorkerState.ASSIGN,demandJobId);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询需求单工种签约列表失败", e);
			result = Result.fail("查询需求单工种签约列表失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "需求单工种待签约列表",notes = "")
	@GetMapping("/demandAssignList")
	public Result demandAssignList(@RequestParam Integer demandId) {
		Result result = Result.SUCCESS;
		try {
			OrderModel orderModel = demandService.demandAssignList(demandId);
			result.setData(orderModel);
		} catch (Exception e) {
			log.error("查询需求单工种签约列表失败", e);
			result = Result.fail("查询需求单工种签约列表失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "需求单工种签约列表",notes = "")
	@GetMapping("/orderWorkerSigningList")
	public Result orderWorkerSigningList(@RequestParam Integer demandJobId) {
		Result result = Result.SUCCESS;
		try {
			JobTypeModel data = demandService.queryOrderWorkerList(OrderWorkerState.SIGNING,demandJobId);
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
	
	@ApiOperation(value = "签约",notes = "")
	@GetMapping("/signing")
	public Result signing(@ApiParam(value = "需求单id",required = true) @RequestParam Integer demandId,
			HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			Demand demand = new Demand();
			demand.setId(demandId);
			demand.setUndertakeUser(userId);
			int orderCount = demandService.signing(demand);
			result.setData(orderCount);
		} catch (Exception e) {
			log.error("签约失败", e);
			result = Result.fail("签约失败！");
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
	
	// waitingSigning
	
	@ApiOperation(value = "待签约详情",notes = "")
	@GetMapping("/waitingSigning")
	public Result waitingSigning(Integer demandId,
			HttpServletRequest request) {
		Result result = Result.SUCCESS;
		
		try {
			Demand demand = demandService.waitingSigningOrder(demandId);
			result.setData(demand);
		} catch (Exception e) {
			log.error("查询待签约详情失败", e);
			result = Result.fail("查询待签约详情失败！");
		}
		return result;
	}
	
	/**
	 * 关单列表
	 * @param page
	 * @param demandQuery
	 * @return
	 */
	@GetMapping("/queryByPage4Close")
	public Result queryByPage4Close(Page<Demand> page, DemandQuery demandQuery) {
		Result result = Result.SUCCESS;
		try {
			Page<Demand> data = demandService.queryByPage4Close(page, demandQuery);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询需求单列表失败", e);
			result = Result.fail("查询需求单列表失败！");
		}
		return result;
	}
	
	/**
	 * 导出
	 * @param response
	 * @param company
	 */
	@ApiOperation(value = "导出",notes = "")
	@GetMapping("/queryByPage4CloseExport")
	public void queryByPage4CloseExport(HttpServletResponse response, DemandQuery demandQuery) {
		try {
			Page<Demand> page = new Page<>();
			page.setPageSize(20000);
			List<Demand> list = demandService.queryByPage4Close(page, demandQuery).getData();
			if (null != list && !list.isEmpty()) {
				ExcelRow headers = ExcelUtil.excelHeaders("招聘编号","企业名称","招聘工种","招聘人数", "性别要求","学历要求","专业要求","接单人","关单人","关单时间","关单原因","状态", "创建时间");
				ExcelData data = new ExcelData();
				for (Demand info : list) {
					ExcelRow row = new ExcelRow();
					row.add(info.getDemandNumber());
					row.add(info.getCompanyName());
					if(!CollectionUtils.isEmpty(info.getDemandJobList())) {
						DemandJob job = info.getDemandJobList().get(0);
						row.add(job.getJobTypeName());
						row.add(job.getWorkerCount());
						row.add(job.getGenderName());
						row.add(job.getDegreeName());
						row.add(job.getMajor());
					}
					else {
						row.add("");
						row.add("");
						row.add("");
						row.add("");
						row.add("");
					}
					row.add(info.getUndertakeUserName());
					row.add(info.getCloseUserName());
					row.add(info.getCloseTime());
					row.add(info.getCloseReason());
					row.add(info.getStateName());
					row.add(info.getCreateTime());
					data.add(row);
				}
				ExcelUtil.exportExcel(headers, data, "关单信息.xls", response);
			}
		} catch (Exception e) {
			log.error("导出失败", e);
		}
	}
	
	@GetMapping("/queryDetail")
	public Result queryDetail(int demandId) {
		Result result = Result.SUCCESS;
		try {
			Demand demand = demandService.queryDetail(demandId);
			result.setData(demand);
		} catch (Exception e) {
			log.error("查询需求单详情失败", e);
			result = Result.fail("查询需求单详情失败！");
		}
		return result;
	}
	
	@GetMapping("/queryDetailWithOrder")
	public Result queryDetailWithOrder(int demandId) {
		Result result = Result.SUCCESS;
		try {
			Demand demand = demandService.queryDetailWithOrder(demandId);
			result.setData(demand);
		} catch (Exception e) {
			log.error("查询需求单详情失败", e);
			result = Result.fail("查询需求单详情失败！");
		}
		return result;
	}
	
	
	/**
	 * 导出
	 * @param response
	 * @param company
	 */
	@ApiOperation(value = "导出",notes = "")
	@GetMapping("/export")
	public void export(HttpServletResponse response, HttpServletRequest request, DemandQuery parameters) {
		try {
			Page<Demand> page = new Page<>();
			page.setPageSize(20000);
			int userId = ToolsUtil.getUserId(request);
			parameters.setUserId(userId);
			List<Demand> list = demandService.queryDemand(page, parameters).getData();
			if (null != list && !list.isEmpty()) {
				ExcelData data = new ExcelData();
				
				List<String> headerList = new ArrayList<String>();
				headerList.add("招聘编号");
				headerList.add("状态");
				headerList.add("企业名称");
				headerList.add("创建时间");
				headerList.add("创建人");
				headerList.add("招聘工种");
				headerList.add("招聘人数");
				headerList.add("性别要求");
				headerList.add("学历要求");
				headerList.add("专业要求");
				
				
				if (Objects.nonNull(parameters.getState()) && parameters.getState() >= GlobalConstant.DemandState.PROCESSING) {
					headerList.add("接单人");
					headerList.add("接单时间");
				}

				
				if (Objects.nonNull(parameters.getState()) && parameters.getState() >= GlobalConstant.DemandState.CLOSE) {
					headerList.add("关单人");
					headerList.add("关单时间");
					headerList.add("关单原因");
				}
				
				headerList.add("备注");
				ExcelRow headers = ExcelUtil.excelHeaders4List(headerList);
				
				for (Demand info : list) {
					ExcelRow row = new ExcelRow();
					
					row.add(info.getDemandNumber());
					row.add(info.getStateName());
					row.add(info.getCompanyName());
					row.add(info.getCreateTime());
					row.add(info.getCreateUserName());
					if(!CollectionUtils.isEmpty(info.getDemandJobList())) {
						DemandJob job = info.getDemandJobList().get(0);
						row.add(job.getJobTypeName());
						row.add(job.getWorkerCount());
						row.add(job.getGenderName());
						row.add(job.getDegreeName());
						row.add(job.getMajor());
					}
					else {
						row.add("");
						row.add("");
						row.add("");
						row.add("");
						row.add("");
					}
					
					if (Objects.nonNull(parameters.getState()) && parameters.getState() >= GlobalConstant.DemandState.PROCESSING) {
						row.add(info.getUndertakeUserName());
						row.add(info.getUndertakeTime());
					}
					
					if (Objects.nonNull(parameters.getState()) && parameters.getState() >= GlobalConstant.DemandState.CLOSE) {
						row.add(info.getCloseUserName());
						row.add(info.getCloseTime());
						row.add(info.getCloseReason());
					}
					row.add(info.getDescription());					
					data.add(row);
				}
				
				ExcelUtil.exportExcel(headers, data, "需求单信息.xls", response);
			
			}
		} catch (Exception e) {
			log.error("导出失败", e);
		}
	}
	
	@GetMapping("/isDemandConfirmed")
	public Result isDemandConfirmed(Integer demandId) {
		Result result = Result.SUCCESS;
		try {
			boolean b = demandService.hasUnConfirmedDemand(demandId);
			result.setData(b);
		} catch (Exception e) {
			log.error("", e);
			result = Result.fail("查询失败！");
		}
		return result;
	}
}
