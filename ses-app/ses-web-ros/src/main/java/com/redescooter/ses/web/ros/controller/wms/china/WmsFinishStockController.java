package com.redescooter.ses.web.ros.controller.wms.china;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsFinishStockService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.service.wms.cn.fr.FrWhService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: 库存的控制器
 * @author: Aleks
 * @create: 2020/12/28 15:19
 */
@Api(tags = {"库存控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/wms/stock")
public class WmsFinishStockController {

    @Autowired
    private WmsFinishStockService wmsFinishStockService;

    @Autowired
    private WmsMaterialStockService wmsMaterialStockService;

    @Autowired
    private FrWhService frWhService;


    @PostMapping(value = "/finishScooterList")
    @ApiOperation(value = "成品库车辆库存列表", response = WmsFinishScooterListResult.class)
    public Response<PageResult<WmsFinishScooterListResult>> finishScooterList(@ModelAttribute @ApiParam("请求参数") WmsFinishScooterListEnter enter) {
        return new Response<>(wmsFinishStockService.finishScooterList(enter));
    }


    @PostMapping(value = "/finishScooterDetail")
    @ApiOperation(value = "成品库车辆库存详情", response = WmsfinishScooterDetailResult.class)
    public Response<WmsfinishScooterDetailResult> finishScooterDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(wmsFinishStockService.finishScooterDetail(enter));
    }


    @PostMapping(value = "/wmsStockCount")
    @ApiOperation(value = "库存统计（统计车辆。组装件。部件的可用，已用，待入库，待出库的数量）", response = WmsStockCountResult.class)
    public Response<WmsStockCountResult> wmsStockCount(@ModelAttribute @ApiParam("请求参数") WmsStockCountEnter enter) {
        return new Response<>(wmsFinishStockService.wmsStockCount(enter));
    }


    @PostMapping(value = "/finishStockTabCount")
    @ApiOperation(value = "成品库tab的数量统计（只有车辆和组装件）", response = Map.class)
    public Response<Map<String, Integer>> finishStockTabCount(@ModelAttribute @ApiParam("请求参数") WmsStockCountEnter enter) {
        return new Response<>(wmsFinishStockService.finishStockTabCount(enter));
    }


    @PostMapping(value = "/ableProductionScooter")
    @ApiOperation(value = "剩下的原料（部件）还可生产多少车", response = AbleProductionScooterResult.class)
    public Response<List<AbleProductionScooterResult>> ableProductionScooter(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(wmsFinishStockService.ableProductionScooter(enter));
    }


    @PostMapping(value = "/finishCombinList")
    @ApiOperation(value = "成品库组装件库存列表", response = WmsFinishCombinListResult.class)
    public Response<PageResult<WmsFinishCombinListResult>> finishCombinList(@ModelAttribute @ApiParam("请求参数") CombinationListEnter enter) {
        return new Response<>(wmsFinishStockService.finishCombinList(enter));
    }


    @PostMapping(value = "/finishCombinDetail")
    @ApiOperation(value = "成品库组装件库存详情", response = WmsfinishCombinDetailResult.class)
    public Response<WmsfinishCombinDetailResult> finishCombinDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(wmsFinishStockService.finishCombinDetail(enter));
    }


    @PostMapping(value = "/materialStockPartsList")
    @ApiOperation(value = "原料库部件的库存列表", response = MaterialStockPartsListResult.class)
    public Response<PageResult<MaterialStockPartsListResult>> materialStockPartsList(@ModelAttribute @ApiParam("请求参数") MaterialStockPartsListEnter enter) {
        return new Response<>(wmsMaterialStockService.materialStockPartsList(enter));
    }


    @PostMapping(value = "/materialStockPartsDetail")
    @ApiOperation(value = "原料库部件的库存详情", response = MaterialpartsStockDetailResult.class)
    public Response<MaterialpartsStockDetailResult> materialStockPartsDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(wmsMaterialStockService.materialStockPartsDetail(enter));
    }


    @PostMapping(value = "/scooterNum")
    @ApiOperation(value = "新建出库单时，计算同车型/颜色的车辆可用库存时多少（出库数量要小于库存数）", response = IntResult.class)
    public Response<IntResult> scooterNum(@ModelAttribute @ApiParam("请求参数") WmsFinishScooterListEnter enter) {
        return new Response<>(frWhService.scooterNum(enter, 1));
    }


    @PostMapping(value = "/outOrInOrderConunt")
    @ApiOperation(value = "出库单和入库单的数量统计，按国家区分（从库存点击出入库管理进入的）", response = Map.class)
    public Response<Map<String, Integer>> outOrInOrderConunt(@ModelAttribute @ApiParam("请求参数") WmsStockTypeEnter enter) {
        return new Response<>(wmsFinishStockService.outOrInOrderConunt(enter));
    }

    @PostMapping(value = "/ableStockQty")
    @ApiOperation(value = "根据组装件id获得成品库组装件库存可用库存数量", response = IntResult.class)
    public Response<IntResult> getAbleStockQtyByProductionCombinBomId(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(wmsFinishStockService.getAbleStockQtyByProductionCombinBomId(enter));
    }


    @PostMapping(value = "/inWhConfirm")
    @ApiOperation(value = "入库确认（中国仓库和法国仓库是共用的）", response = GeneralResult.class)
    public Response<GeneralResult> inWhConfirm(@ModelAttribute @ApiParam("请求参数") OutOrInWhConfirmEnter enter) {
        return new Response<>(wmsFinishStockService.inWhConfirm(enter));
    }


    @PostMapping(value = "/outWhConfirm")
    @ApiOperation(value = "出库确认（中国仓库和法国仓库是共用的）", response = GeneralResult.class)
    public Response<GeneralResult> outWhConfirm(@ModelAttribute @ApiParam("请求参数") OutOrInWhConfirmEnter enter) {
        return new Response<>(wmsFinishStockService.outWhConfirm(enter));
    }

}
