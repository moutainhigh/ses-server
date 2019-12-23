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

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaSysConfig")
@Data
@TableName(value = "pla_sys_config")
public class PlaSysConfig implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    @TableField(value = "group")
    @ApiModelProperty(value = "null")
    private String group;

    @TableField(value = "key")
    @ApiModelProperty(value = "null")
    private String key;

    @TableField(value = "name")
    @ApiModelProperty(value = "null")
    private String name;

    @TableField(value = "value")
    @ApiModelProperty(value = "null")
    private String value;

    @TableField(value = "desc")
    @ApiModelProperty(value = "null")
    private String desc;

    @TableField(value = "update_time")
    @ApiModelProperty(value = "null")
    private Date updateTime;

    @TableField(value = "created_time")
    @ApiModelProperty(value = "null")
    private Date createdTime;

    @TableField(value = "deleted")
    @ApiModelProperty(value = "null")
    private Boolean deleted;

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

    public static final String COL_GROUP = "group";

    public static final String COL_KEY = "key";

    public static final String COL_NAME = "name";

    public static final String COL_VALUE = "value";

    public static final String COL_DESC = "desc";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_DELETED = "deleted";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}