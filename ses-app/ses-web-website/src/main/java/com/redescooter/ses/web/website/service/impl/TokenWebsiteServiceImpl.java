package com.redescooter.ses.web.website.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.ClientTypeEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.CheckEmailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.GetAccountKeyResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.SendCodeMobileUserTaskEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.common.config.SendinBlueConfig;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.ip.IpUtils;
import com.redescooter.ses.web.website.config.RequestsKeyProperties;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dm.SiteUser;
import com.redescooter.ses.web.website.enums.SiteUserStatusEnum;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.TokenWebsiteService;
import com.redescooter.ses.web.website.service.base.SiteUserService;
import com.redescooter.ses.web.website.vo.login.RefreshTokenEnter;
import com.redescooter.ses.web.website.vo.login.SiteResetPassword;
import com.redescooter.ses.web.website.vo.login.SiteSetPasswordEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private SendinBlueConfig sendinBlueConfig;

    @Autowired
    private RequestsKeyProperties requestsKeyProperties;

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
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult signUp(LoginEnter enter) {
        checkUser(getUser(enter));
        return createUser(enter);
    }

    @Override
    public GetAccountKeyResult getAccountKey(GeneralEnter enter) {
        Map<String, String> stringStringMap = RsaUtils.generateRsaKey(RsaUtils.DEFAULT_RSA_KEY_SIZE);

        //设置缓存
        String key = new StringBuilder(enter.getRequestId()).append(":::").append(RsaUtils.PRIVATE_KEY).toString();
        jedisCluster.setex(key, (int) RedisExpireEnum.getSeconds(RedisExpireEnum.DAY_1.getTime()), stringStringMap.get(RsaUtils.PRIVATE_KEY));

        GetAccountKeyResult getAccountKeyResult = new GetAccountKeyResult();
        getAccountKeyResult.setPublicKey(stringStringMap.get(RsaUtils.PUBLIC_KEY));
        getAccountKeyResult.setRequestId(enter.getRequestId());
        return getAccountKeyResult;
    }


    /**
     * 用户登录
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public TokenResult login(LoginEnter enter) {
        //入参对象去空格
        SesStringUtils.objStringTrim(enter);
        if (enter.getPassword() != null) {
            String decryptPassword = "";
            String email = "";
            try {
                email = RsaUtils.decrypt(enter.getLoginName(), requestsKeyProperties.getPrivateKey());
                decryptPassword = RsaUtils.decrypt(enter.getPassword(), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setPassword(decryptPassword);
            enter.setLoginName(email);
        }
        SiteUser user = getUser(enter);
        if (user == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (StringUtils.isNotBlank(user.getLastLoginToken())) {
            // 清除原有token 生成新token
            jedisCluster.del(user.getLastLoginToken());
        }
        String loginPassword = DigestUtils.md5Hex(enter.getPassword() + user.getSalt());

        if (!loginPassword.equals(user.getPassword())) {
            throw new SesWebsiteException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        // 设置token和refresh_token
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
        updateUser.setRefreshToken(token.getRefreshToken());
        siteUserService.updateById(updateUser);

        TokenResult tokens = new TokenResult();
        tokens.setToken(token.getToken());
        tokens.setRefreshToken(token.getRefreshToken());
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
    public GeneralResult setPassword(SiteSetPasswordEnter enter) {
        //先给两个密码去空格（这个事应该前端就要做的）
        if (!Strings.isNullOrEmpty(enter.getNewPassword()) && !Strings.isNullOrEmpty(enter.getCfmPassword())) {
            String decrypt = null;
            String confirmDecrypt = null;
            try {
                //密码校验
                decrypt = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getNewPassword()), requestsKeyProperties.getPrivateKey());
                confirmDecrypt = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getCfmPassword()), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setNewPassword(decrypt);
            enter.setCfmPassword(confirmDecrypt);
        }
        //比较两个密码是否一致
        if (!StringUtils.equals(enter.getNewPassword(), enter.getCfmPassword())) {
            throw new SesWebsiteException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        //发邮件的时候  把用户的信息放在缓存里了  现在拿出来
        if (!jedisCluster.exists(enter.getRequestId())) {
            throw new SesWebsiteException(ExceptionCodeEnums.TOKEN_IS_EXPIRED.getCode(), ExceptionCodeEnums.TOKEN_IS_EXPIRED.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(enter.getRequestId());
        if (map == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.TOKEN_IS_EXPIRED.getCode(), ExceptionCodeEnums.TOKEN_IS_EXPIRED.getMessage());
        }
        // 已经从缓存里拿到了用户信息
        String userId = map.get("userId");
        if (StringUtils.isEmpty(userId)) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        changeUserPsd(enter, Long.valueOf(userId));

        //清楚缓存（一封邮件只允许设置一次密码）
        jedisCluster.del(enter.getRequestId());
        return new GeneralResult(enter.getRequestId());
    }


    // 修改用户的密码
    private void changeUserPsd(SiteSetPasswordEnter enter, Long userId) {
        SiteUser siteUser = siteUserService.getById(userId);
        if (siteUser == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //新旧密码一致 不可以
        if (StringUtils.equals(DigestUtils.md5Hex(enter.getNewPassword() + siteUser.getSalt()), siteUser.getPassword())) {
            throw new SesWebsiteException(ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getCode(), ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getMessage());
        }
        int salt = RandomUtils.nextInt(10000, 99999);
        String newPassword = DigestUtils.md5Hex(enter.getNewPassword() + salt);
        siteUser.setPassword(newPassword);
        siteUser.setSalt(String.valueOf(salt));
        siteUserService.updateById(siteUser);
    }

    /**
     * 发送重置密码邮件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult forgetPasswordEmail(BaseSendMailEnter enter) {
        String decryptEamil = "";
        if (StringUtils.isNotEmpty(enter.getMail())) {
            try {
                //邮箱解密
                decryptEamil = RsaUtils.decrypt(enter.getMail(), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            enter.setMail(decryptEamil);
        }

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
        baseMailTask.setToUserId(user.getId());
        baseMailTask.setUserRequestId(enter.getRequestId());
        baseMailTask.setEvent(MailTemplateEventEnums.FORGET_PSD_SEND_MAIL.getEvent());
        baseMailTask.setMailSystemId(SystemIDEnums.REDE_SES.getSystemId());
        baseMailTask.setMailAppId(AppIDEnums.SES_ROS.getValue());
        mailMultiTaskService.addSetPasswordWebUserTask(baseMailTask);

        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public GeneralResult resetPassword(SiteResetPassword enter) {

        //密码解密
        if (!Strings.isNullOrEmpty(enter.getNewPassword()) && !Strings.isNullOrEmpty(enter.getOldPassword()) && !Strings.isNullOrEmpty(enter.getCfmPassword())) {
            String newPsd = null;
            String oldPsd = null;
            String cfmPsd = null;
            try {
                //密码解密
                newPsd = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getNewPassword()), requestsKeyProperties.getPrivateKey());
                oldPsd = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getOldPassword()), requestsKeyProperties.getPrivateKey());
                cfmPsd = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getCfmPassword()), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            //解密保存到VO
            enter.setNewPassword(newPsd);
            enter.setOldPassword(oldPsd);
            enter.setCfmPassword(cfmPsd);
        }
        SiteUser user = siteUserService.getById(enter.getUserId());
        if (user == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //登录密码
        String password = DigestUtils.md5Hex(enter.getOldPassword() + user.getSalt());
        if (!password.equals(user.getPassword())) {
            throw new SesWebsiteException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        //比较两个密码是否一致
        if (!StringUtils.equals(enter.getNewPassword(), enter.getCfmPassword())) {
            throw new SesWebsiteException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        changeUserPsd(enter, user.getId());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult emailSubscribe(CheckEmailEnter enter) {
        String decryptEamil = "";
        if (StringUtils.isNotEmpty(enter.getEmail())) {
            try {
                //邮箱解密
                decryptEamil = RsaUtils.decrypt(enter.getEmail(), requestsKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            enter.setEmail(decryptEamil);
        }
        String eamil = enter.getEmail();
        adPush(eamil);
        return new GeneralResult(enter.getRequestId());
    }

    private void adPush(String email) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse(sendinBlueConfig.getMediaType());

        Map<String, String> map = new HashMap<>();
        map.put("updateEnabled", sendinBlueConfig.getUpdateEnabled());
        map.put("email", email);

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(map));
        Request request = new Request.Builder()
                .url(sendinBlueConfig.getUrl())
                .post(body)
                .addHeader("accept", sendinBlueConfig.getAccept())
                .addHeader("content-type", sendinBlueConfig.getContentType())
                .addHeader("api-key", sendinBlueConfig.getApiKey())
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("response" + response.message());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BaseMailTaskEnter enter = new BaseMailTaskEnter();
        enter.setEvent(MailTemplateEventEnums.SUBSCRIBE_TO_EMAIL_SUCCESSFULLY.getEvent());
        enter.setMailSystemId(AppIDEnums.SES_ROS.getSystemId());
        enter.setMailAppId(SystemIDEnums.REDE_SES.getValue());
        enter.setToMail(email);
        enter.setCode("0");
        enter.setRequestId("0");
        enter.setUserRequestId("0");
        enter.setToUserId(0L);
        enter.setUserId(0L);
        mailMultiTaskService.subscribeToEmailSuccessfully(enter);

        // todo  数据同步Monday
        //mondayService.websiteSubscriptionEmail(email);
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
            throw new SesWebsiteException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        return userToken;
    }

    private void checkUser(SiteUser user) {
        if (user != null) {
            throw new SesWebsiteException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
    }

    private UserToken getUserToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new SesWebsiteException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(token);
        if (map == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
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
     * 刷新token
     */
    @Override
    public TokenResult refreshToken(RefreshTokenEnter enter) {
        if (null == enter || StringUtils.isBlank(enter.getRefreshToken())) {
            throw new SesWebsiteException(ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getMessage());
        }
        String refreshToken = enter.getRefreshToken();
        Boolean flag = jedisCluster.exists(refreshToken);
        // 刷新token在redis不存在,直接抛出异常,让前端返回登录页面
        if (!flag) {
            throw new SesWebsiteException(ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getMessage());
        }

        // 生成新的access_token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // 获得refresh_token的原来的值
        Map<String, String> map = jedisCluster.hgetAll(refreshToken);
        map.put("token", token);

        // redis存储新的access_token
        jedisCluster.hmset(token, map);
        //jedisCluster.expire(token, new Long(RedisExpireEnum.MINUTES_60.getSeconds()).intValue());
        jedisCluster.expire(token, new Long(RedisExpireEnum.SECOND_5.getSeconds()).intValue());  // 测试使用
        // 更新db
        SiteUser model = new SiteUser();
        Long userId = Long.valueOf(map.get("userId"));
        model.setId(userId);
        model.setLastLoginToken(token);
        model.setLastLoginTime(new Date());
        siteUserService.updateById(model);

        TokenResult result = new TokenResult();
        result.setToken(token);
        result.setRefreshToken(refreshToken);
        return result;
    }

    /**
     * 设置token
     *
     * @param enter
     * @param user
     * @return
     */
    public UserToken setToken(LoginEnter enter, SiteUser user) {
        // 产生token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // 产生refresh_token
        String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");

        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setRefreshToken(refreshToken);
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
            //jedisCluster.expire(token, new Long(RedisExpireEnum.MINUTES_60.getSeconds()).intValue());
            jedisCluster.expire(token, new Long(RedisExpireEnum.SECOND_5.getSeconds()).intValue());  //测试使用
            jedisCluster.hmset(refreshToken, map);
            //jedisCluster.expire(refreshToken, new Long(RedisExpireEnum.DAY_1.getSeconds()).intValue());
            jedisCluster.expire(refreshToken, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());  // 测试使用
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
