package com.wei.boot.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DemandJob {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.id
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.demand_id
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Integer demandId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.job_type_id
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Integer jobTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.worker_count
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Integer workerCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.salary
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private String salary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.require_time
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date requireTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.work_area
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Integer workArea;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.gender
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Integer gender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.age
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private String age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.degree
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Integer degree;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.requirement
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private String requirement;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.create_user
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.create_time
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.update_user
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Integer updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_demand_job.update_time
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.id
     *
     * @return the value of t_yx_demand_job.id
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Integer getId() {
        return id;
    }
    
    
    /**************以下翻译字段*********************/
    /**
     * 工种名字
     */
    private String jobTypeName = "";
    
    /**
     * 工作地区
     */
    private String workAreaName = "";
    
    private String genderName;
    
    private String degreeName;
    
    /**
     * 分配人数
     */
    private int assignCount;
    
    /**
     * 签约人数
     */
    private int signingCount;
    
    /**
     * 收入
     */
    private String income;
    
    /**
     * 地区父code
     */
    private Integer parentCode;
    
    private Integer parentJobTypeId;
    
    
    /**************以上翻译字段*********************/
    
    
    
    private String major;

    public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getJobTypeName() {
		return jobTypeName;
	}

	public Integer getParentJobTypeId() {
		return parentJobTypeId;
	}

	public void setParentJobTypeId(Integer parentJobTypeId) {
		this.parentJobTypeId = parentJobTypeId;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public Integer getParentCode() {
		return parentCode;
	}

	public void setParentCode(Integer parentCode) {
		this.parentCode = parentCode;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}

	public String getWorkAreaName() {
		return workAreaName;
	}

	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}

	public int getAssignCount() {
		return assignCount;
	}

	public void setAssignCount(int assignCount) {
		this.assignCount = assignCount;
	}

	public int getSigningCount() {
		return signingCount;
	}

	public void setSigningCount(int signingCount) {
		this.signingCount = signingCount;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.id
     *
     * @param id the value for t_yx_demand_job.id
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.demand_id
     *
     * @return the value of t_yx_demand_job.demand_id
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Integer getDemandId() {
        return demandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.demand_id
     *
     * @param demandId the value for t_yx_demand_job.demand_id
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.job_type_id
     *
     * @return the value of t_yx_demand_job.job_type_id
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Integer getJobTypeId() {
        return jobTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.job_type_id
     *
     * @param jobTypeId the value for t_yx_demand_job.job_type_id
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setJobTypeId(Integer jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.worker_count
     *
     * @return the value of t_yx_demand_job.worker_count
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Integer getWorkerCount() {
        return workerCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.worker_count
     *
     * @param workerCount the value for t_yx_demand_job.worker_count
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setWorkerCount(Integer workerCount) {
        this.workerCount = workerCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.salary
     *
     * @return the value of t_yx_demand_job.salary
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public String getSalary() {
        return salary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.salary
     *
     * @param salary the value for t_yx_demand_job.salary
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.require_time
     *
     * @return the value of t_yx_demand_job.require_time
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Date getRequireTime() {
        return requireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.require_time
     *
     * @param requireTime the value for t_yx_demand_job.require_time
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setRequireTime(Date requireTime) {
        this.requireTime = requireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.work_area
     *
     * @return the value of t_yx_demand_job.work_area
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Integer getWorkArea() {
        return workArea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.work_area
     *
     * @param workArea the value for t_yx_demand_job.work_area
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setWorkArea(Integer workArea) {
        this.workArea = workArea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.gender
     *
     * @return the value of t_yx_demand_job.gender
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.gender
     *
     * @param gender the value for t_yx_demand_job.gender
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setGender(Integer gender) {
        this.gender = gender == null ? null : gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.age
     *
     * @return the value of t_yx_demand_job.age
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public String getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.age
     *
     * @param age the value for t_yx_demand_job.age
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.degree
     *
     * @return the value of t_yx_demand_job.degree
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Integer getDegree() {
        return degree;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.degree
     *
     * @param degree the value for t_yx_demand_job.degree
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setDegree(Integer degree) {
        this.degree = degree == null ? null : degree;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.requirement
     *
     * @return the value of t_yx_demand_job.requirement
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public String getRequirement() {
        return requirement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.requirement
     *
     * @param requirement the value for t_yx_demand_job.requirement
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setRequirement(String requirement) {
        this.requirement = requirement == null ? null : requirement.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.create_user
     *
     * @return the value of t_yx_demand_job.create_user
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.create_user
     *
     * @param createUser the value for t_yx_demand_job.create_user
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.create_time
     *
     * @return the value of t_yx_demand_job.create_time
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.create_time
     *
     * @param createTime the value for t_yx_demand_job.create_time
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.update_user
     *
     * @return the value of t_yx_demand_job.update_user
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.update_user
     *
     * @param updateUser the value for t_yx_demand_job.update_user
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_demand_job.update_time
     *
     * @return the value of t_yx_demand_job.update_time
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_demand_job.update_time
     *
     * @param updateTime the value for t_yx_demand_job.update_time
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}