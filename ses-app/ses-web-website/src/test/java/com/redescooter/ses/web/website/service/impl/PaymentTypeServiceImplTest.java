package com.redescooter.ses.web.website.service.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.PaymentTypeService;
import com.redescooter.ses.web.website.vo.payment.AddPaymentTypeEnter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTypeServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Test
    void addPaymentType() {
        AddPaymentTypeEnter enter = new AddPaymentTypeEnter();
        enter.setPaymentName("Leasing option");
        enter.setUserId(0L);
        paymentTypeService.addPaymentType(enter);
    }

    @Test
    void getPaymentTypeDetails() {
        IdEnter enter = new IdEnter();
        enter.setId(105424137048064l);
        System.out.println(JSON.toJSONString(paymentTypeService.getPaymentTypeDetails(enter)));
    }

    @Test
    void getPaymentTypeList() {
        System.out.println(JSON.toJSONString(paymentTypeService.getPaymentTypeList(new GeneralEnter())));

    }
}