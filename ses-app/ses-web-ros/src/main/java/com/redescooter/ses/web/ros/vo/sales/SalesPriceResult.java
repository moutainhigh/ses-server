package com.redescooter.ses.web.ros.vo.sales;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesPriceResult  {
    @ApiModelProperty(value = "车型和电池数")
    private String scooterBattery;

    @ApiModelProperty(value = "定金(算作首期)")
    private BigDecimal deposit;

    @ApiModelProperty(value = "期数")
    private Integer period;

    @ApiModelProperty(value = "每期应付(除首期)")
    private BigDecimal shouldPayPeriod;

    @ApiModelProperty(value = "尾款(当类型为全款支付时才有值)")
    private BigDecimal balance;

    @ApiModelProperty(value = "税")
    private BigDecimal tax;

    @ApiModelProperty(value = "状态 1开启(可用) 2关闭(不可用)")
    private Integer status;

}
