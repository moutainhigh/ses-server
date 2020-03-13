package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.organization.EmployeeDeptTypeEnums;
import com.redescooter.ses.api.common.enums.organization.EmployeeStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.StringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.sys.EmployeeServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.sys.EmployeeService;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeDeptResult;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeListEnter;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeListResult;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeResult;
import com.redescooter.ses.web.ros.vo.sys.employee.SaveEmployeeEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName:EmployeeService
 * @description: EmployeeService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/09 11:27
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeServiceMapper employeeServiceMapper;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeSysUserRoleService opeSysUserRoleService;

    @Autowired
    private OpeSysUserService opeSysUserService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private OpeSysDeptService opeSysDeptService;

    @Autowired
    private OpeSysRoleService opeSysRoleService;

    @Autowired
    private JedisService jedisService;

    @Reference
    private IdAppService idAppService;

    /**
     * 员工列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<EmployeeListResult> employeeList(EmployeeListEnter enter) {
        // 拿到所有部门
        List<EmployeeListResult> deptList = employeeServiceMapper.deptList(enter);
        if (CollectionUtils.isEmpty(deptList)) {
            return new ArrayList<>();
        }
        List<Long> deptIds = new ArrayList<>();
        deptList.forEach(item -> {
            deptIds.add(item.getDeptId());
        });
        // 查出所有员工 进行 部门分组 员工 证件信息没有封装
        List<EmployeeResult> employeeList = employeeServiceMapper.employeeList(enter);
        //剔除 admin
        employeeList = employeeList.stream().filter(item -> StringUtils.equals(Constant.ADMIN_USER_NAME, item.getEmail()) == false).collect(Collectors.toList());
        for (EmployeeListResult dept : deptList) {
            List<EmployeeResult> employeeResultList = new ArrayList<>();
            if (CollectionUtils.isEmpty(employeeList)) {
                dept.setEmployeeResult(new ArrayList<>());
                continue;
            }
            employeeList.forEach(item -> {
                if (dept.getDeptId().equals(item.getDeptId())) {
                    employeeResultList.add(item);
                }
            });
            dept.setEmployeeResult(employeeResultList);
        }
        return deptList;

    }

    /**
     * 员工详情
     *
     * @param enter
     * @return
     */
    @Override
    public EmployeeResult employeeDetail(IdEnter enter) {
        //todo 缺少公司名字
        EmployeeResult employeeResult = employeeServiceMapper.employeeDetail(enter);

        // 返回公司
        // 查询所有部门
        List<OpeSysDept> deptList = opeSysDeptService.list();
        OpeSysDept dept = null;
        for (OpeSysDept item : deptList) {
            if (item.getId().equals(employeeResult.getDeptId())) {
                dept = item;
                break;

            }
        }
        OpeSysDept company = findCompany(deptList, dept);
        employeeResult.setCompanyId(company.getId());
        employeeResult.setCompanyName(company.getName());
        return employeeResult;
    }


    private OpeSysDept findCompany(List<OpeSysDept> deptList, OpeSysDept child) {
        for (OpeSysDept item : deptList) {
            if (child.getPId() == 0) {
                return child;
            }
            if (item.getId().equals(child.getPId())) {
                child = item;
                findCompany(deptList, child);
            }
        }
        return null;
    }

    /**
     * 保存员工
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveEmployee(SaveEmployeeEnter enter) {
        // 部门、职位、办公区域、邮箱校验
        checkSaveEmployeeParameter(enter);

        //邮箱去重校验
        QueryWrapper<OpeSysUser> checkSysUserQueryWrapper = new QueryWrapper<>();
        checkSysUserQueryWrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getEmail());
        OpeSysUser checkMail = opeSysUserService.getOne(checkSysUserQueryWrapper);

        // 构建employee 对象
        OpeSysUserProfile opeSysUserProfile = buildOpeSysUserProfile(enter);
        OpeSysUser opeSysUser = null;
        OpeSysUserRole opeSysUserRole = null;
        if (enter.getId() == null || enter.getId() == 0) {
            // 创建
            if (checkMail != null) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
            }
            // 构建个人账户
            opeSysUser = saveSysUserSingle(enter);
            // 构建个人信息
            opeSysUserProfile.setSysUserId(opeSysUser.getId());
            opeSysUserProfile.setId(idAppService.getId(SequenceName.OPE_SYS_USER_PROFILE));
            opeSysUserProfile.setCreatedBy(enter.getUserId());
            opeSysUserProfile.setCreatedTime(new Date());

            // 构建用户角色关系
            opeSysUserRole = new OpeSysUserRole();
            opeSysUserRole.setUserId(opeSysUser.getId());
            opeSysUserRole.setRoleId(enter.getPositionId());
            //构建员工信息
            saveEmployeeSingle(enter, opeSysUser.getId());

        } else {
            // 修改
            // 查询用户信息
            QueryWrapper<OpeSysUser> opeSysUserQueryWrapper = new QueryWrapper<>();
            opeSysUserQueryWrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getEmail());
            opeSysUserQueryWrapper.eq(OpeSysUser.COL_DR, 0);
            opeSysUser = opeSysUserService.getOne(opeSysUserQueryWrapper);
            if (opeSysUser == null) {
                throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            // 邮件过滤
            if (!checkMail.getLoginName().equals(enter.getEmail()) && checkMail != null) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
            }
            // 个人信息更新
            opeSysUserProfile.setSysUserId(opeSysUser.getId());
            // 用户角色关系没改变不更新
            QueryWrapper<OpeSysUserRole> opeSysUserRoleQueryWrapper = new QueryWrapper<>();
            opeSysUserRoleQueryWrapper.eq(OpeSysUserRole.COL_USER_ID, opeSysUser.getId());
            opeSysUserRole = opeSysUserRoleService.getOne(opeSysUserRoleQueryWrapper);
            if (opeSysUserRole == null) {
                throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_BING_POSITION.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_BING_POSITION.getMessage());
            }
            if (!opeSysUserRole.getRoleId().equals(enter.getPositionId())) {
                opeSysUserRole.setRoleId(enter.getPositionId());
            } else {
                opeSysUserRole = null;
            }
            //部门信息没有改变 不进行更新
            if (!opeSysUser.getDeptId().equals(enter.getDeptId())) {
                opeSysUser.setDeptId(enter.getDeptId());
                opeSysUser.setCreatedBy(enter.getUserId());
                opeSysUser.setCreatedTime(new Date());
            } else {
                opeSysUser = null;
            }

        }
        if (opeSysUser != null) {
            opeSysUserRoleService.saveOrUpdate(opeSysUserRole);
        }
        if (opeSysUser != null) {
            opeSysUserService.saveOrUpdate(opeSysUser);
        }
        opeSysUserProfileService.saveOrUpdate(opeSysUserProfile);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 员工部门列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<EmployeeDeptResult> employeeDeptList(EmployeeDeptEnter enter) {
        // 类型过滤
        if (EmployeeDeptTypeEnums.checkValue(enter.getType()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        List<Long> ids = new ArrayList<>();
        if (enter.getBizId() != null && enter.getBizId() != 0) {
            ids.add(enter.getBizId());
        }
        List<EmployeeDeptResult> result = new ArrayList<>();
        List<Long> childIdList = null;
        switch (EmployeeDeptTypeEnums.getEnumByValue(enter.getType())) {
            case DEPT:
                // 根据父级id 查询所有子集id
                Boolean hasNextBoolean = Boolean.TRUE;
                do {
                    childIdList = employeeServiceMapper.getEmployeeDeptChildList(enter.getTenantId(), CollectionUtils.isNotEmpty(ids) == true ? ids.get(ids.size() - 1) : null);
                    if (CollectionUtils.isEmpty(childIdList)) {
                        hasNextBoolean = Boolean.FALSE;
                    }
                    ids.addAll(childIdList);
                } while (hasNextBoolean);
                // 根据id 查询部门信息
                result = employeeServiceMapper.getEmployeeDeptList(enter.getTenantId(), ids);
                // 移除当前父级
                result.removeIf(item -> item.getId().equals(enter.getBizId()));
                break;
            case POSITION:
                result = employeeServiceMapper.getEmployeePositionList(enter.getTenantId(), ids);
                break;
            case OFFICEAREA:
//                result = employeeServiceMapper.getEmployeeOfficeareaList(enter.getTenantId());
                result.add(EmployeeDeptResult.builder()
                        .id(1000000L)
                        .name("Paris")
                        .build());
                result.add(EmployeeDeptResult.builder()
                        .id(1000001L)
                        .name("ShangHai")
                        .build());
                break;
            case COMPANY:
                result = employeeServiceMapper.getEmployeeCompanyList(enter.getTenantId(), null);
                break;
            default:
                return new ArrayList<>();
        }
        return result;
    }

    /**
     * 删除员工
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult trushEmployee(IdEnter enter) {
        // 验证员工是否存在
        OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getId());
        if (opeSysStaff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeSysStaff.getStatus(), EmployeeStatusEnums.IN_SERVICE.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        // 清空token
        OpeSysUser opeSysUser = opeSysUserService.getById(opeSysStaff.getSysUserId());
        //todo暂时不允许对admin 进行离职操作
        if (opeSysUser.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            return null;
        }
        if (StringUtils.isNotBlank(opeSysUser.getLastLoginToken())) {
            jedisService.delKey(opeSysUser.getLastLoginToken());
        }
        // 删除员工
        opeSysStaffService.removeById(opeSysStaff);
        // 删除员工账户
        opeSysUserService.removeById(opeSysStaff.getSysUserId());
        // 删除员工信息
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, 0);
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_SYS_USER_ID, opeSysStaff.getSysUserId());
        opeSysUserProfileService.remove(opeSysUserProfileQueryWrapper);
        // 删除角色员工关联关系
        QueryWrapper<OpeSysUserRole> opeSysUserRoleQueryWrapper = new QueryWrapper<>();
        opeSysUserRoleQueryWrapper.eq(OpeSysUserRole.COL_USER_ID, opeSysStaff.getSysUserId());
        opeSysUserRoleService.remove(opeSysUserRoleQueryWrapper);
        return new GeneralResult(enter.getRequestId());
    }

    private void saveEmployeeSingle(SaveEmployeeEnter enter, Long userId) {
        OpeSysStaff opeSysStaff = new OpeSysStaff();
        opeSysStaff.setId(idAppService.getId(SequenceName.OPE_SYS_STAFF));
        opeSysStaff.setDr(0);
        opeSysStaff.setStatus(EmployeeStatusEnums.IN_SERVICE.getValue());
        opeSysStaff.setSysUserId(userId);
        opeSysStaff.setCreatedBy(enter.getUserId());
        opeSysStaff.setCreatedTime(new Date());
        opeSysStaff.setUpdatedBy(enter.getUserId());
        opeSysStaff.setUpdatedTime(new Date());
        opeSysStaffService.save(opeSysStaff);
    }

    private OpeSysUser saveSysUserSingle(SaveEmployeeEnter enter) {
        OpeSysUser opeSysUser = new OpeSysUser();
        opeSysUser.setId(idAppService.getId(SequenceName.OPE_SYS_USER));
        opeSysUser.setDr(0);
        opeSysUser.setStatus(SysUserStatusEnum.NORMAL.getCode());
        opeSysUser.setAppId(AppIDEnums.SES_ROS.getValue());
        opeSysUser.setSystemId(AppIDEnums.SES_ROS.getSystemId());
        opeSysUser.setDeptId(enter.getDeptId());
        opeSysUser.setLoginName(enter.getEmail());
        opeSysUser.setSalt(String.valueOf(RandomUtils.nextInt(10000, 99999)));
        opeSysUser.setPassword(DigestUtils.md5Hex(Constant.DEFAULT_PASSWORD + opeSysUser.getSalt()));
        opeSysUser.setLastLoginIp(null);
        opeSysUser.setLastLoginTime(null);
        opeSysUser.setLastLoginToken(null);
        opeSysUser.setActivationTime(new Date());
        opeSysUser.setExpireDate(null);
        opeSysUser.setUpdatedBy(enter.getUserId());
        opeSysUser.setUpdatedTime(new Date());
        opeSysUser.setCreatedBy(enter.getUserId());
        opeSysUser.setCreatedTime(new Date());
        return opeSysUser;
    }

    private OpeSysUserProfile buildOpeSysUserProfile(SaveEmployeeEnter enter) {
        OpeSysUserProfile opeSysUserProfile = new OpeSysUserProfile();
        opeSysUserProfile.setDr(0);
        opeSysUserProfile.setRepairShopId(0L);
        opeSysUserProfile.setPicture(StringUtils.isEmpty(enter.getAvatar()) == true ? null : enter.getAvatar());
        opeSysUserProfile.setFirstName(enter.getEmployeeFirstName());
        opeSysUserProfile.setLastName(enter.getEmployeeLastName());
        opeSysUserProfile.setFullName(new StringBuilder(enter.getEmployeeFirstName()).append(" ").append(enter.getEmployeeLastName()).toString());
        opeSysUserProfile.setEmail(enter.getEmail());
        opeSysUserProfile.setCountryCode(enter.getTelCountryCode());
        opeSysUserProfile.setTelNumber(enter.getTelephone());
        opeSysUserProfile.setGender(null);
        opeSysUserProfile.setBirthday(enter.getBirthday());
        opeSysUserProfile.setPlaceBirth(null);
        opeSysUserProfile.setAddressCountryCode(enter.getAddressCountryCode());
        opeSysUserProfile.setAddress(enter.getAddress());
        opeSysUserProfile.setCertificateType(enter.getCertificateType());
        opeSysUserProfile.setCertificateNegativeAnnex(StringUtils.isEmpty(enter.getNegativePicture()) == true ? null : enter.getNegativePicture());
        opeSysUserProfile.setCertificatePositiveAnnex(enter.getPositivePicture());
        opeSysUserProfile.setJoinDate(new Date());
        opeSysUserProfile.setUpdatedBy(enter.getUserId());
        opeSysUserProfile.setUpdatedTime(new Date());
        return opeSysUserProfile;
    }

    private void checkSaveEmployeeParameter(SaveEmployeeEnter enter) {
        if (opeSysDeptService.getById(enter.getDeptId()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }
        if (opeSysRoleService.getById(enter.getPositionId()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        // 办公区域校验
        if (true) {

        }
        //邮箱过滤
        if (!enter.getEmail().contains("@")) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
    }
}
