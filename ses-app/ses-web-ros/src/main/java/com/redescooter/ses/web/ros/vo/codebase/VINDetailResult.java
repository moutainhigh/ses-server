package com.redescooter.ses.web.ros.vo.codebase;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 11:34
 */
@Data
@ApiModel(value = "VIN详情出参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VINDetailResult extends GeneralResult {

    @ApiModelProperty(value = "VIN")
    private String vin;

    @ApiModelProperty(value = "生成时间")
    private Date generateDate;

    @ApiModelProperty(value = "更新时间")
    private Date finishDate;

    @ApiModelProperty(value = "BBI")
    private String bbi;

    @ApiModelProperty(value = "控制器")
    private String controller;

    @ApiModelProperty(value = "电机")
    private String electricMachinery;

    @ApiModelProperty(value = "仪表")
    private String meter;

    @ApiModelProperty(value = "IMEL")
    private String imel;

    @ApiModelProperty(value = "蓝牙地址")
    private String bluetoothAddress;

    @ApiModelProperty(value = "RSN")
    private String rsn;

    @ApiModelProperty(value = "电池")
    private List<String> batteryList;

    @ApiModelProperty(value = "询价单id")
    private Long inquiryId;

    @ApiModelProperty(value = "询价单单号")
    private String orderNo;

    @ApiModelProperty(value = "车型名称")
    private Long specificatTypeName;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "座位数")
    private Integer seatNumber;

    @ApiModelProperty(value = "电池数")
    private Integer batteryNum;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "客户姓名")
    private String customerName;

}
