package com.wei.boot.model;

import java.util.Date;

public class DemandQuery {
	
	private Integer companyId;
	// 录单时间范围-开始时间
	private Date createBeginTime;
	// 录单时间范围-结束时间
	private Date createEndTime;

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
