package com.redescooter.ses.starter.rabbitmq.constants;

/**
 * @ClassName:CustomizeRoutingKey
 * @description: CustomizeRoutingKey 用户自定义路由键 来匹配 绑定时的路由键规则
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/18 10:02
 */
public interface CustomizeRoutingKey {
    //客户开通账户时 路由建
    String CUSTOMER_OPEN_ACCOUNT_EMAIL ="inform.customer.email.account.open";
    //customer 账户开通
    String CUSTOMER_OPEN_ACCOUNT="inform.customer.account.open";
    //保存用户个人资料 foundation
    String CUSTOMER_CUSTOMER_USER_PROFILE ="inform.customer.user.profile";
}
