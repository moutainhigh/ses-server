package com.redescooter.ses.api.mobile.b.vo.meter;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @ClassName:MeterDeliveryOrderReuslt
 * @description: MeterDeliveryOrderReuslt
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/16 17:47 
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MeterDeliveryOrderReuslt extends GeneralResult {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "收件人地址")
    private String recipientAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "geohash")
    private String geohash;

    @ApiModelProperty(value="剩余订单数，包含当前正在进行的订单")
    private int remainingOrderNum;

    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;

}
