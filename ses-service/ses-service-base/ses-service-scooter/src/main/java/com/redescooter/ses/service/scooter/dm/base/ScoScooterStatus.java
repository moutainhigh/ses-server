package com.redescooter.ses.service.scooter.dm.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="com-redescooter-ses-service-scooter-dm-base-ScoScooterStatus")
@Data
@TableName(value = "sco_scooter_status")
public class ScoScooterStatus implements Serializable {
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
     * 智能控制系统ID
     */
    @TableField(value = "scooter_ecu_id")
    @ApiModelProperty(value="智能控制系统ID")
    private Long scooterEcuId;

    /**
     * ScooterID
     */
    @TableField(value = "scooter_id")
    @ApiModelProperty(value="ScooterID")
    private Long scooterId;

    /**
     * 车锁状态
     */
    @TableField(value = "lock_status")
    @ApiModelProperty(value="车锁状态")
    private String lockStatus;

    /**
     * 后备箱状态
     */
    @TableField(value = "top_box_status")
    @ApiModelProperty(value="后备箱状态")
    private String topBoxStatus;

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
     * GEOHash
     */
    @TableField(value = "geohash")
    @ApiModelProperty(value="GEOHash")
    private String geohash;

    /**
     * 电池电量
     */
    @TableField(value = "battery")
    @ApiModelProperty(value="电池电量")
    private Integer battery;

    /**
     * 累积行驶里程
     */
    @TableField(value = "cumulative_mileage")
    @ApiModelProperty(value="累积行驶里程")
    private String cumulativeMileage;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value="乐观锁")
    private Integer revision;

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

    public static final String COL_SCOOTER_ECU_ID = "scooter_ecu_id";

    public static final String COL_SCOOTER_ID = "scooter_id";

    public static final String COL_LOCK_STATUS = "lock_status";

    public static final String COL_TOP_BOX_STATUS = "top_box_status";

    public static final String COL_LONGITULE = "longitule";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_GEOHASH = "geohash";

    public static final String COL_BATTERY = "battery";

    public static final String COL_CUMULATIVE_MILEAGE = "cumulative_mileage";

    public static final String COL_REVISION = "revision";

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