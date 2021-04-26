package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Description 官网车型价格列表出参
 * @Author Chris
 * @Date 2021/4/26 13:27
 */
@ApiModel(value = "官网车型价格列表出参", description = "官网车型价格列表出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ScooterPriceListResult extends GeneralResult {

    @ApiModelProperty(value = "车型和电池数")
    private String scooterBattery;

    @ApiModelProperty(value = "分期付款时间数")
    private String installmentTime;

    @ApiModelProperty(value = "每期应付")
    private BigDecimal shouldPayPeriod;

    @ApiModelProperty(value = "税")
    private BigDecimal tax;

    @ApiModelProperty(value = "定金")
    private BigDecimal prepaidDeposit;

}
