package com.redescooter.ses.mobile.rps.controller.scooterqc;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.scooterqc.ScooterQcService;
import com.redescooter.ses.mobile.rps.vo.scooterqc.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Log4j2
@Api(tags = {"整车质检"})
@CrossOrigin
@RestController
@RequestMapping(value = "/scooterqc/")
public class ScooterQcController {

    @Autowired
    private ScooterQcService scooterService;

    @PostMapping(value = "/scooterQcList")
    @ApiOperation(value = "整车质检列表", response = ScooterQcOneResult.class)
    public Response<PageResult<ScooterQcOneResult>> scooterQcList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response(scooterService.scooterQcList(enter));
    }

    @PostMapping(value = "/partListById")
    @ApiOperation(value = "整车质检部件列表", response = ScooterQcPartResult.class)
    public Response<PageResult<ScooterQcPartResult>> partListById(@ModelAttribute @ApiParam("请求参数") ScooterQcIdEnter enter) {
        return new Response(scooterService.partListById(enter));
    }

    @PostMapping(value = "/scooterQcItem")
    @ApiOperation(value = "整车质检具体选项处理", response = ScooterQcItemListResult.class)
    public Response<ScooterQcItemListResult> scooterQcItem(@ModelAttribute @ApiParam("请求参数") ScooterQcIdItemEnter enter) {
        return new Response(scooterService.scooterQcItem(enter));
    }

    @PostMapping(value = "/setScooterQcItem")
    @ApiOperation(value = "提交整车质检具体选项处理", response = ScooterQcResidueNumResult.class)
    public Response<ScooterQcResidueNumResult> setScooterQcItem(@ModelAttribute @ApiParam("请求参数") ScooterQcItemEnter enter) {
        return new Response(scooterService.setScooterQcItem(enter));
    }

    @PostMapping(value = "/checkScooterSerilaN")
    @ApiOperation(value = "校验整车是否已经质检过", response = ScooterQcResidueNumResult.class)
    public Response<ScooterQcResidueNumResult> checkScooterSerilaN(@ModelAttribute @ApiParam("请求参数") CheckScooterSerilaNEnter enter) {
        return new Response(scooterService.checkScooterSerilaN(enter));
    }

}


