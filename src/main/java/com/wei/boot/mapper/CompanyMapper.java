package com.wei.boot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wei.boot.model.Company;
import com.wei.boot.model.CompanyExample;

public interface CompanyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    int countByExample(CompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    int deleteByExample(CompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    int insert(Company record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    int insertSelective(Company record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    List<Company> selectByExample(CompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    Company selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    int updateByExampleSelective(@Param("record") Company record, @Param("example") CompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    int updateByExample(@Param("record") Company record, @Param("example") CompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    int updateByPrimaryKeySelective(Company record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_company
     *
     * @mbggenerated Sat Aug 11 17:42:36 CST 2018
     */
    int updateByPrimaryKey(Company record);
    
    /**
     * 分页查询
     * @param map
     * @return
     */
    List<Company> selectByPage(Map<String, Object> map);
    /**
	 * 查询企业总数，用于分页
	 * @param company
	 * @return
	 */
	public int selectCount(Map<String, Object> map);
}