package com.redescooter.ses.web.ros.controller.assign;

import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.assign.AssignedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
    @ApiOperation(value = "已分配列表", notes = "已分配列表")
    @PostMapping("/list")
    public Response<>

    /**
     * 已分配列表查看详情
     */
    @ApiOperation(value = "已分配列表查看详情", notes = "已分配列表查看详情")
    @PostMapping("/detail")
    public Response<>

    /**
     * 已分配列表生成PDF
     */
    @ApiOperation(value = "已分配列表生成PDF", notes = "已分配列表生成PDF")
    @PostMapping("/pdf")
    public Response<>

}
