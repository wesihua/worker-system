package com.wei.boot.model;

import java.util.Date;

public class Company {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.id
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.name
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.code
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.industry
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private String industry;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.address
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.contact_name
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private String contactName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.contact_phone
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private String contactPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.description
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.create_time
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.create_user
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.update_time
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_company.update_user
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    private Integer updateUser;
    
    /*
     * 已录入属于该企业的的人员人数
     */
    private int count;
    
    
    private String bank;
    
    private String bankAccount;
    
    private String creditNumber;

    public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.id
     *
     * @return the value of t_yx_company.id
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.id
     *
     * @param id the value for t_yx_company.id
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.name
     *
     * @return the value of t_yx_company.name
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.name
     *
     * @param name the value for t_yx_company.name
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.code
     *
     * @return the value of t_yx_company.code
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.code
     *
     * @param code the value for t_yx_company.code
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.industry
     *
     * @return the value of t_yx_company.industry
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.industry
     *
     * @param industry the value for t_yx_company.industry
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.address
     *
     * @return the value of t_yx_company.address
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.address
     *
     * @param address the value for t_yx_company.address
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.contact_name
     *
     * @return the value of t_yx_company.contact_name
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.contact_name
     *
     * @param contactName the value for t_yx_company.contact_name
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.contact_phone
     *
     * @return the value of t_yx_company.contact_phone
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.contact_phone
     *
     * @param contactPhone the value for t_yx_company.contact_phone
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.description
     *
     * @return the value of t_yx_company.description
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.description
     *
     * @param description the value for t_yx_company.description
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.create_time
     *
     * @return the value of t_yx_company.create_time
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.create_time
     *
     * @param createTime the value for t_yx_company.create_time
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.create_user
     *
     * @return the value of t_yx_company.create_user
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.create_user
     *
     * @param createUser the value for t_yx_company.create_user
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.update_time
     *
     * @return the value of t_yx_company.update_time
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.update_time
     *
     * @param updateTime the value for t_yx_company.update_time
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_company.update_user
     *
     * @return the value of t_yx_company.update_user
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_company.update_user
     *
     * @param updateUser the value for t_yx_company.update_user
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}