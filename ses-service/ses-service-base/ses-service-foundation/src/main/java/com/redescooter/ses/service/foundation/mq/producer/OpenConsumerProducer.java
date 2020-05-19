package com.redescooter.ses.service.foundation.mq.producer;


import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;
import com.redescooter.ses.starter.rabbitmq.constants.CustomizeRoutingKey;
import com.redescooter.ses.starter.rabbitmq.constants.ExchangeName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassNameEmailMassageProducer
 * @Description
 * @Author Joan
 * @Date2020/5/7 16:02
 * @Version V1.0
 **/
@Slf4j
@Component
public class OpenConsumerProducer{
  @Autowired
  private RabbitTemplate rabbitTemplate;
  //回调函数: confirm确认
  final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
    @Override
    public void confirm(CorrelationData correlationData, boolean consumerResult, String message) {

      String messageId = correlationData.getId();
      System.err.println("correlationData: " + correlationData.getId());
      if(consumerResult){
        log.info("邮件任务id=={},消费成功，请关注接收邮件！",message);
      } else {
        log.error("邮件任务id=={},消费失败，等待再次消费发送邮件！",message);
      }
    }
  };


  public void sendOpenAccount(SaveUserProfileHubEnter saveUserProfileHubEnter) {
    rabbitTemplate.setConfirmCallback(confirmCallback);
    CorrelationData correlationData = new CorrelationData(String.valueOf(saveUserProfileHubEnter.getId()));
    rabbitTemplate.convertAndSend(ExchangeName.EXCHANGE_TOPICS_INFORM, CustomizeRoutingKey.CUSTOMER_CUSTOMER_USER_PROFILE,saveUserProfileHubEnter,correlationData);
    System.out.println("发送 open 保存用户个人资料"+saveUserProfileHubEnter);  }

}
