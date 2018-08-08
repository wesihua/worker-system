package com.wei.boot.mapper;

import com.wei.boot.model.JobType;
import com.wei.boot.model.JobTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JobTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    int countByExample(JobTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    int deleteByExample(JobTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    int insert(JobType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    int insertSelective(JobType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    List<JobType> selectByExample(JobTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    JobType selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    int updateByExampleSelective(@Param("record") JobType record, @Param("example") JobTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    int updateByExample(@Param("record") JobType record, @Param("example") JobTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    int updateByPrimaryKeySelective(JobType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_job_type
     *
     * @mbggenerated Wed Aug 08 11:13:28 CST 2018
     */
    int updateByPrimaryKey(JobType record);
}