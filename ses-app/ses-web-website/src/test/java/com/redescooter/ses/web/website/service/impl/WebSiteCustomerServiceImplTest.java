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
        enter.setCustomerFirstName("Alexandre");
        enter.setCustomerLastName("Mao");
        enter.setCountryName("fr");
        enter.setCityName("Paris");
        enter.setAreaName("Ile de France");
        enter.setPostcode("75013");
        enter.setAddress("123 boulevard masséna");
        enter.setPlaceId("231.2131312.13213");
        enter.setLongitude(new BigDecimal("48.819319692999194"));
        enter.setLatitude(new BigDecimal("2.3625413538664763"));
        enter.setTelephone("1111111111");
        enter.setEmail("alex@redelectric.fr");
        enter.setPassword("Alex@123");
        enter.setConfirmPassword("Alex@123");
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