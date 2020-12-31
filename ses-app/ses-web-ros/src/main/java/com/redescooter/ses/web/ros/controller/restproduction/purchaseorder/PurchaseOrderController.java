package com.redescooter.ses.web.ros.controller.restproduction.purchaseorder;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder.PurchaseOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateNoDataResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateProductListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassNamePurchaseOrderController
 * @Description
 * @Author Aleks
 * @Date2020/10/23 18:56
 * @Version V1.0
 **/
@Api(tags = {"采购单模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/purchase/order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private AllocateOrderService allocateOrderService;


    @PostMapping(value = "/save")
    @ApiOperation(value = "采购单新增", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> purchaseSave(@ModelAttribute @ApiParam("请求参数") PurchaseSaveOrUpdateEnter enter) {
        return new Response<>(purchaseOrderService.purchaseSave(enter));
    }


    @PostMapping(value = "/edit")
    @ApiOperation(value = "采购单编辑", response = GeneralResult.class)
    public Response<GeneralResult> purchaseEdit(@ModelAttribute @ApiParam("请求参数") PurchaseSaveOrUpdateEnter enter) {
        return new Response<>(purchaseOrderService.purchaseEdit(enter));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "采购单列表", response = PuraseListResult.class)
    public Response<PageResult<PuraseListResult>> purchaseList(@ModelAttribute @ApiParam("请求参数") PuraseListEnter enter) {
        return new Response<>(purchaseOrderService.purchaseList(enter));
    }


    @PostMapping(value = "/detail")
    @ApiOperation(value = "采购单详情", response = PurchaseDetailResult.class)
    public Response<PurchaseDetailResult> purchaseDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchaseOrderService.purchaseDetail(enter));
    }


    @PostMapping(value = "/confirmOrder")
    @ApiOperation(value = "采购单立即下单", response = GeneralResult.class)
    public Response<GeneralResult> confirmOrder(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchaseOrderService.confirmOrder(enter));
    }


    @PostMapping(value = "/cancelOrder")
    @ApiOperation(value = "采购单取消订单", response = GeneralResult.class)
    public Response<GeneralResult> cancelOrder(@ModelAttribute @ApiParam("请求参数") CancelOrderEnter enter) {
        return new Response<>(purchaseOrderService.cancelOrder(enter));
    }


    @PostMapping(value = "/closeOrder")
    @ApiOperation(value = "采购单关闭订单", response = GeneralResult.class)
    public Response<GeneralResult> closeOrder(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchaseOrderService.closeOrder(enter));
    }


    @PostMapping(value = "/allocateNoData")
    @ApiOperation(value = "关联调拨单号下拉数据源接口", response = GeneralResult.class)
    public Response<List<AllocateNoDataResult>> allocateNoData(@ModelAttribute @ApiParam("请求参数") KeywordEnter enter) {
        return new Response<>(purchaseOrderService.allocateNoData(enter));
    }



    @PostMapping(value = "/allocateProductData")
    @ApiOperation(value = "调拨单的产品列表", response = AllocateProductListResult.class)
    public Response<AllocateProductListResult> allocateProductData(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateOrderService.allocateProductData(enter));
    }


    @PostMapping(value = "/listCount")
    @ApiOperation(value = "列表统计", response = Map.class)
    public Response<Map<String,Integer>> listCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(purchaseOrderService.listCount(enter));
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = "采购单删除", response = GeneralResult.class)
    public Response<GeneralResult> deleteOrder(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(purchaseOrderService.deleteOrder(enter));
    }

    @PostMapping(value = "/purchaseCalendar")
    @ApiOperation(value = "采购单日历", response = PurchaseCalendarResult.class)
    public Response<List<PurchaseCalendarResult>> purchaseCalendar(@ModelAttribute @ApiParam("请求参数") PurchaseCalendarEnter enter) {
        return new Response<>(purchaseOrderService.purchaseCalendar(enter));
    }



}
