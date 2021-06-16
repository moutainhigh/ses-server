package com.redescooter.ses.web.ros.vo.assign.doing.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/12 16:54
 */
@Data
public class AssigningDetailScooterInfoResult {

    @ApiModelProperty(value = "座位数")
    private Integer seatNumber;

    @ApiModelProperty(value = "Vin Code")
    private String vinCode;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "RSN")
    private String rsn;

    @ApiModelProperty(value = "蓝牙地址")
    private String bluetoothAddress;

    @ApiModelProperty(value = "平板序列号")
    private String tabletSn;

    @ApiModelProperty(value = "颜色")
    private String colorName;

    @ApiModelProperty(value = "颜色色值")
    private String colorValue;

    @ApiModelProperty(value = "电池数")
    private Integer batteryNum;

    @ApiModelProperty(value = "电池码")
    private List<String> batteryList;

}
