package com.wei.boot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.model.Area;
import com.wei.boot.model.Dictionary;
import com.wei.boot.model.Result;
import com.wei.boot.service.CommonService;
import com.wei.boot.util.JedisUtil;
import com.wei.boot.util.JsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import redis.clients.jedis.Jedis;

/**
 * 地区、字典等常量controller
 * @author weisihua
 * 2018年8月9日 下午2:06:42
 */

@Api(value = "地区、字典等常量控制器")
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
	@ApiOperation(value = "根据type查询字典项目",notes = "type字段说明")
	@GetMapping(value = "/queryDicByType")
	public Result queryDicByType(@ApiParam(value = "字典type",required = true) @RequestParam String type) {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
			// 先从redis中查询
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
		finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 查询所有字段
	 * @param types 以,分隔的type 如：nation,sex
	 * @return
	 */
	@GetMapping(value = "/queryDicByTypes")
	public Result queryDicByTypes(String types) {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
			String[] typeArray = types.split(",");
			Map<String, List<Dictionary>> map = Maps.newHashMap();
			for(String type : typeArray) {
				// 先从redis中查询
				String jsonStr = jedis.get(type);
				// 如果为空则从数据库中查询
				if(StringUtils.isEmpty(jsonStr)) {
					log.info("从数据库中查询字典项："+type);
					List<Dictionary> list = commonService.queryDicByType(type);
					map.put(type, list);
				}
				else {
					map.put(type, JsonUtil.json2List(jsonStr, Dictionary.class));
				}
			}
			result.setData(map);
		} catch (Exception e) {
			log.error("查询字典项目失败", e);
			result = Result.fail("查询字典项目失败");
		}
		finally {
			jedis.close();
		}
		return result;
	}
	
	@GetMapping("/queryAllProvince")
	@ApiOperation(value = "查询所有省份")
	public Result queryAllProvince() {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		try {
			// 先从redis中查询
			jedis = JedisUtil.getJedis();
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
		finally {
			jedis.close();
		}
		return result;
	}
	
	
	
	@GetMapping("/queryAreaTree")
	@ApiOperation(value = "查询地区树")
	public Result queryAreaTree() {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		try {
			// 先从redis中查询
			jedis = JedisUtil.getJedis();
			String jsonStr = jedis.get(GlobalConstant.RedisKey.KEY_AREA_CITY_TREE);
			// 如果为空则从数据库中查询
			if(StringUtils.isEmpty(jsonStr)) {
				log.info("从数据库中查询地区树");
				List<Area> province = commonService.queryAreaTree();
				result.setData(province);
			}
			else {
				result.setData(JsonUtil.json2List(jsonStr, Area.class));
			}
		} catch (Exception e) {
			log.error("查询地区树失败", e);
			result = Result.fail("查询地区树失败");
		}
		finally {
			jedis.close();
		}
		return result;
	}
	
	@GetMapping("/queryAllAreaTree")
	@ApiOperation(value = "查询全地区树")
	public Result queryAllAreaTree() {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		try {
			// 先从redis中查询
			jedis = JedisUtil.getJedis();
			String jsonStr = jedis.get(GlobalConstant.RedisKey.KEY_AREA_ALL_TREE);
			// 如果为空则从数据库中查询
			if(StringUtils.isEmpty(jsonStr)) {
				log.info("从数据库中查询地区树");
				List<Area> province = commonService.queryAreaTreeNew();
				result.setData(province);
			}
			else {
				result.setData(JsonUtil.json2List(jsonStr, Area.class));
			}
		} catch (Exception e) {
			log.error("查询地区树失败", e);
			result = Result.fail("查询地区树失败");
		}
		finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 自定义拼接的地区json
	 * @return
	 */
	@GetMapping("/queryCityAreaSelectTree")
	public Result queryCityAreaSelectTree() {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		try {
			// 先从redis中查询
			jedis = JedisUtil.getJedis();
			String jsonStr = jedis.get(GlobalConstant.RedisKey.KEY_AREA_CITY_JSON_TREE);
			// 如果为空则从数据库中查询
			if(StringUtils.isEmpty(jsonStr)) {
				log.info("从数据库中查询地区树");
				List<Area> areaList = commonService.queryAreaTree();
				List<Map<String, Object>> province = commonService.queryAreaSelectTree(areaList);
				result.setData(province);
			}
			else {
				//result.setData(JSONArray.fromObject(jsonStr));
				result.setData(jsonStr);
			}
		} catch (Exception e) {
			log.error("查询地区树失败", e);
			result = Result.fail("查询地区树失败");
		}
		finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 自定义拼接的地区json
	 * @return
	 */
	@GetMapping("/queryAllAreaSelectTree")
	public Result queryAllAreaSelectTree() {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		try {
			// 先从redis中查询
			jedis = JedisUtil.getJedis();
			String jsonStr = jedis.get(GlobalConstant.RedisKey.KEY_AREA_JSON_TREE);
			// 如果为空则从数据库中查询
			if(StringUtils.isEmpty(jsonStr)) {
				log.info("从数据库中查询地区树");
				List<Area> areaList = commonService.queryAreaTreeNew();
				List<Map<String, Object>> province = commonService.queryAreaSelectTree(areaList);
				result.setData(province);
			}
			else {
				//result.setData(JSONArray.fromObject(jsonStr));
				result.setData(jsonStr);
			}
		} catch (Exception e) {
			log.error("查询地区树失败", e);
			result = Result.fail("查询地区树失败");
		}
		finally {
			jedis.close();
		}
		return result;
	}
	
	/**
	 * 根据code查询上级code
	 * @param code
	 * @return
	 */
	@GetMapping("/queryParentCodeByCode")
	public Result queryParentCodeByCode(String code) {
		Result result = Result.SUCCESS;
		try {
			// 如果为空则从数据库中查询
			if(!StringUtils.isEmpty(code)) {
				Map<String, Object> map = new HashMap<String, Object>();
				int parentCode = commonService.queryParentByCode(Integer.parseInt(code));
				List<Area> areaList = commonService.queryAreaByParentCode(parentCode+"");
				map.put("parentCode", parentCode);
				map.put("children", areaList);
				result.setData(map);
			}
			else {
				result = Result.ERROR;
			}
		} catch (Exception e) {
			log.error("查询上级code失败", e);
			result = Result.fail("查询上级code失败");
		}
		return result;
	}
	
	@GetMapping("/queryAreaByParentCode")
	@ApiOperation(value = "根据parentCode查询地区集合")
	public Result queryAreaByParentCode(@ApiParam(value="地区父code",required = true) @RequestParam String parentCode) {
		Result result = Result.SUCCESS;
		Jedis jedis = null;
		try {
			// 先从redis中查询
			jedis = JedisUtil.getJedis();
			String jsonStr = jedis.get(GlobalConstant.RedisKey.KEY_AREA_ALL_TREE);
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
		finally {
			jedis.close();
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
