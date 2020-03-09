package com.redescooter.ses.web.ros.service.organization.impl;

import com.redescooter.ses.api.common.enums.organization.EmployeeDeptTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dao.organization.EmployeeServiceMapper;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
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

    /**
     * 员工列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<EmployeeListResult> employeeList(EmployeeListEnter enter) {
        // 部门过滤
        List<EmployeeListResult> deptList = employeeServiceMapper.deptList(enter);
        if (CollectionUtils.isEmpty(deptList)) {
            return new ArrayList<>();
        }
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
        switch (EmployeeDeptTypeEnums.getEnumByValue(enter.getType())) {
            case DEPT:
                log.info("部门");
                break;
            case POSITION:
                log.info("部门");
                break;
            case OFFICEAREA:
                log.info("职位");
                break;
            case COMPANY:
                log.info("公司");
                break;
            default:
                return null;
        }
        return null;
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
}
