package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassNameroleMenuEditEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/2 15:19
 * @Version V1.0
 **/
@Data
public class RoleMenuEditEnter extends GeneralEnter {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("菜单id")
    private List<Long> menuIds;

}
