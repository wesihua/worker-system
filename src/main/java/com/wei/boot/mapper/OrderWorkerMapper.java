package com.wei.boot.mapper;

import com.wei.boot.model.OrderWorker;
import com.wei.boot.model.OrderWorkerExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OrderWorkerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    int countByExample(OrderWorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    int deleteByExample(OrderWorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    int insert(OrderWorker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    int insertSelective(OrderWorker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    List<OrderWorker> selectByExample(OrderWorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    OrderWorker selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    int updateByExampleSelective(@Param("record") OrderWorker record, @Param("example") OrderWorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    int updateByExample(@Param("record") OrderWorker record, @Param("example") OrderWorkerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    int updateByPrimaryKeySelective(OrderWorker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    int updateByPrimaryKey(OrderWorker record);

	List<OrderWorker> selectByPage(Map<String, Object> map);

	int selectCount(Map<String, Object> map);

	List<String> selectIncomeByDemandJobIds(List<Integer> demandJobIds);

	void updateOrderIdByDemandJobIds(Map<String, Object> map);
}