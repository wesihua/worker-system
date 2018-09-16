package com.wei.boot.controller.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Company;
import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandQuery;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.service.DemandService;
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
	public Result saveDemand(@ApiParam(value = "用工需求",required = true) @RequestBody Demand demand) {
		Result result = Result.SUCCESS;
		try {
			demandService.saveDemand(demand);
		} catch (Exception e) {
			log.error("保存失败", e);
			result = Result.fail("保存用工需求信息失败！");
		}
		return result;
	}
	
	@ApiOperation(value = "根据条件查需求单",notes = "")
	@PostMapping("/queryDemand")
	public Result queryDemand(@ApiParam(value = "用工需求条件",required = true) @RequestBody DemandQuery demandQuery,
			@ApiParam(value = "分页条件",required = true) @RequestBody  Page<Demand> page) {
		Result result = Result.SUCCESS;
		try {
			Page<Demand> data = demandService.queryDemand(page,demandQuery);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail("保存用工需求信息失败！");
		}
		return result;
	}
	
	
	
}
