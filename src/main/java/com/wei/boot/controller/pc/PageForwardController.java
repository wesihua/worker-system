package com.wei.boot.controller.pc;

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
@Controller
public class PageForwardController {

	public static final Logger log = LoggerFactory.getLogger(PageForwardController.class);
	
	
	@GetMapping("/home")
	public String home() throws Exception {
		return "home";
	}
	@GetMapping("/welcome")
	public String welcome() throws Exception {
		return "welcome";
	}
	@GetMapping("/role/index")
	public String roleIndex() throws Exception {
		return "role/role";
	}
	@GetMapping("/user/index")
	public String userIndex() throws Exception {
		return "user/user";
	}
	@GetMapping("/company/index")
	public String companyIndex() throws Exception {
		return "company/company";
	}
	@GetMapping("/worker/index")
	public String workerIndex() throws Exception {
		return "worker/worker";
	}
}
