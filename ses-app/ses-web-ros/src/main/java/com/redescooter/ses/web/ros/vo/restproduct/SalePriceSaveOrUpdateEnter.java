package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/24 13:33
 */
@Data
@ApiModel(value = "销售价格新增或者编辑入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SalePriceSaveOrUpdateEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键")
    private Long id;

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

}
