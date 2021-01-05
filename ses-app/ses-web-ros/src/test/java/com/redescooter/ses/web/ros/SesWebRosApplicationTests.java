package com.redescooter.ses.web.ros;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log4j
public class SesWebRosApplicationTests {

    private LocalDateTime beginTime = LocalDateTime.now();

    @Before("setUp")
    public void setUp() throws Exception {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String now = dateFormat.toString();

        log.info("单元测试开始-------<<<<>>>" + now);
    }

    @After("getDown")
    public void getDown() throws Exception {
        Long opetime = Duration.between(beginTime, LocalDateTime.now()).toMillis();
        log.info("单元测试结束-------<<<<>>>耗时：" + opetime);

    }
}
