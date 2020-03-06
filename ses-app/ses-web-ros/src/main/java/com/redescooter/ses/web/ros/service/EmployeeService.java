package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.employee.EmployeeListEnter;
import com.redescooter.ses.web.ros.vo.employee.EmployeeResult;

/**
 * @ClassName:EmployeeService
 * @description: EmployeeService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/05 15:48
 */
public interface EmployeeService {

    PageResult<EmployeeResult> employeeList(EmployeeListEnter enter);
}
