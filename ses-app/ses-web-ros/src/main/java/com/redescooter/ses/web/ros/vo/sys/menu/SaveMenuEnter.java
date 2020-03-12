package com.redescooter.ses.web.ros.vo.sys.menu;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName SaveMenuEnter
 * @Author Jerry
 * @date 2020/03/11 17:39
 * @Description:
 */
@ApiModel(value = "创建菜单")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveMenuEnter extends GeneralEnter {

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单编码")
    private String code;

    @ApiModelProperty(value = "权限码")
    private String permission;

    @ApiModelProperty(value = "路由")
    private String path;

    @ApiModelProperty(value = "对应路由组件component")
    private String component;

    @ApiModelProperty(value = "父菜单ID")
    private Long pId;

    @ApiModelProperty(value = "图表")
    private String icon;

    @ApiModelProperty(value = "菜单权重")
    private Integer sort;

    @ApiModelProperty(value = "菜单类型,-1:目录 0:菜单 1:按钮")
    private String type;

}
