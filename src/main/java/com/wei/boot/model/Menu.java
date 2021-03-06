package com.wei.boot.model;

import java.util.Date;
import java.util.List;

public class Menu {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_menu.id
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_menu.name
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_menu.path
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private String path;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_menu.type
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_menu.parent_id
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Integer parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_menu.create_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_menu.create_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_menu.update_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_menu.update_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    private Integer updateUser;
    
    private List<Menu> children;
    
    /**
     * 是否选中，0:否，1:是
     * 角色编辑菜单权限时用到
     */
    private int selected = 0;

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}


	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_menu.id
     *
     * @return the value of t_sys_menu.id
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_menu.id
     *
     * @param id the value for t_sys_menu.id
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_menu.name
     *
     * @return the value of t_sys_menu.name
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_menu.name
     *
     * @param name the value for t_sys_menu.name
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_menu.path
     *
     * @return the value of t_sys_menu.path
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_menu.path
     *
     * @param path the value for t_sys_menu.path
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_menu.type
     *
     * @return the value of t_sys_menu.type
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_menu.type
     *
     * @param type the value for t_sys_menu.type
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_menu.parent_id
     *
     * @return the value of t_sys_menu.parent_id
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_menu.parent_id
     *
     * @param parentId the value for t_sys_menu.parent_id
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_menu.create_time
     *
     * @return the value of t_sys_menu.create_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_menu.create_time
     *
     * @param createTime the value for t_sys_menu.create_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_menu.create_user
     *
     * @return the value of t_sys_menu.create_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_menu.create_user
     *
     * @param createUser the value for t_sys_menu.create_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_menu.update_time
     *
     * @return the value of t_sys_menu.update_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_menu.update_time
     *
     * @param updateTime the value for t_sys_menu.update_time
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_menu.update_user
     *
     * @return the value of t_sys_menu.update_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_menu.update_user
     *
     * @param updateUser the value for t_sys_menu.update_user
     *
     * @mbggenerated Tue Aug 14 15:16:20 CST 2018
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}