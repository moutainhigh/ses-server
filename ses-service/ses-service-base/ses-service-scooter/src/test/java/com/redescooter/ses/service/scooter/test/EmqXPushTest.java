package com.redescooter.ses.service.scooter.test;

import com.redescooter.ses.service.scooter.base.BaseTest;
import com.redescooter.ses.service.scooter.config.emqx.MqttClientUtil;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * EMQ X消息发送测试类
 * @author assert
 * @date 2020/12/29 16:48
 */
public class EmqXPushTest extends BaseTest {

    @Resource
    private MqttClientUtil mqttClientUtil;

    @Test
    public void messagePushTest() {
        mqttClientUtil.publish("scooter-update", "{\"msg\":\"这是一条推送消息\"}");
    }

}
