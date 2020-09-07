package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserRoleMapper;
import com.redescooter.ses.web.ros.dao.sys.StaffServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.vo.sys.staff.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private IdAppService idAppService;

    @Autowired
    private OpeSysUserRoleService opeSysUserRoleService;

    @Autowired
    private OpeSysUserRoleMapper opeSysUserRoleMapper;

    @Autowired
    private OpeSysUserService opeSysUserService;

    @Override
    @Transactional
    public GeneralResult staffSave(StaffSaveOrEditEnter enter) {
        if(Strings.isNullOrEmpty(enter.getEmail())){
            throw new SesWebRosException(ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getMessage());
        }
        // 校验邮箱是否存在
        checkEmail(enter.getEmail(),enter.getId());
        OpeSysStaff staff = new OpeSysStaff();
        BeanUtils.copyProperties(enter,staff);
        staff.setFullName(staff.getFirstName()+" "+staff.getLastName());
        staff.setTenantId(enter.getTenantId()==null?0L:enter.getTenantId());
        staff.setUpdatedBy(enter.getUserId());
        staff.setUpdatedTime(new Date());
        if(!Strings.isNullOrEmpty(enter.getBirthday())){
            staff.setBirthday(DateUtil.stringToDate(enter.getBirthday()));
        }
        if(!Strings.isNullOrEmpty(enter.getEntryDate())){
            staff.setEntryDate(DateUtil.stringToDate(enter.getEntryDate()));
        }
        staff.setId(idAppService.getId(SequenceName.OPE_SYS_STAFF));
        staff.setCode(createCode());
        staff.setSysUserId(staff.getId());
        opeSysStaffService.save(staff);
        // 员工角色关系表插入数据
        creatRoleStaff(staff.getId(),enter.getRoleIds());
        return new GeneralResult(enter.getRequestId());
    }

    // 新增角色的时候  生成角色编码
    public String createCode(){
        String staffCode = "S0"+new Random().nextInt(99999);
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.eq(OpeSysStaff.COL_CODE,staffCode);
        int count = opeSysStaffService.count(qw);
        if(count > 0){
            createCode();
        }
        return staffCode;
    }


    void checkEmail(String email,Long id){
        QueryWrapper<OpeSysStaff>  qw = new QueryWrapper<>();
        qw.eq(OpeSysStaff.COL_EMAIL,email);
        if(id != null){
            // 员工修改
            qw.ne(OpeSysStaff.COL_ID,id);
        }
        int count = opeSysStaffService.count(qw);
        if(count > 0){
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
    }


    @Transactional
    @Override
    public GeneralResult staffEdit(StaffSaveOrEditEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getId());
        if(staff == null){
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        checkEmail(enter.getEmail(),enter.getId());
        BeanUtils.copyProperties(enter,staff);
        staff.setFullName(staff.getFirstName()+" "+staff.getLastName());
        staff.setUpdatedBy(enter.getUserId());
        staff.setUpdatedTime(new Date());
        opeSysStaffService.updateById(staff);
        // 员工角色关系表插入数据
//        creatRoleStaff(staff.getId(),enter.getRoleIds());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public GeneralResult staffDelete(StaffDeleteEnter enter) {
        if(Strings.isNullOrEmpty(enter.getIds())){
            throw new SesWebRosException(ExceptionCodeEnums.ID_IS_NOT_NULL.getCode(), ExceptionCodeEnums.ID_IS_NOT_NULL.getMessage());
        }
        opeSysStaffService.removeByIds(new ArrayList<>(Arrays.asList(enter.getIds().split(","))));
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public StaffResult staffDetail(StaffOpEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getId());
        if(staff == null){
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        StaffResult staffDetail = staffServiceMapper.staffDetail(staff.getId());
        // 查找员工的角色信息
        StaffRoleResult staffRoleResult = staffServiceMapper.staffRoleMsg(staff.getId());
        if(staffRoleResult != null){
            staffDetail.setRoleId(staffRoleResult.getRoleId());
            staffDetail.setRoleName(staffRoleResult.getRoleName());
        }
        return staffDetail;
    }

    @Override
    public PageResult<StaffListResult> staffList(StaffListEnter enter) {
        List<Long>  userIds = new ArrayList<>();
        if(!Objects.isNull(enter.getRoleId())){
            userIds = staffServiceMapper.userIds(enter.getRoleId());
            if(CollectionUtils.isEmpty(userIds)){
                return PageResult.createZeroRowResult(enter);
            }
        }
        int totalRows = staffServiceMapper.totalRows(enter,userIds);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<StaffListResult> list = staffServiceMapper.staffList(enter,userIds);
        for (StaffListResult result : list) {
            StaffRoleResult staffRoleResult = staffServiceMapper.staffRoleMsg(result.getId());
            if(staffRoleResult != null){
                result.setRoleNames(staffRoleResult.getRoleName());
            }
        }
        return PageResult.create(enter, totalRows, list);
    }


    @Override
    public List<StaffDataResult> principalData(Long tenantId) {
        tenantId = tenantId==null?0L:tenantId;
        return staffServiceMapper.principalData(tenantId);
    }


    @Override
    public Integer deptStaffCount(Long id,Integer type) {
        if (id == null) {
            return 0;
        }
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        if(type == 1){
            qw.eq(OpeSysStaff.COL_DEPT_ID, id);
        } else if (type == 2){
            qw.eq(OpeSysStaff.COL_POSITION_ID, id);
        }
        return opeSysStaffService.count(qw);
    }

    @Override
    @Transactional
    public GeneralResult openAccount(StaffOpEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getId());
        if(staff == null){
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        if(!Strings.isNullOrEmpty(staff.getOpenAccount()) && staff.getOpenAccount().equals("1")){
            throw new SesWebRosException(ExceptionCodeEnums.ALREADY_OPEN.getCode(), ExceptionCodeEnums.ALREADY_OPEN.getMessage());
        }
        staff.setOpenAccount("1");
        opeSysStaffService.updateById(staff);
        // 判断邮箱在user表里面是否已经存在过
        QueryWrapper<OpeSysUser> qw = new QueryWrapper<>();
        qw.eq(OpeSysUser.COL_LOGIN_NAME,staff.getEmail());
        int count = opeSysUserService.count(qw);
        if(count > 0){
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
        user .setSystemId(AppIDEnums.SES_ROS.getSystemId());
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
        return new GeneralResult(enter.getRequestId());
    }


    private void creatRoleStaff(Long staffId, String roleIds){
         // 先看看当前这个员工有没有role关系，有的话先删除，再插入
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_USER_ID,staffId);
        List<OpeSysUserRole> list = opeSysUserRoleService.list(qw);
        if(CollectionUtils.isNotEmpty(list)){
            opeSysUserRoleMapper.delete(qw);
        }
        if(!Strings.isNullOrEmpty(roleIds)){
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

}
