package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ApiModelProperty(value = "期数和每期应付")
    private List<ScooterPriceDetailResult> list = new ArrayList<>();

    @ApiModelProperty(value = "税")
    private BigDecimal tax;

    @ApiModelProperty(value = "定金")
    private BigDecimal prepaidDeposit;

}
