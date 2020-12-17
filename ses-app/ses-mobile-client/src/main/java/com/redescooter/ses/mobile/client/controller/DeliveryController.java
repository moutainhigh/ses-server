package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.mobile.b.service.DeliveryService;
import com.redescooter.ses.api.mobile.b.vo.CompleteEnter;
import com.redescooter.ses.api.mobile.b.vo.CompleteResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryDetailResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListEnter;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListResult;
import com.redescooter.ses.api.mobile.b.vo.RefuseEnter;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:DeliveryController
 * @description: DeliveryController
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 10:05
 */
@Slf4j
@Api(tags = {"餐厅订单模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/delivery", method = RequestMethod.POST)
public class DeliveryController {

    @Reference
    private DeliveryService deliveryService;

    @ApiOperation(value = "餐厅订单列表")
    @RequestMapping(value = "/list")
    public Response<DeliveryListResult> list(@ModelAttribute DeliveryListEnter enter) {
        return new Response<>(deliveryService.list(enter));
    }

    @ApiOperation(value = "订单详情")
    @RequestMapping(value = "/detail")
    public Response<DeliveryDetailResult> detail(@ModelAttribute IdEnter enter) {
        return new Response<>(deliveryService.detail(enter));
    }

    @ApiOperation(value = "开始订单")
    @RequestMapping(value = "/start")
    public Response<GeneralResult> start(@ModelAttribute @ApiParam("请求参数")StartEnter enter) {
        return new Response<>(deliveryService.start(enter));
    }

    @ApiOperation(value = "完成订单")
    @RequestMapping(value = "/complete")
    public Response<CompleteResult> complete(@ModelAttribute CompleteEnter enter) {
        return new Response<>(deliveryService.complete(enter));
    }

    @ApiOperation(value = "拒绝订单")
    @RequestMapping(value = "/refuse")
    public Response<GeneralResult> refuse(@ModelAttribute RefuseEnter enter) {
        return new Response<>(deliveryService.refuse(enter));
    }

}
