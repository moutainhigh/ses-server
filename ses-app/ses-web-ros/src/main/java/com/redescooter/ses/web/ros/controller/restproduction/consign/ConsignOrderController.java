package com.redescooter.ses.web.ros.controller.restproduction.consign;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.consign.ConsignOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.consignorder.ConsignOrderListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"委托单"})
@CrossOrigin
@RestController
@RequestMapping(value = "/restproduction/consign")
public class ConsignOrderController {
    @Autowired
    private ConsignOrderService consignOrderService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "产品类型统计", response = Map.class)
    public Response<Map<Integer, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(consignOrderService.countByType(enter));
    }

    @PostMapping(value = "/statusList")
    @ApiOperation(value = "状态列表", response = Map.class)
    public Response<Map<Integer, Integer>> statusList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(consignOrderService.statusList(enter));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "列表", response = ConsignOrderListResult.class)
    public Response<PageResult<ConsignOrderListResult>> list(@ModelAttribute @ApiParam("请求参数") ConsignOrderListEnter enter) {
        return new Response<>(consignOrderService.list(enter));
    }


    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = ConsignOrderDetailResult.class)
    public Response<ConsignOrderDetailResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(consignOrderService.detail(enter));
    }

    @PostMapping(value = "/signFor")
    @ApiOperation(value = "签收", response = GeneralResult.class)
    public Response<GeneralResult> signFor(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(consignOrderService.signFor(enter));
    }

}
