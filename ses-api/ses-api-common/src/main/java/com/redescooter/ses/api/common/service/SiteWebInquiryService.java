package com.redescooter.ses.api.common.service;

import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryEnter;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryPayEnter;

/**
 * @description: 官网的预订单数据同步到ROS中的服务层
 * @author: Aleks
 * @create: 2021/01/27 16:01
 */
public interface SiteWebInquiryService {

    /**
     * 官网的预订单数据同步到ROS中的预订单
     * @param enter
     */
    void siteWebOrderToRosInquiry(SiteWebInquiryEnter enter);


    /**
     * 官网下的订单支付之后调用 不管支付成功还是失败都要调用
     * @param enter
     */
    void siteWebInquiryPay(SiteWebInquiryPayEnter enter);


    /**
     * 官网订单删除 ROS这边的订单也删除
     * @param email
     */
    void webDeleteOrderAsynRos(String email);
}
