package com.wei.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.model.Area;
import com.wei.boot.model.Dictionary;
import com.wei.boot.model.Result;
import com.wei.boot.service.CommonService;
import com.wei.boot.util.JedisUtil;
import com.wei.boot.util.JsonUtil;

import redis.clients.jedis.Jedis;

/**
 * 地区、字典等常量controller
 * @author weisihua
 * 2018年8月9日 下午2:06:42
 */
@RestController
@RequestMapping("/common")
public class CommonController {

	public static final Logger log = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 根据type查询字典项目
	 * @param type
	 * @return
	 */
	@RequestMapping("/queryDicByType")
	public Result queryDicByType(String type) {
		Result result = Result.SUCCESS;
		try {
			// 先从redis中查询
			Jedis jedis = JedisUtil.getJedis();
			String jsonStr = jedis.get(type);
			// 如果为空则从数据库中查询
			if(StringUtils.isEmpty(jsonStr)) {
				log.info("从数据库中查询字典项："+type);
				List<Dictionary> list = commonService.queryDicByType(type);
				result.setData(list);
			}
			else {
				result.setData(JsonUtil.json2List(jsonStr, Dictionary.class));
			}
		} catch (Exception e) {
			log.error("查询字典项目失败", e);
			result = Result.fail("查询字典项目失败");
		}
		return result;
	}
	
	/**
	 * 查询所有省份
	 * @param type
	 * @return
	 */
	@RequestMapping("/queryAllProvince")
	public Result queryAllProvince() {
		Result result = Result.SUCCESS;
		try {
			// 先从redis中查询
			Jedis jedis = JedisUtil.getJedis();
			String jsonStr = jedis.get(GlobalConstant.RedisKey.KEY_PROVINCE);
			// 如果为空则从数据库中查询
			if(StringUtils.isEmpty(jsonStr)) {
				log.info("从数据库中查询所有省份");
				List<Area> province = commonService.queryAllProvince();
				result.setData(province);
			}
			else {
				result.setData(JsonUtil.json2List(jsonStr, Area.class));
			}
		} catch (Exception e) {
			log.error("查询省份失败", e);
			result = Result.fail("查询省份失败");
		}
		return result;
	}
	
	
	/**
	 * 查询地区树
	 * @param type
	 * @return
	 */
	@RequestMapping("/queryAreaTree")
	public Result queryAreaTree() {
		Result result = Result.SUCCESS;
		try {
			// 先从redis中查询
			Jedis jedis = JedisUtil.getJedis();
			String jsonStr = jedis.get(GlobalConstant.RedisKey.KEY_AREA_TREE);
			// 如果为空则从数据库中查询
			if(StringUtils.isEmpty(jsonStr)) {
				log.info("从数据库中查询地区树");
				List<Area> province = commonService.queryAreaTree(0);
				result.setData(province);
			}
			else {
				result.setData(JsonUtil.json2List(jsonStr, Area.class));
			}
		} catch (Exception e) {
			log.error("查询地区树失败", e);
			result = Result.fail("查询地区树失败");
		}
		return result;
	}
	
	
	/**
	 * 根据parentCode查询地区集合
	 * @param type
	 * @return
	 */
	@RequestMapping("/queryAreaByParentCode")
	public Result queryAreaByParentCode(String parentCode) {
		Result result = Result.SUCCESS;
		try {
			// 先从redis中查询
			Jedis jedis = JedisUtil.getJedis();
			String jsonStr = jedis.get(GlobalConstant.RedisKey.KEY_AREA_TREE);
			// 如果为空则从数据库中查询
			if(StringUtils.isEmpty(jsonStr)) {
				log.info("从数据库中查询地区子集合");
				List<Area> areas = commonService.queryAreaByParentCode(parentCode);
				result.setData(areas);
			}
			else {
				List<Area> areas = JsonUtil.json2List(jsonStr, Area.class);
				result.setData(getChildrenByParentCode(parentCode, areas));
			}
			
		} catch (Exception e) {
			log.error("查询地区子集失败", e);
			result = Result.fail("查询地区子集失败");
		}
		return result;
	}
	
	/**
	 * 递归出子集
	 * @param parentCode
	 * @param areas
	 * @return
	 */
	private List<Area> getChildrenByParentCode(String parentCode, List<Area> areas) {
		List<Area> list = new ArrayList<Area>();
		if(null != areas && areas.size() > 0) {
			for(Area area : areas) {
				if(String.valueOf(area.getCode()).equals(parentCode)) {
					list = area.getChildren();
					break;
				}
				else {
					list = getChildrenByParentCode(parentCode, area.getChildren());
				}
			}
		}
		return list;
	}
}
