package com.redescooter.ses.web.website.service.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.DeliveryService;
import com.redescooter.ses.web.website.vo.delivery.DeliveryModeResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class DeliveryServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private DeliveryService deliveryService;

    @Test
    void deliverylist() {
        List<DeliveryModeResult> deliverylist = deliveryService.deliveryTypelist(new GeneralEnter());

        System.out.println(JSON.toJSONString(deliverylist));

    }
}