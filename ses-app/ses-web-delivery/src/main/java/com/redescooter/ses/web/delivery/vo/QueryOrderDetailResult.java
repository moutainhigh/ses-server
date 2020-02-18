package com.redescooter.ses.web.delivery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:QueryDeliveryDetailResult
 * @description: QueryDeliveryDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/12 15:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class QueryOrderDetailResult extends GeneralResult {
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "订单创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;

    @ApiModelProperty(value = "完成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date deliveredTime;

    @ApiModelProperty(value = "订单分配时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date deliveryDate;

    @ApiModelProperty(value = "期望开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date expectTimeBegin;

    @ApiModelProperty(value = "期望结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date expectTimeEnd;

    @ApiModelProperty(value = "司机Id")
    private Long driverId;

    @ApiModelProperty(value = "司机名字")
    private String driverFirstName;

    @ApiModelProperty(value = "司机名字")
    private String driverLastName;

    @ApiModelProperty(value = "司机邮箱")
    private String driverEmail;

    @ApiModelProperty(value = "司机手机号")
    private String driverPhone;

    @ApiModelProperty(value = "司机登录类型")
    private String driverLoginType;

    @ApiModelProperty(value = "司机昵称")
    private String driverNickname;

    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    @ApiModelProperty(value = "经纬度")
    private String driverLatitude;

    @ApiModelProperty(value = "经纬度")
    private String driverLongitule;

    @ApiModelProperty(value = "门店地址")
    private String tenantAddress;

    @ApiModelProperty(value = "门店经度")
    private String tenantLat;

    @ApiModelProperty(value = "门店维度")
    private String tenantLng;

    @ApiModelProperty(value = "发货人距离")
    private double sendMileage;

    @ApiModelProperty(value = "收货人距离")
    private double recipientMileage;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "发送人公司")
    private String recipientCompany;

    @ApiModelProperty(value = "收货人")
    private String recipientName;

    @ApiModelProperty(value = "发货国家")
    private String recipientCountry;

    @ApiModelProperty(value = "收货人城市")
    private String recipientCity;

    @ApiModelProperty(value = "收货人区域")
    private String recipientArea;

    @ApiModelProperty(value = "收货人 城市代码")
    private String recipientPostcode;

    @ApiModelProperty(value = "收货人地址")
    private String recipientAddress;

    @ApiModelProperty(value = "收货人经度 可能为0")
    private String recipientLatitude;

    @ApiModelProperty(value = "发货维度")
    private String recipientLongitude;

    @ApiModelProperty(value = "geohash", hidden = true)
    private String recipientGeohash;

    @ApiModelProperty(value = "收货人电话")
    private String recipientPhone;

    @ApiModelProperty(value = "收货人邮箱")
    private String recipientMail;

    @ApiModelProperty(value = "收货人备注")
    private String recipientNotes;

    @ApiModelProperty(value = "发货人")
    private String senderName;

    @ApiModelProperty(value = "发货人国家")
    private String senderCountry;

    @ApiModelProperty(value = "发货人城市")
    private String senderCity;

    @ApiModelProperty(value = "发货人区域")
    private String senderArea;

    @ApiModelProperty(value = "发货人城市代码")
    private String senderPostcode;

    @ApiModelProperty(value = "发货人地址")
    private String senderAddress;

    @ApiModelProperty(value = "发货人经度")
    private String senderLatitude;

    @ApiModelProperty(value = "发货人维度")
    private String senderLongitude;

    @ApiModelProperty(value = "geohash", hidden = true)
    private String senderGeohash;

    @ApiModelProperty(value = "发货人公司")
    private String senderCompany;

    @ApiModelProperty(value = "发货人电话")
    private String senderPhone;

    @ApiModelProperty(value = "发货人邮箱")
    private String senderMail;

    @ApiModelProperty(value = "发货人留言")
    private String senderNotes;

    @ApiModelProperty(value = "其他备注")
    private String generalNotes;

    @ApiModelProperty(value = "拒绝原因")
    private String reason;

    @ApiModelProperty(value = "订单节点")
    private List<QueryExpressOrderTraceResult> expressOrderTraceResultList = new ArrayList<>();

}
