package com.wei.boot.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Result;
import com.wei.boot.model.User;
import com.wei.boot.service.UserService;
import com.wei.boot.util.ToolsUtil;

@RestController("appUserController")
@RequestMapping("/app/user")
public class UserController {

	public static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查询用户信息
	 * @param request
	 * @return
	 */
	@GetMapping("/queryUserInfo")
	public Result queryUserInfo(HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			User user = userService.queryById(userId);
			result.setData(user);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
}
