package com.redescooter.ses.service.proxy.mq.consumer;

import com.rabbitmq.client.Channel;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.starter.rabbitmq.config.RabbitConfig;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassNameSpConsumer
 * @Description
 * @Author Joan
 * @Date2020/5/6 10:33
 * @Version V1.0
 **/
@Component
public class SpConsumer{
@Reference
   private MailMultiTaskService mailMultiTaskService;
    // 监听email队列
    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_EMAIL})
        public void receive_email(String msg, long message, Channel channel) throws IOException, TimeoutException {

        mailMultiTaskService.runTaskById(message);
        System.out.println("接收到的email；" + msg+"message"+message);
        channel.close();

    }
}
