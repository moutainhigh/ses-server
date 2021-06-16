package com.redescooter.ses.web.website.controller.business;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.OrderService;
import com.redescooter.ses.web.website.vo.order.AddOrderPartsEnter;
import com.redescooter.ses.web.website.vo.order.AddOrderResult;
import com.redescooter.ses.web.website.vo.order.AddUpdateOrderEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @ApiOperation(value = "Add", response = AddOrderResult.class)
    public Response<AddOrderResult> add(@ModelAttribute AddUpdateOrderEnter enter) {
        return new Response<>(orderService.addOrder(enter));
    }

    @PostMapping(value = "/addOrderParts")
    @ApiOperation(value = "AddOrderParts", response = GeneralResult.class)
    public Response<GeneralResult> AddOrderParts(@ModelAttribute @ApiParam("请求参数") AddOrderPartsEnter enter) {
        return new Response<>(orderService.AddOrderParts(enter));
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "Order List", response = OrderDetailsResult.class)
    public Response<List<OrderDetailsResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(orderService.getOrderList(enter));
    }

    @GetMapping(value = "/details")
    @ApiOperation(value = "Order Details", response = OrderDetailsResult.class)
    public Response<OrderDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(orderService.getOrderDetails(enter));
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = "delete", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute IdEnter enter) {
        return new Response<>(orderService.deleteOrder(enter));
    }
}
