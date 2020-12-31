package com.redescooter.ses.mobile.rps.controller.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.restproductionorder.outbound.OutBoundOrderService;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.ProductQcTempleteItemResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.QcTempleteEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.SaveQcResultEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow.ProductOutWhDetailEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.*;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:OutboundController
 * @description: OutboundController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/03 10:13 
 */
@Log4j2
@Api(tags = {"出库单信息"})
@CrossOrigin
@RestController
@RequestMapping(value = "/outWh")
public class OutboundController {

    @Resource
    private OutBoundOrderService outboundOrderService;

    @PostMapping(value = "/countByProductType")
    @ApiOperation(value = "产品类型统计", response = Map.class)
    public Response<Map<Integer,Integer>> countByProductType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(outboundOrderService.countByProductType(enter));
    }

    @PostMapping(value = "/countByOrderType")
    @ApiOperation(value = "单据类型统计", response = Map.class)
    public Response<Map<Integer,Integer>> countByOrderType(@ModelAttribute @ApiParam("请求参数") CountByOrderTypeParamDTO paramDTO) {
        return new Response<>(outboundOrderService.countByOrderType(paramDTO));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "单据列表", response = OutboundOrderResult.class)
    public Response<PageResult<OutboundOrderResult>> list(@ModelAttribute @ApiParam("请求参数") OutboundOrderEnter enter) {
        return new Response<>(outboundOrderService.list(enter));
    }

    @PostMapping(value = "/startQc")
    @ApiOperation(value = "开始质检", response = GeneralResult.class)
    public Response<GeneralResult> startQc(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.startQc(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = OutboundDetailResult.class)
    public Response<OutboundDetailResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(outboundOrderService.detail(enter));
    }

    @PostMapping(value = "/productOutWhDetail")
    @ApiOperation(value = "已出库产品详情", response = ProductDetailResult.class)
    public Response<ProductDetailResult> productOutWhDetail(@ModelAttribute @ApiParam("请求参数") ProductOutWhDetailEnter enter) {
        return new Response<>(outboundOrderService.productOutWhDetail(enter));
    }

    @PostMapping(value = "/qcTemplete")
    @ApiOperation(value = "质检模版", response = ProductQcTempleteItemResult.class)
    public Response<List<ProductQcTempleteItemResult>> qcTemplete(@ModelAttribute @ApiParam("请求参数") QcTempleteEnter enter) {
        return new Response<>(outboundOrderService.qcTemplete(enter));
    }

    @PostMapping(value = "/saveQcResult")
    @ApiOperation(value = "保存质检结果", response = BooleanResult.class)
    public Response<BooleanResult> saveQcResult(@ModelAttribute @ApiParam("请求参数") SaveQcResultEnter enter) {
        return new Response<>(outboundOrderService.saveQcResult(enter));
    }
}
