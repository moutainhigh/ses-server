package com.redescooter.ses.mobile.client;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SesMobileClientApplicationTests {

    @Before
    public void setUp() throws Exception {
        System.out.println("单元测试开始--------------------->");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("单元测试结束--------------------->");
    }

}
