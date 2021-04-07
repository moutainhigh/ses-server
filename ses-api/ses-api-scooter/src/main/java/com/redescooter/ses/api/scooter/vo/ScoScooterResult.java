package com.redescooter.ses.api.scooter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ScoScooterResult implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 车辆编号
     */
    @ApiModelProperty(value = "车辆编号")
    private String scooterNo;

    @ApiModelProperty(value = "车辆平板序列号")
    private String tabletSn;

    /**
     * 车辆图片
     */
    @ApiModelProperty(value = "车辆图片")
    private String picture;

    /**
     * 状态:LOCKED;UNLOCKED
     */
    @ApiModelProperty(value = "状态:LOCKED;UNLOCKED")
    private String status;

    /**
     * 总里程
     */
    @ApiModelProperty(value = "总里程")
    private Long totalMileage;

    /**
     * AVAILABLE;CHARGING;REPAIR;FAULT;USING
     */
    @ApiModelProperty(value = "AVAILABLE;CHARGING;REPAIR;FAULT;USING")
    private String availableStatus;

    /**
     * 后备箱状态
     */
    @ApiModelProperty(value = "后备箱状态")
    private String boxStatus;

    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    private String model;

    /**
     * 牌照
     */
    @ApiModelProperty(value = "牌照")
    private String licensePlate;

    /**
     * 牌照图片
     */
    @ApiModelProperty(value = "牌照图片")
    private String licensePlatePicture;

    /**
     * 上牌时间
     */
    @ApiModelProperty(value = "上牌时间")
    private Date licensePlateTime;

    /**
     * SCOOTER_ID图片
     */
    @ApiModelProperty(value = "SCOOTER_ID图片")
    private String scooterIdPicture;

    /**
     * 投保时间
     */
    @ApiModelProperty(value = "投保时间")
    private Date insureTime;

    /**
     * 保险
     */
    @ApiModelProperty(value = "保险")
    private String insurance;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

}