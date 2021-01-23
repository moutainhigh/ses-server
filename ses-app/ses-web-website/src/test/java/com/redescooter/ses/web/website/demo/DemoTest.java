package com.redescooter.ses.web.website.demo;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.config.RequestsKeyProperties;
import com.redescooter.ses.web.website.config.StripeConfigProperties;
import com.redescooter.ses.web.website.service.base.SiteProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author jerry
 * @Date 2021/1/23 1:45 上午
 * @Description 测试demo
 **/

public class DemoTest extends SesWebsiteApplicationTests {

    @Autowired
    private SiteProductService siteProductService;

    @Autowired
    private StripeConfigProperties stripeConfigProperties;

    @Autowired
    private RequestsKeyProperties requestsKeyProperties;

    @Test
    public void demo1() {

        System.out.println(stripeConfigProperties.toString());

        System.out.println(requestsKeyProperties.toString());
    }
}
