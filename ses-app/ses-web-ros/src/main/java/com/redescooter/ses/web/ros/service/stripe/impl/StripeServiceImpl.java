package com.redescooter.ses.web.ros.service.stripe.impl;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import com.redescooter.ses.web.ros.service.website.WebsiteOrderFormService;
import com.redescooter.ses.web.ros.vo.website.OrderFormInfoResult;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.test.secret_key}")
    private String API_SECRET_KEY;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @SneakyThrows
    @Override
    public StringResult paymentIntent(IdEnter enter) {

        StringResult result = new StringResult();

        Stripe.apiKey = API_SECRET_KEY;
        log.info("测试环境证书为===={}",API_SECRET_KEY);

        OpeCustomerInquiry payOrder = opeCustomerInquiryService.getById(enter.getId());

        if(payOrder==null){
            return result;
        }

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setCurrency("eur")
                        .setAmount(payOrder.getTotalPrice().longValue())
                        .putMetadata("integration_check", "accept_a_payment")
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
