package com.searun.orderoperation.application;

/**
 * 网络相关的常量 ，如服务器地址，接口action等 NetWork.java
 * 
 * @author zhazhaobao
 */
public class NetWork {

//	public static String SERVER_URL = "http://192.168.2.36:6666/Searun_platform/";
	 public static String SERVER_URL = ApplicationPool.SERVER_URL;

	/**
	 * 获取附近车源信息
	 */
	public static String FIND_AROUND_CAR_ACTION = SERVER_URL
			+ "ep/orderApps/vehicle_requestNearVehicle.action";
	public static final int FIND_AROUND_CAR_ERROR = 1001;
	public static final int FIND_AROUND_CAR_OK = 1002;

	/**
	 * 刷新获取最新的车源信息
	 */
	public static String REFRESH_AROUND_CAR_ACTION = SERVER_URL
			+ "ep/orderApps/vehicle_refreshNearVehicle.action";
	public static final int REFRESH_AROUND_CAR_ERROR = 1003;
	public static final int REFRESH_AROUND_CAR_OK = 1004;

	/**
	 * 上传地理位置信息
	 */
	public static String SUBMIT_PERSONAL_POSITION_ACTION = SERVER_URL
			+ "orderApps/getPdaUserLocation.action";
	public static final int SUBMIT_PERSONAL_POSITION_ERROR = 1005;
	public static final int SUBMIT_PERSONAL_POSITION_OK = 1006;

	/**
	 * 获取附近好友
	 */
	public static String FIND_AROUND_FIRENDS_ACTION = SERVER_URL
			+ "ep/orderApps/public_requestNearFriend.action?";
	public static final int FIND_AROUND_FIRENDS_ERROR = 1007;
	public static final int FIND_AROUND_FIRENDS_OK = 1008;

	/**
	 * 登录
	 */
	public static String lOGIN_ACTION = SERVER_URL
			+ "orderApps/checkPdaUserLogin.action";
	public static final int LOGIN_ERROR = 1009;
	public static final int LOGIN_OK = 1010;

	/**
	 * 提交注册账号信息
	 */
	public static String SUBMIT_REGISTER_INFO_ACTION = SERVER_URL
			+ "orderApps/checkPDAUser.action";
	public static final int SUBMIT_REGISTER_INFO_ERROR = 1011;
	public static final int SUBMIT_REGISTER_INFO_OK = 1012;

	/**
	 * 提交注册账号验证码
	 */
	public static String SUBMIT_REGISTER_AUTHCODE_ACTION = SERVER_URL
			+ "orderApps/checkPDAVerifyCode.action";
	public static final int SUBMIT_REGISTER_AUTHCODE_ERROR = 1013;
	public static final int SUBMIT_REGISTER_AUTHCODE_OK = 1014;

	/**
	 * 提交注册账号密码
	 */
	public static String SUBMIT_REGISTER_PASSWORD_ACTION = SERVER_URL
			+ "orderApps/regestPDAUser.action";
	public static final int SUBMIT_REGISTER_PASSWORD_ERROR = 1015;
	public static final int SUBMIT_REGISTER_PASSWORD_OK = 1016;

	/**
	 * 找回注册账号验证码
	 */
	public static String SUBMIT_RETRIEVEE_AUTHCODE_ACTION = SERVER_URL + "";
	public static final int SUBMIT_RETRIEVEE_AUTHCODE_ERROR = 1017;
	public static final int SUBMIT_RETRIEVEE_AUTHCODE_OK = 1018;

	/**
	 * 重新设置注册账号密码
	 */
	public static String SUBMIT_RETRIEVEE_SETTING_ACTION = SERVER_URL + "";
	public static final int SUBMIT_RETRIEVEE_SETTING_ERROR = 1019;
	public static final int SUBMIT_RETRIEVEE_SETTING_OK = 1020;

	/**
	 * 收藏
	 */
	public static String COLLECT_SOURCE_ACTION = SERVER_URL + "";
	public static final int COLLECT_SOURCE_ERROR = 1021;
	public static final int COLLECT_SOURCE_OK = 1022;

	/**
	 * 提交评价
	 */
	public static String SUBMIT_EVALUATE_ACTION = SERVER_URL + "";
	public static final int SUBMIT_EVALUATE_ERROR = 1023;
	public static final int SUBMIT_EVALUATE_OK = 1024;

	/**
	 * 发布车源
	 */
	public static String PUBLISH_CAR_SOURCE_ACTION = SERVER_URL
			+ "orderApps/releaseRouteByPda.action";
	public static final int PUBLISH_CAR_SOURCE_ERROR = 1025;
	public static final int PUBLISH_CAR_SOURCE_OK = 1026;

	/**
	 * 发布货源
	 */
	public static String PUBLISH_GOODS_SOURCE_ACTION = SERVER_URL
			+ "orderApps/releaseGoodsByPda.action";
	public static final int PUBLISH_GOODS_SOURCE_ERROR = 1027;
	public static final int PUBLISH_GOODS_SOURCE_OK = 1028;

	/**
	 * 发布专线
	 */
	public static String PUBLISH_LINE_SOURCE_ACTION = SERVER_URL + "";
	public static final int PUBLISH_LINE_SOURCE_ERROR = 1029;
	public static final int PUBLISH_LINE_SOURCE_OK = 1030;

	/**
	 * 天气
	 */
	public static String WEATHER_SOURCE_ACTION = "http://v.juhe.cn/weather/index?";
	public static final int WEATHER_SOURCE_ERROR = 1031;
	public static final int WEATHER_SOURCE_OK = 1032;

	/**
	 * 附近加油站
	 */
	public static String GAS_STATION_SOURCE_ACTION = "http://apis.juhe.cn/oil/local?";
	public static final int GAS_STATION_SOURCE_ERROR = 1033;
	public static final int GAS_STATION_SOURCE_OK = 1034;

	/**
	 * 搜索货源
	 */
	public static String SEARCH_GOODS_ACTION = SERVER_URL
			+ "orderApps/searchGoodsByPda.action";
	public static final int SEACH_GOODS_ERROR = 1035;
	public static final int SEACH_GOODS_OK = 1036;

	/**
	 * 搜索车源
	 */
	public static String SEARCH_CAR_SOURCE_ACTION = SERVER_URL
			+ "orderApps/searchRouteByPda.action";
	public static final int SEACH_CAR_SOURCE_ERROR = 1037;
	public static final int SEACH_CAR_SOURCE_OK = 1038;

	/**
	 * 添加新司机
	 */
	public static String ADD_DRIVER_ACTION = SERVER_URL
			+ "orderApps/registDriverByPda.action";
	public static final int ADD_DRIVER_ERROR = 1039;
	public static final int ADD_DRIVER_OK = 1040;

	/**
	 * 获取司机信息
	 */
	public static String GET_DRIVER_INFO_ACTION = SERVER_URL
			+ "orderApps/searchDriverByPda.action";
	public static final int GET_DRIVER_INFO_ERROR = 1041;
	public static final int GET_DRIVER_INFO_OK = 1042;

	/**
	 * 获取司机信息
	 */
	public static String DELETE_DRIVER_INFO_ACTION = SERVER_URL
			+ "orderApps/deleteDriver.action";
	public static final int DELETE_DRIVER_INFO_ERROR = 1043;
	public static final int DELETE_DRIVER_INFO_OK = 1044;

	/**
	 * 获取司机信息
	 */
	public static String EDIT_DRIVER_INFO_ACTION = SERVER_URL
			+ "orderApps/updateDriverByPda.action";
	public static final int EDIT_DRIVER_INFO_ERROR = 1045;
	public static final int EDIT_DRIVER_INFO_OK = 1046;

	/**
	 * 添加新车辆
	 */
	public static String ADD_CAR_ACTION = SERVER_URL
			+ "orderApps/addPdaCars.action";
	public static final int ADD_CAR_ERROR = 1047;
	public static final int ADD_CAR_OK = 1048;

	/**
	 * 搜索车辆
	 */
	public static String SEARCH_CAR_ACTION = SERVER_URL
			+ "orderApps/searchCarByPda.action";
	public static final int SEARCH_CAR_ERROR = 1049;
	public static final int SEARCH_CAR_OK = 1050;

	/**
	 * 修改车辆信息
	 */
	public static String UPDATE_CAR_ACTION = SERVER_URL
			+ "orderApps/updateCarsByPda.action";
	public static final int UPDATE_CAR_ERROR = 1051;
	public static final int UPDATE_CAR_OK = 1052;

	/**
	 * 删除车辆信息
	 */
	public static String DELETE_CAR_ACTION = SERVER_URL
			+ "orderApps/deleteCarByPda.action";
	public static final int DELETE_CAR_ERROR = 1053;
	public static final int DELETE_CAR_OK = 1054;

	/**
	 * 获取用户信息
	 */
	public static String GET_USERINFO_ACTION = SERVER_URL
			+ "orderApps/getmemberInfoByPda.action";
	public static final int GET_USERINFO_ERROR = 1054;
	public static final int GET_USERINFO_OK = 1055;

	/**
	 * 修改用户信息
	 */
	public static String UPDATE_USERINFO_ACTION = SERVER_URL
			+ "orderApps/updateMemberInfoByPda.action";
	public static final int UPDATE_USERINFO_ERROR = 1056;
	public static final int UPDATE_USERINFO_OK = 1057;

	/**
	 * 获取用户订单信息
	 */
	public static String GET_ORDER_INFO_ACTION = SERVER_URL
			+ "orderApps/getOrdersByPda.action";
	public static final int GET_ORDER_INFO_ERROR = 1058;
	public static final int GET_ORDER_INFO_OK = 1059;

	/**
	 * 生成订单
	 */
	public static String CREATE_ORDER_ACTION = SERVER_URL
			+ "orderApps/createOderByPda.action";
	public static final int CREATE_ORDER_ERROR = 1060;
	public static final int CREATE_ORDER_OK = 1061;

	/**
	 * 检测更新
	 */
	public static String CHECK_UPDATE_ACTION = SERVER_URL
			+ "orderApps/checkPdaVersionInfo.action";
	public static final int CHECK_UPDATE_ERROR = 1062;
	public static final int CHECK_UPDATE_OK = 1063;

	/**
	 * 获取货源信息
	 */
	public static String GET_GOODS_INFO_ACTION = SERVER_URL
			+ "orderApps/getUserGoodsByPda.action";
	public static final int GET_GOODS_INFO_ERROR = 1064;
	public static final int GET_GOODS_INFO_OK = 1065;

	/**
	 * 修改货源信息
	 */
	public static String EDIT_GOODS_INFO_ACTION = SERVER_URL
			+ "orderApps/editGoodsByPda.action";
	public static final int EDIT_GOODS_INFO_ERROR = 1066;
	public static final int EDIT_GOODS_INFO_OK = 1067;

	/**
	 * 下单
	 */
	public static String PLACE_AN_ORDER_ACTION = SERVER_URL
			+ "orderApps/createOderByPda.action";
	public static final int PLACE_AN_ORDER_ERROR = 1068;
	public static final int PLACE_AN_ORDER_OK = 1069;

	/**
	 * 下单
	 */
	public static String ORDER_COMPLETE_ACTION = SERVER_URL
			+ "orderApps/createOderByPda.action";
	public static final int ORDER_COMPLETE_ERROR = 1070;
	public static final int ORDER_COMPLETE_OK = 1071;

	/**
	 * 修改订单价格
	 */
	public static String EDIT_ORDER_PRICE_ACTION = SERVER_URL
			+ "orderApps/changeOrderPriceByPda.action";
	public static final int EDIT_ORDER_PRICE_ERROR = 1072;
	public static final int EDIT_ORDER_PRICE_OK = 1073;

	/**
	 * 获取订单详细详细
	 */
	public static String GET_ORDER_DETAIL_ACTION = SERVER_URL
			+ "orderApps/findOrderInfoByPda.action";
	public static final int GET_ORDER_DETAIL_ERROR = 1074;
	public static final int GET_ORDER_DETAIL_OK = 1075;

	/**
	 * 修改订单状态
	 */
	public static String CHANGE_ORDER_STATUS_ACTION = SERVER_URL
			+ "orderApps/changeOrderStatusByPda.action";
	public static final int CHANGE_ORDER_STATUS_ERROR = 1076;
	public static final int CHANGE_ORDER_STATUS_OK = 1077;

	/**
	 * 获取当前订单提示信息
	 */
	public static String GET_CURRENT_ORDER_MESSAGE_ACTION = SERVER_URL
			+ "orderApps/getStatisticalInfo.action";
	public static final int GET_CURRENT_ORDER_MESSAGE_ERROR = 1078;
	public static final int GET_CURRENT_ORDER_MESSAGE_OK = 1079;

	/**
	 * 删除货源
	 */
	public static String DELETE_GOODS_ACTION = SERVER_URL
			+ "orderApps/delGoodsByUser.action";
	public static final int DELETE_GOODS_ERROR = 1080;
	public static final int DELETE_GOODS_OK = 1081;

	/**
	 * 删除线路
	 */
	public static String DELETE_LINE_ACTION = SERVER_URL
			+ "orderApps/deleteRouteByPda.action";
	public static final int DELETE_LINE_ERROR = 1082;
	public static final int DELETE_LINE_OK = 1083;

	/**
	 * 获取订单操作日志
	 */
	public static String GET_ORDER_OPERATION_DATA_ACTION = SERVER_URL
			+ "orderApps/getOrderTracesByPda.action";
	public static final int GET_ORDER_OPERATION_DATA_ERROR = 1084;
	public static final int GET_ORDER_OPERATION_DATA_OK = 1085;

	/**
	 * 修改线路
	 */
	public static String UPDATA_LINE_ACTION = SERVER_URL
			+ "orderApps/updateRouteByPda.action";
	public static final int UPDATA_LINE_ERROR = 1086;
	public static final int UPDATA_LINE_OK = 1087;

	/**
	 * 短信接口
	 */
	public static String SEND_SMS_ACTION = SERVER_URL
			+ "orderApps/sendSmsByPda.action";
	public static final int SEND_SMS_ERROR = 1086;
	public static final int SEND_SMS_OK = 1087;

	/**
	 * 实名认证
	 */
	public static String CERTIFICATION_ACTION = SERVER_URL
			+ "orderApps/memberAuthByPda.action";
	public static final int CERTIFICATION_ERROR = 1088;
	public static final int CERTIFICATION_OK = 1089;

	/**
	 * 获取实名认证信息
	 */
	public static String GET_CERTIFICATION_INFO_ACTION = SERVER_URL
			+ "orderApps/getMemeberAuthInfo.action";
	public static final int GET_CERTIFICATION_INFO_ERROR = 1090;
	public static final int GET_CERTIFICATION_INFO_OK = 1091;

	/**
	 * 获取企业认证信息
	 */
	public static String GET_COMPANY_AUTHENTICATION_INFO_ACTION = SERVER_URL
			+ "orderApps/getCompanyAuthInfo.action";
	public static final int GET_COMPANY_AUTHENTICATION_INFO_ERROR = 1092;
	public static final int GET_COMPANY_AUTHENTICATION_INFO_OK = 1093;

	/**
	 * 企业认证
	 */
	public static String COMPANY_AUTHENTICATION_ACTION = SERVER_URL
			+ "orderApps/companyAuthByPda.action";
	public static final int COMPANY_AUTHENTICATION_ERROR = 1094;
	public static final int COMPANY_AUTHENTICATION_OK = 1095;

	public static String ORDER_TRACKING_ACTION = SERVER_URL
			+ "baidumap/baidumap_toMap.action";

	/**
	 * 会员充值
	 */
	public static String RECHARGE_ACTION = SERVER_URL
			+ "orderApps/doCharge.action";
	public static final int RECHARGE_ERROR = 1096;
	public static final int RECHARGE_OK = 1097;

	/**
	 * 获取充值账号信息
	 */
	public static String GET_RECHARGE_ACCOUNT_ACTION = SERVER_URL
			+ "orderApps/getMemberAccount.action";
	public static final int GET_RECHARGE_ACCOUNT_ERROR = 1098;
	public static final int GET_RECHARGE_ACCOUNT_OK = 1099;

	/**
	 * 获取充值账号信息
	 */
	public static String GET_ACCOUNT_INFO_ACTION = SERVER_URL
			+ "orderApps/getAccounts.action";
	public static final int GET_ACCOUNT_INFO_ERROR = 1100;
	public static final int GET_ACCOUNT_INFO_OK = 1101;

	/**
	 * 添加充值账号
	 */
	public static String ADD_ACCOUNT_ACTION = SERVER_URL
			+ "orderApps/addAccountByPda.action";
	public static final int ADD_ACCOUNT_ERROR = 1102;
	public static final int ADD_ACCOUNT_OK = 1103;

	/**
	 * 删除充值账号
	 */
	public static String DELETE_ACCOUNT_ACTION = SERVER_URL
			+ "orderApps/delAccountByPda.action";
	public static final int DELETE_ACCOUNT_ERROR = 1104;
	public static final int DELETE_ACCOUNT_OK = 1105;

	/**
	 * 修改充值账号
	 */
	public static String UPDATE_ACCOUNT_ACTION = SERVER_URL
			+ "orderApps/updateAccountByPda.action";
	public static final int UPDATE_ACCOUNT_ERROR = 1106;
	public static final int UPDATE_ACCOUNT_OK = 1107;

	/**
	 * 获取结算单
	 */
	public static String GET_ACCOUNT_SETTLEMENT_ACTION = SERVER_URL
			+ "orderApps/getAccountSettles.action";
	public static final int GET_ACCOUNT_SETTLEMENT_ERROR = 1108;
	public static final int GET_ACCOUNT_SETTLEMENT_OK = 1109;

	/**
	 * 获取结算单明细
	 */
	public static String GET_ACCOUNT_SETTLEMENT_DETAIL_ACTION = SERVER_URL
			+ "orderApps/getAccountSettleDettles.action";
	public static final int GET_ACCOUNT_SETTLEMENT_DETAIL_ERROR = 1108;
	public static final int GET_ACCOUNT_SETTLEMENT_DETAIL_OK = 1109;

	/**
	 * 获取结算单明细
	 */
	public static String GET_DEAL_MANAGER_ACTION = SERVER_URL
			+ "orderApps/getAccountLogs.action";
	public static final int GET_DEAL_MANAGER_ERROR = 1110;
	public static final int GET_DEAL_MANAGER_OK = 1111;

	/**
	 * 获取订单结算 计算规则
	 */
	public static String GET_CALCULATE_INFO_ACTION = SERVER_URL
			+ "orderApps/toPayByPda.action";
	public static final int GET_CALCULATE_INFO_ERROR = 1112;
	public static final int GET_CALCULATE_INFO_OK = 1113;

	/**
	 * 付款。
	 */
	public static String SUBMIT_PAYMENT_ACTION = SERVER_URL
			+ "orderApps/payByPda.action";
	public static final int SUBMIT_PAYMENT_ERROR = 1114;
	public static final int SUBMIT_PAYMENT_OK = 1115;

	/**
	 * 作业信息--调度单
	 */
	public static String GET_ORDER_SYSTEM_INFO_ACTION = SERVER_URL
			+ "orderApps/searchCurrentDis.action";
	public static final int GET_ORDER_SYSTEM_INFO_ERROR = 1116;
	public static final int GET_ORDER_SYSTEM_INFO_OK = 1117;

	/**
	 * 作业信息---订单信息
	 */
	public static String GET_ORDER_SYSTEM_ORDER_INFO_ACTION = SERVER_URL
			+ "orderApps/searchLogOrders.action";
	public static final int GET_ORDER_SYSTEM_ORDER_INFO_ERROR = 1118;
	public static final int GET_ORDER_SYSTEM_ORDER_INFO_OK = 1119;

	/**
	 * 作业信息---订单信息 历史信息
	 */
	public static String GET_ORDER_SYSTEM_HISTORY_ACTION = SERVER_URL
			+ "orderApps/searchDispatches.action";
	public static final int GET_ORDER_SYSTEM_HISTORY_ERROR = 1120;
	public static final int GET_ORDER_SYSTEM_HISTORY_OK = 1121;

	/**
	 * 作业信息---订单信息 历史信息
	 */
	public static String SUBMIT_ORDER_PHOTO_ACTION = SERVER_URL
			+ "orderApps/uploadReceipt.action";
	public static final int SUBMIT_ORDER_PHOTO_ERROR = 1122;
	public static final int SUBMIT_ORDER_PHOTO_OK = 1123;

	/**
	 * 找回密码。提交密码
	 */
	public static String SUBMIT_RETRIVEV_PASSWORD_ACTION = SERVER_URL
			+ "orderApps/setPassword.action";
	public static final int SUBMIT_RETRIVEV_PASSWORD_ERROR = 1124;
	public static final int SUBMIT_RETRIVEV_PASSWORD_OK = 1125;

	/**
	 * 作业信息---当前作业信息
	 */
	public static String GET_ORDER_SYSTEM_CURRENT_INFO_ACTION = SERVER_URL
			+ "orderApps/getDispatchInfo.action";
	public static final int GET_ORDER_SYSTEM_CURRENT_INFO_ERROR = 1126;
	public static final int GET_ORDER_SYSTEM_CURRENT_INFO_OK = 1127;

	/**
	 * 签到
	 */
	public static String SIGN_IN_ACTION = SERVER_URL
			+ "orderApps/changeVehicleStatus.action";
	public static final int SIGN_IN_ERROR = 1128;
	public static final int SIGN_IN_OK = 1129;

	/**
	 * 签到，发车等操作
	 */
	public static String CHECK_STATUS_ACTION = SERVER_URL
			+ "orderApps/checkVehicleStatus.action";
	public static final int CHECK_STATUS_ERROR = 1130;
	public static final int CHECK_STATUS_OK = 1131;

	/**
	 * 到货
	 */
	public static String ARRIVAL_GOODS_ACTION = SERVER_URL
			+ "orderApps/arrivalVehicleGoods.action";
	public static final int ARRIVAL_GOODS_ERROR = 1131;
	public static final int ARRIVAL_GOODS_OK = 1132;

	/**
	 * 获取操作单状态
	 */
	public static String GET_STATUS_ACTION = SERVER_URL
			+ "orderApps/getLastestVehicleStatus.action";
	public static final int GET_STATUS_ERROR = 1133;
	public static final int GET_STATUS_OK = 1134;

	/**
	 * 上传订单照片
	 */
	public static String SUBMIT_ORDER_PHOTO2_ACTION = SERVER_URL
			+ "orderApps/uploadOrder.action";
	public static final int SUBMIT_ORDER_PHOTO2_ERROR = 1135;
	public static final int SUBMIT_ORDER_PHOTO2_OK = 1136;

	/**
	 * 获取充值账号信息
	 */
	public static String GET_ATTACHMENTS_ACTION = SERVER_URL
			+ "orderApps/getAttachments.action";
	public static final int GET_ATTACHMENTS_ERROR = 1137;
	public static final int GET_ATTACHMENTS_OK = 1138;

	public static void setServerIp(String url) {
		SERVER_URL = url;
		FIND_AROUND_CAR_ACTION = SERVER_URL
				+ "ep/orderApps/vehicle_requestNearVehicle.action";
		REFRESH_AROUND_CAR_ACTION = SERVER_URL
				+ "ep/orderApps/vehicle_refreshNearVehicle.action";
		SUBMIT_PERSONAL_POSITION_ACTION = SERVER_URL
				+ "orderApps/getPdaUserLocation.action";
		FIND_AROUND_FIRENDS_ACTION = SERVER_URL
				+ "ep/orderApps/public_requestNearFriend.action?";
		lOGIN_ACTION = SERVER_URL + "orderApps/checkPdaUserLogin.action";
		SUBMIT_REGISTER_INFO_ACTION = SERVER_URL
				+ "orderApps/checkPDAUser.action";
		SUBMIT_REGISTER_AUTHCODE_ACTION = SERVER_URL
				+ "orderApps/checkPDAVerifyCode.action";
		SUBMIT_REGISTER_PASSWORD_ACTION = SERVER_URL
				+ "orderApps/regestPDAUser.action";
		SUBMIT_RETRIEVEE_AUTHCODE_ACTION = SERVER_URL + "";
		COLLECT_SOURCE_ACTION = SERVER_URL + "";
		SUBMIT_EVALUATE_ACTION = SERVER_URL + "";
		PUBLISH_CAR_SOURCE_ACTION = SERVER_URL
				+ "orderApps/releaseRouteByPda.action";
		PUBLISH_GOODS_SOURCE_ACTION = SERVER_URL
				+ "orderApps/releaseGoodsByPda.action";
		PUBLISH_LINE_SOURCE_ACTION = SERVER_URL + "";
		WEATHER_SOURCE_ACTION = "http://v.juhe.cn/weather/index?";
		GAS_STATION_SOURCE_ACTION = "http://apis.juhe.cn/oil/local?";
		SEARCH_GOODS_ACTION = SERVER_URL + "orderApps/searchGoodsByPda.action";
		SEARCH_CAR_SOURCE_ACTION = SERVER_URL
				+ "orderApps/searchRouteByPda.action";
		ADD_DRIVER_ACTION = SERVER_URL + "orderApps/registDriverByPda.action";
		GET_DRIVER_INFO_ACTION = SERVER_URL
				+ "orderApps/searchDriverByPda.action";
		DELETE_DRIVER_INFO_ACTION = SERVER_URL
				+ "orderApps/deleteDriver.action";
		EDIT_DRIVER_INFO_ACTION = SERVER_URL
				+ "orderApps/updateDriverByPda.action";
		ADD_CAR_ACTION = SERVER_URL + "orderApps/addPdaCars.action";
		SEARCH_CAR_ACTION = SERVER_URL + "orderApps/searchCarByPda.action";
		UPDATE_CAR_ACTION = SERVER_URL + "orderApps/updateCarsByPda.action";
		DELETE_CAR_ACTION = SERVER_URL + "orderApps/deleteCarByPda.action";
		GET_USERINFO_ACTION = SERVER_URL
				+ "orderApps/getmemberInfoByPda.action";
		UPDATE_USERINFO_ACTION = SERVER_URL
				+ "orderApps/updateMemberInfoByPda.action";
		GET_ORDER_INFO_ACTION = SERVER_URL + "orderApps/getOrdersByPda.action";
		CREATE_ORDER_ACTION = SERVER_URL + "orderApps/createOderByPda.action";
		CHECK_UPDATE_ACTION = SERVER_URL
				+ "orderApps/checkPdaVersionInfo.action";
		GET_GOODS_INFO_ACTION = SERVER_URL
				+ "orderApps/getUserGoodsByPda.action";
		PLACE_AN_ORDER_ACTION = SERVER_URL + "orderApps/createOderByPda.action";
		ORDER_COMPLETE_ACTION = SERVER_URL + "orderApps/createOderByPda.action";
		EDIT_ORDER_PRICE_ACTION = SERVER_URL
				+ "orderApps/changeOrderPriceByPda.action";
		GET_ORDER_DETAIL_ACTION = SERVER_URL
				+ "orderApps/findOrderInfoByPda.action";
		CHANGE_ORDER_STATUS_ACTION = SERVER_URL
				+ "orderApps/changeOrderStatusByPda.action";
		GET_CURRENT_ORDER_MESSAGE_ACTION = SERVER_URL
				+ "orderApps/getStatisticalInfo.action";
		DELETE_GOODS_ACTION = SERVER_URL + "orderApps/delGoodsByUser.action";
		DELETE_LINE_ACTION = SERVER_URL + "orderApps/deleteRouteByPda.action";
		GET_ORDER_OPERATION_DATA_ACTION = SERVER_URL
				+ "orderApps/getOrderTracesByPda.action";
		UPDATA_LINE_ACTION = SERVER_URL + "orderApps/updateRouteByPda.action";
		SEND_SMS_ACTION = SERVER_URL + "orderApps/sendSmsByPda.action";
		CERTIFICATION_ACTION = SERVER_URL + "orderApps/memberAuthByPda.action";
		GET_CERTIFICATION_INFO_ACTION = SERVER_URL
				+ "orderApps/getMemeberAuthInfo.action";
		GET_COMPANY_AUTHENTICATION_INFO_ACTION = SERVER_URL
				+ "orderApps/getCompanyAuthInfo.action";
		COMPANY_AUTHENTICATION_ACTION = SERVER_URL
				+ "orderApps/companyAuthByPda.action";
		ORDER_TRACKING_ACTION = SERVER_URL + "baidumap/baidumap_toMap.action";
		RECHARGE_ACTION = SERVER_URL + "orderApps/doCharge.action";
		GET_RECHARGE_ACCOUNT_ACTION = SERVER_URL
				+ "orderApps/getMemberAccount.action";
		GET_ACCOUNT_INFO_ACTION = SERVER_URL + "orderApps/getAccounts.action";
		ADD_ACCOUNT_ACTION = SERVER_URL + "orderApps/addAccountByPda.action";
		DELETE_ACCOUNT_ACTION = SERVER_URL + "orderApps/delAccountByPda.action";
		UPDATE_ACCOUNT_ACTION = SERVER_URL
				+ "orderApps/updateAccountByPda.action";
		GET_ACCOUNT_SETTLEMENT_ACTION = SERVER_URL
				+ "orderApps/getAccountSettles.action";
		GET_ACCOUNT_SETTLEMENT_DETAIL_ACTION = SERVER_URL
				+ "orderApps/getAccountSettleDettles.action";
		GET_DEAL_MANAGER_ACTION = SERVER_URL
				+ "orderApps/getAccountLogs.action";
		GET_CALCULATE_INFO_ACTION = SERVER_URL + "orderApps/toPayByPda.action";
		SUBMIT_PAYMENT_ACTION = SERVER_URL + "orderApps/payByPda.action";
		GET_ORDER_SYSTEM_INFO_ACTION = SERVER_URL
				+ "orderApps/searchCurrentDis.action";
		GET_ORDER_SYSTEM_ORDER_INFO_ACTION = SERVER_URL
				+ "orderApps/searchLogOrders.action";
		GET_ORDER_SYSTEM_HISTORY_ACTION = SERVER_URL
				+ "orderApps/searchDispatches.action";
		SUBMIT_ORDER_PHOTO_ACTION = SERVER_URL
				+ "orderApps/uploadReceipt.action";
		SUBMIT_RETRIVEV_PASSWORD_ACTION = SERVER_URL
				+ "orderApps/setPassword.action";
		GET_ORDER_SYSTEM_CURRENT_INFO_ACTION = SERVER_URL
				+ "orderApps/getDispatchInfo.action";
		SIGN_IN_ACTION = SERVER_URL + "orderApps/changeVehicleStatus.action";
		CHECK_STATUS_ACTION = SERVER_URL
				+ "orderApps/checkVehicleStatus.action";
		ARRIVAL_GOODS_ACTION = SERVER_URL
				+ "orderApps/arrivalVehicleGoods.action";
		GET_STATUS_ACTION = SERVER_URL
				+ "orderApps/getLastestVehicleStatus.action";
		SUBMIT_ORDER_PHOTO2_ACTION = SERVER_URL
				+ "orderApps/uploadOrder.action";
		GET_ATTACHMENTS_ACTION = SERVER_URL + "orderApps/getAttachments.action";
	}
}
