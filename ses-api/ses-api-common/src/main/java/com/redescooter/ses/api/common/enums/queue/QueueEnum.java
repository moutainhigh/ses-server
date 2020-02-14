package com.redescooter.ses.api.common.enums.queue;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/2/2020 1:58 下午
 * @ClassName: QueueEnum
 * @Function: 消息队列枚举配置
 */
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("ses.order.direct", "ses.order.cancel", "ses.order.cancel"),
    QUEUE_EMAIL_CANCEL("ses.order.direct", "ses.order.cancel", "ses.order.cancel"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("ses.order.direct.ttl", "ses.order.cancel.ttl", "ses.order.cancel.ttl");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
