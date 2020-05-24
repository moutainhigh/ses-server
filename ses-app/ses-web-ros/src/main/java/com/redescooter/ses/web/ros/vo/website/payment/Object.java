package com.redescooter.ses.web.ros.vo.website.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class Object {

    private String id;
    private String object;
    private int amount;
    private int amountCapturable;
    private int amountReceived;
    private String application;
    private String applicationFeeAmount;
    private String canceledAt;
    private String cancellationReason;
    private String captureMethod;
    private Charges charges;
    private String clientSecret;
    private String confirmationMethod;
    private int created;
    private String currency;
    private String customer;
    private String description;
    private String invoice;
    private String lastPaymentError;
    private boolean livemode;
    private Metadata metadata;
    private String nextAction;
    private String onBehalfOf;
    private String paymentMethod;
    private PaymentMethodOptions paymentMethodOptions;
    private List<String> paymentMethodTypes;
    private String receiptEmail;
    private String review;
    private String setupFutureUsage;
    private String shipping;
    private String source;
    private String statementDescriptor;
    private String statementDescriptorSuffix;
    private String status;
    private String transferData;
    private String transferGroup;
}