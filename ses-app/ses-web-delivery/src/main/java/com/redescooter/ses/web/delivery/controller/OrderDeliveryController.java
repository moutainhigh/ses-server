package com.redescooter.ses.web.delivery.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 11:30 上午
 * @ClassName: OrderDeliveryController
 * @Function: TODO
 */
@Api(tags = {"Order-Delivery-API"})
@CrossOrigin
@RestController
@RequestMapping(value = "/order/delivery", method = RequestMethod.POST)
public class OrderDeliveryController {

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = GeneralResult.class)
    public Response<GeneralResult> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "配送单列表", response = GeneralResult.class)
    public Response<GeneralResult> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建配送单", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "配送单编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "配送单详情", response = GeneralResult.class)
    public Response<GeneralResult> details(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }
}
