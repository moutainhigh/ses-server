package com.redescooter.ses.web.ros.service.stripe.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.web.ros.SesWebRosApplicationTests;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import com.redescooter.ses.web.ros.vo.website.payment.PaymentEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


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



    @Test
    public void gsonToObj() {
        String gson = "{\n" + "\t\"id\": \"evt_1GlRWdANSpAXANT53R8zuOFG\",\n" + "\t\"object\": \"event\",\n"
            + "\t\"api_version\": \"2020-03-02\",\n" + "\t\"created\": 1590117983,\n" + "\t\"data\": {\n"
            + "\t\t\"object\": {\n" + "\t\t\t\"id\": \"pi_1GlRWaANSpAXANT5vYnfsPFI\",\n"
            + "\t\t\t\"object\": \"payment_intent\",\n" + "\t\t\t\"amount\": 19000,\n"
            + "\t\t\t\"amount_capturable\": 0,\n" + "\t\t\t\"amount_received\": 19000,\n"
            + "\t\t\t\"application\": null,\n" + "\t\t\t\"application_fee_amount\": null,\n"
            + "\t\t\t\"canceled_at\": null,\n" + "\t\t\t\"cancellation_reason\": null,\n"
            + "\t\t\t\"capture_method\": \"automatic\",\n" + "\t\t\t\"charges\": {\n"
            + "\t\t\t\t\"object\": \"list\",\n" + "\t\t\t\t\"data\": [{\n"
            + "\t\t\t\t\t\"id\": \"ch_1GlRWbANSpAXANT56PFwcKv7\",\n" + "\t\t\t\t\t\"object\": \"charge\",\n"
            + "\t\t\t\t\t\"amount\": 19000,\n" + "\t\t\t\t\t\"amount_refunded\": 0,\n"
            + "\t\t\t\t\t\"application\": null,\n" + "\t\t\t\t\t\"application_fee\": null,\n"
            + "\t\t\t\t\t\"application_fee_amount\": null,\n"
            + "\t\t\t\t\t\"balance_transaction\": \"txn_1GlRWdANSpAXANT5aYYIPoSe\",\n"
            + "\t\t\t\t\t\"billing_details\": {\n" + "\t\t\t\t\t\t\"address\": {\n" + "\t\t\t\t\t\t\t\"city\": null,\n"
            + "\t\t\t\t\t\t\t\"country\": null,\n" + "\t\t\t\t\t\t\t\"line1\": null,\n"
            + "\t\t\t\t\t\t\t\"line2\": null,\n" + "\t\t\t\t\t\t\t\"postal_code\": null,\n"
            + "\t\t\t\t\t\t\t\"state\": null\n" + "\t\t\t\t\t\t},\n" + "\t\t\t\t\t\t\"email\": null,\n"
            + "\t\t\t\t\t\t\"name\": \"123\",\n" + "\t\t\t\t\t\t\"phone\": null\n" + "\t\t\t\t\t},\n"
            + "\t\t\t\t\t\"calculated_statement_descriptor\": \"RED ELECTRIC\",\n" + "\t\t\t\t\t\"captured\": true,\n"
            + "\t\t\t\t\t\"created\": 1590117981,\n" + "\t\t\t\t\t\"currency\": \"eur\",\n"
            + "\t\t\t\t\t\"customer\": null,\n" + "\t\t\t\t\t\"description\": null,\n"
            + "\t\t\t\t\t\"destination\": null,\n" + "\t\t\t\t\t\"dispute\": null,\n"
            + "\t\t\t\t\t\"disputed\": false,\n" + "\t\t\t\t\t\"failure_code\": null,\n"
            + "\t\t\t\t\t\"failure_message\": null,\n" + "\t\t\t\t\t\"fraud_details\": {},\n"
            + "\t\t\t\t\t\"invoice\": null,\n" + "\t\t\t\t\t\"livemode\": true,\n" + "\t\t\t\t\t\"metadata\": {\n"
            + "\t\t\t\t\t\t\"integration_check\": \"payment_intent.succeeded\",\n"
            + "\t\t\t\t\t\t\"order_id\": \"1012443\"\n" + "\t\t\t\t\t},\n" + "\t\t\t\t\t\"on_behalf_of\": null,\n"
            + "\t\t\t\t\t\"order\": null,\n" + "\t\t\t\t\t\"outcome\": {\n"
            + "\t\t\t\t\t\t\"network_status\": \"approved_by_network\",\n" + "\t\t\t\t\t\t\"reason\": null,\n"
            + "\t\t\t\t\t\t\"risk_level\": \"normal\",\n" + "\t\t\t\t\t\t\"seller_message\": \"Payment complete.\",\n"
            + "\t\t\t\t\t\t\"type\": \"authorized\"\n" + "\t\t\t\t\t},\n" + "\t\t\t\t\t\"paid\": true,\n"
            + "\t\t\t\t\t\"payment_intent\": \"pi_1GlRWaANSpAXANT5vYnfsPFI\",\n"
            + "\t\t\t\t\t\"payment_method\": \"pm_1GlRWaANSpAXANT587Br54sk\",\n"
            + "\t\t\t\t\t\"payment_method_details\": {\n" + "\t\t\t\t\t\t\"card\": {\n"
            + "\t\t\t\t\t\t\t\"brand\": \"visa\",\n" + "\t\t\t\t\t\t\t\"checks\": {\n"
            + "\t\t\t\t\t\t\t\t\"address_line1_check\": null,\n"
            + "\t\t\t\t\t\t\t\t\"address_postal_code_check\": null,\n" + "\t\t\t\t\t\t\t\t\"cvc_check\": \"pass\"\n"
            + "\t\t\t\t\t\t\t},\n" + "\t\t\t\t\t\t\t\"country\": \"CN\",\n" + "\t\t\t\t\t\t\t\"exp_month\": 1,\n"
            + "\t\t\t\t\t\t\t\"exp_year\": 2022,\n" + "\t\t\t\t\t\t\t\"fingerprint\": \"4Hu9gFt4zyqps4fD\",\n"
            + "\t\t\t\t\t\t\t\"funding\": \"credit\",\n" + "\t\t\t\t\t\t\t\"installments\": null,\n"
            + "\t\t\t\t\t\t\t\"last4\": \"5117\",\n" + "\t\t\t\t\t\t\t\"network\": \"visa\",\n"
            + "\t\t\t\t\t\t\t\"three_d_secure\": null,\n" + "\t\t\t\t\t\t\t\"wallet\": null\n" + "\t\t\t\t\t\t},\n"
            + "\t\t\t\t\t\t\"type\": \"card\"\n" + "\t\t\t\t\t},\n"
            + "\t\t\t\t\t\"receipt_email\": \"etienne@redescooter.fr\",\n"
            + "\t\t\t\t\t\"receipt_number\": \"1747-0335\",\n" + "\t\t\t\t\t\"receipt_url\": \"https://pay.stripe"
            + ".com/receipts/acct_1GiaKwANSpAXANT5/ch_1GlRWbANSpAXANT56PFwcKv7/rcpt_HK5iXLVWP6ZsUjo6YdXBzUzh4TGBFow"
            + "\",\n" + "\t\t\t\t\t\"refunded\": false,\n" + "\t\t\t\t\t\"refunds\": {\n"
            + "\t\t\t\t\t\t\"object\": \"list\",\n" + "\t\t\t\t\t\t\"data\": [],\n"
            + "\t\t\t\t\t\t\"has_more\": false,\n" + "\t\t\t\t\t\t\"total_count\": 0,\n"
            + "\t\t\t\t\t\t\"url\": \"/v1/charges/ch_1GlRWbANSpAXANT56PFwcKv7/refunds\"\n" + "\t\t\t\t\t},\n"
            + "\t\t\t\t\t\"review\": null,\n" + "\t\t\t\t\t\"shipping\": null,\n" + "\t\t\t\t\t\"source\": null,\n"
            + "\t\t\t\t\t\"source_transfer\": null,\n" + "\t\t\t\t\t\"statement_descriptor\": null,\n"
            + "\t\t\t\t\t\"statement_descriptor_suffix\": null,\n" + "\t\t\t\t\t\"status\": \"succeeded\",\n"
            + "\t\t\t\t\t\"transfer_data\": null,\n" + "\t\t\t\t\t\"transfer_group\": null\n" + "\t\t\t\t}],\n"
            + "\t\t\t\t\"has_more\": false,\n" + "\t\t\t\t\"total_count\": 1,\n"
            + "\t\t\t\t\"url\": \"/v1/charges?payment_intent=pi_1GlRWaANSpAXANT5vYnfsPFI\"\n" + "\t\t\t},\n"
            + "\t\t\t\"client_secret\": \"pi_1GlRWaANSpAXANT5vYnfsPFI_secret_iGhBsJo75PhkqJAJDzqGgSV6x\",\n"
            + "\t\t\t\"confirmation_method\": \"automatic\",\n" + "\t\t\t\"created\": 1590117980,\n"
            + "\t\t\t\"currency\": \"eur\",\n" + "\t\t\t\"customer\": null,\n" + "\t\t\t\"description\": null,\n"
            + "\t\t\t\"invoice\": null,\n" + "\t\t\t\"last_payment_error\": null,\n" + "\t\t\t\"livemode\": true,\n"
            + "\t\t\t\"metadata\": {\n" + "\t\t\t\t\"order_no\": \"\",\n"
            + "\t\t\t\t\"integration_check\": \"payment_intent.succeeded\",\n" + "\t\t\t\t\"order_id\": \"1012443\"\n"
            + "\t\t\t},\n" + "\t\t\t\"next_action\": null,\n" + "\t\t\t\"on_behalf_of\": null,\n"
            + "\t\t\t\"payment_method\": \"pm_1GlRWaANSpAXANT587Br54sk\",\n" + "\t\t\t\"payment_method_options\": {\n"
            + "\t\t\t\t\"card\": {\n" + "\t\t\t\t\t\"installments\": null,\n"
            + "\t\t\t\t\t\"request_three_d_secure\": \"automatic\"\n" + "\t\t\t\t}\n" + "\t\t\t},\n"
            + "\t\t\t\"payment_method_types\": [\n" + "\t\t\t\t\"card\"\n" + "\t\t\t],\n"
            + "\t\t\t\"receipt_email\": \"etienne@redescooter.fr\",\n" + "\t\t\t\"review\": null,\n"
            + "\t\t\t\"setup_future_usage\": null,\n" + "\t\t\t\"shipping\": null,\n" + "\t\t\t\"source\": null,\n"
            + "\t\t\t\"statement_descriptor\": null,\n" + "\t\t\t\"statement_descriptor_suffix\": null,\n"
            + "\t\t\t\"status\": \"succeeded\",\n" + "\t\t\t\"transfer_data\": null,\n"
            + "\t\t\t\"transfer_group\": null\n" + "\t\t}\n" + "\t},\n" + "\t\"livemode\": true,\n"
            + "\t\"pending_webhooks\": 2,\n" + "\t\"request\": {\n" + "\t\t\"id\": \"req_OE4j79FoO99Wad\",\n"
            + "\t\t\"idempotency_key\": null\n" + "\t},\n" + "\t\"type\": \"payment_intent.succeeded\"\n" + "}";

        PaymentEnter paymentEnter = JSON.parseObject(gson, PaymentEnter.class);
        System.out.println(paymentEnter);
    }

}
