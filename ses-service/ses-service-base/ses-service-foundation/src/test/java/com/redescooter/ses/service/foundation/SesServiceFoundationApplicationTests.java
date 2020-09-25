package com.redescooter.ses.service.foundation;

import com.redescooter.ses.api.foundation.service.PushService;
import com.redescooter.ses.tool.utils.DateUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SesServiceFoundationApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Reference
    private PushService pushService;

    @Test
    public void test(){
        Date date = DateUtil.dateAddDays(new Date(), 1);
        if (new Date().before(date)) {
            System.out.println("对的");
        }
    }
}