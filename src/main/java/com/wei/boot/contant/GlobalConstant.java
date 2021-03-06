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
		String KEY_AREA_CITY_TREE = "yx_area_city_tree";	// 地区树形结构key
		String KEY_AREA_ALL_TREE = "yx_area_all_tree";	// 地区树形结构key
		String KEY_AREA_CITY_JSON_TREE = "yx_area_city_json_tree";	// 二级地区树形自定义json结构
		String KEY_AREA_JSON_TREE = "yx_area_json_tree";	// 地区树形自定义json结构
		String KEY_PROVINCE = "yx_province";	// 省份key
		String KEY_MENU = "yx_menu_tree";		// 菜单树key
		String KEY_TOKEN_PREFIX = "yx_token_";	// 用来存放token的key值。格式为：yx_token_ + userId
		String KEY_USER_NAME = "yx_user_name_";
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
	
	/**
	 * 人才信息来源
	 * @author weisihua
	 * 2018年9月6日 下午5:39:10
	 */
	interface Source {
		int APP = 0;
		int PC = 1;
		int IMPORT = 2;
		int QRCODE = 3;
	}
	
	/**
	 * 字典type
	 * @author weisihua
	 * 2018年9月6日 上午11:33:03
	 */
	interface DictionaryType {
		String GENDER = "gender";				// 性别
		String NATION = "nation";				// 民族
		String MARITAL_STATUS = "marital_status";// 婚姻状态
		String DEGREE = "degree";				// 学历
		String WORK_STATUS = "work_status";		// 工作状态
		String WORKER_SOUCE = "worker_souce";	// 人才信息来源
		String NIGHT_WORK = "night_work";		// 是否接受夜班
		String EXPECT_SALARY = "expect_salary";	// 期望薪资
		String LANGUAGE_LEVEL = "language_level";//外语能力
		String DEMAND_STATE= "demand_state"; // 需求单状态
		String DEGREE_DEMAND= "degree_demand"; // 学历要求
		String GENDER_DEMAND= "gender_demand"; // 性别要求
		String ORDER_CONFIRM_STATE = "order_confirm_state"; // 订单确认状态
	}
	
	interface DemandState {
		Integer PENDING = 0;
		Integer PROCESSING = 1;
		Integer SIGNING = 2;
		Integer CLOSE = 3;
	}
	
	interface OrderWorkerState {
		// 分配状态
		Integer ASSIGN = 0;
		// 签约状态
		Integer SIGNING = 1;
	}
	
	interface OrderConfirmState {
		// 待确认
		Integer WAITING = 0;
		// 驳回
		Integer REJECT = 1;
		// 已确认
		Integer SUCCESS = 2;
	}
	
	
	interface UserRole {
		// 超级管理员
		Integer ADMIN = 1;
		
	}
}
