package com.wei.boot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.mapper.AreaMapper;
import com.wei.boot.mapper.DictionaryMapper;
import com.wei.boot.mapper.UserMapper;
import com.wei.boot.model.Area;
import com.wei.boot.model.AreaExample;
import com.wei.boot.model.Dictionary;
import com.wei.boot.model.DictionaryExample;
import com.wei.boot.model.User;
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
	
	@Autowired
	private UserMapper userMapper;
	
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
	public List<Area> queryAreaTree() {
		AreaExample example = new AreaExample();
		List<Area> allArea = areaMapper.selectByExample(example);
		List<Area> provice = recurseCityArea(allArea, 0);
		return provice;
	}

	@Override
	public String queryDicText(String type, int code) {
		String text = "";
		if(StringUtils.isEmpty(type)) {
			return text;
		}
		// 先从redis中查询
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
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
		} finally {
			jedis.close();
		}
		
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

	@Override
	public String queryUserName(Integer id) {

		String userName = "";
		if(Objects.isNull(id)) {
			return userName;
		}
		// 先从redis中查询
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
			String userNameStr = jedis.get(GlobalConstant.RedisKey.KEY_USER_NAME + id);
			// 如果为空则从数据库中查询
			if (!StringUtils.isEmpty(userNameStr)) {
				userName = userNameStr;
			} else {
				User user = userMapper.selectByPrimaryKey(id);
				if (Objects.nonNull(user)) {
					jedis.set(GlobalConstant.RedisKey.KEY_USER_NAME + id, user.getRealName());
					userName = user.getRealName();
				}
			}
		} finally {
			jedis.close();
		}
		return userName;
	}

	@Override
	public int queryParentByCode(int code) {
		AreaExample example = new AreaExample();
		example.createCriteria().andCodeEqualTo(code);
		return areaMapper.selectByExample(example).get(0).getParentCode();
	}

	@Override
	public List<Area> queryAreaTreeNew() {
		AreaExample example = new AreaExample();
		List<Area> allArea = areaMapper.selectByExample(example);
		List<Area> provice = recurseArea(allArea, 0);
		return provice;
	}

	private List<Area> findChildrenByParent(List<Area> allArea, int parentCode){
		List<Area> areaList = new ArrayList<>();
		for(Area area : allArea) {
			if(area.getParentCode() == parentCode) {
				areaList.add(area);
			}
		}
		return areaList;
	}
	
	private List<Area> recurseArea(List<Area> allArea, int parentCode) {
		List<Area> children = findChildrenByParent(allArea, parentCode);
		for(Area area : children) {
			if(area.getType() != 3) {
				area.setChildren(recurseArea(allArea, area.getCode()));
			}
		}
		return children;
	}
	private List<Area> recurseCityArea(List<Area> allArea, int parentCode) {
		List<Area> children = findChildrenByParent(allArea, parentCode);
		for(Area area : children) {
			if(area.getType() == 1) {
				area.setChildren(recurseCityArea(allArea, area.getCode()));
			}
		}
		return children;
	}

	@Override
	public List<Map<String, Object>> queryAreaSelectTree(List<Area> provinceList) {
		//List<Area> provinceList = queryAreaTreeNew();
		List<Map<String, Object>> provinceInfoList = new ArrayList<>();
		for(Area province : provinceList) {
			Map<String, Object> provinceInfo = new HashMap<>();
			provinceInfo.put("id", province.getCode());
			provinceInfo.put("text", province.getName());
			provinceInfo.put("showSearchInput", true);
			provinceInfo.put("showSearchInputInDropdown", false);
			// 拼接城市子菜单
			Map<String, List<Map<String, Object>>> provinceSubmenu = new HashMap<>();
			List<Map<String, Object>> cityInfoList = new ArrayList<>();
			List<Area> cityList = province.getChildren();
			for(Area city : cityList) {
				Map<String, Object> cityInfo = new HashMap<>();
				cityInfo.put("id", city.getCode());
				cityInfo.put("text", city.getName());
				if(!CollectionUtils.isEmpty(city.getChildren())) {
					// 拼接县区子菜单
					Map<String, List<Map<String, Object>>> citySubmenu = new HashMap<>();
					List<Map<String, Object>> countyInfoList = new ArrayList<>();
					List<Area> countyList = city.getChildren();
					for(Area county : countyList) {
						Map<String, Object> countyInfo = new HashMap<>();
						countyInfo.put("id", county.getCode());
						countyInfo.put("text", county.getName());
						countyInfoList.add(countyInfo);
					}
					citySubmenu.put("items", countyInfoList);
					cityInfo.put("submenu", citySubmenu);
				}
				cityInfoList.add(cityInfo);
			}
			provinceSubmenu.put("items", cityInfoList);
			provinceInfo.put("submenu", provinceSubmenu);
			provinceInfoList.add(provinceInfo);
		}
		return provinceInfoList;
	}
}
