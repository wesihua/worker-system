package com.wei.boot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wei.boot.model.Worker;
import com.wei.boot.model.WorkerExample;
import com.wei.boot.model.report.ReportInfo;

public interface WorkerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    int countByExample(WorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    int deleteByExample(WorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    int insert(Worker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    int insertSelective(Worker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    List<Worker> selectByExample(WorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    Worker selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    int updateByExampleSelective(@Param("record") Worker record, @Param("example") WorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    int updateByExample(@Param("record") Worker record, @Param("example") WorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    int updateByPrimaryKeySelective(Worker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_worker
     *
     * @mbggenerated Thu Aug 30 11:23:45 CST 2018
     */
    int updateByPrimaryKey(Worker record);
    
    List<Worker> selectByPage(Map<String, Object> map);
    
    int selectCount(Map<String, Object> map);
    
    void insertBatch(List<Worker> workerList);
    
    int selectCountByTime(Map<String, Object> map);
	
	int selectAllCount();
	
	List<ReportInfo> selectSourcePie(Map<String, Object> map);
	
	List<ReportInfo> selectCreateUserPie(Map<String, Object> map);
	
	List<ReportInfo> selectDegreePie(Map<String, Object> map);
	
	List<ReportInfo> selectWorkStatusPie(Map<String, Object> map);
	
	List<ReportInfo> selectMonthBar(Map<String, Object> map);
	
	List<ReportInfo> selectDayBar(Map<String, Object> map);
	
	List<ReportInfo> selectDemandMonthBar(Map<String, Object> map);
	
	List<ReportInfo> selectDemandUnderTakerPie(Map<String, Object> map);
	
	List<ReportInfo> selectDemandStatePie(Map<String, Object> map);
	
	List<ReportInfo> selectOrderMonthBar(Map<String, Object> map);
	
	List<ReportInfo> selectOrderUndertakerPie(Map<String, Object> map);
	
	List<ReportInfo> selectOrderMemberMonthBar(Map<String, Object> map);
	
	List<ReportInfo> selectOrderMemberUndertakerPie(Map<String, Object> map);
	
	List<ReportInfo> selectOrderIncomeMonthBar(Map<String, Object> map);
	
	List<ReportInfo> selectOrderIncomeUndertakerPie(Map<String, Object> map);
	
	List<Map<String, Object>> selectWorkerCount();
	
	List<ReportInfo> selectDemandDayBar(Map<String, Object> map);
	List<ReportInfo> selectOrderDayBar(Map<String, Object> map);
	List<ReportInfo> selectOrderMemberDayBar(Map<String, Object> map);
	List<ReportInfo> selectOrderIncomeDayBar(Map<String, Object> map);

	List<Worker> selectAssignByPage(Map<String, Object> map);

	int selectAssignCount(Map<String, Object> map);
	
}