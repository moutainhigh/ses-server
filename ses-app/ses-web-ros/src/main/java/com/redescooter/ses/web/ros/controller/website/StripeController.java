package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"Stripe支付"})
@CrossOrigin
@RestController
@RequestMapping(value = "/stripe", method = RequestMethod.POST)
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @WebsiteSignIn
    @PostMapping(value = "/paymentIntent")
    @ApiOperation(value = "获取client_secret", response = StringResult.class)
    public Response<StringResult> paymentIntent(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(stripeService.paymentIntent(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/succeeHooks")
    @ApiOperation(value = "成功钩子")
    public Response<GeneralResult> succeeHooks(spark.Request request, spark.Response response) {
        return new Response<>(stripeService.succeeHooks(request, response));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/failHooks")
    @ApiOperation(value = "失败钩子")
    @ResponseBody
    public Response<GeneralResult> failHooks(spark.Request request, spark.Response response) {
        return new Response<>(stripeService.failHooks(request, response));
    }

}
