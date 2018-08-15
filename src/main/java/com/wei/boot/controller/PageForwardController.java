package com.wei.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 页面跳转demo
 * @author weisihua
 * 2018年7月31日 上午11:17:06
 */
@Api(value = "页面跳转")
@Controller
public class PageForwardController {

	public static final Logger log = LoggerFactory.getLogger(PageForwardController.class);
	
	@ApiOperation(value = "进入登录页面",notes = "login")
	@GetMapping("/")
	public String index() throws Exception {
		log.info("进入登录页面");
		//throw new Exception("休息休息");//如果任何地方有异常，则跳转到默认error页面
		
		return "login";
	}
	
	@ApiOperation(value = "进入页面index",notes = "index")
	@GetMapping("/index")
	public String test() {
		log.info("进入页面index");
		return "index";
	}
}
