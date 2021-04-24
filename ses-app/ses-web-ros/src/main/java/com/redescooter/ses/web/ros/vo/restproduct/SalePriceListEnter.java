package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/23 20:59
 */
@Data
@ApiModel(value = "销售价格列表入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SalePriceListEnter extends PageEnter {

    @ApiModelProperty(value = "状态 0全部状态 1开启(可用) 2关闭(不可用)")
    private Integer status;

    @ApiModelProperty(value = "车型")
    private String scooterBattery;

    @ApiModelProperty(value = "类型 1租借车辆 2全款支付 3分期支付")
    private Integer type;

}
