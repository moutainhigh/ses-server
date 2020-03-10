package com.redescooter.ses.web.ros.vo.tree;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * description: TreeNode 菜单树
 * author: jerry.li
 * create: 2019-05-30 16:58
 */

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class TreeNode extends GeneralEnter {
    protected long id;
    protected long pId;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }

}
