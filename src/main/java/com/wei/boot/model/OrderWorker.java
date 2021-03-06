package com.wei.boot.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderWorker {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.demand_job_id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private Integer demandJobId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.worker_id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private Integer workerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.order_id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private Integer orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.sign_salary
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private String signSalary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.business_income
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private String businessIncome;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.arrive_work_time
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date arriveWorkTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.description
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.create_user
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.create_time
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.update_user
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private Integer updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_order_worker.update_time
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    private Date updateTime;
    
    private String name;
    
    private String idcard;
    
    private String jobTypeName;
    
    private Worker worker;
    
    private String collectUserIncome;
    
    private String undertakeUserIncome;
    
    private Integer workerCreateUser;
    
    private String workerCreateUserName;
    
    private Integer confirmState;
    
    private String confirmStateName;
    
    private String orderNumber;
    
    public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(Integer confirmState) {
		this.confirmState = confirmState;
	}

	public String getConfirmStateName() {
		return confirmStateName;
	}

	public void setConfirmStateName(String confirmStateName) {
		this.confirmStateName = confirmStateName;
	}

	public Integer getWorkerCreateUser() {
		return workerCreateUser;
	}

	public void setWorkerCreateUser(Integer workerCreateUser) {
		this.workerCreateUser = workerCreateUser;
	}

	public String getWorkerCreateUserName() {
		return workerCreateUserName;
	}

	public void setWorkerCreateUserName(String workerCreateUserName) {
		this.workerCreateUserName = workerCreateUserName;
	}

	public String getCollectUserIncome() {
		return collectUserIncome;
	}

	public void setCollectUserIncome(String collectUserIncome) {
		this.collectUserIncome = collectUserIncome;
	}

	public String getUndertakeUserIncome() {
		return undertakeUserIncome;
	}

	public void setUndertakeUserIncome(String undertakeUserIncome) {
		this.undertakeUserIncome = undertakeUserIncome;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getJobTypeName() {
		return jobTypeName;
	}

	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.id
     *
     * @return the value of t_yx_order_worker.id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.id
     *
     * @param id the value for t_yx_order_worker.id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.demand_job_id
     *
     * @return the value of t_yx_order_worker.demand_job_id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Integer getDemandJobId() {
        return demandJobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.demand_job_id
     *
     * @param demandJobId the value for t_yx_order_worker.demand_job_id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setDemandJobId(Integer demandJobId) {
        this.demandJobId = demandJobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.worker_id
     *
     * @return the value of t_yx_order_worker.worker_id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Integer getWorkerId() {
        return workerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.worker_id
     *
     * @param workerId the value for t_yx_order_worker.worker_id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.order_id
     *
     * @return the value of t_yx_order_worker.order_id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.order_id
     *
     * @param orderId the value for t_yx_order_worker.order_id
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.sign_salary
     *
     * @return the value of t_yx_order_worker.sign_salary
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public String getSignSalary() {
        return signSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.sign_salary
     *
     * @param signSalary the value for t_yx_order_worker.sign_salary
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setSignSalary(String signSalary) {
        this.signSalary = signSalary == null ? null : signSalary.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.business_income
     *
     * @return the value of t_yx_order_worker.business_income
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public String getBusinessIncome() {
        return businessIncome;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.business_income
     *
     * @param businessIncome the value for t_yx_order_worker.business_income
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setBusinessIncome(String businessIncome) {
        this.businessIncome = businessIncome == null ? null : businessIncome.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.arrive_work_time
     *
     * @return the value of t_yx_order_worker.arrive_work_time
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Date getArriveWorkTime() {
        return arriveWorkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.arrive_work_time
     *
     * @param arriveWorkTime the value for t_yx_order_worker.arrive_work_time
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setArriveWorkTime(Date arriveWorkTime) {
        this.arriveWorkTime = arriveWorkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.description
     *
     * @return the value of t_yx_order_worker.description
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.description
     *
     * @param description the value for t_yx_order_worker.description
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.create_user
     *
     * @return the value of t_yx_order_worker.create_user
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.create_user
     *
     * @param createUser the value for t_yx_order_worker.create_user
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.create_time
     *
     * @return the value of t_yx_order_worker.create_time
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.create_time
     *
     * @param createTime the value for t_yx_order_worker.create_time
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.update_user
     *
     * @return the value of t_yx_order_worker.update_user
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.update_user
     *
     * @param updateUser the value for t_yx_order_worker.update_user
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_order_worker.update_time
     *
     * @return the value of t_yx_order_worker.update_time
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_order_worker.update_time
     *
     * @param updateTime the value for t_yx_order_worker.update_time
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}