package com.redescooter.ses.web.ros.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.menu.MenuTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.RouterMeta;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.sys.MenuServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.enums.distributor.DelStatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.sys.MenuService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.menu.EditMenuEnter;
import com.redescooter.ses.web.ros.vo.sys.menu.QueryMenuEnter;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuDatasEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuDatasListResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MenuServiceImpl
 * @Author Jerry
 * @date 2020/03/11 18:01
 * @Description:
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private OpeSysUserService sysUserService;

    @Autowired
    private OpeSysMenuService sysMenuService;

    @Autowired
    private OpeSysRoleMenuService roleMenuService;

    @Autowired
    private OpeSysUserRoleService userRoleService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private MenuServiceMapper menuServiceMapper;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult save(SaveMenuEnter enter) {
        sysMenuService.save(this.buildMenuVo(null, enter));
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ?????????????????????????????????????????????????????????????????????user_id??????
     *
     * @param enter
     * @return
     */
    @Override
    public List<MenuTreeResult> trees(GeneralEnter enter) {
//        return this.buildMenuTree(sysMenuService.list(), this.getRoleIds(new IdEnter(enter.getUserId())), Constant.MENU_TREE_ROOT_ID,Boolean.FALSE);

        // ????????????id??????????????????
        LambdaQueryWrapper<OpeSysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(OpeSysUser::getId, enter.getUserId());
        userWrapper.eq(OpeSysUser::getDef1, SysUserSourceEnum.SYSTEM.getValue());
        userWrapper.last("limit 1");
        OpeSysUser admin = sysUserService.getOne(userWrapper);

        // ?????????????????????
        if (admin.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            // ??????????????????
            LambdaQueryWrapper<OpeSysMenu> menuWrapper = new LambdaQueryWrapper<>();
            menuWrapper.orderByAsc(OpeSysMenu::getSort);
            List<OpeSysMenu> menuList = sysMenuService.list(menuWrapper);
            // ??????????????????????????????
            List<MenuTreeResult> result = this.buildMenuParallel(menuList, null, Boolean.TRUE);
            List<MenuTreeResult> list = TreeUtil.build(result, Constant.MENU_TREE_ROOT_ID);
            return list;
        } else {
            // ????????????????????????,?????????????????????????????????id??????
            List<Long> roleIds = this.getRoleIds(new IdEnter(enter.getUserId()));
            if (CollUtil.isNotEmpty(roleIds)) {
                // ????????????id?????????????????????????????????id??????
                List<Long> menuIds = this.getMenuIdsByRoleIds(roleIds);
                if (CollUtil.isNotEmpty(menuIds)) {
                    LambdaQueryWrapper<OpeSysMenu> menuWrapper = new LambdaQueryWrapper<>();
                    menuWrapper.in(OpeSysMenu::getId, menuIds);
                    menuWrapper.orderByAsc(OpeSysMenu::getSort);
                    List<OpeSysMenu> menuList = sysMenuService.list(menuWrapper);
                    // ??????????????????????????????
                    List<MenuTreeResult> result = this.buildMenuParallel(menuList, roleIds, Boolean.FALSE);
                    List<MenuTreeResult> list = TreeUtil.build(result, Constant.MENU_TREE_ROOT_ID);
                    return list;
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * ????????????????????????????????????????????????????????????????????????user_id??????
     *
     * @param enter
     * @return
     */
    @Override
    public List<MenuTreeResult> parallel(QueryMenuEnter enter) {
        LambdaQueryWrapper<OpeSysMenu> query = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(enter.getFatherName())) {
            query.like(OpeSysMenu::getDef1, enter.getFatherName());
        }
        if (StringUtils.isNotBlank(enter.getName())) {
            query.like(OpeSysMenu::getName, enter.getName());
        }
        if (StringUtils.isNotBlank(enter.getCode())) {
            query.like(OpeSysMenu::getCode, enter.getCode());
        }
        if (StringUtils.isNotBlank(enter.getType())) {
            query.like(OpeSysMenu::getType, enter.getType());
        }
        if (StringManaConstant.entityIsNotNull(enter.getLevel())) {
            query.like(OpeSysMenu::getLevel, String.valueOf(enter.getLevel()));
        }
        return this.buildMenuParallel(sysMenuService.list(query), this.getRoleIds(new IdEnter(enter.getUserId())),Boolean.FALSE);
    }

    /**
     * ????????????VUE??????
     *
     * @param enter
     * @return
     */
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

    /**
     * ??????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<MenuTreeResult> roleMenuAuthTree(GeneralEnter enter) {

        OpeSysUser admin = sysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>().eq(OpeSysUser::getId, enter.getUserId()).eq(OpeSysUser::getDef1, SysUserSourceEnum.SYSTEM.getValue()).last("limit 1"));
        if (admin.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            return this.buildMenuTree(sysMenuService.list(), null,Constant.MENU_TREE_ROOT_ID,Boolean.TRUE);
        }else{
            //????????????????????????
            List<Long> roleIds = this.getRoleIds(new IdEnter(enter.getUserId()));
            if (CollUtil.isNotEmpty(roleIds)) {
                List<Long> menuIds = this.getMenuIdsByRoleIds(roleIds);
                if (CollUtil.isNotEmpty(menuIds)) {
                    return this.buildMenuTree(sysMenuService.list(new LambdaQueryWrapper<OpeSysMenu>().in(OpeSysMenu::getId, menuIds).eq(OpeSysMenu::getType, MenuTypeEnums.MENUS.getValue())), roleIds, Constant.MENU_TREE_ROOT_ID,Boolean.FALSE);
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<MenuTreeResult> roleMenuAuthTreeByRoleId(IdEnter enter) {
        //????????????ID
        List<Long> roleIds = new ArrayList<Long>() {{
            add(enter.getId());
        }};
        if (CollUtil.isNotEmpty(roleIds)) {
            boolean flag = false;
            // ???????????????????????????????????????????????????
            OpeSysUser user = sysUserService.getById(enter.getUserId());
            if (StringManaConstant.entityIsNull(user)){
                throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            if (user.getLoginName().equals(Constant.ADMIN_USER_NAME)){
                flag = true;
            }
            return this.buildMenuTree(flag?sysMenuService.list():menuServiceMapper.menusByUserId(user.getId()), roleIds, Constant.MENU_TREE_ROOT_ID,flag);
        }
        return new ArrayList<>();
    }

    @Override
    public List<MenuTreeResult> roleMenuAuthParallel(GeneralEnter enter) {

        OpeSysUser admin = sysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>().eq(OpeSysUser::getId, enter.getUserId()).eq(OpeSysUser::getDef1,SysUserSourceEnum.SYSTEM.getValue()).last("limit 1"));

        if (admin.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            return this.buildMenuParallel(sysMenuService.list(new LambdaQueryWrapper<OpeSysMenu>().orderByAsc(OpeSysMenu::getSort)), null,Boolean.TRUE);
        } else {
            List<Long> roleIds = this.getRoleIds(new IdEnter(enter.getUserId()));
            if (CollUtil.isNotEmpty(roleIds)) {
                List<Long> menuIds = this.getMenuIdsByRoleIds(roleIds);
                if (CollUtil.isNotEmpty(menuIds)) {
                    List<MenuTreeResult> results = this.buildMenuParallel(sysMenuService.list(new LambdaQueryWrapper<OpeSysMenu>().in(OpeSysMenu::getId, menuIds).orderByAsc(OpeSysMenu::getSort)), roleIds,Boolean.FALSE);
                    return results;
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<MenuTreeResult> roleMenuAuthParallelByRoleId(IdEnter enter) {
        //????????????ID
        List<Long> roleIds = new ArrayList<Long>() {{
            add(enter.getId());
        }};
        if (CollUtil.isNotEmpty(roleIds)) {
            return this.buildMenuParallel(sysMenuService.list(), roleIds,Boolean.FALSE);
        }
        return new ArrayList<>();
    }

    @Override
    public MenuTreeResult details(IdEnter enter) {
        OpeSysMenu byId = sysMenuService.getById(enter.getId());
        MenuTreeResult result = new MenuTreeResult();
        BeanUtils.copyProperties(byId, result);
        return result;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult delete(IdEnter enter) {
//        QueryWrapper<OpeSysMenu> wrapper = new QueryWrapper<>();
//        wrapper.eq(OpeSysMenu.COL_P_ID, enter.getId());
//        wrapper.eq(OpeSysMenu.COL_DR, Constant.DR_FALSE);
//        List<OpeSysMenu> list = sysMenuService.list(wrapper);
//        if (CollUtil.isNotEmpty(list)) {
//            //????????????
//            sysMenuService.remove(wrapper);
//            //????????????????????????
//            QueryWrapper<OpeSysRoleMenu> delete = new QueryWrapper<>();
//            delete.eq(OpeSysRoleMenu.COL_MENU_ID, enter.getId());
//            roleMenuService.remove(delete);
//        }

        // ???????????????????????????????????????id???????????????????????????
        List<Long> childs = findChilds(enter.getId());
        if(CollectionUtils.isNotEmpty(childs)){
            sysMenuService.removeByIds(childs);
        }
        childs.add(enter.getId());
        QueryWrapper<OpeSysRoleMenu> delete = new QueryWrapper<>();
        delete.in(OpeSysRoleMenu.COL_MENU_ID,childs);
        roleMenuService.remove(delete);

        QueryWrapper<OpeSysMenu> myself = new QueryWrapper<>();
        myself.eq(OpeSysMenu.COL_ID, enter.getId());
        myself.eq(OpeSysMenu.COL_DR, Constant.DR_FALSE);
        //????????????
        sysMenuService.remove(myself);

        return new GeneralResult(enter.getRequestId());
    }


    public List<Long> findChilds(Long id){
        List<Long> childs = menuServiceMapper.findChilds(id);
        return childs;
    }



    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult edit(EditMenuEnter enter) {
        OpeSysMenu menuUpdate = sysMenuService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(menuUpdate)) {
            throw new SesWebRosException(ExceptionCodeEnums.MENU_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.MENU_IS_NOT_EXIST.getMessage());
        }
        // ?????????????????????
        if (menuUpdate.getPId().equals(Constant.MENU_TREE_ROOT_ID)) {
            throw new SesWebRosException(ExceptionCodeEnums.THE_ROOT_NODE_MENU_CANNOT_BE_EDIT.getCode(), ExceptionCodeEnums.THE_ROOT_NODE_MENU_CANNOT_BE_EDIT.getMessage());
        }
//        sysMenuService.updateById(this.buildMenuVo(menuUpdate.getId(), enter));
        BeanUtils.copyProperties(enter,menuUpdate);
        menuUpdate.setPId(enter.getPid());
        sysMenuService.updateById(menuUpdate);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<MenuTreeResult> findMenuByRoleId(GeneralEnter enter) {
        return this.roleMenuAuthParallel(enter);
    }

    /**
     * ????????????Vo??????
     *
     * @param id
     * @param enter
     * @return
     */
    private OpeSysMenu buildMenuVo(Long id, SaveMenuEnter enter) {
        OpeSysMenu menu = new OpeSysMenu();
        if (StringManaConstant.entityIsNull(id) || 0 == id) {
            menu.setId(idAppService.getId(SequenceName.OPE_SYS_MENU));
            menu.setCreatedBy(enter.getUserId());
            menu.setCreatedTime(new Date());
            menu.setDr(Constant.DR_FALSE);
        } else {
            menu.setId(id);
        }
        if (StringManaConstant.entityIsNull(enter.getPid()) || 0 == enter.getPid()) {
            menu.setPId(Constant.MENU_TREE_ROOT_ID);
        } else {
            menu.setPId(enter.getPid());
        }
        menu.setName(enter.getName());
        if (SesStringUtils.isBlank(enter.getCode())) {
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
        menu.setType(enter.getLevel() == null?"":enter.getLevel().toString());
        menu.setSort(enter.getSort());
        menu.setRemark(enter.getRemark());
        menu.setDef1(enter.getDef1());
        menu.setDef2(enter.getDef2());
        menu.setDef3(enter.getDef3());
        menu.setUpdatedBy(enter.getUserId());
        menu.setUpdatedTime(new Date());
        menu.setIfToLink(enter.getIfToLink());
        menu.setMenuStatus(enter.getMenuStatus());
        return menu;
    }

    /**
     * ??????sysMenu??????????????????
     *
     * @param menus
     * @param root
     * @return
     */
    private List<MenuTreeResult> buildMenuTree(List<OpeSysMenu> menus, List<Long> roleIds, long root, Boolean adminBoolean) {
        List<MenuTreeResult> trees = new ArrayList<>();
        if (CollUtil.isNotEmpty(menus)) {
            for (OpeSysMenu menu : menus) {
                trees.add(buildMenuTreeResult(menu));
            }
//            if (adminBoolean) {
//                trees.forEach(t -> t.setChecked(Boolean.TRUE));
//            } else {
                if (CollUtil.isNotEmpty(roleIds)) {
                    List<Long> list = this.getMenuIdsByRoleIds(roleIds);
                    //???????????????????????????
                    if (CollUtil.isNotEmpty(list)) {
                        list.forEach(li -> trees.stream().filter(t -> li.longValue() == t.getId()).forEach(t -> t.setChecked(Boolean.TRUE)));
                    }
                }
//            }
        }
        return TreeUtil.build(trees, root);
    }

    /**
     * ??????????????????????????????
     *
     * @param menus
     * @param roleIds
     * @return
     */
    private List<MenuTreeResult> buildMenuParallel(List<OpeSysMenu> menus, List<Long> roleIds, Boolean adminBoolean) {
        List<MenuTreeResult> trees = new ArrayList<>();
        if (CollUtil.isNotEmpty(menus)) {
            for (OpeSysMenu menu : menus) {
                trees.add(buildMenuTreeResult(menu));
            }
            if (adminBoolean) {
                trees.forEach(t -> t.setChecked(Boolean.TRUE));
            } else {
                if (CollUtil.isNotEmpty(roleIds)) {
                    List<Long> list = this.getMenuIdsByRoleIds(roleIds);
                    //???????????????????????????
                    if (CollUtil.isNotEmpty(list)) {
                        list.forEach(li -> trees.stream().filter(t -> li.longValue() == t.getId()).forEach(t -> t.setChecked(Boolean.TRUE)));
                    }
                }
            }
        }
        for (MenuTreeResult tree : trees) {
            if(1 == tree.getLevel()){
                tree.setChecked(false);
            }
        }
        return trees;
    }

    /**
     * ???????????????????????????
     *
     * @param menu
     * @return
     */
    private MenuTreeResult buildMenuTreeResult(OpeSysMenu menu) {
        MenuTreeResult node = new MenuTreeResult();
        node.setId(menu.getId());
        node.setPId(menu.getPId());
        node.setPermission(menu.getPermission());
        node.setName(menu.getName());
        node.setCode(menu.getCode());
        node.setPath(menu.getPath());
        node.setLevel(menu.getLevel());
        node.setType(menu.getType());
        node.setIcon(Strings.isNullOrEmpty(menu.getIcon())?"":menu.getIcon());
        node.setSort(menu.getSort());
        node.setRemark(menu.getRemark());
        node.setDef1(menu.getDef1());
        node.setDef2(menu.getDef2());
        node.setDef3(menu.getDef3());
        node.setCreatedTime(menu.getCreatedTime());
        node.setMenuStatus(menu.getMenuStatus());
        node.setIfToLink(menu.getIfToLink());
        node.setComponent(menu.getComponent());
        return node;
    }

    /**
     * ??????????????????????????????ID??????
     *
     * @param enter
     * @return
     */
    private List<Long> getRoleIds(IdEnter enter) {
        List<Long> result = new ArrayList<>();
        if (StringManaConstant.entityIsNotNull(enter.getId())) {
            if (0 != enter.getId()) {
                //????????????????????????
                result = menuServiceMapper.getRoleIds(enter.getId());

//                List<OpeSysUserRole> userRoles = userRoleService.list(new LambdaQueryWrapper<OpeSysUserRole>().eq(OpeSysUserRole::getUserId, enter.getId()));
//                if (CollUtil.isNotEmpty(userRoles)) {
//                    userRoles.forEach(ur -> result.add(ur.getRoleId()));
//                }
            }
        }
        return result;
    }

    /**
     * ????????????????????????????????????
     *
     * @param ids
     * @return
     */
    private List<Long> getMenuIdsByRoleIds(List<Long> ids) {
        List<Long> result = new ArrayList<>();
        if (CollUtil.isNotEmpty(ids)) {
            if (CollUtil.isNotEmpty(ids)) {
                List<OpeSysRoleMenu> roleMenus = roleMenuService.list(new LambdaQueryWrapper<OpeSysRoleMenu>().in(OpeSysRoleMenu::getRoleId, ids));
                if (CollUtil.isNotEmpty(roleMenus)) {
                    roleMenus.forEach(rm -> result.add(rm.getMenuId()));
                }
            }
        }
        return result;
    }

    /**
     * ??????????????????
     */
    @Override
    public List<MenuDatasListResult> menuDatas(MenuDatasEnter enter) {
        if (StringManaConstant.entityIsNull(enter) || StringManaConstant.entityIsNull(enter.getId())) {
            throw new SesWebRosException(ExceptionCodeEnums.ID_IS_NOT_NULL.getCode(), ExceptionCodeEnums.ID_IS_NOT_NULL.getMessage());
        }
        /*List<MenuDatasListResult> list = new ArrayList<>();
        list = menuServiceMapper.menuDatas(enter.getType());
        return list;*/

        // ??????id??????????????????level
        OpeSysMenu menu = sysMenuService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(menu)) {
            throw new SesWebRosException(ExceptionCodeEnums.MENU_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.MENU_IS_NOT_EXIST.getMessage());
        }
        // level 1?????? 2???????????? 3???????????? 9??????
        Integer level = menu.getLevel();
        List<MenuDatasListResult> result;
        if (9 == level) {
            // ?????????????????????????????????
            result = menuServiceMapper.getSecondAndThirdMenu();
        } else if (2 == level || 3 == level) {
            // ????????????????????????????????????????????????????????????????????????
            result = menuServiceMapper.getAllCatalogAndMenu(enter.getId());
        } else {
            result = Collections.EMPTY_LIST;
        }
        result = CollectionUtils.isEmpty(result) ? Collections.EMPTY_LIST : result;
        return result;
    }

    /**
     * ????????????
     */
    @Override
    public List<MenuTreeResult> getCatalogList(GeneralEnter enter) {
        // ????????????id??????????????????
        LambdaQueryWrapper<OpeSysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(OpeSysUser::getId, enter.getUserId());
        userWrapper.eq(OpeSysUser::getDef1, SysUserSourceEnum.SYSTEM.getValue());
        userWrapper.last("limit 1");
        OpeSysUser admin = sysUserService.getOne(userWrapper);
        if (StringManaConstant.entityIsNull(admin)) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        List<MenuTreeResult> result = Lists.newArrayList();
        MenuTreeResult root = new MenuTreeResult();

        // ??????root?????????
        LambdaQueryWrapper<OpeSysMenu> rootWrapper = new LambdaQueryWrapper<>();
        rootWrapper.eq(OpeSysMenu::getDr, DelStatusEnum.VALID.getCode());
        rootWrapper.eq(OpeSysMenu::getLevel, 0);
        rootWrapper.last("limit 1");
        OpeSysMenu rootMenu = sysMenuService.getOne(rootWrapper);
        BeanUtils.copyProperties(rootMenu, root);

        // ?????????????????????,?????????????????????
        if (admin.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            LambdaQueryWrapper<OpeSysMenu> qw = new LambdaQueryWrapper<>();
            qw.eq(OpeSysMenu::getDr, DelStatusEnum.VALID.getCode());
            qw.eq(OpeSysMenu::getLevel, 1);
            qw.orderByAsc(OpeSysMenu::getSort, OpeSysMenu::getCreatedTime);
            List<OpeSysMenu> menuList = sysMenuService.list(qw);
            // ??????????????????????????????
            List<MenuTreeResult> children = this.buildMenuParallel(menuList, null, Boolean.TRUE);
            if (CollectionUtils.isNotEmpty(children)) {
                for (MenuTreeResult child : children) {
                    long id = child.getId();
                    LambdaQueryWrapper<OpeSysMenu> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(OpeSysMenu::getDr, DelStatusEnum.VALID.getCode());
                    wrapper.eq(OpeSysMenu::getPId, id);
                    List<OpeSysMenu> list = sysMenuService.list(wrapper);
                    if (CollectionUtils.isEmpty(list)) {
                        child.set_loading(null);
                    }
                }
            }
            root.setChildren(children);
        } else {
            // ????????????????????????,?????????????????????????????????id??????
            List<Long> roleIds = this.getRoleIds(new IdEnter(enter.getUserId()));
            if (CollUtil.isNotEmpty(roleIds)) {
                // ????????????id?????????????????????????????????id??????
                List<Long> menuIds = this.getMenuIdsByRoleIds(roleIds);
                if (CollUtil.isNotEmpty(menuIds)) {
                    LambdaQueryWrapper<OpeSysMenu> qw = new LambdaQueryWrapper<>();
                    qw.eq(OpeSysMenu::getDr, DelStatusEnum.VALID.getCode());
                    qw.eq(OpeSysMenu::getLevel, 1);
                    qw.in(OpeSysMenu::getId, menuIds);
                    qw.orderByAsc(OpeSysMenu::getSort, OpeSysMenu::getCreatedTime);
                    List<OpeSysMenu> menuList = sysMenuService.list(qw);
                    // ??????????????????????????????
                    List<MenuTreeResult> children = this.buildMenuParallel(menuList, roleIds, Boolean.FALSE);
                    if (CollectionUtils.isNotEmpty(children)) {
                        for (MenuTreeResult child : children) {
                            long id = child.getId();
                            LambdaQueryWrapper<OpeSysMenu> wrapper = new LambdaQueryWrapper<>();
                            wrapper.eq(OpeSysMenu::getDr, DelStatusEnum.VALID.getCode());
                            wrapper.eq(OpeSysMenu::getPId, id);
                            List<OpeSysMenu> list = sysMenuService.list(wrapper);
                            if (CollectionUtils.isEmpty(list)) {
                                child.set_loading(null);
                            }
                        }
                    }
                    root.setChildren(children);
                }
            }
        }
        result.add(root);
        return result;
    }

    /**
     * ????????????id??????????????????
     */
    @Override
    public List<MenuTreeResult> getSubListById(IdEnter enter) {
        LambdaQueryWrapper<OpeSysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSysMenu::getDr, DelStatusEnum.VALID.getCode());
        wrapper.eq(OpeSysMenu::getPId, enter.getId());
        wrapper.orderByAsc(OpeSysMenu::getSort, OpeSysMenu::getCreatedTime);
        List<OpeSysMenu> list = sysMenuService.list(wrapper);
        // list?????????????????????
        String str = JSON.toJSONString(list);
        List<MenuTreeResult> result = JSON.parseArray(str, MenuTreeResult.class);
        if (CollectionUtils.isNotEmpty(result)) {
            for (MenuTreeResult model : result) {
                long id = model.getId();
                LambdaQueryWrapper<OpeSysMenu> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeSysMenu::getDr, DelStatusEnum.VALID.getCode());
                qw.eq(OpeSysMenu::getPId, id);
                List<OpeSysMenu> subList = sysMenuService.list(qw);
                if (CollectionUtils.isEmpty(subList)) {
                    model.set_loading(null);
                }
            }
        }
        return result;
    }

}
