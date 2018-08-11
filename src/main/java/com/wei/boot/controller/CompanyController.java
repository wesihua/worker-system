package com.wei.boot.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Company;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.excel.ExcelData;
import com.wei.boot.model.excel.ExcelRow;
import com.wei.boot.service.CompanyService;
import com.wei.boot.util.ExcelUtil;

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
	@RequestMapping("/queryByPage")
	public Result queryByPage(Page<Company> page, Company company) {
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
	@RequestMapping("/query4Export")
	public void query4Export(HttpServletResponse response, Company company) {
		try {
			List<Company> list = companyService.query4Export(company);
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
	@RequestMapping("/queryByName")
	public Result queryByName(String name) {
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
	@RequestMapping("/queryDetail")
	public Result queryDetail(int companyId) {
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
	@RequestMapping("/saveCompany")
	public Result saveCompany(Company company) {
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
	@RequestMapping("/deleteCompany")
	public Result deleteCompany(int companyId) {
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
