package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameRoleDeleEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/1 15:57
 * @Version V1.0
 **/
@Data
public class RoleOpEnter extends GeneralEnter {

    @ApiModelProperty("角色id")
    private Long id;

}
