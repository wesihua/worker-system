package com.wei.boot.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.model.Result;
import com.wei.boot.model.User;
import com.wei.boot.service.UserService;
import com.wei.boot.util.DateUtils;
import com.wei.boot.util.JedisUtil;
import com.wei.boot.util.MD5Util;
import com.wei.boot.util.ToolsUtil;

import redis.clients.jedis.Jedis;

/**
 * 账号相关业务
 * @author weisihua
 * 2018年8月22日 下午3:02:58
 */
@RestController
@RequestMapping("/account")
public class AccountController {

	public static final Logger log = LoggerFactory.getLogger(AccountController.class);
	
	@Value("${token.secret}")
	private String secret;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 登录
	 * @param user
	 * @param flag 0:pc,1:app
	 * @return
	 */
	@GetMapping("/login")
	public Result login(User user, HttpServletResponse response) {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		if(StringUtils.isEmpty(user.getUserName())) {
			return Result.fail("用户名不能为空！");
		}
		if(StringUtils.isEmpty(user.getPassword())) {
			return Result.fail("密码不能为空！");
		}
		try {
			jedis = JedisUtil.getJedis();
			// 查询用户信息
			List<User> userList = userService.queryByUserName(user.getUserName());
			if(null == userList || userList.size() == 0) {
				return Result.fail("用户不存在！");
			}
			User currentUser = userList.get(0);
			// 比较密码
			if(!user.getPassword().equals(currentUser.getPassword())) {
				return Result.fail("密码输入不正确！");
			}
			// 登录成功，生产token并返回
			String token = MD5Util.MD5Upper32(secret+"_"+currentUser.getUserName()+"_"+currentUser.getPassword()+"_"+DateUtils.getCurTime());
			// 先删除老的token
			if(jedis.exists(GlobalConstant.RedisKey.KEY_TOKEN_PREFIX+currentUser.getId())) {
				String oldToken = jedis.get(GlobalConstant.RedisKey.KEY_TOKEN_PREFIX+currentUser.getId());
				if(jedis.exists(oldToken)) {
					jedis.del(oldToken);
				}
				jedis.del(GlobalConstant.RedisKey.KEY_TOKEN_PREFIX+currentUser.getId());
			}
			jedis.set(GlobalConstant.RedisKey.KEY_TOKEN_PREFIX+currentUser.getId(), token, "NX", "EX", 30*60);// 30分钟有效期，用来存放token
			jedis.set(token, currentUser.getId().toString(), "NX", "EX", 30*60);// 30分钟有效期，用来存放userId
			// 将token放入cookie中
			Cookie cookie = new Cookie(GlobalConstant.TOKEN_NAME, token);
			cookie.setPath("/");
			response.addCookie(cookie);
			
			result = Result.success(token);
		} catch (Exception e) {
			log.error("登录失败", e);
			result = Result.fail("登录失败！");
		}
		finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 退出登录
	 * @param userId
	 * @return
	 */
	@GetMapping("/logout")
	public Result logout(HttpServletRequest request) {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
			int userId = ToolsUtil.getUserId(request);
			if(userId != 0 && jedis.exists(GlobalConstant.RedisKey.KEY_TOKEN_PREFIX+userId)) {
				String token = jedis.get(GlobalConstant.RedisKey.KEY_TOKEN_PREFIX+userId);
				if(jedis.exists(token)) {
					jedis.del(token);
				}
				jedis.del(GlobalConstant.RedisKey.KEY_TOKEN_PREFIX+userId);
			}
		} catch (Exception e) {
			log.error("退出登录失败", e);
			//result = Result.fail("退出登录失败！");
		}
		finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 更改密码
	 * @param userId
	 * @param newPass
	 * @return
	 */
	@GetMapping("/changePass")
	public Result changePass(int userId, String newPass) {
		Result result = Result.SUCCESS;
		try {
			userService.changePass(userId, newPass);
		} catch (Exception e) {
			result = Result.fail(e);
		}
		return result;
	}
	/**
	 * 更改密码
	 * @param userId
	 * @param newPass
	 * @return
	 */
	@GetMapping("/app/changePass")
	public Result changePass(HttpServletRequest request, String newPass) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			userService.changePass(userId, newPass);
		} catch (Exception e) {
			result = Result.fail(e);
		}
		return result;
	}
}
