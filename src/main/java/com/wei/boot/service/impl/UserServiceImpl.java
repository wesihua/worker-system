package com.wei.boot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wei.boot.exception.NormalException;
import com.wei.boot.mapper.UserMapper;
import com.wei.boot.model.Menu;
import com.wei.boot.model.Page;
import com.wei.boot.model.User;
import com.wei.boot.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<Menu> queryTreeByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> queryByPage(Map<String, Object> map) {
		Page<User> page = new Page<User>();
		if(map.containsKey("pageNumber")) {
			page.setPageNumber(Integer.parseInt((String) map.get("pageNumber")));
		}
		if(map.containsKey("pageSize")) {
			page.setPageSize(Integer.parseInt((String) map.get("pageSize")));
		}
		map.put("offset", page.getOffset());
		map.put("pageSize", page.getPageSize());
		if(map.containsKey("userName")) {
			map.put("userName", "%"+(String) map.get("userName")+"%");
		}
		if(map.containsKey("realName")) {
			map.put("realName", "%"+(String) map.get("realName")+"%");
		}
		if(map.containsKey("roleId")) {
			map.put("roleId", Integer.parseInt((String) map.get("roleId")));
		}
		int totalCount = userMapper.selectCount(map);
		List<User> list = userMapper.selectByPage(map);
		page.pageData(list, totalCount);
		return page;
	}


	@Override
	public void updateUser(User user) throws NormalException {
		user.setUpdateTime(new Date());
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void deleteUser(int userId) {
		userMapper.deleteByPrimaryKey(userId);
	}

	@Override
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
	public void insertUser(User user) throws NormalException {
		user.setCreateTime(new Date());
		userMapper.insertSelective(user);
	}

}
