package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysRoleDept")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sys_role_dept")
public class OpeSysRoleDept implements Serializable {
    /**
     * 角色ID
     */
    @TableId(value = "role_id", type = IdType.INPUT)
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    /**
     * 部门ID
     */
    @TableId(value = "dept_id", type = IdType.INPUT)
    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_DEPT_ID = "dept_id";

    public static OpeSysRoleDeptBuilder builder() {
        return new OpeSysRoleDeptBuilder();
    }
}