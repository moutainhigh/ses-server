package com.redescooter.ses.mobile.wh.fr.vo;

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

    @ApiModelProperty(value = "RSN")
    private String rsn;

    @ApiModelProperty(value = "序列号")
    private String tabletSn;

    @ApiModelProperty(value = "蓝牙地址")
    private String bluetoothAddress;

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

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    @ApiModelProperty(value = "车型id")
    private Long specificatTypeId;

    @ApiModelProperty(value = "车座")
    private Integer seatNumber;

    @ApiModelProperty(value = "VIN")
    private String vin;

}
