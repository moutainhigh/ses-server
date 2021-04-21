package com.redescooter.ses.api.common.vo.inquiry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/29 17:00
 */
@Data
public class SiteWebInquiryPayEnter implements Serializable {


    @ApiModelProperty(value = "主建")
    private Long id;

    @ApiModelProperty(value = "支付状态,1待支付，2部分支付，3已支付")
    private Integer payStatus;

    /**
     * 单据总价
     */
    @ApiModelProperty(value = "单据总价")
    private BigDecimal totalPrice;

    /**
     * 已付金额
     */
    @ApiModelProperty(value = "已付金额")
    private BigDecimal amountPaid;

    /**
     * 待付款金额
     */
    @ApiModelProperty(value = "待付款金额")
    private BigDecimal amountObligation;

    /**
     * 预付定金
     */
    @ApiModelProperty(value = "预付定金")
    private BigDecimal prepaidDeposit;

    /**
     * 优惠抵扣金额
     */
    @ApiModelProperty(value = "优惠抵扣金额")
    private BigDecimal amountDiscount;


}
