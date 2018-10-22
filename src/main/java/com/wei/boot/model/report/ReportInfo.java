package com.wei.boot.model.report;

import java.math.BigDecimal;

public class ReportInfo {

	private String name;
	
	private BigDecimal count;
	
	private BigDecimal percentage;

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	
}
