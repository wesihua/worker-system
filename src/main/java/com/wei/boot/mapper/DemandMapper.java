package com.wei.boot.mapper;

import com.wei.boot.model.Demand;
import com.wei.boot.model.DemandExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DemandMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    int countByExample(DemandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    int deleteByExample(DemandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    int insert(Demand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    int insertSelective(Demand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    List<Demand> selectByExample(DemandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    Demand selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    int updateByExampleSelective(@Param("record") Demand record, @Param("example") DemandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    int updateByExample(@Param("record") Demand record, @Param("example") DemandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    int updateByPrimaryKeySelective(Demand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand
     *
     * @mbggenerated Sun Sep 16 21:59:24 CST 2018
     */
    int updateByPrimaryKey(Demand record);

	int selectCount(Map<String, Object> map);

	List<Demand> selectByPage(Map<String, Object> map);

	List<Map<String, String>> statisticsByState();

	int selectCampanyLastDemandNumber(Map<String, Object> map);
	
	
	int selectCountByTime(Map<String, Object> map);
	
	int selectAllCount();
	
	List<Demand> selectByPage4Close(Map<String, Object> map);
	
	int selectCount4Close(Map<String, Object> map);
	
	Demand selectDetail(int demandId);
	
	List<Demand> selectUnConfirmedDemand(int demandId);
	
}