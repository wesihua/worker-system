package com.wei.boot.service;

import java.util.List;

import com.wei.boot.exception.NormalException;
import com.wei.boot.model.Company;
import com.wei.boot.model.Page;

/**
 * 企业接口
 * @author weisihua
 * 2018年8月10日 下午2:35:15
 */
public interface CompanyService {

	/**
	 * 新增或更改企业
	 * @param company
	 * @throws NormalException
	 */
	public void saveCompany(Company company) throws NormalException;
	
	/**
	 * 删除企业
	 * @param companyId
	 * @throws NormalException
	 */
	public void deleteCompany(int companyId) throws NormalException;
	
	/**
	 * 根据条件查询企业，可用于联想查询、查询全部企业
	 * @param company
	 * @return
	 */
	public List<Company> queryByCondition(Company company);
	
	/**
	 * 分页查询企业
	 * @param company
	 * @return
	 */
	public Page<Company> queryCompanyPage(Page<Company> page, Company company);
	
	/**
	 * 查询企业总数，用于分页
	 * @param company
	 * @return
	 */
	public int queryCompanyCount(Company company);
}
