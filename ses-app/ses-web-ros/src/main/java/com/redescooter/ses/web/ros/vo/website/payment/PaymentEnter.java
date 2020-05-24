package com.redescooter.ses.web.ros.vo.website.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@lombok.Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PaymentEnter {

    private String id;
    private String object;
    private Date apiVersion;
    private int created;
    private Data data;
    private boolean livemode;
    private int pendingWebhooks;
    private Request request;
    private String type;

}