package com.redescooter.ses.web.ros.service.stripe.impl;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.web.ros.SesWebRosApplicationTests;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class StripeServiceImplTest extends SesWebRosApplicationTests {

    @Autowired
    private StripeService stripeService;

    @Test
    public void testPaymentIntent() {

        StringResult result = stripeService.paymentIntent(new IdEnter(1004221l));
        log.info(result.getValue());

    }
}