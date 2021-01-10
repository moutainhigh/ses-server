package com.redescooter.ses.web.website.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteOrder;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.OrderService;
import com.redescooter.ses.web.website.service.base.SiteOrderService;
import com.redescooter.ses.web.website.vo.order.AddOrderEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author jerry
 * @Date 2021/1/9 7:19 下午
 * @Description 订单服务
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SiteOrderService siteOrderService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 创建订单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addOrder(AddOrderEnter enter) {
        if (enter.getCustomerId() == null || enter.getColourId() == null || enter.getProductId() == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PARAM_ERROR.getCode(),
                    ExceptionCodeEnums.PARAM_ERROR.getMessage());
        }
        SiteOrder addSiteOrderVO = new SiteOrder();
        addSiteOrderVO.setId(idAppService.getId(SequenceName.SITE_PARTS));
        addSiteOrderVO.setDr(Constant.DR_FALSE);
        addSiteOrderVO.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        addSiteOrderVO.setOrderNo(String.valueOf(idAppService.getId(SequenceName.SITE_PARTS)));
        addSiteOrderVO.setCustomerId(enter.getCustomerId());
        addSiteOrderVO.setCustomerSource("");
        addSiteOrderVO.setSalesId(0L);
        addSiteOrderVO.setStatus("");
        addSiteOrderVO.setOrderType(enter.getOrderType());
        addSiteOrderVO.setProductId(enter.getProductId());
        addSiteOrderVO.setColourId(enter.getColourId());
        addSiteOrderVO.setProductColour(enter.getProductColour());
        addSiteOrderVO.setFullName("");
        addSiteOrderVO.setCompanyName("");
        addSiteOrderVO.setCountryName("");
        addSiteOrderVO.setCityName("");
        addSiteOrderVO.setPostcode("");
        addSiteOrderVO.setAddress("");
        addSiteOrderVO.setDeliveryType(enter.getDeliveryType());
        addSiteOrderVO.setFreight(new BigDecimal("0"));
        addSiteOrderVO.setProductPrice(new BigDecimal("0"));
        addSiteOrderVO.setTotalPrice(new BigDecimal("0"));
        addSiteOrderVO.setAmountPaid(new BigDecimal("0"));
        addSiteOrderVO.setAmountObligation(new BigDecimal("0"));
        addSiteOrderVO.setPrepaidDeposit(new BigDecimal("0"));
        addSiteOrderVO.setAmountDiscount(new BigDecimal("0"));
        addSiteOrderVO.setPaymentTypeId(enter.getPaymentTypeId());
        addSiteOrderVO.setPayStatus("");
        addSiteOrderVO.setScooterQuantity(enter.getScooterQuantity());
        addSiteOrderVO.setEtdDeliveryTime(enter.getEtdDeliveryTime());
        addSiteOrderVO.setRemarks(enter.getRemarks());
        addSiteOrderVO.setRevision(0);
        addSiteOrderVO.setCreatedBy(enter.getUserId());
        addSiteOrderVO.setCreatedTime(new Date());
        addSiteOrderVO.setUpdatedBy(enter.getUserId());

        siteOrderService.save(addSiteOrderVO);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 修改订单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult modifyOrder(AddOrderEnter enter) {
        return null;
    }

    /**
     * 获取订单详情
     *
     * @param enter
     */
    @Override
    public OrderDetailsResult getOrderDetails(IdEnter enter) {
        return null;
    }
}
