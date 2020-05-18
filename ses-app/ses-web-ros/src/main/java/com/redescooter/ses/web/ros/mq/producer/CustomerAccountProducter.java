package com.redescooter.ses.web.ros.mq.producer;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.BaseCustomerEnter;
import com.redescooter.ses.starter.rabbitmq.constants.CustomizeRoutingKey;
import com.redescooter.ses.starter.rabbitmq.constants.ExchangeName;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:CustomerAccountProduct
 * @description: CustomerAccountProduct
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/18 11:40
 */
@Log4j2
public class CustomerAccountProducter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //回调函数: confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean consumerResult, String message) {

            System.err.println("message: " + message);
            if (consumerResult) {
                log.info("客户Id-->{},邮箱为-->{},账号创建成功", correlationData.getId().split(Constant.EMAIL_SPLITTER)[0], correlationData.getId().split(Constant.EMAIL_SPLITTER)[1]);
            } else {
                log.info("客户Id-->{},邮箱为-->{},账号创建成功", correlationData.getId().split(Constant.EMAIL_SPLITTER)[0], correlationData.getId().split(Constant.EMAIL_SPLITTER)[1]);
            }
        }
    };



    public void openAccountMessage(BaseCustomerEnter enter, OpeCustomer customer) {
        rabbitTemplate.setBeforePublishPostProcessors();
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData(enter.getId() + Constant.EMAIL_SPLITTER + enter.getEmail());
        rabbitTemplate.convertAndSend(ExchangeName.EXCHANGE_TOPICS_INFORM, CustomizeRoutingKey.CUSTOMER_OPEN_ACCOUNT_EMAIL, enter, correlationData);

        //同时将消息内容放到缓存里
        System.out.println("为客户为" + enter.getEmail() + "开通账户:");
    }


}
