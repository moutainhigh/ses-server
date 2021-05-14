package com.redescooter.ses.web.ros.vo.app;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 14:15
 */
@Data
@ApiModel(value = "询价单详情出参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InquiryDetailResult extends GeneralResult {

    @ApiModelProperty(value = "车型名称")
    private String scooterName;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "电池数量")
    private Integer batteryNum;

    @ApiModelProperty(value = "车座")
    private Integer seatNumber;

    @ApiModelProperty(value = "RSN")
    private String rsn;

    @ApiModelProperty(value = "平板序列号")
    private String tabletSn;

    @ApiModelProperty(value = "蓝牙地址")
    private String bluetoothAddress;

    @ApiModelProperty(value = "VIN")
    private String vinCode;

    @ApiModelProperty(value = "电池数")
    private Integer batteryNumber;

    @ApiModelProperty(value = "电池码")
    private List<String> batteryList;

    @ApiModelProperty(value = "app节点 0车辆信息 1电池信息 2绑定VIN 3设置软体 4完成")
    private Integer appNode;

    private String battery;

}
