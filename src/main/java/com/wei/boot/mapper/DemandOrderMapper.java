package com.wei.boot.mapper;

import com.wei.boot.model.DemandOrder;
import com.wei.boot.model.DemandOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DemandOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    int countByExample(DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    int deleteByExample(DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    int insert(DemandOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    int insertSelective(DemandOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    List<DemandOrder> selectByExample(DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    DemandOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    int updateByExampleSelective(@Param("record") DemandOrder record, @Param("example") DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    int updateByExample(@Param("record") DemandOrder record, @Param("example") DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    int updateByPrimaryKeySelective(DemandOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sat Sep 15 21:47:36 CST 2018
     */
    int updateByPrimaryKey(DemandOrder record);
}