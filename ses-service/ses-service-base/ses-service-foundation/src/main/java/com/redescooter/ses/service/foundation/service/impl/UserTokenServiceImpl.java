package com.redescooter.ses.service.foundation.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.account.LoginTypeEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.ValidateCodeEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.tenant.TenantStatusEnum;
import com.redescooter.ses.api.common.enums.user.UserStatusEnum;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.account.ChanagePasswordEnter;
import com.redescooter.ses.api.foundation.vo.account.VerifyAccountEnter;
import com.redescooter.ses.api.foundation.vo.login.AccountsDto;
import com.redescooter.ses.api.foundation.vo.login.LoginConfirmEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginResult;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.service.foundation.dao.UserTokenMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPasswordMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.accountType.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 30/12/2019 11:01 上午
 * @ClassName: UserTokenServiceImpl
 * @Function: TODO
 */

@Slf4j
@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private PlaUserMapper userMapper;

    @Autowired
    private PlaUserPasswordMapper userPasswordMapper;

    @Autowired
    private PlaTenantMapper tenantMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private PlaUserMapper plaUserMapper;

    @Autowired
    private PlaTenantMapper plaTenantMapper;

    @Reference
    private MailMultiTaskService mailMultiTaskService;

    /**
     * 用户登录
     *
     * @param enter
     * @return
     */
    @Override
    public LoginResult login(LoginEnter enter) {

        //用户名密码去除空格
        enter.setLoginName(SesStringUtils.stringTrim(enter.getLoginName()));
        enter.setPassword(SesStringUtils.stringTrim(enter.getPassword()));

        //用户名解密
        /*if (enter.getPassword() != null && enter.getLoginName() != null) {
            String decryptPassword = "";
            String loginName = "";
            try {
                loginName = RsaUtils.decrypt(enter.getLoginName(), privatekey);
                decryptPassword = RsaUtils.decrypt(enter.getPassword(), privatekey);
            } catch (Exception e) {
                throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setPassword(decryptPassword);
            enter.setLoginName(loginName);
        }*/

        if (enter.getAppId().equals(AppIDEnums.SAAS_WEB.getValue())) {
            // ① PC端登录逻辑
            return signIn(checkDefaultUser(enter), enter);
        } else if (enter.getAppId().equals(AppIDEnums.SAAS_APP.getValue())) {
            // ② APP端登录逻辑
            List<AccountsDto> checkAppUser = checkAppUser(enter);
            if (checkAppUser.size() == 1) {
                return signIn(checkDefaultUser(enter), enter);
            } else {
                checkAppUser.forEach(appUser -> {
                    appUser.setStatus(null);
                    appUser.setLastLoginToken(null);
                });
                LoginResult result = new LoginResult();
                result.setAccountSelectionList(checkAppUser);
                result.setRequestId(enter.getRequestId());
                // 放入到redis缓存中
                String userListJson = JSON.toJSONString(checkAppUser);
                jedisCluster.set(enter.getRequestId(), userListJson);
                jedisCluster.expire(enter.getRequestId(), new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());
                return result;
            }
        } else {
            throw new FoundationException(ExceptionCodeEnums.ACCESS_DENIED.getCode(),
                    ExceptionCodeEnums.ACCESS_DENIED.getMessage());
        }
    }

    /**
     * 确认登录
     *
     * @param enter
     * @return
     */
    @Override
    public LoginResult loginConfirm(LoginConfirmEnter enter) {

        LoginResult result = new LoginResult();

        String appUserJson = jedisCluster.get(enter.getConfirmRequestId());

        if (appUserJson == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        List<AccountsDto> userList = JSON.parseArray(appUserJson, AccountsDto.class);
        jj:
        for (AccountsDto account : userList) {
            if (account.getUserId().equals(enter.getConfirmUserId())) {
                LoginEnter userLogin = new LoginEnter();
                BeanUtils.copyProperties(enter, userLogin);
                result = signIn(account, userLogin);
                break jj;
            }
        }

        return result;
    }

    /**
     * 用户注销
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
     * 验证码登录发送邮件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult loginSendCode(LoginEnter enter) {
//        if(Strings.isNullOrEmpty(enter.getCode())){
//            throw new FoundationException(ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getCode(), ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getMessage());
//        }
//        String key = EMAIL_LOGIN_CODE + enter.getLoginName();
//        if(!jedisCluster.exists(key)){
//            throw new FoundationException(ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getCode(), ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getMessage());
//        }
//        Map<String, String> map = jedisCluster.hgetAll(key);
//        String code = map.get("code");
//        if(Strings.isNullOrEmpty(code)){
//            throw new FoundationException(ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getCode(), ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getMessage());
//        }
//        if(!code.equals(enter.getCode())){
//            throw new FoundationException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
//        }
//        // 到这里 邮箱登陆的校验就差不多算是通过了 清除缓存 再下面就是登陆的逻辑
//        jedisCluster.del(key);
//        // 登陆还是按照原来的登陆逻辑
//        OpeSysUser sysUser = getOpeSysUser(enter);
////        LoginEnter loginEnter = new LoginEnter();
////        BeanUtil.copyProperties(enter,loginEnter);
        return null;
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
            throw new FoundationException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        return userToken;
    }

    /**
     * 根据租户id锁定所有账户
     *
     * @param list
     * @return
     */
    @Override
    public GeneralResult lockBySaaSAccount(List<Long> list) {
        userTokenMapper.lockBySaaSAccount(list);
        return new GeneralResult();
    }

    /**
     * 解锁SaaS账户
     *
     * @param list
     * @return
     */
    @Override
    public GeneralResult UnlockBySaaSAccount(List<Long> list) {
        userTokenMapper.unlockBySaaSAccount(list);

        return new GeneralResult();
    }

    /**
     * 验证验证码
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult validateCode(ValidateCodeEnter<AccountsDto> enter) {

        Map<String, String> hash = jedisCluster.hgetAll(enter.getT().getRequestId());
        if (hash == null || hash.isEmpty()) {
            throw new FoundationException(ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_EXPIRED.getCode(),
                    ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_EXPIRED.getMessage());
        }
        if (!StringUtils.equals(hash.get("verificationCode"), enter.getCode())) {
            throw new FoundationException(ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_WRONG.getCode(),
                    ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_WRONG.getMessage());
        }
        if (!StringUtils.equals(hash.get("systemId"), enter.getT().getSystemId())) {
            throw new FoundationException(ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_WRONG.getCode(),
                    ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_WRONG.getMessage());
        }
        if (!StringUtils.equals(hash.get("appId"), enter.getT().getAppId())) {
            throw new FoundationException(ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_WRONG.getCode(),
                    ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_WRONG.getMessage());
        }

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 员工离职 账户禁用、token 清除
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult accountDisabled(GeneralEnter enter) {
        // 判断当前账户是否存在
        PlaUser user = userMapper.selectById(enter.getUserId());
        if (user == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        user.setStatus(UserStatusEnum.LOCK.getCode());
        user.setUpdatedBy(enter.getUserId());
        user.setUpdatedTime(new Date());
        userMapper.insertOrUpdateSelective(user);

        if (StringUtils.isNotEmpty(user.getLastLoginToken())) {
            // 清除token
            jedisCluster.del(user.getLastLoginToken());
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 实际登录逻辑
     *
     * @param user
     * @param enter
     * @return
     */
    @Override
    public LoginResult signIn(AccountsDto user, LoginEnter enter) {

        if (StringUtils.isNotBlank(user.getLastLoginToken())) {
            // 清除原有token 生成新token
            jedisCluster.del(user.getLastLoginToken());
        }
        // Redis设置token
        UserToken userToken = setToken(enter, user);

        // 数据库同步登陆信息
        PlaUser updateUser = new PlaUser();
        updateUser.setId(user.getUserId());
        updateUser.setLastLoginIp(enter.getClientIp());
        updateUser.setLastLoginTime(new Date(enter.getTimestamp()));
        updateUser.setLastLoginToken(userToken.getToken());
        userMapper.updateById(updateUser);
        LoginResult result = LoginResult.builder().token(userToken.getToken()).noPassword(Boolean.FALSE).accountType(user.getUserType()).build();
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 获取APP用户信息
     *
     * @param enter
     * @return
     */
    @Override
    public List<UserToken> getAppUser(GetUserEnter enter) {

        QueryWrapper<PlaUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(enter.getEmail())) {
            queryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getEmail());
        }
        if (StringUtils.isNotBlank(enter.getLoginName())) {
            queryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getLoginName());
        }
        if (null != enter.getAccountType() && 0 != enter.getAccountType()) {
            queryWrapper.eq(PlaUser.COL_USER_TYPE, enter.getAccountType());
        }
        if (StringUtils.isNotBlank(enter.getAppId())) {
            queryWrapper.eq(PlaUser.COL_APP_ID, enter.getAppId());
        }
        if (StringUtils.isNotBlank(enter.getSystemId())) {
            queryWrapper.eq(PlaUser.COL_SYSTEM_ID, enter.getSystemId());
        }

        List<PlaUser> plaUser = plaUserMapper.selectList(queryWrapper);

        List<UserToken> plaUseList = new ArrayList<>();

        if (CollectionUtils.isEmpty(plaUser)) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        plaUser.forEach(item -> {
            UserToken user = new UserToken();
            user.setToken(item.getLastLoginToken());
            user.setAppId(item.getAppId());
            user.setClientIp(item.getLastLoginIp());
            user.setSystemId(item.getSystemId());
            user.setTenantId(item.getTenantId());
            user.setUserId(item.getId());
            plaUseList.add(user);
        });
        return plaUseList;
    }

    /**
     * PC端不支持账号通用，一个邮箱只能开设一种类型的用户
     *
     * @param enter
     * @return
     */
    @Override
    public AccountsDto checkDefaultUser(LoginEnter enter) {

        AccountsDto accountsDto = userTokenMapper.checkPcUser(enter);
        /**
         * 账户非空
         */
        if (accountsDto == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        /**
         * 账户密码验证
         */
        if (enter.getLoginType() == LoginTypeEnum.PASSWORD.getCode()) {
            QueryWrapper<PlaUserPassword> wrapper = new QueryWrapper<>();
            wrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getLoginName());
            wrapper.eq(PlaUserPassword.COL_DR, 0);
            PlaUserPassword userPassword = userPasswordMapper.selectOne(wrapper);
            if (enter.getPassword() == null) {
                throw new FoundationException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(),
                        ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
            }
            if (userPassword == null) {
                throw new FoundationException(ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getCode(),
                        ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getMessage());
            }
            if (StringUtils.isBlank(userPassword.getPassword())) {
                throw new FoundationException(ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getCode(),
                        ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getMessage());
            }
            String password = DigestUtils.md5Hex(enter.getPassword() + userPassword.getSalt());
            if (!userPassword.getPassword().equals(password)) {
                throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
        } else {
            // 验证码登录逻辑 todo 待测试
            ValidateCodeEnter<AccountsDto> validateCodeEnter = new ValidateCodeEnter<>();
            BeanUtils.copyProperties(enter, validateCodeEnter);
            validateCodeEnter.setCode(enter.getCode());
            validateCodeEnter.setEvent(ValidateCodeEnums.LOGIN.getCode());
            accountsDto.setRequestId(enter.getRequestId());
            validateCodeEnter.setT(accountsDto);
            validateCode(validateCodeEnter);
        }
        /**
         * 账户状态判断
         */
        if (accountsDto.getStatus().equals(UserStatusEnum.EXPIRED.getValue())) {
            // 判断账号是否过期
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_EXPIRED.getCode(),
                    ExceptionCodeEnums.ACCOUNT_EXPIRED.getMessage());
        } else if (accountsDto.getStatus().equals(UserStatusEnum.LOCK.getValue())) {
            // 判断账号是否锁定
            throw new FoundationException(ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getCode(),
                    ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getMessage());
        } else if (accountsDto.getStatus().equals(UserStatusEnum.CANCEL.getValue())) {
            // 判断账号是否取消
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_CANCELLED.getCode(),
                    ExceptionCodeEnums.ACCOUNT_CANCELLED.getMessage());
        } else if (accountsDto.getStatus().equals(UserStatusEnum.NORMAL.getValue())) {
            /**
             * 合法用户的 1.租户非空判断 2.状态合法判断
             */
            if (accountsDto.getTenantId() != 0) {
                PlaTenant tenant = tenantMapper.selectById(accountsDto.getTenantId());
                if (tenant == null) {
                    // ①、判断账号是否取消
                    throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(),
                            ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
                } else if (tenant.getStatus().equals(TenantStatusEnum.EXPIRED.getValue())) {
                    // ②、 判断租户是否过期
                    throw new FoundationException(ExceptionCodeEnums.ACCOUNT_EXPIRED.getCode(),
                            ExceptionCodeEnums.ACCOUNT_EXPIRED.getMessage());
                } else if (tenant.getStatus().equals(TenantStatusEnum.FROZEN.getValue())) {
                    // ③、 判断租户是否冻结
                    throw new FoundationException(ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getCode(),
                            ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getMessage());
                }
            }

            /**
             * 权限校验
             */
            chectPermission(accountsDto);
            return accountsDto;
        } else {
            // 判断账号是否取消
            throw new FoundationException(ExceptionCodeEnums.ACCESS_DENIED.getCode(),
                    ExceptionCodeEnums.ACCESS_DENIED.getMessage());
        }
    }

    /**
     * APP用户验证，2B/2C账号通用
     *
     * @param enter
     * @return
     */
    @Override
    public List<AccountsDto> checkAppUser(LoginEnter enter) {
        List<AccountsDto> resultMultiple = userTokenMapper.checkAPPUser(enter);
        List<AccountsDto> resultOne = new ArrayList<>();

        if (resultMultiple.size() == 1) {
            AccountsDto accountsDto = checkDefaultUser(enter);
            resultOne.add(accountsDto);
            return resultOne;
        } else if (resultMultiple.size() == 0) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        return resultMultiple;
    }

    /**
     * 权限校验
     *
     * @param dto
     */
    @Override
    public void chectPermission(AccountsDto dto) {

        List<PlaUserPermission> permissionlist = userTokenMapper.chectPermission(dto);

        if (permissionlist.isEmpty()) {
            // 没权限
            throw new FoundationException(ExceptionCodeEnums.AUTHORIZATION_FAILED.getCode(),
                    ExceptionCodeEnums.AUTHORIZATION_FAILED.getMessage());
        }
        boolean hasPerimission = false;
        for (PlaUserPermission p : permissionlist) {
            if (StringUtils.equals(dto.getSystemId(), p.getSystemId())
                    && StringUtils.equals(dto.getAppId(), p.getAppId())) {
                hasPerimission = true;
                break;
            }
        }
        if (!hasPerimission) {
            // 没权限
            throw new FoundationException(ExceptionCodeEnums.AUTHORIZATION_FAILED.getCode(),
                    ExceptionCodeEnums.AUTHORIZATION_FAILED.getMessage());
        }

    }

    /**
     * 设置token
     *
     * @param enter
     * @param userDto
     * @return
     */
    @Override
    public UserToken setToken(LoginEnter enter, AccountsDto userDto) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(userDto.getUserId());
        userToken.setTenantId(userDto.getTenantId());
        userToken.setSystemId(enter.getSystemId());
        userToken.setAppId(enter.getAppId());
        userToken.setClientIp(enter.getClientIp());
        userToken.setClientType(enter.getClientType());
        userToken.setCountry(enter.getCountry());
        userToken.setLanguage(enter.getLanguage());
        userToken.setTimestamp(enter.getTimestamp());
        userToken.setTimeZone(enter.getTimeZone());
        userToken.setVersion(enter.getVersion());
        userToken.setRequestId(enter.getRequestId());
        try {
            Map<String, String> map = org.apache.commons.beanutils.BeanUtils.describe(userToken);
            map.remove("requestId");
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
     * 设置密码
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult setPassword(SetPasswordEnter enter) {

        //密码去空格
        if (StringUtils.isNotEmpty(enter.getConfirmPassword())) {
            enter.setConfirmPassword(SesStringUtils.stringTrim(enter.getConfirmPassword()));
        }
        if (StringUtils.isNotEmpty(enter.getNewPassword())) {
            enter.setNewPassword(SesStringUtils.stringTrim(enter.getNewPassword()));
        }

        //用户名解密
        /*if (StringUtils.isNotEmpty(enter.getNewPassword()) && StringUtils.isNotEmpty(enter.getConfirmPassword())) {
            String newPassword = "";
            String confirmPassword = "";
            try {
                newPassword = RsaUtils.decrypt(enter.getNewPassword(), privatekey);
                confirmPassword = RsaUtils.decrypt(enter.getConfirmPassword(), privatekey);
            } catch (Exception e) {
                throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setNewPassword(newPassword);
            enter.setConfirmPassword(confirmPassword);
        }*/


        /**
         * 系统内部进行设置密码
         */
        if (!StringUtils.equals(enter.getNewPassword(), enter.getConfirmPassword())) {
            throw new FoundationException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(),
                    ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        GetUserEnter getUser = new GetUserEnter();
        getUser.setRequestId(enter.getRequestId());
        if (StringUtils.isNotBlank(enter.getToken())) {
            UserToken userToken = getUserToken(enter.getToken());
            if (userToken.getUserId() == null || userToken.getUserId() == 0) {
                throw new FoundationException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            getUser.setUserId(userToken.getUserId());
            getUser.setAppId(enter.getAppId());
            getUser.setSystemId(enter.getSystemId());
        } else {
            /**
             * 系统外部进行设置密码
             */
            Map<String, String> hash = jedisCluster.hgetAll(enter.getRequestId());
            if (hash == null || hash.isEmpty()) {
                throw new FoundationException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            if (!StringUtils.equals(hash.get("systemId"), enter.getSystemId())) {
                throw new FoundationException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            if (!StringUtils.equals(hash.get("appId"), enter.getAppId())) {
                throw new FoundationException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            getUser.setUserId(Long.parseLong(StringUtils.isBlank(hash.get("userId")) ? "0" : hash.get("userId")) == 0 ? null : Long.parseLong(hash.get("userId")));
            getUser.setEmail(StringUtils.isBlank(hash.get("email")) ? null : hash.get("email"));
            getUser.setAppId(StringUtils.isBlank(hash.get("appId")) ? null : hash.get("appId"));
            getUser.setSystemId(StringUtils.isBlank(hash.get("systemId")) ? null : hash.get("systemId"));
        }

        PlaUser emailUser = userTokenMapper.getUserLimitOne(getUser);
        if (emailUser == null) {
            throw new FoundationException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                    ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
        }

        QueryWrapper<PlaUserPassword> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUserPassword.COL_LOGIN_NAME, emailUser.getLoginName());
        wrapper.eq(PlaUserPassword.COL_DR, 0);
        PlaUserPassword updatePassword = userPasswordMapper.selectOne(wrapper);

        updatePassword.setPassword(DigestUtils.md5Hex(enter.getConfirmPassword() + updatePassword.getSalt()));
        updatePassword.setUpdatedBy(emailUser.getId());
        updatePassword.setUpdatedTime(new Date());
        userPasswordMapper.updateById(updatePassword);

        //将用户状态修改为正常
        PlaUser user = new PlaUser();
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        user.setActivationTime(new Date());
        user.setUpdatedBy(emailUser.getId());
        user.setUpdatedTime(new Date());

        UpdateWrapper<PlaUser> userUpdate = new UpdateWrapper<>();
        userUpdate.eq(PlaUser.COL_DR, 0);
        userUpdate.eq(PlaUser.COL_LOGIN_NAME, emailUser.getLoginName());
        userUpdate.eq(PlaUser.COL_STATUS, UserStatusEnum.INACTIVATED.getValue());
        userMapper.update(user, userUpdate);

        if (StringUtils.isNotBlank(emailUser.getLastLoginToken())) {
            // 清除原有token,重新登录
            jedisCluster.del(emailUser.getLastLoginToken());
            jedisCluster.del(enter.getRequestId());

        }
        // 更新租户账户的激活时间
        QueryWrapper<PlaTenant> plaTenantQueryWrapper = new QueryWrapper<>();
        plaTenantQueryWrapper.eq(PlaTenant.COL_DR, 0);
        plaTenantQueryWrapper.eq(PlaTenant.COL_EMAIL, emailUser.getLoginName());
        PlaTenant plaTenant = plaTenantMapper.selectOne(plaTenantQueryWrapper);
        if (plaTenant != null) {
            plaTenant.setActivationTime(new Date());
            plaTenant.setUpdatedTime(new Date());
            plaTenantMapper.updateById(plaTenant);
        }

        //token 为空为系统外设置密码 设置成功过后 清楚 缓存保证一个requestId 只能用一次
        if (StringUtils.isBlank(enter.getToken())) {
            if (jedisCluster.exists(enter.getRequestId())) {
                jedisCluster.del(enter.getRequestId());
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 系统内部设置密码
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult chanagePassword(ChanagePasswordEnter enter) {

        //邮箱、密码去空格
        if (StringUtils.isNotEmpty(enter.getEmail())) {
            enter.setEmail(SesStringUtils.stringTrim(enter.getEmail()));
        }
        if (StringUtils.isNotEmpty(enter.getNewPassword())) {
            enter.setNewPassword(SesStringUtils.stringTrim(enter.getNewPassword()));
        }
        if (StringUtils.isNotEmpty(enter.getOldPassword())) {
            enter.setOldPassword(SesStringUtils.stringTrim(enter.getOldPassword()));
        }
        if (StringUtils.isNotEmpty(enter.getConfirmNewPassword())) {
            enter.setConfirmNewPassword(SesStringUtils.stringTrim(enter.getConfirmNewPassword()));
        }
        //密码解密
        //用户名解密
        /*if (StringUtils.isNotEmpty(enter.getNewPassword()) && StringUtils.isNotEmpty(enter.getOldPassword()) && StringUtils.isNotEmpty(enter.getConfirmNewPassword())) {
            String newPassword = "";
            String oldPassword = "";
            String confirmPassword = "";
            try {
                newPassword = RsaUtils.decrypt(enter.getNewPassword(), privatekey);
                oldPassword = RsaUtils.decrypt(enter.getOldPassword(), privatekey);
                confirmPassword = RsaUtils.decrypt(enter.getConfirmNewPassword(), privatekey);
            } catch (Exception e) {
                throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setNewPassword(newPassword);
            enter.setOldPassword(oldPassword);
        }
        if (StringUtils.isNotEmpty(enter.getEmail())){
            String email = "";
            try {
                email = RsaUtils.decrypt(enter.getEmail(), privatekey);
            } catch (Exception e) {
                throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            enter.setEmail(email);
        }*/

        //新密码判断是否一致
        if (!StringUtils.equals(enter.getNewPassword(), enter.getConfirmNewPassword())) {
            throw new FoundationException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        GetUserEnter getUser = new GetUserEnter();
        getUser.setAppId(enter.getAppId());
        getUser.setSystemId(enter.getSystemId());
        getUser.setEmail(enter.getEmail());
        PlaUser user = userTokenMapper.getUserLimitOne(getUser);
        if (user == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        QueryWrapper<PlaUserPassword> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUserPassword.COL_LOGIN_NAME, user.getLoginName());
        wrapper.eq(PlaUserPassword.COL_DR, 0);
        PlaUserPassword updatePassword = userPasswordMapper.selectOne(wrapper);

        //旧密码验证
        String oldPsaaword = DigestUtils.md5Hex(enter.getOldPassword() + updatePassword.getSalt());
        if (!StringUtils.equals(oldPsaaword, updatePassword.getPassword())) {
            throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        updatePassword.setPassword(DigestUtils.md5Hex(enter.getNewPassword() + updatePassword.getSalt()));
        updatePassword.setUpdatedBy(enter.getUserId());
        updatePassword.setUpdatedTime(new Date());
        userPasswordMapper.updateById(updatePassword);

        if (StringUtils.isNotBlank(user.getLastLoginToken())) {
            // 清除原有token,重新登录
            jedisCluster.del(user.getLastLoginToken());
        }

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 邮件发送
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult sendEmail(BaseSendMailEnter enter) {

        //邮箱去空格
        if (StringUtils.isNotEmpty(enter.getMail())) {
            enter.setMail(SesStringUtils.stringTrim(enter.getMail()));
        }

        //用户名解密
        /*if (enter.getMail() != null) {
            String email = "";
            try {
                email = RsaUtils.decrypt(enter.getMail(), privatekey);
            } catch (Exception e) {
                throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            enter.setMail(email);
        }*/


        GetUserEnter getUser = new GetUserEnter();
        BeanUtils.copyProperties(enter, getUser);
        getUser.setEmail(enter.getMail());
        PlaUser limitOne = userTokenMapper.getUserLimitOne(getUser);

        if (limitOne == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        QueryWrapper<PlaUserPassword> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getMail());
        wrapper.eq(PlaUserPassword.COL_DR, 0);
        wrapper.isNull(PlaUserPassword.COL_PASSWORD);
        Integer count = userPasswordMapper.selectCount(wrapper);

        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();

        if (count == 0) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEB_PASSWORD.getEvent());
        } else {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEB_ACTIVATE.getEvent());
        }

        if (enter.getMail().indexOf("@") == (-1)) {
            baseMailTaskEnter.setName(enter.getMail());
        } else {
            baseMailTaskEnter.setName(enter.getMail().split("@", 2)[0]);
        }
        baseMailTaskEnter.setToMail(enter.getMail());
        baseMailTaskEnter.setToUserId(limitOne.getId());
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setRequestId(enter.getRequestId());
        baseMailTaskEnter.setMailAppId(enter.getAppId());
        baseMailTaskEnter.setMailSystemId(enter.getSystemId());
        mailMultiTaskService.addSetPasswordWebUserTask(baseMailTaskEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * App 手机号类型验证 用户密码
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult verifyAccount(VerifyAccountEnter enter) {
        if (StringUtils.isBlank(enter.getPassword())) {
            throw new FoundationException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        //密码去空格
        enter.setPassword(SesStringUtils.stringTrim(enter.getPassword()));


        PlaUser plaUser = plaUserMapper.selectById(enter.getUserId());
        if (plaUser == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        QueryWrapper<PlaUserPassword> plaUserPasswordQueryWrapper = new QueryWrapper<>();
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_LOGIN_NAME, plaUser.getLoginName());
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_DR, 0);
        PlaUserPassword plaUserPassword = userPasswordMapper.selectOne(plaUserPasswordQueryWrapper);
        if (plaUserPassword == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(plaUserPassword.getPassword(), DigestUtils.md5Hex(enter.getPassword() + plaUserPassword.getSalt()))) {
            throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        return new GeneralResult(enter.getRequestId());
    }

    private UserToken getUserToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new FoundationException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(token);
        if (map == null) {
            throw new FoundationException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(),
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
}
