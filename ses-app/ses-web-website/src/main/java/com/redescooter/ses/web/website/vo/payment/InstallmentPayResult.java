package com.redescooter.ses.web.website.vo.payment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/27 18:01
 */
@Data
@EqualsAndHashCode
public class InstallmentPayResult implements Serializable {

    @ApiModelProperty(value = "期数")
    private String installmentTime;

    @ApiModelProperty(value = "预付定金")
    private BigDecimal prepaidDeposit;

}
