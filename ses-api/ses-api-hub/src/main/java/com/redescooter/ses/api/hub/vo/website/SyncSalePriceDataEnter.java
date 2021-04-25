package com.redescooter.ses.api.hub.vo.website;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/25 13:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SyncSalePriceDataEnter extends GeneralEnter {

    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    @ApiModelProperty(value = "类型 1租借车辆 2全款支付 3分期支付")
    private Integer type;

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

    @ApiModelProperty(value = "状态 1开启(可用) 2关闭(不可用)")
    private Integer status;

}
