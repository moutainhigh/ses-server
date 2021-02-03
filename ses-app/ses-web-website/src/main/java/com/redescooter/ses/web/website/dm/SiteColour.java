package com.redescooter.ses.web.website.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 颜色表
 */
@ApiModel(value = "com-redescooter-ses-web-website-dm-SiteColour")
@Data
@TableName(value = "site_colour")
public class SiteColour implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 状态,1正常，-1失效
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态,1正常，-1失效")
    private Integer status;

    /**
     * 颜色使用范围,1整车，-1配件
     */
    @TableField(value = "colour_range")
    @ApiModelProperty(value = "颜色使用范围,1整车，-1配件")
    private String colourRange;

    /**
     * 颜色名称
     */
    @TableField(value = "colour_name")
    @ApiModelProperty(value = "颜色名称")
    private String colourName;

    /**
     * 颜色编码
     */
    @TableField(value = "colour_code")
    @ApiModelProperty(value = "颜色编码")
    private String colourCode;

    /**
     * 颜色RGB值
     */
    @TableField(value = "colour_RGB")
    @ApiModelProperty(value = "颜色RGB值")
    private String colourRgb;

    /**
     * 颜色16进制颜色编码
     */
    @TableField(value = "colour_16")
    @ApiModelProperty(value = "颜色16进制颜色编码")
    private String colour16;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 是否同步
     */
    @TableField(value = "synchronize_flag")
    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

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
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_COLOUR_RANGE = "colour_range";

    public static final String COL_COLOUR_NAME = "colour_name";

    public static final String COL_COLOUR_CODE = "colour_code";

    public static final String COL_COLOUR_RGB = "colour_RGB";

    public static final String COL_COLOUR_16 = "colour_16";

    public static final String COL_REMARK = "remark";

    public static final String COL_SYNCHRONIZE_FLAG = "synchronize_flag";

    public static final String COL_REVISION = "revision";

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
