package com.redescooter.ses.web.website.controller.business;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IdResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.OrderService;
import com.redescooter.ses.web.website.vo.order.AddOrderEnter;
import com.redescooter.ses.web.website.vo.order.AddOrderPartsEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 13/1/2020 4:43 下午
 * @ClassName: ProductModelController
 * @Function: 产品型号服务
 */
@Api(tags = {"Order Management"})
@CrossOrigin
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "Add", response = IdResult.class)
    public Response<IdResult> add(@ModelAttribute @ApiParam("请求参数") AddOrderEnter enter) {
        return new Response<>(orderService.addOrder(enter));
    }

    @PostMapping(value = "/addOrderParts")
    @ApiOperation(value = "AddOrderParts", response = GeneralResult.class)
    public Response<GeneralResult> AddOrderParts(@ModelAttribute @ApiParam("请求参数") AddOrderPartsEnter enter) {
        return new Response<>(orderService.AddOrderParts(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "Order Details", response = OrderDetailsResult.class)
    public Response<OrderDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(orderService.getOrderDetails(enter));
    }
}
