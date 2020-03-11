package com.redescooter.ses.web.ros.vo.tree;

import com.redescooter.ses.api.common.vo.tree.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName DeptTreeReslt
 * @Author Jerry
 * @date 2020/03/11 20:18
 * @Description:
 */
@ApiModel(value = "部门树")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeptTreeReslt extends TreeNode {

    @ApiModelProperty(value = "负责人")
    private Integer principal;

    @ApiModelProperty(value = "级别0公司，1部门")
    private Integer level;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
