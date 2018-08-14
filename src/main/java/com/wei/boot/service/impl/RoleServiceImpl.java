package com.wei.boot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.RoleMapper;
import com.wei.boot.mapper.RoleMenuMapper;
import com.wei.boot.model.Page;
import com.wei.boot.model.Role;
import com.wei.boot.model.RoleExample;
import com.wei.boot.model.RoleMenu;
import com.wei.boot.model.RoleMenuExample;
import com.wei.boot.service.RoleService;

public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Override
	public Page<Role> queryByPage(Page<Role> page, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", page.getOffset());
		map.put("pageSize", page.getPageSize());
		if(!StringUtils.isEmpty(name)) {
			map.put("name", "%"+name+"%");
		}
		int totalCount = roleMapper.selectCount(map);
		List<Role> list = roleMapper.selectByPage(map);
		page.pageData(list, totalCount);
		return page;
	}

	@Override
	@Transactional
	public void insertRole(Role role) throws NormalException {
		role.setCreateTime(new Date());
		roleMapper.insertSelective(role);
	}

	@Override
	@Transactional
	public void updateRole(Role role) throws NormalException {
		role.setUpdateTime(new Date());
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	@Transactional
	public void deleteRole(int roleId) throws NormalException {
		roleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	@Transactional
	public void modifyMenuRight(int roleId, List<Integer> menuIds) throws NormalException {
		if(null != menuIds && menuIds.size() > 0) {
			// 先删除已有权限
			RoleMenuExample example = new RoleMenuExample();
			example.createCriteria().andRoleIdEqualTo(roleId);
			roleMenuMapper.deleteByExample(example);
			// 再新增权限
			List<RoleMenu> list = new ArrayList<RoleMenu>();
			for(int menuId : menuIds) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuId);
				roleMenu.setCreateTime(new Date());
				list.add(roleMenu);
			}
			roleMenuMapper.insertBatch(list);
		}
	}

	@Override
	@Transactional
	public void addUser(int roleId, String userName) throws NormalException {
		// 先根据用户名查询用户

	}

	@Override
	@Transactional
	public void removeUser(int roleId, int userId) throws NormalException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Role> queryAll() {
		RoleExample example = new RoleExample();
		example.setOrderByClause("id desc");
		return roleMapper.selectByExample(example);
	}

}
