package com.wei.boot.model;

import java.util.Date;
import java.util.List;

public class JobType {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_job_type.id
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_job_type.name
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_job_type.level
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    private Integer level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_job_type.parent_id
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    private Integer parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_job_type.create_time
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_job_type.create_user
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_job_type.update_time
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_job_type.update_user
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    private Integer updateUser;
    
    
    private List<JobType> children;

    public List<JobType> getChildren() {
		return children;
	}

	public void setChildren(List<JobType> children) {
		this.children = children;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_job_type.id
     *
     * @return the value of t_yx_job_type.id
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_job_type.id
     *
     * @param id the value for t_yx_job_type.id
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_job_type.name
     *
     * @return the value of t_yx_job_type.name
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_job_type.name
     *
     * @param name the value for t_yx_job_type.name
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_job_type.level
     *
     * @return the value of t_yx_job_type.level
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_job_type.level
     *
     * @param level the value for t_yx_job_type.level
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_job_type.parent_id
     *
     * @return the value of t_yx_job_type.parent_id
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_job_type.parent_id
     *
     * @param parentId the value for t_yx_job_type.parent_id
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_job_type.create_time
     *
     * @return the value of t_yx_job_type.create_time
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_job_type.create_time
     *
     * @param createTime the value for t_yx_job_type.create_time
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_job_type.create_user
     *
     * @return the value of t_yx_job_type.create_user
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_job_type.create_user
     *
     * @param createUser the value for t_yx_job_type.create_user
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_job_type.update_time
     *
     * @return the value of t_yx_job_type.update_time
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_job_type.update_time
     *
     * @param updateTime the value for t_yx_job_type.update_time
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_job_type.update_user
     *
     * @return the value of t_yx_job_type.update_user
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_job_type.update_user
     *
     * @param updateUser the value for t_yx_job_type.update_user
     *
     * @mbggenerated Wed Aug 08 19:17:56 CST 2018
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}