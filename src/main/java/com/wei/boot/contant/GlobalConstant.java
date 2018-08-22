package com.wei.boot.contant;

/**
 * 所有常量类存放处
 * @author weisihua
 * 2018年7月25日 下午2:53:58
 */
public interface GlobalConstant {

	int ILLEGAL_REQUEST = 1001;				// 非法请求
	int TOKEN_EXPIRED = 1002;				// token过期
	
	String TOKEN_NAME = "Authorization";
	
	interface RedisKey {
		String KEY_AREA_TREE = "yx_area_tree";	// 地区树形结构key
		String KEY_PROVINCE = "yx_province";	// 省份key
		String KEY_MENU = "yx_menu_tree";		// 菜单树key
		String KEY_TOKEN_PREFIX = "yx_token_";	// 用来存放token的key值。格式为：yx_token_ + userId
	}
	/**
	 * 工种级别
	 * @author weisihua
	 * 2018年8月8日 上午11:30:17
	 */
	interface JobTypeLevel {
		int FIRST = 1;					// 一级
		int SECOND = 2;					// 二级
	}
	
	/**
	 * 地区级别
	 * @author weisihua
	 * 2018年8月9日 下午4:17:35
	 */
	interface AreaType {
		int PROVINCE = 1;				// 省
		int CITY = 2;					// 市
		int COUNTY = 3;					// 县
	}
	
	/**
	 * 用户所属平台
	 * @author weisihua
	 * 2018年8月21日 下午7:37:27
	 */
	interface UserScope {
		int ALL = 0;
		int PC = 1;
		int APP = 2;
	}
}
