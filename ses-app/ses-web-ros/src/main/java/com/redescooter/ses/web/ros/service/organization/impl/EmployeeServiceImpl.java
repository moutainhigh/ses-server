package com.redescooter.ses.web.ros.service.organization.impl;

import com.redescooter.ses.api.common.enums.organization.EmployeeDeptTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dao.organization.EmployeeServiceMapper;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.organization.EmployeeService;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeDeptEnter;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeDeptResult;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeListEnter;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeListResult;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeResult;
import com.redescooter.ses.web.ros.vo.organization.employee.SaveEmployeeEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
    private OpeSysUserService opeSysUserService;

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
        // 查出所有员工 进行 部门分组
        List<EmployeeResult> employeeList = employeeServiceMapper.employeeList(enter);
        if (CollectionUtils.isEmpty(employeeList)) {
            return deptList;
        }
        deptList.forEach(dept -> {
            List<EmployeeResult> employeeResult = new ArrayList<>();
            employeeList.forEach(item -> {
                if (dept.getDeptId().equals(item.getDeptId())) {
                    employeeResult.add(item);
                }
            });
        });
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
        return employeeServiceMapper.employeeDetail(enter);
    }

    /**
     * 保存员工
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveEmployee(SaveEmployeeEnter enter) {
        // 构建employee 对象
        if (enter.getId() != null || enter.getId() != 0) {
            // 创建
        } else {
            // 修改
        }

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
        List<EmployeeDeptResult> result = null;
        List<Long> childIdList = null;
        switch (EmployeeDeptTypeEnums.getEnumByValue(enter.getType())) {
            case DEPT:
                // 根据父级id 查询所有子集id
                Boolean hasNextBoolean = Boolean.TRUE;
                do {
                    childIdList = employeeServiceMapper.getEmployeeDeptChildList(enter.getTenantId(), ids);
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
                result = employeeServiceMapper.getEmployeePositionList(enter.getTenantId(), 1L);
                break;
            case OFFICEAREA:
                result = employeeServiceMapper.getEmployeeOfficeareaList(enter.getTenantId());
                break;
            case COMPANY:
                result = employeeServiceMapper.getEmployeeCompanyList(enter.getTenantId(), 1L);
                break;
            default:
                return new ArrayList<>();
        }
        return new ArrayList<>();
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
        // 删除
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 返回父级或者子集的所有 部门列表
     *
     * @param bizId
     * @param level
     * @return
     */
    private List<EmployeeDeptResult> getDeptList(Long bizId, Boolean level, Long tenantId) {
        List<EmployeeDeptResult> result = null;
        Boolean hasNextBoolean = Boolean.TRUE;

        List<Long> ids = new ArrayList<>();
        ids.add(bizId);
        List<Long> idList = null;
        // 默认返回所有子集
        if (level) {
            do {
                idList = employeeServiceMapper.getEmployeeDeptChildList(tenantId, ids);
                if (CollectionUtils.isEmpty(idList)) {
                    hasNextBoolean = Boolean.FALSE;
                }
                ids.addAll(idList);
            } while (hasNextBoolean);
            result = employeeServiceMapper.getEmployeeDeptList(tenantId, ids);
        } else {
            //返回所有父级
            do {
                idList = employeeServiceMapper.getEmployeeDeptFatherList(tenantId, ids);
                if (CollectionUtils.isEmpty(idList)) {
                    hasNextBoolean = Boolean.FALSE;
                }
                ids.addAll(idList);
            } while (hasNextBoolean);
            result = employeeServiceMapper.getEmployeeDeptList(tenantId, ids);
        }

        return result;
    }
}
