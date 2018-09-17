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
import com.wei.boot.model.Menu;
import com.wei.boot.service.CommonService;
import com.wei.boot.service.MenuService;
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
	
	@Autowired
	private MenuService menuService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		Jedis jedis = JedisUtil.getJedis();
		// 查询所有字典项,放入redis中
		log.info("将字典数据放入redis...");
		List<String> types = commonService.queryAllDicTypes();
		for(String type : types) {
			List<Dictionary> dicList = commonService.queryDicByType(type);
			jedis.set(type, JsonUtil.bean2Json(dicList));
		}
		// 查询地区树，放入redis中
		log.info("将地区树放入redis...");
		List<Area> areas = commonService.queryAreaTree(0);
		jedis.set(GlobalConstant.RedisKey.KEY_AREA_TREE, JsonUtil.bean2Json(areas));
		// 查询所有省份
		log.info("将所有省份放入redis...");
		List<Area> province = commonService.queryAllProvince();
		jedis.set(GlobalConstant.RedisKey.KEY_PROVINCE, JsonUtil.bean2Json(province));
		// 查询所有菜单树，放入redis
		log.info("将所有菜单放入redis...");
		List<Menu> menus = menuService.queryMenuTree();
		jedis.set(GlobalConstant.RedisKey.KEY_MENU, JsonUtil.bean2Json(menus));
		jedis.close();
		log.info("配置信息放入redis结束！");
	}

}
