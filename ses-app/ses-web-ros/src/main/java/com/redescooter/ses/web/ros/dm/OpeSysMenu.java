package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysMenu")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
     * 菜单名字
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "菜单名字")
    private String name;

    /**
     * 菜单权限
     */
    @TableField(value = "permission")
    @ApiModelProperty(value = "菜单权限")
    private String permission;

    /**
     * 菜单路径
     */
    @TableField(value = "path")
    @ApiModelProperty(value = "菜单路径")
    private String path;

    /**
     * 父菜单ID
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    /**
     * 菜单icon
     */
    @TableField(value = "icon")
    @ApiModelProperty(value = "菜单icon")
    private String icon;

    /**
     * 排序值
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序值")
    private Integer sort;

    /**
     * 是否激活
     */
    @TableField(value = "keep_alive")
    @ApiModelProperty(value = "是否激活")
    private String keepAlive;

    /**
     * 菜单类型
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "菜单类型")
    private String type;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_NAME = "name";

    public static final String COL_PERMISSION = "permission";

    public static final String COL_PATH = "path";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_ICON = "icon";

    public static final String COL_SORT = "sort";

    public static final String COL_KEEP_ALIVE = "keep_alive";

    public static final String COL_TYPE = "type";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";
}