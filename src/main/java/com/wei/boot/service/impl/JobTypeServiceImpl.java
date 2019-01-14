package com.wei.boot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.JobTypeMapper;
import com.wei.boot.model.JobType;
import com.wei.boot.model.JobTypeExample;
import com.wei.boot.model.selectivity.TreeInfo;
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
		List<JobType> allList = jobTypeMapper.selectAll();
		List<JobType> firstList = findRoot(allList);
		for(JobType first : firstList) {
			List<JobType> children = findChildrenByRoot(allList, first);
			first.setChildren(children);
		}
		return firstList;
	}

	private List<JobType> findRoot(List<JobType> list){
		List<JobType> root = new ArrayList<JobType>();
		for(JobType info : list) {
			if(info.getLevel() == 1) {
				root.add(info);
			}
		}
		return root;
	}
	private List<JobType> findChildrenByRoot(List<JobType> list, JobType root){
		List<JobType> children = new ArrayList<JobType>();
		for(JobType info : list) {
			if(info.getParentId().intValue() == root.getId().intValue()) {
				children.add(info);
			}
		}
		return children;
	}
	
	public List<JobType> selectTreeByParentId(int parentId) {
		JobTypeExample example = new JobTypeExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<JobType> roots = jobTypeMapper.selectByExample(example);
		roots.stream().forEach(each -> each.setChildren(selectTreeByParentId(each.getId())));
		return roots;
	}

	@Override
	public List<JobType> selectChildJobType() {
		JobTypeExample example = new JobTypeExample();
		example.createCriteria().andLevelEqualTo(GlobalConstant.JobTypeLevel.SECOND);
		return jobTypeMapper.selectByExample(example);
	}

	@Override
	public void insertJobType(List<JobType> list) {
		jobTypeMapper.insertBatch(list);
	}

	
	////////////////////////////////////////////////////////////////////
	@Override
	public List<TreeInfo> queryAllTreeNew() {
		List<TreeInfo> allList = jobTypeMapper.selectAllNew();
		List<TreeInfo> firstList = findRootNew(allList);
		for(TreeInfo first : firstList) {
			List<TreeInfo> children = findChildrenByRoot(allList, first);
			first.setChildren(children);
		}
		return firstList;
	}
	
	private List<TreeInfo> findRootNew(List<TreeInfo> list){
		List<TreeInfo> root = new ArrayList<TreeInfo>();
		for(TreeInfo info : list) {
			if(info.getLevel() == 1) {
				root.add(info);
			}
		}
		return root;
	}
	
	private List<TreeInfo> findChildrenByRoot(List<TreeInfo> list, TreeInfo root){
		List<TreeInfo> children = new ArrayList<TreeInfo>();
		for(TreeInfo info : list) {
			if(info.getParentId().intValue() == root.getId().intValue()) {
				children.add(info);
			}
		}
		return children;
	}

	@Override
	public JobType queryByName(String jobTypeName) {
		if(!StringUtils.isEmpty(jobTypeName)) {
			JobTypeExample example = new JobTypeExample();
			example.createCriteria().andNameEqualTo(jobTypeName);
			List<JobType> list = jobTypeMapper.selectByExample(example);
			if(!CollectionUtils.isEmpty(list)) {
				return list.get(0);
			}
		}
		return null;
	}
}
