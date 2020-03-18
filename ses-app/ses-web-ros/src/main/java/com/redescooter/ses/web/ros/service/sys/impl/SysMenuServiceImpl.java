package com.redescooter.ses.web.ros.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.menu.MenuTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.RouterMeta;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.StringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.sys.MenuServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.menu.EditMenuEnter;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

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
    private MenuServiceMapper menuServiceMapper;

    @Autowired
    private OpeSysUserRoleService sysUserRoleService;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private OpeSysMenuService opeSysMenuService;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public GeneralResult save(SaveMenuEnter enter) {
        sysMenuService.save(this.buildMenuVo(null, enter));
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<MenuTreeResult> trees(IdEnter enter) {
        List<Long> roles = new ArrayList<>();
        roles.add(enter.getId());
        return this.buildMenuTree(sysMenuService.list(), roles, Constant.MENU_TREE_ROOT_ID);
    }

    @Override
    public List<MenuTreeResult> list(IdEnter enter) {
        List<Long> roles = new ArrayList<>();
        roles.add(enter.getId());
        return this.buildMenuList(sysMenuService.list(), roles);
    }

    @Override
    public List<VueRouter<MenuTreeResult>> vueRouters(GeneralEnter enter) {
        List<VueRouter<MenuTreeResult>> routes = new ArrayList<>();
        List<OpeSysMenu> list = sysMenuService.list();
        list.forEach(menu -> {
            VueRouter<MenuTreeResult> route = new VueRouter<>();
            route.setId(String.valueOf(menu.getId()));
            route.setPId(String.valueOf(menu.getPId()));
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getName());
            route.setCode(menu.getCode());
            route.setType(menu.getType());
            route.setLevel(menu.getLevel());
            route.setMeta(new RouterMeta(menu.getName(), menu.getIcon(), true));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

    @Override
    public List<MenuTreeResult> roleMenuAuth(GeneralEnter enter) {
        //获取用户角色岗位
        LambdaQueryWrapper<OpeSysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSysUserRole::getUserId, enter.getUserId());
        List<OpeSysUserRole> userRoles = sysUserRoleService.list(wrapper);

        if (CollUtil.isNotEmpty(userRoles)) {

        }

        return new ArrayList<>();
    }

    @Override
    public List<MenuTreeResult> roleOperationAuth(GeneralEnter enter) {
        return null;
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

    /**
     * 创建菜单Vo实体
     *
     * @param id
     * @param enter
     * @return
     */
    private OpeSysMenu buildMenuVo(Long id, SaveMenuEnter enter) {
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
        if (StringUtils.isBlank(enter.getCode())) {
            menu.setCode(menu.getId() + ":::" + enter.getName());
        } else {
            menu.setCode(enter.getCode());
        }
        menu.setPermission(enter.getPermission());
        menu.setPath(enter.getPath());
        menu.setComponent(enter.getComponent());
        menu.setType(MenuTypeEnums.checkCode(enter.getType()));
        menu.setIcon(enter.getIcon());
        menu.setLevel(enter.getLevel());
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
    private List<MenuTreeResult> buildMenuTree(List<OpeSysMenu> menus, List<Long> roleIds, long root) {
        List<MenuTreeResult> trees = new ArrayList<>();
        MenuTreeResult node;
        for (OpeSysMenu menu : menus) {
            node = new MenuTreeResult();
            node.setId(menu.getId());
            node.setPId(menu.getPId());
            node.setPermission(menu.getPermission());
            node.setName(menu.getName());
            node.setCode(menu.getCode());
            node.setPath(menu.getPath());
            node.setLevel(menu.getLevel());
            node.setType(menu.getType());
            node.setIcon(menu.getIcon());
            node.setSort(menu.getSort());
            trees.add(node);
        }
        if (CollUtil.isNotEmpty(roleIds)) {
            QueryWrapper<OpeSysRoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(OpeSysRoleMenu.COL_ROLE_ID, roleIds);
            List<OpeSysRoleMenu> list = roleMenuService.list(queryWrapper);
            //判断该角色所属权限
            if (CollUtil.isNotEmpty(list)) {
                list.forEach(li -> {
                    trees.stream().filter(t -> li.getMenuId() == t.getId()).forEach(t -> t.setChecked(Boolean.TRUE));
                });
            }
        }

        return TreeUtil.build(trees, root);
    }

    /**
     * 渲染平行结构菜单集合
     *
     * @param menus
     * @param roleIds
     * @return
     */
    private List<MenuTreeResult> buildMenuList(List<OpeSysMenu> menus, List<Long> roleIds) {
        List<MenuTreeResult> trees = new ArrayList<>();
        MenuTreeResult node;
        for (OpeSysMenu menu : menus) {
            node = new MenuTreeResult();
            node.setId(menu.getId());
            node.setPId(menu.getPId());
            node.setPermission(menu.getPermission());
            node.setName(menu.getName());
            node.setCode(menu.getCode());
            node.setPath(menu.getPath());
            node.setLevel(menu.getLevel());
            node.setType(menu.getType());
            node.setIcon(menu.getIcon());
            node.setSort(menu.getSort());
            trees.add(node);
        }
        if (CollUtil.isNotEmpty(roleIds)) {
            QueryWrapper<OpeSysRoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(OpeSysRoleMenu.COL_ROLE_ID, roleIds);
            List<OpeSysRoleMenu> list = roleMenuService.list(queryWrapper);
            //判断该角色所属权限
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
