package com.redescooter.ses.web.delivery.controller;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.service.DriverService;
import com.redescooter.ses.web.delivery.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 11:15 上午
 * @ClassName: DriverController
 * @Function: TODO
 */

@Api(tags = {"DriverController"})
@CrossOrigin
@RestController
@RequestMapping(value = "/driver", method = RequestMethod.POST)
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(driverService.countStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "司机列表", response = ListDriverResult.class)
    public Response<PageResult<ListDriverResult>> list(@ModelAttribute @ApiParam("请求参数") ListDriverPage page) {
        return new Response<>(driverService.list(page));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建司机", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveDriverEnter enter) {
        return new Response<>(driverService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "司机编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") SaveDriverEnter enter) {
        return new Response<>(driverService.save(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "司机详情", response = DriverDetailsResult.class)
    public Response<DriverDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(driverService.details(enter));
    }

    @PostMapping(value = "/leave")
    @ApiOperation(value = "司机离职", response = GeneralResult.class)
    public Response<GeneralResult> leave(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(driverService.leave(enter));
    }


    @PostMapping(value = "/assignScooter")
    @ApiOperation(value = "分配车辆", response = GeneralResult.class)
    public Response<GeneralResult> assignScooter(@ModelAttribute @ApiParam("请求参数") AssignScooterEnter enter) {
        return new Response<>(driverService.assignScooter(enter));
    }

    @PostMapping(value = "/removeScooter")
    @ApiOperation(value = "移除车辆", response = GeneralResult.class)
    public Response<GeneralResult> removeScooter(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(driverService.removeScooter(enter));
    }

    @PostMapping(value = "/againSendEmail")
    @ApiOperation(value = "重发邮件", response = GeneralResult.class)
    public Response<GeneralResult> againSendEmail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(driverService.againSendEmail(enter));
    }

    @PostMapping(value = "/scooterList")
    @ApiOperation(value = "车辆列表", response = ListScooterResult.class)
    public Response<List<ListScooterResult>> scooterList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(driverService.scooterList(enter));
    }
}
