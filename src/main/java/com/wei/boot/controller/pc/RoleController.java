package com.wei.boot.controller.pc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.Menu;
import com.wei.boot.model.Page;
import com.wei.boot.model.Result;
import com.wei.boot.model.Role;
import com.wei.boot.model.User;
import com.wei.boot.model.excel.ExcelData;
import com.wei.boot.model.excel.ExcelRow;
import com.wei.boot.service.RoleService;
import com.wei.boot.util.DateUtils;
import com.wei.boot.util.ExcelUtil;

@RestController
@RequestMapping("/role")
public class RoleController {

	public static final Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 角色列表
	 * @param page
	 * @param role
	 * @return
	 */
	@GetMapping("/list")
	public Result list(Page<Role> page, Role role) {
		Result result = Result.SUCCESS;
		try {
			Page<Role> data = roleService.queryByPage(page, role);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 导出
	 * @param response
	 * @param role
	 */
	@PostMapping("/export")
	public void export(HttpServletResponse response, Role role) {
		try {
			Page<Role> page = new Page<Role>();
			page.setPageSize(20000);
			List<Role> list = roleService.queryByPage(page, role).getData();
			if(null != list && list.size() > 0) {
				ExcelRow headers = ExcelUtil.excelHeaders("角色名称","包含账号数量","创建时间");
				ExcelData data = new ExcelData();
				for(Role info : list) {
					ExcelRow row = new ExcelRow();
					row.add(info.getName());
					row.add(info.getUserCount());
					row.add(DateUtils.formatDate(info.getCreateTime()));
					data.add(row);
				}
				ExcelUtil.exportExcel(headers, data, "角色信息.xlsx", response);
			}
		} catch (Exception e) {
			log.error("导出失败", e);
		}
	}
	
	/**
	 * 查询角色下的用户列表
	 * @param page
	 * @param user
	 * @return
	 */
	@GetMapping("/userlist")
	public Result userlist(Page<User> page, User user) {
		Result result = Result.SUCCESS;
		try {
			Page<User> data = roleService.queryUserByPage(page, user);
			result.setData(data);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 查询所有角色
	 * @return
	 */
	@GetMapping("/queryAll")
	public Result queryAll() {
		Result result = Result.SUCCESS;
		try {
			List<Role> data = roleService.queryAll();
			result.setData(data);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@PostMapping("/addRole")
	public Result addRole(Role role) {
		Result result = Result.SUCCESS;
		try {
			roleService.insertRole(role);
		} catch (Exception e) {
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 更改角色
	 * @param role
	 * @return
	 */
	@PostMapping("/updateRole")
	public Result updateRole(Role role) {
		Result result = Result.SUCCESS;
		try {
			roleService.updateRole(role);
		} catch (Exception e) {
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	@PostMapping("/deleteRole")
	public Result deleteRole(int roleId) {
		Result result = Result.SUCCESS;
		try {
			roleService.deleteRole(roleId);
		} catch (Exception e) {
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 修改角色菜单权限
	 * @param map
	 * @return
	 */
	@PostMapping("/modifyMenuRight")
	public Result modifyMenuRight(@RequestBody Map<String, Object> map) {
		Result result = Result.SUCCESS;
		try {
			int roleId = (int) map.get("roleId");
			@SuppressWarnings("unchecked")
			List<Integer> menuIds = (List<Integer>) map.get("menuIds");
			roleService.modifyMenuRight(roleId, menuIds);
		} catch (Exception e) {
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 角色下新增用户
	 * @param roleId
	 * @param userId
	 * @return
	 */
	@PostMapping("/addUser")
	public Result addUser(int roleId, int userId) {
		Result result = Result.SUCCESS;
		try {
			roleService.addUser(roleId, userId);
		} catch (Exception e) {
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 角色下移除用户
	 * @param userId
	 * @return
	 */
	@PostMapping("/removeUser")
	public Result removeUser(int userId) {
		Result result = Result.SUCCESS;
		try {
			roleService.removeUser(userId);
		} catch (Exception e) {
			result = Result.fail(e);
		}
		return result;
	}
	
	/**
	 * 查询角色下的菜单树-包含选中状态
	 * @param roleId
	 * @return
	 */
	@GetMapping("/queryMenuTreeByRoleId")
	public Result queryMenuTreeByRoleId(int roleId) {
		Result result = Result.SUCCESS;
		try {
			List<Menu> list = roleService.queryMenuTreeByRoleId(roleId);
			result.setData(list);
		} catch (Exception e) {
			log.error("查询失败", e);
			result = Result.fail(e);
		}
		return result;
	}
}
