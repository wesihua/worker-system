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

	private String timeStr;
	
	// 订单状态
	private Integer state;
	
	
	// 关单时间范围-开始时间
	private Date closeBeginTime;
	
	// 关单时间范围-结束时间
	private Date closeEndTime;
	
	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public Date getCloseBeginTime() {
		return closeBeginTime;
	}

	public void setCloseBeginTime(Date closeBeginTime) {
		this.closeBeginTime = closeBeginTime;
	}

	public Date getCloseEndTime() {
		return closeEndTime;
	}

	public void setCloseEndTime(Date closeEndTime) {
		this.closeEndTime = closeEndTime;
	}

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

	@Override
	public String toString() {
		return "DemandQuery [companyId=" + companyId + ", createBeginTime=" + createBeginTime + ", createEndTime="
				+ createEndTime + ", createTime=" + createTime + ", timeStr=" + timeStr + ", state=" + state
				+ ", closeBeginTime=" + closeBeginTime + ", closeEndTime=" + closeEndTime + "]";
	}

}
