package com.redescooter.ses.web.ros.service.stripe.impl;

import com.google.gson.JsonSyntaxException;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.net.ApiResource;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import static com.redescooter.ses.tool.utils.http.HttpUtils.post;

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

    /**
     * 收款成功
     * @param request
     * @param response
     * @return
     */
    @Override
    public GeneralResult succeeHooks(Request request, Response response) {
        String payload = request.body();

        log.info("网络钩子数据回调===={}", payload);

        Event event = null;

        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            // Invalid payload
            response.status(400);
            return new GeneralResult();
        }

        // Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }

        /*************************************************************************************/
        PayResponseBody payResponseBody = generateResponse((PaymentIntent) stripeObject, new PayResponseBody());
        log.info(payResponseBody.toString());
        /*************************************************************************************/

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                // Then define and call a method to handle the successful payment intent.
                // handlePaymentIntentSucceeded(paymentIntent);
                break;
            case "payment_method.attached":
                PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                // Then define and call a method to handle the successful attachment of a PaymentMethod.
                // handlePaymentMethodAttached(paymentMethod);
                break;
            // ... handle other event types
            default:
                // Unexpected event type
                response.status(400);
                return new GeneralResult();
        }

        response.status(200);
        return new GeneralResult();
    }

    @Override
    public GeneralResult failHooks(Request request, Response response) {
        String payload = request.body();

        log.info("网络钩子数据回调===={}", payload);

        Event event = null;

        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            // Invalid payload
            response.status(400);
            return new GeneralResult();
        }

        // Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }

        /*************************************************************************************/
        PayResponseBody payResponseBody = generateResponse((PaymentIntent) stripeObject, new PayResponseBody());
        log.info(payResponseBody.toString());
        /*************************************************************************************/

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                // Then define and call a method to handle the successful payment intent.
                // handlePaymentIntentSucceeded(paymentIntent);
                break;
            case "payment_method.attached":
                PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                // Then define and call a method to handle the successful attachment of a PaymentMethod.
                // handlePaymentMethodAttached(paymentMethod);
                break;
            // ... handle other event types
            default:
                // Unexpected event type
                response.status(400);
                return new GeneralResult();
        }

        response.status(200);
        return new GeneralResult();
    }

    static PayResponseBody generateResponse(PaymentIntent intent, PayResponseBody response) {
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
                System.out.println("💰 Payment received!");
                // Payment is complete, authentication not required
                // To cancel the payment you will need to issue a Refund
                // (https://stripe.com/docs/api/refunds)
                response.setClientSecret(intent.getClientSecret());
                break;
            default:
                response.setError("Unrecognized status");
        }
        return response;
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

