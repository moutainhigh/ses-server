package com.redescooter.ses.web.delivery.service.impl;

import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.base.MailMultiBaseTaskService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.web.delivery.service.TokenDeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
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
    @Reference
    private UserTokenService userTokenService;
    @Reference
    private MailMultiBaseTaskService mailMultiBaseTaskService;
    @Override
    public UserToken checkAndGetSession(GeneralEnter enter) {
        return userTokenService.checkToken(enter);
    }

    @Override
    public GeneralResult sendMail(BaseSendMailEnter enter) {
//
//        GetUserEnter user = new GetUserEnter();
//        user.setEmail(enter.getMail());
//        user.setSystemId(AppIDEnums.SAAS_APP.getSystemId());
//        user.setAppId(AppIDEnums.SAAS_APP.getAppId());
////        UserToken userByEmail = userTokenService.getUserByEmailType(user);
//
//        String code = String.valueOf(RandomUtils.nextInt(10000, 99999));
//
//        BaseMailTaskEnter baseMailTask = new BaseMailTaskEnter();
//        baseMailTask.setCode(code);
//        baseMailTask.setEvent(MailTemplateEventEnums.MOBILE_PASSWORD.getEvent());
//
//        if (enter.getMail().indexOf("@") == (-1)) {
//            baseMailTask.setName(enter.getMail());
//        } else {
//            baseMailTask.setName(enter.getMail().split("@", 2)[0]);
//        }
//        baseMailTask.setToMail(enter.getMail());
//        baseMailTask.setToUserId(userByEmail.getUserId());
//        baseMailTask.setUserRequestId(enter.getRequestId());
//        baseMailTask.setMailAppId(AppIDEnums.SAAS_APP.getAppId());
//        baseMailTask.setMailSystemId(AppIDEnums.SAAS_APP.getSystemId());
//
//        mailMultiBaseTaskService.addSetPasswordMobileUserTask(ValidateCodeEnter.builder().code(code).event(MailTemplateEventEnums.MOBILE_PASSWORD.getEvent()).t(baseMailTask).build());

        return new GeneralResult(enter.getRequestId());
    }
}
