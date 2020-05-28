package com.redescooter.ses.web.delivery.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-delivery-dm-CorDelivery")
@Data
@TableName(value = "cor_delivery")
public class CorDelivery implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    @TableLogic
    private Integer dr;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 交付人,即司机账号的USER_ID字段的值
     */
    @TableField(value = "deliverer_id")
    @ApiModelProperty(value = "交付人,即司机账号的USER_ID字段的值")
    private Long delivererId;

    /**
     * scooterId
     */
    @TableField(value = "scooter_id")
    @ApiModelProperty(value = "scooterId")
    private Long scooterId;

    /**
     * 收件人
     */
    @TableField(value = "recipient")
    @ApiModelProperty(value = "收件人")
    private String recipient;

    /**
     * 收件人邮箱
     */
    @TableField(value = "recipient_email")
    @ApiModelProperty(value = "收件人邮箱")
    private String recipientEmail;

    /**
     * 手机国家区号
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value = "手机国家区号")
    private String countryCode;

    /**
     * 收件人电话
     */
    @TableField(value = "recipient_tel")
    @ApiModelProperty(value = "收件人电话")
    private String recipientTel;

    /**
     * 收件人地址
     */
    @TableField(value = "recipient_address")
    @ApiModelProperty(value = "收件人地址")
    private String recipientAddress;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    /**
     * GeoHash
     */
    @TableField(value = "geohash")
    @ApiModelProperty(value = "GeoHash")
    private String geohash;

    /**
     * 门牌信息
     */
    @TableField(value = "house_info")
    @ApiModelProperty(value = "门牌信息")
    private String houseInfo;

    /**
     * 包裹数量
     */
    @TableField(value = "parcel_quantity")
    @ApiModelProperty(value = "包裹数量")
    private Integer parcelQuantity;

    /**
     * 商品清单
     */
    @TableField(value = "goods_inventory")
    @ApiModelProperty(value = "商品清单")
    private String goodsInventory;

    /**
     * 订单服务结果  按时、延迟、取消  ONTIME、DELAY、CANCEl
     */
    @TableField(value = "result")
    @ApiModelProperty(value = "订单服务结果  按时、延迟、取消  ONTIME、DELAY、CANCEl")
    private String result;

    /**
     * 订单状态:PENDING待配送，  DELIVERING正在配送，REJECTED拒绝，TIMEOUT配送超时，COMPLETE已送达，CANCEL取消失败；PC订单状态显示:待配送Pending,配送中In  progress,拒单Declined,配送超时Overdue,超时完成Delayed,已送达Delivered,取消订单Cancelled
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "订单状态:PENDING待配送，  DELIVERING正在配送，REJECTED拒绝，TIMEOUT配送超时，COMPLETE已送达，CANCEL取消失败；PC订单状态显示:待配送Pending,配送中In  progress,拒单Declined,配送超时Overdue,超时完成Delayed,已送达Delivered,取消订单Cancelled")
    private String status;

    /**
     * 安排配送时间
     */
    @TableField(value = "planned_time")
    @ApiModelProperty(value = "安排配送时间")
    private String plannedTime;

    /**
     * 超时预警值，单位分钟
     */
    @TableField(value = "timeout_expectde")
    @ApiModelProperty(value = "超时预警值，单位分钟")
    private String timeoutExpectde;

    /**
     * 超时预警时间
     */
    @TableField(value = "time_out")
    @ApiModelProperty(value = "超时预警时间")
    private Date timeOut;

    /**
     * 预计开始时间
     */
    @TableField(value = "etd")
    @ApiModelProperty(value = "预计开始时间")
    private Date etd;

    /**
     * 实际开始时间
     */
    @TableField(value = "atd")
    @ApiModelProperty(value = "实际开始时间")
    private Date atd;

    /**
     * 预计送达时间
     */
    @TableField(value = "eta")
    @ApiModelProperty(value = "预计送达时间")
    private Date eta;

    /**
     * 实际送达时间
     */
    @TableField(value = "ata")
    @ApiModelProperty(value = "实际送达时间")
    private Date ata;

    /**
     * 骑行里程
     */
    @TableField(value = "driven_mileage")
    @ApiModelProperty(value = "骑行里程")
    private BigDecimal drivenMileage;

    /**
     * 骑行时长
     */
    @TableField(value = "driven_duration")
    @ApiModelProperty(value = "骑行时长")
    private Integer drivenDuration;

    /**
     * 二氧化碳排放量
     */
    @TableField(value = "co2")
    @ApiModelProperty(value = "二氧化碳排放量")
    private BigDecimal co2;

    /**
     * 节省金额
     */
    @TableField(value = "savings")
    @ApiModelProperty(value = "节省金额")
    private BigDecimal savings;

    /**
     * 0 标识未统计，1 标识 已统计
     */
    @TableField(value = "statistics")
    @ApiModelProperty(value = "0 标识未统计，1 标识 已统计")
    private String statistics;

    /**
     * 标签
     */
    @TableField(value = "label")
    @ApiModelProperty(value = "标签")
    private String label;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_DELIVERER_ID = "deliverer_id";

    public static final String COL_SCOOTER_ID = "scooter_id";

    public static final String COL_RECIPIENT = "recipient";

    public static final String COL_RECIPIENT_EMAIL = "recipient_email";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_RECIPIENT_TEL = "recipient_tel";

    public static final String COL_RECIPIENT_ADDRESS = "recipient_address";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_GEOHASH = "geohash";

    public static final String COL_HOUSE_INFO = "house_info";

    public static final String COL_PARCEL_QUANTITY = "parcel_quantity";

    public static final String COL_GOODS_INVENTORY = "goods_inventory";

    public static final String COL_RESULT = "result";

    public static final String COL_STATUS = "status";

    public static final String COL_PLANNED_TIME = "planned_time";

    public static final String COL_TIMEOUT_EXPECTDE = "timeout_expectde";

    public static final String COL_TIME_OUT = "time_out";

    public static final String COL_ETD = "etd";

    public static final String COL_ATD = "atd";

    public static final String COL_ETA = "eta";

    public static final String COL_ATA = "ata";

    public static final String COL_DRIVEN_MILEAGE = "driven_mileage";

    public static final String COL_DRIVEN_DURATION = "driven_duration";

    public static final String COL_CO2 = "co2";

    public static final String COL_SAVINGS = "savings";

    public static final String COL_STATISTICS = "statistics";

    public static final String COL_LABEL = "label";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}
