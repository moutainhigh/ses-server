package com.redescooter.ses.web.ros.vo.sys.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SaveMenuEnter
 * @Author Jerry
 * @date 2020/03/11 17:39
 * @Description:
 */
@ApiModel(value = "创建菜单")
@Data
public class EditMenuEnter extends SaveMenuEnter {

    @ApiModelProperty(value = "菜单主键")
    private Long menuId;
}
