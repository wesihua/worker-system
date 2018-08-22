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
import com.wei.boot.mapper.MenuMapper;
import com.wei.boot.mapper.UserMapper;
import com.wei.boot.model.Menu;
import com.wei.boot.model.MenuExample;
import com.wei.boot.model.Page;
import com.wei.boot.model.User;
import com.wei.boot.model.UserExample;
import com.wei.boot.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public List<Menu> queryUserMenu(int userId) {
		List<Integer> menuIds = userMapper.selectMenuIdByUserId(userId);
		List<Menu> menuList = queryByMenuIds(0, menuIds);
		return menuList;
	}
	
	/**
	 * 递归菜单树
	 * @param parentId
	 * @param menuIds
	 * @return
	 */
	private List<Menu> queryByMenuIds(int parentId, List<Integer> menuIds) {
		 MenuExample example = new MenuExample();
		 example.createCriteria().andParentIdEqualTo(parentId).andIdIn(menuIds);
		 List<Menu> menuList = menuMapper.selectByExample(example);
		 if(null != menuList && menuList.size() > 0) {
			 menuList.stream().forEach(each -> each.setChildren(queryByMenuIds(each.getId(), menuIds)));
		 }
		 return menuList;
	}

	@Override
	public Page<User> queryByPage(Page<User> page, User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", page.getOffset());
		map.put("pageSize", page.getPageSize());
		if(!StringUtils.isEmpty(user.getUserName())) {
			map.put("userName", "%"+user.getUserName()+"%");
		}
		if(!StringUtils.isEmpty(user.getRealName())) {
			map.put("realName", "%"+user.getRealName()+"%");
		}
		if(user.getRoleId() != 0) {
			map.put("roleId", user.getRoleId());
		}
		int totalCount = userMapper.selectCount(map);
		List<User> list = userMapper.selectByPage(map);
		page.pageData(list, totalCount);
		return page;
	}


	@Override
	@Transactional
	public void updateUser(User user) throws NormalException {
		// 先查询用户是否已存在
		if(StringUtils.isEmpty(user.getUserName())) {
			throw new NormalException("用户名不能为空！");
		}
		List<User> userList = queryByUserName(user.getUserName());
		if(null != userList && userList.size() > 0) {
			throw new NormalException("该用户名已存在！");
		}
		user.setUpdateTime(new Date());
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	@Transactional
	public void deleteUser(int userId) {
		userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	@Transactional
	public void changePass(int userId, String newPass) throws NormalException {
		User user = userMapper.selectByPrimaryKey(userId);
		if(null != user) {
			if(StringUtils.isEmpty(user.getPassword())) {
				throw new NormalException("请输入密码！");
			}
			if(newPass.equals(user.getPassword())) {
				throw new NormalException("该密码与原密码相同！");
			}
			User bean = new User();
			bean.setId(userId);
			bean.setPassword(newPass);
			bean.setUpdateTime(new Date());
			userMapper.updateByPrimaryKeySelective(bean);
		}
		
	}

	@Override
	@Transactional
	public void insertUser(User user) throws NormalException {
		// 先查询用户是否已存在
		if(StringUtils.isEmpty(user.getUserName())) {
			throw new NormalException("用户名不能为空！");
		}
		List<User> userList = queryByUserName(user.getUserName());
		if(null != userList && userList.size() > 0) {
			throw new NormalException("该用户名已存在！");
		}
		user.setCreateTime(new Date());
		userMapper.insertSelective(user);
	}

	@Override
	public List<User> queryByUserName(String userName) {
		UserExample example = new UserExample();
		example.createCriteria().andUserNameLike("%"+userName+"%");
		return userMapper.selectByExample(example);
	}

	@Override
	public User queryById(int userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

}
