package com.wei.boot.service;

import java.util.List;

import com.wei.boot.model.Area;
import com.wei.boot.model.Dictionary;

public interface CommonService {

	/**
	 * 查询所有字典type
	 * 存放到redis中会用到
	 * @return
	 */
	List<String> queryAllDicTypes();
	
	/**
	 * 根据类型查字典
	 * @param type
	 * @return
	 */
	List<Dictionary> queryDicByType(String type);
	
	/**
	 * 查询所有省份
	 * @return
	 */
	List<Area> queryAllProvince();
	
	/**
	 * 根据parentCode查询地区
	 * @param parentCode
	 * @return
	 */
	List<Area> queryAreaByParentCode(String parentCode);
	
	/**
	 * 查询地区树，目前只查询两级
	 * @return
	 */
	List<Area> queryAreaTree(int parentCode);
	
	/**
	 * 查询字典text
	 * @param type
	 * @param code
	 * @return
	 */
	String queryDicText(String type, int code);
	
	
	Area queryAreaByCode(int code);
	
	/**
	 * 查询用户名字
	 * @param id
	 * @return
	 */
	String queryUserName(Integer id);
}
