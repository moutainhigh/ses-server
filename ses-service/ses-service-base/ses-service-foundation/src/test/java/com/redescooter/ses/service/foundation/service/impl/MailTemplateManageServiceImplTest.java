package com.redescooter.ses.service.foundation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.vo.email.EmailListEnter;
import com.redescooter.ses.api.foundation.service.MailTemplateManageService;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateConfigResult;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateResult;
import com.redescooter.ses.api.foundation.vo.mail.QueryMailConfigEnter;
import com.redescooter.ses.service.foundation.SesServiceFoundationApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class MailTemplateManageServiceImplTest extends SesServiceFoundationApplicationTests {

    @DubboReference
    private MailTemplateManageService mailTemplateManageService;

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void getMailTemplateList() {

        List<MailTemplateResult> mailTemplateList = mailTemplateManageService.getMailTemplateList(new EmailListEnter());

        log.info("mailTemplate共计{}条", mailTemplateList.size());
        log.info("mailTemplateList={}", JSONObject.toJSONString(mailTemplateList));

    }

    @Test
    void listParameter() {
        QueryMailConfigEnter enter = new QueryMailConfigEnter();
        enter.setMailTemplateNo(3);
        MailTemplateConfigResult result = mailTemplateManageService.listParameter(enter);
        System.out.println(JSONObject.toJSONString(result));
    }
}