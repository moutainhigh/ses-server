package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统角色表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysRole")
@Data
@TableName(value = "ope_sys_role")
public class OpeSysRole implements Serializable {
    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    private static final long serialVersionUID = 1L;

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

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATE_TIME = "update_time";
}