package com.redescooter.ses.web.ros.vo.salearea;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameRoleAreaEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/4 9:59
 * @Version V1.0
 **/
@Data
public class RoleAreaEnter extends GeneralEnter {

    @ApiModelProperty("角色id")
    private Long roleId;


    @ApiModelProperty("区域的id，多个用,隔开")
    private String saleCityIds;
}
