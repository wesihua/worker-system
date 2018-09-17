package com.wei.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.mapper.AreaMapper;
import com.wei.boot.mapper.DictionaryMapper;
import com.wei.boot.model.Area;
import com.wei.boot.model.AreaExample;
import com.wei.boot.model.Dictionary;
import com.wei.boot.model.DictionaryExample;
import com.wei.boot.service.CommonService;
import com.wei.boot.util.JedisUtil;
import com.wei.boot.util.JsonUtil;

import redis.clients.jedis.Jedis;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private DictionaryMapper dictionaryMapper;
	
	@Autowired
	private AreaMapper areaMapper;
	
	@Override
	public List<String> queryAllDicTypes() {
		
		return dictionaryMapper.selectAllDicTypes();
	}

	@Override
	public List<Dictionary> queryDicByType(String type) {
		
		DictionaryExample example = new DictionaryExample();
		example.createCriteria().andTypeEqualTo(type);
		List<Dictionary> list = dictionaryMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Area> queryAllProvince() {
		
		AreaExample example = new AreaExample();
		example.createCriteria().andTypeEqualTo(GlobalConstant.AreaType.PROVINCE);
		return areaMapper.selectByExample(example);
	}

	@Override
	public List<Area> queryAreaByParentCode(String parentCode) {
		
		AreaExample example = new AreaExample();
		example.createCriteria().andParentCodeEqualTo(Integer.parseInt(parentCode));
		return areaMapper.selectByExample(example);
	}

	@Override
	public List<Area> queryAreaTree(int parentCode) {
		
		List<Area> areas = queryAreaByParentCode(parentCode+"");
		if(null != areas && areas.size() > 0) {
			//provinces.stream().forEach(area -> area.setChildren(queryAreaTree(area.getCode())));
			for(Area area : areas) {
				if(area.getType() != 2) {
					area.setChildren(queryAreaTree(area.getCode()));
				}
			}
		}
		return areas;
	}

	@Override
	public String queryDicText(String type, int code) {
		String text = "";
		if(StringUtils.isEmpty(type)) {
			return text;
		}
		// 先从redis中查询
		Jedis jedis = JedisUtil.getJedis();
		String jsonStr = jedis.get(type);
		// 如果为空则从数据库中查询
		if(!StringUtils.isEmpty(jsonStr)) {
			List<Dictionary> list = JsonUtil.json2List(jsonStr, Dictionary.class);
			for(Dictionary dic : list) {
				if(code == dic.getCode()) {
					text = dic.getName();
					break;
				}
			}
		}
		else {
			DictionaryExample example = new DictionaryExample();
			example.createCriteria().andTypeEqualTo(type).andCodeEqualTo(code);
			List<Dictionary> dicList = dictionaryMapper.selectByExample(example);
			if(dicList != null && dicList.size() > 0) {
				text = dicList.get(0).getName();
			}
		}
		jedis.close();
		return text;
	}

	@Override
	public Area queryAreaByCode(int code) {
		AreaExample example = new AreaExample();
		example.createCriteria().andCodeEqualTo(code);
		List<Area> list = areaMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		else {
			return null;
		}
	}

}
