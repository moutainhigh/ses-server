package com.redescooter.ses.api.foundation.listeners;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassNameSendEmailLisenters
 * @Description
 * @Author kyle
 * @Date2020/4/30 17:20
 * @Version V1.0
 **/
@Component
public class SendEmailLisenters {

    @Autowired
    private AccountBaseService accountBaseService;

    //定义监听器消费消息发送激活邮件
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delivery.queue", durable = "true"),
            exchange = @Exchange(name = "delivery.exchange",
                    type = ExchangeTypes.TOPIC,
                    ignoreDeclarationExceptions = "true"),
            key = {"delivery.sendemail.new","delivery.sendemail.again"}
    ))
    public void listenSendEmail(IdEnter idEnter) {
        if (idEnter != null) {
            accountBaseService.sendEmailActiv(idEnter);
        }
    }

}
