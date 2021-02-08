package com.redescooter.ses.web.delivery.service.impl;

import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.MailMultiBaseTaskService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.web.delivery.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.delivery.exception.SesWebDeliveryException;
import com.redescooter.ses.web.delivery.service.TokenDeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 2:32 上午
 * @ClassName: TokenDeliveryServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class TokenDeliveryServiceImpl implements TokenDeliveryService {
    @DubboReference
    private UserTokenService userTokenService;
    @DubboReference
    private MailMultiBaseTaskService mailMultiBaseTaskService;
    @DubboReference
    private AccountBaseService accountBaseService;
    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @Override
    public UserToken checkAndGetSession(GeneralEnter enter) {
        return userTokenService.checkToken(enter);
    }

    @Override
    public GeneralResult sendMail(BaseSendMailEnter enter) {
        //1. 确定邮件是否存在
        Boolean aBoolean = accountBaseService.chectMail(enter.getMail());
        if (!aBoolean) {
            throw new SesWebDeliveryException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //2. 加入邮箱任务
        BaseMailTaskEnter baseMailTask = new BaseMailTaskEnter();

        if (enter.getMail().indexOf("@") == (-1)) {
            baseMailTask.setName(enter.getMail());
        } else {
            baseMailTask.setName(enter.getMail().split("@", 2)[0]);
        }
        baseMailTask.setToMail(enter.getMail());
        baseMailTask.setToUserId(new Long("0"));
        baseMailTask.setUserRequestId(enter.getRequestId());
        baseMailTask.setEvent(MailTemplateEventEnums.WEB_PASSWORD.getEvent());
        baseMailTask.setMailAppId(AppIDEnums.SAAS_WEB.getValue());
        baseMailTask.setMailSystemId(AppIDEnums.SAAS_WEB.getSystemId());
        mailMultiTaskService.addSetPasswordWebUserTask(baseMailTask);

        return new GeneralResult(enter.getRequestId());
    }
}
