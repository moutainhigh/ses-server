package com.redescooter.ses.web.delivery;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SesWebDeliveryApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {
        System.out.println(RandomUtils.nextInt(1000, 9999));
    }
}
