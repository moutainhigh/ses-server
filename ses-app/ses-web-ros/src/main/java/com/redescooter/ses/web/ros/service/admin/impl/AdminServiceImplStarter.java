package com.redescooter.ses.web.ros.service.admin.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.dept.DeptLevelEnums;
import com.redescooter.ses.api.common.enums.dept.DeptStatusEnums;
import com.redescooter.ses.api.common.enums.employee.EmployeeStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.admin.AdminServiceStarterMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.dm.OpeSysPosition;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.dm.OpeSysRoleDept;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.service.admin.AdminServiceStarter;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysPositionService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleSalesCidyService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:AdminServiceImplStarter
 * @description: AdminServiceImplStarter
 * @author: Alex
 * @Version???1.3
 * @create: 2020/06/01 11:23
 */
//?????? ????????????????????????
@Log4j
@Service
public class AdminServiceImplStarter implements AdminServiceStarter {

    @Autowired
    private AdminServiceStarterMapper adminServiceStarterMapper;

    @Autowired
    private OpeSysDeptService opeSysDeptService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private OpeSysRoleService opeSysRoleService;

    @Autowired
    private OpeSysMenuService opeSysMenuService;

    @Autowired
    private OpeSysRoleSalesCidyService opeSysRoleSalesCidyService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeSysUserService opeSysUserService;

    @Autowired
    private OpeSysRoleDeptService opeSysRoleDeptService;

    @Autowired
    private OpeSysUserRoleService opeSysUserRoleService;

    @Autowired
    private OpeSysRoleMenuService opeSysRoleMenuService;

    @Autowired
    private OpeSysPositionService opeSysPositionService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private CityBaseService cityBaseService;

    /**
     * ??????admin ????????????
     *
     * @return
     */
    @Override
    public Boolean checkAdmin() {
        return adminServiceStarterMapper.checkAdmin(Constant.ADMIN_USER_NAME) == null ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * ??????admin ??????
     *
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveAdmin() {
        if (checkAdmin()) {
            log.info("---------------------admin??????????????????--------------------------");
            return new GeneralResult();
        }
        log.info("---------------------???????????????????????????????????????--------------------------");
        //??????????????????
        OpeSysDept dept = adminServiceStarterMapper.topDept(Constant.DEPT_TREE_ROOT_ID);
        if (StringManaConstant.entityIsNull(dept)) {
            //?????????????????????
            dept = buildOpeSysDept();
            log.info("---------------------ROOT?????????????????????ROOT ??????--------------------------");
        }
        int salt = RandomUtils.nextInt(10000, 99999);
        //??????user
        OpeSysUser opeSysUser = buildOpeSysUser(dept.getId(), salt);
        OpeSysPosition position = createOpeSysPosition(dept, opeSysUser);

        //????????????
        OpeSysStaff opeSysStaff = buildOpeSysStaff(opeSysUser.getId());
        opeSysStaff.setSysUserId(opeSysUser.getId());
        opeSysStaff.setDeptId(dept.getId());
        opeSysStaff.setPositionId(position.getId());
        //??????????????????
        OpeSysUserProfile opeSysUserProfile = buildSysUserProfile(opeSysUser);
        //???????????????admin Role ????????????
        OpeSysRole sysRole = opeSysRoleService.getOne(new LambdaQueryWrapper<OpeSysRole>().eq(OpeSysRole::getRoleName, Constant.ADMIN_USER_NAME).last("limit 1"));
        if (StringManaConstant.entityIsNull(sysRole)) {
            //????????????
            sysRole = saveRole(position.getId());
        }

        //????????????
        opeSysUserService.save(opeSysUser);
        log.info("---------------------??????????????????--------------------------");

        //??????????????????
        opeSysStaffService.save(opeSysStaff);
        log.info("---------------------????????????????????????--------------------------");

        //????????????
        opeSysUserProfileService.save(opeSysUserProfile);
        log.info("---------------------??????????????????--------------------------");

        //??????????????????
        List<OpeSysMenu> sysMenus = opeSysMenuService.list();

        //??????????????????
//        List<CityResult> cityList = cityBaseService.list(new GeneralEnter());

        //??????????????????
//        rolePermissionService.insertRoleMenuPermissions(sysRole.getId(), sysMenus.stream().map(OpeSysMenu::getId).collect(Collectors.toSet()));
//        //??????????????????
//        rolePermissionService.insertRoleDeptPermissions(sysRole.getId(), dept.getId());
//        //????????????????????????
//        rolePermissionService.insertRoleSalesPermissions(sysRole.getId(), cityList.stream().map(CityResult::getId).collect(Collectors.toSet()));
//        //????????????????????????
//        opeSysUserRoleService.save(OpeSysUserRole.builder().userId(opeSysUser.getId()).roleId(sysRole.getId()).build());
        return new GeneralResult();
    }

    private OpeSysPosition createOpeSysPosition(OpeSysDept dept, OpeSysUser opeSysUser) {
        // ????????????
        OpeSysPosition position = new OpeSysPosition();
        position.setId(1000000L);
        position.setPositionName("rede");
        position.setDeptId(dept.getId());
        position.setPositionStatus(DeptStatusEnums.COMPANY.getValue());
        position.setCreatedBy(opeSysUser.getId());
        position.setUpdatedBy(opeSysUser.getId());
        position.setCreatedTime(new Date());
        position.setUpdatedTime(new Date());
        position.setPositionCode("P000001");
        position.setSort(1);
        position.setTenantId(0L);
        position.setSystemRoot(Constant.SYSTEM_ROOT);
        opeSysPositionService.save(position);
        return position;
    }

    /**
     * ??????admin ?????????????????????
     * ??????????????? ?????? bean ????????? ??????????????????
     *
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    @PostConstruct
    public GeneralResult checkAdminDate() {
        //??????????????????
        OpeSysDept dept = adminServiceStarterMapper.topDept(Constant.DEPT_TREE_ROOT_ID);
        if (StringManaConstant.entityIsNull(dept)) {
            //?????????????????????
            dept = buildOpeSysDept();
            log.info("---------------------ROOT?????????????????????ROOT ??????--------------------------");
        }

        OpeSysUser sysUserServiceOne = opeSysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>().eq(OpeSysUser::getLoginName, Constant.ADMIN_USER_NAME).eq(OpeSysUser::getDef1,
                SysUserSourceEnum.SYSTEM.getValue()).last("limit 1"));
        if (StringManaConstant.entityIsNull(sysUserServiceOne)) {
            //??????????????????
            saveAdmin();
            return new GeneralResult();
        }
        log.info("---------------------????????????admin??????????????????--------------------------");
        OpeSysPosition position = opeSysPositionService.getOne(new LambdaQueryWrapper<OpeSysPosition>().eq(OpeSysPosition::getDeptId, dept.getId()).last("limit 1"));
        if (StringManaConstant.entityIsNull(position)) {
            position = createOpeSysPosition(dept, sysUserServiceOne);
        }
        //???????????????admin Role ????????????
        OpeSysRole sysRole = opeSysRoleService.getOne(new LambdaQueryWrapper<OpeSysRole>().eq(OpeSysRole::getRoleName, Constant.ADMIN_USER_NAME).last("limit 1"));
        if (StringManaConstant.entityIsNull(sysRole)) {
            //????????????
            sysRole = saveRole(position.getId());
        }
        //???????????? ??????
        OpeSysStaff opeSysStaff = opeSysStaffService.getOne(new LambdaQueryWrapper<OpeSysStaff>().eq(OpeSysStaff::getSysUserId, sysUserServiceOne.getId()).last("limit 1"));
        if (StringManaConstant.entityIsNull(opeSysStaff)) {
            OpeSysStaff saveOpeSysStaff = buildOpeSysStaff(sysUserServiceOne.getId());
            opeSysStaffService.save(saveOpeSysStaff);
        }

        //??????????????????
        List<OpeSysMenu> opeSysMenus = opeSysMenuService.list();
        //?????? admin ?????? ???Root?????? ??????????????????
        OpeSysRoleDept sysRoleDept = opeSysRoleDeptService.getOne(new LambdaQueryWrapper<OpeSysRoleDept>().eq(OpeSysRoleDept::getDeptId, dept.getId()).eq(OpeSysRoleDept::getRoleId, sysRole.getId()).last("limit 1"));
        if (StringManaConstant.entityIsNull(sysRoleDept)) {
            opeSysRoleDeptService.save(OpeSysRoleDept.builder().deptId(dept.getId()).roleId(sysRole.getId()).build());
        }
//        //??????role ???????????? ??????????????????
//        List<OpeSysRoleMenu> sysRoleMenuList = opeSysRoleMenuService.list(new LambdaQueryWrapper<OpeSysRoleMenu>().eq(OpeSysRoleMenu::getRoleId, sysRole.getId()));
//        if (sysRoleMenuList.size() != opeSysMenus.size()) {
//
//            List<OpeSysRoleMenu> saveOpeSysRoleMenu = new ArrayList<>();
//            opeSysMenus.forEach(item -> {
//                sysRoleMenuList.forEach(role -> {
//                    if (!item.getId().equals(role.getMenuId())) {
//                        saveOpeSysRoleMenu.add(OpeSysRoleMenu.builder().menuId(item.getId()).roleId(sysRoleDept.getRoleId()).build());
//                    }
//                });
//            });
//            //?????? ??????????????????
//            opeSysRoleMenuService.saveBatch(saveOpeSysRoleMenu);
//        }
        //?????? ???????????????????????????????????????
        OpeSysUserRole sysUserRole = opeSysUserRoleService.getOne(new LambdaQueryWrapper<OpeSysUserRole>().eq(OpeSysUserRole::getRoleId, sysRole.getId()).eq(OpeSysUserRole::getUserId,
                sysUserServiceOne.getId()).last("limit 1"));
        if (StringManaConstant.entityIsNull(sysUserRole)) {
            opeSysUserRoleService.save(OpeSysUserRole.builder().roleId(sysRole.getId()).userId(sysUserServiceOne.getId()).build());
        }
        log.info("---------------------????????????admin??????????????????--------------------------");

        return new GeneralResult();
    }


    private OpeSysRole saveRole(Long positionId) {
        OpeSysRole sysRole = OpeSysRole.builder()
                .id(1000000L)
                .dr(0)
                .tenantId(0L)
                .roleName(Constant.ADMIN_USER_NAME)
                .roleCode(null)
                .roleDesc(Constant.ADMIN_USER_NAME)
                .createdBy(0L)
                .createTime(new Date())
                .updatedBy(0L)
                .updateTime(new Date())
                .roleCode("R000001")
                .positionId(positionId)
                .systemRoot(Constant.SYSTEM_ROOT)
                .build();
        opeSysRoleService.save(sysRole);
        return sysRole;
    }

    private OpeSysUserProfile buildSysUserProfile(OpeSysUser opeSysUser) {
        OpeSysUserProfile opeSysUserProfile = OpeSysUserProfile.builder()
                .id(1000000L)
                .dr(0)
                .repairShopId(0L)
                .sysUserId(opeSysUser.getId())
                .picture(null)
                .firstName("rede")
                .lastName("rede")
                .fullName("rede rede")
                .email("rede@redescooter.com")
                .countryCode("33")
                .telNumber(String.valueOf(RandomUtils.nextLong(10000L, 99999L)))
                .gender(null)
                .birthday(RandomUtil.randomDate(new Date(), DateField.HOUR, 10, 100))
                .placeBirth(null)
                .addressBureau("1000000")
                .address(null)
                .addressCountryCode(null)
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .build();
        return opeSysUserProfile;
    }

    private OpeSysStaff buildOpeSysStaff(Long userId) {
        return OpeSysStaff.builder()
                .id(userId)
                .dr(0)
                .status(Integer.parseInt(EmployeeStatusEnums.IN_SERVICE.getValue()))
                .sysUserId(userId)
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .firstName("rede")
                .lastName("rede")
                .fullName("rede rede")
                .email("rede@redescooter.com")
                .countryCode("33")
                .code("S000001")
                .openAccount("1")
                .systemRoot(Constant.SYSTEM_ROOT)
                .build();
    }

    private OpeSysUser buildOpeSysUser(Long deptId, int salt) {
        return OpeSysUser.builder()
                .id(1000000L)
                .dr(0)
                .deptId(deptId)
                .orgStaffId(0L)
                .appId(AppIDEnums.SES_ROS.getValue())
                .systemId(AccountTypeEnums.WEB_ROS.getSystemId())
                .password(DigestUtils.md5Hex(Constant.DEFAULT_PASSWORD + salt))
                .salt(String.valueOf(salt))
                .status(SysUserStatusEnum.NORMAL.getCode())
                .loginName(Constant.ADMIN_USER_NAME)
                .lastLoginTime(null)
                .lastLoginIp(null)
                .lastLoginToken(null)
                .activationTime(new Date())
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .def1(SysUserSourceEnum.SYSTEM.getValue())
                .build();
    }

    private OpeSysDept buildOpeSysDept() {
        OpeSysDept dept;
        dept = OpeSysDept.builder()
                .id(1000000L)
                .dr(0)
                .code("D000001")
                .pId(Constant.DEPT_TREE_ROOT_ID)
                .tenantId(0L)
                .principal(0L)
                .level(Integer.valueOf(DeptLevelEnums.DEPARTMENT.getValue()))
                .name("ROOT")
                .description(null)
                .sort(1)
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .systemRoot(Constant.SYSTEM_ROOT)
                .build();
        opeSysDeptService.save(dept);
        return dept;
    }
}
