package com.redescooter.ses.web.website.service.impl;
import java.math.BigDecimal;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.service.WebSiteCustomerService;
import com.redescooter.ses.web.website.vo.customer.AddCustomerEnter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WebSiteCustomerServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private WebSiteCustomerService webSiteCustomerService;

    @Test
    void addCustomer() {
        AddCustomerEnter enter = new AddCustomerEnter();
        enter.setCountryCode("+33");
        enter.setCustomerFirstName("jerry");
        enter.setCustomerLastName("jerry");
        enter.setCountryName("fr");
        enter.setCityName("Paris");
        enter.setAreaName("11");
        enter.setPostcode("12152111");
        enter.setAddress("法国巴黎十一区");
        enter.setPlaceId("231.2131312.13213");
        enter.setLongitude(new BigDecimal("121.46133513131"));
        enter.setLatitude(new BigDecimal("64.4343431331331"));
        enter.setTelephone("1111111111");
        enter.setEmail("jerry@redescooter.com");
        enter.setPassword("123456");
        enter.setConfirmPassword("123456");
        enter.setUserId(0L);

        webSiteCustomerService.addCustomer(enter);
    }

    @Test
    void getCustomerDetails() {
    }

    @Test
    void testAddCustomer() {
    }

    @Test
    void testGetCustomerDetails() {
    }
}