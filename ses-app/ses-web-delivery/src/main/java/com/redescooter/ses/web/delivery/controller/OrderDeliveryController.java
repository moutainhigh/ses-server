package com.redescooter.ses.web.delivery.controller;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.service.OrderDeliveryService;
import com.redescooter.ses.web.delivery.vo.DeliveryDetailsResult;
import com.redescooter.ses.web.delivery.vo.ListDeliveryPage;
import com.redescooter.ses.web.delivery.vo.ListDeliveryResult;
import com.redescooter.ses.web.delivery.vo.SaveOrderDeliveryEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 11:30 上午
 * @ClassName: OrderDeliveryController
 * @Function: TODO
 */
@Api(tags = {"OrderDeliveryController"})
@CrossOrigin
@RestController
@RequestMapping(value = "/order/delivery", method = RequestMethod.POST)
public class OrderDeliveryController {

    @Autowired
    private OrderDeliveryService orderDeliveryService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(orderDeliveryService.countStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "配送单列表", response = ListDeliveryResult.class)
    public Response<PageResult<ListDeliveryResult>> list(@ModelAttribute @ApiParam("请求参数") ListDeliveryPage page) {
        return new Response<>(orderDeliveryService.list(page));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建配送单", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveOrderDeliveryEnter enter) {
        return new Response<>(orderDeliveryService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "配送单编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") SaveOrderDeliveryEnter enter) {
        return new Response<>(orderDeliveryService.save(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "配送单详情", response = DeliveryDetailsResult.class)
    public Response<DeliveryDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(orderDeliveryService.details(enter));
    }
}
