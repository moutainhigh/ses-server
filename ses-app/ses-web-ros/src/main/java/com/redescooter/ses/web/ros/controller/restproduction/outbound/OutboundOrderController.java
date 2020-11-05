package com.redescooter.ses.web.ros.controller.restproduction.outbound;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.outbound.OutboundOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.SaveOutboundOrderEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"出库单"})
@CrossOrigin
@RestController
@RequestMapping(value = "/restproduction/outbound")
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

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", response = GeneralResult.class)
    public Response<GeneralResult> detail(@ModelAttribute @ApiParam("请求参数") SaveOutboundOrderEnter enter) {
        return new Response<>(outboundOrderService.save(enter));
    }


    @PostMapping(value = "/startQc")
    @ApiOperation(value = "模拟RPS开始质检", response = GeneralResult.class)
    public Response<GeneralResult> startQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.startQc(enter));
    }


    @PostMapping(value = "/endQc")
    @ApiOperation(value = "模拟RPS质检完成", response = GeneralResult.class)
    public Response<GeneralResult> endQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.detail(enter));
    }



}
