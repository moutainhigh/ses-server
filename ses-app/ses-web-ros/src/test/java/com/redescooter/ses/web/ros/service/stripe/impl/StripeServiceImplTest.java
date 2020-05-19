package com.redescooter.ses.web.ros.service.stripe.impl;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.web.ros.SesWebRosApplicationTests;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class StripeServiceImplTest extends SesWebRosApplicationTests {

    @Value("${stripe.secret_key}")
    private String API_SECRET_KEY;

    @Autowired
    private StripeService stripeService;

    /**
     * 支付授权单元测试
     */
    @Test
    public void testPaymentIntent() {

        StringResult result = stripeService.paymentIntent(new IdEnter(1004221l));

        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("result===={}", result.getValue());
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


    }




}