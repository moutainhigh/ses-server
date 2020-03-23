package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpePurchasTrace")
@Data
@TableName(value = "ope_purchas_trace")
public class OpePurchasTrace implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 租户Id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    /**
     * userId
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "userId")
    private Long userId;

    /**
     * 采购订单id
     */
    @TableField(value = "purchas_id")
    @ApiModelProperty(value = "采购订单id")
    private Long purchasId;

    /**
     * 采购单状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "采购单状态")
    private String status;

    /**
     * 事件
     */
    @TableField(value = "event")
    @ApiModelProperty(value = "事件")
    private String event;

    /**
     * 事件时间
     */
    @TableField(value = "event_time")
    @ApiModelProperty(value = "事件时间")
    private Date eventTime;

    /**
     * 备注 放话术参数
     */
    @TableField(value = "memo")
    @ApiModelProperty(value = "备注 放话术参数")
    private String memo;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人")
    private Long createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新人")
    private Integer updateBy;

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

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_PURCHAS_ID = "purchas_id";

    public static final String COL_STATUS = "status";

    public static final String COL_EVENT = "event";

    public static final String COL_EVENT_TIME = "event_time";

    public static final String COL_MEMO = "memo";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}