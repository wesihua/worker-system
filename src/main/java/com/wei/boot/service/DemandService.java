package com.wei.boot.service;

import com.wei.boot.model.Demand;

/**
 * 用工需求接口
 * @author xsy_2018
 *
 */
public interface DemandService {

	/**
	 * 新增
	 * @param demand
	 */
	public void saveDemand(Demand demand);
	
	
}
