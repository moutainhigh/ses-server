package com.redescooter.ses.web.delivery.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com.redescooter.ses.web.delivery.dm.CorDeliveryTrace")
@Data
@TableName(value = "cor_delivery_trace")
public class CorDeliveryTrace implements Serializable {
    /**
     * ID
     */
    @TableField(value = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * deliveryId
     */
    @TableField(value = "delivery_id")
    @ApiModelProperty(value = "deliveryId")
    private Long deliveryId;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * userId,即delivery表中的交付人id同时是司机的user_id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "userId,即delivery表中的交付人id同时是司机的user_id")
    private Long userId;

    /**
     * 订单状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "订单状态")
    private String status;

    /**
     * 事件
     */
    @TableField(value = "event")
    @ApiModelProperty(value = "事件")
    private String event;

    /**
     * 订单取消时，存储取消原因；订单拒绝时，存储拒绝原因；
     */
    @TableField(value = "reason")
    @ApiModelProperty(value = "订单取消时，存储取消原因；订单拒绝时，存储拒绝原因；")
    private String reason;

    /**
     * 事件时间
     */
    @TableField(value = "event_time")
    @ApiModelProperty(value = "事件时间")
    private Date eventTime;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 维度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value = "维度")
    private BigDecimal latitude;

    /**
     * GEOHASH
     */
    @TableField(value = "geohash")
    @ApiModelProperty(value = "GEOHASH")
    private String geohash;

    /**
     * scooterId
     */
    @TableField(value = "scooter_id")
    @ApiModelProperty(value = "scooterId")
    private Long scooterId;

    /**
     * scooter经度
     */
    @TableField(value = "scooter_longitude")
    @ApiModelProperty(value = "scooter经度")
    private BigDecimal scooterLongitude;

    /**
     * scooter维度
     */
    @TableField(value = "scooter_latitude")
    @ApiModelProperty(value = "scooter维度")
    private BigDecimal scooterLatitude;

    /**
     * scooterGEOHASH
     */
    @TableField(value = "scooter_location_geohash")
    @ApiModelProperty(value = "scooterGEOHASH")
    private String scooterLocationGeohash;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

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
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

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

    public static final String COL_DELIVERY_ID = "delivery_id";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_STATUS = "status";

    public static final String COL_EVENT = "event";

    public static final String COL_REASON = "reason";

    public static final String COL_EVENT_TIME = "event_time";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_GEOHASH = "geohash";

    public static final String COL_SCOOTER_ID = "scooter_id";

    public static final String COL_SCOOTER_LONGITUDE = "scooter_longitude";

    public static final String COL_SCOOTER_LATITUDE = "scooter_latitude";

    public static final String COL_SCOOTER_LOCATION_GEOHASH = "scooter_location_geohash";

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