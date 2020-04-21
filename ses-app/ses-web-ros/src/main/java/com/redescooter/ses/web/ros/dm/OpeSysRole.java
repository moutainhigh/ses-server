package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeSysRole")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sys_role")
public class OpeSysRole {
    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键Id")
    private Long id;

    /**
     * 逻辑删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户id")
    private Long tenantId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @ApiModelProperty(value="角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    @ApiModelProperty(value="角色编码")
    private String roleCode;

    /**
     * 角色描述
     */
    @TableField(value = "role_desc")
    @ApiModelProperty(value="角色描述")
    private String roleDesc;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    @TableField(value = "create_time")
    @ApiModelProperty(value="")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    @TableField(value = "update_time")
    @ApiModelProperty(value="")
    private Date updateTime;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_ROLE_NAME = "role_name";

    public static final String COL_ROLE_CODE = "role_code";

    public static final String COL_ROLE_DESC = "role_desc";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATE_TIME = "update_time";
}