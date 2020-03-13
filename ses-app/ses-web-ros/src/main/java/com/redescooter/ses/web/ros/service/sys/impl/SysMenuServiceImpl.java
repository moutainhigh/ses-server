package com.redescooter.ses.web.ros.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.RouterMeta;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleMenuService;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysMenuServiceImpl
 * @Author Jerry
 * @date 2020/03/11 18:01
 * @Description:
 */
@Slf4j
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private OpeSysMenuService sysMenuService;

    @Autowired
    private OpeSysRoleMenuService roleMenuService;

    @Autowired
    private IdAppService idAppService;

    @Override
    public GeneralResult save(SaveMenuEnter enter) {

        OpeSysMenu menu = new OpeSysMenu();
        BeanUtils.copyProperties(enter, menu);
        if (menu.getPId() == null || menu.getPId() == 0) {
            menu.setPId(Constant.MENU_TREE_ROOT_ID);
        }
        menu.setId(idAppService.getId(SequenceName.OPE_SYS_MENU));
        menu.setDr(Constant.DR_FALSE);
        menu.setCreatedBy(enter.getUserId());
        menu.setCreatedTime(new Date());
        menu.setUpdatedBy(enter.getUserId());
        menu.setUpdatedTime(new Date());

        sysMenuService.save(menu);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<MenuTreeResult> trees(IdEnter enter) {

        List<OpeSysMenu> list = sysMenuService.list();

        return this.buildTree(list, enter, Constant.MENU_TREE_ROOT_ID);
    }

    @Override
    public List<VueRouter<MenuTreeResult>> userRouters(GeneralEnter enter) {

        List<VueRouter<MenuTreeResult>> routes = new ArrayList<>();
        List<OpeSysMenu> list = sysMenuService.list();

        list.forEach(menu -> {
            VueRouter<MenuTreeResult> route = new VueRouter<>();
            route.setId(String.valueOf(menu.getId()));
            route.setParentId(String.valueOf(menu.getPId()));
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getName());
            route.setMeta(new RouterMeta(menu.getName(), menu.getIcon(), true));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

    @Override
    public List<MenuTreeResult> userMenuTrees(GeneralEnter enter) {
        return null;
    }

    /**
     * 通过sysMenu创建树形节点
     *
     * @param menus
     * @param root
     * @return
     */
    private List<MenuTreeResult> buildTree(List<OpeSysMenu> menus, IdEnter enter, long root) {
        List<MenuTreeResult> trees = new ArrayList<>();
        MenuTreeResult node;
        for (OpeSysMenu menu : menus) {
            node = new MenuTreeResult();
            BeanUtils.copyProperties(enter, node);
            node.setId(menu.getId());
            node.setPId(menu.getPId());
            node.setName(menu.getName());
            node.setPath(menu.getPath());
            node.setCode(menu.getPermission());
            node.setIcon(menu.getIcon());
            node.setSort(menu.getSort());
            node.setChecked(Boolean.FALSE);
            node.setDisabled(Boolean.FALSE);
            node.setSpread(Boolean.FALSE);
            trees.add(node);
        }
        if (enter.getId() != 0) {
            QueryWrapper<OpeSysRoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(OpeSysRoleMenu.COL_ROLE_ID, enter.getId());
            List<OpeSysRoleMenu> list = roleMenuService.list(queryWrapper);

            if (CollUtil.isNotEmpty(list)) {
                list.forEach(li -> {
                    trees.forEach(t -> {
                        if (li.getMenuId() == t.getId()) {
                            t.setChecked(Boolean.TRUE);
                        }
                    });
                });
            }
        }

        return TreeUtil.build(trees, root);
    }
}
