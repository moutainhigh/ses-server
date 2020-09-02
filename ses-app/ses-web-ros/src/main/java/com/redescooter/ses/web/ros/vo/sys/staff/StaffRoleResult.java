package com.redescooter.ses.web.ros.vo.sys.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameStaffRoleResult
 * @Description
 * @Author Aleks
 * @Date2020/9/2 10:59
 * @Version V1.0
 **/
@Data
public class StaffRoleResult {

    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

}
