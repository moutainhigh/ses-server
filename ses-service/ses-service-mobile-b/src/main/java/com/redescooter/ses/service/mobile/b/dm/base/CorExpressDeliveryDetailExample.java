package com.redescooter.ses.service.mobile.b.dm.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CorExpressDeliveryDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CorExpressDeliveryDetailExample() {
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

        public Criteria andParcelQuantityIsNull() {
            addCriterion("parcel_quantity is null");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityIsNotNull() {
            addCriterion("parcel_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityEqualTo(Integer value) {
            addCriterion("parcel_quantity =", value, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityNotEqualTo(Integer value) {
            addCriterion("parcel_quantity <>", value, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityGreaterThan(Integer value) {
            addCriterion("parcel_quantity >", value, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("parcel_quantity >=", value, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityLessThan(Integer value) {
            addCriterion("parcel_quantity <", value, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("parcel_quantity <=", value, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityIn(List<Integer> values) {
            addCriterion("parcel_quantity in", values, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityNotIn(List<Integer> values) {
            addCriterion("parcel_quantity not in", values, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityBetween(Integer value1, Integer value2) {
            addCriterion("parcel_quantity between", value1, value2, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andParcelQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("parcel_quantity not between", value1, value2, "parcelQuantity");
            return (Criteria) this;
        }

        public Criteria andEtdIsNull() {
            addCriterion("etd is null");
            return (Criteria) this;
        }

        public Criteria andEtdIsNotNull() {
            addCriterion("etd is not null");
            return (Criteria) this;
        }

        public Criteria andEtdEqualTo(Date value) {
            addCriterion("etd =", value, "etd");
            return (Criteria) this;
        }

        public Criteria andEtdNotEqualTo(Date value) {
            addCriterion("etd <>", value, "etd");
            return (Criteria) this;
        }

        public Criteria andEtdGreaterThan(Date value) {
            addCriterion("etd >", value, "etd");
            return (Criteria) this;
        }

        public Criteria andEtdGreaterThanOrEqualTo(Date value) {
            addCriterion("etd >=", value, "etd");
            return (Criteria) this;
        }

        public Criteria andEtdLessThan(Date value) {
            addCriterion("etd <", value, "etd");
            return (Criteria) this;
        }

        public Criteria andEtdLessThanOrEqualTo(Date value) {
            addCriterion("etd <=", value, "etd");
            return (Criteria) this;
        }

        public Criteria andEtdIn(List<Date> values) {
            addCriterion("etd in", values, "etd");
            return (Criteria) this;
        }

        public Criteria andEtdNotIn(List<Date> values) {
            addCriterion("etd not in", values, "etd");
            return (Criteria) this;
        }

        public Criteria andEtdBetween(Date value1, Date value2) {
            addCriterion("etd between", value1, value2, "etd");
            return (Criteria) this;
        }

        public Criteria andEtdNotBetween(Date value1, Date value2) {
            addCriterion("etd not between", value1, value2, "etd");
            return (Criteria) this;
        }

        public Criteria andAtaIsNull() {
            addCriterion("ata is null");
            return (Criteria) this;
        }

        public Criteria andAtaIsNotNull() {
            addCriterion("ata is not null");
            return (Criteria) this;
        }

        public Criteria andAtaEqualTo(Date value) {
            addCriterion("ata =", value, "ata");
            return (Criteria) this;
        }

        public Criteria andAtaNotEqualTo(Date value) {
            addCriterion("ata <>", value, "ata");
            return (Criteria) this;
        }

        public Criteria andAtaGreaterThan(Date value) {
            addCriterion("ata >", value, "ata");
            return (Criteria) this;
        }

        public Criteria andAtaGreaterThanOrEqualTo(Date value) {
            addCriterion("ata >=", value, "ata");
            return (Criteria) this;
        }

        public Criteria andAtaLessThan(Date value) {
            addCriterion("ata <", value, "ata");
            return (Criteria) this;
        }

        public Criteria andAtaLessThanOrEqualTo(Date value) {
            addCriterion("ata <=", value, "ata");
            return (Criteria) this;
        }

        public Criteria andAtaIn(List<Date> values) {
            addCriterion("ata in", values, "ata");
            return (Criteria) this;
        }

        public Criteria andAtaNotIn(List<Date> values) {
            addCriterion("ata not in", values, "ata");
            return (Criteria) this;
        }

        public Criteria andAtaBetween(Date value1, Date value2) {
            addCriterion("ata between", value1, value2, "ata");
            return (Criteria) this;
        }

        public Criteria andAtaNotBetween(Date value1, Date value2) {
            addCriterion("ata not between", value1, value2, "ata");
            return (Criteria) this;
        }

        public Criteria andAtdIsNull() {
            addCriterion("atd is null");
            return (Criteria) this;
        }

        public Criteria andAtdIsNotNull() {
            addCriterion("atd is not null");
            return (Criteria) this;
        }

        public Criteria andAtdEqualTo(Date value) {
            addCriterion("atd =", value, "atd");
            return (Criteria) this;
        }

        public Criteria andAtdNotEqualTo(Date value) {
            addCriterion("atd <>", value, "atd");
            return (Criteria) this;
        }

        public Criteria andAtdGreaterThan(Date value) {
            addCriterion("atd >", value, "atd");
            return (Criteria) this;
        }

        public Criteria andAtdGreaterThanOrEqualTo(Date value) {
            addCriterion("atd >=", value, "atd");
            return (Criteria) this;
        }

        public Criteria andAtdLessThan(Date value) {
            addCriterion("atd <", value, "atd");
            return (Criteria) this;
        }

        public Criteria andAtdLessThanOrEqualTo(Date value) {
            addCriterion("atd <=", value, "atd");
            return (Criteria) this;
        }

        public Criteria andAtdIn(List<Date> values) {
            addCriterion("atd in", values, "atd");
            return (Criteria) this;
        }

        public Criteria andAtdNotIn(List<Date> values) {
            addCriterion("atd not in", values, "atd");
            return (Criteria) this;
        }

        public Criteria andAtdBetween(Date value1, Date value2) {
            addCriterion("atd between", value1, value2, "atd");
            return (Criteria) this;
        }

        public Criteria andAtdNotBetween(Date value1, Date value2) {
            addCriterion("atd not between", value1, value2, "atd");
            return (Criteria) this;
        }

        public Criteria andEtaIsNull() {
            addCriterion("eta is null");
            return (Criteria) this;
        }

        public Criteria andEtaIsNotNull() {
            addCriterion("eta is not null");
            return (Criteria) this;
        }

        public Criteria andEtaEqualTo(Date value) {
            addCriterion("eta =", value, "eta");
            return (Criteria) this;
        }

        public Criteria andEtaNotEqualTo(Date value) {
            addCriterion("eta <>", value, "eta");
            return (Criteria) this;
        }

        public Criteria andEtaGreaterThan(Date value) {
            addCriterion("eta >", value, "eta");
            return (Criteria) this;
        }

        public Criteria andEtaGreaterThanOrEqualTo(Date value) {
            addCriterion("eta >=", value, "eta");
            return (Criteria) this;
        }

        public Criteria andEtaLessThan(Date value) {
            addCriterion("eta <", value, "eta");
            return (Criteria) this;
        }

        public Criteria andEtaLessThanOrEqualTo(Date value) {
            addCriterion("eta <=", value, "eta");
            return (Criteria) this;
        }

        public Criteria andEtaIn(List<Date> values) {
            addCriterion("eta in", values, "eta");
            return (Criteria) this;
        }

        public Criteria andEtaNotIn(List<Date> values) {
            addCriterion("eta not in", values, "eta");
            return (Criteria) this;
        }

        public Criteria andEtaBetween(Date value1, Date value2) {
            addCriterion("eta between", value1, value2, "eta");
            return (Criteria) this;
        }

        public Criteria andEtaNotBetween(Date value1, Date value2) {
            addCriterion("eta not between", value1, value2, "eta");
            return (Criteria) this;
        }

        public Criteria andPrioritySortIsNull() {
            addCriterion("priority_sort is null");
            return (Criteria) this;
        }

        public Criteria andPrioritySortIsNotNull() {
            addCriterion("priority_sort is not null");
            return (Criteria) this;
        }

        public Criteria andPrioritySortEqualTo(Integer value) {
            addCriterion("priority_sort =", value, "prioritySort");
            return (Criteria) this;
        }

        public Criteria andPrioritySortNotEqualTo(Integer value) {
            addCriterion("priority_sort <>", value, "prioritySort");
            return (Criteria) this;
        }

        public Criteria andPrioritySortGreaterThan(Integer value) {
            addCriterion("priority_sort >", value, "prioritySort");
            return (Criteria) this;
        }

        public Criteria andPrioritySortGreaterThanOrEqualTo(Integer value) {
            addCriterion("priority_sort >=", value, "prioritySort");
            return (Criteria) this;
        }

        public Criteria andPrioritySortLessThan(Integer value) {
            addCriterion("priority_sort <", value, "prioritySort");
            return (Criteria) this;
        }

        public Criteria andPrioritySortLessThanOrEqualTo(Integer value) {
            addCriterion("priority_sort <=", value, "prioritySort");
            return (Criteria) this;
        }

        public Criteria andPrioritySortIn(List<Integer> values) {
            addCriterion("priority_sort in", values, "prioritySort");
            return (Criteria) this;
        }

        public Criteria andPrioritySortNotIn(List<Integer> values) {
            addCriterion("priority_sort not in", values, "prioritySort");
            return (Criteria) this;
        }

        public Criteria andPrioritySortBetween(Integer value1, Integer value2) {
            addCriterion("priority_sort between", value1, value2, "prioritySort");
            return (Criteria) this;
        }

        public Criteria andPrioritySortNotBetween(Integer value1, Integer value2) {
            addCriterion("priority_sort not between", value1, value2, "prioritySort");
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