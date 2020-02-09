package com.redescooter.ses.service.mobile.b.dm.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CorExpressDeliveryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CorExpressDeliveryExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andDrIsNull() {
            addCriterion("dr is null");
            return (Criteria) this;
        }

        public Criteria andDrIsNotNull() {
            addCriterion("dr is not null");
            return (Criteria) this;
        }

        public Criteria andDrEqualTo(Integer value) {
            addCriterion("dr =", value, "dr");
            return (Criteria) this;
        }

        public Criteria andDrNotEqualTo(Integer value) {
            addCriterion("dr <>", value, "dr");
            return (Criteria) this;
        }

        public Criteria andDrGreaterThan(Integer value) {
            addCriterion("dr >", value, "dr");
            return (Criteria) this;
        }

        public Criteria andDrGreaterThanOrEqualTo(Integer value) {
            addCriterion("dr >=", value, "dr");
            return (Criteria) this;
        }

        public Criteria andDrLessThan(Integer value) {
            addCriterion("dr <", value, "dr");
            return (Criteria) this;
        }

        public Criteria andDrLessThanOrEqualTo(Integer value) {
            addCriterion("dr <=", value, "dr");
            return (Criteria) this;
        }

        public Criteria andDrIn(List<Integer> values) {
            addCriterion("dr in", values, "dr");
            return (Criteria) this;
        }

        public Criteria andDrNotIn(List<Integer> values) {
            addCriterion("dr not in", values, "dr");
            return (Criteria) this;
        }

        public Criteria andDrBetween(Integer value1, Integer value2) {
            addCriterion("dr between", value1, value2, "dr");
            return (Criteria) this;
        }

        public Criteria andDrNotBetween(Integer value1, Integer value2) {
            addCriterion("dr not between", value1, value2, "dr");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNull() {
            addCriterion("tenant_id is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("tenant_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(Integer value) {
            addCriterion("tenant_id =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(Integer value) {
            addCriterion("tenant_id <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(Integer value) {
            addCriterion("tenant_id >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tenant_id >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(Integer value) {
            addCriterion("tenant_id <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(Integer value) {
            addCriterion("tenant_id <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<Integer> values) {
            addCriterion("tenant_id in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<Integer> values) {
            addCriterion("tenant_id not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(Integer value1, Integer value2) {
            addCriterion("tenant_id between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tenant_id not between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("`result` is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("`result` is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("`result` =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("`result` <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("`result` >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("`result` >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("`result` <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("`result` <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("`result` like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("`result` not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("`result` in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("`result` not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("`result` between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("`result` not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andDriverIdIsNull() {
            addCriterion("driver_id is null");
            return (Criteria) this;
        }

        public Criteria andDriverIdIsNotNull() {
            addCriterion("driver_id is not null");
            return (Criteria) this;
        }

        public Criteria andDriverIdEqualTo(Long value) {
            addCriterion("driver_id =", value, "driverId");
            return (Criteria) this;
        }

        public Criteria andDriverIdNotEqualTo(Long value) {
            addCriterion("driver_id <>", value, "driverId");
            return (Criteria) this;
        }

        public Criteria andDriverIdGreaterThan(Long value) {
            addCriterion("driver_id >", value, "driverId");
            return (Criteria) this;
        }

        public Criteria andDriverIdGreaterThanOrEqualTo(Long value) {
            addCriterion("driver_id >=", value, "driverId");
            return (Criteria) this;
        }

        public Criteria andDriverIdLessThan(Long value) {
            addCriterion("driver_id <", value, "driverId");
            return (Criteria) this;
        }

        public Criteria andDriverIdLessThanOrEqualTo(Long value) {
            addCriterion("driver_id <=", value, "driverId");
            return (Criteria) this;
        }

        public Criteria andDriverIdIn(List<Long> values) {
            addCriterion("driver_id in", values, "driverId");
            return (Criteria) this;
        }

        public Criteria andDriverIdNotIn(List<Long> values) {
            addCriterion("driver_id not in", values, "driverId");
            return (Criteria) this;
        }

        public Criteria andDriverIdBetween(Long value1, Long value2) {
            addCriterion("driver_id between", value1, value2, "driverId");
            return (Criteria) this;
        }

        public Criteria andDriverIdNotBetween(Long value1, Long value2) {
            addCriterion("driver_id not between", value1, value2, "driverId");
            return (Criteria) this;
        }

        public Criteria andOrderSumIsNull() {
            addCriterion("order_sum is null");
            return (Criteria) this;
        }

        public Criteria andOrderSumIsNotNull() {
            addCriterion("order_sum is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSumEqualTo(Integer value) {
            addCriterion("order_sum =", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumNotEqualTo(Integer value) {
            addCriterion("order_sum <>", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumGreaterThan(Integer value) {
            addCriterion("order_sum >", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_sum >=", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumLessThan(Integer value) {
            addCriterion("order_sum <", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumLessThanOrEqualTo(Integer value) {
            addCriterion("order_sum <=", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumIn(List<Integer> values) {
            addCriterion("order_sum in", values, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumNotIn(List<Integer> values) {
            addCriterion("order_sum not in", values, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumBetween(Integer value1, Integer value2) {
            addCriterion("order_sum between", value1, value2, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumNotBetween(Integer value1, Integer value2) {
            addCriterion("order_sum not between", value1, value2, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumIsNull() {
            addCriterion("order_complete_num is null");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumIsNotNull() {
            addCriterion("order_complete_num is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumEqualTo(Integer value) {
            addCriterion("order_complete_num =", value, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumNotEqualTo(Integer value) {
            addCriterion("order_complete_num <>", value, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumGreaterThan(Integer value) {
            addCriterion("order_complete_num >", value, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_complete_num >=", value, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumLessThan(Integer value) {
            addCriterion("order_complete_num <", value, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumLessThanOrEqualTo(Integer value) {
            addCriterion("order_complete_num <=", value, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumIn(List<Integer> values) {
            addCriterion("order_complete_num in", values, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumNotIn(List<Integer> values) {
            addCriterion("order_complete_num not in", values, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumBetween(Integer value1, Integer value2) {
            addCriterion("order_complete_num between", value1, value2, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andOrderCompleteNumNotBetween(Integer value1, Integer value2) {
            addCriterion("order_complete_num not between", value1, value2, "orderCompleteNum");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIsNull() {
            addCriterion("delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIsNotNull() {
            addCriterion("delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date =", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date <>", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThan(Date value) {
            addCriterionForJDBCDate("delivery_date >", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date >=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThan(Date value) {
            addCriterionForJDBCDate("delivery_date <", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date <=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIn(List<Date> values) {
            addCriterionForJDBCDate("delivery_date in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("delivery_date not in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("delivery_date between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("delivery_date not between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeIsNull() {
            addCriterion("delivery_start_time is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeIsNotNull() {
            addCriterion("delivery_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeEqualTo(Date value) {
            addCriterion("delivery_start_time =", value, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeNotEqualTo(Date value) {
            addCriterion("delivery_start_time <>", value, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeGreaterThan(Date value) {
            addCriterion("delivery_start_time >", value, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("delivery_start_time >=", value, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeLessThan(Date value) {
            addCriterion("delivery_start_time <", value, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("delivery_start_time <=", value, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeIn(List<Date> values) {
            addCriterion("delivery_start_time in", values, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeNotIn(List<Date> values) {
            addCriterion("delivery_start_time not in", values, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeBetween(Date value1, Date value2) {
            addCriterion("delivery_start_time between", value1, value2, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("delivery_start_time not between", value1, value2, "deliveryStartTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeIsNull() {
            addCriterion("delivery_end_time is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeIsNotNull() {
            addCriterion("delivery_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeEqualTo(Date value) {
            addCriterion("delivery_end_time =", value, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeNotEqualTo(Date value) {
            addCriterion("delivery_end_time <>", value, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeGreaterThan(Date value) {
            addCriterion("delivery_end_time >", value, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("delivery_end_time >=", value, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeLessThan(Date value) {
            addCriterion("delivery_end_time <", value, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("delivery_end_time <=", value, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeIn(List<Date> values) {
            addCriterion("delivery_end_time in", values, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeNotIn(List<Date> values) {
            addCriterion("delivery_end_time not in", values, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeBetween(Date value1, Date value2) {
            addCriterion("delivery_end_time between", value1, value2, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("delivery_end_time not between", value1, value2, "deliveryEndTime");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageIsNull() {
            addCriterion("driven_mileage is null");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageIsNotNull() {
            addCriterion("driven_mileage is not null");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageEqualTo(BigDecimal value) {
            addCriterion("driven_mileage =", value, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageNotEqualTo(BigDecimal value) {
            addCriterion("driven_mileage <>", value, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageGreaterThan(BigDecimal value) {
            addCriterion("driven_mileage >", value, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("driven_mileage >=", value, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageLessThan(BigDecimal value) {
            addCriterion("driven_mileage <", value, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("driven_mileage <=", value, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageIn(List<BigDecimal> values) {
            addCriterion("driven_mileage in", values, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageNotIn(List<BigDecimal> values) {
            addCriterion("driven_mileage not in", values, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("driven_mileage between", value1, value2, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenMileageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("driven_mileage not between", value1, value2, "drivenMileage");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationIsNull() {
            addCriterion("driven_duration is null");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationIsNotNull() {
            addCriterion("driven_duration is not null");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationEqualTo(Integer value) {
            addCriterion("driven_duration =", value, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationNotEqualTo(Integer value) {
            addCriterion("driven_duration <>", value, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationGreaterThan(Integer value) {
            addCriterion("driven_duration >", value, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("driven_duration >=", value, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationLessThan(Integer value) {
            addCriterion("driven_duration <", value, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationLessThanOrEqualTo(Integer value) {
            addCriterion("driven_duration <=", value, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationIn(List<Integer> values) {
            addCriterion("driven_duration in", values, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationNotIn(List<Integer> values) {
            addCriterion("driven_duration not in", values, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationBetween(Integer value1, Integer value2) {
            addCriterion("driven_duration between", value1, value2, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andDrivenDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("driven_duration not between", value1, value2, "drivenDuration");
            return (Criteria) this;
        }

        public Criteria andCo2IsNull() {
            addCriterion("co2 is null");
            return (Criteria) this;
        }

        public Criteria andCo2IsNotNull() {
            addCriterion("co2 is not null");
            return (Criteria) this;
        }

        public Criteria andCo2EqualTo(BigDecimal value) {
            addCriterion("co2 =", value, "co2");
            return (Criteria) this;
        }

        public Criteria andCo2NotEqualTo(BigDecimal value) {
            addCriterion("co2 <>", value, "co2");
            return (Criteria) this;
        }

        public Criteria andCo2GreaterThan(BigDecimal value) {
            addCriterion("co2 >", value, "co2");
            return (Criteria) this;
        }

        public Criteria andCo2GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("co2 >=", value, "co2");
            return (Criteria) this;
        }

        public Criteria andCo2LessThan(BigDecimal value) {
            addCriterion("co2 <", value, "co2");
            return (Criteria) this;
        }

        public Criteria andCo2LessThanOrEqualTo(BigDecimal value) {
            addCriterion("co2 <=", value, "co2");
            return (Criteria) this;
        }

        public Criteria andCo2In(List<BigDecimal> values) {
            addCriterion("co2 in", values, "co2");
            return (Criteria) this;
        }

        public Criteria andCo2NotIn(List<BigDecimal> values) {
            addCriterion("co2 not in", values, "co2");
            return (Criteria) this;
        }

        public Criteria andCo2Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("co2 between", value1, value2, "co2");
            return (Criteria) this;
        }

        public Criteria andCo2NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("co2 not between", value1, value2, "co2");
            return (Criteria) this;
        }

        public Criteria andSavingsIsNull() {
            addCriterion("savings is null");
            return (Criteria) this;
        }

        public Criteria andSavingsIsNotNull() {
            addCriterion("savings is not null");
            return (Criteria) this;
        }

        public Criteria andSavingsEqualTo(BigDecimal value) {
            addCriterion("savings =", value, "savings");
            return (Criteria) this;
        }

        public Criteria andSavingsNotEqualTo(BigDecimal value) {
            addCriterion("savings <>", value, "savings");
            return (Criteria) this;
        }

        public Criteria andSavingsGreaterThan(BigDecimal value) {
            addCriterion("savings >", value, "savings");
            return (Criteria) this;
        }

        public Criteria andSavingsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("savings >=", value, "savings");
            return (Criteria) this;
        }

        public Criteria andSavingsLessThan(BigDecimal value) {
            addCriterion("savings <", value, "savings");
            return (Criteria) this;
        }

        public Criteria andSavingsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("savings <=", value, "savings");
            return (Criteria) this;
        }

        public Criteria andSavingsIn(List<BigDecimal> values) {
            addCriterion("savings in", values, "savings");
            return (Criteria) this;
        }

        public Criteria andSavingsNotIn(List<BigDecimal> values) {
            addCriterion("savings not in", values, "savings");
            return (Criteria) this;
        }

        public Criteria andSavingsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("savings between", value1, value2, "savings");
            return (Criteria) this;
        }

        public Criteria andSavingsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("savings not between", value1, value2, "savings");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(Long value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(Long value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(Long value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(Long value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(Long value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(Long value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<Long> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<Long> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(Long value1, Long value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(Long value1, Long value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
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

        public Criteria andUpdatedByIsNull() {
            addCriterion("updated_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIsNotNull() {
            addCriterion("updated_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByEqualTo(Long value) {
            addCriterion("updated_by =", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotEqualTo(Long value) {
            addCriterion("updated_by <>", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThan(Long value) {
            addCriterion("updated_by >", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("updated_by >=", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThan(Long value) {
            addCriterion("updated_by <", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThanOrEqualTo(Long value) {
            addCriterion("updated_by <=", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIn(List<Long> values) {
            addCriterion("updated_by in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotIn(List<Long> values) {
            addCriterion("updated_by not in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByBetween(Long value1, Long value2) {
            addCriterion("updated_by between", value1, value2, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotBetween(Long value1, Long value2) {
            addCriterion("updated_by not between", value1, value2, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNull() {
            addCriterion("updated_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNotNull() {
            addCriterion("updated_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeEqualTo(Date value) {
            addCriterion("updated_time =", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotEqualTo(Date value) {
            addCriterion("updated_time <>", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThan(Date value) {
            addCriterion("updated_time >", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_time >=", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThan(Date value) {
            addCriterion("updated_time <", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("updated_time <=", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIn(List<Date> values) {
            addCriterion("updated_time in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotIn(List<Date> values) {
            addCriterion("updated_time not in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeBetween(Date value1, Date value2) {
            addCriterion("updated_time between", value1, value2, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("updated_time not between", value1, value2, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andDef1IsNull() {
            addCriterion("def1 is null");
            return (Criteria) this;
        }

        public Criteria andDef1IsNotNull() {
            addCriterion("def1 is not null");
            return (Criteria) this;
        }

        public Criteria andDef1EqualTo(String value) {
            addCriterion("def1 =", value, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1NotEqualTo(String value) {
            addCriterion("def1 <>", value, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1GreaterThan(String value) {
            addCriterion("def1 >", value, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1GreaterThanOrEqualTo(String value) {
            addCriterion("def1 >=", value, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1LessThan(String value) {
            addCriterion("def1 <", value, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1LessThanOrEqualTo(String value) {
            addCriterion("def1 <=", value, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1Like(String value) {
            addCriterion("def1 like", value, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1NotLike(String value) {
            addCriterion("def1 not like", value, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1In(List<String> values) {
            addCriterion("def1 in", values, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1NotIn(List<String> values) {
            addCriterion("def1 not in", values, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1Between(String value1, String value2) {
            addCriterion("def1 between", value1, value2, "def1");
            return (Criteria) this;
        }

        public Criteria andDef1NotBetween(String value1, String value2) {
            addCriterion("def1 not between", value1, value2, "def1");
            return (Criteria) this;
        }

        public Criteria andDef2IsNull() {
            addCriterion("def2 is null");
            return (Criteria) this;
        }

        public Criteria andDef2IsNotNull() {
            addCriterion("def2 is not null");
            return (Criteria) this;
        }

        public Criteria andDef2EqualTo(String value) {
            addCriterion("def2 =", value, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2NotEqualTo(String value) {
            addCriterion("def2 <>", value, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2GreaterThan(String value) {
            addCriterion("def2 >", value, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2GreaterThanOrEqualTo(String value) {
            addCriterion("def2 >=", value, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2LessThan(String value) {
            addCriterion("def2 <", value, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2LessThanOrEqualTo(String value) {
            addCriterion("def2 <=", value, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2Like(String value) {
            addCriterion("def2 like", value, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2NotLike(String value) {
            addCriterion("def2 not like", value, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2In(List<String> values) {
            addCriterion("def2 in", values, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2NotIn(List<String> values) {
            addCriterion("def2 not in", values, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2Between(String value1, String value2) {
            addCriterion("def2 between", value1, value2, "def2");
            return (Criteria) this;
        }

        public Criteria andDef2NotBetween(String value1, String value2) {
            addCriterion("def2 not between", value1, value2, "def2");
            return (Criteria) this;
        }

        public Criteria andDef3IsNull() {
            addCriterion("def3 is null");
            return (Criteria) this;
        }

        public Criteria andDef3IsNotNull() {
            addCriterion("def3 is not null");
            return (Criteria) this;
        }

        public Criteria andDef3EqualTo(String value) {
            addCriterion("def3 =", value, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3NotEqualTo(String value) {
            addCriterion("def3 <>", value, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3GreaterThan(String value) {
            addCriterion("def3 >", value, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3GreaterThanOrEqualTo(String value) {
            addCriterion("def3 >=", value, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3LessThan(String value) {
            addCriterion("def3 <", value, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3LessThanOrEqualTo(String value) {
            addCriterion("def3 <=", value, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3Like(String value) {
            addCriterion("def3 like", value, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3NotLike(String value) {
            addCriterion("def3 not like", value, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3In(List<String> values) {
            addCriterion("def3 in", values, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3NotIn(List<String> values) {
            addCriterion("def3 not in", values, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3Between(String value1, String value2) {
            addCriterion("def3 between", value1, value2, "def3");
            return (Criteria) this;
        }

        public Criteria andDef3NotBetween(String value1, String value2) {
            addCriterion("def3 not between", value1, value2, "def3");
            return (Criteria) this;
        }

        public Criteria andDef5IsNull() {
            addCriterion("def5 is null");
            return (Criteria) this;
        }

        public Criteria andDef5IsNotNull() {
            addCriterion("def5 is not null");
            return (Criteria) this;
        }

        public Criteria andDef5EqualTo(String value) {
            addCriterion("def5 =", value, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5NotEqualTo(String value) {
            addCriterion("def5 <>", value, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5GreaterThan(String value) {
            addCriterion("def5 >", value, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5GreaterThanOrEqualTo(String value) {
            addCriterion("def5 >=", value, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5LessThan(String value) {
            addCriterion("def5 <", value, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5LessThanOrEqualTo(String value) {
            addCriterion("def5 <=", value, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5Like(String value) {
            addCriterion("def5 like", value, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5NotLike(String value) {
            addCriterion("def5 not like", value, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5In(List<String> values) {
            addCriterion("def5 in", values, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5NotIn(List<String> values) {
            addCriterion("def5 not in", values, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5Between(String value1, String value2) {
            addCriterion("def5 between", value1, value2, "def5");
            return (Criteria) this;
        }

        public Criteria andDef5NotBetween(String value1, String value2) {
            addCriterion("def5 not between", value1, value2, "def5");
            return (Criteria) this;
        }

        public Criteria andDef6IsNull() {
            addCriterion("def6 is null");
            return (Criteria) this;
        }

        public Criteria andDef6IsNotNull() {
            addCriterion("def6 is not null");
            return (Criteria) this;
        }

        public Criteria andDef6EqualTo(Double value) {
            addCriterion("def6 =", value, "def6");
            return (Criteria) this;
        }

        public Criteria andDef6NotEqualTo(Double value) {
            addCriterion("def6 <>", value, "def6");
            return (Criteria) this;
        }

        public Criteria andDef6GreaterThan(Double value) {
            addCriterion("def6 >", value, "def6");
            return (Criteria) this;
        }

        public Criteria andDef6GreaterThanOrEqualTo(Double value) {
            addCriterion("def6 >=", value, "def6");
            return (Criteria) this;
        }

        public Criteria andDef6LessThan(Double value) {
            addCriterion("def6 <", value, "def6");
            return (Criteria) this;
        }

        public Criteria andDef6LessThanOrEqualTo(Double value) {
            addCriterion("def6 <=", value, "def6");
            return (Criteria) this;
        }

        public Criteria andDef6In(List<Double> values) {
            addCriterion("def6 in", values, "def6");
            return (Criteria) this;
        }

        public Criteria andDef6NotIn(List<Double> values) {
            addCriterion("def6 not in", values, "def6");
            return (Criteria) this;
        }

        public Criteria andDef6Between(Double value1, Double value2) {
            addCriterion("def6 between", value1, value2, "def6");
            return (Criteria) this;
        }

        public Criteria andDef6NotBetween(Double value1, Double value2) {
            addCriterion("def6 not between", value1, value2, "def6");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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