package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com.redescooter.ses.service.foundation.dm.base.PlaMessage")
@Data
@TableName(value = "pla_message")
public class PlaMessage implements Serializable {
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
     * 系统ID
     */
    @TableField(value = "system_id")
    @ApiModelProperty(value="系统ID")
    private String systemId;

    /**
     * 应用ID
     */
    @TableField(value = "app_id")
    @ApiModelProperty(value="应用ID")
    private String appId;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 消息类型：推送消息PUSH，站内消息SITE
     */
    @TableField(value = "message_type")
    @ApiModelProperty(value="消息类型：推送消息PUSH，站内消息SITE")
    private String messageType;

    /**
     * 消息类型
     */
    @TableField(value = "biz_type")
    @ApiModelProperty(value="消息类型")
    private String bizType;

    /**
     * 业务Id
     */
    @TableField(value = "biz_id")
    @ApiModelProperty(value="业务Id")
    private String bizId;

    /**
     * 消息状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="消息状态")
    private String status;

    /**
     * 当前业务的业务状态
     */
    @TableField(value = "business_status")
    @ApiModelProperty(value="当前业务的业务状态")
    private String businessStatus;

    /**
     * 消息标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="消息标题")
    private String title;

    /**
     * 消息内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value="消息内容")
    private String content;

    /**
     * 消息内容参数
     */
    @TableField(value = "memo")
    @ApiModelProperty(value="消息内容参数")
    private String memo;

    /**
     * 发送时间
     */
    @TableField(value = "send_time")
    @ApiModelProperty(value="发送时间")
    private Date sendTime;

    /**
     * 已读时间
     */
    @TableField(value = "read_time")
    @ApiModelProperty(value="已读时间")
    private Date readTime;

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

    public static final String COL_DR = "dr";

    public static final String COL_SYSTEM_ID = "system_id";

    public static final String COL_APP_ID = "app_id";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_MESSAGE_TYPE = "message_type";

    public static final String COL_BIZ_TYPE = "biz_type";

    public static final String COL_BIZ_ID = "biz_id";

    public static final String COL_STATUS = "status";

    public static final String COL_BUSINESS_STATUS = "business_status";

    public static final String COL_TITLE = "title";

    public static final String COL_CONTENT = "content";

    public static final String COL_MEMO = "memo";

    public static final String COL_SEND_TIME = "send_time";

    public static final String COL_READ_TIME = "read_time";

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