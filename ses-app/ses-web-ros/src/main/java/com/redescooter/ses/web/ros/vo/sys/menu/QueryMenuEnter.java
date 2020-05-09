package com.redescooter.ses.web.ros.vo.sys.menu;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName QueryMenuEnter
 * @Author Jerry
 * @date 2020/03/20 12:06
 * @Description:
 */
@ApiModel(value = "菜单查询入参")
@Data
public class QueryMenuEnter extends GeneralEnter {

    @ApiModelProperty(value = "父级名称")
    private String fatherName;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单编码")
    private String code;

    @ApiModelProperty(value = "菜单类型")
    private String type;

    @ApiModelProperty(value = "菜单等级")
    private Integer level;
}
