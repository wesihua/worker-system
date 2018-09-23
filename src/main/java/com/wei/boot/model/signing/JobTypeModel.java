package com.wei.boot.model.signing;

import java.io.Serializable;

import com.wei.boot.model.DemandJob;
import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.Page;

public class JobTypeModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7001602204565716543L;

	private DemandJob demandJob;
	
	private Page<OrderWorker> pageData;

	public DemandJob getDemandJob() {
		return demandJob;
	}

	public void setDemandJob(DemandJob demandJob) {
		this.demandJob = demandJob;
	}

	public Page<OrderWorker> getPageData() {
		return pageData;
	}

	public void setPageData(Page<OrderWorker> pageData) {
		this.pageData = pageData;
	}
	
	

}
