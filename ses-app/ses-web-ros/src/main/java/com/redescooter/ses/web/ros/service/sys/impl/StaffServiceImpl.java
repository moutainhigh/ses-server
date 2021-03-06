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
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
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
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.UserDataEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.UserDataResult;
import com.redescooter.ses.web.ros.vo.sys.staff.*;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
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

    @Autowired
    private DeptServiceMapper deptServiceMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private OpeSysRoleSalesCidyService opeSysRoleSalesCidyService;

    @Autowired
    private OpeSaleAreaService opeSaleAreaService;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private IdAppService idAppService;

    @Value("${Request.privateKey}")
    private String privatekey;

    @Value("${Request.publicKey}")
    private String publicKey;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult staffSave(StaffSaveOrEditEnter enter) {
        if (Strings.isNullOrEmpty(enter.getEmail())) {
            throw new SesWebRosException(ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getMessage());
        }
        // ???????????? ???????????????????????????????????????????????????????????????
        checkDept(enter);
        // ????????????????????????
        checkEmail(enter.getEmail(), enter.getId());
        // ??????????????????????????????????????????
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
        if (StringManaConstant.entityIsNotNull(enter.getIfSafeCode()) && 1 == enter.getIfSafeCode()) {
            // ??????8????????????????????????
            String code = null;
            try {
                code = RsaUtils.encrypt(getRundom(), publicKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            staff.setSafeCode(code);
        }
        opeSysStaffService.save(staff);
        // ?????????????????????????????????
        creatRoleStaff(staff.getId(), enter.getRoleId());
        // ?????? ??????user_profile??????
        creatUserProfile(staff);
        try {
            // todo ??????????????????????????????????????????????????????  ???????????????????????????
            inintUserMsg(enter.getUserId());
        } catch (Exception e) {
        }
        return new GeneralResult(enter.getRequestId());
    }


    // ??????user_profile??????
    public void creatUserProfile(OpeSysStaff staff) {
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


    // ?????????????????????  ??????????????????
    public String createCode() {
        String staffCode = "S0" + new Random().nextInt(99999);
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.eq(OpeSysStaff.COL_CODE, staffCode);
        int count = opeSysStaffService.count(qw);
        if (0 < count) {
            createCode();
        }
        return staffCode;
    }


    void checkEmail(String email, Long id) {
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.eq(OpeSysStaff.COL_EMAIL, email);
        qw.eq(OpeSysStaff.COL_DR, Constant.DR_FALSE);
        if (StringManaConstant.entityIsNotNull(id)) {
            // ????????????
            qw.ne(OpeSysStaff.COL_ID, id);
        }
        int count = opeSysStaffService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
    }


    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult staffEdit(StaffSaveOrEditEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(staff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        // ???????????? ???????????????????????????????????????????????????????????????
        checkDept(enter);
        checkDeptPos(enter.getDeptId(), enter.getPositionId());
        checkEmail(enter.getEmail(), enter.getId());
        // ??????????????????  ???????????????
        if (!enter.getStatus().equals(staff.getStatus())) {
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
        // ???????????????  ?????????????????????????????????  ?????????????????????
        if (Strings.isNullOrEmpty(staff.getSafeCode()) && 1 == enter.getIfSafeCode()) {
            String code = null;
            try {
                code = RsaUtils.encrypt(getRundom(), publicKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            staff.setSafeCode(code);
        }
        opeSysStaffService.updateById(staff);
        // ?????????????????????????????????
        creatRoleStaff(staff.getId(), enter.getRoleId());
        return new GeneralResult(enter.getRequestId());
    }


    void changeUserStatus(Integer newStatus, Integer oldStatus, Long id) {
        OpeSysUser user = opeSysUserService.getById(id);
        if (StringManaConstant.entityIsNotNull(user)) {
            if (newStatus.equals(DeptStatusEnums.COMPANY.getValue()) && oldStatus.equals(DeptStatusEnums.DEPARTMENT.getValue())) {
                // ????????????????????????????????? user????????????
                user.setStatus(UserStatusEnum.NORMAL.getCode());
            } else if (newStatus.equals(DeptStatusEnums.DEPARTMENT.getValue()) && oldStatus.equals(DeptStatusEnums.COMPANY.getValue())) {
                // ????????????????????????????????? user????????????
                user.setStatus(UserStatusEnum.LOCK.getCode());
            }
            opeSysUserService.updateById(user);
        }
    }


    // ???????????? ???????????????????????????????????????????????????????????????
    public void checkDept(StaffSaveOrEditEnter enter) {
        // ?????????????????????????????????
        OpeSysStaff opUser = opeSysStaffService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(opUser)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        // ???????????????????????????????????????
        List<Long> deptIds = deptServiceMapper.getChildDeptIds(opUser.getDeptId());
        deptIds.add(opUser.getDeptId());
        if (!deptIds.contains(enter.getDeptId())) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_ERROR.getCode(), ExceptionCodeEnums.DEPT_IS_ERROR.getMessage());
        }
        // ??????????????????????????????
        if (!Strings.isNullOrEmpty(enter.getAddress1()) && 500 < enter.getAddress1().length()){
            throw new SesWebRosException(ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getMessage());
        }
        if (!Strings.isNullOrEmpty(enter.getAddress2()) && 500 < enter.getAddress2().length()){
            throw new SesWebRosException(ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getMessage());
        }
        if (!Strings.isNullOrEmpty(enter.getCountryName()) && 30 < enter.getCountryName().length()){
            throw new SesWebRosException(ExceptionCodeEnums.COUNTRY_NAME_TOO_LONG.getCode(), ExceptionCodeEnums.COUNTRY_NAME_TOO_LONG.getMessage());
        }
    }


    void checkDeptPos(Long deptId, Long positionId) {
        OpeSysDept dept = opeSysDeptMapper.selectById(deptId);
        if (StringManaConstant.entityIsNull(dept)) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }
        if (dept.getDeptStatus().equals(DeptStatusEnums.DEPARTMENT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_DISABLE.getCode(), ExceptionCodeEnums.DEPT_DISABLE.getMessage());
        }
        OpeSysPosition position = opeSysPositionMapper.selectById(positionId);
        if (StringManaConstant.entityIsNull(position)) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        if (position.getPositionStatus().equals(DeptStatusEnums.DEPARTMENT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_DISABLED.getCode(), ExceptionCodeEnums.POSITION_DISABLED.getMessage());
        }
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
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
        if (StringManaConstant.entityIsNull(staff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        StaffResult staffDetail = staffServiceMapper.staffDetail(staff.getId());
        // ???????????????????????????
        StaffRoleResult staffRoleResult = staffServiceMapper.staffRoleMsg(staff.getId());
        if (StringManaConstant.entityIsNotNull(staffRoleResult)) {
            staffDetail.setRoleId(staffRoleResult.getRoleId());
            staffDetail.setRoleName(staffRoleResult.getRoleName());
            staffDetail.setRoleStatus(staffRoleResult.getRoleStatus());
        }
        return staffDetail;
    }

    @Override
    public PageResult<StaffListResult> staffList(StaffListEnter enter) {
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
        List<Long> userIds = new ArrayList<>();
        if (!Objects.isNull(enter.getRoleId())) {
            userIds = staffServiceMapper.userIds(enter.getRoleId());
            if (CollectionUtils.isEmpty(userIds)) {
                return PageResult.createZeroRowResult(enter);
            }
        }
        int totalRows = staffServiceMapper.totalRows(enter, userIds, flag ? null : deptIds, Constant.SYSTEM_ROOT);
        if (NumberUtil.eqZero(totalRows)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<StaffListResult> list = staffServiceMapper.staffList(enter, userIds, flag ? null : deptIds, Constant.SYSTEM_ROOT);
        for (StaffListResult result : list) {
            StaffRoleResult staffRoleResult = staffServiceMapper.staffRoleMsg(result.getId());
            if (StringManaConstant.entityIsNotNull(staffRoleResult)) {
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
        if (StringManaConstant.entityIsNull(id)) {
            return 0;
        }
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        if (1 == type) {
            qw.eq(OpeSysStaff.COL_DEPT_ID, id);
        } else if (type == 2) {
            qw.eq(OpeSysStaff.COL_POSITION_ID, id);
        }
        return opeSysStaffService.count(qw);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult openAccount(StaffOpEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(staff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        if (!Strings.isNullOrEmpty(staff.getOpenAccount()) && staff.getOpenAccount().equals("1")) {
            throw new SesWebRosException(ExceptionCodeEnums.ALREADY_OPEN.getCode(), ExceptionCodeEnums.ALREADY_OPEN.getMessage());
        }
        if (2 == staff.getStatus()) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_DISABLED.getCode(), ExceptionCodeEnums.ACCOUNT_DISABLED.getMessage());
        }
        staff.setOpenAccount("1");
        opeSysStaffService.updateById(staff);
        // ???????????????user??????????????????????????????
        QueryWrapper<OpeSysUser> qw = new QueryWrapper<>();
        qw.eq(OpeSysUser.COL_LOGIN_NAME, staff.getEmail());
        qw.eq(OpeSysUser.COL_APP_ID, AppIDEnums.SES_ROS.getValue());
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
        // 2020 09 11 ??????  ????????????????????????  ??????????????????
        try {
            emailToStaff(staff, enter.getRequestId());
        } catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * @return
     * @Author Aleks
     * @Description ?????????????????????????????????
     * @Date 2020/9/17 14:56
     * @Param [enter]
     **/
    @Override
    public Boolean checkLoginPsd(UserPsdEnter enter) {
        boolean flag = true;
        OpeSysUser user = opeSysUserService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(user)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        String psd = "";
        try {
            //????????????
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
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editSafeCode(UserPsdEnter enter) {
        // ??????????????? ??????????????????
        String safeCode = "";
        try {
            //????????????
            safeCode = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getPassword()), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        if (NumberUtil.ltEightOrGtTwenty(safeCode.length())) {
            throw new SesWebRosException(ExceptionCodeEnums.PSD_LENGTH_ERROR.getCode(), ExceptionCodeEnums.PSD_LENGTH_ERROR.getMessage());
        }
        OpeSysStaff staff = opeSysStaffService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(staff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        staff.setSafeCode(enter.getPassword());
        opeSysStaffService.updateById(staff);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editUserPsd(WebResetPasswordEnter enter) {
        // ???????????????????????? ????????????????????? ????????????
        String newPassword = null;
        String confirmDecrypt = null;
        String oldPsd = "";
        try {
            //????????????
            newPassword = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getNewPassword()), privatekey);
            confirmDecrypt = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getConfirmPassword()), privatekey);
            oldPsd = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getOldPassword()), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        // ???????????????????????????  ????????????
        if (StringUtils.equals(newPassword, oldPsd)) {
            throw new SesWebRosException(ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getCode(), ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getMessage());
        }
        if (NumberUtil.ltEightOrGtTwenty(newPassword.length())) {
            throw new SesWebRosException(ExceptionCodeEnums.PSD_LENGTH_ERROR.getCode(), ExceptionCodeEnums.PSD_LENGTH_ERROR.getMessage());
        }
        // ???????????????????????????????????????
        if (!StringUtils.equals(newPassword, confirmDecrypt)) {
            throw new SesWebRosException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        OpeSysUser user = opeSysUserService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(user)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        // ???????????????????????????
        String password = DigestUtils.md5Hex(oldPsd + user.getSalt());
        if (!password.equals(user.getPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.OLD_PSD_ERROR.getCode(), ExceptionCodeEnums.OLD_PSD_ERROR.getMessage());
        }
        user.setPassword(DigestUtils.md5Hex(newPassword + user.getSalt()));
        opeSysUserService.updateById(user);
        return new GeneralResult(enter.getRequestId());
    }


    /*
     * @Author Aleks
     * @Description  ?????????????????? ?????????????????? ????????????
     * @Date  2020/9/17 14:16
     * @Param [enter]
     * @return
     **/
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult firstLoginEditPsd(UserPsdEnter enter) {
        String psd = "";
        // ?????????????????????????????????????????? ????????????
        try {
            //????????????
            psd = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getPassword()), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        OpeSysUser user = opeSysUserService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(user)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        user.setPassword(DigestUtils.md5Hex(psd + user.getSalt()));
        opeSysUserService.updateById(user);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult userMsgEdit(UserMsgEditEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(staff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }

        // ???????????????????????????
        if (StringUtils.isNotEmpty(enter.getFirstName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getFirstName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(enter.getLastName())) {
            if (NumberUtil.ltTwoOrGtTwenty(enter.getLastName().length())) {
                throw new SesWebRosException(ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.CONSTANT_NAME_IS_NOT_ILLEGAL.getMessage());
            }
        }
        // ??????????????????????????????
        if (!Strings.isNullOrEmpty(enter.getAddress1()) && 500 < enter.getAddress1().length()){
            throw new SesWebRosException(ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getMessage());
        }
        if (!Strings.isNullOrEmpty(enter.getAddress2()) && 500 < enter.getAddress2().length()){
            throw new SesWebRosException(ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.ADDRESS_IS_NOT_ILLEGAL.getMessage());
        }
        if (!Strings.isNullOrEmpty(enter.getCountryName()) && 30 < enter.getCountryName().length()){
            throw new SesWebRosException(ExceptionCodeEnums.COUNTRY_NAME_TOO_LONG.getCode(), ExceptionCodeEnums.COUNTRY_NAME_TOO_LONG.getMessage());
        }
        checkDeptPos(enter.getDeptId(), enter.getPositionId());
        checkEmail(enter.getEmail(), enter.getUserId());
        // ??????????????????  ???????????????
        if (!enter.getStatus().equals(staff.getStatus())) {
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
        if (StringManaConstant.entityIsNull(staff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        if (StringManaConstant.entityIsNull(staff.getIfSafeCode()) || 0 == staff.getIfSafeCode()) {
            throw new SesWebRosException(ExceptionCodeEnums.SAFE_CODE_NOT_OPEN.getCode(), ExceptionCodeEnums.SAFE_CODE_NOT_OPEN.getMessage());
        }
        SafeCodeResult result = new SafeCodeResult();
        result.setSafeCode(staff.getSafeCode());
        return result;
    }

    @Override
    public StaffResult userMsgDetail(GeneralEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(staff)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        StaffResult staffDetail = staffServiceMapper.staffDetail(staff.getId());
        // ???????????????????????????
        StaffRoleResult staffRoleResult = staffServiceMapper.staffRoleMsg(staff.getId());
        if (StringManaConstant.entityIsNotNull(staffRoleResult)) {
            staffDetail.setRoleId(staffRoleResult.getRoleId());
            staffDetail.setRoleName(staffRoleResult.getRoleName());
        }
        return staffDetail;
    }


    /**
     * @return
     * @Author Aleks
     * @Description ?????????????????????
     * @Date 2020/9/17 15:19
     * @Param [enter]
     **/
    @Override
    public List<StaffSaleAreaResult> staffSaleArea(GeneralEnter enter) {
        List<StaffSaleAreaResult> results = new ArrayList<>();
        // ??????????????????????????????
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_USER_ID, enter.getUserId());
        List<OpeSysUserRole> list = opeSysUserRoleService.list(qw);
        if (!CollectionUtils.isEmpty(list)) {
            // ?????????????????????????????????
            QueryWrapper<OpeSysRoleSalesCidy> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(OpeSysRoleSalesCidy.COL_ROLE_ID, list.stream().map(OpeSysUserRole::getRoleId).collect(Collectors.toList()));
            List<OpeSysRoleSalesCidy> areaList = opeSysRoleSalesCidyService.list(queryWrapper);
            if (!CollectionUtils.isEmpty(areaList)) {
                // ???????????????????????????  ???????????????????????? ?????????????????????
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


    // ???????????????????????????????????????????????????
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
        // ????????????????????????????????????role???????????????????????????????????????
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_USER_ID, staffId);
        List<OpeSysUserRole> list = opeSysUserRoleService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
//            opeSysUserRoleMapper.delete(qw);
            staffServiceMapper.deleUserRoleByStaffId(staffId);
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
    @GlobalTransactional(rollbackFor = Exception.class)
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
     * @Description ?????????????????????
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
        if (StringManaConstant.entityIsNull(staff)) {
            return;
        }
        if (staff.getEmail().equals(Constant.ADMIN_USER_NAME)) {
            // ??????????????????????????????
            return;
        }
        // ???????????????????????????????????????????????????????????????????????????
        Set<Long> list = getDeptIds(id);
        InitUserMsgResult userMsg = new InitUserMsgResult();
        userMsg.setDeptIds(list);
        Map<String, String> map = new HashMap<>();
        map.put("deptIds", StringUtils.join(list, ","));
        jedisCluster.hmset(key, map);
//        return userMsg;
    }

    @Override
    public List<UserDataResult> userData(UserDataEnter enter) {
        List<UserDataResult> list = staffServiceMapper.userData(enter);
        return list;
    }


    public Set<Long> getDeptIds(Long id) {
        Set<Long> ids = new HashSet<>();
        List<OpeSysRoleData> dataList = staffServiceMapper.roleDatas(id);
        if (CollectionUtils.isNotEmpty(dataList)) {
            // ??????????????????
            Map<Long, List<OpeSysRoleData>> map = dataList.stream().collect(Collectors.groupingBy(OpeSysRoleData::getRoleId));
            // ????????????????????????????????????id
            for (Long roleId : map.keySet()) {
                if (1 < map.get(roleId).size()) {
                    // ????????????????????????????????? ????????????????????????????????????id??????
                    ids.addAll((map.get(roleId).stream().map(OpeSysRoleData::getDeptId).collect(Collectors.toSet())));
                } else if (1 == map.get(roleId).size()) {
                    // ???????????? ????????????????????????4?????????????????????????????????????????????
                    Integer type = map.get(roleId).get(0).getDataType();
                    switch (type) {
                        case 1:
                            // ?????????
                            QueryWrapper<OpeSysDept> qw = new QueryWrapper<>();
                            ids.addAll(opeSysDeptMapper.selectList(qw).stream().map(OpeSysDept::getId).collect(Collectors.toSet()));
                            break;
                        case 2:
                            // ??????
                        default:
                            break;
                        case 3:
                            // ????????? ???????????????????????????????????????????????????????????????
                            ids.add(deptServiceMapper.getDeptIdByRoleId(roleId));
                            break;
                        case 4:
                            // ????????????????????????
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


    // ??????8????????????
    public static String getRundom() {
//         48-57 65-90 97-122
        StringBuffer id = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            char s = 0;
            int j = random.nextInt(3) % 4;
            switch (j) {
                case 0:
                    //??????????????????
                    s = (char) (random.nextInt(57) % (57 - 48 + 1) + 48);
                    break;
                case 1:
                    //????????????????????????
                    s = (char) (random.nextInt(90) % (90 - 65 + 1) + 65);
                    break;
                case 2:
                    //????????????????????????
                    s = (char) (random.nextInt(122) % (122 - 97 + 1) + 97);
                    break;
            }
            id.append(s);
        }
        return id.toString();
    }



    @Override
    public GeneralResult testFrTranslate(StringEnter enter) {

        if (true){
            throw new SesWebRosException(Integer.parseInt(enter.getKeyword()), getByCode(Integer.parseInt(enter.getKeyword()),ExceptionCodeEnums.class));
        }

        return null;
    }


    public static <T extends ExceptionCodeEnums> String getByCode(Integer code, Class<T> t){
        for(T item: t.getEnumConstants()){
            if(item.getCode() == code){
                return item.getMessage();
            }
        }
        return "";
    }
}
