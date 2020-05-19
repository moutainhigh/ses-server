package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Log4j2
@Api(tags = {"Stripe支付"})
@CrossOrigin
@RestController
@RequestMapping(value = "/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping(value = "/paymentIntent")
    @ApiOperation(value = "获取client_secret", response = StringResult.class)
    public Response<StringResult> paymentIntent(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(stripeService.paymentIntent(enter));
    }

//    @PostMapping(value = "/webhookPaymentIntent")
//    @ApiOperation(value = "结果验证", response = ProductResult.class)
//    public void webhookPaymentIntent(Request request, Response response) {
//        stripeService.webhookPaymentIntent(request, response);
//    }

}
