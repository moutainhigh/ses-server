package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:DriverOrderInfoResult
 * @description: DriverOrderInfoResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 17:09
 */
@ApiModel(value = "司机订单信息出参", description = "司机订单信息出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class DriverOrderInfoResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "司机名")
    private String driverFirstName;

    @ApiModelProperty(value = "司机姓")
    private String driverLastName;

    @ApiModelProperty(value = "scooterId")
    private Long scooterId;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "电量")
    private Integer battery;

    @ApiModelProperty(value = "订单信息")
    private List<DeliveryMapResult> deliveryMapResultList;
}
