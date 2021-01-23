package com.redescooter.ses.web.website.demo;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.dm.SiteProduct;
import com.redescooter.ses.web.website.service.base.SiteProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/23 1:45 上午
 * @Description 测试demo
 **/

public class DemoTest extends SesWebsiteApplicationTests {

    @Autowired
    private SiteProductService siteProductService;

    @Test
    public void demo1() {
        SiteProduct product = siteProductService.getById(169872600109056l);
        System.out.printf(String.valueOf(product.getId()));


    }
}
