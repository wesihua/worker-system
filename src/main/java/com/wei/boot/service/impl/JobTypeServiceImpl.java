package com.wei.boot.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.JobTypeMapper;
import com.wei.boot.model.JobType;
import com.wei.boot.model.JobTypeExample;
import com.wei.boot.service.JobTypeService;

@Service
public class JobTypeServiceImpl implements JobTypeService {

	@Autowired
	private JobTypeMapper jobTypeMapper;
	
	@Override
	@Transactional
	public void saveJobType(JobType jobType) throws NormalException {
		// 新增
		if(null == jobType.getId() || jobType.getId() == 0) {
			if(jobType.getLevel() == 0) {
				jobType.setLevel(1);
			}
			if(StringUtils.isEmpty(jobType.getName())) {
				throw new NormalException("工种名称为空");
			}
			if(GlobalConstant.JobTypeLevel.SECOND == jobType.getLevel()) {
				if(null == jobType.getParentId() || jobType.getParentId() == 0) {
					throw new NormalException("二级工种请选择上级工种");
				}
			}
			else {
				jobType.setParentId(0);
			}
			jobType.setCreateTime(new Date());
			jobTypeMapper.insert(jobType);
		}
		// 更新
		else {
			jobType.setUpdateTime(new Date());
			jobTypeMapper.updateByPrimaryKeySelective(jobType);
		}
	}

	@Override
	@Transactional
	public void deleteJobType(int id) throws NormalException {

		jobTypeMapper.deleteByPrimaryKey(id);
		JobTypeExample example = new JobTypeExample();
		example.createCriteria().andParentIdEqualTo(id);
		jobTypeMapper.deleteByExample(example);
	}

	@Override
	public List<JobType> selectAllRoot() throws NormalException {
		JobTypeExample example = new JobTypeExample();
		example.createCriteria().andLevelEqualTo(GlobalConstant.JobTypeLevel.FIRST);
		return jobTypeMapper.selectByExample(example);
	}

	@Override
	public List<JobType> selectByParentId(int parentId) {
		JobTypeExample example = new JobTypeExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		return jobTypeMapper.selectByExample(example);
	}

	@Override
	public List<JobType> selectAllTree() {
		List<JobType> roots = selectTreeByParentId(0);
		return roots;
	}

	public List<JobType> selectTreeByParentId(int parentId) {
		JobTypeExample example = new JobTypeExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<JobType> roots = jobTypeMapper.selectByExample(example);
		roots.stream().forEach(each -> each.setChildren(selectTreeByParentId(each.getId())));
		return roots;
	}

	@Override
	public List<JobType> selectByName(String jobTypeName) {
		JobTypeExample example = new JobTypeExample();
		
		if(!StringUtils.isEmpty(jobTypeName)) {
			example.createCriteria().andNameLike("%"+jobTypeName+"%");
		}
		example.createCriteria().andLevelEqualTo(GlobalConstant.JobTypeLevel.SECOND);
		return jobTypeMapper.selectByExample(example);
	}
}
