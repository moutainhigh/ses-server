package com.redescooter.ses.service.foundation.mq.consumer;

import com.rabbitmq.client.Channel;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.starter.rabbitmq.constants.QueueName;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Reference;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassNameopenAccountConsumer
 * @Description
 * @Author Joan
 * @Date2020/5/18 15:13
 * @Version V1.0
 **/
@Component
public class OpenConsumerAccount {

  @Autowired
  private AccountBaseService accountBaseService;

  // 监听email队列
  @RabbitListener(queues = {QueueName.QUEUE_INFORM_CUSOTMER_ACCOUNT})
  public void openConsumer(DateTimeParmEnter<BaseCustomerResult> message, @Headers Map<String, Object> headers, Channel channel) {

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
     */
    boolean multiple = false;
    //调用mq
    accountBaseService.open(message);
    //确认消息被消费
    try {
      channel.basicAck(deliveryTag, multiple);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
