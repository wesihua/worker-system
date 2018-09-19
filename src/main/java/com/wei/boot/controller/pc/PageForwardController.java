package com.wei.boot.controller.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面跳转demo
 * @author weisihua
 * 2018年7月31日 上午11:17:06
 */
@Controller
public class PageForwardController {

	public static final Logger log = LoggerFactory.getLogger(PageForwardController.class);
	
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
	@GetMapping("/role/index")
	public String roleIndex() {
		return "role/role";
	}
	@GetMapping("/user/index")
	public String userIndex() {
		return "user/user";
	}
	@GetMapping("/company/index")
	public String companyIndex() {
		return "company/company";
	}
	@GetMapping("/worker/index")
	public String workerIndex() {
		return "worker/worker";
	}
	
	@GetMapping("/worker/add")
	public String workerAdd() {
		return "worker/addWorker";
	}

	@GetMapping("/jobtype/index")
	public String jobtypeIndex() {
		return "jobtype/jobtype";
	}
	
	@GetMapping("/signing/index")
	public String signingIndex() {
		return "signing/demand";
	}
}
