package com.redescooter.ses.web.ros.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.OpeSysRoleDept;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleSalesCidyService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName RolePermissionServiceImpl
 * @Author Jerry
 * @date 2020/03/12 14:46
 * @Description:
 */
@Slf4j
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private OpeSysRoleSalesCidyService roleSalesCidyService;

    @Autowired
    private OpeSysRoleMenuService roleMenuService;

    @Autowired
    private OpeSysRoleDeptService roleDeptService;

    @Override
    public void insertRoleSalesPermissions(long roleId, Set<Long> cidyId) {

        if (CollUtil.isNotEmpty(cidyId)) {
            List<OpeSysRoleSalesCidy> saveList = new ArrayList<>();
            for (Long id : cidyId) {
                saveList.add(new OpeSysRoleSalesCidy(roleId, id));
            }
            roleSalesCidyService.batchInsert(saveList);
        }
    }

    @Override
    public void deleteRoleSalesPermissions(long roleId, Set<Long> cidyId) {
        UpdateWrapper<OpeSysRoleSalesCidy> delete = new UpdateWrapper<>();
        delete.eq(OpeSysRoleSalesCidy.COL_ROLE_ID, roleId);
        if (CollectionUtils.isNotEmpty(cidyId)) {
            delete.in(OpeSysRoleSalesCidy.COL_CITY_ID, new ArrayList<>(cidyId));
        }
        roleSalesCidyService.remove(delete);
    }

    @Override
    public void insertRoleDeptPermissions(long roleId, long deptId) {
        OpeSysRoleDept save = new OpeSysRoleDept(roleId, deptId);
        roleDeptService.save(save);
    }

    @Override
    public void deleteRoleDeptPermissions(long roleId, long deptId) {

        UpdateWrapper<OpeSysRoleDept> delete = new UpdateWrapper<>();
        delete.eq(OpeSysRoleDept.COL_ROLE_ID, roleId);
        delete.eq(OpeSysRoleDept.COL_DEPT_ID, deptId);
        roleDeptService.remove(delete);

    }

    @Override
    public void insertRoleMenuPermissions(long roleId, Set<Long> menuId) {
        if (CollUtil.isNotEmpty(menuId)) {
            List<OpeSysRoleMenu> saveList = new ArrayList<>();
            for (Long id : menuId) {
                saveList.add(new OpeSysRoleMenu(roleId, id));
            }
            roleMenuService.batchInsert(saveList);
        }
    }

    @Override
    public void deleteRoleMenuPermissions(long roleId, Set<Long> menuId) {
        UpdateWrapper<OpeSysRoleMenu> delete = new UpdateWrapper<>();
        delete.eq(OpeSysRoleMenu.COL_ROLE_ID, roleId);
        if (CollectionUtils.isNotEmpty(menuId)) {
            delete.in(OpeSysRoleMenu.COL_MENU_ID, new ArrayList<>(menuId));
        }
        roleMenuService.remove(delete);
    }

    @Override
    public void deleteRoleMeunByRoleId(IdEnter enter) {
        LambdaQueryWrapper<OpeSysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSysRoleMenu::getRoleId, enter.getId());
        roleMenuService.remove(wrapper);
    }

    @Override
    public void deleteRoleDeptByRoleId(IdEnter enter) {
        LambdaQueryWrapper<OpeSysRoleDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSysRoleDept::getRoleId, enter.getId());
        roleDeptService.remove(wrapper);
    }

    /**
     * 删除所有的销售区域
     *
     * @param enter
     */
    @Override
    public void deleteRoleSalesPermissionsByRoleId(IdEnter enter) {
        LambdaQueryWrapper<OpeSysRoleSalesCidy> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSysRoleSalesCidy::getRoleId, enter.getId());
        roleSalesCidyService.remove(wrapper);
    }
}
