package com.wei.boot.controller.pc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Menu;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.User;
import com.wei.boot.model.excel.ExcelData;
import com.wei.boot.model.excel.ExcelRow;
import com.wei.boot.service.UserService;
import com.wei.boot.util.DateUtils;
import com.wei.boot.util.ExcelUtil;
import com.wei.boot.util.ToolsUtil;

/**
 * 用户controller
 * @author weisihua
 * 2018年8月22日 上午10:34:30
 */
@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户列表
	 * @param page
	 * @param user
	 * @return
	 */
	@GetMapping("/list")
	public Result list(Page<User> page, User user) {
		Result result = Result.SUCCESS;
		try {
			Page<User> data = userService.queryByPage(page, user);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 导出用户
	 * @param response
	 * @param user
	 */
	@PostMapping("/export")
	public void export(HttpServletResponse response, User user) {
		try {
			Page<User> page = new Page<User>();
			page.setPageSize(20000);
			List<User> list = userService.queryByPage(page, user).getData();
			if(null != list && list.size() > 0) {
				ExcelRow headers = ExcelUtil.excelHeaders("账号名称","密码","所属人员","所属角色","创建时间");
				ExcelData data = new ExcelData();
				for(User info : list) {
					ExcelRow row = new ExcelRow();
					row.add(info.getUserName());
					row.add(info.getPassword());
					row.add(info.getRealName());
					row.add(info.getRoleName());
					row.add(DateUtils.formatDate(info.getCreateTime()));
					data.add(row);
				}
				ExcelUtil.exportExcel(headers, data, "用户信息.xlsx", response);
			}
		} catch (Exception e) {
			log.error("导出失败", e);
		}
	}
	
	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	@GetMapping("/queryByUserName")
	public Result queryByUserName(String userName) {
		Result result = Result.SUCCESS;
		try {
			List<User> list = userService.queryByUserName(userName);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 获取登录的用户信息
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
	
	/**
	 * 获取登录的用户信息
	 * @param request
	 * @return
	 */
	@GetMapping("/queryById")
	public Result queryById(int userId) {
		Result result = Result.SUCCESS;
		try {
			User user = userService.queryById(userId);
			result.setData(user);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 登录后查询用户菜单，用于展示
	 * @return
	 */
	@GetMapping("/queryUserMenu")
	public Result queryUserMenu(HttpServletRequest request) {
		Result result = Result.SUCCESS;
		try {
			int userId = ToolsUtil.getUserId(request);
			List<Menu> menuList = userService.queryUserMenu(userId);
			result.setData(menuList);
		} catch (Exception e) {
			log.error("查询用户菜单失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	@GetMapping("/addUser")
	public Result addUser(User user) {
		Result result = Result.SUCCESS;
		try {
			userService.insertUser(user);
		} catch (Exception e) {
			log.error("新增用户失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	@GetMapping("/updateUser")
	public Result updateUser(User user) {
		Result result = Result.SUCCESS;
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			log.error("更新用户失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	@GetMapping("/deleteUser")
	public Result deleteUser(int userId) {
		Result result = Result.SUCCESS;
		try {
			userService.deleteUser(userId);
		} catch (Exception e) {
			log.error("删除用户失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
}
