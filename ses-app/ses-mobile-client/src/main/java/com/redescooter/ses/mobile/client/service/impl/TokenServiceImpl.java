package com.redescooter.ses.mobile.client.service.impl;

import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.ValidateCodeEnter;
import com.redescooter.ses.api.foundation.service.base.MailMultiBaseTaskService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.proxy.vo.mail.SendMailEnter;
import com.redescooter.ses.mobile.client.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 21/11/2019 5:45 下午
 * @ClassName: TokenServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisCluster jedisCluster;

    @Reference
    private UserTokenService userTokenService;
    @Reference
    private MailMultiBaseTaskService mailMultiBaseTaskService;


    @Override
    public UserToken checkAndGetSession(GeneralEnter enter) {
        return userTokenService.checkToken(enter);
    }

    @Override
    public GeneralResult sendCode(SendMailEnter enter) {

        GetUserEnter user = new GetUserEnter();
        user.setEmail(enter.getMail());
        user.setSystemId(AppIDEnums.SAAS_APP.getSystemId());
        user.setAppId(AppIDEnums.SAAS_APP.getAppId());
        UserToken userByEmail = userTokenService.getUserByEmailType(user);

        String code = String.valueOf(RandomUtils.nextInt(10000, 99999));
        // 存入redis
        jedisCluster.set(enter.getRequestId(), code);
        // 设置超时时间
        jedisCluster.expire(enter.getRequestId(), 60);

        BaseMailTaskEnter baseMailTask = new BaseMailTaskEnter();
        baseMailTask.setCode(code);
        baseMailTask.setEvent(MailTemplateEventEnums.MOBILE_PASSWORD.getEvent());

        if (enter.getMail().indexOf("@") == (-1)) {
            baseMailTask.setName(enter.getMail());
        } else {
            baseMailTask.setName(enter.getMail().split("@", 2)[0]);
        }
        baseMailTask.setToMail(enter.getMail());
        baseMailTask.setToUserId(userByEmail.getUserId());
        baseMailTask.setUserRequestId(enter.getRequestId());
        baseMailTask.setMailAppId(AppIDEnums.SAAS_APP.getAppId());
        baseMailTask.setMailSystemId(AppIDEnums.SAAS_APP.getSystemId());

        mailMultiBaseTaskService.addSetPasswordMobileUserTask(ValidateCodeEnter.builder().code(code).event(MailTemplateEventEnums.MOBILE_PASSWORD.getEvent()).t(baseMailTask).build());

        return new GeneralResult(enter.getRequestId());
    }
}
