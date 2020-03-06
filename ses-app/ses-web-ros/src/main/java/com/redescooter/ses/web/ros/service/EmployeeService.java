package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.employee.EmployeeDeptResult;
import com.redescooter.ses.web.ros.vo.employee.EmployeeListEnter;
import com.redescooter.ses.web.ros.vo.employee.EmployeeResult;
import com.redescooter.ses.web.ros.vo.position.SaveEmployeeEnter;

/**
 * @ClassName:EmployeeService
 * @description: EmployeeService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/05 15:48
 */
public interface EmployeeService {
    /**
     * 员工列表
     *
     * @param enter
     * @return
     */
    PageResult<EmployeeResult> employeeList(EmployeeListEnter enter);

    /**
     * 员工详情
     *
     * @param enter
     * @return
     */
    EmployeeResult employeeDetail(IdEnter enter);

    /**
     * 保存员工
     *
     * @param enter
     * @return
     */
    GeneralResult saveEmployee(SaveEmployeeEnter enter);

    /**
     * 员工部门列表
     *
     * @param enter
     * @return
     */
    EmployeeDeptResult employeeDeptList(GeneralEnter enter);

    /**
     * 删除员工
     *
     * @param enter
     * @return
     */
    GeneralResult trushEmployee(IdEnter enter);


}
