package com.wei.boot.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderWorkerExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public OrderWorkerExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdIsNull() {
            addCriterion("demand_job_id is null");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdIsNotNull() {
            addCriterion("demand_job_id is not null");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdEqualTo(Integer value) {
            addCriterion("demand_job_id =", value, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdNotEqualTo(Integer value) {
            addCriterion("demand_job_id <>", value, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdGreaterThan(Integer value) {
            addCriterion("demand_job_id >", value, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("demand_job_id >=", value, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdLessThan(Integer value) {
            addCriterion("demand_job_id <", value, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdLessThanOrEqualTo(Integer value) {
            addCriterion("demand_job_id <=", value, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdIn(List<Integer> values) {
            addCriterion("demand_job_id in", values, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdNotIn(List<Integer> values) {
            addCriterion("demand_job_id not in", values, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdBetween(Integer value1, Integer value2) {
            addCriterion("demand_job_id between", value1, value2, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andDemandJobIdNotBetween(Integer value1, Integer value2) {
            addCriterion("demand_job_id not between", value1, value2, "demandJobId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdIsNull() {
            addCriterion("worker_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkerIdIsNotNull() {
            addCriterion("worker_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkerIdEqualTo(Integer value) {
            addCriterion("worker_id =", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdNotEqualTo(Integer value) {
            addCriterion("worker_id <>", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdGreaterThan(Integer value) {
            addCriterion("worker_id >", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("worker_id >=", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdLessThan(Integer value) {
            addCriterion("worker_id <", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdLessThanOrEqualTo(Integer value) {
            addCriterion("worker_id <=", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdIn(List<Integer> values) {
            addCriterion("worker_id in", values, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdNotIn(List<Integer> values) {
            addCriterion("worker_id not in", values, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdBetween(Integer value1, Integer value2) {
            addCriterion("worker_id between", value1, value2, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("worker_id not between", value1, value2, "workerId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andSignSalaryIsNull() {
            addCriterion("sign_salary is null");
            return (Criteria) this;
        }

        public Criteria andSignSalaryIsNotNull() {
            addCriterion("sign_salary is not null");
            return (Criteria) this;
        }

        public Criteria andSignSalaryEqualTo(String value) {
            addCriterion("sign_salary =", value, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryNotEqualTo(String value) {
            addCriterion("sign_salary <>", value, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryGreaterThan(String value) {
            addCriterion("sign_salary >", value, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryGreaterThanOrEqualTo(String value) {
            addCriterion("sign_salary >=", value, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryLessThan(String value) {
            addCriterion("sign_salary <", value, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryLessThanOrEqualTo(String value) {
            addCriterion("sign_salary <=", value, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryLike(String value) {
            addCriterion("sign_salary like", value, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryNotLike(String value) {
            addCriterion("sign_salary not like", value, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryIn(List<String> values) {
            addCriterion("sign_salary in", values, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryNotIn(List<String> values) {
            addCriterion("sign_salary not in", values, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryBetween(String value1, String value2) {
            addCriterion("sign_salary between", value1, value2, "signSalary");
            return (Criteria) this;
        }

        public Criteria andSignSalaryNotBetween(String value1, String value2) {
            addCriterion("sign_salary not between", value1, value2, "signSalary");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeIsNull() {
            addCriterion("business_income is null");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeIsNotNull() {
            addCriterion("business_income is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeEqualTo(String value) {
            addCriterion("business_income =", value, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeNotEqualTo(String value) {
            addCriterion("business_income <>", value, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeGreaterThan(String value) {
            addCriterion("business_income >", value, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeGreaterThanOrEqualTo(String value) {
            addCriterion("business_income >=", value, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeLessThan(String value) {
            addCriterion("business_income <", value, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeLessThanOrEqualTo(String value) {
            addCriterion("business_income <=", value, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeLike(String value) {
            addCriterion("business_income like", value, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeNotLike(String value) {
            addCriterion("business_income not like", value, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeIn(List<String> values) {
            addCriterion("business_income in", values, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeNotIn(List<String> values) {
            addCriterion("business_income not in", values, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeBetween(String value1, String value2) {
            addCriterion("business_income between", value1, value2, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andBusinessIncomeNotBetween(String value1, String value2) {
            addCriterion("business_income not between", value1, value2, "businessIncome");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeIsNull() {
            addCriterion("arrive_work_time is null");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeIsNotNull() {
            addCriterion("arrive_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeEqualTo(Date value) {
            addCriterion("arrive_work_time =", value, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeNotEqualTo(Date value) {
            addCriterion("arrive_work_time <>", value, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeGreaterThan(Date value) {
            addCriterion("arrive_work_time >", value, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("arrive_work_time >=", value, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeLessThan(Date value) {
            addCriterion("arrive_work_time <", value, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeLessThanOrEqualTo(Date value) {
            addCriterion("arrive_work_time <=", value, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeIn(List<Date> values) {
            addCriterion("arrive_work_time in", values, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeNotIn(List<Date> values) {
            addCriterion("arrive_work_time not in", values, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeBetween(Date value1, Date value2) {
            addCriterion("arrive_work_time between", value1, value2, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andArriveWorkTimeNotBetween(Date value1, Date value2) {
            addCriterion("arrive_work_time not between", value1, value2, "arriveWorkTime");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Integer value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Integer value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Integer value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Integer value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Integer> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Integer> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Integer value1, Integer value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Integer value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Integer value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Integer value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Integer value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Integer> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Integer> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated do_not_delete_during_merge Sun Oct 21 16:58:48 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_yx_order_worker
     *
     * @mbggenerated Sun Oct 21 16:58:48 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}