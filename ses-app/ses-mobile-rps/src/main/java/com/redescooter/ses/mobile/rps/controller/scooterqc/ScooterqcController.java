package com.redescooter.ses.mobile.rps.controller.scooterqc;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.scooterqc.ScooterqcService;
import com.redescooter.ses.mobile.rps.vo.scooterqc.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@Api(tags = {"整车质检"})
@CrossOrigin
@RestController
@RequestMapping(value = "/rps/scooterqc/")
public class ScooterqcController {

    @Autowired
    ScooterqcService scooterService;

    /**
     * @Author kyle
     * @Description //1、查询整车质检列表
     * @Date  2020/4/14 14:35
     * @Param [enter]
     **/
    @IgnoreLoginCheck
    @PostMapping(value = "/scooterQcList")
    @ApiOperation(value = "整车质检列表", response = ScooterQcListResult.class)
    public Response<PageResult<ScooterQcListResult>> scooterQcList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response(scooterService.scooterQcList(enter));
    }

    /**
     * @Author kyle
     * @Description //1、根据组装单id查询到对应的部件详情列表
     * @Date  2020/4/14 14:35
     * @Param [enter]

     **/
    @IgnoreLoginCheck
    @PostMapping(value = "/partListById")
    @ApiOperation(value = "整车质检部件列表/{整车组装单id}", response = ScooterQcPartsResult.class)
    public Response<List<ScooterQcPartsResult>> partListById(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(scooterService.partListById(enter));
    }


    /**
     * @Author kyle
     * @Description //1、根据部件id查询部件质检选项详情列表
     * @Date  2020/4/14 14:35
     * @Param [enter]
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemResult>
     **/
    @IgnoreLoginCheck
    @PostMapping(value = "/scooterQcItem")
    @ApiOperation(value = "整车质检具体选项处理", response = ScooterQcItemResult.class)
    public Response<ScooterQcItemResult> scooterQcItem(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response(scooterService.scooterQcItem(enter));
    }

    /**
     * @Author kyle
     * @Description //1、提交部件质检选项详情列表修改（成功）
     * @Date  2020/4/14 14:27
     * @Param [enter]
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemEnter>
     **/
    @IgnoreLoginCheck
    @PostMapping(value = "/setScooterQcItemSuccess")
    @ApiOperation(value = "提交整车质检具体选项处理成功/{部件质检选项详情列表vo}", response = ScooterQcItemResult.class)
    public Response<ScooterQcItemEnter> setScooterQcItemSuccess(@ModelAttribute @ApiParam("请求参数") ScooterQcItemEnter enter) {
        return new Response(scooterService.setScooterQcItem(enter));
    }


    /**
     * @Author kyle
     * @Description //1、提交部件质检选项详情列表修改（失败）
     * @Date  2020/4/14 14:35
     * @Param [enter]
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.scooterqc.ScooterQcItemEnter>
     **/
    @IgnoreLoginCheck
    @PostMapping(value = "/setScooterQcItemFail")
    @ApiOperation(value = "提交整车质检具体选项处理失败/{部件质检选项详情列表vo}", response = ScooterQcItemResult.class)
    public Response<ScooterQcItemEnter> setScooterQcItemFail(@ModelAttribute @ApiParam("请求参数") ScooterQcItemEnter enter) {
        return new Response(scooterService.setScooterQcItem(enter));
    }



}


