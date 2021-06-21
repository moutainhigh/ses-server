package com.redescooter.ses.api.common.vo.node;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 16:20
 */
@Data
@ApiModel(value = "录入车辆入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InputScooterEnter extends GeneralEnter {

    @ApiModelProperty(value = "车辆id")
    private Long scooterId;

    @ApiModelProperty(value = "RSN")
    private String rsn;

    @ApiModelProperty(value = "序列号")
    private String tabletSn;

    @ApiModelProperty(value = "蓝牙地址")
    private String bluetoothMacAddress;

    @ApiModelProperty(value = "BBI")
    private String bbi;

    @ApiModelProperty(value = "控制器")
    private String controller;

    @ApiModelProperty(value = "电机")
    private String motor;

    @ApiModelProperty(value = "IMEI")
    private String imei;

}
