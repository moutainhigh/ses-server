package com.redescooter.ses.web.ros.mq;


import com.rabbitmq.client.Channel;
import com.redescooter.ses.starter.rabbitmq.constants.CustomizeRoutingKey;
import com.redescooter.ses.starter.rabbitmq.constants.ExchangeName;
import com.redescooter.ses.starter.rabbitmq.constants.QueueName;
import com.redescooter.ses.web.ros.mq.consumer.SpConsumer;
import com.redescooter.ses.web.ros.mq.producer.SpProducer;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j
public class RabbitMqApplicationTests {

    @Autowired
    private SpProducer spProducer;

    @Autowired
    private SpConsumer spConsumer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sender() {
        String message = "hello,world";
        rabbitTemplate.convertAndSend(ExchangeName.EXCHANGE_TOPICS_INFORM, CustomizeRoutingKey.CUSTOMER_OPEN_ACCOUNT, message);

        System.out.println("发送消息------------------{" + message + "}-------------");
    }

    // 监听email队列
    @RabbitListener(queues = {QueueName.QUEUE_INFORM_CUSOTMER_ACCOUNT})
    public void receive_email(String message, @Headers Map<String, Object> headers, Channel channel) {
        System.out.println("接收到的email队列；" + message);
        /**
         * Delivery Tag 用来标识信道中投递的消息。RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，
         * 以便 Consumer 可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了。
         * RabbitMQ 保证在每个信道中，每条消息的 Delivery Tag 从 1 开始递增。
         */
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        /**
         *  multiple 取值为 false 时，表示通知 RabbitMQ 当前消息被确认
         *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
         *   true 表示 支持批处理
         */
        boolean multiple = false;
        //确认消息被消费
        try {
            channel.basicAck(deliveryTag, multiple);

            //basicReject（8，false）消息处理失败后将消息 直接丢弃
            //basicNackk(8, true, false);表示deliveryTag=8之前未确认的消息都处理失败且将这些消息直接丢弃。
            channel.basicReject(deliveryTag,multiple);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


