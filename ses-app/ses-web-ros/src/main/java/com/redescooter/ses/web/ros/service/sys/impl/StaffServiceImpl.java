package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserRoleMapper;
import com.redescooter.ses.web.ros.dao.sys.StaffServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.vo.sys.staff.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    @Transactional
    public GeneralResult staffSave(StaffSaveOrEditEnter enter) {
        OpeSysStaff staff = new OpeSysStaff();
        BeanUtils.copyProperties(enter,staff);
        staff.setFullName(staff.getFirstName()+" "+staff.getLastName());
        staff.setUpdatedBy(enter.getUserId());
        staff.setUpdatedTime(new Date());
        staff.setId(idAppService.getId(SequenceName.OPE_SYS_STAFF));
        opeSysStaffService.save(staff);
        // 员工角色关系表插入数据
        creatRoleStaff(staff.getId(),enter.getRoleIds());
        return new GeneralResult(enter.getRequestId());
    }


    @Transactional
    @Override
    public GeneralResult staffEdit(StaffSaveOrEditEnter enter) {
        OpeSysStaff staff = opeSysStaffService.getById(enter.getId());
        if(staff == null){
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(enter,staff);
        staff.setFullName(staff.getFirstName()+" "+staff.getLastName());
        staff.setUpdatedBy(enter.getUserId());
        staff.setUpdatedTime(new Date());
        opeSysStaffService.updateById(staff);
        // 员工角色关系表插入数据
        creatRoleStaff(staff.getId(),enter.getRoleIds());
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public GeneralResult staffDelete(StaffDeleteEnter enter) {
        opeSysStaffService.removeByIds(enter.getIds());
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
            staffDetail.setRoleIds(staffRoleResult.getRoleId());
            staffDetail.setRoleNames(staffRoleResult.getRoleName());
        }
        return staffDetail;
    }

    @Override
    public PageResult<StaffListResult> staffList(StaffListEnter enter) {
        int totalRows = staffServiceMapper.totalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<StaffListResult> list = staffServiceMapper.staffList(enter);
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
    public Integer deptStaffCount(Long deptId) {
        if (deptId == null) {
            return 0;
        }
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.eq(OpeSysStaff.COL_DEPT_ID, deptId);
        return opeSysStaffService.count(qw);
    }


    private void creatRoleStaff(Long staffId, List<Long> roleIds){
         // 先看看当前这个员工有没有role关系，有的话先删除，再插入
        QueryWrapper<OpeSysUserRole> qw = new QueryWrapper<>();
        qw.eq(OpeSysUserRole.COL_USER_ID,staffId);
        List<OpeSysUserRole> list = opeSysUserRoleService.list(qw);
        if(CollectionUtils.isNotEmpty(list)){
            opeSysUserRoleMapper.delete(qw);
        }
        if(CollectionUtils.isNotEmpty(roleIds)){
            List<OpeSysUserRole> insertList = new ArrayList<>();
            for (Long roleId : roleIds) {
                OpeSysUserRole userRole = new OpeSysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(staffId);
                insertList.add(userRole);
            }
            opeSysUserRoleService.batchInsert(insertList);
        }

    }

}
