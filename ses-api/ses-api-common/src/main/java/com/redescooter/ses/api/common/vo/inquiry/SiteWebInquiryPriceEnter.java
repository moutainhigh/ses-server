package com.redescooter.ses.api.common.vo.inquiry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/7 18:37
 */
@Data
public class SiteWebInquiryPriceEnter implements Serializable {

    private static final long serialVersionUID = -9879107293686980L;

    @ApiModelProperty(value = "单据总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "待付款金额")
    private BigDecimal amountObligation;

}
