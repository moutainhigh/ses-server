package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 菜单权限表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysMenu")
@Data
@TableName(value = "ope_sys_menu")
public class OpeSysMenu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "菜单ID")
    private Long id;

    /**
     * 逻辑删除标识
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识")
    private Integer dr;

    /**
     * 父菜单ID
     */
    @TableField(value = "p_id")
    @ApiModelProperty(value = "父菜单ID")
    private Long pId;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 菜单编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value = "菜单编码")
    private String code;

    /**
     * 权限码
     */
    @TableField(value = "permission")
    @ApiModelProperty(value = "权限码")
    private String permission;

    /**
     * 路由
     */
    @TableField(value = "path")
    @ApiModelProperty(value = "路由")
    private String path;

    /**
     * 对应路由组件component
     */
    @TableField(value = "component")
    @ApiModelProperty(value = "对应路由组件component")
    private String component;

    /**
     * 菜单类型：0菜单，1按钮，2：目录，3开放API
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "菜单类型：0菜单，1按钮，2：目录，3开放API")
    private String type;

    /**
     * 是否外链。0：for，1：是
     */
    @TableField(value = "if_to_link")
    @ApiModelProperty(value = "是否外链。0：for，1：是")
    private String ifToLink;

    /**
     * 员工状态 1：正常，2：禁用
     */
    @TableField(value = "menu_status")
    @ApiModelProperty(value = "状态 1：正常，2：禁用")
    private Integer menuStatus;

    /**
     * 图表
     */
    @TableField(value = "icon")
    @ApiModelProperty(value = "图表")
    private String icon;

    /**
     * 等级
     */
    @TableField(value = "level")
    @ApiModelProperty(value = "等级")
    private Integer level;

    /**
     * 菜单权重
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "菜单权重")
    private Integer sort;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

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
    private BigDecimal def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private String def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_P_ID = "p_id";

    public static final String COL_NAME = "name";

    public static final String COL_CODE = "code";

    public static final String COL_PERMISSION = "permission";

    public static final String COL_PATH = "path";

    public static final String COL_COMPONENT = "component";

    public static final String COL_TYPE = "type";

    public static final String COL_IF_TO_LINK = "if_to_link";

    public static final String COL_MENU_STATUS = "menu_status";

    public static final String COL_ICON = "icon";

    public static final String COL_LEVEL = "level";

    public static final String COL_SORT = "sort";

    public static final String COL_REMARK = "remark";

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