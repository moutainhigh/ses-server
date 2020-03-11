package com.redescooter.ses.web.ros.vo.tree;

import com.redescooter.ses.api.common.vo.tree.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * description: MenuTreeResult
 * author: jerry.li
 * create: 2019-05-30 14:34
 */

@ApiModel(value = "菜单树")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class MenuTreeResult extends TreeNode {

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单编码")
    private String code;

    @ApiModelProperty(value = "权限码")
    private String permission;

    @ApiModelProperty(value = "路由")
    private String path;

    @ApiModelProperty(value = "菜单类型")
    private String type;

    @ApiModelProperty(value = "图表")
    private String icon;

    @ApiModelProperty(value = "菜单权重")
    private int sort;

    @ApiModelProperty(value = "是否选中")
    private boolean checked;

    @ApiModelProperty(value = "是否禁用")
    private boolean disabled;

    @ApiModelProperty(value = "是否展开")
    private boolean spread = false;

}
