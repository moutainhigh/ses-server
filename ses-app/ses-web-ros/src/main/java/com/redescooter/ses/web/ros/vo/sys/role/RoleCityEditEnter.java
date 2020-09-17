package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassNameRoleCityEditEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/2 16:23
 * @Version V1.0
 **/
@Data
public class RoleCityEditEnter extends GeneralEnter {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("区域id")
    private List<Long> cityIds;

}
