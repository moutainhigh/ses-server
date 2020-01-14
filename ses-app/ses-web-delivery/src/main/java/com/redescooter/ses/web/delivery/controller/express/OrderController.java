package com.redescooter.ses.web.delivery.controller.express;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 10:52 上午
 * @ClassName: OrderController
 * @Function: TODO
 */
@Api(tags = {"OrderController"})
@CrossOrigin
@RestController
@RequestMapping(value = "/express/orders", method = RequestMethod.POST)
public class OrderController {


    @PostMapping(value = "/importOrders")
    @ApiOperation(value = "订单导入", response = GeneralResult.class)
    public Response<GeneralResult> importOrders(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "订单列表", response = PageResult.class)
    public Response<PageResult<GeneralResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "订单详情", response = GeneralResult.class)
    public Response<GeneralResult> details(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }
}
