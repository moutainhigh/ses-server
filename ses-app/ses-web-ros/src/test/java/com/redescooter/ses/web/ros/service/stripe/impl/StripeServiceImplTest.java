package com.redescooter.ses.web.ros.service.stripe.impl;

import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.web.ros.SesWebRosApplicationTests;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
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
    @Reference
    private MailMultiTaskService mailMultiTaskService;
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
  @Test
  public void sendmail(){
      String eamil="joan@redescooter.com";
    String name = eamil.substring(0, eamil.indexOf("@"));
    BaseMailTaskEnter enter = new BaseMailTaskEnter();
    enter.setName(name);
    enter.setEvent(MailTemplateEventEnums.SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL.getName());
    enter.setMailAppId(AppIDEnums.SES_ROS.getSystemId());
    enter.setMailSystemId(SystemIDEnums.REDE_SES.getValue());
    enter.setToMail(eamil);
    enter.setCode("0");
    enter.setRequestId("0");
    enter.setUserRequestId("0");
    enter.setToUserId(0L);
    enter.setUserId(0L);
    mailMultiTaskService.subscriptionPaySucceedSendmail(enter);  }



}
