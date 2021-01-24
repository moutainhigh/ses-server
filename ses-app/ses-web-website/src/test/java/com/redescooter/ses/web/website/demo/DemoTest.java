package com.redescooter.ses.web.website.demo;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.config.RequestsKeyProperties;
import com.redescooter.ses.web.website.config.StripeConfigProperties;
import com.redescooter.ses.web.website.service.base.SiteProductService;
import org.jasypt.util.text.BasicTextEncryptor;
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

    @Test
    public void demo2() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        // 加密密钥
        textEncryptor.setPassword("RED@Scooter2021");
        // 要加密的数据（如数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("1qaz2wsx");
        System.out.println("username:" + username);
        System.out.println("password:" + password);
    }
}
