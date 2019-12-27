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

@ApiModel(value="com-redescooter-ses-service-scooter-dm-base-ScoScooterNavigation")
@Data
@TableName(value = "sco_scooter_navigation")
public class ScoScooterNavigation implements Serializable {
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
     * 车辆ID
     */
    @TableField(value = "scooter_id")
    @ApiModelProperty(value="车辆ID")
    private Long scooterId;

    /**
     * 车辆编号
     */
    @TableField(value = "scooter_no")
    @ApiModelProperty(value="车辆编号")
    private String scooterNo;

    /**
     * 目的地
     */
    @TableField(value = "destination")
    @ApiModelProperty(value="目的地")
    private String destination;

    /**
     * 目的地经度
     */
    @TableField(value = "destination_longitude")
    @ApiModelProperty(value="目的地经度")
    private BigDecimal destinationLongitude;

    /**
     * 目的地纬度
     */
    @TableField(value = "destination_latitude")
    @ApiModelProperty(value="目的地纬度")
    private BigDecimal destinationLatitude;

    /**
     * 状态 NORMAL;PROCESSING;COMPLETED;
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态 NORMAL;PROCESSING;COMPLETED;")
    private String status;

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

    public static final String COL_SCOOTER_ID = "scooter_id";

    public static final String COL_SCOOTER_NO = "scooter_no";

    public static final String COL_DESTINATION = "destination";

    public static final String COL_DESTINATION_LONGITUDE = "destination_longitude";

    public static final String COL_DESTINATION_LATITUDE = "destination_latitude";

    public static final String COL_STATUS = "status";

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