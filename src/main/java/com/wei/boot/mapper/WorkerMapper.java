package com.wei.boot.mapper;

import com.wei.boot.model.User;
import com.wei.boot.model.Worker;
import com.wei.boot.model.WorkerExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
}