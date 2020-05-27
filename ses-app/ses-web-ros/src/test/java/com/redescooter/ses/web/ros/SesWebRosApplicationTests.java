package com.redescooter.ses.web.ros;

import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

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

    @Autowired
    private WebSiteTokenService webSiteService;

    @Test
    public void stream() {
        String test="RedEScooter2019";
        System.out.println(DigestUtils.md5Hex(test+ "40382"));
    }


    @Test
    public void testSendMail(){
        BaseSendMailEnter enter = new BaseSendMailEnter();
        enter.setMail("aleks@redescooter.com");
        webSiteService.sendEmail(enter);
    }

}
