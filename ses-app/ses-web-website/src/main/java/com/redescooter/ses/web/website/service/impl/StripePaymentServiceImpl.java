package com.redescooter.ses.web.website.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonSyntaxException;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.hub.service.operation.CustomerInquiryService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.web.website.config.RequestsKeyProperties;
import com.redescooter.ses.web.website.config.StripeConfigProperties;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteCustomer;
import com.redescooter.ses.web.website.dm.SiteOrder;
import com.redescooter.ses.web.website.dm.SitePaymentRecords;
import com.redescooter.ses.web.website.dm.SiteProductModel;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.enums.PaymentStatusEnums;
import com.redescooter.ses.web.website.enums.SiteOrderStatusEnums;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.StripePaymentService;
import com.redescooter.ses.web.website.service.base.*;
import com.redescooter.ses.web.website.vo.stripe.PayResponseBody;
import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.ApiResource;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author jerry
 * @Date 2021/1/23 11:55 下午
 * @Description Stripe第三方支付实现类
 **/
@Slf4j
@Service
public class StripePaymentServiceImpl implements StripePaymentService {

    private final String integrationCheck = "integration_check";

    @Value("${rede.stripe.secret_key}")
    private String API_SECRET_KEY;

    @Value("${rede.stripe.receipt_email}")
    private String ReceiptEmail;

    @Value("${rede.stripe.currency}")
    private String Currency;

    @Value("${rede.stripe.payment_method_types}")
    private String PaymentMethodType;

    @Value("${rede.stripe.payment_event}")
    private String PaymentEvent;

    @Autowired
    private StripeConfigProperties stripeConfigProperties;

    @Autowired
    private RequestsKeyProperties requestsKeyProperties;

    @Autowired
    private SiteOrderService siteOrderService;

    @Autowired
    private SiteCustomerService siteCustomerService;

    @Autowired
    private SitePaymentRecordsService sitePaymentRecordsService;

    @Autowired
    private SiteProductModelService siteProductModelService;

    @Autowired
    private SiteProductService siteProductService;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private CustomerInquiryService customerInquiryService;

    /**
     * 支付
     *
     * @param enter
     * @return
     */
    @Override
    public StringResult paymentIntent(IdEnter enter) {
        StringResult result = new StringResult();

        Stripe.apiKey = API_SECRET_KEY;
        log.info(Stripe.apiKey+"{>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>}");
        SiteOrder order = siteOrderService.getById(enter.getId());

        if (order == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        map.put(integrationCheck, PaymentEvent);
        map.put("order_id", String.valueOf(order.getId()));
        map.put("order_no", order.getOrderNo());
        map.put("product_id", String.valueOf(order.getProductId()));

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setReceiptEmail(ReceiptEmail)
                    .setCurrency(Currency).addPaymentMethodType(PaymentMethodType)
                    /**欧元转换欧分**/
                    .setAmount(order.getPrepaidDeposit().multiply(new BigDecimal("100")).longValue())
                    .putAllMetadata(map)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            String clientSecret = intent.getClientSecret();
            log.info("===========" + clientSecret);
            result.setValue(clientSecret);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return result;
    }

    /**
     * 网络钩子 成功时
     *
     * @param stripeJson
     */
    @Override
    public GeneralResult succeeHooks(String stripeJson) {
        if (ObjectUtil.isNull(stripeJson)) {
            throw new SesWebsiteException(ExceptionCodeEnums.STRIPE_RESPONSE_EXIST.getCode(), ExceptionCodeEnums.STRIPE_RESPONSE_EXIST.getMessage());
        }
        if (JSONObject.parseObject(stripeJson).size() == 0) {
            throw new SesWebsiteException(ExceptionCodeEnums.STRIPE_RESPONSE_EXIST.getCode(), ExceptionCodeEnums.STRIPE_RESPONSE_EXIST.getMessage());
        }
        String payload = stripeJson;
        log.info("=============================");
        log.info("::::::网络钩子数据回调===={}", payload);
        log.info("=============================");

        //事件类型
        Event event = null;
        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            // Invalid payload
            log.error("===支付失败===", e.getMessage());

            //TODO 邮件通知，订单支付失败
            return new GeneralResult(String.valueOf(UUID.randomUUID()));
        }
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataObjectDeserializer.getObject().get();
        generateResponse((PaymentIntent) stripeObject, new PayResponseBody(), stripeJson);

        return new GeneralResult(String.valueOf(UUID.randomUUID()));
    }

    /**
     * 取消支付的勾子
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult cancelledPaymentIntent(String enter) {
        return null;
    }

    /**
     * 网络钩子 失败时
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult failHooks(String enter) {
        return null;
    }

    /**
     * @return
     * @Author Aleks
     * @Description 获取加密之后的公钥
     * @Date 2020/7/20 15:52
     * @Param
     **/
    @Override
    public PublicSecretResult publicSecret() {
        PublicSecretResult result = new PublicSecretResult();
        String secret = requestsKeyProperties.getPublicKey();
        String front = secret.substring(0, secret.length() / 2);
        String behind = secret.substring(secret.length() / 2, secret.length());
        String whole = front + behind;

        System.out.println(whole);
        try {
            front = RsaUtils.encrypt(front, requestsKeyProperties.getPublicKey());
            behind = RsaUtils.encrypt(behind, requestsKeyProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setFront(front);
        result.setBehind(behind);
        return result;
    }

    /**
     * 在次支付校验
     *
     * @param enter
     * @return
     */
    @Override
    public BooleanResult payAgainCheck(IdEnter enter) {
       return customerInquiryService.payAgainCheck(enter);
    }

    private PayResponseBody generateResponse(PaymentIntent intent, PayResponseBody response, String stripeJson) {
        switch (intent.getStatus()) {
            case "requires_action":
            case "requires_source_action":
                // Card requires authentication
                response.setClientSecret(intent.getClientSecret());
                response.setRequiresAction(true);
                break;
            case "requires_payment_method":
            case "requires_source":
                response.setError("Your card was denied, please provide a new payment method");
                break;
            case "succeeded":
                // 支付后续业务
                response.setClientSecret(intent.getClientSecret());

                Map<String, String> metadata = intent.getMetadata();
                metadata.forEach((k, v) -> {
                    log.info("====={}=================={}======", k, v);
                });
                //订单支付成功后，走后续业务
                paymentSuccess(Long.valueOf(metadata.get("order_id")), stripeJson);
                log.info("<<<<<<<<<<💰 Payment received!>>>>>>>>");
                break;
            // 付款失败
            case "faild":
                break;
            // 取消付款
            case "canceled":
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
    private void paymentSuccess(Long id, String stripeJson) {

        SiteOrder siteOrder = siteOrderService.getById(id);
        if (ObjectUtil.isNull(siteOrder)) {
            //TODO 邮件发送给我
            throw new SesWebsiteException(ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getMessage());
        }
        //获取已付金额
        BigDecimal price = siteOrder.getPrepaidDeposit();
        //更新已付金额
        siteOrder.setAmountPaid(siteOrder.getAmountPaid().add(price));
        //减少待付金额：代付金额=总金额-已付金额-优惠价
        siteOrder.setAmountObligation(siteOrder.getAmountObligation().subtract(price).subtract(siteOrder.getAmountDiscount()));
        siteOrder.setPayStatus(PaymentStatusEnums.DEPOSIT_PAID.getValue());
        siteOrder.setStatus(SiteOrderStatusEnums.IN_PROGRESS.getValue());
        siteOrder.setUpdatedTime(new Date());
        siteOrderService.updateById(siteOrder);

        //TODO 将潜在客户预定客户转化为潜在客户
        //邮件发送
        sendmail(siteOrder);
        //保存支付记录
        savePaymentRecords(siteOrder, stripeJson);
    }

    private void sendmail(SiteOrder order) {

        SiteCustomer customer = siteCustomerService.getById(order.getCustomerId());
//        SiteProduct product = siteProductService.getById(order.getProductId());
        SiteProductModel productModel = siteProductModelService.getById(order.getProductId());

        String eamil = customer.getEmail();
        String name = eamil.substring(0, eamil.indexOf("@"));
        BaseMailTaskEnter enter = new BaseMailTaskEnter();
        enter.setName(name);
        enter.setEvent(MailTemplateEventEnums.SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL.getEvent());
        //todo 这里注意一下，与旧的官网的系统ID和应用ID不一样
        enter.setMailSystemId(AppIDEnums.SES_WEBSITE.getSystemId());
        enter.setMailAppId(SystemIDEnums.REDE_SITE.getValue());
        enter.setToMail(eamil);
        enter.setCode("0");
        enter.setRequestId("0");
        enter.setUserRequestId("0");
        enter.setToUserId(0L);
        enter.setUserId(0L);
        enter.setPrice(order.getAmountObligation().toString());
        enter.setFullName(customer.getCustomerFullName());
        enter.setModel(productModel.getProductModelName());
        mailMultiTaskService.subscriptionPaySucceedSendmail(enter);
    }

    private void savePaymentRecords(SiteOrder order, String stripeJson) {
        SitePaymentRecords records = new SitePaymentRecords();
        records.setId(idAppService.getId(SequenceName.SITE_PAYMENT_RECORDS));
        records.setDr(Constant.DR_FALSE);
        records.setStatus(CommonStatusEnums.NORMAL.getValue());
        records.setOrderId(order.getId());
        records.setCustomerid(order.getCustomerId());
        records.setAmount(order.getAmountPaid());
        records.setAmountObligation(order.getAmountObligation());
        records.setStripeJson(stripeJson);
        records.setSynchronizeFlag(false);
        records.setRevision(0);
        records.setCreatedBy(0L);
        records.setCreatedTime(new Date());
        records.setUpdatedBy(0L);
        records.setUpdatedTime(new Date());
        sitePaymentRecordsService.save(records);
    }
}
