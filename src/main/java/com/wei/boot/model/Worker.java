package com.wei.boot.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Worker {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.id
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.name
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.photo
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private String photo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.sex
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.telephone
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private String telephone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.idcard
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private String idcard;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.birthday
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.birthplace_code
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer birthplaceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.work_year
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer workYear;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.marital_status
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer maritalStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.workplace_code
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer workplaceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.expect_salary
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer expectSalary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.work_status
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer workStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.nation
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer nation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.position
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private String position;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.address
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.language_level
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer languageLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.work_expect
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private String workExpect;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.email
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.title
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.night_work
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer nightWork;
    
    /*
     * 当前学历
     */
    private Integer degree;
    
    /*
     * 个人简介
     */
    private String profile;
    
    /*
     * 备注
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.souce
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer souce;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.create_time
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.create_user
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.update_time
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_yx_worker.update_user
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    private Integer updateUser;
    
    private String jobtypeName;
    
    
    private List<WorkerEducation> educationList;
    
    
    private List<WorkerExperience> experienceList;
    
    /*********************** 以下字段用于接收参数 **********************/
    
    private String company;
    
    private String beginTime;
    
    private String endTime;
    
    private Integer firstId;	// 查询条件-工种大类
    
    private Integer secondId;	// 查询条件-工种小类
    
    private List<WorkerJobType> jobTypeList;	// 工种集合，用于新增/编辑时接收参数
    
    /*********************** 以下是翻译字段 **********************/
    
    private String sexName = "";
    
    private String birthplaceName = "";
    
    private String maritalStatusName = "";
    
    private String workplaceName = "";
    
    private String expectSalaryName = "";
    
    private String workStatusName = "";
    
    private String nationName = "";
    
    private String languageLevelName = "";
    
    private String nightWorkName = "";
    
    private String sourceName = "";
    
    private String createUserName = "";
    
    private String degreeName = "";
    
    private String birthdayName = "";
    
    private int age;


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBirthdayName() {
		return birthdayName;
	}

	public void setBirthdayName(String birthdayName) {
		this.birthdayName = birthdayName;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobtypeName() {
		return jobtypeName;
	}

	public void setJobtypeName(String jobtypeName) {
		this.jobtypeName = jobtypeName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<WorkerJobType> getJobTypeList() {
		return jobTypeList;
	}

	public void setJobTypeList(List<WorkerJobType> jobTypeList) {
		this.jobTypeList = jobTypeList;
	}

	public Integer getFirstId() {
		return firstId;
	}

	public void setFirstId(Integer firstId) {
		this.firstId = firstId;
	}

	public Integer getSecondId() {
		return secondId;
	}

	public void setSecondId(Integer secondId) {
		this.secondId = secondId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getBirthplaceName() {
		return birthplaceName;
	}

	public void setBirthplaceName(String birthplaceName) {
		this.birthplaceName = birthplaceName;
	}

	public String getMaritalStatusName() {
		return maritalStatusName;
	}

	public void setMaritalStatusName(String maritalStatusName) {
		this.maritalStatusName = maritalStatusName;
	}

	public String getWorkplaceName() {
		return workplaceName;
	}

	public void setWorkplaceName(String workplaceName) {
		this.workplaceName = workplaceName;
	}

	public String getExpectSalaryName() {
		return expectSalaryName;
	}

	public void setExpectSalaryName(String expectSalaryName) {
		this.expectSalaryName = expectSalaryName;
	}

	public String getWorkStatusName() {
		return workStatusName;
	}

	public void setWorkStatusName(String workStatusName) {
		this.workStatusName = workStatusName;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getLanguageLevelName() {
		return languageLevelName;
	}

	public void setLanguageLevelName(String languageLevelName) {
		this.languageLevelName = languageLevelName;
	}

	public String getNightWorkName() {
		return nightWorkName;
	}

	public void setNightWorkName(String nightWorkName) {
		this.nightWorkName = nightWorkName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public List<WorkerEducation> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<WorkerEducation> educationList) {
		this.educationList = educationList;
	}

	public List<WorkerExperience> getExperienceList() {
		return experienceList;
	}

	public void setExperienceList(List<WorkerExperience> experienceList) {
		this.experienceList = experienceList;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.id
     *
     * @return the value of t_yx_worker.id
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.id
     *
     * @param id the value for t_yx_worker.id
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.name
     *
     * @return the value of t_yx_worker.name
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.name
     *
     * @param name the value for t_yx_worker.name
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.photo
     *
     * @return the value of t_yx_worker.photo
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.photo
     *
     * @param photo the value for t_yx_worker.photo
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.sex
     *
     * @return the value of t_yx_worker.sex
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.sex
     *
     * @param sex the value for t_yx_worker.sex
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.telephone
     *
     * @return the value of t_yx_worker.telephone
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.telephone
     *
     * @param telephone the value for t_yx_worker.telephone
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.idcard
     *
     * @return the value of t_yx_worker.idcard
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.idcard
     *
     * @param idcard the value for t_yx_worker.idcard
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.birthday
     *
     * @return the value of t_yx_worker.birthday
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.birthday
     *
     * @param birthday the value for t_yx_worker.birthday
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.birthplace_code
     *
     * @return the value of t_yx_worker.birthplace_code
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getBirthplaceCode() {
        return birthplaceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.birthplace_code
     *
     * @param birthplaceCode the value for t_yx_worker.birthplace_code
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setBirthplaceCode(Integer birthplaceCode) {
        this.birthplaceCode = birthplaceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.work_year
     *
     * @return the value of t_yx_worker.work_year
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getWorkYear() {
        return workYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.work_year
     *
     * @param workYear the value for t_yx_worker.work_year
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.marital_status
     *
     * @return the value of t_yx_worker.marital_status
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.marital_status
     *
     * @param maritalStatus the value for t_yx_worker.marital_status
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.workplace_code
     *
     * @return the value of t_yx_worker.workplace_code
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getWorkplaceCode() {
        return workplaceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.workplace_code
     *
     * @param workplaceCode the value for t_yx_worker.workplace_code
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setWorkplaceCode(Integer workplaceCode) {
        this.workplaceCode = workplaceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.expect_salary
     *
     * @return the value of t_yx_worker.expect_salary
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getExpectSalary() {
        return expectSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.expect_salary
     *
     * @param expectSalary the value for t_yx_worker.expect_salary
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setExpectSalary(Integer expectSalary) {
        this.expectSalary = expectSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.work_status
     *
     * @return the value of t_yx_worker.work_status
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getWorkStatus() {
        return workStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.work_status
     *
     * @param workStatus the value for t_yx_worker.work_status
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.nation
     *
     * @return the value of t_yx_worker.nation
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getNation() {
        return nation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.nation
     *
     * @param nation the value for t_yx_worker.nation
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setNation(Integer nation) {
        this.nation = nation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.position
     *
     * @return the value of t_yx_worker.position
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.position
     *
     * @param position the value for t_yx_worker.position
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.address
     *
     * @return the value of t_yx_worker.address
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.address
     *
     * @param address the value for t_yx_worker.address
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.language_level
     *
     * @return the value of t_yx_worker.language_level
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getLanguageLevel() {
        return languageLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.language_level
     *
     * @param languageLevel the value for t_yx_worker.language_level
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setLanguageLevel(Integer languageLevel) {
        this.languageLevel = languageLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.work_expect
     *
     * @return the value of t_yx_worker.work_expect
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public String getWorkExpect() {
        return workExpect;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.work_expect
     *
     * @param workExpect the value for t_yx_worker.work_expect
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setWorkExpect(String workExpect) {
        this.workExpect = workExpect == null ? null : workExpect.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.email
     *
     * @return the value of t_yx_worker.email
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.email
     *
     * @param email the value for t_yx_worker.email
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.title
     *
     * @return the value of t_yx_worker.title
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.title
     *
     * @param title the value for t_yx_worker.title
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.night_work
     *
     * @return the value of t_yx_worker.night_work
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getNightWork() {
        return nightWork;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.night_work
     *
     * @param nightWork the value for t_yx_worker.night_work
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setNightWork(Integer nightWork) {
        this.nightWork = nightWork;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.souce
     *
     * @return the value of t_yx_worker.souce
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getSouce() {
        return souce;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.souce
     *
     * @param souce the value for t_yx_worker.souce
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setSouce(Integer souce) {
        this.souce = souce;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.create_time
     *
     * @return the value of t_yx_worker.create_time
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.create_time
     *
     * @param createTime the value for t_yx_worker.create_time
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.create_user
     *
     * @return the value of t_yx_worker.create_user
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.create_user
     *
     * @param createUser the value for t_yx_worker.create_user
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.update_time
     *
     * @return the value of t_yx_worker.update_time
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.update_time
     *
     * @param updateTime the value for t_yx_worker.update_time
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_yx_worker.update_user
     *
     * @return the value of t_yx_worker.update_user
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_yx_worker.update_user
     *
     * @param updateUser the value for t_yx_worker.update_user
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}