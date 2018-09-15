package com.wei.boot.controller.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Demand;
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
	 * 保存企业信息
	 * @param company
	 * @return
	 */
	@ApiOperation(value = "保存企业用工需求信息",notes = "")
	@PostMapping("/saveCompany")
	public Result saveCompany(@ApiParam(value = "用工需求",required = true) @RequestBody Demand demand) {
		Result result = Result.SUCCESS;
		try {
			demandService.saveDemand(demand);
		} catch (Exception e) {
			log.error("保存失败", e);
			result = Result.fail("保存用工需求信息失败！");
		}
		return result;
	}
	
}
