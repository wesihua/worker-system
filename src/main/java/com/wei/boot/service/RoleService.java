package com.wei.boot.service;

import java.util.List;
import java.util.Map;

import com.wei.boot.exception.NormalException;
import com.wei.boot.model.Menu;
import com.wei.boot.model.Page;
import com.wei.boot.model.Role;
import com.wei.boot.model.User;

/**
 * 角色接口
 * @author weisihua
 * 2018年8月14日 下午4:09:12
 */
public interface RoleService {

	/**
	 * 分页查询角色
	 * @param name
	 * @return
	 */
	Page<Role> queryByPage(Map<String, Object> map);
	
	/**
	 * 查询该角色下所有的用户
	 * @param map
	 * @return
	 */
	Page<User> queryUserByPage(Map<String, Object> map);
	
	/**
	 * 查询所有角色
	 * @return
	 */
	List<Role> queryAll();
	/**
	 * 新增角色
	 * @throws NormalException
	 */
	void insertRole(Role role) throws NormalException;
	
	/**
	 * 更改角色
	 * @param role
	 * @throws NormalException
	 */
	void updateRole(Role role) throws NormalException;
	
	/**
	 * 删除角色，同时删除绑定关系
	 * @param roleId
	 * @throws NormalException
	 */
	void deleteRole(int roleId) throws NormalException;
	
	/**
	 * 更改菜单权限
	 * @param roleId
	 * @throws NormalException
	 */
	void modifyMenuRight(int roleId, List<Integer> menuIds) throws NormalException;
	
	/**
	 * 在该角色下增加用户
	 * @param userId
	 * @throws NormalException
	 */
	void addUser(int roleId, int userId) throws NormalException;
	
	/**
	 * 从该角色下删除用户
	 * @param userId
	 * @throws NormalException
	 */
	void removeUser(int userId) throws NormalException;
	
	/**
	 * 查询角色选中的菜单树
	 * @param userId
	 * @return
	 */
	List<Menu> queryMenuTreeByRoleId(int roleId);
}
