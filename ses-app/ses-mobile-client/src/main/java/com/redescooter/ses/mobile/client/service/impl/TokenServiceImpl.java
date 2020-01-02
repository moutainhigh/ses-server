package com.redescooter.ses.mobile.client.service.impl;

import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.mail.MailTemplateEventEnum;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.login.SetPasswordMobileUserTaskEnter;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.mobile.client.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.client.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Map;

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
    private AccountBaseService accountBaseService;

    @Reference
    private MailMultiTaskService mailMultiTaskService;

    @Override
    public UserToken checkAndGetSession(GeneralEnter enter) {
        return userTokenService.checkToken(enter);
    }

    @Override
    public GeneralResult sendCode(BaseSendMailEnter enter) {

        GetUserEnter getUserEnter = new GetUserEnter();
        getUserEnter.setLoginName(enter.getMail());
        getUserEnter.setSystemId(AccountTypeEnums.APP_EXPRESS.getSystemId());
        getUserEnter.setAppId(AppIDEnums.SAAS_APP.getValue());
        List<UserToken> appUserList = userTokenService.getAppUser(getUserEnter);

        String code = String.valueOf(RandomUtils.nextInt(10000, 99999));

        BaseMailTaskEnter baseMailTask = new BaseMailTaskEnter();
        baseMailTask.setCode(code);
        baseMailTask.setEvent(MailTemplateEventEnums.MOBILE_PASSWORD.getEvent());

        if (enter.getMail().indexOf("@") == (-1)) {
            baseMailTask.setName(enter.getMail());
        } else {
            baseMailTask.setName(enter.getMail().split("@", 2)[0]);
        }
        baseMailTask.setToMail(enter.getMail());
        baseMailTask.setToUserId(appUserList.get(0).getUserId());
        baseMailTask.setUserRequestId(enter.getRequestId());
        baseMailTask.setEvent(MailTemplateEventEnum.MOBILE_PASSWORD.getEvent());
        baseMailTask.setMailAppId(AppIDEnums.SAAS_APP.getValue());
        baseMailTask.setMailSystemId(AppIDEnums.SAAS_APP.getSystemId());
        SetPasswordMobileUserTaskEnter setPasswordMobileUserTaskEnter = SetPasswordMobileUserTaskEnter.builder().code(code).build();
        BeanUtils.copyProperties(baseMailTask, setPasswordMobileUserTaskEnter);
        mailMultiTaskService.addSetPasswordMobileUserTask(setPasswordMobileUserTaskEnter);

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult setPassword(SetPasswordEnter enter) {
        Map<String, String> map = jedisCluster.hgetAll(enter.getRequestId());
        if (map == null) {
            throw new MobileBException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(map.get("verificationCode"), enter.getCode())) {
            throw new MobileBException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
        }
        if (!StringUtils.equals(enter.getConfirmPassword(), enter.getNewPassword())) {
            throw new MobileBException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        enter.setUserId(Long.valueOf(map.get("userId")));
        QueryUserResult queryUserResult = accountBaseService.queryUserById(enter);
        enter.setAppId(queryUserResult.getAppId());
        enter.setSystemId(queryUserResult.getSystemId());
        return userTokenService.setPassword(enter);
    }
}
