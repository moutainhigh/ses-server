package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.base.OpeSysStaffMapper;
import com.redescooter.ses.web.ros.dao.sys.RoleServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.dm.OpeSysRoleData;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleDataService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.sys.EmployeeService;
import com.redescooter.ses.web.ros.service.sys.MenuService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import com.redescooter.ses.web.ros.service.sys.RoleService;
import com.redescooter.ses.web.ros.service.sys.SalesAreaService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionIdEnter;
import com.redescooter.ses.web.ros.vo.sys.role.DeptRoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleCityEditEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleDataResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleDetailResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleMenuEditEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleOpEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleQueryListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleSaveOrEditEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
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

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult save(RoleEnter enter) {
        //employeeListEnter??????????????????
        RoleEnter roleEnter = SesStringUtils.objStringTrim(enter);
        if (NumberUtil.ltTwoOrGtTwenty(roleEnter.getRoleName().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getMessage());
        }
        String roleName = SesStringUtils.upperCaseString(enter.getRoleName());
        enter.setRoleName(roleName);
        //??????????????????
        OpeSysRole role = this.builderRole(null, enter);
        sysRoleService.save(role);

        enter.setRoleId(role.getId());
        this.insertRoleAouth(enter);

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult edit(RoleEnter roleEnter) {
        if (NumberUtil.ltTwoOrGtTwenty(roleEnter.getRoleName().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getMessage());
        }
        //employeeListEnter??????????????????
        RoleEnter enter = SesStringUtils.objStringTrim(roleEnter);
        String roleName = SesStringUtils.upperCaseString(enter.getRoleName());
        enter.setRoleName(roleName);
        OpeSysRole role = this.builderRole(enter.getRoleId(), enter);
        sysRoleService.updateById(role);
        this.updateRoleAouth(enter);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult delete(IdEnter enter) {

        //?????????????????????????????????????????????????????????????????????????????????
        LambdaQueryWrapper<OpeSysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeSysUserRole::getRoleId, enter.getId());
        int count = sysUserRoleService.count(wrapper);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getCode(), ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getMessage());
        }
        //????????????
        sysRoleService.removeById(enter.getId());
        //?????????????????????????????????
        rolePermissionService.deleteRoleMeunByRoleId(enter);
        //???????????????????????????????????????
        salesAreaService.deleteRoleSalesAreaByRoleId(enter);
        //?????????????????????????????????
        rolePermissionService.deleteRoleDeptByRoleId(enter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeptRoleListResult> list(RoleListEnter enter) {
        if (NumberUtil.notNullAndGtFifty(enter.getKeyword())) {
            return new ArrayList<>();
        }
        //??????????????????
        List<DeptRoleListResult> opeSysDeptList = roleServiceMapper.roleDeptlist(enter);
        if (CollectionUtils.isEmpty(opeSysDeptList)) {
            return new ArrayList<>();
        }
        //??????????????????
        List<RoleResult> roleList = roleServiceMapper.list(enter);
        if (CollectionUtils.isEmpty(roleList)) {
            return opeSysDeptList;
        }
        opeSysDeptList.forEach(item -> {
            List<RoleResult> roleResultList = new ArrayList<>();
            roleList.forEach(role -> {
                if (item.getDeptId().equals(role.getDeptId())) {
                    //???????????????????????????????????????
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
        if (StringManaConstant.entityIsNull(id) || 0 == id) {
            role.setId(idAppService.getId(SequenceName.OPE_SYS_ROLE));
            role.setDr(Constant.DR_FALSE);
            role.setCreatedBy(enter.getUserId());
            role.setCreateTime(new Date());
        } else {
            if (StringManaConstant.entityIsNull(sysRoleService.getById(enter.getRoleId()))) {
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
            // ??? ???????????? json ?????? ??? set??????
            salesPermissionIds = new HashSet<>(JSON.parseArray(enter.getSalesPermissionIds(), Long.class));
        }
        if (SesStringUtils.isNotBlank(enter.getMeunPermissionIds())) {
            // ??? ???????????? json ?????? ???set ??????
            meunPermissionIds = new HashSet<>(JSON.parseArray(enter.getMeunPermissionIds(), Long.class));
        }

        checkRoleAuothParameter(enter, salesPermissionIds, meunPermissionIds);

        //??????????????????????????????
        rolePermissionService.insertRoleSalesPermissions(enter.getRoleId(), salesPermissionIds);
        //??????????????????????????????
        rolePermissionService.insertRoleMenuPermissions(enter.getRoleId(), meunPermissionIds);
        //??????????????????????????????
        rolePermissionService.insertRoleDeptPermissions(enter.getRoleId(), enter.getDeptId());
    }

    private void updateRoleAouth(RoleEnter enter) {
        Set<Long> salesPermissionIds = null;
        Set<Long> meunPermissionIds = null;
        if (SesStringUtils.isNotBlank(enter.getSalesPermissionIds())) {
            // ??? ???????????? json ?????? ??? set??????
            salesPermissionIds = new HashSet<>(JSON.parseArray(enter.getSalesPermissionIds(), Long.class));
        }
        if (SesStringUtils.isNotBlank(enter.getMeunPermissionIds())) {
            // ??? ???????????? json ?????? ???set ??????
            meunPermissionIds = new HashSet<>(JSON.parseArray(enter.getMeunPermissionIds(), Long.class));
        }
        checkRoleAuothParameter(enter, salesPermissionIds, meunPermissionIds);

        //??????????????????
        rolePermissionService.deleteRoleMeunByRoleId(new IdEnter(enter.getRoleId()));
        rolePermissionService.deleteRoleDeptByRoleId(new IdEnter(enter.getRoleId()));
        rolePermissionService.deleteRoleSalesPermissionsByRoleId(new IdEnter(enter.getRoleId()));

        //????????????
        this.insertRoleAouth(enter);
    }

    private void checkRoleAuothParameter(RoleEnter enter, Set<Long> salesPermissionIds, Set<Long> meunPermissionIds) {
        // ????????????
        if (StringManaConstant.entityIsNull(sysDeptService.getById(enter.getDeptId()))) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }

        //???????????? ??????
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
        // ???????????? ??????
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult roleSave(RoleSaveOrEditEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        if (StringManaConstant.entityIsNotNull(enter.getSort()) && 0 > enter.getSort()) {
            throw new SesWebRosException(ExceptionCodeEnums.SORT_NOT_NEG.getCode(), ExceptionCodeEnums.SORT_NOT_NEG.getMessage());
        }
        if (NumberUtil.ltTwoOrGtTwenty(enter.getRoleName().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getMessage());
        }
        if (!Strings.isNullOrEmpty(enter.getRoleDesc()) && 100 < enter.getRoleDesc().length()) {
            throw new SesWebRosException(ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getMessage());
        }
        // ??????????????????????????????
        checkRoleName(enter.getRoleName(), null);
        String roleName = SesStringUtils.upperCaseString(enter.getRoleName());
        enter.setRoleName(roleName);
        OpeSysRole role = new OpeSysRole();
        BeanUtils.copyProperties(enter, role);
        role.setId(idAppService.getId(SequenceName.OPE_SYS_ROLE));
        role.setTenantId(enter.getTenantId() == null ? 0L : enter.getTenantId());
        String roleCode = createCode();
        role.setRoleCode(roleCode);
        role.setCreatedBy(enter.getUserId());
        role.setUpdatedBy(enter.getUserId());
        role.setUpdateTime(new Date());
        sysRoleService.save(role);
        return new GeneralResult(enter.getRequestId());
    }


    // ??????????????????????????????????????????????????????????????????
    public void checkRoleName(String roleName, Long roleId) {
        QueryWrapper<OpeSysRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysRole.COL_ROLE_NAME, roleName);
        if (StringManaConstant.entityIsNotNull(roleId)) {
            // ??????
            qw.ne(OpeSysRole.COL_ID, roleId);
        }
        int count = sysRoleService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_NAME_EXIST.getCode(), ExceptionCodeEnums.ROLE_NAME_EXIST.getMessage());
        }
    }


    // ?????????????????????  ??????????????????
    public String createCode() {
        String roleCode = "R0" + new Random().nextInt(99999);
        QueryWrapper<OpeSysRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysRole.COL_ROLE_CODE, roleCode);
        int count = sysRoleService.count(qw);
        if (0 < count) {
            createCode();
        }
        return roleCode;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult roleEdit(RoleSaveOrEditEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        if (NumberUtil.ltTwoOrGtTwenty(enter.getRoleName().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getCode(), ExceptionCodeEnums.JOB_TITLE_IS_ILLEGAL.getMessage());
        }
        if (!Strings.isNullOrEmpty(enter.getRoleDesc()) && 100 < enter.getRoleDesc().length()) {
            throw new SesWebRosException(ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.REMARK_IS_NOT_ILLEGAL.getMessage());
        }
        // ??????????????????????????????
        checkRoleName(enter.getRoleName(), enter.getId());
        OpeSysRole role = sysRoleService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(role)) {
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getMessage());
        }
        // ?????????????????????????????????????????????????????????????????????????????????????????????
        if (1 == role.getRoleStatus() && 2 == enter.getRoleStatus()) {
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
        if (StringManaConstant.entityIsNull(role)) {
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getMessage());
        }
        // ????????????????????????????????????????????????????????????
        roleDeleCheck(role.getId());
        sysRoleService.removeById(role.getId());
        // ???????????? ?????????????????????????????????????????????????????????????????????
        rolePermissionService.deleteRoleMeunByRoleId(new IdEnter(enter.getId()));
        // ????????????????????????????????????????????? ??????
        QueryWrapper<OpeSysRoleData> qw = new QueryWrapper<>();
        qw.eq(OpeSysRoleData.COL_ROLE_ID, enter.getId());
        opeSysRoleDataService.remove(qw);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public RoleDetailResult roleDetail(RoleOpEnter enter) {
        OpeSysRole role = sysRoleService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(role)) {
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ROLE_IS_NOT_EXIST.getMessage());
        }
        RoleDetailResult roleDetail = roleServiceMapper.roleDetail(role.getId());
        // ?????????????????????????????????
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_ROLE_ID, role.getId());
        List<OpeSysUserRole> staffList = sysUserRoleService.list(qw);
        roleDetail.setNum(CollectionUtils.isEmpty(staffList) ? 0 : staffList.size());
        return roleDetail;
    }


    @Override
    public PageResult<RoleListResult> roleList(RoleQueryListEnter enter) {
        Set<Long> deptIds = new HashSet<>();
        String key = JedisConstant.LOGIN_ROLE_DATA + enter.getUserId();
        // ?????????????????????????????????????????????????????????????????????
        boolean flag = true;
        if (jedisCluster.exists(key)) {
            flag = false;
            Map<String, String> map = jedisCluster.hgetAll(key);
            String ids = map.get("deptIds");
            if (!Strings.isNullOrEmpty(ids)) {
                for (String s : ids.split(",")) {
                    deptIds.add(Long.parseLong(s));
                }
            }
        }
        int totalRows = roleServiceMapper.totalRows(enter, flag ? null : deptIds, Constant.SYSTEM_ROOT);
        if (NumberUtil.eqZero(totalRows)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<RoleListResult> list = roleServiceMapper.roleList(enter, flag ? null : deptIds, Constant.SYSTEM_ROOT);
        List<Long> roleIds = list.stream().map(RoleListResult::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(roleIds)) {
            // ????????????????????????????????????
            QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
            qw.in(OpeSysUserRole.COL_ROLE_ID, roleIds);
            List<OpeSysUserRole> staffList = sysUserRoleService.list(qw);
            if (CollectionUtils.isNotEmpty(staffList)) {
                // ?????????id??????
                Map<Long, List<OpeSysUserRole>> map = staffList.stream().collect(Collectors.groupingBy(OpeSysUserRole::getRoleId));
                for (RoleListResult result : list) {
                    for (Long roleId : map.keySet()) {
                        if (Objects.equals(result.getId(), roleId)) {
                            result.setNum(map.get(roleId).size());
                        }
                    }
                }
            }
        }
        return PageResult.create(enter, totalRows, list);
    }


    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void disableRole(List<Long> positionIds) {
        QueryWrapper<OpeSysRole> role = new QueryWrapper<>();
        role.in(OpeSysRole.COL_POSITION_ID, positionIds);
        List<OpeSysRole> roles = sysRoleService.list(role);
        if (CollectionUtils.isNotEmpty(roles)) {
            List<OpeSysRole> update = new ArrayList<>();
            for (OpeSysRole sysRole : roles) {
                sysRole.setRoleStatus(2);
                update.add(sysRole);
            }
            sysRoleService.updateBatch(update);
            // ???????????????????????????
            for (OpeSysRole opeSysRole : roles) {
                forbiddenStaff(opeSysRole);
            }
        }
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult roleMenuEdit(RoleMenuEditEnter enter) {
        // ??????????????????????????????????????????????????????
        rolePermissionService.deleteRoleMeunByRoleId(new IdEnter(enter.getRoleId()));
        Set<Long> set = new HashSet<>();
        if (!Strings.isNullOrEmpty(enter.getMenuIds())) {
            for (String s : enter.getMenuIds().split(",")) {
                set.add(Long.parseLong(s));
            }
            set.add(1008301L);
            rolePermissionService.insertRoleMenuPermissions(enter.getRoleId(), set);
        }

        // ????????????id??????????????????????????????,???????????????redis?????????????????????,?????????
        LambdaQueryWrapper<OpeSysUserRole> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeSysUserRole::getRoleId, enter.getRoleId());
        List<OpeSysUserRole> list = sysUserRoleService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            for (OpeSysUserRole model : list) {
                String key = JedisConstant.PERMISSION + model.getUserId();
                Boolean flag = jedisCluster.exists(key);
                if (flag) {
                    jedisCluster.del(key);
                }
            }
        }
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<SalesAreaTressResult> roleCity(RoleOpEnter enter) {
        return null;
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult roleCityEdit(RoleCityEditEnter enter) {
        // ??????????????????????????????????????????
        rolePermissionService.deleteRoleSalesPermissionsByRoleId(new IdEnter(enter.getRoleId()));
        // ???????????????????????????
        rolePermissionService.insertRoleSalesPermissions(enter.getRoleId(), new HashSet<>(enter.getCityIds()));
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public List<RoleDataResult> roleData(PositionIdEnter enter) {
        List<RoleDataResult> resultList = new ArrayList<>();

        List<OpeSysRole> roles = roleServiceMapper.rolesByDeptAndPosition(enter);
        if (CollectionUtils.isNotEmpty(roles)) {
            for (OpeSysRole role : roles) {
                RoleDataResult result = new RoleDataResult();
                result.setRoleId(role.getId());
                result.setRoleName(role.getRoleName());
                resultList.add(result);
            }

        }
        return resultList;
    }


    public void roleDeleCheck(Long roleId) {
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_ROLE_ID, roleId);
        List<OpeSysUserRole> staffList = sysUserRoleService.list(qw);
        if (CollectionUtils.isNotEmpty(staffList)) {
            throw new SesWebRosException(ExceptionCodeEnums.ROLE_IS_NOT_DELETE.getCode(), ExceptionCodeEnums.ROLE_IS_NOT_DELETE.getMessage());
        }
    }


    /**
     * @return
     * @Author Aleks
     * @Description ???????????????????????????????????????????????????????????????????????????????????????????????????
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @Date 2020/9/1 14:36
     * @Param [role]
     **/
    public void forbiddenStaff(OpeSysRole role) {
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.eq(OpeSysStaff.COL_ROLE_ID, role.getId());
        qw.eq(OpeSysStaff.COL_STATUS, 1);
        List<OpeSysStaff> staffList = roleServiceMapper.roleStaffs(role.getId());
        if (CollectionUtils.isEmpty(staffList)) {
            return;
        }
        List<OpeSysStaff> updateList = new ArrayList<>();
        for (OpeSysStaff staff : staffList) {
            List<OpeSysRole> stallRoles = roleServiceMapper.staffRoles(staff.getId(), role.getId());
            if (CollectionUtils.isEmpty(stallRoles)) {
                updateList.add(staff);
            } else {
                // ??????????????????????????????
                Integer num = stallRoles.size();
                if (num == stallRoles.stream().filter(o -> o.getRoleStatus() == 2).collect(Collectors.toList()).size()) {
                    // ??????????????????
                    updateList.add(staff);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            for (OpeSysStaff sysStaff : updateList) {
                sysStaff.setStatus(2);
            }
            opeSysStaffMapper.updateBatch(updateList);
            // ?????????????????????????????????????????????????????????
            employeeService.disAbleUser(updateList.stream().map(OpeSysStaff::getId).collect(Collectors.toList()));
        }
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deleRoleByPosIds(List<Long> posIds) {
        QueryWrapper<OpeSysRole> qw = new QueryWrapper<>();
        qw.in(OpeSysRole.COL_POSITION_ID, posIds);
        sysRoleService.remove(qw);
    }

}
