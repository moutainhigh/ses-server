package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.ProductColourService;
import com.redescooter.ses.web.website.vo.product.AddProductColourEnter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class ProductColourServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private ProductColourService productColourService;

    @Test
    void addProductColour() {

        AddProductColourEnter e50Enter = new AddProductColourEnter();
        //e50Enter.setColourId(101121207832576l);
        e50Enter.setColourId(101123070103552l);
        e50Enter.setProductId(121442149339136l);
        productColourService.addProductColour(e50Enter);

        AddProductColourEnter e100Enter = new AddProductColourEnter();
        //e100Enter.setColourId(101121207832576l);
        e100Enter.setColourId(101123070103552l);
        e100Enter.setProductId(122680341434368l);
        productColourService.addProductColour(e100Enter);

        AddProductColourEnter e125Enter = new AddProductColourEnter();
        //e125Enter.setColourId(101121207832576l);
        e125Enter.setColourId(101123103657984l);
        e125Enter.setProductId(122680341434368l);
        productColourService.addProductColour(e125Enter);

    }

}