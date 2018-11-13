package com.wei.boot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.model.TestUser;
import com.wei.boot.model.excel.ExcelData;
import com.wei.boot.model.excel.ExcelRow;
import com.wei.boot.util.ExcelUtil;

@RestController
public class DemoController {

	@RequestMapping("/hello")
	public String home() {
		return "hello world";
	}
	
	@RequestMapping("/zxctest")
	public String home2() {
		return "hello zxc";
	}
	
	@RequestMapping("/download")
	public void download(HttpServletResponse response) {
		List<TestUser> users = new ArrayList<TestUser>();
		TestUser user1 = new TestUser();
		user1.setAge(10);
		user1.setBirthDay(new Date());
		user1.setUserName("张三");
		TestUser user2 = new TestUser();
		user2.setAge(20);
		user2.setBirthDay(new Date());
		user2.setUserName("李四");
		TestUser user3 = new TestUser();
		user3.setAge(30);
		user3.setBirthDay(new Date());
		user3.setUserName("王五");
		
		users.add(user1);
		users.add(user2);
		users.add(user3);
		// 拼接表头
		ExcelRow headers = ExcelUtil.excelHeaders("姓名","年龄","出生日期");
		// 组装数据
		ExcelData data = new ExcelData();
		for(TestUser user : users) {
			ExcelRow row = new ExcelRow();
			row.add(user.getUserName());
			row.add(user.getAge());
			row.add(user.getBirthDay());
			data.add(row);
		}
		try {
			ExcelUtil.exportExcel(headers, data, "一个简单的excel.xlsx", response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
