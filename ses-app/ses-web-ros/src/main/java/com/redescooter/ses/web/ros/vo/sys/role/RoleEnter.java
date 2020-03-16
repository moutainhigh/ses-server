package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
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
public class RoleEnter extends PermissionEnter {

    @ApiModelProperty(value = "部门ID")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 为空")
    private Long deptId;

    @ApiModelProperty(value = "岗位名称")
    @NotNull(code = ValidationExceptionCode.ROLE_NAME_IS_EMPTY, message = "职位名称为空")
    private String roleName;

    @ApiModelProperty(value = "岗位编码")
    private String roleCode;

    @ApiModelProperty(value = "岗位描述")
    private String description;

}
