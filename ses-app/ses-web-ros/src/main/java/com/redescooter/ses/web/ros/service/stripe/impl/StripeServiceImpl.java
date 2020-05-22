package com.redescooter.ses.web.ros.service.stripe.impl;

import com.google.gson.JsonSyntaxException;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.ApiResource;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Service
public class StripeServiceImpl implements StripeService {

    private final String integrationCheck = "integration_check";

    @Value("${stripe.secret_key}")
    private String API_SECRET_KEY;

    @Value("${stripe.receipt_email}")
    private String ReceiptEmail;

    @Value("${stripe.currency}")
    private String Currency;

    @Value("${stripe.payment_method_types}")
    private String PaymentMethodType;

    @Value("${stripe.payment_event}")
    private String PaymentEvent;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;
    @Reference
    private MailMultiTaskService mailMultiTaskService;


    @SneakyThrows
    @Override
    public StringResult paymentIntent(IdEnter enter) {

        StringResult result = new StringResult();

        Stripe.apiKey = API_SECRET_KEY;
        OpeCustomerInquiry payOrder = opeCustomerInquiryService.getById(enter.getId());

        if (payOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        map.put(integrationCheck, PaymentEvent);
        map.put("order_id", String.valueOf(payOrder.getId()));
        map.put("order_no", payOrder.getOrderNo());

        try {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setReceiptEmail(ReceiptEmail)
                            .setCurrency(Currency)
                            .addPaymentMethodType(PaymentMethodType)
                            .setAmount(payOrder.getTotalPrice().longValue())
                            .putAllMetadata(map)
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);
            result.setValue(intent.getClientSecret());

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return result;
    }

    /**
     * 收款成功
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public GeneralResult succeeHooks(Request request, Response response) {

        if (response == null || response.body()==null) {
            return new GeneralResult(String.valueOf(UUID.randomUUID()));
        }
        String payload = response.body();
        log.info("=============================");
        log.info("网络钩子数据回调===={}", payload);
        log.info("=============================");
        Event event = null;
        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            // Invalid payload
            response.status(400);
            return new GeneralResult(String.valueOf(UUID.randomUUID()));
        }
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataObjectDeserializer.getObject().get();

        PayResponseBody payResponseBody = generateResponse((PaymentIntent) stripeObject, new PayResponseBody());

        log.info("=============================");
        log.info(payResponseBody.toString());
        log.info("=============================");
        return new GeneralResult(String.valueOf(UUID.randomUUID()));
    }

    @Override
    public GeneralResult failHooks(Request request, Response response) {

        if (response == null || response.body()==null) {
            return new GeneralResult(String.valueOf(UUID.randomUUID()));
        }

        String payload = response.body();
        log.info("=============================");
        log.info("网络钩子数据回调===={}", payload);
        log.info("=============================");
        Event event = null;
        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            // Invalid payload
            response.status(400);
            return new GeneralResult(String.valueOf(UUID.randomUUID()));
        }
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataObjectDeserializer.getObject().get();

        PayResponseBody payResponseBody = generateResponse((PaymentIntent) stripeObject, new PayResponseBody());

        log.info("=============================");
        log.info(payResponseBody.toString());
        log.info("=============================");

        return new GeneralResult(String.valueOf(UUID.randomUUID()));
    }

    private PayResponseBody generateResponse(PaymentIntent intent, PayResponseBody response) {
        switch (intent.getStatus()) {
            case "requires_action":
            case "requires_source_action":
                // Card requires authentication
                response.setClientSecret(intent.getClientSecret());
                response.setRequiresAction(true);
                break;
            case "requires_payment_method":
            case "requires_source":
                // Card was not properly authenticated, suggest a new payment method
                response.setError("Your card was denied, please provide a new payment method");
                break;
            case "succeeded":
                //支付后续业务
                response.setClientSecret(intent.getClientSecret());

                Map<String, String> metadata = intent.getMetadata();
                metadata.forEach((k, v) -> {
                    log.info("====={}=================={}======", k, v);
                });

                paymentSuccess(metadata.get("order_id"));
                System.out.println("💰 Payment received!");

                break;
            default:
                response.setError("Unrecognized status");
        }
        return response;
    }

    /**
     * 支付成功进行订单数据保存
     *
     * @param id
     */
    private void paymentSuccess(String id) {
        //订单Id
        Long orderId = Long.valueOf(id);

        OpeCustomerInquiry customerInquiry = opeCustomerInquiryService.getById(orderId);
        if (customerInquiry == null) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        //订单数据保存
        customerInquiry.setPayStatus(InquiryStatusEnums.PAY_DEPOSIT.getValue());
        customerInquiry.setStatus(InquiryStatusEnums.PAY_DEPOSIT.getValue());
        customerInquiry.setUpdatedTime(new Date());
        opeCustomerInquiryService.updateById(customerInquiry);

        sendmail(customerInquiry.getEmail());
    }

    /*
     *  发送邮件
     *
     * */
    private void sendmail(String eamil) {
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
        mailMultiTaskService.subscriptionPaySucceedSendmail(enter);
    }

    static class PayResponseBody {
        private String clientSecret;
        private Boolean requiresAction;
        private String error;

        public PayResponseBody() {

        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public void setRequiresAction(Boolean requiresAction) {
            this.requiresAction = requiresAction;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

}

