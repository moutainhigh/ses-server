package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.dept.DeptLevelEnums;
import com.redescooter.ses.api.common.enums.employee.AddressBureauEnums;
import com.redescooter.ses.api.common.enums.employee.EmployeeDeptTypeEnums;
import com.redescooter.ses.api.common.enums.employee.EmployeeStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
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
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.sys.employee.DeptEmployeeListResult;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeDeptResult;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeListEnter;
import com.redescooter.ses.web.ros.vo.sys.employee.EmployeeResult;
import com.redescooter.ses.web.ros.vo.sys.employee.SaveEmployeeEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service(value = "employeeService")
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

    @Autowired
    private SysDeptService sysDeptService;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 员工列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeptEmployeeListResult> employeeList(EmployeeListEnter enter) {

        if (NumberUtil.notNullAndGtFifty(enter.getKeyword())) {
            return new ArrayList<>();
        }
        // 拿到所有部门
        List<DeptEmployeeListResult> deptList = employeeServiceMapper.deptList(enter);
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
        employeeList = employeeList.stream().filter(item -> SesStringUtils.equals(Constant.ADMIN_USER_NAME, item.getEmail()) == false).collect(Collectors.toList());
        for (DeptEmployeeListResult dept : deptList) {
            List<EmployeeResult> employeeResultList = new ArrayList<>();
            if (CollectionUtils.isEmpty(employeeList)) {
                dept.setEmployeeResult(new ArrayList<>());
                continue;
            }
            employeeList.forEach(item -> {
                //解析出每个办公区域信息
                String addressBureau = null;
                if (StringManaConstant.entityIsNotNull(item.getAddressBureauId()) || 0 != item.getAddressBureauId()) {
                    addressBureau = AddressBureauEnums.getEnumByCode(String.valueOf(item.getAddressBureauId())).getMessage();
                }
                item.setAddressBureau(addressBureau);
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
        EmployeeResult employeeResult = employeeServiceMapper.employeeDetail(enter);
        if (StringManaConstant.entityIsNull(employeeResult)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }


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
        //解析出每个办公区域信息
        String addressBureau = null;
        if (StringManaConstant.entityIsNotNull(employeeResult.getAddressBureauId()) || 0 != employeeResult.getAddressBureauId()) {
            addressBureau = AddressBureauEnums.getEnumByCode(String.valueOf(employeeResult.getAddressBureauId())).getMessage();
        }
        employeeResult.setAddressBureau(addressBureau);
        DeptTreeReslt deptTreeReslt = sysDeptService.topDeptartment(new IdEnter(employeeResult.getDeptId()), DeptLevelEnums.COMPANY.getValue());
        if(StringManaConstant.entityIsNull(deptTreeReslt)){
            throw new SesWebRosException(ExceptionCodeEnums.TOP_DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TOP_DEPT_IS_NOT_EXIST.getMessage());
        }
        employeeResult.setCompanyId(deptTreeReslt == null?0L:deptTreeReslt.getId());
        employeeResult.setCompanyName(deptTreeReslt.getName());
        return employeeResult;
    }

    /**
     * 保存员工
     *
     * @param saveEmployeeEnter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveEmployee(SaveEmployeeEnter saveEmployeeEnter) {

        //employeeListEnter参数值去空格
        SaveEmployeeEnter enter = SesStringUtils.objStringTrim(saveEmployeeEnter);
        // 部门、职位、办公区域、邮箱校验
        checkSaveEmployeeParameter(enter);
        //员工名称首位大写
        String firstName = SesStringUtils.upperCaseString(enter.getEmployeeFirstName());
        String lastName = SesStringUtils.upperCaseString(enter.getEmployeeLastName());
        enter.setEmployeeFirstName(firstName);
        enter.setEmployeeLastName(lastName);
        //邮箱去重校验
        QueryWrapper<OpeSysUser> checkSysUserQueryWrapper = new QueryWrapper<>();
        checkSysUserQueryWrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getEmail());
        checkSysUserQueryWrapper.eq(OpeSysUser.COL_DEF1, SysUserSourceEnum.SYSTEM.getValue());
        checkSysUserQueryWrapper.last("limit 1");
        OpeSysUser checkMail = opeSysUserService.getOne(checkSysUserQueryWrapper);

        // 构建employee 对象
        OpeSysUserProfile opeSysUserProfile = buildOpeSysUserProfile(enter);
        OpeSysUser opeSysUser = null;
        OpeSysUserRole opeSysUserRole = null;
        if (StringManaConstant.entityIsNull(enter.getId()) || 0 == enter.getId()) {
            if (!enter.getEmail().contains("@") || NumberUtil.ltTwoOrGtFifty(enter.getEmail().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
            }
            // 创建
            if (StringManaConstant.entityIsNotNull(checkMail)) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
            }
            //邮箱过滤

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
            //发送邮件
          createEmployeeEmail(enter);
        } else {
            // 修改
            // 查询用户信息
            QueryWrapper<OpeSysUser> opeSysUserQueryWrapper = new QueryWrapper<>();
            opeSysUserQueryWrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getEmail());
            opeSysUserQueryWrapper.eq(OpeSysUser.COL_DR, Constant.DR_FALSE);
            opeSysUserQueryWrapper.eq(OpeSysUser.COL_DEF1, SysUserSourceEnum.SYSTEM.getValue());
            opeSysUserQueryWrapper.last("limit 1");
            opeSysUser = opeSysUserService.getOne(opeSysUserQueryWrapper);
            if (StringManaConstant.entityIsNull(opeSysUser)) {
                throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            // 邮件过滤
            if (!checkMail.getLoginName().equals(enter.getEmail()) && StringManaConstant.entityIsNotNull(checkMail)) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
            }
            QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
            opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_SYS_USER_ID, opeSysUser.getId());
            opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, Constant.DR_FALSE);
            opeSysUserProfileQueryWrapper.last("limit 1");
            OpeSysUserProfile sysUserProfile = opeSysUserProfileService.getOne(opeSysUserProfileQueryWrapper);
            // 个人信息更新
            opeSysUserProfile.setId(sysUserProfile.getId());
            opeSysUserProfile.setSysUserId(opeSysUser.getId());
            // 用户角色关系没改变不更新
            QueryWrapper<OpeSysUserRole> opeSysUserRoleQueryWrapper = new QueryWrapper<>();
            opeSysUserRoleQueryWrapper.eq(OpeSysUserRole.COL_USER_ID, opeSysUser.getId());
            opeSysUserRoleQueryWrapper.last("limit 1");
            opeSysUserRole = opeSysUserRoleService.getOne(opeSysUserRoleQueryWrapper);
            if (StringManaConstant.entityIsNull(opeSysUserRole)) {
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
        if (StringManaConstant.entityIsNotNull(opeSysUserRole)) {
            opeSysUserRoleService.saveOrUpdate(opeSysUserRole);
        }
        if (StringManaConstant.entityIsNotNull(opeSysUser)) {
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
        if (StringManaConstant.entityIsNull(EmployeeDeptTypeEnums.checkValue(enter.getType()))) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        List<Long> ids = new ArrayList<>();
        if (StringManaConstant.entityIsNotNull(enter.getBizId()) && 0 != enter.getBizId()) {
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
                result = employeeServiceMapper.getEmployeePositionList(enter.getTenantId(), ids, Constant.ADMIN_USER_NAME);
                break;
            case OFFICEAREA:
                for (AddressBureauEnums item : AddressBureauEnums.values()) {
                    result.add(EmployeeDeptResult.builder().id(Long.valueOf(item.getCode())).name(item.getMessage()).build());
                }
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
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult trushEmployee(IdEnter enter) {
        // 验证员工是否存在
        OpeSysStaff opeSysStaff = opeSysStaffService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeSysStaff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        if (!SesStringUtils.equals(opeSysStaff.getStatus().toString(), EmployeeStatusEnums.IN_SERVICE.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        // 清空token
        OpeSysUser opeSysUser = opeSysUserService.getById(opeSysStaff.getSysUserId());
        //todo暂时不允许对admin 进行离职操作
        if (opeSysUser.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            return null;
        }
        if (SesStringUtils.isNotBlank(opeSysUser.getLastLoginToken())) {
            jedisService.delKey(opeSysUser.getLastLoginToken());
        }
        // 删除员工
        opeSysStaffService.removeById(opeSysStaff.getSysUserId());
        // 删除员工账户
        opeSysUserService.removeById(opeSysStaff.getSysUserId());
        // 删除员工信息
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, Constant.DR_FALSE);
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
        opeSysStaff.setStatus(Integer.parseInt(EmployeeStatusEnums.IN_SERVICE.getValue()));
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
        opeSysUser.setDef1(SysUserSourceEnum.SYSTEM.getValue());
        return opeSysUser;
    }

    private OpeSysUserProfile buildOpeSysUserProfile(SaveEmployeeEnter enter) {
        OpeSysUserProfile opeSysUserProfile = new OpeSysUserProfile();
        opeSysUserProfile.setDr(0);
        opeSysUserProfile.setRepairShopId(0L);
        opeSysUserProfile.setPicture(SesStringUtils.isEmpty(enter.getAvatar()) == true ? null : enter.getAvatar());
        opeSysUserProfile.setFirstName(enter.getEmployeeFirstName());
        opeSysUserProfile.setLastName(enter.getEmployeeLastName());
        opeSysUserProfile.setFullName(new StringBuilder(enter.getEmployeeFirstName()).append(" ").append(enter.getEmployeeLastName()).toString());
        opeSysUserProfile.setEmail(enter.getEmail());
        opeSysUserProfile.setCountryCode(enter.getTelCountryCode());
        opeSysUserProfile.setTelNumber(enter.getTelephone());
        opeSysUserProfile.setGender(null);
        opeSysUserProfile.setBirthday(enter.getBirthday());
        opeSysUserProfile.setPlaceBirth(null);
        opeSysUserProfile.setAddressBureau(String.valueOf(enter.getAddressBureauId()));
        opeSysUserProfile.setAddressCountryCode(enter.getAddressCountryCode());
        opeSysUserProfile.setAddress(enter.getAddress());
        opeSysUserProfile.setCertificateType(enter.getCertificateType());
        opeSysUserProfile.setCertificateNegativeAnnex(SesStringUtils.isEmpty(enter.getNegativePicture()) == true ? null : enter.getNegativePicture());
        opeSysUserProfile.setCertificatePositiveAnnex(enter.getPositivePicture());
        opeSysUserProfile.setJoinDate(new Date());
        opeSysUserProfile.setUpdatedBy(enter.getUserId());
        opeSysUserProfile.setUpdatedTime(new Date());
        return opeSysUserProfile;
    }

    private void checkSaveEmployeeParameter(SaveEmployeeEnter enter) {
        if (StringManaConstant.entityIsNull(opeSysDeptService.getById(enter.getDeptId()))) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }
        if (StringManaConstant.entityIsNull(opeSysRoleService.getById(enter.getPositionId()))) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        // 办公区域校验
        if (SesStringUtils.isBlank(AddressBureauEnums.checkCode(String.valueOf(enter.getAddressBureauId())))) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        if (NumberUtil.ltTwoOrGtTwenty(enter.getEmployeeFirstName().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMPLOYEE_NAME_IS_NOT_ILLEGAL.getMessage());
        }
        if (NumberUtil.ltTwoOrGtTwenty(enter.getEmployeeLastName().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMPLOYEE_NAME_IS_NOT_ILLEGAL.getMessage());
        }
        if (NumberUtil.ltTwoOrGtTwenty(enter.getTelephone().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMPLOYEE_NAME_IS_NOT_ILLEGAL.getMessage());
        }
        if (NumberUtil.ltTwoOrGtTwoHundred(enter.getAddress().length())) {
            throw new SesWebRosException(ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getMessage());
        }
    }


    private void createEmployeeEmail(SaveEmployeeEnter saveEmployeeEnter){
        BaseMailTaskEnter enter = new BaseMailTaskEnter();
        enter.setName(saveEmployeeEnter.getEmployeeFirstName()+" "+saveEmployeeEnter.getEmployeeLastName());
        enter.setEvent(MailTemplateEventEnums.ROS_CREATE_EMPLOYEE.getEvent());
        enter.setSystemId(SystemIDEnums.REDE_SES.getSystemId());
        enter.setAppId(AppIDEnums.SES_ROS.getValue());
        enter.setEmail(saveEmployeeEnter.getEmail());
        enter.setRequestId(saveEmployeeEnter.getRequestId());
        enter.setUserId(saveEmployeeEnter.getUserId());
        mailMultiTaskService.addCreateEmployeeMailTask(enter);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void disAbleUser(List<Long> userIds) {
        if(CollectionUtils.isNotEmpty(userIds)){
            QueryWrapper<OpeSysUser> qw = new QueryWrapper<>();
            qw.in(OpeSysUser.COL_ID,userIds);
            List<OpeSysUser> users = opeSysUserService.list(qw);
            if(CollectionUtils.isNotEmpty(users)){
                List<OpeSysUser> list = new ArrayList<>();
                for (OpeSysUser user : users) {
                    user.setStatus(SysUserStatusEnum.LOCK.getCode());
                    list.add(user);
                }
                opeSysUserService.updateBatch(list);
            }
        }

    }

}
