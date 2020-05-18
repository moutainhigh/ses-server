package com.redescooter.ses.starter.rabbitmq.constants;

/**
 * @ClassName:CustomizeRoutingKey
 * @description: CustomizeRoutingKey 用户自定义路由键 来匹配 绑定时的路由键规则
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/18 10:02
 */
public interface CustomizeRoutingKey {
    //客户开通账户时 路由建 邮件发送
    String CUSTOMER_OPEN_ACCOUNT="inform.customer.email.account.open";
  //客户开通账户时 路由建 调用 foundation open接口
  String CUSTOMER_OPEN_ACCOUN="inform.customer.open.account";
}
