package com.wei.boot.contant;

/**
 * 所有常量类存放处
 * @author weisihua
 * 2018年7月25日 下午2:53:58
 */
public interface GlobalConstant {

	int ILLEGAL_REQUEST = 1001;			// 非法请求
	int TOKEN_EXPIRED = 1002;			// token过期
	
	/**
	 * 工种级别
	 * @author weisihua
	 * 2018年8月8日 上午11:30:17
	 */
	interface JobTypeLevel {
		int FIRST = 1;					// 一级
		int SECOND = 2;					// 二级
	}
}
