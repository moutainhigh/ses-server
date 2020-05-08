package com.redescooter.ses.api.foundation.mq;

import com.redescooter.ses.api.common.vo.base.IdEnter;

/**
 * @ClassNameEmailMassageProducerMq
 * @Description
 * @Author Joan
 * @Date2020/5/8 15:12
 * @Version V1.0
 **/
public interface EmailMassageProducerMq {
  //MQ发送激活邮件
  void SendEmailMassage(IdEnter idEnter);
}
