package com.wei.boot.controller.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
	@GetMapping("/report")
	public String report() {
		return "report/chart";
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
	
	@GetMapping("/worker/edit")
	public String workerEdit(Model model, String workerId) {
		model.addAttribute("workerId", workerId);
		return "worker/editWorker";
	}
	@GetMapping("/worker/detail")
	public String workerDetail(Model model, String workerId, String createUserName) {
		model.addAttribute("workerId", workerId);
		model.addAttribute("createUserName", createUserName);
		return "worker/detail";
	}

	@GetMapping("/jobtype/index")
	public String jobtypeIndex() {
		return "jobtype/jobtype";
	}
	
	@GetMapping("/signing/index")
	public String signingIndex() {
		return "signing/demand";
	}
	
	@GetMapping("/signing/demandDetail")
	public String demandDetail(Integer demandId,ModelMap map) {
		map.addAttribute("demandId", demandId);
		return "signing/detail";
	}
	
	@GetMapping("/signing/addDemand")
	public String addDemand() {
		return "signing/addDemand";
	}
	
	@GetMapping("/signing/workerList")
	public String workerList(Integer jobTypeId,ModelMap map) {
		map.addAttribute("jobTypeId", jobTypeId);
		return "signing/workerList";
	}
	
	@GetMapping("/signing/addWorker")
	public String addWorker(Integer jobTypeId,ModelMap map) {
		map.addAttribute("jobTypeId", jobTypeId);
		return "signing/addWorker";
	}
	
}
