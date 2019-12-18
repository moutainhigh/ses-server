package com.redescooter.ses.web.ros.utils;//package com.redescooter.ses.app.crm.web.utils;
//
//import com.redescooter.ses.app.crm.web.vo.SysMenuTreeResult;
//import com.redescooter.ses.app.crm.web.vo.base.SysMenu;
//import com.redescooter.ses.app.crm.web.vo.bo.TreeNode;
//import com.redescooter.ses.api.common.vo.GeneralEnter;
//import lombok.experimental.UtilityClass;
//import org.springframework.beans.BeanUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author jerry
// * @date 2019年05月30日23:34:11
// */
//@UtilityClass
//public class TreeUtil {
//
//    /**
//     * 两层循环实现建树
//     *
//     * @param treeNodes 传入的树节点列表
//     * @return
//     */
//    public <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {
//
//        List<T> trees = new ArrayList<>();
//
//        for (T treeNode : treeNodes) {
//
//            if (root.equals(treeNode.getPId())) {
//                trees.add(treeNode);
//            }
//
//            for (T it : treeNodes) {
//                if (it.getPId() == treeNode.getId()) {
//                    if (treeNode.getChildren() == null) {
//                        treeNode.setChildren(new ArrayList<>());
//                    }
//                    treeNode.add(it);
//                }
//            }
//        }
//        return trees;
//    }
//
//    /**
//     * 使用递归方法建树
//     *
//     * @param treeNodes
//     * @return
//     */
//    public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
//        List<T> trees = new ArrayList<T>();
//        for (T treeNode : treeNodes) {
//            if (root.equals(treeNode.getPId())) {
//                trees.add(findChildren(treeNode, treeNodes));
//            }
//        }
//        return trees;
//    }
//
//    /**
//     * 递归查找子节点
//     *
//     * @param treeNodes
//     * @return
//     */
//    public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
//        for (T it : treeNodes) {
//            if (treeNode.getId() == it.getPId()) {
//                if (treeNode.getChildren() == null) {
//                    treeNode.setChildren(new ArrayList<>());
//                }
//                treeNode.add(findChildren(it, treeNodes));
//            }
//        }
//        return treeNode;
//    }
//
//    /**
//     * 通过sysMenu创建树形节点
//     *
//     * @param menus
//     * @param root
//     * @return
//     */
//    public List<SysMenuTreeResult> buildTree(List<SysMenu> menus, GeneralEnter enter, int root) {
//        List<SysMenuTreeResult> trees = new ArrayList<>();
//        SysMenuTreeResult node;
//        for (SysMenu menu : menus) {
//            node = new SysMenuTreeResult();
//            BeanUtils.copyProperties(enter,node);
//            node.setId(menu.getId());
//            node.setPId(menu.getPId());
//            node.setName(menu.getName());
//            node.setPath(menu.getPath());
//            node.setCode(menu.getPermission());
//            node.setLabel(menu.getName());
//            node.setComponent(menu.getComponent());
//            node.setIcon(menu.getIcon());
//            node.setKeepAlive(menu.getKeepAlive());
//            trees.add(node);
//        }
//        return TreeUtil.build(trees, root);
//    }
//}
