package com.redescooter.ses.web.ros.dao.organization;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeListEnter;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeListResult;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeResult;

import java.util.List;

/**
 * @ClassName:EmployeeService
 * @description: EmployeeService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/09 13:08
 */
public interface EmployeeServiceMapper {
    /**
     * 员工列表中的 部门列表
     *
     * @param enter
     * @return
     */
    List<EmployeeListResult> deptList(EmployeeListEnter enter);

    /**
     * 员工列表
     *
     * @param enter
     * @return
     */
    List<EmployeeResult> employeeList(EmployeeListEnter enter);

    /**
     * 员工详情
     *
     * @param enter
     * @return
     */
    EmployeeResult employeeDetail(IdEnter enter);
}
