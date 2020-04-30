package com.redescooter.ses.web.delivery.mq.consumer;

import com.rabbitmq.client.Channel;
import com.redescooter.ses.starter.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName SpConsumer 消费者
 * @Author Jerry
 * @date 2020/04/07 03:24
 * @Description:
 */

@Component
public class SpConsumer {

    // 监听email队列
    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(String msg, Message message, Channel channel) {
        System.out.println("接收到的email；" + msg);
    }

    // 监听sms队列
    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_SMS})
    public void receive_sms(String msg, Message message, Channel channel) {
        System.out.println("接收到的sms；" + msg);
    }
}