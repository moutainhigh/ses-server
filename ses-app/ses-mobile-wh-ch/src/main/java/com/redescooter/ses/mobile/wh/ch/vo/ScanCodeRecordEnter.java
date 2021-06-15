package com.redescooter.ses.mobile.wh.ch.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ScanCodeRecord
 * @Description
 * @Author Charles
 * @Date 2021/06/04 16:24
 */
@Data
@ApiModel(value = "部件入库")
@NoArgsConstructor
@AllArgsConstructor
public class ScanCodeRecordEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("操作人邮箱")
    private String warehouseEmail;

    @ApiModelProperty("操作人id")
    private Long warehouseId;

    @ApiModelProperty("车辆名称")
    private String scooterName;

    @ApiModelProperty("颜色")
    private String colorCode;

    @ApiModelProperty("rsn 码")
    private String rsn;

    @ApiModelProperty("bbi sn码")
    private String bbiSn;

    @ApiModelProperty("控制器 sn 码")
    private String controllerSn;

    @ApiModelProperty("电机码")
    private String motorSn;

    @ApiModelProperty("仪表码")
    private String meterSn;

    @ApiModelProperty("仪表iemi")
    private String meterImei;

    @ApiModelProperty("仪表 bt")
    private String meterBt;

    @ApiModelProperty("仪表 wifi")
    private String meterWifi;

    @ApiModelProperty("备注")
    private String remarks;
}
