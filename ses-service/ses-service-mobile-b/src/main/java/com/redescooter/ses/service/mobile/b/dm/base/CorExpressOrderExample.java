package com.redescooter.ses.service.mobile.b.dm.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CorExpressOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CorExpressOrderExample() {
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

        public Criteria andBatchNoIsNull() {
            addCriterion("batch_no is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("batch_no =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("batch_no <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("batch_no >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("batch_no >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("batch_no <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("batch_no <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("batch_no like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("batch_no not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("batch_no in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("batch_no not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("batch_no between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("batch_no not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
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

        public Criteria andRecipientCountryIsNull() {
            addCriterion("recipient_country is null");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryIsNotNull() {
            addCriterion("recipient_country is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryEqualTo(String value) {
            addCriterion("recipient_country =", value, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryNotEqualTo(String value) {
            addCriterion("recipient_country <>", value, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryGreaterThan(String value) {
            addCriterion("recipient_country >", value, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_country >=", value, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryLessThan(String value) {
            addCriterion("recipient_country <", value, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryLessThanOrEqualTo(String value) {
            addCriterion("recipient_country <=", value, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryLike(String value) {
            addCriterion("recipient_country like", value, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryNotLike(String value) {
            addCriterion("recipient_country not like", value, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryIn(List<String> values) {
            addCriterion("recipient_country in", values, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryNotIn(List<String> values) {
            addCriterion("recipient_country not in", values, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryBetween(String value1, String value2) {
            addCriterion("recipient_country between", value1, value2, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientCountryNotBetween(String value1, String value2) {
            addCriterion("recipient_country not between", value1, value2, "recipientCountry");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceIsNull() {
            addCriterion("recipient_province is null");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceIsNotNull() {
            addCriterion("recipient_province is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceEqualTo(String value) {
            addCriterion("recipient_province =", value, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceNotEqualTo(String value) {
            addCriterion("recipient_province <>", value, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceGreaterThan(String value) {
            addCriterion("recipient_province >", value, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_province >=", value, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceLessThan(String value) {
            addCriterion("recipient_province <", value, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceLessThanOrEqualTo(String value) {
            addCriterion("recipient_province <=", value, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceLike(String value) {
            addCriterion("recipient_province like", value, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceNotLike(String value) {
            addCriterion("recipient_province not like", value, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceIn(List<String> values) {
            addCriterion("recipient_province in", values, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceNotIn(List<String> values) {
            addCriterion("recipient_province not in", values, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceBetween(String value1, String value2) {
            addCriterion("recipient_province between", value1, value2, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientProvinceNotBetween(String value1, String value2) {
            addCriterion("recipient_province not between", value1, value2, "recipientProvince");
            return (Criteria) this;
        }

        public Criteria andRecipientCityIsNull() {
            addCriterion("recipient_city is null");
            return (Criteria) this;
        }

        public Criteria andRecipientCityIsNotNull() {
            addCriterion("recipient_city is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientCityEqualTo(String value) {
            addCriterion("recipient_city =", value, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityNotEqualTo(String value) {
            addCriterion("recipient_city <>", value, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityGreaterThan(String value) {
            addCriterion("recipient_city >", value, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_city >=", value, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityLessThan(String value) {
            addCriterion("recipient_city <", value, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityLessThanOrEqualTo(String value) {
            addCriterion("recipient_city <=", value, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityLike(String value) {
            addCriterion("recipient_city like", value, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityNotLike(String value) {
            addCriterion("recipient_city not like", value, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityIn(List<String> values) {
            addCriterion("recipient_city in", values, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityNotIn(List<String> values) {
            addCriterion("recipient_city not in", values, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityBetween(String value1, String value2) {
            addCriterion("recipient_city between", value1, value2, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientCityNotBetween(String value1, String value2) {
            addCriterion("recipient_city not between", value1, value2, "recipientCity");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeIsNull() {
            addCriterion("recipient_postcode is null");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeIsNotNull() {
            addCriterion("recipient_postcode is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeEqualTo(String value) {
            addCriterion("recipient_postcode =", value, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeNotEqualTo(String value) {
            addCriterion("recipient_postcode <>", value, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeGreaterThan(String value) {
            addCriterion("recipient_postcode >", value, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_postcode >=", value, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeLessThan(String value) {
            addCriterion("recipient_postcode <", value, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeLessThanOrEqualTo(String value) {
            addCriterion("recipient_postcode <=", value, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeLike(String value) {
            addCriterion("recipient_postcode like", value, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeNotLike(String value) {
            addCriterion("recipient_postcode not like", value, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeIn(List<String> values) {
            addCriterion("recipient_postcode in", values, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeNotIn(List<String> values) {
            addCriterion("recipient_postcode not in", values, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeBetween(String value1, String value2) {
            addCriterion("recipient_postcode between", value1, value2, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientPostcodeNotBetween(String value1, String value2) {
            addCriterion("recipient_postcode not between", value1, value2, "recipientPostcode");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressIsNull() {
            addCriterion("recipient_address is null");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressIsNotNull() {
            addCriterion("recipient_address is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressEqualTo(String value) {
            addCriterion("recipient_address =", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressNotEqualTo(String value) {
            addCriterion("recipient_address <>", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressGreaterThan(String value) {
            addCriterion("recipient_address >", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_address >=", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressLessThan(String value) {
            addCriterion("recipient_address <", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressLessThanOrEqualTo(String value) {
            addCriterion("recipient_address <=", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressLike(String value) {
            addCriterion("recipient_address like", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressNotLike(String value) {
            addCriterion("recipient_address not like", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressIn(List<String> values) {
            addCriterion("recipient_address in", values, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressNotIn(List<String> values) {
            addCriterion("recipient_address not in", values, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressBetween(String value1, String value2) {
            addCriterion("recipient_address between", value1, value2, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressNotBetween(String value1, String value2) {
            addCriterion("recipient_address not between", value1, value2, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeIsNull() {
            addCriterion("recipient_latitude is null");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeIsNotNull() {
            addCriterion("recipient_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeEqualTo(BigDecimal value) {
            addCriterion("recipient_latitude =", value, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("recipient_latitude <>", value, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeGreaterThan(BigDecimal value) {
            addCriterion("recipient_latitude >", value, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recipient_latitude >=", value, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeLessThan(BigDecimal value) {
            addCriterion("recipient_latitude <", value, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recipient_latitude <=", value, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeIn(List<BigDecimal> values) {
            addCriterion("recipient_latitude in", values, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("recipient_latitude not in", values, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recipient_latitude between", value1, value2, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recipient_latitude not between", value1, value2, "recipientLatitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeIsNull() {
            addCriterion("recipient_longitude is null");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeIsNotNull() {
            addCriterion("recipient_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeEqualTo(BigDecimal value) {
            addCriterion("recipient_longitude =", value, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("recipient_longitude <>", value, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeGreaterThan(BigDecimal value) {
            addCriterion("recipient_longitude >", value, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recipient_longitude >=", value, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeLessThan(BigDecimal value) {
            addCriterion("recipient_longitude <", value, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recipient_longitude <=", value, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeIn(List<BigDecimal> values) {
            addCriterion("recipient_longitude in", values, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("recipient_longitude not in", values, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recipient_longitude between", value1, value2, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recipient_longitude not between", value1, value2, "recipientLongitude");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashIsNull() {
            addCriterion("recipient_geohash is null");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashIsNotNull() {
            addCriterion("recipient_geohash is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashEqualTo(String value) {
            addCriterion("recipient_geohash =", value, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashNotEqualTo(String value) {
            addCriterion("recipient_geohash <>", value, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashGreaterThan(String value) {
            addCriterion("recipient_geohash >", value, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_geohash >=", value, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashLessThan(String value) {
            addCriterion("recipient_geohash <", value, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashLessThanOrEqualTo(String value) {
            addCriterion("recipient_geohash <=", value, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashLike(String value) {
            addCriterion("recipient_geohash like", value, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashNotLike(String value) {
            addCriterion("recipient_geohash not like", value, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashIn(List<String> values) {
            addCriterion("recipient_geohash in", values, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashNotIn(List<String> values) {
            addCriterion("recipient_geohash not in", values, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashBetween(String value1, String value2) {
            addCriterion("recipient_geohash between", value1, value2, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andRecipientGeohashNotBetween(String value1, String value2) {
            addCriterion("recipient_geohash not between", value1, value2, "recipientGeohash");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceIsNull() {
            addCriterion("customer_reference is null");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceIsNotNull() {
            addCriterion("customer_reference is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceEqualTo(String value) {
            addCriterion("customer_reference =", value, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceNotEqualTo(String value) {
            addCriterion("customer_reference <>", value, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceGreaterThan(String value) {
            addCriterion("customer_reference >", value, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceGreaterThanOrEqualTo(String value) {
            addCriterion("customer_reference >=", value, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceLessThan(String value) {
            addCriterion("customer_reference <", value, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceLessThanOrEqualTo(String value) {
            addCriterion("customer_reference <=", value, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceLike(String value) {
            addCriterion("customer_reference like", value, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceNotLike(String value) {
            addCriterion("customer_reference not like", value, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceIn(List<String> values) {
            addCriterion("customer_reference in", values, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceNotIn(List<String> values) {
            addCriterion("customer_reference not in", values, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceBetween(String value1, String value2) {
            addCriterion("customer_reference between", value1, value2, "customerReference");
            return (Criteria) this;
        }

        public Criteria andCustomerReferenceNotBetween(String value1, String value2) {
            addCriterion("customer_reference not between", value1, value2, "customerReference");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIsNull() {
            addCriterion("recipient_name is null");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIsNotNull() {
            addCriterion("recipient_name is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientNameEqualTo(String value) {
            addCriterion("recipient_name =", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotEqualTo(String value) {
            addCriterion("recipient_name <>", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameGreaterThan(String value) {
            addCriterion("recipient_name >", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_name >=", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLessThan(String value) {
            addCriterion("recipient_name <", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLessThanOrEqualTo(String value) {
            addCriterion("recipient_name <=", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLike(String value) {
            addCriterion("recipient_name like", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotLike(String value) {
            addCriterion("recipient_name not like", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIn(List<String> values) {
            addCriterion("recipient_name in", values, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotIn(List<String> values) {
            addCriterion("recipient_name not in", values, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameBetween(String value1, String value2) {
            addCriterion("recipient_name between", value1, value2, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotBetween(String value1, String value2) {
            addCriterion("recipient_name not between", value1, value2, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyIsNull() {
            addCriterion("recipient_company is null");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyIsNotNull() {
            addCriterion("recipient_company is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyEqualTo(String value) {
            addCriterion("recipient_company =", value, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyNotEqualTo(String value) {
            addCriterion("recipient_company <>", value, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyGreaterThan(String value) {
            addCriterion("recipient_company >", value, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_company >=", value, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyLessThan(String value) {
            addCriterion("recipient_company <", value, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyLessThanOrEqualTo(String value) {
            addCriterion("recipient_company <=", value, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyLike(String value) {
            addCriterion("recipient_company like", value, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyNotLike(String value) {
            addCriterion("recipient_company not like", value, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyIn(List<String> values) {
            addCriterion("recipient_company in", values, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyNotIn(List<String> values) {
            addCriterion("recipient_company not in", values, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyBetween(String value1, String value2) {
            addCriterion("recipient_company between", value1, value2, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientCompanyNotBetween(String value1, String value2) {
            addCriterion("recipient_company not between", value1, value2, "recipientCompany");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneIsNull() {
            addCriterion("recipient_phone is null");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneIsNotNull() {
            addCriterion("recipient_phone is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneEqualTo(String value) {
            addCriterion("recipient_phone =", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotEqualTo(String value) {
            addCriterion("recipient_phone <>", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneGreaterThan(String value) {
            addCriterion("recipient_phone >", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_phone >=", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneLessThan(String value) {
            addCriterion("recipient_phone <", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneLessThanOrEqualTo(String value) {
            addCriterion("recipient_phone <=", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneLike(String value) {
            addCriterion("recipient_phone like", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotLike(String value) {
            addCriterion("recipient_phone not like", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneIn(List<String> values) {
            addCriterion("recipient_phone in", values, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotIn(List<String> values) {
            addCriterion("recipient_phone not in", values, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneBetween(String value1, String value2) {
            addCriterion("recipient_phone between", value1, value2, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotBetween(String value1, String value2) {
            addCriterion("recipient_phone not between", value1, value2, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientMailIsNull() {
            addCriterion("recipient_mail is null");
            return (Criteria) this;
        }

        public Criteria andRecipientMailIsNotNull() {
            addCriterion("recipient_mail is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientMailEqualTo(String value) {
            addCriterion("recipient_mail =", value, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailNotEqualTo(String value) {
            addCriterion("recipient_mail <>", value, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailGreaterThan(String value) {
            addCriterion("recipient_mail >", value, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_mail >=", value, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailLessThan(String value) {
            addCriterion("recipient_mail <", value, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailLessThanOrEqualTo(String value) {
            addCriterion("recipient_mail <=", value, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailLike(String value) {
            addCriterion("recipient_mail like", value, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailNotLike(String value) {
            addCriterion("recipient_mail not like", value, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailIn(List<String> values) {
            addCriterion("recipient_mail in", values, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailNotIn(List<String> values) {
            addCriterion("recipient_mail not in", values, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailBetween(String value1, String value2) {
            addCriterion("recipient_mail between", value1, value2, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientMailNotBetween(String value1, String value2) {
            addCriterion("recipient_mail not between", value1, value2, "recipientMail");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesIsNull() {
            addCriterion("recipient_notes is null");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesIsNotNull() {
            addCriterion("recipient_notes is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesEqualTo(String value) {
            addCriterion("recipient_notes =", value, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesNotEqualTo(String value) {
            addCriterion("recipient_notes <>", value, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesGreaterThan(String value) {
            addCriterion("recipient_notes >", value, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_notes >=", value, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesLessThan(String value) {
            addCriterion("recipient_notes <", value, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesLessThanOrEqualTo(String value) {
            addCriterion("recipient_notes <=", value, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesLike(String value) {
            addCriterion("recipient_notes like", value, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesNotLike(String value) {
            addCriterion("recipient_notes not like", value, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesIn(List<String> values) {
            addCriterion("recipient_notes in", values, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesNotIn(List<String> values) {
            addCriterion("recipient_notes not in", values, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesBetween(String value1, String value2) {
            addCriterion("recipient_notes between", value1, value2, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andRecipientNotesNotBetween(String value1, String value2) {
            addCriterion("recipient_notes not between", value1, value2, "recipientNotes");
            return (Criteria) this;
        }

        public Criteria andSenderCountryIsNull() {
            addCriterion("sender_country is null");
            return (Criteria) this;
        }

        public Criteria andSenderCountryIsNotNull() {
            addCriterion("sender_country is not null");
            return (Criteria) this;
        }

        public Criteria andSenderCountryEqualTo(String value) {
            addCriterion("sender_country =", value, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryNotEqualTo(String value) {
            addCriterion("sender_country <>", value, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryGreaterThan(String value) {
            addCriterion("sender_country >", value, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryGreaterThanOrEqualTo(String value) {
            addCriterion("sender_country >=", value, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryLessThan(String value) {
            addCriterion("sender_country <", value, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryLessThanOrEqualTo(String value) {
            addCriterion("sender_country <=", value, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryLike(String value) {
            addCriterion("sender_country like", value, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryNotLike(String value) {
            addCriterion("sender_country not like", value, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryIn(List<String> values) {
            addCriterion("sender_country in", values, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryNotIn(List<String> values) {
            addCriterion("sender_country not in", values, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryBetween(String value1, String value2) {
            addCriterion("sender_country between", value1, value2, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCountryNotBetween(String value1, String value2) {
            addCriterion("sender_country not between", value1, value2, "senderCountry");
            return (Criteria) this;
        }

        public Criteria andSenderCityIsNull() {
            addCriterion("sender_city is null");
            return (Criteria) this;
        }

        public Criteria andSenderCityIsNotNull() {
            addCriterion("sender_city is not null");
            return (Criteria) this;
        }

        public Criteria andSenderCityEqualTo(String value) {
            addCriterion("sender_city =", value, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityNotEqualTo(String value) {
            addCriterion("sender_city <>", value, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityGreaterThan(String value) {
            addCriterion("sender_city >", value, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityGreaterThanOrEqualTo(String value) {
            addCriterion("sender_city >=", value, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityLessThan(String value) {
            addCriterion("sender_city <", value, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityLessThanOrEqualTo(String value) {
            addCriterion("sender_city <=", value, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityLike(String value) {
            addCriterion("sender_city like", value, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityNotLike(String value) {
            addCriterion("sender_city not like", value, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityIn(List<String> values) {
            addCriterion("sender_city in", values, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityNotIn(List<String> values) {
            addCriterion("sender_city not in", values, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityBetween(String value1, String value2) {
            addCriterion("sender_city between", value1, value2, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderCityNotBetween(String value1, String value2) {
            addCriterion("sender_city not between", value1, value2, "senderCity");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceIsNull() {
            addCriterion("sender_province is null");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceIsNotNull() {
            addCriterion("sender_province is not null");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceEqualTo(String value) {
            addCriterion("sender_province =", value, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceNotEqualTo(String value) {
            addCriterion("sender_province <>", value, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceGreaterThan(String value) {
            addCriterion("sender_province >", value, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("sender_province >=", value, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceLessThan(String value) {
            addCriterion("sender_province <", value, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceLessThanOrEqualTo(String value) {
            addCriterion("sender_province <=", value, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceLike(String value) {
            addCriterion("sender_province like", value, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceNotLike(String value) {
            addCriterion("sender_province not like", value, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceIn(List<String> values) {
            addCriterion("sender_province in", values, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceNotIn(List<String> values) {
            addCriterion("sender_province not in", values, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceBetween(String value1, String value2) {
            addCriterion("sender_province between", value1, value2, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderProvinceNotBetween(String value1, String value2) {
            addCriterion("sender_province not between", value1, value2, "senderProvince");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeIsNull() {
            addCriterion("sender_postcode is null");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeIsNotNull() {
            addCriterion("sender_postcode is not null");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeEqualTo(String value) {
            addCriterion("sender_postcode =", value, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeNotEqualTo(String value) {
            addCriterion("sender_postcode <>", value, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeGreaterThan(String value) {
            addCriterion("sender_postcode >", value, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeGreaterThanOrEqualTo(String value) {
            addCriterion("sender_postcode >=", value, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeLessThan(String value) {
            addCriterion("sender_postcode <", value, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeLessThanOrEqualTo(String value) {
            addCriterion("sender_postcode <=", value, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeLike(String value) {
            addCriterion("sender_postcode like", value, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeNotLike(String value) {
            addCriterion("sender_postcode not like", value, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeIn(List<String> values) {
            addCriterion("sender_postcode in", values, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeNotIn(List<String> values) {
            addCriterion("sender_postcode not in", values, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeBetween(String value1, String value2) {
            addCriterion("sender_postcode between", value1, value2, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderPostcodeNotBetween(String value1, String value2) {
            addCriterion("sender_postcode not between", value1, value2, "senderPostcode");
            return (Criteria) this;
        }

        public Criteria andSenderAddressIsNull() {
            addCriterion("sender_address is null");
            return (Criteria) this;
        }

        public Criteria andSenderAddressIsNotNull() {
            addCriterion("sender_address is not null");
            return (Criteria) this;
        }

        public Criteria andSenderAddressEqualTo(String value) {
            addCriterion("sender_address =", value, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressNotEqualTo(String value) {
            addCriterion("sender_address <>", value, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressGreaterThan(String value) {
            addCriterion("sender_address >", value, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressGreaterThanOrEqualTo(String value) {
            addCriterion("sender_address >=", value, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressLessThan(String value) {
            addCriterion("sender_address <", value, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressLessThanOrEqualTo(String value) {
            addCriterion("sender_address <=", value, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressLike(String value) {
            addCriterion("sender_address like", value, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressNotLike(String value) {
            addCriterion("sender_address not like", value, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressIn(List<String> values) {
            addCriterion("sender_address in", values, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressNotIn(List<String> values) {
            addCriterion("sender_address not in", values, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressBetween(String value1, String value2) {
            addCriterion("sender_address between", value1, value2, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderAddressNotBetween(String value1, String value2) {
            addCriterion("sender_address not between", value1, value2, "senderAddress");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeIsNull() {
            addCriterion("sender_latitude is null");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeIsNotNull() {
            addCriterion("sender_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeEqualTo(BigDecimal value) {
            addCriterion("sender_latitude =", value, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("sender_latitude <>", value, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeGreaterThan(BigDecimal value) {
            addCriterion("sender_latitude >", value, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sender_latitude >=", value, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeLessThan(BigDecimal value) {
            addCriterion("sender_latitude <", value, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sender_latitude <=", value, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeIn(List<BigDecimal> values) {
            addCriterion("sender_latitude in", values, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("sender_latitude not in", values, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sender_latitude between", value1, value2, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sender_latitude not between", value1, value2, "senderLatitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeIsNull() {
            addCriterion("sender_longitude is null");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeIsNotNull() {
            addCriterion("sender_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeEqualTo(BigDecimal value) {
            addCriterion("sender_longitude =", value, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("sender_longitude <>", value, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeGreaterThan(BigDecimal value) {
            addCriterion("sender_longitude >", value, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sender_longitude >=", value, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeLessThan(BigDecimal value) {
            addCriterion("sender_longitude <", value, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sender_longitude <=", value, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeIn(List<BigDecimal> values) {
            addCriterion("sender_longitude in", values, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("sender_longitude not in", values, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sender_longitude between", value1, value2, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sender_longitude not between", value1, value2, "senderLongitude");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashIsNull() {
            addCriterion("sender_geohash is null");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashIsNotNull() {
            addCriterion("sender_geohash is not null");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashEqualTo(String value) {
            addCriterion("sender_geohash =", value, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashNotEqualTo(String value) {
            addCriterion("sender_geohash <>", value, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashGreaterThan(String value) {
            addCriterion("sender_geohash >", value, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashGreaterThanOrEqualTo(String value) {
            addCriterion("sender_geohash >=", value, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashLessThan(String value) {
            addCriterion("sender_geohash <", value, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashLessThanOrEqualTo(String value) {
            addCriterion("sender_geohash <=", value, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashLike(String value) {
            addCriterion("sender_geohash like", value, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashNotLike(String value) {
            addCriterion("sender_geohash not like", value, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashIn(List<String> values) {
            addCriterion("sender_geohash in", values, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashNotIn(List<String> values) {
            addCriterion("sender_geohash not in", values, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashBetween(String value1, String value2) {
            addCriterion("sender_geohash between", value1, value2, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderGeohashNotBetween(String value1, String value2) {
            addCriterion("sender_geohash not between", value1, value2, "senderGeohash");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyIsNull() {
            addCriterion("sender_company is null");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyIsNotNull() {
            addCriterion("sender_company is not null");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyEqualTo(String value) {
            addCriterion("sender_company =", value, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyNotEqualTo(String value) {
            addCriterion("sender_company <>", value, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyGreaterThan(String value) {
            addCriterion("sender_company >", value, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("sender_company >=", value, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyLessThan(String value) {
            addCriterion("sender_company <", value, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyLessThanOrEqualTo(String value) {
            addCriterion("sender_company <=", value, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyLike(String value) {
            addCriterion("sender_company like", value, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyNotLike(String value) {
            addCriterion("sender_company not like", value, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyIn(List<String> values) {
            addCriterion("sender_company in", values, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyNotIn(List<String> values) {
            addCriterion("sender_company not in", values, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyBetween(String value1, String value2) {
            addCriterion("sender_company between", value1, value2, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderCompanyNotBetween(String value1, String value2) {
            addCriterion("sender_company not between", value1, value2, "senderCompany");
            return (Criteria) this;
        }

        public Criteria andSenderNameIsNull() {
            addCriterion("sender_name is null");
            return (Criteria) this;
        }

        public Criteria andSenderNameIsNotNull() {
            addCriterion("sender_name is not null");
            return (Criteria) this;
        }

        public Criteria andSenderNameEqualTo(String value) {
            addCriterion("sender_name =", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotEqualTo(String value) {
            addCriterion("sender_name <>", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameGreaterThan(String value) {
            addCriterion("sender_name >", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameGreaterThanOrEqualTo(String value) {
            addCriterion("sender_name >=", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameLessThan(String value) {
            addCriterion("sender_name <", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameLessThanOrEqualTo(String value) {
            addCriterion("sender_name <=", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameLike(String value) {
            addCriterion("sender_name like", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotLike(String value) {
            addCriterion("sender_name not like", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameIn(List<String> values) {
            addCriterion("sender_name in", values, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotIn(List<String> values) {
            addCriterion("sender_name not in", values, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameBetween(String value1, String value2) {
            addCriterion("sender_name between", value1, value2, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotBetween(String value1, String value2) {
            addCriterion("sender_name not between", value1, value2, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneIsNull() {
            addCriterion("sender_phone is null");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneIsNotNull() {
            addCriterion("sender_phone is not null");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneEqualTo(String value) {
            addCriterion("sender_phone =", value, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneNotEqualTo(String value) {
            addCriterion("sender_phone <>", value, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneGreaterThan(String value) {
            addCriterion("sender_phone >", value, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("sender_phone >=", value, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneLessThan(String value) {
            addCriterion("sender_phone <", value, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneLessThanOrEqualTo(String value) {
            addCriterion("sender_phone <=", value, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneLike(String value) {
            addCriterion("sender_phone like", value, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneNotLike(String value) {
            addCriterion("sender_phone not like", value, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneIn(List<String> values) {
            addCriterion("sender_phone in", values, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneNotIn(List<String> values) {
            addCriterion("sender_phone not in", values, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneBetween(String value1, String value2) {
            addCriterion("sender_phone between", value1, value2, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderPhoneNotBetween(String value1, String value2) {
            addCriterion("sender_phone not between", value1, value2, "senderPhone");
            return (Criteria) this;
        }

        public Criteria andSenderMailIsNull() {
            addCriterion("sender_mail is null");
            return (Criteria) this;
        }

        public Criteria andSenderMailIsNotNull() {
            addCriterion("sender_mail is not null");
            return (Criteria) this;
        }

        public Criteria andSenderMailEqualTo(String value) {
            addCriterion("sender_mail =", value, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailNotEqualTo(String value) {
            addCriterion("sender_mail <>", value, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailGreaterThan(String value) {
            addCriterion("sender_mail >", value, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailGreaterThanOrEqualTo(String value) {
            addCriterion("sender_mail >=", value, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailLessThan(String value) {
            addCriterion("sender_mail <", value, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailLessThanOrEqualTo(String value) {
            addCriterion("sender_mail <=", value, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailLike(String value) {
            addCriterion("sender_mail like", value, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailNotLike(String value) {
            addCriterion("sender_mail not like", value, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailIn(List<String> values) {
            addCriterion("sender_mail in", values, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailNotIn(List<String> values) {
            addCriterion("sender_mail not in", values, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailBetween(String value1, String value2) {
            addCriterion("sender_mail between", value1, value2, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderMailNotBetween(String value1, String value2) {
            addCriterion("sender_mail not between", value1, value2, "senderMail");
            return (Criteria) this;
        }

        public Criteria andSenderNotesIsNull() {
            addCriterion("sender_notes is null");
            return (Criteria) this;
        }

        public Criteria andSenderNotesIsNotNull() {
            addCriterion("sender_notes is not null");
            return (Criteria) this;
        }

        public Criteria andSenderNotesEqualTo(String value) {
            addCriterion("sender_notes =", value, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesNotEqualTo(String value) {
            addCriterion("sender_notes <>", value, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesGreaterThan(String value) {
            addCriterion("sender_notes >", value, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesGreaterThanOrEqualTo(String value) {
            addCriterion("sender_notes >=", value, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesLessThan(String value) {
            addCriterion("sender_notes <", value, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesLessThanOrEqualTo(String value) {
            addCriterion("sender_notes <=", value, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesLike(String value) {
            addCriterion("sender_notes like", value, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesNotLike(String value) {
            addCriterion("sender_notes not like", value, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesIn(List<String> values) {
            addCriterion("sender_notes in", values, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesNotIn(List<String> values) {
            addCriterion("sender_notes not in", values, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesBetween(String value1, String value2) {
            addCriterion("sender_notes between", value1, value2, "senderNotes");
            return (Criteria) this;
        }

        public Criteria andSenderNotesNotBetween(String value1, String value2) {
            addCriterion("sender_notes not between", value1, value2, "senderNotes");
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

        public Criteria andAssignFlagIsNull() {
            addCriterion("assign_flag is null");
            return (Criteria) this;
        }

        public Criteria andAssignFlagIsNotNull() {
            addCriterion("assign_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAssignFlagEqualTo(Boolean value) {
            addCriterion("assign_flag =", value, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignFlagNotEqualTo(Boolean value) {
            addCriterion("assign_flag <>", value, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignFlagGreaterThan(Boolean value) {
            addCriterion("assign_flag >", value, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("assign_flag >=", value, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignFlagLessThan(Boolean value) {
            addCriterion("assign_flag <", value, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("assign_flag <=", value, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignFlagIn(List<Boolean> values) {
            addCriterion("assign_flag in", values, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignFlagNotIn(List<Boolean> values) {
            addCriterion("assign_flag not in", values, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("assign_flag between", value1, value2, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("assign_flag not between", value1, value2, "assignFlag");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIsNull() {
            addCriterion("assign_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIsNotNull() {
            addCriterion("assign_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeEqualTo(Date value) {
            addCriterion("assign_time =", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotEqualTo(Date value) {
            addCriterion("assign_time <>", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThan(Date value) {
            addCriterion("assign_time >", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("assign_time >=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThan(Date value) {
            addCriterion("assign_time <", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThanOrEqualTo(Date value) {
            addCriterion("assign_time <=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIn(List<Date> values) {
            addCriterion("assign_time in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotIn(List<Date> values) {
            addCriterion("assign_time not in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeBetween(Date value1, Date value2) {
            addCriterion("assign_time between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotBetween(Date value1, Date value2) {
            addCriterion("assign_time not between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeIsNull() {
            addCriterion("vehicle_type is null");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeIsNotNull() {
            addCriterion("vehicle_type is not null");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeEqualTo(String value) {
            addCriterion("vehicle_type =", value, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeNotEqualTo(String value) {
            addCriterion("vehicle_type <>", value, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeGreaterThan(String value) {
            addCriterion("vehicle_type >", value, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("vehicle_type >=", value, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeLessThan(String value) {
            addCriterion("vehicle_type <", value, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeLessThanOrEqualTo(String value) {
            addCriterion("vehicle_type <=", value, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeLike(String value) {
            addCriterion("vehicle_type like", value, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeNotLike(String value) {
            addCriterion("vehicle_type not like", value, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeIn(List<String> values) {
            addCriterion("vehicle_type in", values, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeNotIn(List<String> values) {
            addCriterion("vehicle_type not in", values, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeBetween(String value1, String value2) {
            addCriterion("vehicle_type between", value1, value2, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andVehicleTypeNotBetween(String value1, String value2) {
            addCriterion("vehicle_type not between", value1, value2, "vehicleType");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginIsNull() {
            addCriterion("expect_time_begin is null");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginIsNotNull() {
            addCriterion("expect_time_begin is not null");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginEqualTo(Date value) {
            addCriterion("expect_time_begin =", value, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginNotEqualTo(Date value) {
            addCriterion("expect_time_begin <>", value, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginGreaterThan(Date value) {
            addCriterion("expect_time_begin >", value, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginGreaterThanOrEqualTo(Date value) {
            addCriterion("expect_time_begin >=", value, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginLessThan(Date value) {
            addCriterion("expect_time_begin <", value, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginLessThanOrEqualTo(Date value) {
            addCriterion("expect_time_begin <=", value, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginIn(List<Date> values) {
            addCriterion("expect_time_begin in", values, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginNotIn(List<Date> values) {
            addCriterion("expect_time_begin not in", values, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginBetween(Date value1, Date value2) {
            addCriterion("expect_time_begin between", value1, value2, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeBeginNotBetween(Date value1, Date value2) {
            addCriterion("expect_time_begin not between", value1, value2, "expectTimeBegin");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndIsNull() {
            addCriterion("expect_time_end is null");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndIsNotNull() {
            addCriterion("expect_time_end is not null");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndEqualTo(Date value) {
            addCriterion("expect_time_end =", value, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndNotEqualTo(Date value) {
            addCriterion("expect_time_end <>", value, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndGreaterThan(Date value) {
            addCriterion("expect_time_end >", value, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndGreaterThanOrEqualTo(Date value) {
            addCriterion("expect_time_end >=", value, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndLessThan(Date value) {
            addCriterion("expect_time_end <", value, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndLessThanOrEqualTo(Date value) {
            addCriterion("expect_time_end <=", value, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndIn(List<Date> values) {
            addCriterion("expect_time_end in", values, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndNotIn(List<Date> values) {
            addCriterion("expect_time_end not in", values, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndBetween(Date value1, Date value2) {
            addCriterion("expect_time_end between", value1, value2, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andExpectTimeEndNotBetween(Date value1, Date value2) {
            addCriterion("expect_time_end not between", value1, value2, "expectTimeEnd");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesIsNull() {
            addCriterion("general_notes is null");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesIsNotNull() {
            addCriterion("general_notes is not null");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesEqualTo(String value) {
            addCriterion("general_notes =", value, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesNotEqualTo(String value) {
            addCriterion("general_notes <>", value, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesGreaterThan(String value) {
            addCriterion("general_notes >", value, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesGreaterThanOrEqualTo(String value) {
            addCriterion("general_notes >=", value, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesLessThan(String value) {
            addCriterion("general_notes <", value, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesLessThanOrEqualTo(String value) {
            addCriterion("general_notes <=", value, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesLike(String value) {
            addCriterion("general_notes like", value, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesNotLike(String value) {
            addCriterion("general_notes not like", value, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesIn(List<String> values) {
            addCriterion("general_notes in", values, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesNotIn(List<String> values) {
            addCriterion("general_notes not in", values, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesBetween(String value1, String value2) {
            addCriterion("general_notes between", value1, value2, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andGeneralNotesNotBetween(String value1, String value2) {
            addCriterion("general_notes not between", value1, value2, "generalNotes");
            return (Criteria) this;
        }

        public Criteria andDenialReasonIsNull() {
            addCriterion("denial_reason is null");
            return (Criteria) this;
        }

        public Criteria andDenialReasonIsNotNull() {
            addCriterion("denial_reason is not null");
            return (Criteria) this;
        }

        public Criteria andDenialReasonEqualTo(String value) {
            addCriterion("denial_reason =", value, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonNotEqualTo(String value) {
            addCriterion("denial_reason <>", value, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonGreaterThan(String value) {
            addCriterion("denial_reason >", value, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonGreaterThanOrEqualTo(String value) {
            addCriterion("denial_reason >=", value, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonLessThan(String value) {
            addCriterion("denial_reason <", value, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonLessThanOrEqualTo(String value) {
            addCriterion("denial_reason <=", value, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonLike(String value) {
            addCriterion("denial_reason like", value, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonNotLike(String value) {
            addCriterion("denial_reason not like", value, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonIn(List<String> values) {
            addCriterion("denial_reason in", values, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonNotIn(List<String> values) {
            addCriterion("denial_reason not in", values, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonBetween(String value1, String value2) {
            addCriterion("denial_reason between", value1, value2, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDenialReasonNotBetween(String value1, String value2) {
            addCriterion("denial_reason not between", value1, value2, "denialReason");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeIsNull() {
            addCriterion("delivered_time is null");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeIsNotNull() {
            addCriterion("delivered_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeEqualTo(Date value) {
            addCriterion("delivered_time =", value, "deliveredTime");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeNotEqualTo(Date value) {
            addCriterion("delivered_time <>", value, "deliveredTime");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeGreaterThan(Date value) {
            addCriterion("delivered_time >", value, "deliveredTime");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("delivered_time >=", value, "deliveredTime");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeLessThan(Date value) {
            addCriterion("delivered_time <", value, "deliveredTime");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeLessThanOrEqualTo(Date value) {
            addCriterion("delivered_time <=", value, "deliveredTime");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeIn(List<Date> values) {
            addCriterion("delivered_time in", values, "deliveredTime");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeNotIn(List<Date> values) {
            addCriterion("delivered_time not in", values, "deliveredTime");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeBetween(Date value1, Date value2) {
            addCriterion("delivered_time between", value1, value2, "deliveredTime");
            return (Criteria) this;
        }

        public Criteria andDeliveredTimeNotBetween(Date value1, Date value2) {
            addCriterion("delivered_time not between", value1, value2, "deliveredTime");
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