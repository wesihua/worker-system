package com.wei.boot.service;

import java.util.List;

import com.wei.boot.exception.NormalException;
import com.wei.boot.model.Menu;

/**
 * 菜单service
 * @author weisihua
 * 2018年8月14日 下午3:27:24
 */
public interface MenuService {

	/**
	 * 更改菜单名称
	 * @param name
	 * @throws NormalException
	 */
	void updateName(Menu menu) throws NormalException;
	
	/**
	 * 删除菜单 【维护人员使用，不会对客户暴露】
	 * @param menuId
	 * @throws NormalException
	 */
	void deleteMenu(int menuId) throws NormalException;
	
	/**
	 * 查询全部菜单树
	 * @param parentId
	 * @return
	 */
	List<Menu> queryMenuTree();
}
