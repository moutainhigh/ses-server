package com.redescooter.ses.web.ros.controller.wms.china;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsFinishStockService;
import com.redescooter.ses.web.ros.service.wms.cn.china.WmsMaterialStockService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
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
public class WmsStockController {

    @Autowired
    private WmsFinishStockService wmsFinishStockService;

    @Autowired
    private WmsMaterialStockService wmsMaterialStockService;


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
    public Response<Map<String, Integer>> finishStockTabCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
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

}
