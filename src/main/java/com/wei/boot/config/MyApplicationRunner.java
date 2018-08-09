package com.wei.boot.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.model.Area;
import com.wei.boot.model.Dictionary;
import com.wei.boot.service.CommonService;
import com.wei.boot.util.JedisUtil;
import com.wei.boot.util.JsonUtil;

import redis.clients.jedis.Jedis;

/**
 * 系统启动后执行一些操作，eg:将地区，字典放入redis中
 * @author weisihua
 * 2018年8月9日 下午4:37:16
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

	public static final Logger log = LoggerFactory.getLogger(MyApplicationRunner.class);
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		log.info("项目启动，将字典数据、地区树形结构放入redis开始");
		Jedis jedis = JedisUtil.getJedis();
		// 查询所有字典项,放入redis中
		List<String> types = commonService.queryAllDicTypes();
		for(String type : types) {
			List<Dictionary> dicList = commonService.queryDicByType(type);
			jedis.set(type, JsonUtil.bean2Json(dicList));
		}
		// 查询地区树，放入redis中
		List<Area> areas = commonService.queryAreaTree(0);
		jedis.set(GlobalConstant.RedisKey.KEY_AREA_TREE, JsonUtil.bean2Json(areas));
		// 查询所有省份
		List<Area> province = commonService.queryAllProvince();
		jedis.set(GlobalConstant.RedisKey.KEY_PROVINCE, JsonUtil.bean2Json(province));
		log.info("配置信息放入redis结束！");
	}

}
