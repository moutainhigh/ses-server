package com.redescooter.ses.web.ros.vo.codebase;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 11:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RSNDetailScooterResult {

    @ApiModelProperty(value = "BBI")
    private String bbi;

    @ApiModelProperty(value = "控制器")
    private String controller;

    @ApiModelProperty(value = "电机")
    private String electricMachinery;

    @ApiModelProperty(value = "仪表")
    private String meter;

    @ApiModelProperty(value = "IMEI")
    private String imei;

    @ApiModelProperty(value = "蓝牙地址")
    private String bluetoothAddress;

    @ApiModelProperty(value = "RSN")
    private String rsn;

    @ApiModelProperty(value = "VIN")
    private String vin;

}
