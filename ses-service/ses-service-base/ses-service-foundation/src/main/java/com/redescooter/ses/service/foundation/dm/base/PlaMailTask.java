package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaMailTask")
@Data
@TableName(value = "pla_mail_task")
public class PlaMailTask implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * PENDING待发送，SUCCESS发送成功，FAIL发送失败
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "PENDING待发送，SUCCESS发送成功，FAIL发送失败")
    private String status;

    /**
     * 系统ID
     */
    @TableField(value = "system_id")
    @ApiModelProperty(value = "系统ID")
    private String systemId;

    /**
     * 应用ID
     */
    @TableField(value = "app_id")
    @ApiModelProperty(value = "应用ID")
    private String appId;

    /**
     * 请求ID
     */
    @TableField(value = "request_id")
    @ApiModelProperty(value = "请求ID")
    private String requestId;

    /**
     * 模板编号
     */
    @TableField(value = "mail_template_no")
    @ApiModelProperty(value = "模板编号")
    private Integer mailTemplateNo;

    /**
     * 发送方
     */
    @TableField(value = "send_mail")
    @ApiModelProperty(value = "发送方")
    private String sendMail;

    /**
     * 接受方
     */
    @TableField(value = "receive_mail")
    @ApiModelProperty(value = "接受方")
    private String receiveMail;

    /**
     * 收件方用户ID
     */
    @TableField(value = "to_user_id")
    @ApiModelProperty(value = "收件方用户ID")
    private Long toUserId;

    /**
     * 主题
     */
    @TableField(value = "subject")
    @ApiModelProperty(value = "主题")
    private String subject;

    /**
     * 附件地址，可为空
     */
    @TableField(value = "file_path")
    @ApiModelProperty(value = "附件地址，可为空")
    private String filePath;

    /**
     * 发送json保存
     */
    @TableField(value = "parameter")
    @ApiModelProperty(value = "发送json保存")
    private String parameter;

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

    /**
     * 内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "内容")
    private String content;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_SYSTEM_ID = "system_id";

    public static final String COL_APP_ID = "app_id";

    public static final String COL_REQUEST_ID = "request_id";

    public static final String COL_MAIL_TEMPLATE_NO = "mail_template_no";

    public static final String COL_SEND_MAIL = "send_mail";

    public static final String COL_RECEIVE_MAIL = "receive_mail";

    public static final String COL_TO_USER_ID = "to_user_id";

    public static final String COL_SUBJECT = "subject";

    public static final String COL_FILE_PATH = "file_path";

    public static final String COL_PARAMETER = "parameter";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static final String COL_CONTENT = "content";
}