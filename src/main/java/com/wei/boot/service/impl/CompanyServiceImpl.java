package com.wei.boot.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.CompanyMapper;
import com.wei.boot.mapper.WorkerMapper;
import com.wei.boot.model.Company;
import com.wei.boot.model.CompanyExample;
import com.wei.boot.model.CompanyExample.Criteria;
import com.wei.boot.model.Page;
import com.wei.boot.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private WorkerMapper workerMapper;
	
	@Override
	@Transactional
	public void saveCompany(Company company) throws NormalException {

		// 新增
		if(null == company.getId() || company.getId() == 0) {
			company.setCreateTime(new Date());
			companyMapper.insertSelective(company);
		}
		// 编辑
		else {
			company.setUpdateTime(new Date());
			companyMapper.updateByPrimaryKeySelective(company);
		}
	}

	@Override
	@Transactional
	public void deleteCompany(int companyId) throws NormalException {

		if(companyId == 0) {
			throw new NormalException("企业编号为空！");
		}
		companyMapper.deleteByPrimaryKey(companyId);
	}

	@Override
	public List<Company> queryByName(String name) {
		CompanyExample example = new CompanyExample();
		if(!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%"+name+"%");
		}
		return companyMapper.selectByExample(example);
	}

	@Override
	public Page<Company> queryByPage(Page<Company> page, Company company) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageSize", page.getPageSize());
		map.put("offset", page.getOffset());
		if(!StringUtils.isEmpty(company.getName())) {
			map.put("companyName", "%"+company.getName()+"%");
		}
		if(!StringUtils.isEmpty(company.getContactName())) {
			map.put("contactName", company.getContactName()+"%");
		}
		if(!StringUtils.isEmpty(company.getContactPhone())) {
			map.put("contactPhone", company.getContactPhone());
		}
		int totalCount = companyMapper.selectCount(map);
		List<Company> list = companyMapper.selectByPage(map);
		if(null != list && list.size() > 0) {
			for(Company companyInfo : list) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("company", companyInfo.getName()+"%");
				int count = workerMapper.selectCount(param);
				companyInfo.setCount(count);
			}
		}
		page.pageData(list, totalCount);
		return page;
	}

	@Override
	public List<Company> query4Export(Company company) {
		CompanyExample example = new CompanyExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(company.getName())) {
			criteria.andNameLike("%"+company.getName()+"%");
		}
		if(!StringUtils.isEmpty(company.getContactName())) {
			criteria.andContactNameLike("%"+company.getContactName()+"%");
		}
		if(!StringUtils.isEmpty(company.getContactPhone())) {
			criteria.andContactPhoneEqualTo(company.getContactPhone());
		}
		return companyMapper.selectByExample(example);
	}

	@Override
	public Company queryById(int companyId) {
		return companyMapper.selectByPrimaryKey(companyId);
	}

	@Override
	public List<Company> queryAll() {
		return companyMapper.selectByExample(new CompanyExample());
	}


}
