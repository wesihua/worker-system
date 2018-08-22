package com.wei.boot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.RoleMapper;
import com.wei.boot.mapper.RoleMenuMapper;
import com.wei.boot.mapper.UserMapper;
import com.wei.boot.model.Menu;
import com.wei.boot.model.Page;
import com.wei.boot.model.Role;
import com.wei.boot.model.RoleExample;
import com.wei.boot.model.RoleMenu;
import com.wei.boot.model.RoleMenuExample;
import com.wei.boot.model.User;
import com.wei.boot.model.UserExample;
import com.wei.boot.service.RoleService;
import com.wei.boot.service.UserService;
import com.wei.boot.util.JedisUtil;
import com.wei.boot.util.JsonUtil;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Page<Role> queryByPage(Page<Role> page, Role role) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", page.getOffset());
		map.put("pageSize", page.getPageSize());
		if(!StringUtils.isEmpty(role.getName())) {
			map.put("name", "%"+role.getName()+"%");
		}
		int totalCount = roleMapper.selectCount(map);
		List<Role> list = roleMapper.selectByPage(map);
		if(null != list && list.size() > 0) {
			for(Role info : list) {
				UserExample example = new UserExample();
				example.createCriteria().andRoleIdEqualTo(info.getId());
				List<User> userList = userMapper.selectByExample(example);
				info.setUserCount(userList.size());
			}
		}
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
	public void addUser(int roleId, int userId) throws NormalException {
		// 更改用户
		User user = new User();
		user.setId(userId);
		user.setUpdateTime(new Date());
		user.setRoleId(roleId);
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	@Transactional
	public void removeUser(int userId) throws NormalException {
		User user = new User();
		user.setId(userId);
		user.setUpdateTime(new Date());
		user.setRoleId(0);
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public List<Role> queryAll() {
		RoleExample example = new RoleExample();
		example.setOrderByClause("id desc");
		return roleMapper.selectByExample(example);
	}

	@Override
	public List<Menu> queryMenuTreeByRoleId(int roleId) {
		// 先查询该角色下绑定的所有菜单id
		RoleMenuExample example = new RoleMenuExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		List<Integer> menuIds = roleMenuMapper.selectByExample(example).stream().map(roleMenu -> roleMenu.getRoleId()).collect(Collectors.toList());
		// 查询所有菜单树
		List<Menu> menus = JsonUtil.json2List(JedisUtil.getJedis().get(GlobalConstant.RedisKey.KEY_MENU), Menu.class);
		revalueMenuTree(menus, menuIds);
		return menus;
	}
	
	/**
	 * 重新给菜单树赋 selected 字段的值
	 * @param menus 菜单树
	 * @param menuIds 关联的菜单id
	 * @param flag show/selected
	 * @return
	 */
	private void revalueMenuTree(List<Menu> menus, List<Integer> menuIds) {
		if(null != menuIds && menuIds.size() > 0) {
			for(Menu menu : menus) {
				if(menuIds.contains(menu.getId())) {
					menu.setSelected(1);
				}
				if(null != menu.getChildren() && menu.getChildren().size() > 0) {
					revalueMenuTree(menu.getChildren(), menuIds);
				}
			}
		}
	}

	@Override
	public Page<User> queryUserByPage(Page<User> page, User user) {
		return userService.queryByPage(page, user);
	}
}
