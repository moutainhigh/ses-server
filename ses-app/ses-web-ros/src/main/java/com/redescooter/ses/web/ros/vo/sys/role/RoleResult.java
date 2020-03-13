package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName PermissionEnter
 * @Author Jerry
 * @date 2020/03/12 14:01
 * @Description:
 */
@ApiModel(value = "创建角色")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RoleResult extends GeneralResult {

    @ApiModelProperty(value = "岗位ID")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

}
