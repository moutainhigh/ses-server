package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IdResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteCustomer;
import com.redescooter.ses.web.website.dm.SiteOrder;
import com.redescooter.ses.web.website.dm.SiteParts;
import com.redescooter.ses.web.website.dm.SiteUser;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.enums.PaymentStatusEnums;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.OrderService;
import com.redescooter.ses.web.website.service.base.SiteCustomerService;
import com.redescooter.ses.web.website.service.base.SiteOrderService;
import com.redescooter.ses.web.website.service.base.SiteUserService;
import com.redescooter.ses.web.website.vo.order.AddOrderEnter;
import com.redescooter.ses.web.website.vo.order.AddOrderPartsEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

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

    @Autowired
    private SiteUserService siteUserService;

    @Autowired
    private SiteCustomerService siteCustomerService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 创建订单
     *
     * @param enter
     * @return
     */
    @Override
    public IdResult addOrder(AddOrderEnter enter) {
        SiteUser user = siteUserService.getOne(new QueryWrapper<SiteUser>()
                .eq(SiteUser.COL_ID, enter.getUserId())
                .eq(SiteUser.COL_DR, Constant.DR_FALSE));

        if (user == null || enter.getColourId() == null || enter.getProductId() == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PARAM_ERROR.getCode(),
                    ExceptionCodeEnums.PARAM_ERROR.getMessage());
        }
        SiteCustomer customer = siteCustomerService.getOne(new QueryWrapper<SiteCustomer>()
                .eq(SiteCustomer.COL_ID, user.getCustomerId())
                .eq(SiteCustomer.COL_DR, Constant.DR_FALSE));
        if (customer == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PARAM_ERROR.getCode(),
                    ExceptionCodeEnums.PARAM_ERROR.getMessage());
        }
        SiteOrder addSiteOrderVO = new SiteOrder();
        addSiteOrderVO.setId(idAppService.getId(SequenceName.SITE_PARTS));
        addSiteOrderVO.setDr(Constant.DR_FALSE);
        addSiteOrderVO.setStatus(String.valueOf(CommonStatusEnums.NORMAL.getValue()));
        addSiteOrderVO.setOrderNo(String.valueOf(idAppService.getId(SequenceName.SITE_PARTS)));
        addSiteOrderVO.setCustomerId(user.getCustomerId());
        addSiteOrderVO.setSalesId(0L);
        addSiteOrderVO.setOrderType(enter.getOrderType());
        addSiteOrderVO.setProductId(enter.getProductId());
        addSiteOrderVO.setColourId(enter.getColourId());
        addSiteOrderVO.setFullName(customer.getCustomerFullName());
        addSiteOrderVO.setCountryName(customer.getCountryName());
        addSiteOrderVO.setCityName(customer.getCityName());
        addSiteOrderVO.setPostcode(customer.getPostcode());
        addSiteOrderVO.setAddress(customer.getAddress());
        addSiteOrderVO.setDeliveryType(enter.getDeliveryType());
        /**
         * 价格自动计算 TODO 接来下下要做的
         */
        addSiteOrderVO.setFreight(new BigDecimal("0"));
        addSiteOrderVO.setProductPrice(new BigDecimal("0"));
        addSiteOrderVO.setTotalPrice(new BigDecimal("0"));
        addSiteOrderVO.setAmountPaid(new BigDecimal("0"));
        addSiteOrderVO.setAmountObligation(new BigDecimal("0"));
        addSiteOrderVO.setPrepaidDeposit(new BigDecimal("0"));
        addSiteOrderVO.setAmountDiscount(new BigDecimal("0"));
        addSiteOrderVO.setPaymentTypeId(enter.getPaymentTypeId());
        addSiteOrderVO.setPayStatus(String.valueOf(PaymentStatusEnums.UNPAID_PAID.getValue()));
        addSiteOrderVO.setScooterQuantity(enter.getScooterQuantity());
        addSiteOrderVO.setEtdDeliveryTime(enter.getEtdDeliveryTime());
        addSiteOrderVO.setRemarks(enter.getRemarks());
        addSiteOrderVO.setRevision(0);
        addSiteOrderVO.setCreatedBy(enter.getUserId());
        addSiteOrderVO.setCreatedTime(new Date());
        addSiteOrderVO.setUpdatedBy(enter.getUserId());

        siteOrderService.save(addSiteOrderVO);
        IdResult result = new IdResult();
        result.setId(addSiteOrderVO.getId());
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 创建订单配件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult AddOrderParts(AddOrderPartsEnter enter) {
        /**
         * 1. 添加配件
         * 2. 计算价格
         */
        return null;
    }

    /**
     * 获取订单详情
     *
     * @param enter
     */
    @Override
    public OrderDetailsResult getOrderDetails(IdEnter enter) {
        SiteOrder order = siteOrderService.getById(enter.getId());

        OrderDetailsResult result = new OrderDetailsResult();
        BeanUtils.copyProperties(order, result);
        result.setRequestId(enter.getRequestId());

        return result;
    }
}
