package com.wei.boot.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DemandQuery {

	// 企业id
	private Integer companyId;

	// 录单时间范围-开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date createBeginTime;

	// 录单时间范围-结束时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date createEndTime;

	private Date createTime;

	private String timeStr;

	// 订单状态
	private Integer state;

	// 关单时间范围-开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date closeBeginTime;

	// 关单时间范围-结束时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date closeEndTime;

	private String companyName;

	private String demandNumber;

	private String undertakeUserName = "";

	private String closeUserName = "";

	private String beginTime;

	private String endTime;

	private Integer userId;

	private String closeBeginTimeStr;

	private String closeEndTimeStr;

	private String createBeginTimeStr;

	private String createEndTimeStr;

	private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public String getCloseBeginTimeStr() {
		return closeBeginTimeStr;
	}

	public void setCloseBeginTimeStr(String closeBeginTimeStr) {
		this.closeBeginTimeStr = closeBeginTimeStr;
	}

	public String getCloseEndTimeStr() {
		return closeEndTimeStr;
	}

	public void setCloseEndTimeStr(String closeEndTimeStr) {
		this.closeEndTimeStr = closeEndTimeStr;
	}

	public String getCreateBeginTimeStr() {
		return createBeginTimeStr;
	}

	public void setCreateBeginTimeStr(String createBeginTimeStr) {
		this.createBeginTimeStr = createBeginTimeStr;
	}

	public String getCreateEndTimeStr() {
		return createEndTimeStr;
	}

	public void setCreateEndTimeStr(String createEndTimeStr) {
		this.createEndTimeStr = createEndTimeStr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

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

		if (!StringUtils.isEmpty(closeBeginTimeStr)) {
			return getTime(closeBeginTimeStr);
		}
		return closeBeginTime;
	}

	private Date getTime(String timeStr) {
		try {
			return dateformat.parse(timeStr);
		} catch (ParseException e) {
		}
		return null;
	}

	public void setCloseBeginTime(Date closeBeginTime) {

		this.closeBeginTime = closeBeginTime;
	}

	public Date getCloseEndTime() {

		if (!StringUtils.isEmpty(closeEndTimeStr)) {
			return getTime(closeEndTimeStr);
		}
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

		if (!StringUtils.isEmpty(createBeginTimeStr)) {
			return getTime(createBeginTimeStr);
		}
		return createBeginTime;
	}

	public void setCreateBeginTime(Date createBeginTime) {
		this.createBeginTime = createBeginTime;
	}

	public Date getCreateEndTime() {

		if (!StringUtils.isEmpty(createEndTimeStr)) {
			return getTime(createEndTimeStr);
		}
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
