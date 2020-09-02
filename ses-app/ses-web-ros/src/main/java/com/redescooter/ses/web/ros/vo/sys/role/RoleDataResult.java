package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameRoleDataResult
 * @Description
 * @Author Aleks
 * @Date2020/9/2 17:42
 * @Version V1.0
 **/
@Data
public class RoleDataResult {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

}
