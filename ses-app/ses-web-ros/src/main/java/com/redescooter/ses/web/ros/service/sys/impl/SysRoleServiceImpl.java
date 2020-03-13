package com.redescooter.ses.web.ros.service.sys.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.service.sys.SysRoleService;
import com.redescooter.ses.web.ros.service.sys.SysSalesAreaService;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.position.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.DeptRoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysRoleServiceImpl
 * @Author Jerry
 * @date 2020/03/12 14:12
 * @Description:
 */

@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysSalesAreaService sysSalesAreaService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private OpeSysRoleService roleService;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public GeneralResult save(RoleEnter enter) {
        //保存岗位角色
        OpeSysRole role = this.builderRole(null, enter);
        roleService.save(role);

        enter.setRoleId(role.getId());
        this.insertRoleAouth(enter);

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult edit(RoleEnter enter) {
        OpeSysRole role = this.builderRole(enter.getRoleId(), enter);
        roleService.updateById(role);

        this.updateRoleAouth(enter);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 角色列表
     *
     * @param enter
     * @return
     */
    @Override
    public DeptRoleListResult list(RoleListEnter enter) {
        DeptRoleListResult result = DeptRoleListResult.builder()
                .deptId(1000013L)
                .deptName("产品部")
                .totalCount(1)
                .build();
        List<RoleResult> roleResultList = new ArrayList<>();
        roleResultList.add(RoleResult.builder().id(1000000L).roleName("管理员").description("admin").build());
        result.setRoleList(roleResultList);
        return result;
    }

    @Override
    public DeptAuthorityDetailsResult authorityDetails(IdEnter enter) {

        //根据岗位ID获取部门菜单权限树
        List<MenuTreeResult> muns = menuService.trees(enter);

        //根据岗位ID获取销售区域树
        List<SalesAreaTressResult> areas = sysSalesAreaService.list(enter);

        DeptAuthorityDetailsResult result = new DeptAuthorityDetailsResult();
        result.setMenuTreeResult(muns);
        result.setSalesAreaTressResult(areas);
        result.setRequestId(enter.getRequestId());
        return result;
    }


    private OpeSysRole builderRole(Long id, RoleEnter enter) {
        OpeSysRole role = new OpeSysRole();
        if (id == null || id == 0) {
            role.setId(idAppService.getId(SequenceName.OPE_SYS_ROLE));
            role.setDr(Constant.DR_FALSE);
            role.setCreatedBy(enter.getUserId());
            role.setCreateTime(new Date());
        } else {
            role.setId(id);
        }
        role.setTenantId(enter.getTenantId());
        role.setRoleName(enter.getRoleName());
        role.setRoleCode(enter.getRoleCode());
        role.setRoleDesc(enter.getDescription());
        role.setUpdatedBy(enter.getUserId());
        role.setUpdateTime(new Date());
        return role;
    }

    private void insertRoleAouth(RoleEnter enter) {
        //创建岗位销售区域关系
        rolePermissionService.insertRoleSalesPermissions(enter.getRoleId(), enter.getSalesPermissionIds());
        //创建岗位菜单权限关系
        rolePermissionService.insertRoleMenuPermissions(enter.getRoleId(), enter.getMeunPermissionIds());
        //创建岗位部门权限关系
        rolePermissionService.insertRoleDeptPermissions(enter.getRoleId(), enter.getDeptId());
    }

    private void updateRoleAouth(RoleEnter enter) {
        //删除历史权限
        rolePermissionService.deleteRoleDeptPermissions(enter.getRoleId(), enter.getDeptId());
        rolePermissionService.deleteRoleMenuPermissions(enter.getRoleId(), enter.getMeunPermissionIds());
        rolePermissionService.deleteRoleSalesPermissions(enter.getRoleId(), enter.getSalesPermissionIds());
        //重建权限
        this.insertRoleAouth(enter);
    }
}
