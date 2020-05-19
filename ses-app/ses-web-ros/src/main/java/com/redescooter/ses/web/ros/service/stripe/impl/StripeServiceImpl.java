package com.redescooter.ses.web.ros.service.stripe.impl;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    @SneakyThrows
    @Override
    public StringResult paymentIntent(IdEnter enter) {

        StringResult result = new StringResult();

        Stripe.apiKey = API_SECRET_KEY;
        log.info("====================================");
        log.info("API_SECRET_KEY===={}", API_SECRET_KEY);
        log.info("ReceiptEmail===={}", ReceiptEmail);
        log.info("Currency===={}", Currency);
        log.info("PaymentMethodType===={}", PaymentMethodType);
        log.info("PaymentEvent===={}", PaymentEvent);
        log.info("====================================");

        OpeCustomerInquiry payOrder = opeCustomerInquiryService.getById(enter.getId());

        if (payOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getMessage());
        }

        Map<String, String> map = new HashMap<>();
        map.put(integrationCheck, PaymentEvent);
        map.put("order_id", String.valueOf(payOrder.getId()));
        map.put("order_no", payOrder.getOrderNo());

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
        return result;
    }

//    @Override
//    public void webhookPaymentIntent(Request request, Response response) {
////        String payload = request.body();
////        String sigHeader = request.headers("Stripe-Signature");
////        String endpointSecret = dotenv.get("STRIPE_WEBHOOK_SECRET");
//
//    }
}
