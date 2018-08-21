package com.wei.boot.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.MenuMapper;
import com.wei.boot.model.Menu;
import com.wei.boot.model.MenuExample;
import com.wei.boot.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	@Transactional
	public void updateName(Menu menu) throws NormalException {

		if(StringUtils.isEmpty(menu.getName())) {
			throw new NormalException("菜单名称为空！");
		}
		menu.setUpdateTime(new Date());
		menuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	@Transactional
	public void deleteMenu(int menuId) throws NormalException {

		menuMapper.deleteByPrimaryKey(menuId);
	}

	@Override
	public List<Menu> queryMenuTree() {
		List<Menu> menus = queryByParentId(0);
		return menus;
	}

	/**
	 * 根据parentId查询子集
	 * @param parentId
	 * @return
	 */
	private List<Menu> queryByParentId(int parentId) {
		MenuExample example = new MenuExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<Menu> list = menuMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			list.stream().forEach(menu -> menu.setChildren(queryByParentId(menu.getId())));
		}
		return list;
	}
	
}
