package com.redescooter.ses.api.common.vo.edorder;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:Order
 * @description: Order
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/18 12:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class BaseExpressOrderResult {
    @ApiModelProperty(value="主键")
    private Long id;

    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    @ApiModelProperty(value="deliveryId")
    private Long expressDeliveryId;

    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    @ApiModelProperty(value="完成时订单id")
    private Long expressOrderId;

    @ApiModelProperty(value="交付人id")
    private Long driverId;

    @ApiModelProperty(value="订单状态")
    private String status;

    @ApiModelProperty(value="事件")
    private String event;

    @ApiModelProperty(value="订单取消时，存储取消原因；订单拒绝时，存储拒绝原因；")
    private String reason;

    @ApiModelProperty(value="事件时间")
    private Date eventTime;

    @ApiModelProperty(value="经度")
    private BigDecimal longitude;

    @ApiModelProperty(value="维度")
    private BigDecimal latitude;

    @ApiModelProperty(value="GEOHASH")
    private String geohash;

    @ApiModelProperty(value="scooterId")
    private Long scooterId;

    @ApiModelProperty(value="scooter经度")
    private BigDecimal scooterLongitude;

    @ApiModelProperty(value="scooter维度")
    private BigDecimal scooterLatitude;

    @ApiModelProperty(value="scooterGEOHASH")
    private String scooterGeohash;

    @ApiModelProperty(value="创建人")
    private Long createdBy;

    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    @ApiModelProperty(value="更新时间")
    private Date updatedTime;
}
