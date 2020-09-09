package com.redescooter.ses.web.ros.vo.roledata;

import com.redescooter.ses.api.common.vo.tree.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameRoleDataTree
 * @Description
 * @Author Aleks
 * @Date2020/9/9 13:48
 * @Version V1.0
 **/
@Data
public class RoleDataTree extends TreeNode {

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门编码")
    private String deptCode;

    @ApiModelProperty("部门等级")
    private Integer level;

    @ApiModelProperty(value = "是否选中")
    private boolean checked = false;


}
