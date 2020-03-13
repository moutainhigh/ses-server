package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @ClassName PermissionEnter
 * @Author Jerry
 * @date 2020/03/12 14:01
 * @Description:
 */
@ApiModel(value = "岗位权限")
@Data
public class PermissionEnter extends GeneralEnter {

    @ApiModelProperty(value = "岗位ID")
    private Long roleId;

    @ApiModelProperty(value = "已选择销售区域ID")
    private Set<Long> salesPermissionIds;

    @ApiModelProperty(value = "已选择菜单ID")
    private Set<Long> meunPermissionIds;


}
