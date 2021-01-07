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

        AddProductModelEnter E50Enter = new AddProductModelEnter();
        E50Enter.setProductClassId(187725594529792L);
        E50Enter.setProductModelName("Model E50");
        E50Enter.setRemark("");
        E50Enter.setUserId(0L);

        productModelService.addProductModel(E50Enter);

        AddProductModelEnter E100Enter = new AddProductModelEnter();
        E100Enter.setProductClassId(187725594529792L);
        E100Enter.setProductModelName("Model E100");
        E100Enter.setRemark("");
        E100Enter.setUserId(0L);
        productModelService.addProductModel(E100Enter);

        AddProductModelEnter E125Enter = new AddProductModelEnter();
        E125Enter.setProductClassId(187723711287296L);
        E125Enter.setProductModelName("Model E100");
        E125Enter.setRemark("");
        E125Enter.setUserId(0L);
        productModelService.addProductModel(E125Enter);

    }

    @Test
    void modityProductModel() {

        ModityProductModelEnter E50Enter = new ModityProductModelEnter();
        E50Enter.setId(145743367524352L);
        E50Enter.setProductModelName("MODEL E50");
        productModelService.modityProductModel(E50Enter);

        ModityProductModelEnter E100Enter = new ModityProductModelEnter();
        E100Enter.setId(145745401761792L);
        E100Enter.setProductModelName("MODEL E100");
        productModelService.modityProductModel(E100Enter);

        ModityProductModelEnter E125Enter = new ModityProductModelEnter();
        E125Enter.setId(145745431121920L);
        E125Enter.setProductModelName("MODEL E125");
        productModelService.modityProductModel(E125Enter);
    }

    @Test
    void removeProductModel() {

        IdEnter enter = new IdEnter();
        enter.setId(156883715772416L);
        productModelService.removeProductModel(enter);
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