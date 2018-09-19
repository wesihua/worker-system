package com.wei.boot.model;

import java.util.Date;

public class DemandQuery {
	
	// 企业id 
	private Integer companyId;
	
	// 录单时间范围-开始时间
	private Date createBeginTime;
	
	// 录单时间范围-结束时间
	private Date createEndTime;
	
	private Date createTime;
	
	// 订单状态
	private Integer state;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getCreateBeginTime() {
		return createBeginTime;
	}

	public void setCreateBeginTime(Date createBeginTime) {
		this.createBeginTime = createBeginTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}
	

}
