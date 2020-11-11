package com.redescooter.ses.web.ros.controller.restproduction.inwhouse;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseSaveOrUpdateEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassNameAllocateOrderController
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:37
 * @Version V1.0
 **/
@Api(tags = {"入库单控制层"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/inWhouse/order")
public class InWhouseOrderController {


    @Autowired
    private InWhouseService inWhouseService;


    @PostMapping(value = "/list")
    @ApiOperation(value = "入库单列表", response = InWhouseListResult.class)
    public Response<PageResult<InWhouseListResult>> inWhouseList(@ModelAttribute @ApiParam("请求参数") InWhouseListEnter enter) {
        return new Response<>(inWhouseService.inWhouseList(enter));
    }


    @PostMapping(value = "/save")
    @ApiOperation(value = "入库单新增", response = GeneralResult.class)
    public Response<GeneralResult> inWhouseSave(@ModelAttribute @ApiParam("请求参数") InWhouseSaveOrUpdateEnter enter) {
        return new Response<>(inWhouseService.inWhouseSave(enter));
    }


    @PostMapping(value = "/edit")
    @ApiOperation(value = "入库单编辑", response = GeneralResult.class)
    public Response<GeneralResult> inWhouseEdit(@ModelAttribute @ApiParam("请求参数") InWhouseSaveOrUpdateEnter enter) {
        return new Response<>(inWhouseService.inWhouseEdit(enter));
    }


    @PostMapping(value = "/listCount")
    @ApiOperation(value = "列表统计", response = Map.class)
    public Response<Map<String,Integer>> listCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(inWhouseService.listCount(enter));
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = "入库单删除", response = GeneralResult.class)
    public Response<GeneralResult> inWhouseDelete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inWhouseService.inWhouseDelete(enter));
    }


    @PostMapping(value = "/detail")
    @ApiOperation(value = "入库单详情", response = InWhouseDetailResult.class)
    public Response<InWhouseDetailResult> inWhouseDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inWhouseService.inWhouseDetail(enter));
    }


    @PostMapping(value = "/inWhConfirm")
    @ApiOperation(value = "入库确认", response = GeneralResult.class)
    public Response<GeneralResult> inWhConfirm(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inWhouseService.inWhConfirm(enter));
    }


    @PostMapping(value = "/readyQc")
    @ApiOperation(value = "准备质检", response = GeneralResult.class)
    public Response<GeneralResult> inWhReadyQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inWhouseService.inWhReadyQc(enter));
    }
}
