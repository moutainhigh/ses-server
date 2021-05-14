package com.redescooter.ses.web.ros.vo.app;

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

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    @ApiModelProperty(value = "车型id")
    private Long specificatTypeId;

    @ApiModelProperty(value = "车座")
    private Integer seatNumber;

}
