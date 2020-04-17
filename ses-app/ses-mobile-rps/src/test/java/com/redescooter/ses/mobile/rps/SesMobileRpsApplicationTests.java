package com.redescooter.ses.mobile.rps;

import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SesMobileRpsApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Reference
    private IdAppService idappService;

    @Test
    public void test() {
        System.out.println(idappService.getId(SequenceName.OPE_ALLOCATE_B_TRACE));
    }

}
