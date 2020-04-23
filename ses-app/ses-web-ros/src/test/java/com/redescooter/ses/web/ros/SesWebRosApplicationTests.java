package com.redescooter.ses.web.ros;

import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.starter.redis.service.JedisService;
import lombok.extern.log4j.Log4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j
public class SesWebRosApplicationTests {

    @Before
    public void setUp() throws Exception {
        System.out.println("单元测试开始--------------------->");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("单元测试结束--------------------->");
    }
    @Autowired
    private JedisService jedisService;

    @Test
    public void stream() {
        System.out.println(jedisService.lock("1234/56789", 10));


        log.info("---------------------------------------------------------");
        System.out.println(jedisService.get("1234/56789"));
        test();
    }

    @Test
    public  void test(){
        jedisService.unlock("1234/56789");
    }
}
