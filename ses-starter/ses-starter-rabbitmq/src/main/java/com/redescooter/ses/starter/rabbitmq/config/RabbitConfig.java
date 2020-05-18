package com.redescooter.ses.starter.rabbitmq.config;

import com.redescooter.ses.starter.rabbitmq.constants.BindingQueueRoutingKey;
import com.redescooter.ses.starter.rabbitmq.constants.ExchangeName;
import com.redescooter.ses.starter.rabbitmq.constants.QueueName;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * todo 定义RabbitConfig类，配置Exchange、Queue、及绑定交换机
 *
 * @ClassName RabbitConfig
 * @Author Jerry
 * @date 2020/04/07 03:20
 * @Description:
 */

@Configuration
public class RabbitConfig {

    /**
     * 此处以topic交换机为例
     * 交换机配置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     *
     * @return the exchange
     */
    @Bean(ExchangeName.EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM() {
        // durable(true)持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.topicExchange(ExchangeName.EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    //声明队列
    @Bean(QueueName.QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS() {
        // new Queue 默认为 durable(true)可持久化、exclusive（true）独占队列，由 autoDelete（true） 无消息时自动删除
        Queue queue = new Queue(QueueName.QUEUE_INFORM_SMS);
        return queue;
    }

    //声明队列
    @Bean(QueueName.QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL() {
        Queue queue = new Queue(QueueName.QUEUE_INFORM_EMAIL);
        return queue;
    }

    //声明队列 客户账户创建队列
    @Bean(QueueName.QUEUE_INFORM_CUSOTMER_ACCOUNT)
    public Queue QUEUE_INFORM_CUSOTMER_ACCOUNT() {
        Queue queue = new Queue(QueueName.QUEUE_INFORM_CUSOTMER_ACCOUNT);
        return queue;
    }

    /**
     * channel.queueBind(INFORM_QUEUE_SMS,"inform_exchange_topic","inform.#.sms.#");
     * 绑定队列到交换机 .
     *
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QueueName.QUEUE_INFORM_SMS) Queue queue, @Qualifier(ExchangeName.EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(BindingQueueRoutingKey.BINDING_QUEUE_INFORM_SMS).noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QueueName.QUEUE_INFORM_EMAIL) Queue queue, @Qualifier(ExchangeName.EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(BindingQueueRoutingKey.BINDING_QUEUE_INFORM_EMAIL).noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL_ACCOUNT(@Qualifier(QueueName.QUEUE_INFORM_CUSOTMER_ACCOUNT) Queue queue, @Qualifier(ExchangeName.EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(BindingQueueRoutingKey.BINDING_QUEUE_INFORM_EMAIL).noargs();
    }
}
