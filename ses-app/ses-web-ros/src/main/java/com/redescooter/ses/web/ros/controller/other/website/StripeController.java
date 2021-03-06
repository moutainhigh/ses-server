package com.redescooter.ses.web.ros.controller.other.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/stripe", method = RequestMethod.POST)
@Api(tags = {"Stripe Payment"})
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @WebsiteSignIn
    @PostMapping(value = "/paymentIntent")
    @ApiOperation(value = "获取密钥", response = StringResult.class)
    public Response<StringResult> paymentIntent(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(stripeService.paymentIntent(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/succeeHooks")
    @ApiOperation(value = "成功钩子")
    public Response<GeneralResult> succeeHooks(@RequestBody  String enter) {
        return new Response<>(stripeService.succeeHooks(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/failHooks")
    @ApiOperation(value = "失败钩子")
    @ResponseBody
    public Response<GeneralResult> failHooks(@RequestBody String enter) {
        return new Response<>(stripeService.failHooks(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/canceledHooks")
    @ApiOperation(value = "取消钩子")
    @ResponseBody
    public Response<GeneralResult> canceledHooks(@RequestBody String enter) {
        return new Response<>(stripeService.cancelledPaymentIntent(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/publicSecret")
    @ApiOperation(value = "获取公钥", response = StringResult.class)
    public Response<PublicSecretResult> publicSecret() {
        return new Response(stripeService.publicSecret());
    }

}
