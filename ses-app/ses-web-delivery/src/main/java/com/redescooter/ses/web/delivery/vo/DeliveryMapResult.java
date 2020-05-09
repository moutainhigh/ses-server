package com.redescooter.ses.web.delivery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:DeliveryMapResult
 * @description: DeliveryMapResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 14:58
 */
@ApiModel(value = "订单地图出参", description = "订单地图出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class DeliveryMapResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "收货人纬度")
    private String lat;

    @ApiModelProperty(value = "收货人经度")
    private String lng;

    @ApiModelProperty(value = "收货人")
    private String recipient;

    @ApiModelProperty(value = "收货人电话")
    private String recipientTel;

    @ApiModelProperty(value = "数量")
    private Integer parcelQuantity;

    @ApiModelProperty(value = "收货人地址")
    private String recipientAddress;

    @ApiModelProperty(value = "eta")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date eta;

    @ApiModelProperty(value = "订单状态")
    private String status;
}
