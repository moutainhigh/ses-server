package com.redescooter.ses.starter.rabbitmq.constant;

/**
 * 交换机、队列绑定的路由键 匹配规则
 */
public interface BindRoutingKeyConstant {

    String BINDING_QUEUE_INFORM_SMS = "inform.#.sms.#";

    String BINDING_QUEUE_INFORM_EMAIL = "inform.#.email.#";

    String BINDING_QUEUE_INFORM_ACCOUNT = "iform.#.customer.account.#";
}
