package com.redescooter.ses.web.ros.vo.assign.tobe.result;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 查询客户走到哪个节点并带出车辆信息数据子出参
 * @Author Chris
 * @Date 2021/1/6 13:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "查询客户走到哪个节点并带出车辆信息数据子出参", description = "查询客户走到哪个节点并带出车辆信息数据子出参")
public class ToBeAssignNodeScooterInfoSubResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = 7720484683210251733L;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "颜色色值")
    private String colorValue;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    @ApiModelProperty(value = "待完成分配数")
    private Integer toBeAssignCount;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "座位数")
    private Integer seatNumber;

    @ApiModelProperty(value = "Vin Code")
    private String vinCode;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "R.SN")
    private String rsn;

    @ApiModelProperty(value = "电池数量")
    private Integer batteryNum;

    @ApiModelProperty(value = "Qty")
    private Integer qty;

    @ApiModelProperty(value = "蓝牙地址")
    private String bluetoothAddress;

    @ApiModelProperty(value = "平板序列号")
    private String tabletSn;

    @ApiModelProperty(value = "电池码 字符号数组 如果多个时逗号分隔")
    private List<String> batteryList;

}
