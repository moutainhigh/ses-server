package com.redescooter.ses.web.ros.controller.restproduction.invoice;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.invoice.InvoiceOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.SaveInvoiceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.QueryStaffResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"发货单"})
@CrossOrigin
@RestController
@RequestMapping(value = "/restproduction/invoice")
public class InvocieController {
    @Autowired
    private InvoiceOrderService invoiceOrderService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "产品类型统计", response = Map.class)
    public Response<Map<Integer, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(invoiceOrderService.countByType(enter));
    }

    @PostMapping(value = "/statusList")
    @ApiOperation(value = "状态列表", response = Map.class)
    public Response<Map<Integer, String>> statusList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(invoiceOrderService.statusList(enter));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "列表", response = InvoiceOrderListResult.class)
    public Response<PageResult<InvoiceOrderListResult>> list(@ModelAttribute @ApiParam("请求参数") InvoiceOrderListEnter enter) {
        return new Response<>(invoiceOrderService.list(enter));
    }


    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = InvoiceOrderDetailResult.class)
    public Response<InvoiceOrderDetailResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(invoiceOrderService.detail(enter));
    }

    @PostMapping(value = "/stockUp")
    @ApiOperation(value = "备料", response = Map.class)
    public Response<GeneralResult> stockUp(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(invoiceOrderService.stockUp(enter));
    }

    @PostMapping(value = "/staffList")
    @ApiOperation(value = "员工选择列表", response = QueryStaffResult.class)
    public Response<List<QueryStaffResult>> staffList(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        return new Response<>(invoiceOrderService.staffList(enter));
    }

    @PostMapping(value = "/loading")
    @ApiOperation(value = "装车", response = Map.class)
    public Response<GeneralResult> loading(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(invoiceOrderService.loading(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", response = GeneralResult.class)
    public Response<GeneralResult> detail(@ModelAttribute @ApiParam("请求参数") SaveInvoiceEnter enter) {
        return new Response<>(invoiceOrderService.save(enter));
    }


}
