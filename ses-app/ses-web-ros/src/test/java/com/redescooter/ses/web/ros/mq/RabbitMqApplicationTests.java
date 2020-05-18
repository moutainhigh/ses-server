package com.redescooter.ses.web.ros.mq;


import com.rabbitmq.client.Channel;
import com.redescooter.ses.starter.rabbitmq.constants.ExchangeName;
import com.redescooter.ses.starter.rabbitmq.constants.QueueName;
import com.redescooter.ses.web.ros.mq.consumer.SpConsumer;
import com.redescooter.ses.web.ros.mq.producer.SpProducer;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void testSendMsg() {
        spProducer.sendByTopics();
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


