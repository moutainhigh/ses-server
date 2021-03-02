package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PublicSecretResult;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.StripePaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class StripePaymentServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private StripePaymentService stripePaymentService;

    @Test
    void paymentIntent() {
        IdEnter enter = new IdEnter();
        enter.setId(196138743107584l);
        StringResult stringResult = stripePaymentService.paymentIntent(enter);
        System.out.println(stringResult);
    }

    @Test
    void testPaymentIntent() {
    }

    @Test
    void succeeHooks() {
    }

    @Test
    void cancelledPaymentIntent() {
    }

    @Test
    void failHooks() {
    }

    @Test
    void publicSecret() {
        PublicSecretResult publicSecretResult = stripePaymentService.publicSecret();
        System.out.println(publicSecretResult.toString());
    }
}