package com.wei.boot.model;

import java.util.Date;

public class Role {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_role.id
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_role.name
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_role.description
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_role.create_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_role.create_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_role.update_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_role.update_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Integer updateUser;
    
    /**
     * 该角色下用户数量
     */
    private int userCount;

    public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_role.id
     *
     * @return the value of t_sys_role.id
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_role.id
     *
     * @param id the value for t_sys_role.id
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_role.name
     *
     * @return the value of t_sys_role.name
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_role.name
     *
     * @param name the value for t_sys_role.name
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_role.description
     *
     * @return the value of t_sys_role.description
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_role.description
     *
     * @param description the value for t_sys_role.description
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_role.create_time
     *
     * @return the value of t_sys_role.create_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_role.create_time
     *
     * @param createTime the value for t_sys_role.create_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_role.create_user
     *
     * @return the value of t_sys_role.create_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_role.create_user
     *
     * @param createUser the value for t_sys_role.create_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_role.update_time
     *
     * @return the value of t_sys_role.update_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_role.update_time
     *
     * @param updateTime the value for t_sys_role.update_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_role.update_user
     *
     * @return the value of t_sys_role.update_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_role.update_user
     *
     * @param updateUser the value for t_sys_role.update_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}