package com.redescooter.ses.mobile.client.service.impl;

import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.login.SendCodeMobileUserTaskEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.mobile.client.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.client.exception.SesMobileClientException;
import com.redescooter.ses.mobile.client.service.TokenService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

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
    private MailMultiTaskService mailMultiTaskService;

    @Reference
    private UserTokenService userTokenService;

    @Reference
    private AccountBaseService accountBaseService;

    @Reference
    private UserBaseService userBaseService;

    @Override
    public UserToken checkAndGetSession(GeneralEnter enter) {
        return userTokenService.checkToken(enter);
    }

    @Override
    public GeneralResult sendCode(BaseSendMailEnter enter) {

        //1. 确定邮件是否存在
        Boolean aBoolean = accountBaseService.chectMail(enter.getMail());
        if (!aBoolean) {
            throw new SesMobileClientException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //2. 加入邮箱任务
        String code = String.valueOf(RandomUtils.nextInt(1000, 9999));
        SendCodeMobileUserTaskEnter baseMailTask = new SendCodeMobileUserTaskEnter();

        BeanUtils.copyProperties(enter, baseMailTask);
        baseMailTask.setCode(code);
        baseMailTask.setEvent(MailTemplateEventEnums.MOBILE_PASSWORD.getEvent());

        if (enter.getMail().indexOf("@") == (-1)) {
            baseMailTask.setName(enter.getMail());
        } else {
            baseMailTask.setName(enter.getMail().split("@", 2)[0]);
        }
        baseMailTask.setToMail(enter.getMail());
        baseMailTask.setToUserId(new Long("0"));
        baseMailTask.setUserRequestId(enter.getRequestId());
        baseMailTask.setEvent(MailTemplateEventEnums.MOBILE_PASSWORD.getEvent());
        baseMailTask.setMailAppId(AppIDEnums.SAAS_APP.getValue());
        baseMailTask.setMailSystemId(AppIDEnums.SAAS_APP.getSystemId());
        mailMultiTaskService.addSetPasswordMobileUserTask(baseMailTask);

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult setPassword(SetPasswordEnter enter) {

        //密码去空格
        enter.setNewPassword(SesStringUtils.stringTrim(enter.getNewPassword()));
        enter.setConfirmPassword(SesStringUtils.stringTrim(enter.getConfirmPassword()));
        enter.setCode(SesStringUtils.stringTrim(enter.getCode()));
        //todo
        Map<String, String> map = jedisCluster.hgetAll(enter.getRequestId());
        if (map == null) {
            throw new SesMobileClientException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(map.get("code"), enter.getCode())) {
            throw new SesMobileClientException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
        }
        if (!StringUtils.equals(enter.getConfirmPassword(), enter.getNewPassword())) {
            throw new SesMobileClientException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        return userTokenService.setPassword(enter);
    }
}
