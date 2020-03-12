package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Set;

/**
 * @ClassName PermissionEnter
 * @Author Jerry
 * @date 2020/03/12 14:01
 * @Description:
 */
@ApiModel(value = "角色权限")
@Data
public class PermissionEnter extends GeneralEnter {

    private Long roleId;

    private Set<Long> salesPermissionIds;

    private Set<Long> meunPermissionIds;


}
