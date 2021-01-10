package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.ProductPartsService;
import com.redescooter.ses.web.website.vo.product.AddProductPartsEnter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class ProductPartsServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private ProductPartsService productPartsService;

    @Test
    void addProductParts() {
        AddProductPartsEnter enter = new AddProductPartsEnter();
        enter.setPartsId(3770018097152l);
        enter.setProductId(121442149339136l);
        enter.setQty(2);
        enter.setParameter("{\"Maximum mileage\":\"140\",\"Minimum payment\":\"12.50\",\"full payment\":\"890\"}");
        enter.setUserId(0L);
        productPartsService.addProductParts(enter);
    }
}