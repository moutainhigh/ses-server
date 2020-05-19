package com.redescooter.ses.service.hub.mq;

import com.rabbitmq.client.Channel;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;
import com.redescooter.ses.starter.rabbitmq.constants.QueueName;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassNameSaveProileConsumer
 * @Description
 * @Author Joan
 * @Date2020/5/18 19:21
 * @Version V1.0
 **/
@Component
public class SaveUserPofileConsumer {
  @Autowired
  private com.redescooter.ses.api.hub.common.UserProfileService userProfileService;

  // 监听email队列
  @RabbitListener(queues = {QueueName.QUEUE_INFORM_CUSOTMER_USER})
  public void openConsumer(SaveUserProfileHubEnter saveUserProfileHubEnter, @Headers Map<String, Object> headers, Channel channel) throws IOException {

    System.out.println("接收到保存用戶信息；" + saveUserProfileHubEnter);
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
    if (saveUserProfileHubEnter!=null&& StringUtils.equals(saveUserProfileHubEnter.getUserType(), CustomerTypeEnum.PERSONAL.getValue())){
      //调用mq
      userProfileService.saveUserProfile2C(saveUserProfileHubEnter);
      System.out.println("调用 open 保存用户个人资料2 C" +saveUserProfileHubEnter);
    }else {
      userProfileService.saveUserProfile2B(saveUserProfileHubEnter);
      System.out.println("调用 open 保存用户个人资料2 B"+saveUserProfileHubEnter);
    }

    //确认消息被消费
    try {
      channel.basicAck(deliveryTag, multiple);
    } catch (IOException e) {
      channel.basicNack(deliveryTag,multiple,false);
      e.printStackTrace();
    }
  }
}
