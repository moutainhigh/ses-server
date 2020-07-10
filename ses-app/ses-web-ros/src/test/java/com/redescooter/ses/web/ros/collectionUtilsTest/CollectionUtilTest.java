package com.redescooter.ses.web.ros.collectionUtilsTest;

import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 26/12/2019 6:23 下午
 * @ClassName: RedisOne
 * @Function: TODO
 */
@Log4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionUtilTest {
  @Reference
  private MailMultiTaskService mailMultiTaskService;
    @Test
    public void disjunction() {


        System.out.println("Method one:" + Math.random() * 100);

        Random random = new Random();
        System.out.println("Method two:" + new Random().nextInt(100));

    }

    @Test
    public void stream() {

        System.out.println(DigestUtils.md5Hex("RedeScooter@2020" + 12727));
    }
  @Test
  public void sendEmail() {
    BaseMailTaskEnter enter = new BaseMailTaskEnter();
    enter.setEvent(MailTemplateEventEnums.SUBSCRIBE_TO_EMAIL_SUCCESSFULLY.getName());
    enter.setMailSystemId(AppIDEnums.SES_ROS.getSystemId());
    enter.setMailAppId(SystemIDEnums.REDE_SES.getValue());
    enter.setToMail("15574925925@163.com");
    enter.setCode("0");
    enter.setRequestId("0");
    enter.setUserRequestId("0");
    enter.setToUserId(0L);
    enter.setUserId(0L);
    mailMultiTaskService.subscribeToEmailSuccessfully(enter);
  }


}
