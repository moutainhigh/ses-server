package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统角色表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysRole")
@Data
@Builder
@TableName(value = "ope_sys_role")
public class OpeSysRole {
    private static final long serialVersionUID = 1L;
    /**
     * 主键Id
     */
    @TableId(value = "id")
    @ApiModelProperty(value = "主键Id")
    private Long id;

    /**
     * 逻辑删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * 所属岗位id
     */
    @TableField(value = "position_id")
    @ApiModelProperty(value = "所属岗位id")
    private Long positionId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    /**
     * 角色状态  1：正常，2：禁用
     */
    @TableField(value = "role_status")
    @ApiModelProperty(value = "角色状态  1：正常，2：禁用")
    private Integer roleStatus;

    /**
     * 角色描述
     */
    @TableField(value = "role_desc")
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 是否开启销售区域功能 0：否，1：是
     */
    @TableField(value = "sale_area")
    @ApiModelProperty(value = "是否开启销售区域功能 0：否，1：是")
    private Integer saleArea;

    /**
     * 系统内置标识
     */
    @TableField(value = "system_root")
    @ApiModelProperty(value = "系统内置标识")
    private String systemRoot;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

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
    @TableField(value = "def4")
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_POSITION_ID = "position_id";

    public static final String COL_ROLE_NAME = "role_name";

    public static final String COL_ROLE_CODE = "role_code";

    public static final String COL_ROLE_STATUS = "role_status";

    public static final String COL_ROLE_DESC = "role_desc";

    public static final String COL_SORT = "sort";

    public static final String COL_SALE_AREA = "sale_area";

    public static final String COL_SYSTEM_ROOT = "system_root";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF4 = "def4";

    public static final String COL_DEF5 = "def5";
}