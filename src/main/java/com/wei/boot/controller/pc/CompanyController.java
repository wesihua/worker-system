package com.wei.boot.controller.pc;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Company;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.excel.ExcelData;
import com.wei.boot.model.excel.ExcelRow;
import com.wei.boot.service.CompanyService;
import com.wei.boot.util.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "公司相关接口")
@RestController
@RequestMapping("/company")
public class CompanyController {

	public static final Logger log = LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 分页查询
	 * @param page
	 * @param company
	 * @return
	 */
	@ApiOperation(value = "公司分页查询",notes = "")
	@GetMapping("/list")
	public Result list(Page<Company> page, Company company) {
		Result result = Result.SUCCESS;
		try {
			Page<Company> data = companyService.queryByPage(page, company);
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
	 * @param company
	 */
	@ApiOperation(value = "导出",notes = "")
	@PostMapping("/export")
	public void export(HttpServletResponse response, Company company) {
		try {
			Page<Company> page = new Page<Company>();
			page.setPageSize(20000);
			List<Company> list = companyService.queryByPage(page, company).getData();
			if(null != list && list.size() > 0) {
				ExcelRow headers = ExcelUtil.excelHeaders("企业名称","地址","所属行业","联系人","联系电话");
				ExcelData data = new ExcelData();
				for(Company info : list) {
					ExcelRow row = new ExcelRow();
					row.add(info.getName());
					row.add(info.getAddress());
					row.add(info.getIndustry());
					row.add(info.getContactName());
					row.add(info.getContactPhone());
					data.add(row);
				}
				ExcelUtil.exportExcel(headers, data, "企业信息.xlsx", response);
			}
		} catch (Exception e) {
			log.error("导出失败", e);
		}
	}
	
	/**
	 * 根据名称查询
	 * @param name
	 * @return
	 */
	@ApiOperation(value = "根据名称查询",notes = "")
	@GetMapping("/queryByName")
	public Result queryByName(@ApiParam(value = "公司名称",required = true) @RequestParam  String name) {
		Result result = Result.SUCCESS;
		try {
			List<Company> list = companyService.queryByName(name);
			result.setData(list);
		} catch (Exception e) {
			log.error("删除失败", e);
			result = Result.fail("删除企业失败！");
		}
		return result;
	}
	
	
	/**
	 * 根据名称查询
	 * @param name
	 * @return
	 */
	@ApiOperation(value = "根据名称查询",notes = "")
	@GetMapping("/queryDetail")
	public Result queryDetail(@ApiParam(value = "公司id",required = true) @RequestParam int companyId) {
		Result result = Result.SUCCESS;
		try {
			Company company = companyService.queryById(companyId);
			result.setData(company);
		} catch (Exception e) {
			log.error("查询详情", e);
			result = Result.fail("查询企业详情失败！");
		}
		return result;
	}
	/**
	 * 保存企业信息
	 * @param company
	 * @return
	 */
	@ApiOperation(value = "保存企业信息",notes = "")
	@PostMapping("/saveCompany")
	public Result saveCompany(@ApiParam(value = "公司",required = true) @RequestBody Company company) {
		Result result = Result.SUCCESS;
		try {
			companyService.saveCompany(company);
		} catch (Exception e) {
			log.error("保存失败", e);
			result = Result.fail("保存企业信息失败！");
		}
		return result;
	}
	
	/**
	 * 删除企业
	 * @param companyId
	 * @return
	 */
	@ApiOperation(value = "删除企业",notes = "")
	@GetMapping("/deleteCompany")
	public Result deleteCompany(@ApiParam(value = "公司id",required = true) @RequestParam int companyId) {
		Result result = Result.SUCCESS;
		try {
			companyService.deleteCompany(companyId);
		} catch (Exception e) {
			log.error("删除失败", e);
			result = Result.fail("删除企业失败！");
		}
		return result;
	}
}
