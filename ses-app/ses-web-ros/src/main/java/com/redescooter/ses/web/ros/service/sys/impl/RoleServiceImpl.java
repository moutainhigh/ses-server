package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
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
import com.redescooter.ses.web.ros.service.sys.MenuService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import com.redescooter.ses.web.ros.service.sys.RoleService;
import com.redescooter.ses.web.ros.service.sys.SalesAreaService;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.role.DeptRoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @ClassName RoleServiceImpl
 * @Author Jerry
 * @date 2020/03/12 14:12
 * @Description:
 */

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleServiceMapper roleServiceMapper;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private SalesAreaService salesAreaService;
    @Autowired
    private OpeSysRoleService sysRoleService;
    @Autowired
    private OpeSysDeptService sysDeptService;
    @Autowired
    private OpeSysMenuService sysMenuService;
    @Autowired
    private OpeSysUserRoleService sysUserRoleService;

    @Autowired
    private IdAppService idAppService;
    @Reference
    private CityBaseService ctiyBaseService;

    @Override
    public GeneralResult save(RoleEnter enter) {
        //保存岗位角色
        OpeSysRole role = this.builderRole(null, enter);
        sysRoleService.save(role);

        enter.setRoleId(role.getId());
        this.insertRoleAouth(enter);

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult edit(RoleEnter enter) {
        OpeSysRole role = this.builderRole(enter.getRoleId(), enter);
        sysRoleService.updateById(role);
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
        sysRoleService.removeById(enter.getId());
        //删除角色对应的权限关系
        rolePermissionService.deleteRoleMeunByRoleId(enter);
        //删除角色对应的销售区域关系
        salesAreaService.deleteRoleSalesAreaByRoleId(enter);
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
        //查看所有角色
        List<RoleResult> roleList = roleServiceMapper.list(enter);
        if (CollectionUtils.isEmpty(roleList)) {
            return opeSysDeptList;
        }
        opeSysDeptList.forEach(item -> {
            List<RoleResult> roleResultList = new ArrayList<>();
            roleList.forEach(role -> {
                if (item.getDeptId().equals(role.getDeptId())) {
                    //查询该部门岗位下的人员数量
                    role.setCount(sysUserRoleService.count(new LambdaQueryWrapper<OpeSysUserRole>().eq(OpeSysUserRole::getRoleId, role.getId())));
                    roleResultList.add(role);
                }
            });
            item.setRoleList(roleResultList);
        });
        return opeSysDeptList;
    }

    @Override
    public DeptAuthorityDetailsResult roleAuthDetails(String type, IdEnter enter) {
        DeptAuthorityDetailsResult result = new DeptAuthorityDetailsResult();
        result.setMenuResult(this.roleMenuById(type, enter));
        result.setSalesAreaResult(this.roleSalesAreaById(type, enter));
        result.setRequestId(enter.getRequestId());
        return result;
    }

    @Override
    public List<SalesAreaTressResult> roleSalesAreaById(String type, IdEnter enter) {
        List<SalesAreaTressResult> result = null;
        if ("tree".equals(type)) {
            result = salesAreaService.trees(enter);
        }
        if ("parallel".equals(type)) {
            result = salesAreaService.list(enter);
        }
        return result;
    }

    @Override
    public List<MenuTreeResult> roleMenuById(String type, IdEnter enter) {
        List<MenuTreeResult> result = null;
        if ("tree".equals(type)) {
            result = menuService.roleMenuAuthTreeByRoleId(enter);
        }
        if ("parallel".equals(type)) {
            result = menuService.roleMenuAuthParallelByRoleId(enter);
        }
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
            if (sysRoleService.getById(enter.getRoleId()) == null) {
                throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
            }
            role.setId(id);
        }
        role.setTenantId(enter.getTenantId());
        role.setRoleName(enter.getRoleName());
        role.setRoleCode(SesStringUtils.isNotBlank(enter.getRoleCode()) == true ? enter.getRoleCode() : null);
        role.setRoleDesc(SesStringUtils.isNotBlank(enter.getDescription()) == true ? enter.getDescription() : null);
        role.setUpdatedBy(enter.getUserId());
        role.setUpdateTime(new Date());
        return role;
    }

    private void insertRoleAouth(RoleEnter enter) {
        Set<Long> salesPermissionIds = null;
        Set<Long> meunPermissionIds = null;
        if (SesStringUtils.isNotBlank(enter.getSalesPermissionIds())) {
            // 将 销售区域 json 格式 转 set集合
            salesPermissionIds = new HashSet<>(JSON.parseArray(enter.getSalesPermissionIds(), Long.class));
        }
        if (SesStringUtils.isNotBlank(enter.getMeunPermissionIds())) {
            // 将 菜单列表 json 格式 转set 集合
            meunPermissionIds = new HashSet<>(JSON.parseArray(enter.getMeunPermissionIds(), Long.class));
        }

        checkRoleAuothParameter(enter, salesPermissionIds, meunPermissionIds);

        //创建岗位销售区域关系
        rolePermissionService.insertRoleSalesPermissions(enter.getRoleId(), salesPermissionIds);
        //创建岗位菜单权限关系
        rolePermissionService.insertRoleMenuPermissions(enter.getRoleId(), meunPermissionIds);
        //创建岗位部门权限关系
        rolePermissionService.insertRoleDeptPermissions(enter.getRoleId(), enter.getDeptId());
    }

    private void updateRoleAouth(RoleEnter enter) {
        Set<Long> salesPermissionIds = null;
        Set<Long> meunPermissionIds = null;
        if (SesStringUtils.isNotBlank(enter.getSalesPermissionIds())) {
            // 将 销售区域 json 格式 转 set集合
            salesPermissionIds = new HashSet<>(JSON.parseArray(enter.getSalesPermissionIds(), Long.class));
        }
        if (SesStringUtils.isNotBlank(enter.getMeunPermissionIds())) {
            // 将 菜单列表 json 格式 转set 集合
            meunPermissionIds = new HashSet<>(JSON.parseArray(enter.getMeunPermissionIds(), Long.class));
        }
        checkRoleAuothParameter(enter, salesPermissionIds, meunPermissionIds);

        //删除历史权限
        rolePermissionService.deleteRoleMeunByRoleId(new IdEnter(enter.getRoleId()));
        rolePermissionService.deleteRoleDeptByRoleId(new IdEnter(enter.getRoleId()));
        rolePermissionService.deleteRoleSalesPermissionsByRoleId(new IdEnter(enter.getRoleId()));

        //重建权限
        this.insertRoleAouth(enter);
    }

    private void checkRoleAuothParameter(RoleEnter enter, Set<Long> salesPermissionIds, Set<Long> meunPermissionIds) {
        // 部门过滤
        if (sysDeptService.getById(enter.getDeptId()) == null) {
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
            List<OpeSysMenu> sysMenuList = sysMenuService.list();
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