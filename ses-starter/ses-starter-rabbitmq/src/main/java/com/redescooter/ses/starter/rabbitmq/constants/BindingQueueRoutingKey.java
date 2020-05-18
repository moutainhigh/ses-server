package com.redescooter.ses.starter.rabbitmq.constants;

/**
 * @ClassName:BindingQueueRoutingKey
 * @description: BindingQueueRoutingKey
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/18 09:59
 */
public interface BindingQueueRoutingKey {

    String BINDING_QUEUE_INFORM_SMS = "inform.#.sms.#";

    String BINDING_QUEUE_INFORM_EMAIL = "inform.#.email.#";
}
