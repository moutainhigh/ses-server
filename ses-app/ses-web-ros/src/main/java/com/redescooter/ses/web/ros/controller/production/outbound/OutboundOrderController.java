package com.redescooter.ses.web.ros.controller.production.outbound;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.outbound.OutboundOrderService;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhRelationOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"出库单"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/restproduction/outbound")
public class OutboundOrderController {

    @Autowired
    private OutboundOrderService outboundOrderService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "产品类型统计", response = Map.class)
    public Response<Map<Integer, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(outboundOrderService.countByType(enter));
    }

    @PostMapping(value = "/statusList")
    @ApiOperation(value = "状态列表", response = Map.class)
    public Response<Map<Integer, String>> statusList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(outboundOrderService.statusList(enter));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "列表", response = OutboundOrderListResult.class)
    public Response<PageResult<OutboundOrderListResult>> list(@ModelAttribute @ApiParam("请求参数") OutboundOrderListEnter enter) {
        return new Response<>(outboundOrderService.list(enter));
    }


    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = OutboundOrderDetailResult.class)
    public Response<OutboundOrderDetailResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.detail(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.delete(enter));
    }


//    @PostMapping(value = "/save")
//    @ApiOperation(value = "保存", response = GeneralResult.class)
//    public Response<GeneralResult> detail(@ModelAttribute @ApiParam("请求参数") SaveOutboundOrderEnter enter) {
//        return new Response<>(outboundOrderService.save(enter));
//    }


    @PostMapping(value = "/invoiceData")
    @ApiOperation(value = "关联的发货单号下拉接口", response = GeneralResult.class)
    public Response<List<InWhRelationOrderResult>> invoiceData(@ModelAttribute @ApiParam("请求参数") KeywordEnter enter) {
        return new Response<>(outboundOrderService.invoiceData(enter));
    }


    @PostMapping(value = "/relationInvoiceScooterData")
    @ApiOperation(value = "关联的整车发货单产品信息", response = SaveOrUpdateOutScooterBEnter.class)
    public Response<List<SaveOrUpdateOutScooterBEnter>> relationInvoiceScooterData(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.relationInvoiceScooterData(enter));
    }


    @PostMapping(value = "/relationInvoiceCombinData")
    @ApiOperation(value = "关联的组装件发货单产品信息", response = SaveOrUpdateOutCombinBEnter.class)
    public Response<List<SaveOrUpdateOutCombinBEnter>> relationInvoiceCombinData(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.relationInvoiceCombinData(enter));
    }


    @PostMapping(value = "/relationInvoicePartsData")
    @ApiOperation(value = "关联的发货单部件信息", response = SaveOrUpdateOutPartsBEnter.class)
    public Response<List<SaveOrUpdateOutPartsBEnter>> relationInvoicePartsData(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.relationInvoicePartsData(enter));
    }


    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", response = GeneralResult.class)
    public Response<GeneralResult> outOrderSave(@ModelAttribute @ApiParam("请求参数") SaveOrUpdateOutOrderEnter enter) {
        return new Response<>(outboundOrderService.outOrderSave(enter));
    }


    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑", response = GeneralResult.class)
    public Response<GeneralResult> outOrderEdit(@ModelAttribute @ApiParam("请求参数") SaveOrUpdateOutOrderEnter enter) {
        return new Response<>(outboundOrderService.outOrderEdit(enter));
    }


    @PostMapping(value = "/submit")
    @ApiOperation(value = "提交", response = GeneralResult.class)
    public Response<GeneralResult> outOrderSubmit(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.outOrderSubmit(enter));
    }

    @PostMapping(value = "/relationCombinOrderData")
    @ApiOperation(value = "关联的组装单单据号下拉数据源,由组装单创建的出库单，只可能是部件出库单", response = InWhRelationOrderResult.class)
    public Response<List<InWhRelationOrderResult>> relationCombinOrderData(@ModelAttribute @ApiParam("请求参数") CombinationListEnter enter) {
        return new Response<>(outboundOrderService.relationCombinOrderData(enter));
    }


    @PostMapping(value = "/relationCombinOrderDataPartsData")
    @ApiOperation(value = "关联的组装单的产品信息，转为部件数据,由组装单创建的出库单，只可能是部件出库单", response = SaveOrUpdateOutPartsBEnter.class)
    public Response<List<SaveOrUpdateOutPartsBEnter>> relationCombinOrderDataPartsData(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.relationCombinOrderDataPartsData(enter));
    }


    @PostMapping(value = "/outWhConfirm")
    @ApiOperation(value = "出库单确认出库", response = GeneralResult.class)
    public Response<GeneralResult> outWhConfirm(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.outWhConfirm(enter));
    }


    /***************以下两个接口是模拟RPS操作,后续可删掉*****************/

    @PostMapping(value = "/startQc")
    @ApiOperation(value = "模拟RPS开始质检", response = GeneralResult.class)
    public Response<GeneralResult> startQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.startQc(enter));
    }

    @PostMapping(value = "/endQc")
    @ApiOperation(value = "模拟RPS质检完成", response = GeneralResult.class)
    public Response<GeneralResult> endQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.endQc(enter));
    }

}
