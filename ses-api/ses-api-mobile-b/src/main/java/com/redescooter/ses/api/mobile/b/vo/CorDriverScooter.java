package com.redescooter.ses.api.mobile.b.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-service-mobile-b-dm-base-CorDriverScooter")
@Data
public class CorDriverScooter implements Serializable {
    public static final String COL_USER_ID = "user_id";
    public static final String COL_RFID = "rfid";
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
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 司机ID
     */
    @ApiModelProperty(value = "司机ID")
    private Long driverId;

    /**
     * 车辆ID
     */
    @ApiModelProperty(value = "车辆ID")
    private Long scooterId;

    /**
     * 车辆分配开始时间
     */
    @ApiModelProperty(value = "车辆分配开始时间")
    private Date beginTime;

    /**
     * 车辆归还时间
     */
    @ApiModelProperty(value = "车辆归还时间")
    private Date endTime;

    /**
     * 状态 1 使用中，2 已还车
     */
    @ApiModelProperty(value = "状态 1 使用中，2 已还车")
    private String status;

    /**
     * 行驶里程
     */
    @ApiModelProperty(value = "行驶里程")
    private String mileage;

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
    private String def1;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_DRIVER_ID = "driver_id";

    public static final String COL_SCOOTER_ID = "scooter_id";

    public static final String COL_BEGIN_TIME = "begin_time";

    public static final String COL_END_TIME = "end_time";

    public static final String COL_STATUS = "status";

    public static final String COL_MILEAGE = "mileage";

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