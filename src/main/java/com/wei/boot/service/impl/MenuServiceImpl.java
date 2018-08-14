package com.wei.boot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.MenuMapper;
import com.wei.boot.mapper.RoleMenuMapper;
import com.wei.boot.model.Menu;
import com.wei.boot.model.MenuExample;
import com.wei.boot.model.RoleMenuExample;
import com.wei.boot.service.MenuService;
import com.wei.boot.util.JedisUtil;
import com.wei.boot.util.JsonUtil;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
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
	public List<Menu> queryTreeByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Menu> queryTreeByUserRoleId(int roleId) {
		// 先查询该角色下绑定的所有菜单id
		RoleMenuExample example = new RoleMenuExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		List<Integer> menuIds = roleMenuMapper.selectByExample(example).stream().map(roleMenu -> roleMenu.getRoleId()).collect(Collectors.toList());
		// 查询所有菜单树
		List<Menu> menus = JsonUtil.json2List(JedisUtil.getJedis().get(GlobalConstant.RedisKey.KEY_MENU), Menu.class);
		revalueMenuTree(menus, menuIds, "selected");
		return menus;
	}

	@Override
	public List<Menu> queryMenuTree(int parentId) {
		List<Menu> menus = queryByParentId(0);
		if(null != menus && menus.size() > 0) {
			menus.stream().forEach(menu -> menu.setChildren(queryMenuTree(menu.getId())));
		}
		return menus;
	}

	/**
	 * 根据parentId查询子集
	 * @param parentId
	 * @return
	 */
	private List<Menu> queryByParentId(int parentId) {
		List<Menu> list = new ArrayList<Menu>();
		MenuExample example = new MenuExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		list = menuMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 重新给菜单树赋 selected、show 字段的值
	 * @param menus 菜单树
	 * @param menuIds 关联的菜单id
	 * @param flag show/selected
	 * @return
	 */
	private void revalueMenuTree(List<Menu> menus, List<Integer> menuIds, String flag) {
		if(null != menuIds && menuIds.size() > 0) {
			for(Menu menu : menus) {
				if(menuIds.contains(menu.getId())) {
					if("show".equals(flag)) {
						menu.setShow(1);
					}
					else {
						menu.setSelected(1);
					}
				}
				if(null != menu.getChildren() && menu.getChildren().size() > 0) {
					revalueMenuTree(menu.getChildren(), menuIds, flag);
				}
			}
		}
	}
}
