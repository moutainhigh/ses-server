package com.redescooter.ses.web.website.vo.payment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/27 18:00
 */
@Data
@EqualsAndHashCode
public class FullPayResult implements Serializable {

    @ApiModelProperty(value = "预付定金")
    private BigDecimal prepaidDeposit;

    @ApiModelProperty(value = "尾款")
    private BigDecimal balance;

}
