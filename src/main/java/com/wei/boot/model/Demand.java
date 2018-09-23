package com.wei.boot.model;

import java.util.Date;
import java.util.List;

public class Demand {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.id
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.demand_number
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private String demandNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.company_id
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Integer companyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.state
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.description
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.other_treatment
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private String otherTreatment;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.undertake_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Date undertakeTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.undertake_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Integer undertakeUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.close_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Date closeTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.close_status
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Integer closeStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.close_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Integer closeUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.close_reason
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private String closeReason;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.create_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.create_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.update_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Integer updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand.update_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    private Date updateTime;
    
    /**
     * 需求用工列表
     */
    private List<DemandJob> demandJobList;
    
    
 /*********************** 以下是翻译字段 **********************/
    
    private String companyName = "";
    
    private String createUserName = "";
    
    private String undertakeUserName = "";
    
    private String closeUserName = "";
    
    private int totalIncome;
    
    public int getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(int totalIncome) {
		this.totalIncome = totalIncome;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
	
	/**************** 以上是翻译字段  ***************************/

	public List<DemandJob> getDemandJobList() {
		return demandJobList;
	}

	public void setDemandJobList(List<DemandJob> demandJobList) {
		this.demandJobList = demandJobList;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.id
     *
     * @return the value of t_yx_demand.id
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.id
     *
     * @param id the value for t_yx_demand.id
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.demand_number
     *
     * @return the value of t_yx_demand.demand_number
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public String getDemandNumber() {
        return demandNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.demand_number
     *
     * @param demandNumber the value for t_yx_demand.demand_number
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setDemandNumber(String demandNumber) {
        this.demandNumber = demandNumber == null ? null : demandNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.company_id
     *
     * @return the value of t_yx_demand.company_id
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.company_id
     *
     * @param companyId the value for t_yx_demand.company_id
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.state
     *
     * @return the value of t_yx_demand.state
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.state
     *
     * @param state the value for t_yx_demand.state
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.description
     *
     * @return the value of t_yx_demand.description
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.description
     *
     * @param description the value for t_yx_demand.description
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.other_treatment
     *
     * @return the value of t_yx_demand.other_treatment
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public String getOtherTreatment() {
        return otherTreatment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.other_treatment
     *
     * @param otherTreatment the value for t_yx_demand.other_treatment
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setOtherTreatment(String otherTreatment) {
        this.otherTreatment = otherTreatment == null ? null : otherTreatment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.undertake_time
     *
     * @return the value of t_yx_demand.undertake_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Date getUndertakeTime() {
        return undertakeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.undertake_time
     *
     * @param undertakeTime the value for t_yx_demand.undertake_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setUndertakeTime(Date undertakeTime) {
        this.undertakeTime = undertakeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.undertake_user
     *
     * @return the value of t_yx_demand.undertake_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Integer getUndertakeUser() {
        return undertakeUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.undertake_user
     *
     * @param undertakeUser the value for t_yx_demand.undertake_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setUndertakeUser(Integer undertakeUser) {
        this.undertakeUser = undertakeUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.close_time
     *
     * @return the value of t_yx_demand.close_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Date getCloseTime() {
        return closeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.close_time
     *
     * @param closeTime the value for t_yx_demand.close_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.close_status
     *
     * @return the value of t_yx_demand.close_status
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Integer getCloseStatus() {
        return closeStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.close_status
     *
     * @param closeStatus the value for t_yx_demand.close_status
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setCloseStatus(Integer closeStatus) {
        this.closeStatus = closeStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.close_user
     *
     * @return the value of t_yx_demand.close_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Integer getCloseUser() {
        return closeUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.close_user
     *
     * @param closeUser the value for t_yx_demand.close_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setCloseUser(Integer closeUser) {
        this.closeUser = closeUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.close_reason
     *
     * @return the value of t_yx_demand.close_reason
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public String getCloseReason() {
        return closeReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.close_reason
     *
     * @param closeReason the value for t_yx_demand.close_reason
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason == null ? null : closeReason.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.create_user
     *
     * @return the value of t_yx_demand.create_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.create_user
     *
     * @param createUser the value for t_yx_demand.create_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.create_time
     *
     * @return the value of t_yx_demand.create_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.create_time
     *
     * @param createTime the value for t_yx_demand.create_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.update_user
     *
     * @return the value of t_yx_demand.update_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.update_user
     *
     * @param updateUser the value for t_yx_demand.update_user
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand.update_time
     *
     * @return the value of t_yx_demand.update_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand.update_time
     *
     * @param updateTime the value for t_yx_demand.update_time
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}