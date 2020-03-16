package com.redescooter.ses.web.ros.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.menu.MenuTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.RouterMeta;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.sys.MenuServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleMenuService;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.menu.EditMenuEnter;
import com.redescooter.ses.web.ros.vo.sys.menu.ModulePermissionsResult;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private MenuServiceMapper menuServiceMapper;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private OpeSysMenuService opeSysMenuService;

    @Override
    public GeneralResult save(SaveMenuEnter enter) {
        sysMenuService.save(this.buildMenu(null, enter));
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<MenuTreeResult> trees(IdEnter enter) {

        List<OpeSysMenu> list = sysMenuService.list();

        return this.buildTree(list, enter, Constant.MENU_TREE_ROOT_ID);
    }

    @Override
    public List<MenuTreeResult> list(IdEnter enter) {

        List<OpeSysMenu> list = sysMenuService.list();

        return this.buildList(list, enter);
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
    public Map<String, ModulePermissionsResult> userMenuTrees(GeneralEnter enter) {
        //获取所有父子权限列表
        List<ModulePermissionsResult> fatSon = menuServiceMapper.modulePermissions(enter);

        //查询该用户角色下的数据权限

        return null;
    }

    @Override
    public Map<String, ModulePermissionsResult> modulePermissions(IdEnter enter) {
        //获取所有父子权限列表
        List<ModulePermissionsResult> fatSon = menuServiceMapper.modulePermissions(enter);

        Map<String, ModulePermissionsResult> resultMap = new HashMap<>();

        if (enter.getId() != 0) {
            QueryWrapper<OpeSysRoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(OpeSysRoleMenu.COL_ROLE_ID, enter.getId());
            List<OpeSysRoleMenu> list = roleMenuService.list(queryWrapper);

            if (CollUtil.isNotEmpty(list)) {
                list.forEach(li -> fatSon.forEach(t -> {
                    if (CollUtil.isNotEmpty(list)) {
                        t.getChilds().stream().filter(ch -> li.getMenuId() == ch.getId()).forEach(ch -> ch.setChecked(Boolean.TRUE));
                    }
                }));
            }
        }

        if (CollUtil.isNotEmpty(fatSon)) {
            fatSon.forEach(rs -> {
                resultMap.put(String.valueOf(rs.getId()), rs);
            });
        }
        return resultMap;
    }

    @Override
    public MenuTreeResult details(IdEnter enter) {

        OpeSysMenu byId = sysMenuService.getById(enter.getId());

        MenuTreeResult result = new MenuTreeResult();
        BeanUtils.copyProperties(byId, result);

        return result;
    }

    @Override
    public GeneralResult delete(IdEnter enter) {

        QueryWrapper<OpeSysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeSysMenu.COL_P_ID, enter.getId());
        wrapper.eq(OpeSysMenu.COL_DR, Constant.DR_FALSE);
        List<OpeSysMenu> list = sysMenuService.list(wrapper);

        if (CollUtil.isNotEmpty(list)) {
            //删除儿子
            sysMenuService.remove(wrapper);
            //删除角色菜单关系
            QueryWrapper<OpeSysRoleMenu> delete = new QueryWrapper<>();
            delete.eq(OpeSysRoleMenu.COL_MENU_ID, enter.getId());
            roleMenuService.remove(delete);

        }

        QueryWrapper<OpeSysMenu> myself = new QueryWrapper<>();
        myself.eq(OpeSysMenu.COL_ID, enter.getId());
        myself.eq(OpeSysMenu.COL_DR, Constant.DR_FALSE);
        //删除自己
        sysMenuService.remove(myself);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 菜单编辑
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult edit(EditMenuEnter enter) {
        OpeSysMenu opeSysMenu = opeSysMenuService.getById(enter.getMenuId());
        if (opeSysMenu == null) {
            throw new SesWebRosException(ExceptionCodeEnums.MENU_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.MENU_IS_NOT_EXIST.getMessage());
        }
        // 根节点不可编辑
        if (opeSysMenu.getPId().equals(Constant.MENU_TREE_ROOT_ID) || enter.getPId().equals(Constant.MENU_TREE_ROOT_ID)) {
            throw new SesWebRosException(ExceptionCodeEnums.THE_ROOT_NODE_MENU_CANNOT_BE_EDIT.getCode(), ExceptionCodeEnums.THE_ROOT_NODE_MENU_CANNOT_BE_EDIT.getMessage());
        }
        opeSysMenu.setPId(enter.getPId());
        opeSysMenu.setPath(enter.getPath());
        opeSysMenu.setName(enter.getName());
        opeSysMenu.setPermission(enter.getPermission());
        opeSysMenu.setComponent(enter.getComponent());
        opeSysMenu.setType(enter.getType());
        opeSysMenu.setIcon(enter.getIcon());
        opeSysMenu.setSort(enter.getSort());
        opeSysMenu.setUpdatedBy(enter.getUserId());
        opeSysMenu.setUpdatedTime(new Date());
        opeSysMenuService.updateById(opeSysMenu);
        return new GeneralResult(enter.getRequestId());
    }

    private OpeSysMenu buildMenu(Long id, SaveMenuEnter enter) {
        OpeSysMenu menu = new OpeSysMenu();

        if (id == null) {
            menu.setId(idAppService.getId(SequenceName.OPE_SYS_MENU));
        } else {
            menu.setId(id);
        }
        if (enter.getPId() == null || enter.getPId() == 0) {
            menu.setPId(Constant.MENU_TREE_ROOT_ID);
        } else {
            menu.setPId(enter.getPId());
        }
        menu.setDr(Constant.DR_FALSE);
        menu.setName(enter.getName());
        menu.setCode(menu.getId() + ":::" + enter.getName());
        menu.setPermission(enter.getPermission());
        menu.setPath(enter.getPath());
        menu.setComponent(enter.getComponent());
        menu.setType(MenuTypeEnums.checkCode(enter.getType()));
        menu.setIcon(enter.getIcon());
        menu.setSort(enter.getSort());
        menu.setCreatedBy(enter.getUserId());
        menu.setCreatedTime(new Date());
        menu.setUpdatedBy(enter.getUserId());
        menu.setUpdatedTime(new Date());

        return menu;
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
            node.setCode(menu.getCode());
            node.setPermission(menu.getPermission());
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

    private List<MenuTreeResult> buildList(List<OpeSysMenu> menus, IdEnter enter) {
        List<MenuTreeResult> trees = new ArrayList<>();
        MenuTreeResult node;
        for (OpeSysMenu menu : menus) {
            node = new MenuTreeResult();
            BeanUtils.copyProperties(enter, node);
            node.setId(menu.getId());
            node.setPId(menu.getPId());
            node.setName(menu.getName());
            node.setPath(menu.getPath());
            node.setCode(menu.getCode());
            node.setType(menu.getType());
            node.setPermission(menu.getPermission());
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

        return trees;
    }
}
