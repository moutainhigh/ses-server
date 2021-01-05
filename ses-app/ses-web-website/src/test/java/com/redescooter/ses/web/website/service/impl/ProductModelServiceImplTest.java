package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.ProductModelService;
import com.redescooter.ses.web.website.vo.product.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductModelServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired(required = true)
    private ProductModelService productModelService;

    @Test
    void addProductModel() {

        addProductModelEnter E50Enter = new addProductModelEnter();
        E50Enter.setProductClassId(187725594529792L);
        E50Enter.setProductModelName("Model E50");
        E50Enter.setCnName("Model E50");
        E50Enter.setFrName("Model E50");
        E50Enter.setEnName("Model E50");
        E50Enter.setRemark("");
        E50Enter.setUserId(0L);

        assertTrue(productModelService.addProductModel(E50Enter));

        addProductModelEnter E100Enter = new addProductModelEnter();
        E100Enter.setProductClassId(187725594529792L);
        E100Enter.setProductModelName("Model E100");
        E100Enter.setCnName("Model E100");
        E100Enter.setFrName("Model E100");
        E100Enter.setEnName("Model E100");
        E100Enter.setRemark("");
        E100Enter.setUserId(0L);
        assertTrue(productModelService.addProductModel(E100Enter));

        addProductModelEnter E125Enter = new addProductModelEnter();
        E125Enter.setProductClassId(187723711287296L);
        E125Enter.setProductModelName("Model E100");
        E125Enter.setCnName("Model E100");
        E125Enter.setFrName("Model E100");
        E125Enter.setEnName("Model E100");
        E125Enter.setRemark("");
        E125Enter.setUserId(0L);
        assertTrue(productModelService.addProductModel(E125Enter));

    }

    @Test
    void modityProductModel() {

        modityProductModelEnter E50Enter = new modityProductModelEnter();
        E50Enter.setId(145743367524352L);
        E50Enter.setProductModelName("MODEL E50");
        E50Enter.setCnName("MODEL E50");
        E50Enter.setFrName("MODEL E50");
        E50Enter.setEnName("MODEL E50");
        assertTrue(productModelService.modityProductModel(E50Enter));

        modityProductModelEnter E100Enter = new modityProductModelEnter();
        E100Enter.setId(145745401761792L);
        E100Enter.setProductModelName("MODEL E100");
        E100Enter.setCnName("MODEL E100");
        E100Enter.setFrName("MODEL E100");
        E100Enter.setEnName("MODEL E100");
        assertTrue(productModelService.modityProductModel(E100Enter));

        modityProductModelEnter E125Enter = new modityProductModelEnter();
        E125Enter.setId(145745431121920L);
        E125Enter.setProductModelName("MODEL E125");
        E125Enter.setCnName("MODEL E125");
        E125Enter.setFrName("MODEL E125");
        E125Enter.setEnName("MODEL E125");
        assertTrue(productModelService.modityProductModel(E125Enter));

    }

    @Test
    void removeProductModel() {

        IdEnter enter = new IdEnter();
        enter.setId(156883715772416L);
        assertTrue(productModelService.removeProductModel(enter));
    }

    @Test
    void getProductModelDetails() {
        IdEnter enter = new IdEnter();
        enter.setId(156836924116992L);
        ProductModelDetailsResult details = productModelService.getProductModelDetails(enter);

        System.out.println(details.toString());
    }

    @Test
    void getProductModelList() {

        List<ProductModelDetailsResult> list = productModelService.getProductModelList(new GeneralEnter());

        list.forEach(p -> {
            System.out.println(p.toString());
        });

        assertNotNull(list);
    }

}