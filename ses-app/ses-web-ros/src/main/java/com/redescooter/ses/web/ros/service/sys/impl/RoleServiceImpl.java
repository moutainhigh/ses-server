package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.dept.DeptStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSysStaffMapper;
import com.redescooter.ses.web.ros.dao.sys.RoleServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.sys.*;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionIdEnter;
import com.redescooter.ses.web.ros.vo.sys.role.*;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.stream.Collectors;

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

    @Reference
    private IdAppService idAppService;
    @Reference
    private CityBaseService ctiyBaseService;

    @Autowired
    private OpeSysStaffMapper opeSysStaffMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OpeSysRoleDataService opeSysRoleDataService;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    @Transactional
    public GeneralResult save(RoleEnter enter) {
      //employeeListEnter参数值去空格
      RoleEnter roleEnter = SesStringUtils.objStringTrim(enter);
      if (roleEnter.getRoleName().length()<2||roleEnter.getRoleName().length()>20){
        throw new SesWebRosException(ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getMessage());
      }
      String roleName = SesStringUtils.upperCaseString(enter.getRoleName());
      enter.setRoleName(roleName);
      //保存岗位角色
        OpeSysRole role = this.builderRole(null, enter);
        sysRoleService.save(role);

        enter.setRoleId(role.getId());
        this.insertRoleAouth(enter);

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @Transactional
    public GeneralResult edit(RoleEnter roleEnter) {
        if (roleEnter.getRoleName().length() < 2 || roleEnter.getRoleName().length() > 20) {
            throw new SesWebRosException(ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getMessage());
        }
        //employeeListEnter参数值去空格
        RoleEnter enter = SesStringUtils.objStringTrim(roleEnter);
        String roleName = SesStringUtils.upperCaseString(enter.getRoleName());
        enter.setRoleName(roleName);
        OpeSysRole role = this.builderRole(enter.getRoleId(), enter);
        sysRoleService.updateById(role);
        this.updateRoleAouth(enter);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @Transactional
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
      if (enter.getKeyword()!=null && enter.getKeyword().length()>50){
        return new ArrayList<>();
      }
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
//        result.setSalesAreaResult(this.roleSalesAreaById(type, enter));
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


    @Override
    public GeneralResult roleSave(RoleSaveOrEditEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        if (enter.getSort() != null && enter.getSort() < 0){
            throw new SesWebRosException(ExceptionCodeEnums.SORT_NOT_NEG.getCode(), ExceptionCodeEnums.SORT_NOT_NEG.getMessage());
        }
        if (enter.getRoleName().length()<2||enter.getRoleName().length()>20){
            throw new SesWebRosException(ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getMessage());
        }
        if(!Strings.isNullOrEmpty(enter.getRoleDesc()) && enter.getRoleDesc().length() > 100){
            throw new SesWebRosException(ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getMessage());
        }
        // 校验角色名称是否重复
        checkRoleName(enter.getRoleName(),null);
        String roleName = SesStringUtils.upperCaseString(enter.getRoleName());
        enter.setRoleName(roleName);
        OpeSysRole role = new OpeSysRole();
        BeanUtils.copyProperties(enter,role);
        role.setId(idAppService.getId(SequenceName.OPE_SYS_ROLE));
        role.setTenantId(enter.getTenantId()==null?0L:enter.getTenantId());
        String roleCode = createCode();
        role.setRoleCode(roleCode);
        role.setCreatedBy(enter.getUserId());
        role.setUpdatedBy(enter.getUserId());
        role.setUpdateTime(new Date());
        sysRoleService.save(role);
        return new GeneralResult(enter.getRequestId());
    }


    // 校验角色名称是否重复（新增和编辑都要走这里）
    public void checkRoleName(String roleName, Long roleId){
        QueryWrapper<OpeSysRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysRole.COL_ROLE_NAME,roleName);
        if (roleId != null){
            // 编辑
            qw.ne(OpeSysRole.COL_ID,roleId);
        }
        int count = sysRoleService.count(qw);
        if (count > 0){
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_NAME_EXIST.getCode(), ExceptionCodeEnums.ROLE_NAME_EXIST.getMessage());
        }
    }


    // 新增角色的时候  生成角色编码
    public String createCode(){
        String roleCode = "R0"+new Random().nextInt(99999);
        QueryWrapper<OpeSysRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysRole.COL_ROLE_CODE,roleCode);
        int count = sysRoleService.count(qw);
        if(count > 0){
            createCode();
        }
        return roleCode;
    }

    @Override
    @Transactional
    public GeneralResult roleEdit(RoleSaveOrEditEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        if (enter.getRoleName().length() < 2 || enter.getRoleName().length() > 20) {
            throw new SesWebRosException(ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getMessage());
        }
        if (!Strings.isNullOrEmpty(enter.getRoleDesc()) && enter.getRoleDesc().length() > 100) {
            throw new SesWebRosException(ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getMessage());
        }
        // 校验角色名称是否重复
        checkRoleName(enter.getRoleName(),enter.getId());
        OpeSysRole role = sysRoleService.getById(enter.getId());
        if (role == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getMessage());
        }
        // 如果角色的状态从正常变为禁用，需要把这个角色下面的员工全部禁用
        if (role.getRoleStatus() == 1 && enter.getRoleStatus() == 2) {
            forbiddenStaff(role);
        }
        role.setPositionId(enter.getPositionId());
        role.setRoleName(enter.getRoleName());
        role.setSort(enter.getSort());
        role.setRoleStatus(enter.getRoleStatus());
        role.setRoleDesc(enter.getRoleDesc());
        role.setUpdatedBy(enter.getUserId());
        role.setUpdateTime(new Date());
        role.setSaleArea(enter.getSaleArea());
        sysRoleService.updateById(role);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public GeneralResult roleDelete(RoleOpEnter enter) {
        OpeSysRole role = sysRoleService.getById(enter.getId());
        if (role == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getMessage());
        }
        // 检验角色下面是否有员工，有员工则不能删除
        roleDeleCheck(role.getId());
        sysRoleService.removeById(role.getId());
        // 角色删除 把角色绑定菜单的关联关系删除（防止表数据过大）
        rolePermissionService.deleteRoleMeunByRoleId(new IdEnter(enter.getId()));
        // 判断角色有没有数据权限，有的话 删除
        QueryWrapper<OpeSysRoleData>  qw = new QueryWrapper<>();
        qw.eq(OpeSysRoleData.COL_ROLE_ID,enter.getId());
        opeSysRoleDataService.remove(qw);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public RoleDetailResult roleDetail(RoleOpEnter enter) {
        OpeSysRole role = sysRoleService.getById(enter.getId());
        if (role == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getMessage());
        }
        RoleDetailResult roleDetail = roleServiceMapper.roleDetail(role.getId());
        // 查找当前角色下的员工数
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_ROLE_ID, role.getId());
        List<OpeSysUserRole> staffList = sysUserRoleService.list(qw);
        roleDetail.setNum(CollectionUtils.isEmpty(staffList)?0:staffList.size());
        return roleDetail;
    }


    @Override
    public PageResult<RoleListResult> roleList(RoleQueryListEnter enter) {
        Set<Long> deptIds =  new HashSet<>();
        String key = JedisConstant.LOGIN_ROLE_DATA + enter.getUserId();
        // 通过这个来判断是不是管理员账号，默认为是管理员
        boolean flag = true;
        if (jedisCluster.exists(key)){
            flag = false;
            Map<String, String> map = jedisCluster.hgetAll(key);
            String ids = map.get("deptIds");
            if(!Strings.isNullOrEmpty(ids)){
                for (String s : ids.split(",")) {
                    deptIds.add(Long.parseLong(s));
                }
            }
        }
        int totalRows = roleServiceMapper.totalRows(enter,flag?null:deptIds);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<RoleListResult> list = roleServiceMapper.roleList(enter,flag?null:deptIds);
        List<Long> roleIds = list.stream().map(RoleListResult::getId).collect(Collectors.toList());
        // 查询这些角色下的员工数量
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.in(OpeSysUserRole.COL_ROLE_ID,roleIds);
        List<OpeSysUserRole> staffList = sysUserRoleService.list(qw);
        if(CollectionUtils.isNotEmpty(staffList)){
            // 按角色id分组
            Map<Long,List<OpeSysUserRole>> map = staffList.stream().collect(Collectors.groupingBy(OpeSysUserRole::getRoleId));
            for (RoleListResult result : list) {
                for (Long roleId : map.keySet()) {
                    if(Objects.equals(result.getId(),roleId)){
                        result.setNum(map.get(roleId).size());
                    }
                }
            }
        }
        return  PageResult.create(enter, totalRows, list);
    }


    @Transactional
    @Override
    public void disableRole(List<Long> positionIds) {
        QueryWrapper<OpeSysRole> role = new QueryWrapper<>();
        role.in(OpeSysRole.COL_POSITION_ID,positionIds);
        List<OpeSysRole> roles = sysRoleService.list(role);
        if(CollectionUtils.isNotEmpty(roles)){
            List<OpeSysRole> update = new ArrayList<>();
            for (OpeSysRole sysRole : roles) {
                sysRole.setRoleStatus(2);
                update.add(sysRole);
            }
            sysRoleService.updateBatch(update);
            // 禁用角色下面的员工
            for (OpeSysRole opeSysRole : roles) {
                forbiddenStaff(opeSysRole);
            }
        }
    }

    @Override
    @Transactional
    public GeneralResult roleMenuEdit(RoleMenuEditEnter enter) {
        // 先把当前角色已经有的菜单权限全部删除
        rolePermissionService.deleteRoleMeunByRoleId(new IdEnter(enter.getRoleId()));
        Set<Long> set = new HashSet<>();
        if(!Strings.isNullOrEmpty(enter.getMenuIds())){
            for (String s : enter.getMenuIds().split(",")) {
                set.add(Long.parseLong(s));
            }
            set.add(1008301L);
            rolePermissionService.insertRoleMenuPermissions(enter.getRoleId(),set);
        }
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<SalesAreaTressResult> roleCity(RoleOpEnter enter) {
        return null;
    }


    @Override
    @Transactional
    public GeneralResult roleCityEdit(RoleCityEditEnter enter) {
        // 先删除当前角色原先的销售区域
        rolePermissionService.deleteRoleSalesPermissionsByRoleId(new IdEnter(enter.getRoleId()));
        // 再绑定新的销售区域
        rolePermissionService.insertRoleSalesPermissions(enter.getRoleId(), new HashSet<>(enter.getCityIds()));
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<RoleDataResult> roleData(PositionIdEnter enter) {
        List<RoleDataResult> resultList = new ArrayList<>();
        QueryWrapper<OpeSysRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysRole.COL_TENANT_ID,enter.getTenantId());
        qw.eq(OpeSysRole.COL_ROLE_STATUS, DeptStatusEnums.COMPANY.getValue());
        if(!Strings.isNullOrEmpty(enter.getPositionId())){
            qw.in(OpeSysRole.COL_POSITION_ID,enter.getPositionId().split(","));
        }
        if (enter.getType() == 2){
            qw.eq(OpeSysRole.COL_ROLE_STATUS,1);
        }
        List<OpeSysRole> roles = sysRoleService.list(qw);
        if(CollectionUtils.isNotEmpty(roles)){
            for (OpeSysRole role : roles) {
                RoleDataResult result = new RoleDataResult();
                result.setRoleId(role.getId());
                result.setRoleName(role.getRoleName());
                resultList.add(result);
            }

        }
        return resultList;
    }


    public void roleDeleCheck(Long roleId){
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_ROLE_ID, roleId);
        List<OpeSysUserRole> staffList = sysUserRoleService.list(qw);
        if(CollectionUtils.isNotEmpty(staffList)){
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_IS_NOT_DELETE.getCode(), ExceptionCodeEnums.ROLE_IS_NOT_DELETE.getMessage());
        }
    }


    /**
     * @Author Aleks
     * @Description  如果员工只有当前的这个一个角色，直接禁用员工，如果员工有多个角色，
     *               判断别的角色的状态，如果别的角色都是禁用，则员工变禁用，否则员工状态不变
     * @Date  2020/9/1 14:36
     * @Param [role]
     * @return
     **/
    public void forbiddenStaff(OpeSysRole role) {
        // 角色的状态从正常变为禁用，需要校验这个角色下面是否有（正常的）员工，有的话，不能修改
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.eq(OpeSysStaff.COL_ROLE_ID, role.getId());
        qw.eq(OpeSysStaff.COL_STATUS, 1);
        List<OpeSysStaff> staffList = roleServiceMapper.roleStaffs(role.getId());
        if(CollectionUtils.isEmpty(staffList)){
            return;
        }
        List<OpeSysStaff> updateList = new ArrayList<>();
        for (OpeSysStaff staff : staffList) {
            List<OpeSysRole> stallRoles = roleServiceMapper.staffRoles(staff.getId(),role.getId());
            if(CollectionUtils.isEmpty(stallRoles)){
                updateList.add(staff);
            }else {
                // 判断是否全部为禁用的
                Integer num = stallRoles.size();
                if(num == stallRoles.stream().filter(o->o.getRoleStatus() == 2).collect(Collectors.toList()).size()){
                    // 全部为禁用的
                    updateList.add(staff);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(updateList)){
            for (OpeSysStaff sysStaff : updateList) {
                sysStaff.setStatus(2);
            }
            opeSysStaffMapper.updateBatch(updateList);
            // 员工禁用之后，员工对应的账号也要被禁用
            employeeService.disAbleUser(updateList.stream().map(OpeSysStaff::getId).collect(Collectors.toList()));
        }
    }


    @Override
    public void deleRoleByPosIds(List<Long> posIds) {
         QueryWrapper<OpeSysRole> qw = new QueryWrapper<>();
         qw.in(OpeSysRole.COL_POSITION_ID,posIds);
        sysRoleService.remove(qw);
    }

}
