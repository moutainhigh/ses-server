package com.redescooter.ses.api.common.service;

import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryEnter;

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

}
