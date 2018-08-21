package com.wei.boot.service;

import java.util.List;
import java.util.Map;

import com.wei.boot.model.Menu;
import com.wei.boot.model.Page;
import com.wei.boot.model.User;

public interface UserService {

	/**
	 * 查询用户菜单树,用于登录后菜单显示
	 * @param userId
	 * @return
	 */
	List<Menu> queryTreeByUserId(int userId);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	Page<User> queryByPage(Map<String, Object> map);
	
	/**
	 * 新增用户
	 * @param user
	 */
	void insertUser(User user);
	
	/**
	 * 更改用户
	 * @param user
	 */
	void updateUser(User user);
	
	/**
	 * 删除用户
	 * @param userId
	 */
	void deleteUser(int userId);
	
	/**
	 * 更改密码
	 * @param user
	 */
	void changePass(User user);
	
}
