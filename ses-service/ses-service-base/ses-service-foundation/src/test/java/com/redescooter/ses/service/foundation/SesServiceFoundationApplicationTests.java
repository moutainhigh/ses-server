package com.redescooter.ses.service.foundation;

import com.redescooter.ses.api.foundation.service.PushService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SesServiceFoundationApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Reference
    private PushService pushService;
}