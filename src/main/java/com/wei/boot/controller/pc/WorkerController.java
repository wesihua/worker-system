package com.wei.boot.controller.pc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.model.JobType;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.Worker;
import com.wei.boot.model.WorkerEducation;
import com.wei.boot.model.WorkerExperience;
import com.wei.boot.model.WorkerJobType;
import com.wei.boot.model.excel.ExcelData;
import com.wei.boot.model.excel.ExcelRow;
import com.wei.boot.service.WorkerService;
import com.wei.boot.util.ExcelUtil;
import com.wei.boot.util.JsonUtil;
import com.wei.boot.util.ToolsUtil;

/**
 * 人才信息controller
 * @author weisihua
 * 2018年9月3日 上午11:40:46
 */
@RestController
@RequestMapping("/worker")
public class WorkerController {

	public static final Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private WorkerService workerService;
	
	/**
	 * 列表
	 * @param page
	 * @param worker
	 * @return
	 */
	@RequestMapping("/list")
	public Result list(Page<Worker> page, Worker worker) {
		Result result = Result.SUCCESS;
		try {
			Page<Worker> data = workerService.queryByPage(page, worker);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 导出
	 * @param response
	 * @param worker
	 */
	@PostMapping("/export")
	public void export(HttpServletResponse response, Worker worker) {
		try {
			Page<Worker> page = new Page<Worker>();
			page.setPageSize(20000);
			List<Worker> list = workerService.queryByPage(page, worker).getData();
			if(null != list && list.size() > 0) {
				ExcelRow headers = ExcelUtil.excelHeaders("姓名","联系电话","身份证号","性别","年龄","擅长工种","工作年限","职称","来源","录入人","录入时间");
				ExcelData data = new ExcelData();
				for(Worker info : list) {
					ExcelRow row = new ExcelRow();
					row.add(info.getName());
					row.add(info.getTelephone());
					row.add(info.getIdcard());
					row.add(info.getSexName());
					row.add(info.getAge());	// 由身份证号计算得出
					row.add(info.getJobTypeName());
					row.add(info.getWorkYear());
					row.add(info.getTitle());
					row.add(info.getSourceName());
					row.add(info.getCreateUserName());
					data.add(row);
				}
				ExcelUtil.exportExcel(headers, data, "人才信息.xlsx", response);
			}
		} catch (Exception e) {
			log.error("导出失败", e);
		}
	}
	
	/**
	 * 查询详情
	 * @param workerId
	 * @return
	 */
	@RequestMapping("/queryDetail")
	public Result queryDetail(int workerId) {
		Result result = Result.SUCCESS;
		try {
			Worker worker = workerService.queryDetail(workerId);
			result.setData(worker);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 新增人才
	 * @param worker
	 * @return
	 */
	@RequestMapping("/addWorker")
	public Result addWorker(Worker worker,HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			worker.setCreateUser(userId);
			worker.setSouce(GlobalConstant.Source.PC);
			workerService.addWorker(worker);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 删除人才
	 * @param workerId
	 * @return
	 */
	@RequestMapping("/deleteWorker")
	public Result deleteWorker(int workerId) {
		Result result = Result.SUCCESS;
		try {
			workerService.deleteWorker(workerId);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 更改人才基本信息
	 * @param worker
	 * @return
	 */
	@RequestMapping("/updateWorkerBody")
	public Result updateWorkerBody(Worker worker, HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			worker.setUpdateUser(userId);
			workerService.updateWorkerBody(worker);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 更改教育经历信息
	 * @param workerId
	 * @param educationJson
	 * @return
	 */
	@RequestMapping("/updateWorkerEducation")
	public Result updateWorkerEducation(int workerId, String educationJson) {
		Result result = Result.SUCCESS;
		try {
			List<WorkerEducation> educationList = JsonUtil.json2List(educationJson, WorkerEducation.class);
			workerService.updateEducation(workerId, educationList);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 更改工作经验
	 * @param workerId
	 * @param experienceJson
	 * @return
	 */
	@RequestMapping("/updateWorkerExperience")
	public Result updateWorkerExperience(int workerId, String experienceJson) {
		Result result = Result.SUCCESS;
		try {
			List<WorkerExperience> experienceList = JsonUtil.json2List(experienceJson, WorkerExperience.class);
			workerService.updateExperience(workerId, experienceList);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 更改工种信息
	 * @param workerId
	 * @param jobTypeJson
	 * @return
	 */
	@RequestMapping("/updateWorkerJobType")
	public Result updateWorkerJobType(int workerId, String jobTypeJson) {
		Result result = Result.SUCCESS;
		try {
			List<WorkerJobType> jobTypeList = JsonUtil.json2List(jobTypeJson, WorkerJobType.class);
			workerService.updateJobType(workerId, jobTypeList);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 查询选中的工种树
	 * @param workerId
	 * @return
	 */
	@RequestMapping("/querySelectedJobType")
	public Result querySelectedJobType(int workerId) {
		Result result = Result.SUCCESS;
		try {
			List<JobType> list = workerService.querySelectedJobType(workerId);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
}
