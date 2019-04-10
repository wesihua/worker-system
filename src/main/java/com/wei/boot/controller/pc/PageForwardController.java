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
		return "report/report";
	}
	@GetMapping("/orderReport")
	public String orderReport() {
		return "report/orderReport";
	}
	@GetMapping("/reportForm")
	public String reportForm() {
		return "reportForm/reportForm";
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
	public String workerIndex(Model model, String current,String workerName,String telephone,String idcard,String firstId,
			String secondId,String createUser,String source, String company,String beginTime,String endTime,String minAge,String maxAge,
			String sex,String degree,String expectSalary,String workYear,String discipline,String workStatus) {
		model.addAttribute("current", current);
		model.addAttribute("workerName", workerName);
		model.addAttribute("telephone", telephone);
		model.addAttribute("idcard", idcard);
		model.addAttribute("firstId", firstId);
		model.addAttribute("secondId", secondId);
		model.addAttribute("createUser", createUser);
		model.addAttribute("source", source);
		model.addAttribute("company", company);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("minAge", minAge);
		model.addAttribute("maxAge", maxAge);
		model.addAttribute("sex", sex);
		model.addAttribute("degree", degree);
		model.addAttribute("expectSalary", expectSalary);
		model.addAttribute("workYear", workYear);
		model.addAttribute("discipline", discipline);
		model.addAttribute("workStatus", workStatus);
		return "worker/worker";
	}
	@GetMapping("/order/index")
	public String orderIndex() {
		return "order/order";
	}
	@GetMapping("/companyReport")
	public String companyReport() {
		return "report/companyReport";
	}
	@GetMapping("/userReport")
	public String userReport() {
		return "report/userReport";
	}
	
	@GetMapping("/worker/add")
	public String workerAdd() {
		return "worker/addWorker";
	}
	@GetMapping("/worker/import")
	public String workerImport() {
		return "import/import";
	}
	@GetMapping("/worker/importSuccess")
	public String workerImportSuccess() {
		return "import/importSuccess";
	}
	
	@GetMapping("/mobile/resume")
	public String mobileResume() {
		return "mobile/mobile";
	}
	
	@GetMapping("/worker/edit")
	public String workerEdit(Model model, String workerId,String current,String workerName,String telephone,String idcard,String firstId,
			String secondId,String createUser,String source, String company,String beginTime,String endTime,String minAge,String maxAge,
			String sex,String degree,String expectSalary,String workYear,String discipline,String workStatus) {
		
		model.addAttribute("current", current);
		model.addAttribute("workerId", workerId);
		model.addAttribute("workerName", workerName);
		model.addAttribute("telephone", telephone);
		model.addAttribute("idcard", idcard);
		model.addAttribute("firstId", firstId);
		model.addAttribute("secondId", secondId);
		model.addAttribute("createUser", createUser);
		model.addAttribute("source", source);
		model.addAttribute("company", company);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("minAge", minAge);
		model.addAttribute("maxAge", maxAge);
		model.addAttribute("sex", sex);
		model.addAttribute("degree", degree);
		model.addAttribute("expectSalary", expectSalary);
		model.addAttribute("workYear", workYear);
		model.addAttribute("discipline", discipline);
		model.addAttribute("workStatus", workStatus);
		return "worker/editWorker";
	}
	@GetMapping("/worker/detail")
	public String workerDetail(Model model, String workerId, String createUserName,String current,String workerName,String telephone,String idcard,String firstId,
			String secondId,String createUser,String source, String company,String beginTime,String endTime,String minAge,String maxAge,
			String sex,String degree,String expectSalary,String workYear,String discipline,String workStatus) {
		model.addAttribute("workerId", workerId);
		model.addAttribute("createUserName", createUserName);
		model.addAttribute("current", current);
		model.addAttribute("workerName", workerName);
		model.addAttribute("telephone", telephone);
		model.addAttribute("idcard", idcard);
		model.addAttribute("firstId", firstId);
		model.addAttribute("secondId", secondId);
		model.addAttribute("createUser", createUser);
		model.addAttribute("source", source);
		model.addAttribute("company", company);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("minAge", minAge);
		model.addAttribute("maxAge", maxAge);
		model.addAttribute("sex", sex);
		model.addAttribute("degree", degree);
		model.addAttribute("expectSalary", expectSalary);
		model.addAttribute("workYear", workYear);
		model.addAttribute("discipline", discipline);
		model.addAttribute("workStatus", workStatus);
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
	
	@GetMapping("/signing/toSigning")
	public String toSigning(Integer demandId,ModelMap map) {
		map.addAttribute("demandId", demandId);
		return "signing/signing";
	}
	
	@GetMapping("/signing/addDemand")
	public String addDemand(Integer demandId,ModelMap map) {
		map.addAttribute("demandId", demandId);
		return "signing/addDemand";
	}
	
	@GetMapping("/signing/workerList")
	public String workerList(Integer jobTypeId,Integer source,ModelMap map) {
		map.addAttribute("jobTypeId", jobTypeId);
		map.addAttribute("source", source);
		return "signing/workerList";
	}
	
	@GetMapping("/signing/addWorker")
	public String addWorker(Integer jobTypeId,ModelMap map) {
		map.addAttribute("jobTypeId", jobTypeId);
		return "signing/addWorker";
	}
	
	@GetMapping("/signing/waiting")
	public String signingWaiting() {
		return "signing/demand_waiting";
	}
	
	@GetMapping("/signing/processing")
	public String signingProcessing() {
		return "signing/demand_processing";
	}
	
	@GetMapping("/signing/signed")
	public String signingSigned() {
		return "signing/demand_signed";
	}
	
	@GetMapping("/signing/end")
	public String signingEnd() {
		return "signing/demand_end";
	}
	
	// 
	
	@GetMapping("/demand/close")
	public String demandClose() {
		return "demand_close/demand_close";
	}
	@GetMapping("/demand/confirm")
	public String demandConfirm() {
		return "order/orderConfirm";
	}
	
	@GetMapping("/demand/detail")
	public String demandDetail(Model model, String demandId) {
		model.addAttribute("demandId", demandId);
		return "demand_close/detail";
	}
	@GetMapping("/demand/detailWithOrder")
	public String demandDetailWithOrder(Model model, String demandId) {
		model.addAttribute("demandId", demandId);
		return "demand_close/detailWithOrder";
	}
}
