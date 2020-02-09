package com.redescooter.ses.mobile.client.controller.express;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.mobile.b.service.express.EdOrderService;
import com.redescooter.ses.api.mobile.b.vo.*;
import com.redescooter.ses.api.mobile.b.vo.express.OrderResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"快递订单模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/ed/order", method = RequestMethod.POST)
public class EdExpressOrderController {

    private EdOrderService edOrderService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "订单列表", response = OrderResult.class)
    public Response<List<OrderResult>> orderList(@ModelAttribute @ApiParam("请求参数") DeliveryListEnter enter) {
        return new Response<>(edOrderService.orderList(enter));
    }


    @PostMapping(value = "/start")
    @ApiOperation(value = "开始订单", response = GeneralResult.class)
    public Response<GeneralResult> start(@ModelAttribute @ApiParam("请求参数") StartEnter enter) {
        return new Response<>(edOrderService.start(enter));
    }

    @PostMapping(value = "/refuse")
    @ApiOperation(value = "拒绝订单", response = GeneralResult.class)
    public Response<GeneralResult> refuse(@ModelAttribute @ApiParam("请求参数") RefuseEnter enter) {
        return new Response<>(edOrderService.refuse(enter));
    }

    @PostMapping(value = "/complete")
    @ApiOperation(value = "完成订单", response = CompleteResult.class)
    public Response<CompleteResult> complete(@ModelAttribute @ApiParam("请求参数") CompleteEnter enter) {
        return new Response<>(edOrderService.complete(enter));
    }

}
