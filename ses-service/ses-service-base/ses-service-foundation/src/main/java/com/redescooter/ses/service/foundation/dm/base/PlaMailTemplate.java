package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaMailTemplate")
@Data
@TableName(value = "pla_mail_template")
public class PlaMailTemplate implements Serializable {
    /**
     * 主键
     */
    @TableField(value = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * Normal正常的，Disabled失效的
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "Normal正常的，Disabled失效的")
    private String status;

    /**
     * 模板编号
     */
    @TableField(value = "mail_template_no")
    @ApiModelProperty(value = "模板编号")
    private Integer mailTemplateNo;

    @TableField(value = "name")
    @ApiModelProperty(value = "null")
    private String name;

    /**
     * 邮件发送事件
     */
    @TableField(value = "event")
    @ApiModelProperty(value = "邮件发送事件")
    private String event;

    /**
     * 主题
     */
    @TableField(value = "subject")
    @ApiModelProperty(value = "主题")
    private String subject;

    /**
     * 说明
     */
    @TableField(value = "memo")
    @ApiModelProperty(value = "说明")
    private String memo;

    /**
     * 模板远程备份
     */
    @TableField(value = "backup")
    @ApiModelProperty(value = "模板远程备份")
    private String backup;

    /**
     * 邮件有效期，单位秒
     */
    @TableField(value = "expire")
    @ApiModelProperty(value = "邮件有效期，单位秒")
    private Integer expire;

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
     * 模板实际内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "模板实际内容")
    private String content;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_MAIL_TEMPLATE_NO = "mail_template_no";

    public static final String COL_NAME = "name";

    public static final String COL_EVENT = "event";

    public static final String COL_SUBJECT = "subject";

    public static final String COL_MEMO = "memo";

    public static final String COL_BACKUP = "backup";

    public static final String COL_EXPIRE = "expire";

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