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
 * @Date: 31/12/2019 11:21 上午
 * @ClassName: ScooterController
 * @Function: TODO
 */
@Api(tags = {"ScooterController"})
@CrossOrigin
@RestController
@RequestMapping(value = "/scooter", method = RequestMethod.POST)
public class ScooterController {

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = GeneralResult.class)
    public Response<GeneralResult> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "车辆列表", response = GeneralResult.class)
    public Response<GeneralResult> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建车辆", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "车辆编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "车辆详情", response = GeneralResult.class)
    public Response<GeneralResult> details(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }
}
