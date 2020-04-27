package com.redescooter.ses.web.ros;

import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.starter.redis.service.JedisService;
import lombok.extern.log4j.Log4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
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
        int count = 1;
        System.out.println(jedisService.lock(String.valueOf(count++), 2));


        log.info("---------------------------------------------------------");
        System.out.println(jedisService.get(String.valueOf(count)));
        log.info("---------------------------------------------------------");

        System.out.println(jedisService.get("adasdasdasd"));

        jedisService.unlock(String.valueOf(count));
        System.out.println(jedisService.get(String.valueOf(count)));
    }
}
