package com.redescooter.ses.web.delivery.vo.edorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import lombok.*;


import io.swagger.annotations.*;
@ApiModel(value = "订单拒绝详情", description = "订单拒绝详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class RefuseOrderDetailResult extends GeneralResult {

    @ApiModelProperty(value = "订单Id")
    private Long id;

    @ApiModelProperty(value = "司机Id")
    private Long driverId;

    @ApiModelProperty(value = "司机姓名")
    private String driverFirstName;

    @ApiModelProperty(value = "司机姓名")
    private String driverLastName;

    @ApiModelProperty(value = "拒绝原因")
    private String reason;
}
