package com.redescooter.ses.web.ros.vo.tree;

import com.redescooter.ses.api.common.vo.tree.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

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
public class DeptTreeReslt extends TreeNode{

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

    @ApiModelProperty(value = "是否选中")
    private boolean checked;

    @ApiModelProperty(value = "是否禁用")
    private boolean disabled;

    @ApiModelProperty(value = "是否展开")
    private boolean spread = false;

    @ApiModelProperty(value = "员工头像,逗号分隔")
    private String employeePictures;

    @ApiModelProperty(value = "员工数量")
    private int employeeCount;
}
