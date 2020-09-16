package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.dept.DeptStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.user.UserStatusEnum;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSysDeptMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysPositionMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserRoleMapper;
import com.redescooter.ses.web.ros.dao.sys.DeptServiceMapper;
import com.redescooter.ses.web.ros.dao.sys.StaffServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.sys.EmployeeService;
import com.redescooter.ses.web.ros.service.sys.RolePermissionService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.vo.sys.staff.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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



    @Override
    @Transactional
    public GeneralResult staffSave(StaffSaveOrEditEnter enter) {
        if(Strings.isNullOrEmpty(enter.getEmail())){
            throw new SesWebRosException(ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getMessage());
        }
        // 校验邮箱是否存在
        checkEmail(enter.getEmail(),enter.getId());
        // 校验部门、岗位是否是可用状态
        checkDeptPos(enter.getDeptId(),enter.getPositionId());
        OpeSysStaff staff = new OpeSysStaff();
        BeanUtils.copyProperties(enter,staff);
        staff.setFullName(staff.getFirstName()+" "+staff.getLastName());
        staff.setTenantId(enter.getTenantId()==null?0L:enter.getTenantId());
        staff.setUpdatedBy(enter.getUserId());
        staff.setUpdatedTime(new Date());
        staff.setCreatedBy(enter.getUserId());
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
        creatRoleStaff(staff.getId(),enter.getRoleId());
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
        opeSysUserProfile.setGender(staff.getGender()==null?"1":staff.getGender().toString());
        opeSysUserProfile.setAddress(staff.getAddress1());
        opeSysUserProfile.setAddressBureau(staff.getAddress2());
        opeSysUserProfile.setBirthday(staff.getBirthday());
        opeSysUserProfile.setTelNumber(staff.getTelephone());
        opeSysUserProfile.setJoinDate(staff.getEntryDate());
        opeSysUserProfile.setCertificateType(staff.getCertificateType()==null?"1":staff.getCertificateType().toString());
        opeSysUserProfile.setCertificateNegativeAnnex(staff.getCertificatPicture1());
        opeSysUserProfile.setCertificatePositiveAnnex(staff.getCertificatPicture2());
        opeSysUserProfile.setPicture(staff.getEmployeePicture());
        opeSysUserProfileService.save(opeSysUserProfile);
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
        checkDeptPos(enter.getDeptId(),enter.getPositionId());
        checkEmail(enter.getEmail(),enter.getId());
        // 员工状态变化  影响到账号
        if(enter.getStatus() != staff.getStatus()){
            changeUserStatus(enter.getStatus(),staff.getStatus(),staff.getId());
        }
        BeanUtils.copyProperties(enter,staff);
        staff.setFullName(staff.getFirstName()+" "+staff.getLastName());
        staff.setUpdatedBy(enter.getUserId());
        staff.setUpdatedTime(new Date());
        if(!Strings.isNullOrEmpty(enter.getBirthday())){
            staff.setBirthday(DateUtil.stringToDate(enter.getBirthday()));
        }
        if(!Strings.isNullOrEmpty(enter.getEntryDate())){
            staff.setEntryDate(DateUtil.stringToDate(enter.getEntryDate()));
        }
        opeSysStaffService.updateById(staff);
        // 员工角色关系表插入数据
        creatRoleStaff(staff.getId(),enter.getRoleId());
        return new GeneralResult(enter.getRequestId());
    }


    void changeUserStatus(Integer newStatus,Integer oldStatus,Long id){
        OpeSysUser user = opeSysUserService.getById(id);
        if(user != null){
            if(newStatus == DeptStatusEnums.COMPANY.getValue() && oldStatus == DeptStatusEnums.DEPARTMENT.getValue()){
                // 员工状态从禁用变为正常 user也要正常
                user.setStatus(UserStatusEnum.NORMAL.getCode());
            }else if (newStatus == DeptStatusEnums.DEPARTMENT.getValue() && oldStatus == DeptStatusEnums.COMPANY.getValue()){
                // 员工状态从正常变为禁用 user也要禁用
                user.setStatus(UserStatusEnum.LOCK.getCode());
            }
            opeSysUserService.updateById(user);
        }
    };


    void checkDeptPos(Long deptId,Long positionId){
        OpeSysDept dept = opeSysDeptMapper.selectById(deptId);
        if(dept == null){
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }
        if(dept.getDeptStatus() == DeptStatusEnums.DEPARTMENT.getValue()){
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_DISABLE.getCode(), ExceptionCodeEnums.DEPT_DISABLE.getMessage());
        }
        OpeSysPosition position = opeSysPositionMapper.selectById(positionId);
        if(position == null){
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        if(position.getPositionStatus() == DeptStatusEnums.DEPARTMENT.getValue()){
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_DISABLED.getCode(), ExceptionCodeEnums.POSITION_DISABLED.getMessage());
        }
    }


    @Override
    public GeneralResult staffDelete(StaffDeleteEnter enter) {
        if(Strings.isNullOrEmpty(enter.getIds())){
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
        List<Long>  userIds = new ArrayList<>();
        if(!Objects.isNull(enter.getRoleId())){
            userIds = staffServiceMapper.userIds(enter.getRoleId());
            if(CollectionUtils.isEmpty(userIds)){
                return PageResult.createZeroRowResult(enter);
            }
        }
        int totalRows = staffServiceMapper.totalRows(enter,userIds,flag?null:deptIds);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<StaffListResult> list = staffServiceMapper.staffList(enter,userIds,flag?null:deptIds);
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
        // 2020 09 11 追加  员工开通账号之后  给员工发邮件
        try {
            emailToStaff(staff,enter.getRequestId());
        }catch (Exception e){

        }
        return new GeneralResult(enter.getRequestId());
    }


    // 给员工发邮件，这个方法作为异步执行
    @Async
    public void emailToStaff(OpeSysStaff staff,String requestId){
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



    @Override
    public void disAbleStaff(List<Long> deptIds) {
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.in(OpeSysStaff.COL_DEPT_ID,deptIds);
        List<OpeSysStaff>  staffList = opeSysStaffService.list(qw);
        if(CollectionUtils.isNotEmpty(staffList)){
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
     * @Author Aleks
     * @Description  初始化用户信息
     * @Date  2020/9/14 16:42
     * @Param [id]
     * @return
     **/
    @Override
    public void inintUserMsg(Long id) {
        String key = JedisConstant.LOGIN_ROLE_DATA + id;
        if(jedisCluster.exists(key)){
            jedisCluster.del(key);
        }
        OpeSysStaff staff = opeSysStaffService.getById(id);
        if(staff == null){
            return ;
        }
        if(staff.getEmail().equals(Constant.ADMIN_USER_NAME)){
            // 管理员账号，直接返回
            return;
        }
        // 最终目的是找到该用户对应的角色有哪些部门的查看权限
        Set<Long> list = getDeptIds(id);
        InitUserMsgResult userMsg = new InitUserMsgResult();
        userMsg.setDeptIds(list);
        Map<String,String> map = new HashMap<>();
        map.put("deptIds", StringUtils.join(list,","));
        jedisCluster.hmset(key, map);
//        return userMsg;
    }


    public Set<Long> getDeptIds(Long id){
        Set<Long> ids = new HashSet<>();
        List<OpeSysRoleData> dataList = staffServiceMapper.roleDatas(id);
        if(CollectionUtils.isNotEmpty(dataList)){
            // 按照角色分组
            Map<Long, List<OpeSysRoleData>> map = dataList.stream().collect(Collectors.groupingBy(OpeSysRoleData::getRoleId));
            // 找到每个角色的对应的部门id
            for (Long roleId : map.keySet()) {
                if(map.get(roleId).size() > 1){
                    // 这种情况必然是自定义的 且勾选了多条，直接拿部门id就行
                    ids.addAll((map.get(roleId).stream().map(OpeSysRoleData::getDeptId).collect(Collectors.toSet())));
                }else if (map.get(roleId).size() == 1){
                    // 这种情况 就是勾选了上面的4种情况，需要分别找到对应的部门
                    Integer type = map.get(roleId).get(0).getDataType();
                    switch (type){
                        case 1:
                            // 全部的
                            QueryWrapper<OpeSysDept> qw = new QueryWrapper<>();
                            ids.addAll(opeSysDeptMapper.selectList(qw).stream().map(OpeSysDept::getId).collect(Collectors.toSet()));
                        case 2:
                            // 本人
                            break;
                        case 3:
                            // 本部门 找到角色的部门（就是角色对应的岗位的部门）
                            ids.add(deptServiceMapper.getDeptIdByRoleId(roleId));
                        case 4:
                            // 本部门及其子部门
                            Long deptId = deptServiceMapper.getDeptIdByRoleId(roleId);
                            ids.add(deptId);
                            ids.addAll(deptServiceMapper.getChildDeptIds(deptId));
                    }
                }
            }
        }
        return ids;
    }
}
