package com.wei.boot.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.model.JobType;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.Worker;
import com.wei.boot.service.WorkerService;
import com.wei.boot.util.CheckUtils;
import com.wei.boot.util.ToolsUtil;

@RestController
@RequestMapping("/app/worker")
public class WorkerController {

	public static final Logger log = LoggerFactory.getLogger(WorkerController.class);
	
	@Autowired
	private WorkerService workerService;
	
	
	/**
	 * 列表
	 * @param page
	 * @param worker
	 * @return
	 */
	@RequestMapping("/list")
	public Result list(Page<Worker> page, String keyWord, Worker worker) {
		Result result = Result.SUCCESS;
		try {
			if(!StringUtils.isEmpty(keyWord)) {
				if(CheckUtils.isMobile(keyWord)) {
					worker.setTelephone(keyWord);
				}
				else {
					worker.setName(keyWord);
				}
			}
			Page<Worker> data = workerService.queryByPage4App(page, worker);
			result.setData(data);
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
	public Result addWorker(@RequestBody Worker worker, HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			worker.setCreateUser(userId);
			worker.setSouce(GlobalConstant.Source.APP);
			workerService.addWorker(worker);
		} catch (Exception e) {
			log.error("新增人才失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 更改人才基本信息
	 * @param worker
	 * @return
	 */
	@RequestMapping("/updateWorker")
	public Result updateWorker(@RequestBody Worker worker,HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			worker.setUpdateUser(userId);
			workerService.updateWorker4App(worker);
		} catch (Exception e) {
			log.error("更新人才信息失败", e);
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
