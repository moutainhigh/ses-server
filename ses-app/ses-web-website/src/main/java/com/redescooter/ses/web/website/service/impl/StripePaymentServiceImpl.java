package com.redescooter.ses.web.website.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.JsonSyntaxException;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.hub.service.operation.CustomerInquiryService;
import com.redescooter.ses.api.hub.vo.website.SyncOrderDataEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.website.config.RequestsKeyProperties;
import com.redescooter.ses.web.website.config.StripeConfigProperties;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dao.base.SiteOrderBMapper;
import com.redescooter.ses.web.website.dm.*;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.enums.PaymentStatusEnums;
import com.redescooter.ses.web.website.enums.PaymentTimeEnums;
import com.redescooter.ses.web.website.enums.SiteOrderStatusEnums;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.StripePaymentService;
import com.redescooter.ses.web.website.service.base.*;
import com.redescooter.ses.web.website.vo.stripe.PayResponseBody;
import com.stripe.Stripe;
import com.stripe.model.*;
import com.stripe.net.ApiResource;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author jerry
 * @Date 2021/1/23 11:55 ??????
 * @Description Stripe????????????????????????
 **/
@Slf4j
@Service
public class StripePaymentServiceImpl implements StripePaymentService {

    private final String integrationCheck = "integration_check";

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

    @Autowired
    private SiteProductPriceService siteProductPriceService;

    @Autowired
    private SitePartsService sitePartsService;

    @Autowired
    private SitePaymentTypeService sitePaymentTypeService;

    @Autowired
    private SiteOrderBMapper siteOrderBMapper;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private CustomerInquiryService customerInquiryService;

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    public StringResult paymentIntent(IdEnter enter) {
        StringResult result = new StringResult();

        Stripe.apiKey = stripeConfigProperties.getSecretkey();
        log.info(Stripe.apiKey + "{>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>}");
        SiteOrder order = siteOrderService.getById(enter.getId());
        if (order == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        map.put(integrationCheck, stripeConfigProperties.getPaymentEvent());
        map.put("order_id", String.valueOf(order.getId()));
        map.put("order_no", order.getOrderNo());
        map.put("product_id", String.valueOf(order.getProductId()));
        BigDecimal amout = null;
        LambdaQueryWrapper<SiteProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SiteProduct::getProductModelId, order.getProductId());
        SiteProduct product = siteProductService.getOne(queryWrapper);
        if (product == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PRODUCT_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PRODUCT_NOT_EXIST_EXIST.getMessage());
        }
        SitePaymentType byId = sitePaymentTypeService.getById(order.getPaymentTypeId());
        LambdaQueryWrapper<SiteProductPrice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SiteProductPrice::getProductModelId, product.getProductModelId());
        wrapper.eq(SiteProductPrice::getDr, Constant.DR_FALSE);
        wrapper.eq(SiteProductPrice::getInstallmentTime, order.getDef5());
        wrapper.eq(SiteProductPrice::getPriceType, byId.getPaymentCode());
        wrapper.last("limit 1");
        SiteProductPrice siteProductPrice = siteProductPriceService.getOne(wrapper);
        if (siteProductPrice == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.NOT_FOUNT_PRODUCT_PRICE.getCode(),
                    ExceptionCodeEnums.NOT_FOUNT_PRODUCT_PRICE.getMessage());
        }
        if (siteProductPrice.getPriceType() == 1 || siteProductPrice.getPriceType() == 3) {
            if (order.getPayStatus() == PaymentStatusEnums.UNPAID_PAID.getValue()) {
                amout = order.getPrepaidDeposit().add(order.getFreight());
            } else if (order.getPayStatus() == PaymentStatusEnums.DEPOSIT_PAID.getValue() || order.getPayStatus() == PaymentStatusEnums.ON_INSTALMENT.getValue()) {
                //??????????????????????????????????????????????????? ????????????????????????????????????
                if (Integer.parseInt(order.getDef2()) == Integer.parseInt(siteProductPrice.getInstallmentTime())) {
                    throw new SesWebsiteException(ExceptionCodeEnums.INSTALLMENT_COMPLETION.getCode(),
                            ExceptionCodeEnums.INSTALLMENT_COMPLETION.getMessage());
                }
//                LambdaQueryWrapper<SiteOrderB> wrapper1 = new LambdaQueryWrapper<>();
//                wrapper1.eq(SiteOrderB::getOrderId, order.getId());
//                wrapper1.isNotNull(SiteOrderB::getDef2);
//                SiteOrderB siteOrderB = siteOrderBMapper.selectOne(wrapper1);
                //??????????????????????????? ?????????????????????????????????????????????????????????
                if (order.getDef1().equals("0")) {
                    amout = siteProductPrice.getShouldPayPeriod();
                } else {
                    amout = siteProductPrice.getShouldPayPeriod().add(new BigDecimal(order.getDef1()));
                }
            }
        }
        if (order.getPayStatus() == PaymentStatusEnums.UNPAID_PAID.getValue()) {
            amout = order.getPrepaidDeposit().add(order.getFreight());
        } else if (order.getPayStatus() == PaymentStatusEnums.DEPOSIT_PAID.getValue()) {
            amout = order.getAmountObligation();
        }
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setReceiptEmail(stripeConfigProperties.getReceiptEmail())
                    .setCurrency(stripeConfigProperties.getCurrency()).addPaymentMethodType(stripeConfigProperties.getPaymentMethodTypes())
                    /**??????????????????**/
                    .setAmount(amout.multiply(new BigDecimal("100")).longValue())
                    .putAllMetadata(map)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            log.info(intent + "{>>>>>>>>>>>>>>>>>>>>>>>>>>intent}");
            String clientSecret = intent.getClientSecret();
            log.info("===========" + clientSecret);
            result.setValue(clientSecret);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return result;
    }


    /**
     * ???????????? ?????????
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
        log.info("::::::????????????????????????===={}", payload);
        log.info("=============================");

        //????????????
        Event event = null;
        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            // Invalid payload
            log.error("===????????????===", e.getMessage());

            //TODO ?????????????????????????????????
            return new GeneralResult(String.valueOf(UUID.randomUUID()));
        }
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataObjectDeserializer.getObject().get();
        generateResponse((PaymentIntent) stripeObject, new PayResponseBody(), stripeJson);

        return new GeneralResult(String.valueOf(UUID.randomUUID()));
    }

    /**
     * ?????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult cancelledPaymentIntent(String enter) {
        return null;
    }

    /**
     * ???????????? ?????????
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
     * @Description ???????????????????????????
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
     * ??????????????????
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
                // ??????????????????
                response.setClientSecret(intent.getClientSecret());

                Map<String, String> metadata = intent.getMetadata();
                metadata.forEach((k, v) -> {
                    log.info("====={}=================={}======", k, v);
                });
                //???????????????????????????????????????
                paymentSuccess(Long.valueOf(metadata.get("order_id")), stripeJson);
                log.info("<<<<<<<<<<???? Payment received!>>>>>>>>");
                break;
            // ????????????
            case "faild":
                Map<String, String> metadata2 = intent.getMetadata();
                metadata2.forEach((k, v) -> {
                    log.info("====={}=================={}======", k, v);
                });
                //???????????????????????????????????????
                paymentFail(Long.valueOf(metadata2.get("order_id")));
                break;
            // ????????????
            case "canceled":
                break;
            default:
                response.setError("Unrecognized status");
        }
        return response;
    }

    /**
     * ????????????????????????????????????
     *
     * @param id
     */
    private void paymentSuccess(Long id, String stripeJson) {
        log.info(">>>>>>>>>>>>>>>>>>??????paymentSuccess");
        SiteOrder siteOrder = siteOrderService.getById(id);
        if (ObjectUtil.isNull(siteOrder)) {
            //TODO ??????????????????
            throw new SesWebsiteException(ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getMessage());
        }
        LambdaQueryWrapper<SiteProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SiteProduct::getProductModelId, siteOrder.getProductId());
        SiteProduct product = siteProductService.getOne(queryWrapper);
        if (product == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PRODUCT_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PRODUCT_NOT_EXIST_EXIST.getMessage());
        }
        SitePaymentType sitePaymentType = sitePaymentTypeService.getById(siteOrder.getPaymentTypeId());
        LambdaQueryWrapper<SiteProductPrice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SiteProductPrice::getProductModelId, product.getProductModelId());
        wrapper.eq(SiteProductPrice::getInstallmentTime, siteOrder.getDef5());
        wrapper.eq(SiteProductPrice::getDr, Constant.DR_FALSE);
        wrapper.eq(SiteProductPrice::getPriceType, sitePaymentType.getPaymentCode());
        wrapper.last("limit 1");
        SiteProductPrice siteProductPrice = siteProductPriceService.getOne(wrapper);
        if (siteProductPrice == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.NOT_FOUNT_PRODUCT_PRICE.getCode(),
                    ExceptionCodeEnums.NOT_FOUNT_PRODUCT_PRICE.getMessage());
        }
        LambdaQueryWrapper<SiteParts> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SiteParts::getPartsType, 3);
        queryWrapper1.last("limit 1");
        SiteParts siteParts = sitePartsService.getOne(queryWrapper1);
        if (siteParts == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PARTS_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PARTS_NOT_EXIST_EXIST.getMessage());
        }
        String battery = siteProductPrice.getBattery().substring(0, 1);
        BigDecimal batteryResult = new BigDecimal(siteOrder.getBatteryQty()).subtract(new BigDecimal(battery));
        BigDecimal batteryResult2 = new BigDecimal("0");
//        LambdaQueryWrapper<SiteOrderB> wrapper1 = new LambdaQueryWrapper<>();
//        wrapper1.eq(SiteOrderB::getOrderId, siteOrder.getId());
//        wrapper1.isNotNull(SiteOrderB::getDef2);
//        SiteOrderB siteOrderB = siteOrderBMapper.selectOne(wrapper1);
//        if (siteOrderB == null) {
//            siteOrderB.setDef2("0");
//        }
        //???????????? ?????????????????????????????????????????????
        if (new Date().before(DateUtil.subMonth(siteOrder.getCreatedTime(), Integer.parseInt(siteOrder.getDef2())))) {
            siteOrder.setDef3(PaymentTimeEnums.ADVANCE.getRemark());
        } else if (new Date().after(DateUtil.subMonth(siteOrder.getCreatedTime(), Integer.parseInt(siteOrder.getDef2())))) {
            siteOrder.setDef3(PaymentTimeEnums.POSTPONE.getRemark());
        } else {
            siteOrder.setDef3(PaymentTimeEnums.PUNCTUALITY.getRemark());
        }
        // ???????????????????????? ??????????????????
        if (siteOrder.getPayStatus() == PaymentStatusEnums.UNPAID_PAID.getValue()) {
            // ????????????????????? ?????????????????????????????????????????????
            if (siteProductPrice.getPriceType() == 1 || siteProductPrice.getPriceType() == 3) {
                if (batteryResult.compareTo(BigDecimal.ZERO) == 0) {
                    batteryResult2 = new BigDecimal("0");
                } else {
                    batteryResult2 = batteryResult.multiply(siteParts.getPrice()).divide(new BigDecimal(siteProductPrice.getInstallmentTime()), 2, BigDecimal.ROUND_UP);
                }
                siteOrder.setAmountPaid(siteOrder.getPrepaidDeposit().add(siteOrder.getFreight()));
                if (siteOrder.getDef1().equals("0")) {
                    siteOrder.setAmountObligation(siteProductPrice.getShouldPayPeriod().add(batteryResult2));
                } else {
                    siteOrder.setAmountObligation(siteProductPrice.getShouldPayPeriod().add(batteryResult2).add(new BigDecimal(siteOrder.getDef1()).divide(new BigDecimal(siteProductPrice.getInstallmentTime()), 2, BigDecimal.ROUND_UP)));
                }
                BigDecimal peijian = new BigDecimal(siteOrder.getDef1()).divide(new BigDecimal(siteProductPrice.getInstallmentTime()),2,BigDecimal.ROUND_UP).multiply(new BigDecimal(siteProductPrice.getInstallmentTime()));
                BigDecimal installmentTime = new BigDecimal(siteProductPrice.getInstallmentTime());
                siteOrder.setTotalPrice(siteOrder.getTotalPrice());
                Integer restPeriods = Integer.parseInt(siteOrder.getDef2());
                siteOrder.setDef2(restPeriods.toString());
            } else {
                siteOrder.setTotalPrice(siteOrder.getTotalPrice());
                siteOrder.setAmountPaid(siteOrder.getPrepaidDeposit().add(siteOrder.getFreight()));
                siteOrder.setAmountObligation(siteOrder.getTotalPrice().subtract(siteOrder.getAmountPaid()));
            }
            siteOrder.setPayStatus(PaymentStatusEnums.DEPOSIT_PAID.getValue());
            siteOrder.setStatus(SiteOrderStatusEnums.IN_PROGRESS.getValue());
        } else if (siteOrder.getPayStatus() == PaymentStatusEnums.DEPOSIT_PAID.getValue() || siteOrder.getPayStatus() == PaymentStatusEnums.ON_INSTALMENT.getValue()) {
            if (siteProductPrice.getPriceType() == 1 || siteProductPrice.getPriceType() == 3) {
                if (Integer.parseInt(siteOrder.getDef2()) == Integer.parseInt(siteProductPrice.getInstallmentTime())) {
                    siteOrder.setPayStatus(PaymentStatusEnums.FINISHED_INSTALMENT.getValue());
                    siteOrder.setStatus(SiteOrderStatusEnums.COMPLETED.getValue());
                    siteOrder.setAmountObligation(new BigDecimal("0"));
                } else {
                    if (batteryResult.compareTo(BigDecimal.ZERO) == 0) {
                        batteryResult2 = new BigDecimal("0");
                    } else {
                        batteryResult2 = batteryResult.multiply(siteParts.getPrice()).divide(new BigDecimal(siteProductPrice.getInstallmentTime()), 2, BigDecimal.ROUND_UP);
                    }
                    siteOrder.setAmountObligation(siteProductPrice.getShouldPayPeriod().add(new BigDecimal(siteOrder.getDef1()).divide(new BigDecimal(siteProductPrice.getInstallmentTime()), 2, BigDecimal.ROUND_UP)).add(batteryResult2));
                    Integer restPeriods = Integer.parseInt(siteOrder.getDef2()) + 1;
                    siteOrder.setPayStatus(PaymentStatusEnums.ON_INSTALMENT.getValue());
                    siteOrder.setStatus(SiteOrderStatusEnums.IN_PROGRESS.getValue());
                    siteOrder.setDef2(restPeriods.toString());
                    BigDecimal peijian = new BigDecimal(siteOrder.getDef1()).divide(new BigDecimal(siteProductPrice.getInstallmentTime()),2,BigDecimal.ROUND_UP).multiply(new BigDecimal(siteProductPrice.getInstallmentTime()));
                    BigDecimal installmentTime = new BigDecimal(siteProductPrice.getInstallmentTime());
//                    siteOrder.setTotalPrice(siteOrder.getPrepaidDeposit().add(siteOrder.getFreight()).add(siteProductPrice.getShouldPayPeriod().multiply(installmentTime)).add(batteryResult2.multiply(installmentTime)).add(peijian));
                    siteOrder.setTotalPrice(siteOrder.getTotalPrice());
                    siteOrder.setAmountPaid(siteOrder.getAmountPaid().add(siteOrder.getAmountObligation()));
                }
            } else {
                // ??????????????????
                siteOrder.setAmountPaid(siteOrder.getAmountPaid().add(siteOrder.getAmountObligation()));
                siteOrder.setAmountObligation(siteOrder.getAmountObligation().subtract(siteOrder.getAmountObligation()));
                siteOrder.setPayStatus(PaymentStatusEnums.BALANCE_PAID.getValue());
                siteOrder.setStatus(SiteOrderStatusEnums.COMPLETED.getValue());
                siteOrder.setTotalPrice(siteOrder.getAmountObligation().add(siteOrder.getAmountPaid()));
            }

        }

        //??????????????????
        //BigDecimal price = siteOrder.getPrepaidDeposit();
        //??????????????????
        // siteOrder.setAmountPaid(siteOrder.getAmountPaid().add(price));
        //?????????????????????????????????=?????????-????????????-?????????
        //siteOrder.setAmountObligation(siteOrder.getAmountObligation().subtract(price).subtract(siteOrder.getAmountDiscount()));
        siteOrder.setUpdatedTime(new Date());
        siteOrderService.updateById(siteOrder);
        SiteOrder siteOrder1 = siteOrderService.getById(id);
        if (siteProductPrice.getPriceType() == 1 || siteProductPrice.getPriceType() == 3) {
            if (Integer.parseInt(siteOrder.getDef2()) == Integer.parseInt(siteProductPrice.getInstallmentTime())) {
                siteOrder.setPayStatus(PaymentStatusEnums.FINISHED_INSTALMENT.getValue());
                siteOrder.setStatus(SiteOrderStatusEnums.COMPLETED.getValue());
                siteOrder.setAmountObligation(new BigDecimal("0"));
            }
        }
        siteOrderService.updateById(siteOrder);
        //??????ros????????????
        IdEnter idEnter = new IdEnter();
        idEnter.setId(siteOrder.getId());
        SyncOrderDataEnter syncOrderDataEnter = new SyncOrderDataEnter();
        syncOrderDataEnter.setAmountPaid(siteOrder.getAmountPaid());
        syncOrderDataEnter.setAmountDiscount(siteOrder.getAmountDiscount());
        if (siteProductPrice.getPriceType() == 1 || siteProductPrice.getPriceType() == 3) {
            //syncOrderDataEnter.setAmountObligation(siteOrder.getAmountObligation().multiply(new BigDecimal(siteOrder1.getDef5()).subtract(new BigDecimal(siteOrder1.getDef2()))));
            syncOrderDataEnter.setAmountObligation(siteOrder.getTotalPrice().subtract(siteOrder.getAmountPaid()));
        }else {
            syncOrderDataEnter.setAmountObligation(siteOrder.getAmountObligation());
        }
        syncOrderDataEnter.setTotalPrice(siteOrder.getTotalPrice());
        syncOrderDataEnter.setPayStatus(siteOrder.getPayStatus());
        syncOrderDataEnter.setIsInstallment(sitePaymentType.getPaymentCode());
        synchronizationOfRosSuccess(idEnter, syncOrderDataEnter);

        //TODO ????????????????????????????????????????????????
        //????????????
        sendmail(siteOrder);
        //??????????????????
        savePaymentRecords(siteOrder, stripeJson);


    }


    /**
     * ????????????????????????????????????
     *
     * @param id
     */
    private void paymentFail(Long id) {

        SiteOrder siteOrder = siteOrderService.getById(id);
        if (ObjectUtil.isNull(siteOrder)) {
            //TODO ??????????????????
            throw new SesWebsiteException(ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getMessage());
        }
        siteOrder.setPayStatus(PaymentStatusEnums.UNPAID_PAID.getValue());
        siteOrder.setStatus(SiteOrderStatusEnums.TO_BE_PAID.getValue());
        siteOrder.setUpdatedTime(new Date());
        siteOrderService.updateById(siteOrder);
        //??????ros????????????
        IdEnter idEnter = new IdEnter();
        idEnter.setId(siteOrder.getId());
        synchronizationOfRosFail(idEnter);
    }


    @Async
    public void synchronizationOfRosSuccess(IdEnter idEnter, SyncOrderDataEnter syncOrderDataEnter) {

        customerInquiryService.synchronizationOfRosSuccess(idEnter, syncOrderDataEnter);
    }

    @Async
    public void synchronizationOfRosFail(IdEnter idEnter) {
        customerInquiryService.synchronizationOfRosFail(idEnter);
    }

    private void sendmail(SiteOrder order) {
        log.info(order + "{>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sendEmail order}");

        SiteCustomer customer = siteCustomerService.getById(order.getCustomerId());
        log.info(customer + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>customer");
//        SiteProduct product = siteProductService.getById(order.getProductId());
        SiteProductModel productModel = siteProductModelService.getById(order.getProductId());
        log.info(productModel + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>productModel");

        String eamil = customer.getEmail();
        String name = eamil.substring(0, eamil.indexOf("@"));
        BaseMailTaskEnter enter = new BaseMailTaskEnter();
        enter.setName(name);
        enter.setEvent(MailTemplateEventEnums.SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL.getEvent());
        //todo ?????????????????????????????????????????????ID?????????ID?????????
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
