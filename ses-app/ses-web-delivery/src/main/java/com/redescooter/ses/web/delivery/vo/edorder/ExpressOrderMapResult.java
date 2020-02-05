package com.redescooter.ses.web.delivery.vo.edorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.delivery.vo.QueryOrderDetailResult;
import com.redescooter.ses.web.delivery.vo.ScooterMapResult;

import java.util.List;

import io.swagger.annotations.*;
import lombok.*;

@ApiModel(value = "快递地图出参", description = "快递地图出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ExpressOrderMapResult extends GeneralResult {
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    @ApiModelProperty(value = "租户经度")
    private String tenantLng;

    @ApiModelProperty(value = "租户纬度")
    private String tenantLat;

    @ApiModelProperty(value = "订单列表")
    private List<QueryOrderDetailResult> orderList;

    @ApiModelProperty(value = "车辆列表")
    private List<ScooterMapResult> scooterMapResultList;
}
