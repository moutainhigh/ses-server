package com.redescooter.ses.web.ros.service.sys.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.sys.SysRoleServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.service.sys.SysRoleService;
import com.redescooter.ses.web.ros.service.sys.SysSalesAreaService;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.DeptRoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
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

    @Autowired
    private OpeSysDeptService opeSysDeptService;

    @Autowired
    private OpeSysRoleService opeSysRoleService;

    @Autowired
    private SysRoleServiceMapper sysRoleServiceMapper;

    @Autowired
    private OpeSysMenuService opeSysMenuService;

    @Reference
    private CityBaseService ctiyBaseService;

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
    public List<DeptRoleListResult> list(RoleListEnter enter) {
        //查询所有部门
        List<DeptRoleListResult> opeSysDeptList = sysRoleServiceMapper.roleDeptlist(enter);
        if (CollectionUtils.isEmpty(opeSysDeptList)) {
            return new ArrayList<>();
        }
        List<RoleResult> roleList = sysRoleServiceMapper.list(enter);
        if (CollectionUtils.isEmpty(roleList)) {
            return opeSysDeptList;
        }
        opeSysDeptList.forEach(item -> {
            List<RoleResult> roleResultList = new ArrayList<>();
            roleList.forEach(role -> {
                if (item.getDeptId().equals(role.getDeptId())) {
                    roleResultList.add(role);
                }
            });
            item.setRoleList(roleResultList);
        });
        return opeSysDeptList;
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
            if (opeSysRoleService.getById(enter.getRoleId()) == null) {
                throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
            }
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
        // 部门过滤
        if (opeSysDeptService.getById(enter.getDeptId()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }
        //销售区域 校验
        if (CollectionUtils.isNotEmpty(enter.getSalesPermissionIds())) {
            List<CityResult> cityList = ctiyBaseService.list(enter);
            List<Long> cityIds = new ArrayList<>();
            cityList.forEach(item -> {
                cityIds.add(item.getId());
            });
            enter.getSalesPermissionIds().forEach(item -> {
                if (!cityIds.contains(item)) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });
        }
        // 菜单过滤 校验
        if (CollectionUtils.isNotEmpty(enter.getMeunPermissionIds())) {
            List<OpeSysMenu> sysMenuList = opeSysMenuService.list();
            List<Long> sysMenuIds = new ArrayList<>();

            sysMenuList.forEach(item -> {
                sysMenuIds.add(item.getId());
            });
            enter.getMeunPermissionIds().forEach(item -> {
                if (!sysMenuIds.contains(item)) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });
        }

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
