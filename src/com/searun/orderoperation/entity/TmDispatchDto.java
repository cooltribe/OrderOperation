package com.searun.orderoperation.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 调度单传输类
 * 
 * @类名: TmDispatchDto.java
 * @描述: xxx
 * @作者: wangjinkui
 * @日期: 2015年1月26日 下午1:54:19
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
public class TmDispatchDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 526397218235451593L;

	/**
	 * 调度单ID
	 */
	private Long dispatchId;

	/**
	 * 调度单号
	 */
	private String dh_dispatch_no;

	/**
	 * 车牌号
	 */
	private String dh_truck_license_no;

	/**
	 * 车型
	 */
	private String cx;

	/**
	 * 总重量
	 */
	private BigDecimal loh_total_gross_weight;

	/**
	 * 总体积
	 */
	private BigDecimal loh_total_cubage;

	/**
	 * 总件数
	 */
	private Integer loh_total_packs;

	/**
	 * 主驾驶
	 */
	private String dh_primary_driver;

	/**
	 * 主驾驶电话
	 */
	private String dh_primary_tel;

	/**
	 * 副驾驶
	 */
	private String dh_secondary_driver;

	/**
	 * 副驾驶电话
	 */
	private String dh_secondary_tel;

	/**
	 * 作业单明细
	 */
	private List<SysOrderDto> sysOrderDtos;

	/**
	 * 回单
	 */
	private List<SCM_AttachMentDto> attatchMentDtos = new ArrayList<SCM_AttachMentDto>();

	/**
	 * 车辆状态信息
	 */
	private VehicleStatusDto vehicleStatusDto;

	public VehicleStatusDto getVehicleStatusDto() {
		return vehicleStatusDto;
	}

	public void setVehicleStatusDto(VehicleStatusDto vehicleStatusDto) {
		this.vehicleStatusDto = vehicleStatusDto;
	}

	public List<SCM_AttachMentDto> getAttatchMentDtos() {
		return attatchMentDtos;
	}

	public void setAttatchMentDtos(List<SCM_AttachMentDto> attatchMentDtos) {
		this.attatchMentDtos = attatchMentDtos;
	}

	public Long getDispatchId() {
		return dispatchId;
	}

	public void setDispatchId(Long dispatchId) {
		this.dispatchId = dispatchId;
	}

	public String getDh_dispatch_no() {
		return dh_dispatch_no;
	}

	public void setDh_dispatch_no(String dh_dispatch_no) {
		this.dh_dispatch_no = dh_dispatch_no;
	}

	public String getDh_truck_license_no() {
		return dh_truck_license_no;
	}

	public void setDh_truck_license_no(String dh_truck_license_no) {
		this.dh_truck_license_no = dh_truck_license_no;
	}

	public String getCx() {
		return cx;
	}

	public void setCx(String cx) {
		this.cx = cx;
	}

	public BigDecimal getLoh_total_gross_weight() {
		return loh_total_gross_weight;
	}

	public void setLoh_total_gross_weight(BigDecimal loh_total_gross_weight) {
		this.loh_total_gross_weight = loh_total_gross_weight;
	}

	public BigDecimal getLoh_total_cubage() {
		return loh_total_cubage;
	}

	public void setLoh_total_cubage(BigDecimal loh_total_cubage) {
		this.loh_total_cubage = loh_total_cubage;
	}

	public void setLoh_total_packs(Integer loh_total_packs) {
		this.loh_total_packs = loh_total_packs;
	}

	public int getLoh_total_packs() {
		return loh_total_packs;
	}

	public void setLoh_total_packs(int loh_total_packs) {
		this.loh_total_packs = loh_total_packs;
	}

	public String getDh_primary_driver() {
		return dh_primary_driver;
	}

	public void setDh_primary_driver(String dh_primary_driver) {
		this.dh_primary_driver = dh_primary_driver;
	}

	public String getDh_primary_tel() {
		return dh_primary_tel;
	}

	public void setDh_primary_tel(String dh_primary_tel) {
		this.dh_primary_tel = dh_primary_tel;
	}

	public String getDh_secondary_driver() {
		return dh_secondary_driver;
	}

	public void setDh_secondary_driver(String dh_secondary_driver) {
		this.dh_secondary_driver = dh_secondary_driver;
	}

	public String getDh_secondary_tel() {
		return dh_secondary_tel;
	}

	public void setDh_secondary_tel(String dh_secondary_tel) {
		this.dh_secondary_tel = dh_secondary_tel;
	}

	public List<SysOrderDto> getSysOrderDtos() {
		return sysOrderDtos;
	}

	public void setSysOrderDtos(List<SysOrderDto> sysOrderDtos) {
		this.sysOrderDtos = sysOrderDtos;
	}

}
