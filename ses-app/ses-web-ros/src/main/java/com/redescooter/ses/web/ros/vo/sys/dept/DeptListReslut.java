package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.vo.tree.TreeNode;
import com.redescooter.ses.web.ros.vo.sys.role.RoleResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
public class DeptListReslut extends TreeNode {

    @ApiModelProperty(value = "负责人")
    private Integer principal;

    @ApiModelProperty(value = "级别0公司，1部门")
    private Integer level;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "岗位列表")
    private List<RoleResult> roles;

    @ApiModelProperty(value = "是否选中")
    private boolean checked;

    @ApiModelProperty(value = "是否展开")
    private boolean spread = false;
}
