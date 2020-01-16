package com.redescooter.ses.api.mobile.b.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ApiModelProperty(value = "订单服务结果  1 按时、3 延迟、2 取消  ONTIME、DELAY、CANCEl")
    private String result;

    @ApiModelProperty(value = "订单状态1 待配送，2配送中，3拒单，4配送超时，5超时完成，6已送达，7失败（取消订单），8超时预警")
    private String status;

    @ApiModelProperty(value = "安排配送时间 单位分钟")
    private String plannedTime;

    @ApiModelProperty(value = "超时预警值，单位分钟")
    private String timeoutExpectde;

    @ApiModelProperty(value = "超时预警时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date timeOut;

    @ApiModelProperty(value = "预计开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date etd;

    @ApiModelProperty(value = "实际开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date atd;

    @ApiModelProperty(value = "预计送达时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date eta;

    @ApiModelProperty(value = "实际送达时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date ata;

    @ApiModelProperty(value = "骑行里程 单位 米")
    private BigDecimal drivenMileage;

    @ApiModelProperty(value = "骑行时长 单位 s")
    private Integer drivenDuration;

    @ApiModelProperty(value = "二氧化碳排放量 单位 g")
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

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date updatedTime;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;

    @ApiModelProperty(value = "超时标签是否显示，true显示，false 不显示")
    private Boolean label;
}
