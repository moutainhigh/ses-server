package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.IdEnter;

import java.util.Set;

/**
 * @ClassName RolePermissionService
 * @Author Jerry
 * @date 2020/03/12 14:28
 * @Description:
 */
public interface RolePermissionService {

    /**
     * 创建角色销售区域关系
     */
    void insertRoleSalesPermissions(long roleId, Set<Long> cidyId);

    /**
     * 删除角色销售区域关系
     */
    void deleteRoleSalesPermissions(long roleId, Set<Long> cidyId);

    /**
     * 创建角色部门关系
     */
    void insertRoleDeptPermissions(long roleId, long deptId);

    /**
     * 删除角色部门关系
     */
    void deleteRoleDeptPermissions(long roleId, long deptId);

    /**
     * 创建角色菜单关系
     */
    void insertRoleMenuPermissions(long roleId, Set<Long> menuId);

    /**
     * 删除角色菜单关系
     */
    void deleteRoleMenuPermissions(long role, Set<Long> menuId);

    /**
     * 删除该角色下的所有菜单权限
     *
     * @param enter
     */
    void deleteRoleMeunByRoleId(IdEnter enter);

    /**
     * 删除角色与部门的对应关系
     *
     * @param enter
     */
    void deleteRoleDeptByRoleId(IdEnter enter);

    /**
     * 删除所有的销售区域
     *
     * @param enter
     */
    void deleteRoleSalesPermissionsByRoleId(IdEnter enter);
}