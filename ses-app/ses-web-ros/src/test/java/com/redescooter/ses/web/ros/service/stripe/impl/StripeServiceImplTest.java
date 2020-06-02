package com.redescooter.ses.web.ros.service.stripe.impl;

import com.redescooter.ses.web.ros.SesWebRosApplicationTests;
import com.redescooter.ses.web.ros.service.admin.AdminServiceStarter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class StripeServiceImplTest extends SesWebRosApplicationTests {

    @Autowired
    private AdminServiceStarter adminServiceStarter;

    @Test
    public void adminTest(){
        adminServiceStarter.checkAdminDate();
    }
}
