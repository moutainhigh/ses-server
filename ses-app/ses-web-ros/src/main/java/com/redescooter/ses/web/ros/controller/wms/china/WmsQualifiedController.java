package com.redescooter.ses.web.ros.controller.wms.china;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsQualifiedService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description: 库存的控制器
 * @author: Aleks
 * @create: 2020/12/30 11:19
 */
@Api(tags = {"不合格品库库存控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/wms/unqualified/stock")
public class WmsQualifiedController {


    @Autowired
    private WmsQualifiedService wmsQualifiedService;


    @PostMapping(value = "/scooterList")
    @ApiOperation(value = "不合格品库车辆库存列表", response = WmsQualifiedScooterListResult.class)
    public Response<PageResult<WmsQualifiedScooterListResult>> scooterList(@ModelAttribute @ApiParam("请求参数") WmsFinishScooterListEnter enter) {
        return new Response<>(wmsQualifiedService.scooterList(enter));
    }


    @PostMapping(value = "/scooterDetail")
    @ApiOperation(value = "不合格品库车辆库存详情", response = WmsfinishScooterDetailResult.class)
    public Response<WmsfinishScooterDetailResult> scooterDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(wmsQualifiedService.scooterDetail(enter));
    }



    @PostMapping(value = "/combineList")
    @ApiOperation(value = "不合格品库组装件库存列表", response = WmsQualifiedCombinListResult.class)
    public Response<PageResult<WmsQualifiedCombinListResult>> combineList(@ModelAttribute @ApiParam("请求参数") CombinationListEnter enter) {
        return new Response<>(wmsQualifiedService.combineList(enter));
    }


    @PostMapping(value = "/combinDetail")
    @ApiOperation(value = "不合格品库组装件库存详情", response = WmsfinishCombinDetailResult.class)
    public Response<WmsfinishCombinDetailResult> combinDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(wmsQualifiedService.combinDetail(enter));
    }


    @PostMapping(value = "/partsList")
    @ApiOperation(value = "不合格品库部件库存列表", response = WmsQualifiedPartsListResult.class)
    public Response<PageResult<WmsQualifiedPartsListResult>> partsList(@ModelAttribute @ApiParam("请求参数") MaterialStockPartsListEnter enter) {
        return new Response<>(wmsQualifiedService.partsList(enter));
    }


    @PostMapping(value = "/partsDetail")
    @ApiOperation(value = "不合格品库部件的库存详情", response = MaterialpartsStockDetailResult.class)
    public Response<MaterialpartsStockDetailResult> partsDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(wmsQualifiedService.partsDetail(enter));
    }


    @PostMapping(value = "/quailifiedQtyCount")
    @ApiOperation(value = "不合格品库库存统计", response = WmsQualifiedQtyCountResult.class)
    public Response<WmsQualifiedQtyCountResult> quailifiedQtyCount(@ModelAttribute @ApiParam("请求参数") WmsQualifiedQtyCountEnter enter) {
        return new Response<>(wmsQualifiedService.quailifiedQtyCount(enter));
    }

    @PostMapping(value = "/unailifiedStockTabCount")
    @ApiOperation(value = "不合格品库tab的数量统计（车辆/组装件/部件）", response = Map.class)
    public Response<Map<String, Integer>> unailifiedStockTabCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(wmsQualifiedService.unailifiedStockTabCount(enter));
    }


    @PostMapping(value = "/inWhConfirm")
    @ApiOperation(value = "入库确认(不合格品库)", response = GeneralResult.class)
    public Response<GeneralResult> inWhConfirm(@ModelAttribute @ApiParam("请求参数") OutOrInWhConfirmEnter enter) {
        return new Response<>(wmsQualifiedService.inWhConfirm(enter));
    }


    @PostMapping(value = "/outWhConfirm")
    @ApiOperation(value = "出库确认(不合格品库)", response = GeneralResult.class)
    public Response<GeneralResult> outWhConfirm(@ModelAttribute @ApiParam("请求参数") OutOrInWhConfirmEnter enter) {
        return new Response<>(wmsQualifiedService.outWhConfirm(enter));
    }
}
