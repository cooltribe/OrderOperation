package com.searun.orderoperation.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 车辆状态信息类
 * @类名: VehicleStatusDto.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2015年3月4日 上午11:30:19
 * @版本:  V1.0
 * ============================================================================
 * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.sy56.com
 * ============================================================================
 */
/**
 * @类名: VehicleStatusDto.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2015年3月4日 上午11:33:54
 * @版本: V1.0
 *      ====================================================================
 *      ======== 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
 *      ------------------
 *      ----------------------------------------------------------
 *      提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
 *      ----------------------------------
 *      ------------------------------------------ 官方网站：http://www.sy56.com
 *      ======
 *      ======================================================================
 */
public class VehicleStatusDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String row_id;

	/**
	 * 车牌号
	 */
	private String vehicle_num;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 经度
	 */
	private BigDecimal lng;

	/**
	 * 纬度
	 */
	private BigDecimal lat;

	/**
	 * 位置描述
	 */
	private String location_desc;

	/**
	 * 作业类型
	 */
	private Integer task_type;

	/**
	 * 作业状态
	 */
	private Integer task_status;

	/**
	 * 调度单ID
	 */
	private Long dispatch_id;

	/**
	 * 调度单号
	 */
	private String dh_dispatch_no;

	/**
	 * 作业单号
	 */
	private String task_no;

	public String getTask_no() {
		return task_no;
	}

	public void setTask_no(String task_no) {
		this.task_no = task_no;
	}

	public String getVehicle_num() {
		return vehicle_num;
	}

	public void setVehicle_num(String vehicle_num) {
		this.vehicle_num = vehicle_num;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public String getLocation_desc() {
		return location_desc;
	}

	public void setLocation_desc(String location_desc) {
		this.location_desc = location_desc;
	}

	public Integer getTask_type() {
		return task_type;
	}

	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}

	public Integer getTask_status() {
		return task_status;
	}

	public void setTask_status(Integer task_status) {
		this.task_status = task_status;
	}

	public Long getDispatch_id() {
		return dispatch_id;
	}

	public void setDispatch_id(Long dispatch_id) {
		this.dispatch_id = dispatch_id;
	}

	public String getDh_dispatch_no() {
		return dh_dispatch_no;
	}

	public void setDh_dispatch_no(String dh_dispatch_no) {
		this.dh_dispatch_no = dh_dispatch_no;
	}

	public String getRow_id() {
		return row_id;
	}

	public void setRow_id(String row_id) {
		this.row_id = row_id;
	}


}
