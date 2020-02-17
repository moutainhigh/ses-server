package com.redescooter.ses.web.delivery.vo.edorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.delivery.vo.QueryOrderDetailResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(value = "快递地图司机订单列表出参", description = "快递地图司机订单列表出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DiverOrderInforResult extends GeneralResult {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "司机名")
    private String driverFirstName;

    @ApiModelProperty(value = "司机姓")
    private String driverLastName;

    @ApiModelProperty(value = "车辆主键")
    private Long scooterId;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "电量")
    private Integer battery;

    @ApiModelProperty(value = "订单信息列表")
    private List<QueryOrderDetailResult> orderList;
}
