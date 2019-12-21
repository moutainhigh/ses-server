package com.redescooter.ses.web.delivery.service;


import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.proxy.vo.mail.SendMailEnter;

/**
 * @description: TokenService
 * @author: Darren
 * @create: 2019/01/18 10:04
 */
public interface TokenDeliveryService {

    UserToken checkAndGetSession(GeneralEnter enter);

    GeneralResult sendMail(SendMailEnter enter);
}
