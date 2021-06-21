package com.redescooter.ses.web.ros.vo.wms.cn.fr;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 新增法国仓库入库单产品明细入参
 * @Author Chris
 * @Date 2021/4/15 10:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "新增法国仓库入库单产品明细入参", description = "新增法国仓库入库单产品明细入参")
public class FrInWhOrderAddScooterBEnter extends GeneralEnter {

    @ApiModelProperty(value = "整车序列号", required = true)
    private String rsn;

    @ApiModelProperty(value = "蓝牙地址", required = true)
    private String bluetoothMacAddress;

    @ApiModelProperty(value = "仪表序列号", required = true)
    private String tabletSn;

    @ApiModelProperty(value = "颜色 1半雅黑 2荣誉灰 3天空灰", required = true)
    private Integer color;

    @ApiModelProperty(value = "颜色名称", required = true)
    private String colorName;

    @ApiModelProperty(value = "颜色色值", required = true)
    private String colorValue;

    @ApiModelProperty(value = "bbi", required = true)
    private String bbi;

    @ApiModelProperty(value = "控制器", required = true)
    private String controller;

    @ApiModelProperty(value = "电机", required = true)
    private String motor;

    @ApiModelProperty(value = "IMEI", required = true)
    private String imei;

}
