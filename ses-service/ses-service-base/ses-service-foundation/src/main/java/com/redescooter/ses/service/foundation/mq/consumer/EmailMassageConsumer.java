package com.redescooter.ses.service.foundation.mq.consumer;

import com.rabbitmq.client.Channel;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.starter.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassNameEmailMassageConsumer
 * @Description
 * @Author Joan
 * @Date2020/5/6 19:10
 * @Version V1.0
 **/
@Component
public class EmailMassageConsumer {
//    @Autowired
//    private MailMultiTaskService mailMultiTaskService;
//    // 监听email队列
//    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_EMAIL})
//    public void receive_email(String msg, long message, Channel channel) throws IOException, TimeoutException {
//
//      //  mailMultiTaskService.runTaskById(message);
//        System.out.println("接收到的email；" + msg+"message"+message);
//        channel.close();
//
//    }

    // 监听email队列
    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(String msg, Message message, Channel channel) {
        System.out.println("接收到的email队列；" + msg);
    }

}
