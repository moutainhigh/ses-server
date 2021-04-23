package com.redescooter.ses.web.website.controller.business;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.website.service.StripePaymentService;
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
public class SiteStripeController {

    @Autowired
    private StripePaymentService stripePaymentService;

    @PostMapping(value = "/paymentIntent")
    @ApiOperation(value = "Get [client_secret]", response = StringResult.class)
    public Response<StringResult> paymentIntent(@ModelAttribute IdEnter enter) {
        return new Response<>(stripePaymentService.paymentIntent(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/succeeHooks")
    @ApiOperation(value = "Succee Hooks")
    public Response<GeneralResult> succeeHooks(@RequestBody String enter) {
        return new Response<>(stripePaymentService.succeeHooks(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/failHooks")
    @ApiOperation(value = "Fail Hooks")
    @ResponseBody
    public Response<GeneralResult> failHooks(@RequestBody String enter) {
        return new Response<>(stripePaymentService.failHooks(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/canceledHooksWebsite")
    @ApiOperation(value = "Canceled Hooks")
    @ResponseBody
    public Response<GeneralResult> canceledHooks(@RequestBody String enter) {
        return new Response<>(stripePaymentService.cancelledPaymentIntent(enter));
    }


    @IgnoreLoginCheck
    @PostMapping(value = "/payAgainCheck")
    @ApiOperation(value = "再次支付", response = BooleanResult.class)
    public Response<BooleanResult> payAgainCheck(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(stripePaymentService.payAgainCheck(enter));
    }

}
