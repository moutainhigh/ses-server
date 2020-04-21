package com.redescooter.ses.mobile.rps.controller.scooterqc;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.scooterqc.ScooterqcService;
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
@RequestMapping(value = "/rps/scooterqc/")
public class ScooterqcController {

    @Autowired
    private ScooterqcService scooterService;

    @IgnoreLoginCheck
    @PostMapping(value = "/scooterQcList")
    @ApiOperation(value = "整车质检列表", response = ScooterQcOneResult.class)
    public Response<PageResult<ScooterQcOneResult>> scooterQcList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response(scooterService.scooterQcList(enter));
    }


    @IgnoreLoginCheck
    @PostMapping(value = "/partListById")
    @ApiOperation(value = "整车质检部件列表", response = ScooterQcPartResult.class)
    public Response<PageResult<ScooterQcPartResult>> partListById(@ModelAttribute @ApiParam("请求参数") ScooterQcIdEnter enter) {
        return new Response(scooterService.partListById(enter));
    }


    @IgnoreLoginCheck
    @PostMapping(value = "/scooterQcItem")
    @ApiOperation(value = "整车质检具体选项处理", response = ScooterQcItemResult.class)
    public Response<PageResult<ScooterQcItemResult>> scooterQcItem(@ModelAttribute @ApiParam("请求参数") ScooterQcIdItemEnter enter) {
        return new Response(scooterService.scooterQcItem(enter));
    }


    @IgnoreLoginCheck
    @PostMapping(value = "/setScooterQcItem")
    @ApiOperation(value = "提交整车质检具体选项处理", response = ScooterQcResidueNumResult.class)
    public Response<ScooterQcResidueNumResult> setScooterQcItem(@ModelAttribute @ApiParam("请求参数") ScooterQcItemEnter enter) {
        return new Response(scooterService.setScooterQcItem(enter));
    }


//    @IgnoreLoginCheck
//    @PostMapping(value = "/setScooterQcItemFail")
//    @ApiOperation(value = "提交整车质检具体选项处理失败", response = ScooterQcResidueNumResult.class)
//    public Response<ScooterQcResidueNumResult> setScooterQcItemFail(@ModelAttribute @ApiParam("请求参数") String JsonEnter) {
//        return new Response(scooterService.setScooterQcItem(JsonEnter));
//    }


}


