package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:QueryExpressOrderTraceResult
 * @description: QueryExpressOrderTraceResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/16 15:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class QueryExpressOrderTraceResult extends GeneralResult {

    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="deliveryId")
    private Long expressDeliveryId;

    @ApiModelProperty(value="完成时订单id")
    private Long expressOrderId;

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
    private String createdName;

    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    @ApiModelProperty(value="driverName")
    private String driverName;

    @ApiModelProperty(value="termWord")
    private String termWord;

}
