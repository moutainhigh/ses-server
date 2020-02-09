package com.redescooter.ses.service.mobile.b.dm.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CorExpressOrderTraceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CorExpressOrderTraceExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
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

        public Criteria andExpressDeliveryIdIsNull() {
            addCriterion("express_delivery_id is null");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdIsNotNull() {
            addCriterion("express_delivery_id is not null");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdEqualTo(Long value) {
            addCriterion("express_delivery_id =", value, "expressDeliveryId");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdNotEqualTo(Long value) {
            addCriterion("express_delivery_id <>", value, "expressDeliveryId");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdGreaterThan(Long value) {
            addCriterion("express_delivery_id >", value, "expressDeliveryId");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("express_delivery_id >=", value, "expressDeliveryId");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdLessThan(Long value) {
            addCriterion("express_delivery_id <", value, "expressDeliveryId");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdLessThanOrEqualTo(Long value) {
            addCriterion("express_delivery_id <=", value, "expressDeliveryId");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdIn(List<Long> values) {
            addCriterion("express_delivery_id in", values, "expressDeliveryId");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdNotIn(List<Long> values) {
            addCriterion("express_delivery_id not in", values, "expressDeliveryId");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdBetween(Long value1, Long value2) {
            addCriterion("express_delivery_id between", value1, value2, "expressDeliveryId");
            return (Criteria) this;
        }

        public Criteria andExpressDeliveryIdNotBetween(Long value1, Long value2) {
            addCriterion("express_delivery_id not between", value1, value2, "expressDeliveryId");
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

        public Criteria andTenantIdEqualTo(Long value) {
            addCriterion("tenant_id =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(Long value) {
            addCriterion("tenant_id <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(Long value) {
            addCriterion("tenant_id >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tenant_id >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(Long value) {
            addCriterion("tenant_id <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(Long value) {
            addCriterion("tenant_id <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<Long> values) {
            addCriterion("tenant_id in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<Long> values) {
            addCriterion("tenant_id not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(Long value1, Long value2) {
            addCriterion("tenant_id between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(Long value1, Long value2) {
            addCriterion("tenant_id not between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdIsNull() {
            addCriterion("express_order_id is null");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdIsNotNull() {
            addCriterion("express_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdEqualTo(Long value) {
            addCriterion("express_order_id =", value, "expressOrderId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdNotEqualTo(Long value) {
            addCriterion("express_order_id <>", value, "expressOrderId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdGreaterThan(Long value) {
            addCriterion("express_order_id >", value, "expressOrderId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("express_order_id >=", value, "expressOrderId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdLessThan(Long value) {
            addCriterion("express_order_id <", value, "expressOrderId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("express_order_id <=", value, "expressOrderId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdIn(List<Long> values) {
            addCriterion("express_order_id in", values, "expressOrderId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdNotIn(List<Long> values) {
            addCriterion("express_order_id not in", values, "expressOrderId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdBetween(Long value1, Long value2) {
            addCriterion("express_order_id between", value1, value2, "expressOrderId");
            return (Criteria) this;
        }

        public Criteria andExpressOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("express_order_id not between", value1, value2, "expressOrderId");
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

        public Criteria andEventIsNull() {
            addCriterion("event is null");
            return (Criteria) this;
        }

        public Criteria andEventIsNotNull() {
            addCriterion("event is not null");
            return (Criteria) this;
        }

        public Criteria andEventEqualTo(String value) {
            addCriterion("event =", value, "event");
            return (Criteria) this;
        }

        public Criteria andEventNotEqualTo(String value) {
            addCriterion("event <>", value, "event");
            return (Criteria) this;
        }

        public Criteria andEventGreaterThan(String value) {
            addCriterion("event >", value, "event");
            return (Criteria) this;
        }

        public Criteria andEventGreaterThanOrEqualTo(String value) {
            addCriterion("event >=", value, "event");
            return (Criteria) this;
        }

        public Criteria andEventLessThan(String value) {
            addCriterion("event <", value, "event");
            return (Criteria) this;
        }

        public Criteria andEventLessThanOrEqualTo(String value) {
            addCriterion("event <=", value, "event");
            return (Criteria) this;
        }

        public Criteria andEventLike(String value) {
            addCriterion("event like", value, "event");
            return (Criteria) this;
        }

        public Criteria andEventNotLike(String value) {
            addCriterion("event not like", value, "event");
            return (Criteria) this;
        }

        public Criteria andEventIn(List<String> values) {
            addCriterion("event in", values, "event");
            return (Criteria) this;
        }

        public Criteria andEventNotIn(List<String> values) {
            addCriterion("event not in", values, "event");
            return (Criteria) this;
        }

        public Criteria andEventBetween(String value1, String value2) {
            addCriterion("event between", value1, value2, "event");
            return (Criteria) this;
        }

        public Criteria andEventNotBetween(String value1, String value2) {
            addCriterion("event not between", value1, value2, "event");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andEventTimeIsNull() {
            addCriterion("event_time is null");
            return (Criteria) this;
        }

        public Criteria andEventTimeIsNotNull() {
            addCriterion("event_time is not null");
            return (Criteria) this;
        }

        public Criteria andEventTimeEqualTo(Date value) {
            addCriterion("event_time =", value, "eventTime");
            return (Criteria) this;
        }

        public Criteria andEventTimeNotEqualTo(Date value) {
            addCriterion("event_time <>", value, "eventTime");
            return (Criteria) this;
        }

        public Criteria andEventTimeGreaterThan(Date value) {
            addCriterion("event_time >", value, "eventTime");
            return (Criteria) this;
        }

        public Criteria andEventTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("event_time >=", value, "eventTime");
            return (Criteria) this;
        }

        public Criteria andEventTimeLessThan(Date value) {
            addCriterion("event_time <", value, "eventTime");
            return (Criteria) this;
        }

        public Criteria andEventTimeLessThanOrEqualTo(Date value) {
            addCriterion("event_time <=", value, "eventTime");
            return (Criteria) this;
        }

        public Criteria andEventTimeIn(List<Date> values) {
            addCriterion("event_time in", values, "eventTime");
            return (Criteria) this;
        }

        public Criteria andEventTimeNotIn(List<Date> values) {
            addCriterion("event_time not in", values, "eventTime");
            return (Criteria) this;
        }

        public Criteria andEventTimeBetween(Date value1, Date value2) {
            addCriterion("event_time between", value1, value2, "eventTime");
            return (Criteria) this;
        }

        public Criteria andEventTimeNotBetween(Date value1, Date value2) {
            addCriterion("event_time not between", value1, value2, "eventTime");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(BigDecimal value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(BigDecimal value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(BigDecimal value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<BigDecimal> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(BigDecimal value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(BigDecimal value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(BigDecimal value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<BigDecimal> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andGeohashIsNull() {
            addCriterion("geohash is null");
            return (Criteria) this;
        }

        public Criteria andGeohashIsNotNull() {
            addCriterion("geohash is not null");
            return (Criteria) this;
        }

        public Criteria andGeohashEqualTo(String value) {
            addCriterion("geohash =", value, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashNotEqualTo(String value) {
            addCriterion("geohash <>", value, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashGreaterThan(String value) {
            addCriterion("geohash >", value, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashGreaterThanOrEqualTo(String value) {
            addCriterion("geohash >=", value, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashLessThan(String value) {
            addCriterion("geohash <", value, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashLessThanOrEqualTo(String value) {
            addCriterion("geohash <=", value, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashLike(String value) {
            addCriterion("geohash like", value, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashNotLike(String value) {
            addCriterion("geohash not like", value, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashIn(List<String> values) {
            addCriterion("geohash in", values, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashNotIn(List<String> values) {
            addCriterion("geohash not in", values, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashBetween(String value1, String value2) {
            addCriterion("geohash between", value1, value2, "geohash");
            return (Criteria) this;
        }

        public Criteria andGeohashNotBetween(String value1, String value2) {
            addCriterion("geohash not between", value1, value2, "geohash");
            return (Criteria) this;
        }

        public Criteria andScooterIdIsNull() {
            addCriterion("scooter_id is null");
            return (Criteria) this;
        }

        public Criteria andScooterIdIsNotNull() {
            addCriterion("scooter_id is not null");
            return (Criteria) this;
        }

        public Criteria andScooterIdEqualTo(Long value) {
            addCriterion("scooter_id =", value, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterIdNotEqualTo(Long value) {
            addCriterion("scooter_id <>", value, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterIdGreaterThan(Long value) {
            addCriterion("scooter_id >", value, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterIdGreaterThanOrEqualTo(Long value) {
            addCriterion("scooter_id >=", value, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterIdLessThan(Long value) {
            addCriterion("scooter_id <", value, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterIdLessThanOrEqualTo(Long value) {
            addCriterion("scooter_id <=", value, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterIdIn(List<Long> values) {
            addCriterion("scooter_id in", values, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterIdNotIn(List<Long> values) {
            addCriterion("scooter_id not in", values, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterIdBetween(Long value1, Long value2) {
            addCriterion("scooter_id between", value1, value2, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterIdNotBetween(Long value1, Long value2) {
            addCriterion("scooter_id not between", value1, value2, "scooterId");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeIsNull() {
            addCriterion("scooter_longitude is null");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeIsNotNull() {
            addCriterion("scooter_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeEqualTo(BigDecimal value) {
            addCriterion("scooter_longitude =", value, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("scooter_longitude <>", value, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeGreaterThan(BigDecimal value) {
            addCriterion("scooter_longitude >", value, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("scooter_longitude >=", value, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeLessThan(BigDecimal value) {
            addCriterion("scooter_longitude <", value, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("scooter_longitude <=", value, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeIn(List<BigDecimal> values) {
            addCriterion("scooter_longitude in", values, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("scooter_longitude not in", values, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("scooter_longitude between", value1, value2, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("scooter_longitude not between", value1, value2, "scooterLongitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeIsNull() {
            addCriterion("scooter_latitude is null");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeIsNotNull() {
            addCriterion("scooter_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeEqualTo(BigDecimal value) {
            addCriterion("scooter_latitude =", value, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("scooter_latitude <>", value, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeGreaterThan(BigDecimal value) {
            addCriterion("scooter_latitude >", value, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("scooter_latitude >=", value, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeLessThan(BigDecimal value) {
            addCriterion("scooter_latitude <", value, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("scooter_latitude <=", value, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeIn(List<BigDecimal> values) {
            addCriterion("scooter_latitude in", values, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("scooter_latitude not in", values, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("scooter_latitude between", value1, value2, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("scooter_latitude not between", value1, value2, "scooterLatitude");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashIsNull() {
            addCriterion("scooter_geohash is null");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashIsNotNull() {
            addCriterion("scooter_geohash is not null");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashEqualTo(String value) {
            addCriterion("scooter_geohash =", value, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashNotEqualTo(String value) {
            addCriterion("scooter_geohash <>", value, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashGreaterThan(String value) {
            addCriterion("scooter_geohash >", value, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashGreaterThanOrEqualTo(String value) {
            addCriterion("scooter_geohash >=", value, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashLessThan(String value) {
            addCriterion("scooter_geohash <", value, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashLessThanOrEqualTo(String value) {
            addCriterion("scooter_geohash <=", value, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashLike(String value) {
            addCriterion("scooter_geohash like", value, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashNotLike(String value) {
            addCriterion("scooter_geohash not like", value, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashIn(List<String> values) {
            addCriterion("scooter_geohash in", values, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashNotIn(List<String> values) {
            addCriterion("scooter_geohash not in", values, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashBetween(String value1, String value2) {
            addCriterion("scooter_geohash between", value1, value2, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andScooterGeohashNotBetween(String value1, String value2) {
            addCriterion("scooter_geohash not between", value1, value2, "scooterGeohash");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(Long value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(Long value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(Long value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(Long value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(Long value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<Long> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<Long> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(Long value1, Long value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(Long value1, Long value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("created_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("created_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Date value) {
            addCriterion("created_time =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Date value) {
            addCriterion("created_time <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Date value) {
            addCriterion("created_time >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("created_time >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Date value) {
            addCriterion("created_time <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("created_time <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Date> values) {
            addCriterion("created_time in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Date> values) {
            addCriterion("created_time not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Date value1, Date value2) {
            addCriterion("created_time between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("created_time not between", value1, value2, "createdTime");
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