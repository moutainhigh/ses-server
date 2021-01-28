package com.redescooter.ses.web.ros.service.website.impl;

import com.redescooter.ses.api.common.enums.inquiry.InquirySourceEnums;
import com.redescooter.ses.api.common.service.SiteWebInquiryService;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryEnter;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/27 16:02
 */
@DubboService
public class SiteWebServiceImpl implements SiteWebInquiryService {

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    @Autowired
    private OpeCustomerService opeCustomerService;


    /**
     * 官网的订单数据同步到ROS中
     */
    @Async
    @Override
    public void siteWebOrderToRosInquiry(SiteWebInquiryEnter enter) {
        OpeCustomerInquiry inquiry = new OpeCustomerInquiry();
        BeanUtils.copyProperties(enter, inquiry);
        inquiry.setCreatedBy(0L);
        inquiry.setUpdatedBy(0L);
        inquiry.setCreatedTime(new Date());
        inquiry.setUpdatedTime(new Date());
        // 等价城市
        inquiry.setDistrict(enter.getCityName());
        inquiry.setCountry(enter.getCountryName());
        inquiry.setDef1(enter.getCountryName());
        inquiry.setCountryCode("33");
        inquiry.setCity(enter.getCityName());
        inquiry.setDef2(enter.getCityName());
        inquiry.setPostCode(enter.getPostcode());
        inquiry.setDef3(enter.getPostcode());
        // 1快递 2餐厅 3自由行业
        inquiry.setIndustry("3");
        // 2表示个人
        inquiry.setCustomerType("2");
        // 下面的数据是从客户表来的
        OpeCustomer customer = opeCustomerService.getById(enter.getCustomerId());
        if (customer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        inquiry.setFirstName(customer.getCustomerFirstName());
        inquiry.setLastName(customer.getCustomerLastName());
        inquiry.setTelephone(customer.getTelephone());
        inquiry.setEmail(customer.getEmail());
        inquiry.setContactFirst(customer.getContactFirstName());
        inquiry.setContactLast(customer.getContactLastName());
        inquiry.setContantFullName(customer.getContactFullName());
        inquiry.setSource(InquirySourceEnums.ORDER_FORM.getValue());
        // todo 产品型号
        inquiry.setProductModel("");
        opeCustomerInquiryService.saveOrUpdate(inquiry);
    }
}
