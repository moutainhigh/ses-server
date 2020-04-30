package com.redescooter.ses.web.ros.mq.producer;

import com.redescooter.ses.starter.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName SpProducer 生产者
 * @Author Jerry
 * @date 2020/04/07 03:22
 * @Description:
 */

@Component
public class SpProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendByTopics() {
        for (int i = 0; i < 5; i++) {
            String message = "sms email inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_TOPICS_INFORM, "inform.sms.email", message);
            System.out.println("发送的消息:'" + message + "'");
        }
    }
}
