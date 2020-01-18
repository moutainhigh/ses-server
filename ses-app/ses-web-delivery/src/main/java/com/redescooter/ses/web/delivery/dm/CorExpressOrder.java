package com.redescooter.ses.web.delivery.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-redescooter-ses-web-delivery-dm-CorExpressOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "cor_express_order")
public class CorExpressOrder implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户id")
    private Long tenantId;

    /**
     * 批次号
     */
    @TableField(value = "batch_no")
    @ApiModelProperty(value="批次号")
    private String batchNo;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value="订单号")
    private String orderNo;

    /**
     * 状态，待分配UNASGN,已分配ASGN，已完成COMPLETE
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态，待分配UNASGN,已分配ASGN，已完成COMPLETE")
    private String status;

    /**
     * 收货方国家
     */
    @TableField(value = "recipient_country")
    @ApiModelProperty(value="收货方国家")
    private String recipientCountry;

    /**
     * 收件方省份
     */
    @TableField(value = "recipient_province")
    @ApiModelProperty(value="收件方省份")
    private String recipientProvince;

    /**
     * 收件方城市
     */
    @TableField(value = "recipient_city")
    @ApiModelProperty(value="收件方城市")
    private String recipientCity;

    /**
     * 收件方邮编
     */
    @TableField(value = "recipient_postcode")
    @ApiModelProperty(value="收件方邮编")
    private String recipientPostcode;

    /**
     * 收件方详细地址
     */
    @TableField(value = "recipient_address")
    @ApiModelProperty(value="收件方详细地址")
    private String recipientAddress;

    /**
     * 收件方纬度
     */
    @TableField(value = "recipient_latitude")
    @ApiModelProperty(value="收件方纬度")
    private BigDecimal recipientLatitude;

    /**
     * 收货方经度
     */
    @TableField(value = "recipient_longitude")
    @ApiModelProperty(value="收货方经度")
    private BigDecimal recipientLongitude;

    /**
     * GeoHash
     */
    @TableField(value = "recipient_geohash")
    @ApiModelProperty(value="GeoHash")
    private String recipientGeohash;

    /**
     * 客户id，等价于收件人名称
     */
    @TableField(value = "customer_reference")
    @ApiModelProperty(value="客户id，等价于收件人名称")
    private String customerReference;

    /**
     * 收件人
     */
    @TableField(value = "recipient_name")
    @ApiModelProperty(value="收件人")
    private String recipientName;

    /**
     * 收件方公司，个人时为空
     */
    @TableField(value = "recipient_company")
    @ApiModelProperty(value="收件方公司，个人时为空")
    private String recipientCompany;

    /**
     * 收件人电话
     */
    @TableField(value = "recipient_phone")
    @ApiModelProperty(value="收件人电话")
    private String recipientPhone;

    /**
     * 收件方邮箱
     */
    @TableField(value = "recipient_mail")
    @ApiModelProperty(value="收件方邮箱")
    private String recipientMail;

    /**
     * 收件方备注
     */
    @TableField(value = "recipient_notes")
    @ApiModelProperty(value="收件方备注")
    private String recipientNotes;

    /**
     * 发货方国家
     */
    @TableField(value = "sender_country")
    @ApiModelProperty(value="发货方国家")
    private String senderCountry;

    /**
     * 发货方城市
     */
    @TableField(value = "sender_city")
    @ApiModelProperty(value="发货方城市")
    private String senderCity;

    /**
     * 发货方省份
     */
    @TableField(value = "sender_province")
    @ApiModelProperty(value="发货方省份")
    private String senderProvince;

    /**
     * 发货方邮编
     */
    @TableField(value = "sender_postcode")
    @ApiModelProperty(value="发货方邮编")
    private String senderPostcode;

    /**
     * 发货方详细地址
     */
    @TableField(value = "sender_address")
    @ApiModelProperty(value="发货方详细地址")
    private String senderAddress;

    /**
     * 发货方纬度
     */
    @TableField(value = "sender_latitude")
    @ApiModelProperty(value="发货方纬度")
    private BigDecimal senderLatitude;

    /**
     * 发货方经度
     */
    @TableField(value = "sender_longitude")
    @ApiModelProperty(value="发货方经度")
    private BigDecimal senderLongitude;

    /**
     * Geohash
     */
    @TableField(value = "sender_geohash")
    @ApiModelProperty(value="Geohash")
    private String senderGeohash;

    /**
     * 发货方公司，个人时为空
     */
    @TableField(value = "sender_company")
    @ApiModelProperty(value="发货方公司，个人时为空")
    private String senderCompany;

    /**
     * 发货人
     */
    @TableField(value = "sender_name")
    @ApiModelProperty(value="发货人")
    private String senderName;

    /**
     * 发货人手机
     */
    @TableField(value = "sender_phone")
    @ApiModelProperty(value="发货人手机")
    private String senderPhone;

    /**
     * 发货人邮箱
     */
    @TableField(value = "sender_mail")
    @ApiModelProperty(value="发货人邮箱")
    private String senderMail;

    /**
     * 发货方备注
     */
    @TableField(value = "sender_notes")
    @ApiModelProperty(value="发货方备注")
    private String senderNotes;

    /**
     * 包裹数
     */
    @TableField(value = "parcel_quantity")
    @ApiModelProperty(value="包裹数")
    private Integer parcelQuantity;

    /**
     * 是否已分配
     */
    @TableField(value = "assign_flag")
    @ApiModelProperty(value="是否已分配")
    private Boolean assignFlag;

    /**
     * 分配递送时间
     */
    @TableField(value = "assign_time")
    @ApiModelProperty(value="分配递送时间")
    private Date assignTime;

    /**
     * 车辆类型
     */
    @TableField(value = "vehicle_type")
    @ApiModelProperty(value="车辆类型")
    private String vehicleType;

    /**
     * 期望送达时间,注意这是时间段
     */
    @TableField(value = "expect_time_begin")
    @ApiModelProperty(value="期望送达时间,注意这是时间段")
    private Date expectTimeBegin;

    /**
     * 期望送达时间,注意这是时间段
     */
    @TableField(value = "expect_time_end")
    @ApiModelProperty(value="期望送达时间,注意这是时间段")
    private Date expectTimeEnd;

    /**
     * 其他备注
     */
    @TableField(value = "general_notes")
    @ApiModelProperty(value="其他备注")
    private String generalNotes;

    /**
     * 拒绝原因
     */
    @TableField(value = "denial_reason")
    @ApiModelProperty(value="拒绝原因")
    private String denialReason;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_BATCH_NO = "batch_no";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_STATUS = "status";

    public static final String COL_RECIPIENT_COUNTRY = "recipient_country";

    public static final String COL_RECIPIENT_PROVINCE = "recipient_province";

    public static final String COL_RECIPIENT_CITY = "recipient_city";

    public static final String COL_RECIPIENT_POSTCODE = "recipient_postcode";

    public static final String COL_RECIPIENT_ADDRESS = "recipient_address";

    public static final String COL_RECIPIENT_LATITUDE = "recipient_latitude";

    public static final String COL_RECIPIENT_LONGITUDE = "recipient_longitude";

    public static final String COL_RECIPIENT_GEOHASH = "recipient_geohash";

    public static final String COL_CUSTOMER_REFERENCE = "customer_reference";

    public static final String COL_RECIPIENT_NAME = "recipient_name";

    public static final String COL_RECIPIENT_COMPANY = "recipient_company";

    public static final String COL_RECIPIENT_PHONE = "recipient_phone";

    public static final String COL_RECIPIENT_MAIL = "recipient_mail";

    public static final String COL_RECIPIENT_NOTES = "recipient_notes";

    public static final String COL_SENDER_COUNTRY = "sender_country";

    public static final String COL_SENDER_CITY = "sender_city";

    public static final String COL_SENDER_PROVINCE = "sender_province";

    public static final String COL_SENDER_POSTCODE = "sender_postcode";

    public static final String COL_SENDER_ADDRESS = "sender_address";

    public static final String COL_SENDER_LATITUDE = "sender_latitude";

    public static final String COL_SENDER_LONGITUDE = "sender_longitude";

    public static final String COL_SENDER_GEOHASH = "sender_geohash";

    public static final String COL_SENDER_COMPANY = "sender_company";

    public static final String COL_SENDER_NAME = "sender_name";

    public static final String COL_SENDER_PHONE = "sender_phone";

    public static final String COL_SENDER_MAIL = "sender_mail";

    public static final String COL_SENDER_NOTES = "sender_notes";

    public static final String COL_PARCEL_QUANTITY = "parcel_quantity";

    public static final String COL_ASSIGN_FLAG = "assign_flag";

    public static final String COL_ASSIGN_TIME = "assign_time";

    public static final String COL_VEHICLE_TYPE = "vehicle_type";

    public static final String COL_EXPECT_TIME_BEGIN = "expect_time_begin";

    public static final String COL_EXPECT_TIME_END = "expect_time_end";

    public static final String COL_GENERAL_NOTES = "general_notes";

    public static final String COL_DENIAL_REASON = "denial_reason";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}