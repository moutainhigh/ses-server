package com.redescooter.ses.web.delivery.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = GeneralResult.class)
    public Response<GeneralResult> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "司机列表", response = GeneralResult.class)
    public Response<GeneralResult> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建司机", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveDriverEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "司机编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "司机详情", response = GeneralResult.class)
    public Response<GeneralResult> details(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/leave")
    @ApiOperation(value = "司机离职", response = GeneralResult.class)
    public Response<GeneralResult> leave(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }


    @PostMapping(value = "/assignScooter")
    @ApiOperation(value = "分配车辆", response = GeneralResult.class)
    public Response<GeneralResult> assignScooter(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/removeScooter")
    @ApiOperation(value = "移除车辆", response = GeneralResult.class)
    public Response<GeneralResult> removeScooter(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/againSendEmail")
    @ApiOperation(value = "重发邮件", response = GeneralResult.class)
    public Response<GeneralResult> againSendEmail(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }
}
