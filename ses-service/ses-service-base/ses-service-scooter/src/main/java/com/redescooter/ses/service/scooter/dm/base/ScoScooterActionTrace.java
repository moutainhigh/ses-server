package com.redescooter.ses.service.scooter.dm.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="com-redescooter-ses-service-scooter-dm-base-ScoScooterActionTrace")
@Data
@TableName(value = "sco_scooter_action_trace")
public class ScoScooterActionTrace implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    /**
     * 司机ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="司机ID")
    private Long userId;

    /**
     * 车辆ID
     */
    @TableField(value = "scooter_id")
    @ApiModelProperty(value="车辆ID")
    private Long scooterId;

    /**
     * 用户经度
     */
    @TableField(value = "user_longitule")
    @ApiModelProperty(value="用户经度")
    private BigDecimal userLongitule;

    /**
     * 用户纬度
     */
    @TableField(value = "user_latitude")
    @ApiModelProperty(value="用户纬度")
    private BigDecimal userLatitude;

    /**
     * 用户GeoHash
     */
    @TableField(value = "user_location_geohash")
    @ApiModelProperty(value="用户GeoHash")
    private String userLocationGeohash;

    /**
     * 操作类型 1 开锁 2 关锁 3 开导航 4 结束导航
     */
    @TableField(value = "action_type")
    @ApiModelProperty(value="操作类型 1 开锁 2 关锁 3 开导航 4 结束导航")
    private String actionType;

    /**
     * 操作结果 1 成功 0 失败
     */
    @TableField(value = "action_result")
    @ApiModelProperty(value="操作结果 1 成功 0 失败")
    private String actionResult;

    /**
     * 操作时间
     */
    @TableField(value = "action_time")
    @ApiModelProperty(value="操作时间")
    private Date actionTime;

    /**
     * 电池电量
     */
    @TableField(value = "battery")
    @ApiModelProperty(value="电池电量")
    private Integer battery;

    /**
     * 经度
     */
    @TableField(value = "longitule")
    @ApiModelProperty(value="经度")
    private BigDecimal longitule;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value="纬度")
    private BigDecimal latitude;

    /**
     * GeoHash
     */
    @TableField(value = "geohash")
    @ApiModelProperty(value="GeoHash")
    private String geohash;

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

    public static final String COL_USER_ID = "user_id";

    public static final String COL_SCOOTER_ID = "scooter_id";

    public static final String COL_USER_LONGITULE = "user_longitule";

    public static final String COL_USER_LATITUDE = "user_latitude";

    public static final String COL_USER_LOCATION_GEOHASH = "user_location_geohash";

    public static final String COL_ACTION_TYPE = "action_type";

    public static final String COL_ACTION_RESULT = "action_result";

    public static final String COL_ACTION_TIME = "action_time";

    public static final String COL_BATTERY = "battery";

    public static final String COL_LONGITULE = "longitule";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_GEOHASH = "geohash";

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