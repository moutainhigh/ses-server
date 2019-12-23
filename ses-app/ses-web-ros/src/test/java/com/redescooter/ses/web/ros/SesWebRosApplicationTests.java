package com.redescooter.ses.web.ros;

import com.redescooter.ses.web.ros.service.CustomerRosService;
import com.redescooter.ses.web.ros.vo.account.OpenAccountEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SesWebRosApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Reference
    private CustomerRosService customerRosService;

    @Test
    public void createAccount() {
        OpenAccountEnter enter = OpenAccountEnter.builder()
                .endActivationTime("2019-12-23 16:45:40")
                .startActivationTime("2019-12-25 16:45:40")
                .id(1000004L)
                .build();
        enter.setUserId(1000003L);
        customerRosService.openAccount(enter);
    }
}
