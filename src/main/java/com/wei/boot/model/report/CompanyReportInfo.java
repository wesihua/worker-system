package com.wei.boot.model.report;

import java.math.BigDecimal;

public class CompanyReportInfo {

	private String companyName;
	
	private int demandCount;
	
	private int orderCount;
	
	private int orderMemberCount;
	
	private BigDecimal orderIncome;

	private String userName;
	
	private int workerCount;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getWorkerCount() {
		return workerCount;
	}

	public void setWorkerCount(int workerCount) {
		this.workerCount = workerCount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getDemandCount() {
		return demandCount;
	}

	public void setDemandCount(int demandCount) {
		this.demandCount = demandCount;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getOrderMemberCount() {
		return orderMemberCount;
	}

	public void setOrderMemberCount(int orderMemberCount) {
		this.orderMemberCount = orderMemberCount;
	}

	public BigDecimal getOrderIncome() {
		return orderIncome;
	}

	public void setOrderIncome(BigDecimal orderIncome) {
		this.orderIncome = orderIncome;
	}
}
