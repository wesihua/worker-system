package com.wei.boot.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wei.boot.model.DemandOrder;
import com.wei.boot.model.DemandOrderExample;
import com.wei.boot.model.report.CompanyReportInfo;

public interface DemandOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    int countByExample(DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    int deleteByExample(DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    int insert(DemandOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    int insertSelective(DemandOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    List<DemandOrder> selectByExample(DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    DemandOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    int updateByExampleSelective(@Param("record") DemandOrder record, @Param("example") DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    int updateByExample(@Param("record") DemandOrder record, @Param("example") DemandOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    int updateByPrimaryKeySelective(DemandOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_demand_order
     *
     * @mbggenerated Sun Oct 21 16:49:38 CST 2018
     */
    int updateByPrimaryKey(DemandOrder record);
    
    
    int selectOrderCountByTime(Map<String, Object> map);
    
    int selectAllOrderCount();
    
    
    int selectOrderWorkerCountByTime(Map<String, Object> map);
    
    int selectAllOrderWorkerCount();
    
    
    BigDecimal selectIncomeByTime(Map<String, Object> map);
    
    BigDecimal selectAllIncome();
    
    List<DemandOrder> selectByPage(Map<String, Object> map);
    
    int selectCount(Map<String, Object> map);
    
    List<Map<String, Object>> selectOrderCount();
    List<Map<String, Object>> selectIncomeCount();
    List<Map<String, Object>> selectOrderWorkerCount();
    List<CompanyReportInfo> selectCompanyOrderReport(Map<String, Object> map);
    int selectCompanyOrderReportCount(Map<String, Object> map);
    List<CompanyReportInfo> selectUserOrderReport(Map<String, Object> map);

	BigDecimal selectIncomeByDemandId(Integer demandId);

	int selectSigningCountByDemandId(Integer demandId);
    
	List<DemandOrder> selectByDemandId(int demandId);
	
	CompanyReportInfo selectTotalOrderCount(Map<String, Object> map);
}