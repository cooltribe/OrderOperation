package com.searun.orderoperation.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 作业单明细传输类
 * 
 * @类名: SysOrderDto.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2015年1月26日 下午1:54:34
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
public class SysOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1269887973503484877L;
	/**
	 * 订单号
	 */
	private String order_no;
	
	/**
	 * 作业单号
	 */
	private String clh_cargo_load_no;

	/**
	 * 货主
	 */
	private String loh_owner_name;

	/**
	 * 装货详细地址
	 */
	private String loh_load_address;

	/**
	 * 卸货详细地址
	 */
	private String loh_unload_address;

	/**
	 * 最早应到货日期
	 */
	private Date loh_expect_unload_datetime;
	
	/**
	 * 最晚应到货日期
	 */
	private Date loh_expect_unload_datetime_u;


	/**
	 * 物料明细
	 */
	private List<MaterialDto> materialDtos;
	
	/**
	在SysOrderDto中添加 * 是否到达
	 */
	private boolean isArrival;
	
	

	public boolean isArrival() {
		return isArrival;
	}

	public void setArrival(boolean isArrival) {
		this.isArrival = isArrival;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getClh_cargo_load_no() {
		return clh_cargo_load_no;
	}

	public void setClh_cargo_load_no(String clh_cargo_load_no) {
		this.clh_cargo_load_no = clh_cargo_load_no;
	}

	public String getLoh_owner_name() {
		return loh_owner_name;
	}

	public void setLoh_owner_name(String loh_owner_name) {
		this.loh_owner_name = loh_owner_name;
	}

	public String getLoh_load_address() {
		return loh_load_address;
	}

	public void setLoh_load_address(String loh_load_address) {
		this.loh_load_address = loh_load_address;
	}

	public String getLoh_unload_address() {
		return loh_unload_address;
	}

	public void setLoh_unload_address(String loh_unload_address) {
		this.loh_unload_address = loh_unload_address;
	}


	public Date getLoh_expect_unload_datetime_u() {
		return loh_expect_unload_datetime_u;
	}

	public void setLoh_expect_unload_datetime_u(Date loh_expect_unload_datetime_u) {
		this.loh_expect_unload_datetime_u = loh_expect_unload_datetime_u;
	}

	public Date getLoh_expect_unload_datetime() {
		return loh_expect_unload_datetime;
	}

	public void setLoh_expect_unload_datetime(Date loh_expect_unload_datetime) {
		this.loh_expect_unload_datetime = loh_expect_unload_datetime;
	}

	public List<MaterialDto> getMaterialDtos() {
		return materialDtos;
	}

	public void setMaterialDtos(List<MaterialDto> materialDtos) {
		this.materialDtos = materialDtos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
