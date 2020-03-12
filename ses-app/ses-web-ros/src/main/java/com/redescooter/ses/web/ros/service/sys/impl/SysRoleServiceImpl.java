package com.redescooter.ses.web.ros.service.sys.impl;

import java.util.Date;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import com.redescooter.ses.web.ros.service.sys.SysRoleService;
import com.redescooter.ses.web.ros.vo.sys.role.SaveRoleEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private OpeSysRoleService roleService;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public GeneralResult save(SaveRoleEnter enter) {
        //保存岗位角色
        OpeSysRole role = this.builderRole(enter);
        roleService.save(role);
        //创建岗位销售区域关系
        rolePermissionService.insertRoleSalesPermissions(role.getId(), enter.getSalesPermissionIds());
        //创建岗位菜单权限关系
        rolePermissionService.insertRoleMenuPermissions(role.getId(), enter.getMeunPermissionIds());
        //创建岗位部门权限关系
        rolePermissionService.insertRoleDeptPermissions(role.getId(), enter.getDeptId());
        return new GeneralResult(enter.getRequestId());
    }


    private OpeSysRole builderRole(SaveRoleEnter enter) {
        OpeSysRole role = new OpeSysRole();
        role.setId(idAppService.getId(SequenceName.OPE_SYS_ROLE));
        role.setDr(Constant.DR_FALSE);
        role.setTenantId(enter.getTenantId());
        role.setRoleName(enter.getRoleName());
        role.setRoleCode(enter.getRoleCode());
        role.setRoleDesc(enter.getDescription());
        role.setCreatedBy(enter.getUserId());
        role.setCreateTime(new Date());
        role.setUpdatedBy(enter.getUserId());
        role.setUpdateTime(new Date());
        return role;
    }
}
