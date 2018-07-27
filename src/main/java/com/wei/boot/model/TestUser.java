package com.wei.boot.model;

import java.lang.reflect.Field;
import java.util.Date;

public class TestUser {

	private int age;
	
	private String userName;
	
	private Date birthDay;

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public static void main(String[] args) throws Exception {
		TestUser user = new TestUser();
		user.setAge(100);
		user.setUserName("weisihua");
		
		Field field = user.getClass().getDeclaredField("age");
		String fieldType = field.getGenericType().toString();
		field.setAccessible(true);
		field.set(user, 99);
		
		System.out.println(fieldType+"    "+user.getAge());
	}
}
