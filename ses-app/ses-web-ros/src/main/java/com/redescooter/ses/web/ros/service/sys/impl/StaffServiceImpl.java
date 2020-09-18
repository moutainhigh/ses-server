package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.dept.DeptStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.user.UserStatusEnum;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.accountType.RsaUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSysDeptMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysPositionMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserRoleMapper;
import com.redescooter.ses.web.ros.dao.sys.DeptServiceMapper;
import com.redescooter.ses.web.ros.dao.sys.StaffServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.sys.EmployeeService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.staff.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassNameStaffServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/8/31 19:48
 * @Version V1.0
 **/
@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private StaffServiceMapper staffServiceMapper;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private OpeSysUserRoleService opeSysUserRoleService;

    @Autowired
    private OpeSysUserRoleMapper opeSysUserRoleMapper;

    @Autowired
    private OpeSysUserService opeSysUserService;


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OpeSysDeptMapper opeSysDeptMapper;

    @Autowired
    private OpeSysPositionMapper opeSysPositionMapper;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Reference
    private MailMultiTaskService mailMultiTaskService;

    @Autowired
    private DeptServiceMapper deptServiceMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Value("${Request.privateKey}")
    private String privatekey;

    @Autowired
    private OpeSysRoleSalesCidyService opeSysRoleSalesCidyService;

    @Autowired
    private OpeSaleAreaService opeSaleAreaService;

    @Override
    @Transactional
    public GeneralResult staffSave(StaffSaveOrEditEnter enter) {
        if (Strings.isNullOrEmpty(enter.getEmail())) {
            throw new SesWebRosException(ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getMessage());
        }
        // 校验邮箱是否存在
        checkEmail(enter.getEmail(), enter.getId());
        // 校验部门、岗位是否是可用状态
        checkDeptPos(enter.getDeptId(), enter.getPositionId());
        OpeSysStaff staff = new OpeSysStaff();
        BeanUtils.copyProperties(enter, staff);
        staff.setFullName(staff.getFirstName() + " " + staff.getLastName());
        staff.setTenantId(enter.getTenantId() == null ? 0L : enter.getTenantId());
        staff.setUpdatedBy(enter.getUserId());
        staff.setUpdatedTime(new Date());
        staff.setCreatedBy(enter.getUserId());
        if (!Strings.isNullOrEmpty(enter.getBirthday())) {
            staff.setBirthday(DateUtil.stringToDate(enter.getBirthday()));
        }
        if (!Strings.isNullOrEmpty(enter.getEntryDate())) {
            staff.setEntryDate(DateUtil.stringToDate(enter.getEntryDate()));
        }
        staff.setId(idAppService.getId(SequenceName.OPE_SYS_STAFF));
        staff.setCode(createCode());
        staff.setSysUserId(staff.getId());
        if (enter.getIfSafeCode() != null && enter.getIfSafeCode() == 1) {
            // 产生8位数的随机字符串
            String code = RsaUtils.encryptByPrivateKey(getRundom(), privatekey);
            staff.setSafeCode(code);
        }
        opeSysStaffService.save(staff);
        // 员工角色关系表插入数据
        creatRoleStaff(staff.getId(), enter.getRoleId());
        // 追加 创建user_profile数据
        creatUserProfile(staff);
        try {
            // todo 新增完之后刷新缓存（暂时先这么处理）  这个后面会统一改掉
            inintUserMsg(enter.getUserId());
        }catch (Exception e){}
        return new GeneralResult(enter.getRequestId());
    }


    // 创建user_profile数据
    public void creatUserProfile(OpeSysStaff staff){
        OpeSysUserProfile opeSysUserProfile = new OpeSysUserProfile();
        opeSysUserProfile.setId(idAppService.getId(SequenceName.OPE_SYS_USER_PROFILE));
        opeSysUserProfile.setFirstName(staff.getFirstName());
        opeSysUserProfile.setLastName(staff.getLastName());
        opeSysUserProfile.setFullName(staff.getFullName());
        opeSysUserProfile.setSysUserId(staff.getId());
        opeSysUserProfile.setCreatedBy(staff.getCreatedBy());
        opeSysUserProfile.setCreatedTime(new Date());
        opeSysUserProfile.setUpdatedBy(staff.getUpdatedBy());
        opeSysUserProfile.setUpdatedTime(new Date());
        opeSysUserProfile.setCountryCode(staff.getCountryCode());
        opeSysUserProfile.setEmail(staff.getEmail());
        opeSysUserProfile.setGender(staff.getGender() == null ? "1" : staff.getGender().toString());
        opeSysUserProfile.setAddress(staff.getAddress1());
        opeSysUserProfile.setAddressBureau(staff.getAddress2());
        opeSysUserProfile.setBirthday(staff.getBirthday());
        opeSysUserProfile.setTelNumber(staff.getTelephone());
        opeSysUserProfile.setJoinDate(staff.getEntryDate());
        opeSysUserProfile.setCertificateType(staff.getCertificateType() == null ? "1" : staff.getCertificateType().toString());
        opeSysUserProfile.setCertificateNegativeAnnex(staff.getCertificatPicture1());
        opeSysUserProfile.setCertificatePositiveAnnex(staff.getCertificatPicture2());
        opeSysUserProfile.setPicture(staff.getEmployeePicture());
        opeSysUserProfileService.save(opeSysUserProfile);
    }


    // 新增角色的时候  生成角色编码
    public String createCode() {
        String staffCode = "S0" + new Random().nextInt(99999);
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.eq(OpeSysStaff.COL_CODE, staffCode);
        int count = opeSysStaffService.count(qw);
        if (count > 0) {
            createCode();
        }
        return staffCode;
    }


    void checkEmail(String email, Long id) {
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.eq(OpeSysStaff.COL_EMAIL, email);
        qw.eq(OpeSysStaff.COL_DR, 0);
        if (id != null) {
            // 员工修改
            qw.ne(OpeSysStaff.COL_ID, id);
        }
        int count = opeSysStaffService.count(qw);
        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
    }


    @Transactional
    @Override
    public GeneralResult staffEdit(StaffSaveOrEditEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getId());
        if (staff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        checkDeptPos(enter.getDeptId(), enter.getPositionId());
        checkEmail(enter.getEmail(), enter.getId());
        // 员工状态变化  影响到账号
        if (enter.getStatus() != staff.getStatus()) {
            changeUserStatus(enter.getStatus(), staff.getStatus(), staff.getId());
        }
        BeanUtils.copyProperties(enter, staff);
        staff.setFullName(staff.getFirstName() + " " + staff.getLastName());
        staff.setUpdatedBy(enter.getUserId());
        staff.setUpdatedTime(new Date());
        if (!Strings.isNullOrEmpty(enter.getBirthday())) {
            staff.setBirthday(DateUtil.stringToDate(enter.getBirthday()));
        }
        if (!Strings.isNullOrEmpty(enter.getEntryDate())) {
            staff.setEntryDate(DateUtil.stringToDate(enter.getEntryDate()));
        }
        opeSysStaffService.updateById(staff);
        // 员工角色关系表插入数据
        creatRoleStaff(staff.getId(), enter.getRoleId());
        return new GeneralResult(enter.getRequestId());
    }


    void changeUserStatus(Integer newStatus, Integer oldStatus, Long id) {
        OpeSysUser user = opeSysUserService.getById(id);
        if (user != null) {
            if (newStatus == DeptStatusEnums.COMPANY.getValue() && oldStatus == DeptStatusEnums.DEPARTMENT.getValue()) {
                // 员工状态从禁用变为正常 user也要正常
                user.setStatus(UserStatusEnum.NORMAL.getCode());
            } else if (newStatus == DeptStatusEnums.DEPARTMENT.getValue() && oldStatus == DeptStatusEnums.COMPANY.getValue()) {
                // 员工状态从正常变为禁用 user也要禁用
                user.setStatus(UserStatusEnum.LOCK.getCode());
            }
            opeSysUserService.updateById(user);
        }
    }

    ;


    void checkDeptPos(Long deptId, Long positionId) {
        OpeSysDept dept = opeSysDeptMapper.selectById(deptId);
        if (dept == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }
        if (dept.getDeptStatus() == DeptStatusEnums.DEPARTMENT.getValue()) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_DISABLE.getCode(), ExceptionCodeEnums.DEPT_DISABLE.getMessage());
        }
        OpeSysPosition position = opeSysPositionMapper.selectById(positionId);
        if (position == null) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        if (position.getPositionStatus() == DeptStatusEnums.DEPARTMENT.getValue()) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_DISABLED.getCode(), ExceptionCodeEnums.POSITION_DISABLED.getMessage());
        }
    }


    @Override
    public GeneralResult staffDelete(StaffDeleteEnter enter) {
        if (Strings.isNullOrEmpty(enter.getIds())) {
            throw new SesWebRosException(ExceptionCodeEnums.ID_IS_NOT_NULL.getCode(), ExceptionCodeEnums.ID_IS_NOT_NULL.getMessage());
        }
        opeSysUserRoleService.removeByIds(new ArrayList<>(Arrays.asList(enter.getIds().split(","))));
        opeSysStaffService.removeByIds(new ArrayList<>(Arrays.asList(enter.getIds().split(","))));
        opeSysUserService.removeByIds(new ArrayList<>(Arrays.asList(enter.getIds().split(","))));
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public StaffResult staffDetail(StaffOpEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getId());
        if (staff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        StaffResult staffDetail = staffServiceMapper.staffDetail(staff.getId());
        // 查找员工的角色信息
        StaffRoleResult staffRoleResult = staffServiceMapper.staffRoleMsg(staff.getId());
        if (staffRoleResult != null) {
            staffDetail.setRoleId(staffRoleResult.getRoleId());
            staffDetail.setRoleName(staffRoleResult.getRoleName());
        }
        return staffDetail;
    }

    @Override
    public PageResult<StaffListResult> staffList(StaffListEnter enter) {
        Set<Long> deptIds = new HashSet<>();
        String key = JedisConstant.LOGIN_ROLE_DATA + enter.getUserId();
        // 通过这个来判断是不是管理员账号，默认为是管理员
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
        List<Long> userIds = new ArrayList<>();
        if (!Objects.isNull(enter.getRoleId())) {
            userIds = staffServiceMapper.userIds(enter.getRoleId());
            if (CollectionUtils.isEmpty(userIds)) {
                return PageResult.createZeroRowResult(enter);
            }
        }
        int totalRows = staffServiceMapper.totalRows(enter, userIds, flag ? null : deptIds);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<StaffListResult> list = staffServiceMapper.staffList(enter, userIds, flag ? null : deptIds);
        for (StaffListResult result : list) {
            StaffRoleResult staffRoleResult = staffServiceMapper.staffRoleMsg(result.getId());
            if (staffRoleResult != null) {
                result.setRoleNames(staffRoleResult.getRoleName());
            }
        }
        return PageResult.create(enter, totalRows, list);
    }


    @Override
    public List<StaffDataResult> principalData(Long tenantId) {
        tenantId = tenantId == null ? 0L : tenantId;
        return staffServiceMapper.principalData(tenantId);
    }


    @Override
    public Integer deptStaffCount(Long id, Integer type) {
        if (id == null) {
            return 0;
        }
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        if (type == 1) {
            qw.eq(OpeSysStaff.COL_DEPT_ID, id);
        } else if (type == 2) {
            qw.eq(OpeSysStaff.COL_POSITION_ID, id);
        }
        return opeSysStaffService.count(qw);
    }

    @Override
    @Transactional
    public GeneralResult openAccount(StaffOpEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getId());
        if (staff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        if (!Strings.isNullOrEmpty(staff.getOpenAccount()) && staff.getOpenAccount().equals("1")) {
            throw new SesWebRosException(ExceptionCodeEnums.ALREADY_OPEN.getCode(), ExceptionCodeEnums.ALREADY_OPEN.getMessage());
        }
        staff.setOpenAccount("1");
        opeSysStaffService.updateById(staff);
        // 判断邮箱在user表里面是否已经存在过
        QueryWrapper<OpeSysUser> qw = new QueryWrapper<>();
        qw.eq(OpeSysUser.COL_LOGIN_NAME, staff.getEmail());
        int count = opeSysUserService.count(qw);
        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
        OpeSysUser user = new OpeSysUser();
        int salt = RandomUtils.nextInt(10000, 99999);
        String decryptPassword = "RedEScooter2019";
        user.setPassword(DigestUtils.md5Hex(decryptPassword + salt));
        user.setSalt(String.valueOf(salt));
        user.setId(staff.getId());
        user.setDeptId(staff.getDeptId());
        user.setAppId(AppIDEnums.SES_ROS.getValue());
        user.setSystemId(AppIDEnums.SES_ROS.getSystemId());
        user.setPassword(DigestUtils.md5Hex(decryptPassword + salt));
        user.setSalt(String.valueOf(salt));
        user.setStatus(SysUserStatusEnum.NORMAL.getCode());
        user.setLoginName(staff.getEmail());
        user.setLastLoginToken(null);
        user.setLastLoginIp(null);
        user.setCreatedBy(enter.getUserId());
        user.setCreatedTime(new Date());
        user.setUpdatedBy(enter.getUserId());
        user.setUpdatedTime(new Date());
        user.setDef1(SysUserStatusEnum.NORMAL.getValue());
        opeSysUserService.save(user);
        // 2020 09 11 追加  员工开通账号之后  给员工发邮件
        try {
            emailToStaff(staff, enter.getRequestId());
        } catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description 校验输入的密码是否正确
     * @Date 2020/9/17 14:56
     * @Param [enter]
     **/
    @Override
    public Boolean checkLoginPsd(UserPsdEnter enter) {
        boolean flag = true;
        OpeSysUser user = opeSysUserService.getById(enter.getUserId());
        if (user == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        String psd = "";
        try {
            //密码校验
            psd = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getPassword()), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        String password = DigestUtils.md5Hex(psd + user.getSalt());
        if (!password.equals(user.getPassword())) {
            flag = false;
        }
        return flag;
    }

    @Override
    public GeneralResult editSafeCode(UserPsdEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getUserId());
        if (staff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        staff.setSafeCode(enter.getPassword());
        opeSysStaffService.updateById(staff);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult editUserPsd(WebResetPasswordEnter enter) {
        // 前端传过来的密码 都是经过加密的 需要解密
        String newPassword = null;
        String confirmDecrypt = null;
        String oldPsd = "";
        try {
            //密码校验
            newPassword = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getNewPassword()), privatekey);
            confirmDecrypt = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getConfirmPassword()), privatekey);
            oldPsd = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getOldPassword()), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        // 老的密码和新的密码  不能一致
        if (StringUtils.equals(newPassword, oldPsd)) {
            throw new SesWebRosException(ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getCode(), ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getMessage());
        }
        if (newPassword.length() > 20 || newPassword.length() < 8) {
            throw new SesWebRosException(ExceptionCodeEnums.PSD_LENGTH_ERROR.getCode(), ExceptionCodeEnums.PSD_LENGTH_ERROR.getMessage());
        }
        // 校验两次的的新密码是否一致
        if (!StringUtils.equals(newPassword, confirmDecrypt)) {
            throw new SesWebRosException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        OpeSysUser user = opeSysUserService.getById(enter.getUserId());
        if (user == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        user.setPassword(DigestUtils.md5Hex(newPassword + user.getSalt()));
        opeSysUserService.updateById(user);
        return new GeneralResult(enter.getRequestId());
    }


    /*
     * @Author Aleks
     * @Description  开通账号之后 首次登陆系统 修改密码
     * @Date  2020/9/17 14:16
     * @Param [enter]
     * @return
     **/
    @Override
    public GeneralResult firstLoginEditPsd(UserPsdEnter enter) {
        String psd = "";
        // 后端接受到的是加密之后的密码 需要解密
        try {
            //密码校验
            psd = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getPassword()), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        OpeSysUser user = opeSysUserService.getById(enter.getUserId());
        if (user == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        user.setPassword(DigestUtils.md5Hex(psd + user.getSalt()));
        opeSysUserService.updateById(user);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult userMsgEdit(UserMsgEditEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getUserId());
        if (staff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        checkDeptPos(enter.getDeptId(), enter.getPositionId());
        checkEmail(enter.getEmail(), enter.getUserId());
        // 员工状态变化  影响到账号
        if (enter.getStatus() != staff.getStatus()) {
            changeUserStatus(enter.getStatus(), staff.getStatus(), staff.getId());
        }
        BeanUtils.copyProperties(enter, staff);
        staff.setFullName(staff.getFirstName() + " " + staff.getLastName());
        staff.setUpdatedBy(enter.getUserId());
        staff.setUpdatedTime(new Date());
        if (!Strings.isNullOrEmpty(enter.getBirthday())) {
            staff.setBirthday(DateUtil.stringToDate(enter.getBirthday()));
        }
        if (!Strings.isNullOrEmpty(enter.getEntryDate())) {
            staff.setEntryDate(DateUtil.stringToDate(enter.getEntryDate()));
        }
        opeSysStaffService.updateById(staff);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public SafeCodeResult getSafeCode(GeneralEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getUserId());
        if (staff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        SafeCodeResult result = new SafeCodeResult();
        result.setSafeCode(staff.getSafeCode());
        return result;
    }

    @Override
    public StaffResult userMsgDetail(GeneralEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getUserId());
        if (staff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        StaffResult staffDetail = staffServiceMapper.staffDetail(staff.getId());
        // 查找员工的角色信息
        StaffRoleResult staffRoleResult = staffServiceMapper.staffRoleMsg(staff.getId());
        if (staffRoleResult != null) {
            staffDetail.setRoleId(staffRoleResult.getRoleId());
            staffDetail.setRoleName(staffRoleResult.getRoleName());
        }
        return staffDetail;
    }


    /**
     * @return
     * @Author Aleks
     * @Description 员工的销售区域
     * @Date 2020/9/17 15:19
     * @Param [enter]
     **/
    @Override
    public List<StaffSaleAreaResult> staffSaleArea(GeneralEnter enter) {
        List<StaffSaleAreaResult> results = new ArrayList<>();
        // 先找到员工有哪些角色
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_USER_ID, enter.getUserId());
        List<OpeSysUserRole> list = opeSysUserRoleService.list(qw);
        if (!CollectionUtils.isEmpty(list)) {
            // 找到角色对应的销售区域
            QueryWrapper<OpeSysRoleSalesCidy> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(OpeSysRoleSalesCidy.COL_ROLE_ID, list.stream().map(OpeSysUserRole::getRoleId).collect(Collectors.toList()));
            List<OpeSysRoleSalesCidy> areaList = opeSysRoleSalesCidyService.list(queryWrapper);
            if (!CollectionUtils.isEmpty(areaList)) {
                // 如果角色有销售区域  找到这些销售区域 然后转为树结构
                QueryWrapper<OpeSaleArea> wrapper = new QueryWrapper<>();
                wrapper.in(OpeSaleArea.COL_ID, areaList.stream().map(OpeSysRoleSalesCidy::getCityId).collect(Collectors.toList()));
                List<OpeSaleArea> saleAreas = opeSaleAreaService.list(wrapper);
                if (!CollectionUtils.isEmpty(saleAreas)) {
                    for (OpeSaleArea area : saleAreas) {
                        StaffSaleAreaResult result = new StaffSaleAreaResult();
                        result.setAreaCode(area.getAreaCode());
                        result.setAreaName(area.getAreaName());
                        result.setSaleCityId(area.getId());
                        result.setId(area.getId());
                        result.setPId(area.getPId());
                        results.add(result);
                    }
                }
            }
        }
        return TreeUtil.build(results, Constant.AREA_TREE_ROOT_ID);
    }


    // 给员工发邮件，这个方法作为异步执行
    @Override
    @Async
    public void emailToStaff(OpeSysStaff staff, String requestId) {
        BaseMailTaskEnter enter = new BaseMailTaskEnter();
        enter.setName(staff.getFullName());
        enter.setEvent(MailTemplateEventEnums.ROS_CREATE_EMPLOYEE.getEvent());
        enter.setSystemId(SystemIDEnums.REDE_SES.getSystemId());
        enter.setAppId(AppIDEnums.SES_ROS.getValue());
        enter.setEmail(staff.getEmail());
        enter.setRequestId(requestId);
        enter.setUserId(staff.getId());
        mailMultiTaskService.addCreateEmployeeMailTask(enter);
    }


    private void creatRoleStaff(Long staffId, String roleIds) {
        // 先看看当前这个员工有没有role关系，有的话先删除，再插入
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_USER_ID, staffId);
        List<OpeSysUserRole> list = opeSysUserRoleService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            opeSysUserRoleMapper.delete(qw);
        }
        if (!Strings.isNullOrEmpty(roleIds)) {
            List<OpeSysUserRole> insertList = new ArrayList<>();
            for (String roleId : roleIds.split(",")) {
                OpeSysUserRole userRole = new OpeSysUserRole();
                userRole.setRoleId(Long.valueOf(roleId));
                userRole.setUserId(staffId);
                insertList.add(userRole);
            }
            opeSysUserRoleService.batchInsert(insertList);
        }

    }


    @Override
    public void disAbleStaff(List<Long> deptIds) {
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.in(OpeSysStaff.COL_DEPT_ID, deptIds);
        List<OpeSysStaff> staffList = opeSysStaffService.list(qw);
        if (CollectionUtils.isNotEmpty(staffList)) {
            List<OpeSysStaff> staffs = new ArrayList<>();
            for (OpeSysStaff staff : staffList) {
                staff.setStatus(2);
                staffs.add(staff);
            }
            opeSysStaffService.updateBatchById(staffs);
            employeeService.disAbleUser(staffs.stream().map(OpeSysStaff::getId).collect(Collectors.toList()));
        }
    }


    /**
     * @return
     * @Author Aleks
     * @Description 初始化用户信息
     * @Date 2020/9/14 16:42
     * @Param [id]
     **/
    @Override
    public void inintUserMsg(Long id) {
        String key = JedisConstant.LOGIN_ROLE_DATA + id;
        if (jedisCluster.exists(key)) {
            jedisCluster.del(key);
        }
        OpeSysStaff staff = opeSysStaffService.getById(id);
        if (staff == null) {
            return;
        }
        if (staff.getEmail().equals(Constant.ADMIN_USER_NAME)) {
            // 管理员账号，直接返回
            return;
        }
        // 最终目的是找到该用户对应的角色有哪些部门的查看权限
        Set<Long> list = getDeptIds(id);
        InitUserMsgResult userMsg = new InitUserMsgResult();
        userMsg.setDeptIds(list);
        Map<String, String> map = new HashMap<>();
        map.put("deptIds", StringUtils.join(list, ","));
        jedisCluster.hmset(key, map);
//        return userMsg;
    }


    public Set<Long> getDeptIds(Long id) {
        Set<Long> ids = new HashSet<>();
        List<OpeSysRoleData> dataList = staffServiceMapper.roleDatas(id);
        if (CollectionUtils.isNotEmpty(dataList)) {
            // 按照角色分组
            Map<Long, List<OpeSysRoleData>> map = dataList.stream().collect(Collectors.groupingBy(OpeSysRoleData::getRoleId));
            // 找到每个角色的对应的部门id
            for (Long roleId : map.keySet()) {
                if (map.get(roleId).size() > 1) {
                    // 这种情况必然是自定义的 且勾选了多条，直接拿部门id就行
                    ids.addAll((map.get(roleId).stream().map(OpeSysRoleData::getDeptId).collect(Collectors.toSet())));
                } else if (map.get(roleId).size() == 1) {
                    // 这种情况 就是勾选了上面的4种情况，需要分别找到对应的部门
                    Integer type = map.get(roleId).get(0).getDataType();
                    switch (type) {
                        case 1:
                            // 全部的
                            QueryWrapper<OpeSysDept> qw = new QueryWrapper<>();
                            ids.addAll(opeSysDeptMapper.selectList(qw).stream().map(OpeSysDept::getId).collect(Collectors.toSet()));
                            break;
                        case 2:
                            // 本人
                        default:
                            break;
                        case 3:
                            // 本部门 找到角色的部门（就是角色对应的岗位的部门）
                            ids.add(deptServiceMapper.getDeptIdByRoleId(roleId));
                            break;
                        case 4:
                            // 本部门及其子部门
                            Long deptId = deptServiceMapper.getDeptIdByRoleId(roleId);
                            ids.add(deptId);
                            ids.addAll(deptServiceMapper.getChildDeptIds(deptId));
                            break;
                    }
                }
            }
        }
        return ids;
    }


    // 生成8位随机数
    public static String getRundom() {
//         48-57 65-90 97-122
        StringBuffer id = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            char s = 0;
            int j = random.nextInt(3) % 4;
            switch (j) {
                case 0:
                    //随机生成数字
                    s = (char) (random.nextInt(57) % (57 - 48 + 1) + 48);
                    break;
                case 1:
                    //随机生成大写字母
                    s = (char) (random.nextInt(90) % (90 - 65 + 1) + 65);
                    break;
                case 2:
                    //随机生成小写字母
                    s = (char) (random.nextInt(122) % (122 - 97 + 1) + 97);
                    break;
            }
            id.append(s);
        }
        return id.toString();
    }
}
