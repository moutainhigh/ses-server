package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.organization.EmployeeService;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeDeptEnter;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeDeptResult;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeListEnter;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeListResult;
import com.redescooter.ses.web.ros.vo.organization.employee.EmployeeResult;
import com.redescooter.ses.web.ros.vo.organization.employee.SaveEmployeeEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName:EmployeeController
 * @description: SysEmployeeController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/10 14:05
 */
@Api(tags = {"员工管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/employee")
public class SysEmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @PostMapping(value = "/list")
    @ApiOperation(value = "员工列表", response = EmployeeListResult.class)
    public Response<List<EmployeeListResult>> employeeList(@ModelAttribute @ApiParam("请求参数") EmployeeListEnter enter) {
        return new Response<>(employeeService.employeeList(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "员工详情", response = EmployeeResult.class)
    public Response<EmployeeResult> employeeList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(employeeService.employeeDetail(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存员工", response = GeneralResult.class)
    public Response<GeneralResult> employeeList(@ModelAttribute @ApiParam("请求参数") SaveEmployeeEnter enter) {
        return new Response<>(employeeService.saveEmployee(enter));
    }

    @PostMapping(value = "/companyDeptPositionList")
    @ApiOperation(value = "公司部门职位列表", response = EmployeeDeptResult.class)
    public Response<List<EmployeeDeptResult>> employeeDeptList(@ModelAttribute @ApiParam("请求参数") EmployeeDeptEnter enter) {
        return new Response<>(employeeService.employeeDeptList(enter));
    }

    @PostMapping(value = "/trush")
    @ApiOperation(value = "员工离职", response = GeneralResult.class)
    public Response<GeneralResult> trushEmployee(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(employeeService.trushEmployee(enter));
    }
}
