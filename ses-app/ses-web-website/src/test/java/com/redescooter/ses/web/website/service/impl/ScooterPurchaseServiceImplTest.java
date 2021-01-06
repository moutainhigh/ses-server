package com.redescooter.ses.web.website.service.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.ScooterPurchaseService;
import com.redescooter.ses.web.website.vo.product.ModelPriceResult;
import com.redescooter.ses.web.website.vo.product.ProductsResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScooterPurchaseServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private ScooterPurchaseService scooterPurchaseService;

    @Test
    void modelPriceList() {
        GeneralEnter enter = new GeneralEnter();
        enter.setUserId(0L);

        List<ModelPriceResult> modelPriceResults = scooterPurchaseService.modelPriceList(enter);

        System.out.println(JSON.toJSONString(modelPriceResults));


    }

    @Test
    void getProductDetails() {
        IdEnter enter = new IdEnter();
        enter.setId(121442149339136l);
        List<ProductsResult> productDetails = scooterPurchaseService.getProductDetails(enter);
        System.out.println(JSON.toJSONString(productDetails));
    }
}