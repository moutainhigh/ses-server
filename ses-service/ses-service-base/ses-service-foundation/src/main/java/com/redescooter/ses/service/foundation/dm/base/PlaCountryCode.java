package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "com.redescooter.ses.service.dm.base.PlaCountryCode")
@Data
@TableName(value = "pla_country_code")
public class PlaCountryCode implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除主键
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除主键")
    private Integer dr;

    /**
     * 国家国旗图标
     */
    @TableField(value = "icon")
    @ApiModelProperty(value = "国家国旗图标")
    private String icon;

    /**
     * 国家编码
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value = "国家编码")
    private String countryCode;

    /**
     * 国家名称
     */
    @TableField(value = "country_name")
    @ApiModelProperty(value = "国家名称")
    private String countryName;

    /**
     * 国家语言
     */
    @TableField(value = "country_language")
    @ApiModelProperty(value = "国家语言")
    private String countryLanguage;

    /**
     * 时区
     */
    @TableField(value = "time_zone")
    @ApiModelProperty(value = "时区")
    private String timeZone;

    @TableField(value = "def1")
    @ApiModelProperty(value = "null")
    private String def1;

    @TableField(value = "def2")
    @ApiModelProperty(value = "null")
    private String def2;

    @TableField(value = "def3")
    @ApiModelProperty(value = "null")
    private String def3;

    private static final long serialVersionUID = 1L;

    public static final String COL_DR = "dr";

    public static final String COL_ICON = "icon";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_COUNTRY_NAME = "country_name";

    public static final String COL_COUNTRY_LANGUAGE = "country_language";

    public static final String COL_TIME_ZONE = "time_zone";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";
}