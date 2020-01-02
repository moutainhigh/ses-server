package com.redescooter.ses.service.hub.source.consumer.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-service-hub-source-consumer-dm-ConUserScooter")
@Data
@TableName(value = "con_user_scooter")
public class ConUserScooter implements Serializable {
    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
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
     * 租户ID
     */
    @TableField(value = "TENANT_ID")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 司机ID
     */
    @TableField(value = "USER_ID")
    @ApiModelProperty(value = "司机ID")
    private Long userId;

    /**
     * 车辆ID
     */
    @TableField(value = "SCOOTER_ID")
    @ApiModelProperty(value = "车辆ID")
    private Long scooterId;

    /**
     * RFID
     */
    @TableField(value = "RFID")
    @ApiModelProperty(value = "RFID")
    private Long rfid;

    /**
     * 车辆分配开始时间
     */
    @TableField(value = "BEGIN_TIME")
    @ApiModelProperty(value = "车辆分配开始时间")
    private Date beginTime;

    /**
     * 车辆归还时间
     */
    @TableField(value = "END_TIME")
    @ApiModelProperty(value = "车辆归还时间")
    private Date endTime;

    /**
     * 状态 USED，FINSH，使用中、未使用
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value = "状态 USED，FINSH，使用中、未使用")
    private String status;

    /**
     * 行驶里程
     */
    @TableField(value = "MILEAGE")
    @ApiModelProperty(value = "行驶里程")
    private Double mileage;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "UPDATED_BY")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATED_TIME")
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

    public static final String COL_ID = "ID";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "TENANT_ID";

    public static final String COL_USER_ID = "USER_ID";

    public static final String COL_SCOOTER_ID = "SCOOTER_ID";

    public static final String COL_RFID = "RFID";

    public static final String COL_BEGIN_TIME = "BEGIN_TIME";

    public static final String COL_END_TIME = "END_TIME";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_MILEAGE = "MILEAGE";

    public static final String COL_CREATED_BY = "CREATED_BY";

    public static final String COL_CREATED_TIME = "CREATED_TIME";

    public static final String COL_UPDATED_BY = "UPDATED_BY";

    public static final String COL_UPDATED_TIME = "UPDATED_TIME";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}