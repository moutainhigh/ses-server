package com.redescooter.ses.web.website.service.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.TokenWebsiteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class TokenWebsiteServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private TokenWebsiteService tokenWebsiteService;

    @Test
    void login() {

        LoginEnter enter = new LoginEnter();
        enter.setLoginType(0);
        enter.setLoginName("jerry@redescooter.com");
        enter.setPassword("123456");
        System.out.println(JSON.toJSONString(tokenWebsiteService.login(enter)));
    }

    @Test
    void logout() {
    }

    @Test
    void modifyPassword() {
    }

    @Test
    void sendCode() {
    }
}