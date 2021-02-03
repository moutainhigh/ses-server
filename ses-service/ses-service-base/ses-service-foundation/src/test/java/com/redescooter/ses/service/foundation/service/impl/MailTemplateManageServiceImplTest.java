package com.redescooter.ses.service.foundation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.foundation.service.MailTemplateManageService;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateResult;
import com.redescooter.ses.api.foundation.vo.mail.QueryMailTemplateEnter;
import com.redescooter.ses.service.foundation.SesServiceFoundationApplicationTests;
import com.redescooter.ses.starter.dubbo.annotation.RecordDubboLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        List<MailTemplateResult> mailTemplateList = mailTemplateManageService.getMailTemplateList(new QueryMailTemplateEnter());

        log.info("mailTemplate共计{}条", mailTemplateList.size());
        log.info("mailTemplateList={}", JSONObject.toJSONString(mailTemplateList));

    }
}