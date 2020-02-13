package com.redescooter.ses.web.delivery.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-redescooter-ses-web-delivery-dm-CorTenantScooter")
@Data
@TableName(value = "cor_tenant_scooter")
public class CorTenantScooter implements Serializable {
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
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * tenantId
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="tenantId")
    private Long tenantId;

    /**
     * 车辆型号
     */
    @TableField(value = "model")
    @ApiModelProperty(value="车辆型号")
    private String model;

    /**
     * 车辆ID
     */
    @TableField(value = "scooter_id")
    @ApiModelProperty(value="车辆ID")
    private Long scooterId;

    /**
     * 车辆经度
     */
    @TableField(value = "longitule")
    @ApiModelProperty(value="车辆经度")
    private BigDecimal longitule;

    /**
     * 车辆纬度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value="车辆纬度")
    private BigDecimal latitude;

    /**
     * 车牌号
     */
    @TableField(value = "license_plate")
    @ApiModelProperty(value="车牌号")
    private String licensePlate;

    /**
     * 车牌号图片
     */
    @TableField(value = "license_plate_picture")
    @ApiModelProperty(value="车牌号图片")
    private String licensePlatePicture;

    /**
     * 车辆状态 AVAILABLE;CHARGING;REPAIR;FAULT;USEING
     */
    @TableField(value = "status")
    @ApiModelProperty(value="车辆状态 AVAILABLE;CHARGING;REPAIR;FAULT;USEING")
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

    public static final String COL_MODEL = "model";

    public static final String COL_SCOOTER_ID = "scooter_id";

    public static final String COL_LONGITULE = "longitule";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_LICENSE_PLATE = "license_plate";

    public static final String COL_LICENSE_PLATE_PICTURE = "license_plate_picture";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}