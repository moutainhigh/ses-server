package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.StringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.sys.RoleServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.service.sys.SysRoleService;
import com.redescooter.ses.web.ros.service.sys.SysSalesAreaService;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.menu.ModulePermissionsResult;
import com.redescooter.ses.web.ros.vo.sys.role.DeptRoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleResult;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private RoleServiceMapper roleServiceMapper;

    @Autowired
    private OpeSysMenuService opeSysMenuService;

    @Autowired
    private OpeSysUserRoleService sysUserRoleService;

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

    @Override
    public GeneralResult delete(IdEnter enter) {

        //验证该角色下是否有人员，如果有人员，那么选进行解绑人员
        LambdaQueryWrapper<OpeSysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSysUserRole::getRoleId, enter.getId());
        int count = sysUserRoleService.count(wrapper);
        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getCode(), ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getMessage());
        }
        //删除角色
        roleService.removeById(enter.getId());
        //删除角色对应的权限关系
        rolePermissionService.deleteRoleMeunByRoleId(enter);
        //删除角色对应的销售区域关系
        sysSalesAreaService.deleteRoleSalesAreaByRoleId(enter);
        //删除角色对应的部门关系
        rolePermissionService.deleteRoleDeptByRoleId(enter);
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
        List<DeptRoleListResult> opeSysDeptList = roleServiceMapper.roleDeptlist(enter);
        if (CollectionUtils.isEmpty(opeSysDeptList)) {
            return new ArrayList<>();
        }
        List<RoleResult> roleList = roleServiceMapper.list(enter);
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
        Map<String, ModulePermissionsResult> muns = menuService.modulePermissions(enter);
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
        role.setRoleCode(StringUtils.isNotBlank(enter.getRoleCode()) == true ? enter.getRoleCode() : null);
        role.setRoleDesc(StringUtils.isNotBlank(enter.getDescription()) == true ? enter.getDescription() : null);
        role.setUpdatedBy(enter.getUserId());
        role.setUpdateTime(new Date());
        return role;
    }

    private void insertRoleAouth(RoleEnter enter) {
        // 将 销售区域 json 格式 转 set集合
        Set<Long> salesPermissionIds = new HashSet<>(JSON.parseArray(enter.getSalesPermissionIds(), Long.class));

        // 将 菜单列表 json 格式 转set 集合
        Set<Long> meunPermissionIds = new HashSet<>(JSON.parseArray(enter.getMeunPermissionIds(), Long.class));
        checkRoleAuothParameter(enter, salesPermissionIds, meunPermissionIds);

        //创建岗位销售区域关系
        rolePermissionService.insertRoleSalesPermissions(enter.getRoleId(), salesPermissionIds);
        //创建岗位菜单权限关系
        rolePermissionService.insertRoleMenuPermissions(enter.getRoleId(), meunPermissionIds);
        //创建岗位部门权限关系
        rolePermissionService.insertRoleDeptPermissions(enter.getRoleId(), enter.getDeptId());
    }

    private void updateRoleAouth(RoleEnter enter) {
        // 将 销售区域 json 格式 转 set集合
        Set<Long> salesPermissionIds = new HashSet<>(JSON.parseArray(enter.getSalesPermissionIds(), Long.class));

        // 将 菜单列表 json 格式 转set 集合
        Set<Long> meunPermissionIds = new HashSet<>(JSON.parseArray(enter.getMeunPermissionIds(), Long.class));
        checkRoleAuothParameter(enter, salesPermissionIds, meunPermissionIds);

        //删除历史权限
        rolePermissionService.deleteRoleDeptPermissions(enter.getRoleId(), enter.getDeptId());
        rolePermissionService.deleteRoleMenuPermissions(enter.getRoleId(), meunPermissionIds);
        rolePermissionService.deleteRoleSalesPermissions(enter.getRoleId(), salesPermissionIds);
        //重建权限
        this.insertRoleAouth(enter);
    }

    private void checkRoleAuothParameter(RoleEnter enter, Set<Long> salesPermissionIds, Set<Long> meunPermissionIds) {
        // 部门过滤
        if (opeSysDeptService.getById(enter.getDeptId()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }

        //销售区域 校验
        if (CollectionUtils.isNotEmpty(salesPermissionIds)) {
            List<CityResult> cityList = ctiyBaseService.list(enter);
            List<Long> cityIds = new ArrayList<>();
            cityList.forEach(item -> {
                cityIds.add(item.getId());
            });
            salesPermissionIds.forEach(item -> {
                if (!cityIds.contains(item)) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });
        }
        // 菜单过滤 校验
        if (CollectionUtils.isNotEmpty(meunPermissionIds)) {
            List<OpeSysMenu> sysMenuList = opeSysMenuService.list();
            List<Long> sysMenuIds = new ArrayList<>();

            sysMenuList.forEach(item -> {
                sysMenuIds.add(item.getId());
            });
            meunPermissionIds.forEach(item -> {
                if (!sysMenuIds.contains(item)) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });
        }
    }
}