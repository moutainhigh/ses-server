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

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaMailConfig")
@Data
@TableName(value = "pla_mail_config")
public class PlaMailConfig implements Serializable {
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
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * normal正常，Disabled失效的
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "normal正常，Disabled失效的")
    private String status;

    /**
     * 模板编号
     */
    @TableField(value = "mail_template_no")
    @ApiModelProperty(value = "模板编号")
    private Integer mailTemplateNo;

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
     * 对应key，不可为空
     */
    @TableField(value = "param_key")
    @ApiModelProperty(value = "对应key，不可为空")
    private String paramKey;

    /**
     * 对应值域，可为空
     */
    @TableField(value = "param_value")
    @ApiModelProperty(value = "对应值域，可为空")
    private String paramValue;

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

    public static final String COL_STATUS = "status";

    public static final String COL_MAIL_TEMPLATE_NO = "mail_template_no";

    public static final String COL_SYSTEM_ID = "system_id";

    public static final String COL_APP_ID = "app_id";

    public static final String COL_PARAM_KEY = "param_key";

    public static final String COL_PARAM_VALUE = "param_value";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}