package com.redescooter.ses.web.ros.controller.sys;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}