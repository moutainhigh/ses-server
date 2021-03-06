package com.redescooter.ses.admin.dev.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.admin.dev.dm.AdmSysUser;
import com.redescooter.ses.admin.dev.exception.ExceptionCodeEnums;
import com.redescooter.ses.admin.dev.exception.SesAdminDevException;
import com.redescooter.ses.admin.dev.service.base.AdmSysUserService;
import com.redescooter.ses.admin.dev.service.base.AdminTokenService;
import com.redescooter.ses.admin.dev.vo.user.UserInfoResult;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.SendCodeMobileUserTaskEnter;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassNameAdminTokenServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/12/3 10:01
 * @Version V1.0
 **/
@Service
@Slf4j
public class AdminTokenServiceImpl implements AdminTokenService {

    @Autowired
    private AdmSysUserService admSysUserService;

    @Autowired
    private JedisCluster jedisCluster;

    @DubboReference
    private UserTokenService userTokenService;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @Value("${Request.privateKey}")
    private String privateKey;

    /**
     * @return
     * @Author Aleks
     * @Description ?????????????????????
     * @Date 2020/12/3 14:03
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public TokenResult login(LoginEnter enter) {

        //???????????????????????????
        enter.setLoginName(SesStringUtils.stringTrim(enter.getLoginName()));

        QueryWrapper<AdmSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(AdmSysUser.COL_DR,0);
        wrapper.eq(AdmSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(AdmSysUser.COL_APP_ID, enter.getAppId());
        wrapper.eq(AdmSysUser.COL_SYSTEM_ID, enter.getSystemId());
        wrapper.eq(AdmSysUser.COL_DEF1, SysUserSourceEnum.SYSTEM.getValue());
        wrapper.last("limit 1");
        AdmSysUser sysUser = admSysUserService.getOne(wrapper);
        //??????????????????????????????????????????????????????????????????????????????
        if (sysUser == null) {
            throw new SesAdminDevException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        QueryWrapper<AdmSysUser> wrapper1 = new QueryWrapper<>();
        wrapper1.eq(AdmSysUser.COL_STATUS,1);
        wrapper1.eq(AdmSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper1.eq(AdmSysUser.COL_APP_ID, enter.getAppId());
        wrapper1.eq(AdmSysUser.COL_SYSTEM_ID, enter.getSystemId());
        wrapper1.eq(AdmSysUser.COL_DEF1, SysUserSourceEnum.SYSTEM.getValue());
        AdmSysUser statusResult = admSysUserService.getOne(wrapper1);
        if (statusResult == null){
            throw new SesAdminDevException(ExceptionCodeEnums.INVALID_ACCOUNT_STATUS.getCode(), ExceptionCodeEnums.INVALID_ACCOUNT_STATUS.getMessage());
        }
        // ?????????????????????????????????  2020 7 17
        //????????????
        String decryptPassword = checkPassWord(enter);
        //??????MD5 ??????
        String password = DigestUtils.md5Hex(decryptPassword + sysUser.getSalt());
        String psdErrorKey = JedisConstant.LOGIN_PSD_ERROR_NUM + sysUser.getId();
        if (jedisCluster.exists(psdErrorKey)) {
            Map<String, String> data = jedisCluster.hgetAll(psdErrorKey);
            String oldNum = data.get("num");
            if (Integer.parseInt(oldNum) >= 5) {
                throw new SesAdminDevException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getMessage());
            }
        }
        if (!password.equals(sysUser.getPassword())) {
            //????????????3????????????????????????????????????????????????5???????????????1??????
            Integer errorNum = passWordMistaken(psdErrorKey);
            if (errorNum < 3) {
                throw new SesAdminDevException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            } else if (errorNum == 3 || errorNum == 4) {
                throw new SesAdminDevException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getMessage());
            } else {
                throw new SesAdminDevException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getMessage());
            }
        }
        //???????????????????????????????????????????????????????????????????????????????????????
        if (jedisCluster.exists(psdErrorKey)) {
            jedisCluster.del(psdErrorKey);
        }
        TokenResult result = getTokenResult(enter, sysUser);
        return result;
    }


    /**
     * Rsa ????????????
     *
     * @param enter
     * @return
     */
    private String checkPassWord(LoginEnter enter) {
        //???????????? ??????????????? ????????????????????????
        if (StringUtils.isBlank(enter.getPassword())) {
            throw new SesAdminDevException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(), ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
        }
        enter.setPassword(SesStringUtils.stringTrim(enter.getPassword()));
        String decryptPassword = "";
        try {
            decryptPassword = RsaUtils.decrypt(enter.getPassword(), privateKey);
        } catch (Exception e) {
            throw new SesAdminDevException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        return decryptPassword;
    }


    public Integer passWordMistaken(String key) {
        Integer num = 1;
        Map<String, String> map = new HashMap<>();
        // ??????????????????????????????????????????
        if (jedisCluster.exists(key)) {
            //???????????????????????????????????????????????????
            Map<String, String> data = jedisCluster.hgetAll(key);
            String oldNum = data.get("num");
            if (Integer.parseInt(oldNum) >= 5) {
                return Integer.parseInt(oldNum);
            }
            num = Integer.parseInt(oldNum) + 1;
            if (num == 5) {
                map.put("num", num.toString());
                jedisCluster.hmset(key, map);
                jedisCluster.expire(key, Long.valueOf(RedisExpireEnum.MINUTES_1.getSeconds()).intValue());
                return num;
            }
        }
        map.put("num", num.toString());
        jedisCluster.hmset(key, map);
        return num;
    }


    private TokenResult getTokenResult(LoginEnter enter, AdmSysUser sysUser) {
        //??????????????????token??????
        if (StringUtils.isNotBlank(sysUser.getLastLoginToken())) {
            jedisCluster.del(sysUser.getLastLoginToken());

            //???????????????????????? ???????????????key???
            jedisCluster.del(new StringBuilder(enter.getRequestId()).append(":::").append(RsaUtils.PRIVATE_KEY).toString());
            String key = new StringBuilder(enter.getLoginName()).append(":::").append("LOGIN").toString();
            jedisCluster.setex(key, (int) RedisExpireEnum.getSeconds(RedisExpireEnum.DAY_1.getTime()), key);
        }
        //???token????????????????????? ??????Redis???
        UserToken userToken = setToken(enter, sysUser);
        if (Strings.isNullOrEmpty(sysUser.getLastLoginToken()) && !sysUser.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            // ?????????????????????token??????  ????????????????????? ??????????????? ??????????????????????????????????????????(???????????????????????????????????????)
            String key = JedisConstant.FIRST_LOGIN_RESET_PSD + sysUser.getId();
            Map<String, String> map = new HashMap<>();
            map.put("flag", "1");
            jedisCluster.hmset(key, map);
        }
        //??????????????????,???????????????
        //  setAuth(userRole.getRoleId());

        sysUser.setLastLoginToken(userToken.getToken());
        sysUser.setLastLoginTime(new Date(enter.getTimestamp()));
        sysUser.setLastLoginIp(enter.getClientIp());
        sysUser.setUpdatedBy(enter.getUserId());
        sysUser.setUpdatedTime(new Date());
        admSysUserService.updateById(sysUser);

        TokenResult result = new TokenResult();
        result.setToken(userToken.getToken());
        result.setRequestId(enter.getRequestId());
        return result;
    }


    private UserToken setToken(LoginEnter enter, AdmSysUser user) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(user.getId());
        userToken.setTenantId(new Long("0"));
        userToken.setSystemId(enter.getSystemId());
        userToken.setAppId(enter.getAppId());
        userToken.setClientIp(enter.getClientIp());
        userToken.setClientType(enter.getClientType());
        userToken.setCountry(enter.getCountry());
        userToken.setLanguage(enter.getLanguage());
        userToken.setTimestamp(enter.getTimestamp());
        userToken.setTimeZone(enter.getTimeZone());
        userToken.setVersion(enter.getVersion());
        userToken.setDeptId(user.getDeptId());
        try {
            Map<String, String> map = org.apache.commons.beanutils.BeanUtils.describe(userToken);
            map.remove("requestId");
            map.remove("refreshToken");
            jedisCluster.hmset(token, map);
            jedisCluster.expire(token, new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());
        } catch (IllegalAccessException e) {
            log.error("setToken IllegalAccessException userSession:" + userToken, e);
        } catch (InvocationTargetException e) {
            log.error("setToken InvocationTargetException userSession:" + userToken, e);
        } catch (NoSuchMethodException e) {
            log.error("setToken NoSuchMethodException userSession:" + userToken, e);
        }
        return userToken;
    }


    /**
     * @return
     * @Author Aleks
     * @Description ????????????????????????????????????????????????????????????????????????
     * @Date 2020/12/3 14:03
     * @Param [enter]
     **/
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult emailLoginSendCode(LoginEnter enter) {
        // ??????????????????????????????????????????????????????
        AdmSysUser sysUser = getOpeSysUser(enter);
        // ????????????????????????  ?????????????????????  ???????????????
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        String key = JedisConstant.EMAIL_LOGIN_CODE + enter.getLoginName();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        jedisCluster.hmset(key, map);
        // ?????????????????????5??????
        jedisCluster.expire(key, Long.valueOf(RedisExpireEnum.MINUTES_5.getSeconds()).intValue());
        // ??????????????????  ????????????????????????  ???????????????????????????????????????  (??????????????????)
        SendCodeMobileUserTaskEnter sendCodeMobileUserTaskEnter = new SendCodeMobileUserTaskEnter();
        sendCodeMobileUserTaskEnter.setCode(code);
        sendCodeMobileUserTaskEnter.setEvent(MailTemplateEventEnums.ROS_LOGIN_BY_CODE.getEvent());
        sendCodeMobileUserTaskEnter.setAppId(sysUser.getAppId());
        sendCodeMobileUserTaskEnter.setSystemId(sysUser.getSystemId());
        sendCodeMobileUserTaskEnter.setToMail(enter.getLoginName());
        sendCodeMobileUserTaskEnter.setUserRequestId(enter.getRequestId());
        sendCodeMobileUserTaskEnter.setMailAppId(sysUser.getAppId());
        sendCodeMobileUserTaskEnter.setMailSystemId(sysUser.getSystemId());
        sendCodeMobileUserTaskEnter.setName(sysUser.getLoginName().split("@")[0]);
        sendCodeMobileUserTaskEnter.setUserId(sysUser.getId());
        sendCodeMobileUserTaskEnter.setRequestId(enter.getRequestId());
        sendCodeMobileUserTaskEnter.setEmail(enter.getLoginName());
        mailMultiTaskService.addMultiMailTask(sendCodeMobileUserTaskEnter);
        return new GeneralResult(enter.getRequestId());
    }


    private AdmSysUser getOpeSysUser(LoginEnter enter) {
        if (enter.getLoginName().length() > 50 || enter.getLoginName().length() < 2) {
            throw new SesAdminDevException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
        }
        QueryWrapper<AdmSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(AdmSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(AdmSysUser.COL_APP_ID, enter.getAppId());
        wrapper.eq(AdmSysUser.COL_SYSTEM_ID, enter.getSystemId());
        wrapper.eq(AdmSysUser.COL_DEF1, SysUserSourceEnum.SYSTEM.getValue());
        wrapper.last("limit 1");
        AdmSysUser sysUser = admSysUserService.getOne(wrapper);
        //??????????????????????????????????????????????????????????????????????????????
        if (sysUser == null) {
            throw new SesAdminDevException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        return sysUser;
    }


    /**
     * @return
     * @Author Aleks
     * @Description ????????????????????????
     * @Date 2020/12/3 14:03
     * @Param [enter]
     **/
    @Override
    public TokenResult emailLogin(LoginEnter enter) {
        if (Strings.isNullOrEmpty(enter.getCode())) {
            throw new SesAdminDevException(ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getCode(), ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getMessage());
        }
        String key = JedisConstant.EMAIL_LOGIN_CODE + enter.getLoginName();
        if (!jedisCluster.exists(key)) {
            throw new SesAdminDevException(ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getCode(), ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(key);
        String code = map.get("code");
        if (Strings.isNullOrEmpty(code)) {
            throw new SesAdminDevException(ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getCode(), ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getMessage());
        }
        if (!code.equals(enter.getCode())) {
            throw new SesAdminDevException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
        }
        // ????????? ???????????????????????????????????????????????? ???????????? ??????????????????????????????
        jedisCluster.del(key);
        // ???????????????????????????????????????
        AdmSysUser sysUser = getOpeSysUser(enter);
        TokenResult result = getTokenResult(enter, sysUser);
        return result;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult modifyPassword(ModifyPasswordEnter enter) {
        //???????????????
        if (StringUtils.isNotEmpty(enter.getOldPassword())) {
            enter.setOldPassword(SesStringUtils.stringTrim(enter.getOldPassword()));
        }
        if (StringUtils.isNotEmpty(enter.getNewPassword())) {
            enter.setNewPassword(SesStringUtils.stringTrim(enter.getNewPassword()));
        }

        if (StringUtils.isNotEmpty(enter.getNewPassword()) && StringUtils.isNotEmpty(enter.getOldPassword())) {
            String newPassword = "";
            String confirmPassword = "";
            try {
                newPassword = RsaUtils.decrypt(enter.getNewPassword(), privateKey);
                confirmPassword = RsaUtils.decrypt(enter.getOldPassword(), privateKey);
            } catch (Exception e) {
                throw new SesAdminDevException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setNewPassword(newPassword);
            enter.setOldPassword(confirmPassword);
        }

        if (!StringUtils.equals(enter.getNewPassword(), enter.getOldPassword())) {
            throw new SesAdminDevException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        GetUserEnter getUser = getGetUserEnter(enter);
        QueryWrapper<AdmSysUser> emailUser = new QueryWrapper<>();
        emailUser.eq(AdmSysUser.COL_LOGIN_NAME, getUser.getEmail());
        emailUser.eq(AdmSysUser.COL_APP_ID, getUser.getAppId());
        emailUser.eq(AdmSysUser.COL_SYSTEM_ID, getUser.getSystemId());
        emailUser.eq(AdmSysUser.COL_ID, getUser.getUserId());
        emailUser.last("limit 1");
        AdmSysUser opeSysUser = admSysUserService.getOne(emailUser);
        if (opeSysUser == null) {
            throw new SesAdminDevException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(), ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
        }

        opeSysUser.setPassword(DigestUtils.md5Hex(enter.getOldPassword() + opeSysUser.getSalt()));
        opeSysUser.setUpdatedBy(opeSysUser.getId());
        opeSysUser.setUpdatedTime(new Date());
        admSysUserService.updateById(opeSysUser);

        if (StringUtils.isNotBlank(opeSysUser.getLastLoginToken())) {
            // ????????????token,????????????
            jedisCluster.del(opeSysUser.getLastLoginToken());
            jedisCluster.del(enter.getRequestId());
        }
        //token ?????????????????????????????? ?????????????????? ?????? ??????????????????requestId ???????????????
        if (StringUtils.isBlank(enter.getToken())) {
            if (jedisCluster.exists(enter.getRequestId())) {
                jedisCluster.del(enter.getRequestId());
            }
        }
        return new GeneralResult(enter.getRequestId());
    }


    private GetUserEnter getGetUserEnter(ModifyPasswordEnter enter) {
        GetUserEnter getUser = new GetUserEnter();
        getUser.setRequestId(enter.getRequestId());

        if (StringUtils.isNotBlank(enter.getToken())) {
            // ????????????
            String code = jedisCluster.get(enter.getRequestId());
            if (Strings.isNullOrEmpty(code)) {
                throw new SesAdminDevException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(), ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            if (!StringUtils.equals(code, enter.getCode())) {
                throw new SesAdminDevException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
            }
            // ??????????????????????????????
            UserToken userToken = getUserToken(enter.getToken());
            if (userToken.getUserId() == null || userToken.getUserId() == 0) {
                throw new FoundationException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(), ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            getUser.setUserId(userToken.getUserId());
            getUser.setAppId(enter.getAppId());
            getUser.setSystemId(enter.getSystemId());
        } else {
            // ??????????????????????????????
            Map<String, String> hash = jedisCluster.hgetAll(enter.getRequestId());
            if (hash == null || hash.isEmpty()) {
                throw new SesAdminDevException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(), ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            if (!StringUtils.equals(hash.get("systemId"), enter.getSystemId())) {
                throw new SesAdminDevException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(), ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            if (!StringUtils.equals(hash.get("appId"), enter.getAppId())) {
                throw new SesAdminDevException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(), ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            getUser.setUserId(Long.parseLong(StringUtils.isBlank(hash.get("userId")) ? "0" : hash.get("userId")) == 0 ? null : Long.parseLong(hash.get("userId")));
            getUser.setEmail(StringUtils.isBlank(hash.get("email")) ? null : hash.get("email"));
            getUser.setAppId(StringUtils.isBlank(hash.get("appId")) ? null : hash.get("appId"));
            getUser.setSystemId(StringUtils.isBlank(hash.get("systemId")) ? null : hash.get("systemId"));
        }
        return getUser;
    }


    private UserToken getUserToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new SesAdminDevException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(token);
        if (map == null) {
            throw new SesAdminDevException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        UserToken userToken = new UserToken();
        try {
            BeanUtils.populate(userToken, map);
        } catch (Exception e) {
            log.error("checkToken Exception sessionMap:" + map, e);
        }
        return userToken;
    }


    /**
     * @return
     * @Author Aleks
     * @Description
     * @Date 2020/12/3 14:06
     * @Param
     **/
    @Override
    public GeneralResult logout(GeneralEnter enter) {
        String token = enter.getToken();
        jedisCluster.del(token);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public UserInfoResult userInfo(GeneralEnter enter) {
        UserInfoResult userInfo = new UserInfoResult();
        userInfo.setFirstName("rede");
        userInfo.setLastName("rede");
        userInfo.setFullName("rede rede");
        userInfo.setPicture("https://rede.oss-cn-shanghai.aliyuncs.com/1600658459971.jpg");
        return userInfo;
    }

    @Override
    public UserToken checkAndGetSession(GeneralEnter enter) {
        return userTokenService.checkToken(enter);
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult sendForgetPasswordEmail(BaseSendMailEnter enter) {
        if (Strings.isNullOrEmpty(enter.getMail())) {
            throw new SesAdminDevException(ExceptionCodeEnums.EMAIL_NOT_NULL.getCode(), ExceptionCodeEnums.EMAIL_NOT_NULL.getMessage());
        }
        String decryptMail = null;
        if (StringUtils.isNotEmpty(enter.getMail())) {
            try {
                //????????????
                decryptMail = RsaUtils.decrypt(enter.getMail(), privateKey);
            } catch (Exception e) {
                throw new SesAdminDevException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            enter.setMail(decryptMail);

            AdmSysUser user = admSysUserService.getOne(new LambdaQueryWrapper<AdmSysUser>().eq(AdmSysUser::getDef1, SysUserSourceEnum.SYSTEM.getValue()).eq(AdmSysUser::getLoginName,
                    enter.getMail()).last("limit 1"));
            if (user == null) {
                throw new SesAdminDevException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            BaseMailTaskEnter sendEnter = new BaseMailTaskEnter();
            sendEnter.setName(enter.getMail().substring(0, enter.getMail().indexOf("@")));
            sendEnter.setEvent(MailTemplateEventEnums.OMS_FORGET_PSD_SEND_MAIL.getEvent());
            sendEnter.setMailSystemId(SystemIDEnums.REDE_DEV.getSystemId());
            sendEnter.setMailAppId(AppIDEnums.SES_DEV.getValue());
            sendEnter.setToMail(enter.getMail());
            sendEnter.setRequestId(enter.getRequestId());
            sendEnter.setUserRequestId(enter.getRequestId());
            sendEnter.setToUserId(user.getId());
            mailMultiTaskService.addSetPasswordWebUserTask(sendEnter);
        }
        return new GeneralResult(enter.getRequestId());
    }

}
