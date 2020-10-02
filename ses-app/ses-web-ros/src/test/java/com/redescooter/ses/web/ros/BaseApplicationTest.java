package com.redescooter.ses.web.ros;


import lombok.extern.log4j.Log4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j
public class BaseApplicationTest {

    private LocalDateTime beginTime = LocalDateTime.now();

    @Before
    public void setUp() throws Exception {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String now = dateFormat.toString();

        log.info("单元测试开始-------<<<<>>>" + now);
    }

    @After
    public void tearDown() throws Exception {
        Long opetime = Duration.between(beginTime, LocalDateTime.now()).toMillis();
        log.info("单元测试结束-------<<<<>>>耗时：" + opetime);

    }
}