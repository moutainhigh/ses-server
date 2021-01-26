package com.redescooter.ses.web.ros.controller.restproduction.inwhouse;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproduction.SaleCombinService;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.vo.restproduct.BomNameData;
import com.redescooter.ses.web.ros.vo.restproduct.BomNoEnter;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameData;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Autowired
    private SaleCombinService saleCombinService;

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


    @PostMapping(value = "/relationPurchaseOrderData")
    @ApiOperation(value = "关联的生产采购单单据号下拉数据源", response = InWhRelationOrderResult.class)
    public Response<List<InWhRelationOrderResult>> relationPurchaseOrderData(@ModelAttribute @ApiParam("请求参数") KeywordEnter enter) {
        return new Response<>(inWhouseService.relationPurchaseOrderData(enter));
    }


    @PostMapping(value = "/relationPurchaseOrderPartsData")
    @ApiOperation(value = "关联的生产采购单部件信息", response = SaveOrUpdatePartsBEnter.class)
    public Response<List<SaveOrUpdatePartsBEnter>> relationPurchaseOrderPartsData(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inWhouseService.relationPurchaseOrderPartsData(enter));
    }


    @PostMapping(value = "/relationCombinOrderData")
    @ApiOperation(value = "关联的组装单单据号下拉数据源", response = InWhRelationOrderResult.class)
    public Response<List<InWhRelationOrderResult>> relationCombinOrderData(@ModelAttribute @ApiParam("请求参数") KeywordEnter enter) {
        return new Response<>(inWhouseService.relationCombinOrderData(enter));
    }


    @PostMapping(value = "/relationCombinOrderCombinData")
    @ApiOperation(value = "关联的组装单的组装件产品信息", response = SaveOrUpdateCombinBEnter.class)
    public Response<List<SaveOrUpdateCombinBEnter>> relationCombinOrderCombinData(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inWhouseService.relationCombinOrderCombinData(enter));
    }


    @PostMapping(value = "/relationCombinOrderScooterData")
    @ApiOperation(value = "关联的组装单的整车产品信息", response = SaveOrUpdateScooterBEnter.class)
    public Response<List<SaveOrUpdateScooterBEnter>> relationCombinOrderScooterData(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inWhouseService.relationCombinOrderScooterData(enter));
    }


    @PostMapping(value = "/combinCnNameData")
    @ApiOperation(value = "销售组装件名称下拉接口(中文名称)", response = CombinNameData.class)
    public Response<List<CombinNameData>> combinCnNameData(@ModelAttribute @ApiParam("请求参数") CombinNameEnter enter) {
        return new Response<>(saleCombinService.combinCnNameData(enter));
    }


    @PostMapping(value = "/bomNoData")
    @ApiOperation(value = "销售组装件编号下拉接口", response = BomNameData.class)
    public Response<List<BomNameData>> bomNoData(@ModelAttribute @ApiParam("请求参数") BomNoEnter enter) {
        return new Response<>(saleCombinService.cnBomNoData(enter));
    }


    //********************* 一下为模拟RPS的操作的接口 先暂时加两个按钮代替
    @PostMapping(value = "/startQc")
    @ApiOperation(value = "模拟RPS操作，开始质检", response = GeneralResult.class)
    public Response<GeneralResult> startQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inWhouseService.startQc(enter));
    }


    @PostMapping(value = "/finishQc")
    @ApiOperation(value = "模拟RPS操作，完成质检", response = GeneralResult.class)
    public Response<GeneralResult> finishQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inWhouseService.finishQc(enter));
    }

}
