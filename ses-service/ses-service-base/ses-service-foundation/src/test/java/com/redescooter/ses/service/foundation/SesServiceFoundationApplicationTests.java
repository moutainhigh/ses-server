package com.redescooter.ses.service.foundation;

import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SesServiceFoundationApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MailMultiTaskService mailMultiTaskService;

    @Test
    public void test(){
        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEB_ACTIVATE.getEvent());
        baseMailTaskEnter.setName("alex");
        baseMailTaskEnter.setToMail("alex@redescooter.com");
        baseMailTaskEnter.setToUserId(1L);
        baseMailTaskEnter.setUserRequestId("3464sadasdasdasd432");
        baseMailTaskEnter.setMailAppId(AccountTypeEnums.WEB_EXPRESS.getAppId());
        baseMailTaskEnter.setMailSystemId(AccountTypeEnums.WEB_EXPRESS.getSystemId());
        mailMultiTaskService.addActivateWebUserTask(baseMailTaskEnter);
    }
}
