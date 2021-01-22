package com.redescooter.ses.web.website.service.impl;
import java.util.Date;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.OrderService;
import com.redescooter.ses.web.website.vo.order.AddOrderEnter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class OrderServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WebApplicationContext wac ;

    //伪造一个MVC的环境，伪造的环境不会启动tomcat，
    // 所以测试用例会启动的很快
    private MockMvc mockMvc;

    //在测试之前注册mockmvc
    @Before("setUp")
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void addOrder() {

    }

    @Test
    void addOrderParts() {
    }

    @Test
    void getOrderDetails() {
    }
}