package com.redescooter.ses.api.common.vo.tree;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * description: TreeNode 菜单树
 * author: jerry.li
 * create: 2019-05-30 16:58
 */
@ApiModel(value = "基础树形节点")
@Data //生成getter,setter等函数
public class TreeNode extends GeneralResult {

    @ApiModelProperty(value = "当前节点id")
    protected long id;

    @ApiModelProperty(value = "父节点id")
    protected long pId;

    @ApiModelProperty(value = "子节点列表")
    protected List children = new ArrayList();

    public void add(TreeNode node) {
        children.add(node);
    }
}
