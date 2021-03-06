package com.redescooter.ses.web.website.vo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/26 17:32
 */
@ApiModel(value = "官网车型价格详情出参", description = "官网车型价格详情出参")
@Data
@EqualsAndHashCode
public class ScooterPriceDetailResult implements Serializable {

    @ApiModelProperty(value = "分期付款时间数")
    private String installmentTime;

    @ApiModelProperty(value = "每期应付")
    private BigDecimal shouldPayPeriod;

}
