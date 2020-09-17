package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统参数设置
 */
@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaSysParamSetting")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pla_sys_param_setting")
public class PlaSysParamSetting implements Serializable {
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
    @TableLogic
    private Integer dr;

    /**
     * 系统类型
     */
    @TableField(value = "system_type")
    @ApiModelProperty(value = "系统类型")
    private String systemType;

    /**
     * 分组Id
     */
    @TableField(value = "group_id")
    @ApiModelProperty(value = "分组Id")
    private Long groupId;

    /**
     * 参数名
     */
    @TableField(value = "`parameter_name`")
    @ApiModelProperty(value = "参数名")
    private String parameterName;

    /**
     * key值
     */
    @TableField(value = "`key`")
    @ApiModelProperty(value = "key值")
    private String key;

    /**
     * 属性值
     */
    @TableField(value = "`value`")
    @ApiModelProperty(value = "属性值")
    private String value;

    /**
     * 是否启用
     */
    @TableField(value = "`enable`")
    @ApiModelProperty(value = "是否启用")
    private Boolean enable;

    /**
     * 描述
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value = "描述")
    private String desc;

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
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

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

    public static final String COL_SYSTEM_TYPE = "system_type";

    public static final String COL_GROUP_ID = "group_id";

    public static final String COL_PARAMETER_NAME = "parameter_name";

    public static final String COL_KEY = "key";

    public static final String COL_VALUE = "value";

    public static final String COL_ENABLE = "enable";

    public static final String COL_DESC = "desc";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}