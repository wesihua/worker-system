package com.wei.boot.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DemandQuery {
	
	// 企业id 
	private Integer companyId;
	
	// 录单时间范围-开始时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date createBeginTime;
	
	// 录单时间范围-结束时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date createEndTime;
	
	private Date createTime;

	private String timeStr;
	
	// 订单状态
	private Integer state;
	
	
	// 关单时间范围-开始时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date closeBeginTime;
	
	// 关单时间范围-结束时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date closeEndTime;
	
	private String companyName;
	
	private String demandNumber;
	
	private String undertakeUserName = "";
    
    private String closeUserName = "";
    
    private String beginTime;
    
    private String endTime;
	
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUndertakeUserName() {
		return undertakeUserName;
	}

	public void setUndertakeUserName(String undertakeUserName) {
		this.undertakeUserName = undertakeUserName;
	}

	public String getCloseUserName() {
		return closeUserName;
	}

	public void setCloseUserName(String closeUserName) {
		this.closeUserName = closeUserName;
	}

	public String getDemandNumber() {
		return demandNumber;
	}

	public void setDemandNumber(String demandNumber) {
		this.demandNumber = demandNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

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
