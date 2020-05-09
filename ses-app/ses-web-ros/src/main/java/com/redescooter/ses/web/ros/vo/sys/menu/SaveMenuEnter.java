package com.redescooter.ses.web.ros.vo.sys.menu;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
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
    @NotNull(code = ValidationExceptionCode.MENU_NAME_IS_EMPTY, message = "菜单名字为空")
    private String name;

    @ApiModelProperty(value = "菜单编码")
    private String code;

    @ApiModelProperty(value = "权限码")
    private String permission;

    @ApiModelProperty(value = "路由")
    @NotNull(code = ValidationExceptionCode.MENU_PATH_IS_EMPTY, message = "菜单路径为空")
    private String path;

    @ApiModelProperty(value = "对应路由组件component")
    private String component;

    @ApiModelProperty(value = "父菜单ID")
    @NotNull(code = ValidationExceptionCode.PID_IS_EMPTY, message = "父级Id为空")
    private Long pid;

    @ApiModelProperty(value = "图表")
    private String icon;

    @ApiModelProperty(value = "菜单权重")
    private Integer sort;

    @ApiModelProperty(value = "菜单类型 0:菜单 1:按钮")
    @NotNull(code = ValidationExceptionCode.MENU_TYPE_IS_EMPTY, message = "菜单类型为空")
    private String type;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "扩展字段,父级name")
    private String def1;

    @ApiModelProperty(value = "扩展字段，父级code")
    private String def2;

    @ApiModelProperty(value = "扩展字段，父级type")
    private String def3;

}
