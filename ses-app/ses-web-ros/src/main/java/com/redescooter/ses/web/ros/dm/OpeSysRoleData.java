package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色数据权限表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysRoleData")
@Data
@TableName(value = "ope_sys_role_data")
public class OpeSysRoleData implements Serializable {
    /**
     * 角色Id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value = "角色Id")
    private Long roleId;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 1:全部,2:本人,3:本部门,4:本部门及下属部门
     */
    @TableField(value = "data_type")
    @ApiModelProperty(value = "1:全部,2:本人,3:本部门,4:本部门及下属部门")
    private Integer dataType;

    private static final long serialVersionUID = 1L;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_DATA_TYPE = "data_type";
}