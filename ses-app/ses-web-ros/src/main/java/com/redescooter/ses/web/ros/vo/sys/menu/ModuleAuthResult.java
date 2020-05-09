package com.redescooter.ses.web.ros.vo.sys.menu;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassName ModuleAuthResult
 * @Author Jerry
 * @date 2020/03/13 14:49
 * @Description:
 */
@ApiModel(value = "模块权限")
@Data
public class ModuleAuthResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "父级")
    private Long pId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "权重")
    private Integer sort;

    @ApiModelProperty(value = "子级")
    private List<MenuTreeResult> childs;
}
