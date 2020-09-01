package com.redescooter.ses.web.ros.service.admin.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.dept.DeptLevelEnums;
import com.redescooter.ses.api.common.enums.employee.EmployeeStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.admin.AdminServiceStarterMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.dm.OpeSysRoleDept;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.service.admin.AdminServiceStarter;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptRelationService;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleMenuService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleSalesCidyService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.base.impl.OpeSysUserRoleServiceImpl;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import com.redescooter.ses.web.ros.service.sys.SalesAreaService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName:AdminServiceImplStarter
 * @description: AdminServiceImplStarter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/01 11:23
 */
//不传 默认为最低优先级
@Log4j
@Service
public class AdminServiceImplStarter implements AdminServiceStarter {

    @Autowired
    private AdminServiceStarterMapper adminServiceStarterMapper;

    @Autowired
    private OpeSysDeptService opeSysDeptService;

    @Reference
    private IdAppService idAppService;

    @Reference
    private CityBaseService cityBaseService;

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

    /**
     * 检查admin 是否存在
     *
     * @return
     */
    @Override
    public Boolean checkAdmin() {
        return adminServiceStarterMapper.checkAdmin(Constant.ADMIN_USER_NAME) == null ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * 保存admin 用户
     *
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveAdmin() {
        if (checkAdmin()) {
            log.info("---------------------admin存在无需保存--------------------------");
            return new GeneralResult();
        }

        //查询顶级部门
        OpeSysDept dept = adminServiceStarterMapper.topDept(Constant.DEPT_TREE_ROOT_ID);
        if (dept == null) {
            //保存部门根节点
            dept = buildOpeSysDept();
            log.info("---------------------ROOT部门不存在保存ROOT 部门--------------------------");
        }
        int salt = RandomUtils.nextInt(10000, 99999);
        //保存user
        OpeSysUser opeSysUser = buildOpeSysUser(dept.getId(), salt);
        //保存员工
        OpeSysStaff opeSysStaff = buildOpeSysStaff(opeSysUser.getId());
        opeSysStaff.setSysUserId(opeSysUser.getId());
        //创建个人信息
        OpeSysUserProfile opeSysUserProfile = buildSysUserProfile(opeSysUser);
        //查询是否有admin Role 没有的话
        OpeSysRole sysRole = opeSysRoleService.getOne(new LambdaQueryWrapper<OpeSysRole>().eq(OpeSysRole::getRoleName, Constant.ADMIN_USER_NAME).last("limit 1"));
        if (sysRole == null) {
            //创建角色
            sysRole = saveRole();
        }

        //账户保存
        opeSysUserService.save(opeSysUser);
        log.info("---------------------账户保存成功--------------------------");

        //个人信息保存
        opeSysStaffService.save(opeSysStaff);
        log.info("---------------------个人信息保存成功--------------------------");

        //员工保存
        opeSysUserProfileService.save(opeSysUserProfile);
        log.info("---------------------员工保存成功--------------------------");

        //查询所有菜单
        List<OpeSysMenu> sysMenus = opeSysMenuService.list();

        //查询销售区域
        List<CityResult> cityList = cityBaseService.list(new GeneralEnter());

        //保存角色权限
        rolePermissionService.insertRoleMenuPermissions(sysRole.getId(), sysMenus.stream().map(OpeSysMenu::getId).collect(Collectors.toSet()));
        //保存部门角色
        rolePermissionService.insertRoleDeptPermissions(sysRole.getId(), dept.getId());
        //绑定员工销售区域
        rolePermissionService.insertRoleSalesPermissions(sysRole.getId(), cityList.stream().map(CityResult::getId).collect(Collectors.toSet()));
        //员工角色绑定关系
        opeSysUserRoleService.save(OpeSysUserRole.builder().userId(opeSysUser.getId()).roleId(sysRole.getId()).build());
        return new GeneralResult();
    }

    /**
     * 校验admin 的数据是否完整
     * 在服务启动 完成 bean 注册后 会调用该方法
     *
     * @return
     */
    @Transactional
    @Override
    //@PostConstruct
    public GeneralResult checkAdminDate() {
        //查询顶级部门
        OpeSysDept dept = adminServiceStarterMapper.topDept(Constant.DEPT_TREE_ROOT_ID);
        if (dept == null) {
            //保存部门根节点
            dept = buildOpeSysDept();
            log.info("---------------------ROOT部门不存在保存ROOT 部门--------------------------");
        }

        OpeSysUser sysUserServiceOne = opeSysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>().eq(OpeSysUser::getLoginName, Constant.ADMIN_USER_NAME).eq(OpeSysUser::getDef1,
        SysUserSourceEnum.SYSTEM.getValue()).last("limit 1"));
        if (sysUserServiceOne == null) {
            //账号信息创建
            saveAdmin();
            return new GeneralResult();
        }
        //查询是否有admin Role 没有的话
        OpeSysRole sysRole = opeSysRoleService.getOne(new LambdaQueryWrapper<OpeSysRole>().eq(OpeSysRole::getRoleName, Constant.ADMIN_USER_NAME).last("limit 1"));
        if (sysRole == null) {
            //创建角色
            sysRole = saveRole();
        }
        //查询员工 是否
        OpeSysStaff opeSysStaff = opeSysStaffService.getOne(new LambdaQueryWrapper<OpeSysStaff>().eq(OpeSysStaff::getSysUserId,sysUserServiceOne.getId()).last("limit 1"));
        if (opeSysStaff == null) {
            OpeSysStaff saveOpeSysStaff = buildOpeSysStaff(sysUserServiceOne.getId());
            opeSysStaffService.save(saveOpeSysStaff);
        }

        //查询所有页面
        List<OpeSysMenu> opeSysMenus = opeSysMenuService.list();
        //校验 admin 是否 和Root部门 存在绑定关系
        OpeSysRoleDept sysRoleDept = opeSysRoleDeptService.getOne(new LambdaQueryWrapper<OpeSysRoleDept>().eq(OpeSysRoleDept::getDeptId, dept.getId()).eq(OpeSysRoleDept::getRoleId, sysRole.getId()).last("limit 1"));
        if (sysRoleDept == null) {
            opeSysRoleDeptService.save(OpeSysRoleDept.builder().deptId(dept.getId()).roleId(sysRole.getId()).build());
        }
        //校验role 是否具有 所有页面权限
        List<OpeSysRoleMenu> sysRoleMenuList = opeSysRoleMenuService.list(new LambdaQueryWrapper<OpeSysRoleMenu>().eq(OpeSysRoleMenu::getRoleId, sysRole.getId()));
        if (sysRoleMenuList.size() != opeSysMenus.size()) {

            List<OpeSysRoleMenu> saveOpeSysRoleMenu = new ArrayList<>();
            opeSysMenus.forEach(item -> {
                sysRoleMenuList.forEach(role -> {
                    if (!item.getId().equals(role.getMenuId())) {
                        saveOpeSysRoleMenu.add(OpeSysRoleMenu.builder().menuId(item.getId()).roleId(sysRoleDept.getRoleId()).build());
                    }
                });
            });
            //更新 页面权限信息
            opeSysRoleMenuService.saveBatch(saveOpeSysRoleMenu);
        }
        //校验 员工和角色是否具有绑定关系
        OpeSysUserRole sysUserRole = opeSysUserRoleService.getOne(new LambdaQueryWrapper<OpeSysUserRole>().eq(OpeSysUserRole::getRoleId, sysRole.getId()).eq(OpeSysUserRole::getUserId,
                sysUserServiceOne.getId()).last("limit 1"));
        if (sysUserRole == null) {
            opeSysUserRoleService.save(OpeSysUserRole.builder().roleId(sysRole.getId()).userId(sysUserServiceOne.getId()).build());
        }

        return new GeneralResult();
    }


    private OpeSysRole saveRole() {
        OpeSysRole sysRole;
        sysRole = OpeSysRole.builder()
                .id(idAppService.getId(SequenceName.OPE_SYS_ROLE))
                .dr(0)
                .tenantId(0L)
                .roleName(Constant.ADMIN_USER_NAME)
                .roleCode(null)
                .roleDesc(Constant.ADMIN_USER_NAME)
                .createdBy(0L)
                .createTime(new Date())
                .updatedBy(0L)
                .updateTime(new Date())
                .build();
        opeSysRoleService.save(sysRole);
        return sysRole;
    }

    private OpeSysUserProfile buildSysUserProfile(OpeSysUser opeSysUser) {
        OpeSysUserProfile opeSysUserProfile = OpeSysUserProfile.builder()
                .id(idAppService.getId(SequenceName.OPE_SYS_USER_PROFILE))
                .dr(0)
                .repairShopId(0L)
                .sysUserId(opeSysUser.getId())
                .picture(null)
                .firstName("ROOT")
                .lastName("ROOT")
                .fullName("ROOT ROOT")
                .email("root@redescooter.com")
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
                .id(idAppService.getId(SequenceName.OPE_SYS_STAFF))
                .dr(0)
                .status(Integer.parseInt(EmployeeStatusEnums.IN_SERVICE.getValue()))
                .sysUserId(userId)
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .build();
    }

    private OpeSysUser buildOpeSysUser(Long deptId, int salt) {
        return OpeSysUser.builder()
                .id(idAppService.getId(SequenceName.OPE_SYS_USER))
                .dr(0)
                .deptId(deptId)
                .orgStaffId(0L)
                .appId(AppIDEnums.SES_ROS.getValue())
                .systemId(AccountTypeEnums.WEB_ROS.getSystemId())
                .password(DigestUtils.md5Hex(Constant.DEFAULT_PASSWORD + salt))
                .salt(String.valueOf(salt))
                .status(SysUserStatusEnum.NORMAL.getValue())
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
                .id(idAppService.getId(SequenceName.OPE_SYS_DEPT))
                .dr(0)
                .pId(Constant.DEPT_TREE_ROOT_ID)
                .tenantId(0L)
                .principal(0L)
                .level(Integer.valueOf(DeptLevelEnums.COMPANY.getValue()))
                .name("ROOT")
                .description(null)
                .sort(1)
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .build();
        opeSysDeptService.save(dept);
        return dept;
    }
}
