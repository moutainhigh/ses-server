package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.ProductClassService;
import com.redescooter.ses.web.website.vo.product.ProductClassDetailsResult;
import com.redescooter.ses.web.website.vo.product.AddProductClassEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductClassEnter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductClassServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private ProductClassService productClassService;

    @Test
    void addProductClass() {

        AddProductClassEnter highEnter = new AddProductClassEnter();
        highEnter.setProductClassName("HIGH_SPEED");
        highEnter.setCnName("高速");
        highEnter.setFrName("HAUTE VITESSE");
        highEnter.setEnName("HIGH SPEED");
        highEnter.setRemark("");
        highEnter.setUserId(0L);

        productClassService.addProductClass(highEnter);

        AddProductClassEnter lowEnter = new AddProductClassEnter();
        lowEnter.setProductClassName("HIGH_SPEED");
        lowEnter.setCnName("低速");
        lowEnter.setFrName("FAIBLE VITESSE");
        lowEnter.setEnName("LOW SPEED");
        lowEnter.setRemark("no");
        lowEnter.setUserId(0L);
        productClassService.addProductClass(lowEnter);

    }

    @Test
    void modityProductClass() {

        ModityProductClassEnter modityEnter = new ModityProductClassEnter();
        modityEnter.setId(187723711287296L);
        modityEnter.setProductClassName("HIGH_SPEED");
        modityEnter.setCnName("高速");
        modityEnter.setFrName("HAUTE VITESSE");
        modityEnter.setEnName("HIGH SPEED");
        modityEnter.setRemark("再次新过");
        modityEnter.setUserId(0L);

        productClassService.modityProductClass(modityEnter);

    }

    @Test
    void removeProductClass() {
        IdEnter enter = new IdEnter();
        enter.setId(187723711287296L);
        productClassService.removeProductClass(enter);
    }

    @Test
    void getProductClassDetails() {

        IdEnter enter = new IdEnter();
        enter.setId(187723711287296L);

        ProductClassDetailsResult details = productClassService.getProductClassDetails(enter);

        System.out.println(details.toString());

    }

    @Test
    void getProductClassList() {

        List<ProductClassDetailsResult> list = productClassService.getProductClassList(new GeneralEnter());

        list.forEach(p -> {
            System.out.println(p.toString());
        });

        assertNotNull(list);
    }
}