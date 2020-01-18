package com.redescooter.ses.api.common.vo.edorder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class BaseExpressOrderResult {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    @ApiModelProperty(value = "批次号")
    private String batchNo;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "状态，待分配UNASGN,已分配ASGN，已完成COMPLETE")
    private String status;

    @ApiModelProperty(value = "收货方国家")
    private String recipientCountry;

    @ApiModelProperty(value = "收件方省份")
    private String recipientProvince;

    @ApiModelProperty(value = "收件方城市")
    private String recipientCity;

    @ApiModelProperty(value = "收件方邮编")
    private String recipientPostcode;

    @ApiModelProperty(value = "收件方详细地址")
    private String recipientAddress;

    @ApiModelProperty(value = "收件方纬度")
    private BigDecimal recipientLatitude;

    @ApiModelProperty(value = "收货方经度")
    private BigDecimal recipientLongitude;

    @ApiModelProperty(value = "GeoHash")
    private String recipientGeohash;

    @ApiModelProperty(value = "客户id，等价于收件人名称")
    private String customerReference;

    @ApiModelProperty(value = "收件人")
    private String recipientName;

    @ApiModelProperty(value = "收件方公司，个人时为空")
    private String recipientCompany;

    @ApiModelProperty(value = "收件人电话")
    private String recipientPhone;

    @ApiModelProperty(value = "收件方邮箱")
    private String recipientMail;

    @ApiModelProperty(value = "收件方备注")
    private String recipientNotes;

    @ApiModelProperty(value = "发货方国家")
    private String senderCountry;

    @ApiModelProperty(value = "发货方城市")
    private String senderCity;

    @ApiModelProperty(value = "发货方省份")
    private String senderProvince;

    @ApiModelProperty(value = "发货方邮编")
    private String senderPostcode;

    @ApiModelProperty(value = "发货方详细地址")
    private String senderAddress;

    @ApiModelProperty(value = "发货方纬度")
    private BigDecimal senderLatitude;

    @ApiModelProperty(value = "发货方经度")
    private BigDecimal senderLongitude;

    @ApiModelProperty(value = "Geohash")
    private String senderGeohash;

    @ApiModelProperty(value = "发货方公司，个人时为空")
    private String senderCompany;

    @ApiModelProperty(value = "发货人")
    private String senderName;

    @ApiModelProperty(value = "发货人手机")
    private String senderPhone;

    @ApiModelProperty(value = "发货人邮箱")
    private String senderMail;

    @ApiModelProperty(value = "发货方备注")
    private String senderNotes;

    @ApiModelProperty(value = "包裹数")
    private Integer parcelQuantity;

    @ApiModelProperty(value = "是否已分配")
    private Boolean assignFlag;

    @ApiModelProperty(value = "分配递送时间")
    private Date assignTime;

    @ApiModelProperty(value = "车辆类型")
    private String vehicleType;

    @ApiModelProperty(value = "期望送达时间,注意这是时间段")
    private Date expectTimeBegin;

    @ApiModelProperty(value = "期望送达时间,注意这是时间段")
    private Date expectTimeEnd;

    @ApiModelProperty(value = "其他备注")
    private String generalNotes;

    @ApiModelProperty(value = "拒绝原因")
    private String denialReason;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Integer updatedBy;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}
