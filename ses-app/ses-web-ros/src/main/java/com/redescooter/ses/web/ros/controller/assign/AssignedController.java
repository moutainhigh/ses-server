package com.redescooter.ses.web.ros.controller.assign;

import com.redescooter.ses.web.ros.service.assign.AssignedService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 车辆已分配控制器
 * @Author Chris
 * @Date 2020/12/27 16:35
 */
@Api(value = "车辆已分配控制器", tags = "车辆已分配控制器")
@CrossOrigin
@RestController
@RequestMapping("/assigned")
public class AssignedController {

    @Autowired
    private AssignedService assignedService;

    /**
     * 已分配列表
     */

    /**
     * 已分配列表查看详情
     */

    /**
     * 已分配列表生成PDF
     */

}
