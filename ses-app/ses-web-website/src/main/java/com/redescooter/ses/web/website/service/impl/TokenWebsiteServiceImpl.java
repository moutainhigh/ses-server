package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.ClientTypeEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.SendCodeMobileUserTaskEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.ip.IpUtils;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteUser;
import com.redescooter.ses.web.website.enums.SiteUserStatusEnum;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.TokenWebsiteService;
import com.redescooter.ses.web.website.service.base.SiteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author Jerry
 * @version V1.0
 * @Date: 03/01/2021 10:50
 * @ClassName: TokenRepairServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class TokenWebsiteServiceImpl implements TokenWebsiteService {

    @Autowired
    private SiteUserService siteUserService;

    @Autowired
    private JedisCluster jedisCluster;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * 用户创建
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult signUp(LoginEnter enter) {
        checkUser(getUser(enter));
        return createUser(enter);
    }

    /**
     * 用户登录
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public TokenResult login(LoginEnter enter) {

        SiteUser user = getUser(enter);
        if (user == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (StringUtils.isNotBlank(user.getLastLoginToken())) {
            // 清除原有token 生成新token
            jedisCluster.del(user.getLastLoginToken());
        }
        String loginPassword = DigestUtils.md5Hex(enter.getPassword() + user.getSalt());

        if (!loginPassword.equals(user.getPassword())) {
            throw new SesWebsiteException(ExceptionCodeEnums.PASSROD_WRONG.getCode(),
                    ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        UserToken token = setToken(enter, user);

        // 数据库同步登陆信息
        SiteUser updateUser = new SiteUser();
        updateUser.setId(user.getId());

        if (user.getActivationTime() == null) {
            user.setActivationTime(new Date());
        }
        updateUser.setActivationTime(new Date());
        updateUser.setLastLoginIp(token.getClientIp());
        updateUser.setLastLoginTime(new Date(token.getTimestamp()));
        updateUser.setLastLoginToken(token.getToken());
        siteUserService.updateById(updateUser);

        TokenResult tokens = new TokenResult();
        tokens.setToken(token.getToken());
        tokens.setRequestId(enter.getRequestId() == null ? UUID.randomUUID().toString().replaceAll("-", "") : enter.getRequestId());
        return tokens;
    }

    /**
     * 用户登出
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult logout(GeneralEnter enter) {
        String token = enter.getToken();
        jedisCluster.del(token);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 设置密码
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult setPassword(ModifyPasswordEnter enter) {

        return null;
    }

    /**
     * 发送重置密码邮件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult forgetPasswordEmail(BaseSendMailEnter enter) {

        SiteUser user = siteUserService.getOne(new QueryWrapper<SiteUser>()
                .eq(SiteUser.COL_LOGIN_NAME, enter.getMail()));
        if (user == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        SendCodeMobileUserTaskEnter baseMailTask = new SendCodeMobileUserTaskEnter();
        BeanUtils.copyProperties(enter, baseMailTask);
        baseMailTask.setEvent(MailTemplateEventEnums.FORGET_PSD_SEND_MAIL.getEvent());

        if (enter.getMail().indexOf("@") == (-1)) {
            baseMailTask.setName(enter.getMail());
        } else {
            baseMailTask.setName(enter.getMail().split("@", 2)[0]);
        }
        baseMailTask.setToMail(enter.getMail());
        baseMailTask.setToUserId(new Long("0"));
        baseMailTask.setUserRequestId(enter.getRequestId());
        baseMailTask.setEvent(MailTemplateEventEnums.FORGET_PSD_SEND_MAIL.getEvent());
        baseMailTask.setMailAppId(AppIDEnums.SES_WEBSITE.getValue());
        baseMailTask.setMailSystemId(AppIDEnums.SES_WEBSITE.getSystemId());
        mailMultiTaskService.addSetPasswordWebUserTask(baseMailTask);

        return new GeneralResult(enter.getRequestId());
    }

    private SiteUser getUser(LoginEnter enter) {
        return siteUserService.getOne(new QueryWrapper<SiteUser>()
                .eq(SiteUser.COL_DR, Constant.DR_FALSE)
                .eq(SiteUser.COL_LOGIN_NAME, enter.getLoginName()));
    }

    private GeneralResult createUser(LoginEnter enter) {
        SiteUser user = new SiteUser();
        user.setId(idAppService.getId(SequenceName.SITE_USER));
        user.setDr(Constant.DR_FALSE);
        user.setAppId(AppIDEnums.SES_WEBSITE.getAppId());
        user.setSystemId(SystemIDEnums.REDE_SITE.getSystemId());

        int salt = RandomUtils.nextInt(10000, 99999);

        user.setPassword(DigestUtils.md5Hex(enter.getPassword() + salt));
        user.setSalt(String.valueOf(salt));
        user.setStatus(SiteUserStatusEnum.NORMAL.getValue());
        user.setLoginName(enter.getLoginName());
        user.setCustomerId(enter.getCustomerId());
        user.setSystemId(SystemIDEnums.REDE_SITE.getSystemId());
        user.setAppId(AppIDEnums.SES_WEBSITE.getValue());

        user.setSynchronizeFlag(false);
        user.setRevision(0);
        user.setCreatedBy(enter.getUserId());
        user.setCreatedTime(new Date());
        user.setUpdatedBy(enter.getUserId());
        user.setUpdatedTime(new Date());
        siteUserService.save(user);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 登陆token检查
     *
     * @param enter
     * @return
     */
    @Override
    public UserToken checkToken(GeneralEnter enter) {
        UserToken userToken = getUserToken(enter.getToken());
        if (!StringUtils.equals(userToken.getClientType(), enter.getClientType())
                || !StringUtils.equals(userToken.getSystemId(), enter.getSystemId())
                || !StringUtils.equals(userToken.getAppId(), enter.getAppId())) {
            throw new SesWebsiteException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        return userToken;
    }

    private void checkUser(SiteUser user) {
        if (user != null) {
            throw new SesWebsiteException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(),
                    ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
    }

    private UserToken getUserToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new SesWebsiteException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(token);
        if (map == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        UserToken userToken = new UserToken();
        try {
            org.apache.commons.beanutils.BeanUtils.populate(userToken, map);
        } catch (IllegalAccessException e) {
            log.error("checkToken IllegalAccessException sessionMap:" + map, e);
        } catch (InvocationTargetException e) {
            log.error("checkToken IllegalAccessException sessionMap:" + map, e);
        }
        return userToken;
    }

    /**
     * 设置token
     *
     * @param enter
     * @param user
     * @return
     */
    public UserToken setToken(LoginEnter enter, SiteUser user) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(user.getId());
        userToken.setTenantId(new Long("0"));
        userToken.setSystemId(user.getSystemId());
        userToken.setAppId(user.getAppId());
        if (enter.getClientIp() == null) {
            // 获取request对象
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String ip = IpUtils.getIpAddr(request);
            userToken.setClientIp(ip);
        } else {
            userToken.setClientIp(enter.getClientIp());
        }
        userToken.setClientType(enter.getClientType() == null ? ClientTypeEnums.PC.getValue() : enter.getClientType());
        userToken.setCountry(enter.getCountry() == null ? "fr" : enter.getCountry());
        userToken.setLanguage(enter.getLanguage() == null ? "fr" : enter.getLanguage());
        userToken.setTimestamp(System.currentTimeMillis());
        userToken.setTimeZone(String.valueOf(TimeZone.getDefault()));
        userToken.setVersion(Constant.DEFAULT_VERSION);
        userToken.setRequestId(enter.getRequestId());
        try {
            Map<String, String> map = org.apache.commons.beanutils.BeanUtils.describe(userToken);
            map.remove("requestId");
            map.remove("deptId");

            jedisCluster.hmset(token, map);
            jedisCluster.expire(token, new Long(RedisExpireEnum.MINUTES_30.getSeconds()).intValue());
        } catch (IllegalAccessException e) {
            log.error("setToken IllegalAccessException userSession:" + userToken, e);
        } catch (InvocationTargetException e) {
            log.error("setToken InvocationTargetException userSession:" + userToken, e);
        } catch (NoSuchMethodException e) {
            log.error("setToken NoSuchMethodException userSession:" + userToken, e);
        }
        return userToken;
    }
}
