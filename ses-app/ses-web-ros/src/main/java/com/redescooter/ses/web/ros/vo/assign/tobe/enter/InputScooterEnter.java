package com.redescooter.ses.web.ros.vo.assign.tobe.enter;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/24 14:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InputScooterEnter {

    @ApiModelProperty(value = "BBI", required = true)
    private String bbi;

    @ApiModelProperty(value = "控制器", required = true)
    private String controller;

    @ApiModelProperty(value = "电机", required = true)
    private String electricMachinery;

    @ApiModelProperty(value = "仪表", required = true)
    private String meter;

    @ApiModelProperty(value = "IMEI", required = true)
    private String imei;

    @ApiModelProperty(value = "蓝牙地址", required = true)
    private String bluetoothAddress;

    @ApiModelProperty(value = "平板序列号", required = true)
    private String tabletSn;

}
