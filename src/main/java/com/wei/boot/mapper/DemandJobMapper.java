package com.wei.boot.mapper;

import com.wei.boot.model.DemandJob;
import com.wei.boot.model.DemandJobExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DemandJobMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    int countByExample(DemandJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    int deleteByExample(DemandJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    int insert(DemandJob record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    int insertSelective(DemandJob record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    List<DemandJob> selectByExample(DemandJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    DemandJob selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    int updateByExampleSelective(@Param("record") DemandJob record, @Param("example") DemandJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    int updateByExample(@Param("record") DemandJob record, @Param("example") DemandJobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    int updateByPrimaryKeySelective(DemandJob record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_job
     *
     * @mbggenerated Sat Sep 15 21:46:36 CST 2018
     */
    int updateByPrimaryKey(DemandJob record);
    
    List<DemandJob> selectByDemandId(int demandId);
	
}