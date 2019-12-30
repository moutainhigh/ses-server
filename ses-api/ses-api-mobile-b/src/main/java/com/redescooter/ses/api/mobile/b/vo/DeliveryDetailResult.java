package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:DeliveryDetailResult
 * @description: DeliveryDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 14:41
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryDetailResult extends GeneralResult {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "交付人,即司机账号的USER_ID字段的值")
    private Long delivererId;

    @ApiModelProperty(value = "scooterId")
    private Long scooterId;

    @ApiModelProperty(value = "收件人")
    private String recipient;

    @ApiModelProperty(value = "收件人邮箱")
    private String recipientEmail;

    @ApiModelProperty(value = "收件人电话")
    private String recipientTel;

    @ApiModelProperty(value = "收件人地址")
    private String recipientAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "门牌信息")
    private String houseInfo;

    @ApiModelProperty(value = "包裹数量")
    private Integer parcelQuantity;

    @ApiModelProperty(value = "商品清单")
    private String goodsInventory;

    @ApiModelProperty(value = "订单服务结果  按时、延迟、取消  ONTIME、DELAY、CANCEl")
    private String result;

    @ApiModelProperty(value = "订单状态:PENDING待配送，  DELIVERING正在配送，REJECTED拒绝，TIMEOUT配送超时，COMPLETE已送达，CANCEL取消失败；PC订单状态显示:待配送Pending,配送中In  progress,拒单Declined,配送超时Overdue,超时完成Delayed,已送达Delivered," +
            "取消订单Cancelled")
    private String status;

    @ApiModelProperty(value = "安排配送时间")
    private String plannedTime;

    @ApiModelProperty(value = "超时预警值，单位分钟")
    private String timeoutExpectde;

    @ApiModelProperty(value = "超时预警时间")
    private Date timeOut;

    @ApiModelProperty(value = "预计开始时间")
    private Date etd;

    @ApiModelProperty(value = "实际开始时间")
    private Date atd;

    @ApiModelProperty(value = "预计送达时间")
    private Date eta;

    @ApiModelProperty(value = "实际送达时间")
    private Date ata;

    @ApiModelProperty(value = "骑行里程")
    private BigDecimal drivenMileage;

    @ApiModelProperty(value = "骑行时长")
    private Integer drivenDuration;

    @ApiModelProperty(value = "二氧化碳排放量")
    private BigDecimal co2;

    @ApiModelProperty(value = "节省金额")
    private BigDecimal savings;

    @ApiModelProperty(value = "门店经度")
    private BigDecimal tenantLongitude;

    @ApiModelProperty(value = "门店纬度")
    private BigDecimal tenantLatitude;

    @ApiModelProperty(value = "车辆经度")
    private BigDecimal scooterLongitude;

    @ApiModelProperty(value = "车辆纬度")
    private BigDecimal scooterLatitude;

    @ApiModelProperty(value = "电量")
    private Integer battery;
}
