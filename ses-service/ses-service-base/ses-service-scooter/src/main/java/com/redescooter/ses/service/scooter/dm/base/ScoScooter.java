package com.redescooter.ses.service.scooter.dm.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@ApiModel(value = "com-redescooter-ses-service-scooter-dm-base-ScoScooter")
@Data
@TableName(value = "sco_scooter")
public class ScoScooter implements Serializable {
    public static final String COL_DEF1 = "def1";
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
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 车辆编号
     */
    @TableField(value = "scooter_no")
    @ApiModelProperty(value = "车辆编号")
    private String scooterNo;

    /**
     * 车辆图片
     */
    @TableField(value = "picture")
    @ApiModelProperty(value = "车辆图片")
    private String picture;

    /**
     * 状态:LOCKED;UNLOCKED
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态:LOCKED;UNLOCKED")
    private String status;

    /**
     * 总里程
     */
    @TableField(value = "total_mileage")
    @ApiModelProperty(value = "总里程")
    private Long totalMileage;

    /**
     * AVAILABLE;CHARGING;REPAIR;FAULT;USING
     */
    @TableField(value = "available_status")
    @ApiModelProperty(value = "AVAILABLE;CHARGING;REPAIR;FAULT;USING")
    private String availableStatus;

    /**
     * 后备箱状态
     */
    @TableField(value = "box_status")
    @ApiModelProperty(value = "后备箱状态")
    private String boxStatus;

    /**
     * 型号
     */
    @TableField(value = "model")
    @ApiModelProperty(value = "型号")
    private String model;

    /**
     * 牌照
     */
    @TableField(value = "license_plate")
    @ApiModelProperty(value = "牌照")
    private String licensePlate;

    /**
     * 牌照图片
     */
    @TableField(value = "license_plate_picture")
    @ApiModelProperty(value = "牌照图片")
    private String licensePlatePicture;

    /**
     * 上牌时间
     */
    @TableField(value = "license_plate_time")
    @ApiModelProperty(value = "上牌时间")
    private Date licensePlateTime;

    /**
     * SCOOTER_ID图片
     */
    @TableField(value = "scooter_id_picture")
    @ApiModelProperty(value = "SCOOTER_ID图片")
    private String scooterIdPicture;

    /**
     * 投保时间
     */
    @TableField(value = "insure_time")
    @ApiModelProperty(value = "投保时间")
    private Date insureTime;

    /**
     * 保险
     */
    @TableField(value = "insurance")
    @ApiModelProperty(value = "保险")
    private String insurance;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

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

    public static final String COL_SCOOTER_NO = "scooter_no";

    public static final String COL_PICTURE = "picture";

    public static final String COL_STATUS = "status";

    public static final String COL_TOTAL_MILEAGE = "total_mileage";

    public static final String COL_AVAILABLE_STATUS = "available_status";

    public static final String COL_BOX_STATUS = "box_status";

    public static final String COL_MODEL = "model";

    public static final String COL_LICENSE_PLATE = "license_plate";

    public static final String COL_LICENSE_PLATE_PICTURE = "license_plate_picture";

    public static final String COL_LICENSE_PLATE_TIME = "license_plate_time";

    public static final String COL_SCOOTER_ID_PICTURE = "scooter_id_picture";

    public static final String COL_INSURE_TIME = "insure_time";

    public static final String COL_INSURANCE = "insurance";

    public static final String COL_REVISION = "revision";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}