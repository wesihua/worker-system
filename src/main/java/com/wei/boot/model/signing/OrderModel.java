package com.wei.boot.model.signing;

import java.io.Serializable;
import java.util.List;

import com.wei.boot.model.Demand;
import com.wei.boot.model.OrderWorker;

public class OrderModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Demand demand;
	
	private List<OrderWorker> orderWorkerList;

	public Demand getDemand() {
		return demand;
	}

	public void setDemand(Demand demand) {
		this.demand = demand;
	}

	public List<OrderWorker> getOrderWorkerList() {
		return orderWorkerList;
	}

	public void setOrderWorkerList(List<OrderWorker> orderWorkerList) {
		this.orderWorkerList = orderWorkerList;
	} 
	
	

}
