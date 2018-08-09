package com.wei.boot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.JobType;
import com.wei.boot.model.Result;
import com.wei.boot.service.JobTypeService;

/**
 * 工种controller
 * @author weisihua
 * 2018年8月8日 下午1:46:23
 */
@RestController
@RequestMapping("/jobType")
public class JobTypeController {

	public static final Logger log = LoggerFactory.getLogger(JobTypeController.class);
	
	@Autowired
	private JobTypeService jobTypeService;
	
	/**
	 * 查询工种树形结构
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryTree")
	public Result queryTree(HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			List<JobType> list = jobTypeService.selectAllTree(0);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询树形工种失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 保存工种
	 * @param request
	 * @param info
	 * @return
	 */
	@RequestMapping("/saveJobType")
	public Result saveJobType(HttpServletRequest request, JobType info) {
		Result result = Result.SUCCESS;
		try {
			jobTypeService.saveJobType(info);
		} catch (Exception e) {
			log.error("保存工种失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 删除工种
	 * @param request
	 * @param jobTypeId
	 * @return
	 */
	@RequestMapping("/removeJobType")
	public Result removeJobType(HttpServletRequest request, int jobTypeId) {
		Result result = Result.SUCCESS;
		try {
			jobTypeService.deleteJobType(jobTypeId);
		} catch (Exception e) {
			log.error("删除工种失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 查询父级工种
	 * @return
	 */
	@RequestMapping("/queryRootJobType")
	public Result queryRootJobType() {
		Result result = Result.SUCCESS;
		try {
			List<JobType> list = jobTypeService.selectAllRoot();
			result.setData(list);
		} catch (Exception e) {
			log.error("查询父级工种", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 查询子级工种
	 * @param request
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/queryByParentId")
	public Result queryByParentId(HttpServletRequest request, int parentId) {
		Result result = Result.SUCCESS;
		try {
			List<JobType> list = jobTypeService.selectByParentId(parentId);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询子级工种", e);
			result = Result.fail(e);
		}
		return result;
	}
}
