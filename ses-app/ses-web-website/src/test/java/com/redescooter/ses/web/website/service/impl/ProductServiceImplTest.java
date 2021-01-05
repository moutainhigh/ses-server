package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.ProductService;
import com.redescooter.ses.web.website.vo.product.AddProductEnter;
import com.redescooter.ses.web.website.vo.product.ProductDetailsResult;
import com.redescooter.ses.web.website.vo.product.ProductModelDetailsResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    void addProduct() {
        AddProductEnter enter = new AddProductEnter();
//        enter.setCnName("MODEL E50");
//        enter.setFrName("MODEL E50");
//        enter.setEnName("MODEL E50");
//        enter.setCnName("MODEL E125");
//        enter.setFrName("MODEL E125");
//        enter.setEnName("MODEL E125");
        enter.setCnName("MODEL E100");
        enter.setFrName("MODEL E100");
        enter.setEnName("MODEL E100");
//        enter.setProductModelId(145743367524352L);
//        enter.setProductModelId(145745431121920L);
        enter.setProductModelId(145745431121920L);
        enter.setMinBatteryNum(2);
        enter.setAfterSalesFlag(false);
        enter.setAddedServicesFlag(false);
        enter.setMaterParameter("");
        enter.setOtherParameter("");
//        enter.setSpeed("45 km/h");
//        enter.setMileage("300 km");
        enter.setSpeed("80 km/h");
        enter.setPower("72V");
        enter.setMileage("220 km");
        enter.setChargeCycle("2h30");
        enter.setUserId(0L);
        productService.addProduct(enter);

    }

    @Test
    void modityProduct() {
    }

    @Test
    void removeProduct() {
    }

    @Test
    void getProductDetails() {
        IdEnter enter = new IdEnter();
        enter.setId(121442149339136l);
        ProductDetailsResult details = productService.getProductDetails(enter);

        System.out.println(details.toString());
    }

    @Test
    void getProductList() {
        List<ProductDetailsResult> list = productService.getProductList(new GeneralEnter());

        list.forEach(p -> {
            System.out.println(p.toString());
        });
        assertNotNull(list);
    }
}