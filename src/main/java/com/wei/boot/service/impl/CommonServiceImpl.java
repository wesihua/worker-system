package com.wei.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wei.boot.contant.GlobalConstant;
import com.wei.boot.mapper.AreaMapper;
import com.wei.boot.mapper.DictionaryMapper;
import com.wei.boot.model.Area;
import com.wei.boot.model.AreaExample;
import com.wei.boot.model.Dictionary;
import com.wei.boot.model.DictionaryExample;
import com.wei.boot.service.CommonService;

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

}
