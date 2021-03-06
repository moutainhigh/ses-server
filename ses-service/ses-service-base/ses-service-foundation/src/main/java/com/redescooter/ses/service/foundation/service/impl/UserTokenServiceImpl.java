package com.redescooter.ses.service.foundation.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.account.LoginTypeEnum;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.ClientTypeEnums;
import com.redescooter.ses.api.common.enums.base.ValidateCodeEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.tenant.TenantStatusEnum;
import com.redescooter.ses.api.common.enums.user.UserStatusEnum;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.RefreshTokenEnter;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.common.vo.base.ValidateCodeEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.account.ChanagePasswordEnter;
import com.redescooter.ses.api.foundation.vo.account.VerifyAccountEnter;
import com.redescooter.ses.api.foundation.vo.login.AccountsDto;
import com.redescooter.ses.api.foundation.vo.login.LoginConfirmEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginResult;
import com.redescooter.ses.api.foundation.vo.message.PinEnter;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.vo.QueryUserProfileByEmailEnter;
import com.redescooter.ses.api.hub.vo.QueryUserProfileByEmailResult;
import com.redescooter.ses.service.foundation.config.LoginExtremeExperienceConfig;
import com.redescooter.ses.service.foundation.dao.UserTokenMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPasswordMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaUserPasswordService;
import com.redescooter.ses.service.foundation.service.base.PlaUserService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 30/12/2019 11:01 ??????
 * @ClassName: UserTokenServiceImpl
 * @Function: TODO
 */
@Slf4j
@DubboService
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private PlaUserMapper userMapper;

    @Autowired
    private PlaUserPasswordMapper userPasswordMapper;

    @Autowired
    private PlaUserPasswordService plaUserPasswordService;

    @Autowired
    private PlaTenantMapper tenantMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private PlaUserMapper plaUserMapper;

    @Autowired
    private PlaUserService plaUserService;

    @Autowired
    private PlaTenantMapper plaTenantMapper;

    @Autowired
    private LoginExtremeExperienceConfig loginExtremeExperienceConfig;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private UserProfileService userProfileService;

    @Value("${Request.privateKey}")
    private String privateKey;

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public LoginResult login(LoginEnter enter) {

        //???????????????????????????
        enter.setLoginName(SesStringUtils.stringTrim(enter.getLoginName()));
        enter.setPassword(SesStringUtils.stringTrim(enter.getPassword()));

        //???????????????
        if (null != enter.getPassword() && null != enter.getLoginName()) {
            String decryptPassword = "";
            String loginName = "";
            try {
                loginName = RsaUtils.decrypt(enter.getLoginName(), privateKey);
                decryptPassword = RsaUtils.decrypt(enter.getPassword(), privateKey);
            } catch (Exception e) {
                throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setPassword(decryptPassword);
            enter.setLoginName(loginName);
        }

        if (enter.getAppId().equals(AppIDEnums.SAAS_WEB.getValue())) {
            // ??? PC???????????????
            return signIn(checkDefaultUser(enter), enter);
        } else if (enter.getAppId().equals(AppIDEnums.SAAS_APP.getValue())) {
            // ??? APP???????????????
            List<AccountsDto> checkAppUser = checkAppUser(enter);
            log.info(checkAppUser.size() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            if (1 == checkAppUser.size()) {
                return signIn(checkDefaultUser(enter), enter);
            } else {
                checkAppUser.forEach(appUser -> {
                    appUser.setStatus(null);
                    appUser.setLastLoginToken(null);
                });
                LoginResult result = new LoginResult();
                result.setAccountSelectionList(checkAppUser);
                result.setRequestId(enter.getRequestId());
                // ?????????redis?????????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public LoginResult loginConfirm(LoginConfirmEnter enter) {

        LoginResult result = new LoginResult();

        String appUserJson = jedisCluster.get(enter.getConfirmRequestId());

        if (null == appUserJson) {
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
     * ????????????
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
     * ???????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult loginSendCode(LoginEnter enter) {

        // ?????????????????????????????????????????????????????? ???????????????SAAS PC??????
        List<PlaUser> plaUserList = getPlaUsers(enter);
        // ????????????????????????  ?????????????????????  ???????????????
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        BaseMailTaskEnter sendCodeMobileUserTaskEnter = new BaseMailTaskEnter();
        sendCodeMobileUserTaskEnter.setCode(code);
        sendCodeMobileUserTaskEnter.setEvent(MailTemplateEventEnums.SAAS_LOGIN_BY_CODE.getEvent());
        sendCodeMobileUserTaskEnter.setAppId(enter.getAppId());
        sendCodeMobileUserTaskEnter.setSystemId(enter.getSystemId());
        sendCodeMobileUserTaskEnter.setToMail(enter.getLoginName());
        sendCodeMobileUserTaskEnter.setUserRequestId(enter.getRequestId());
        sendCodeMobileUserTaskEnter.setMailAppId(enter.getAppId());
        sendCodeMobileUserTaskEnter.setMailSystemId(enter.getSystemId());
        sendCodeMobileUserTaskEnter.setName(enter.getLoginName().split("@")[0]);
        sendCodeMobileUserTaskEnter.setUserId(plaUserList.get(0).getId());
        sendCodeMobileUserTaskEnter.setRequestId(enter.getRequestId());
        sendCodeMobileUserTaskEnter.setEmail(enter.getLoginName());
        mailMultiTaskService.addMultiMailTask(sendCodeMobileUserTaskEnter);
        return new GeneralResult(enter.getRequestId());
    }


    private List<PlaUser> getPlaUsers(LoginEnter enter) {
        QueryWrapper<PlaUser> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
        wrapper.eq(PlaUser.COL_APP_ID, enter.getAppId());
        wrapper.eq(PlaUser.COL_SYSTEM_ID, enter.getSystemId());
        wrapper.in(PlaUser.COL_USER_TYPE, AccountTypeEnums.WEB_EXPRESS.getAccountType(), AccountTypeEnums.WEB_RESTAURANT.getAccountType());
        List<PlaUser> plaUserList = plaUserMapper.selectList(wrapper);
        //??????????????????????????????????????????????????????????????????????????????
        if (CollectionUtils.isEmpty(plaUserList)) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        return plaUserList;
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public LoginResult loginByCode(LoginEnter enter) {
        if (Strings.isNullOrEmpty(enter.getCode())) {
            throw new FoundationException(ExceptionCodeEnums.CODE_IS_EMPTY.getCode(), ExceptionCodeEnums.CODE_IS_EMPTY.getMessage());
        }
        // ???????????????????????????????????????
        enter.setLoginType(LoginTypeEnum.DYNAMIC_CODE.getCode());
        return signIn(checkDefaultUser(enter), enter);
    }

    /**
     * ??????token??????
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
     * ????????????id??????????????????
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
     * ??????SaaS??????
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
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult validateCode(ValidateCodeEnter<AccountsDto> enter) {

        Map<String, String> hash = jedisCluster.hgetAll(enter.getT().getRequestId());
        if (null == hash || hash.isEmpty()) {
            throw new FoundationException(ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_EXPIRED.getCode(),
                    ExceptionCodeEnums.USERTOKEN_SERVICE_CODE_EXPIRED.getMessage());
        }
        if (!StringUtils.equals(hash.get("code"), enter.getCode())) {
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
     * ???????????? ???????????????token ??????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult accountDisabled(GeneralEnter enter) {
        // ??????????????????????????????
        PlaUser user = userMapper.selectById(enter.getUserId());
        if (null == user) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        user.setStatus(UserStatusEnum.LOCK.getCode());
        user.setUpdatedBy(enter.getUserId());
        user.setUpdatedTime(new Date());
        userMapper.insertOrUpdateSelective(user);

        if (StringUtils.isNotEmpty(user.getLastLoginToken())) {
            // ??????token
            jedisCluster.del(user.getLastLoginToken());
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
     *
     * @param user
     * @param enter
     * @return
     */
    @Override
    public LoginResult signIn(AccountsDto user, LoginEnter enter) {

        if (StringUtils.isNotBlank(user.getLastLoginToken())) {
            // ????????????token ?????????token
            jedisCluster.del(user.getLastLoginToken());
        }
        // Redis??????token
        UserToken userToken = setToken(enter, user);

        // ???????????????????????????
        PlaUser updateUser = new PlaUser();
        updateUser.setId(user.getUserId());
        updateUser.setLastLoginIp(enter.getClientIp());
        updateUser.setLastLoginTime(new Date(enter.getTimestamp()));
        updateUser.setLastLoginToken(userToken.getToken());
        updateUser.setRefreshToken(userToken.getRefreshToken());
        userMapper.updateById(updateUser);
        LoginResult result = LoginResult.builder().token(userToken.getToken()).noPassword(Boolean.FALSE).accountType(user.getUserType()).build();
        result.setRefreshToken(userToken.getRefreshToken());
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * ??????APP????????????
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
     * PC????????????????????????????????????????????????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public AccountsDto checkDefaultUser(LoginEnter enter) {

        AccountsDto accountsDto = userTokenMapper.checkPcUser(enter);
        /**
         * ????????????
         */
        if (null == accountsDto) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        /**
         * ??????????????????
         */
        if (enter.getLoginType() == LoginTypeEnum.PASSWORD.getCode()) {
            QueryWrapper<PlaUserPassword> wrapper = new QueryWrapper<>();
            wrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getLoginName());
            wrapper.eq(PlaUserPassword.COL_DR, Constant.DR_FALSE);
            PlaUserPassword userPassword = userPasswordMapper.selectOne(wrapper);
            if (null == enter.getPassword()) {
                throw new FoundationException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(),
                        ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
            }
            if (null == userPassword) {
                throw new FoundationException(ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getCode(), ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getMessage());
            }
            if (StringUtils.isBlank(userPassword.getPassword())) {
                throw new FoundationException(ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getCode(),
                        ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getMessage());
            }

            if (StringUtils.equals(ClientTypeEnums.PC.getValue(), enter.getClientType())) {
                //????????????
                extremeExperience(enter, accountsDto, userPassword);
            } else {
                String password = DigestUtils.md5Hex(enter.getPassword() + userPassword.getSalt());
                if (!userPassword.getPassword().equals(password)) {
                    throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
                }
            }

        } else {
            // ????????????????????? todo ?????????
            ValidateCodeEnter<AccountsDto> validateCodeEnter = new ValidateCodeEnter<>();
            BeanUtils.copyProperties(enter, validateCodeEnter);
            validateCodeEnter.setCode(enter.getCode());
            validateCodeEnter.setEvent(ValidateCodeEnums.LOGIN.getCode());
            accountsDto.setRequestId(enter.getRequestId());
            validateCodeEnter.setT(accountsDto);
            validateCode(validateCodeEnter);
        }
        /**
         * ??????????????????
         */
        if (accountsDto.getStatus().equals(UserStatusEnum.EXPIRED.getValue())) {
            // ????????????????????????
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_EXPIRED.getCode(),
                    ExceptionCodeEnums.ACCOUNT_EXPIRED.getMessage());
        } else if (accountsDto.getStatus().equals(UserStatusEnum.LOCK.getValue())) {
            // ????????????????????????
            throw new FoundationException(ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getCode(),
                    ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getMessage());
        } else if (accountsDto.getStatus().equals(UserStatusEnum.CANCEL.getValue())) {
            // ????????????????????????
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_CANCELLED.getCode(),
                    ExceptionCodeEnums.ACCOUNT_CANCELLED.getMessage());
        } else if (accountsDto.getStatus().equals(UserStatusEnum.NORMAL.getValue())) {
            /**
             * ??????????????? 1.?????????????????? 2.??????????????????
             */
            if (0 != accountsDto.getTenantId()) {
                PlaTenant tenant = tenantMapper.selectById(accountsDto.getTenantId());
                if (null == tenant) {
                    // ??????????????????????????????
                    throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(),
                            ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
                } else if (tenant.getStatus().equals(TenantStatusEnum.EXPIRED.getValue())) {
                    // ?????? ????????????????????????
                    throw new FoundationException(ExceptionCodeEnums.ACCOUNT_EXPIRED.getCode(),
                            ExceptionCodeEnums.ACCOUNT_EXPIRED.getMessage());
                } else if (tenant.getStatus().equals(TenantStatusEnum.FROZEN.getValue())) {
                    // ?????? ????????????????????????
                    throw new FoundationException(ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getCode(),
                            ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getMessage());
                }
            }

            /**
             * ????????????
             */
            chectPermission(accountsDto);
            return accountsDto;
        } else {
            // ????????????????????????
            throw new FoundationException(ExceptionCodeEnums.ACCESS_DENIED.getCode(), ExceptionCodeEnums.ACCESS_DENIED.getMessage());
        }
    }

    private void extremeExperience(LoginEnter enter, AccountsDto accountsDto, PlaUserPassword userPassword) {
        String password = DigestUtils.md5Hex(enter.getPassword() + userPassword.getSalt());
        String psdErrorKey = JedisConstant.LOGIN_PSD_ERROR_NUM + accountsDto.getUserId();
        if (jedisCluster.exists(psdErrorKey)) {
            Map<String, String> data = jedisCluster.hgetAll(psdErrorKey);
            String oldNum = data.get("num");
            if (Integer.parseInt(oldNum) >= 5) {
                throw new FoundationException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getMessage());
            }
        }
        if (!password.equals(userPassword.getPassword())) {
            //????????????3????????????????????????????????????????????????5???????????????1??????
            Integer errorNum = passWordMistaken(psdErrorKey);
            if (errorNum < 3) {
                throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            } else if (errorNum == 3 || errorNum == 4) {
                throw new FoundationException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getMessage());
            } else {
                throw new FoundationException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getMessage());
            }
        }

        //???????????????????????????????????????????????????????????????????????????????????????
        if (jedisCluster.exists(psdErrorKey)) {
            jedisCluster.del(psdErrorKey);
        }
    }

    /**
     * APP???????????????2B/2C????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<AccountsDto> checkAppUser(LoginEnter enter) {
        /**
         * ??????????????????
         */
        if (enter.getLoginType() == LoginTypeEnum.PASSWORD.getCode()) {
            if (StringUtils.isEmpty(enter.getPassword())) {
                throw new FoundationException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(),
                        ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
            }
            QueryWrapper<PlaUserPassword> wrapper = new QueryWrapper<>();
            wrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getLoginName());
            wrapper.eq(PlaUserPassword.COL_DR, Constant.DR_FALSE);
            wrapper.orderByDesc(PlaAppVersion.COL_CREATED_TIME);
            wrapper.last("limit 1");
            PlaUserPassword userPassword = userPasswordMapper.selectOne(wrapper);
            if (null == userPassword) {
                throw new FoundationException(ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getCode(), ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getMessage());
            }
            if (StringUtils.isBlank(userPassword.getPassword())) {
                throw new FoundationException(ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getCode(),
                        ExceptionCodeEnums.ACCOUNT_NOT_ACTIVATED.getMessage());
            }
            String password = DigestUtils.md5Hex(enter.getPassword() + userPassword.getSalt());
            if (!userPassword.getPassword().equals(password)) {
                throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }

        }
        List<AccountsDto> resultMultiple = userTokenMapper.checkAPPUser(enter);
        List<AccountsDto> resultOne = new ArrayList<>();

        if (1 == resultMultiple.size()) {
            AccountsDto accountsDto = checkDefaultUser(enter);
            resultOne.add(accountsDto);
        }
        if (1 < resultMultiple.size()) {
            resultOne.addAll(resultMultiple);
        }
        if (CollectionUtils.isEmpty(resultMultiple)) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //??????????????????
        List<PlaTenant> tenantList = plaTenantMapper.selectList(new QueryWrapper<PlaTenant>().in(PlaTenant.COL_ID, resultOne.stream().map(AccountsDto::getTenantId).collect(Collectors.toList())));
        //????????????????????????
        List<String> emailList = tenantList.stream().map(PlaTenant::getEmail).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(emailList)) {
            return resultOne;
        }

        QueryUserProfileByEmailEnter queryUserProfileByEmailEnter = new QueryUserProfileByEmailEnter();
        queryUserProfileByEmailEnter.setEmail(emailList);
        //????????????????????????
        List<QueryUserProfileByEmailResult> userPictureList = userProfileService.getUserPicture(queryUserProfileByEmailEnter);
        resultOne.forEach(item -> {
            userPictureList.forEach(user -> {
                if (item.getTenantId().equals(user.getTenantId())) {
                    item.setPicture(user.getPicture());
                }
            });
        });
        return resultOne;
    }

    /**
     * ????????????
     *
     * @param dto
     */
    @Override
    public void chectPermission(AccountsDto dto) {

        List<PlaUserPermission> permissionlist = userTokenMapper.chectPermission(dto);

        if (permissionlist.isEmpty()) {
            // ?????????
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
            // ?????????
            throw new FoundationException(ExceptionCodeEnums.AUTHORIZATION_FAILED.getCode(),
                    ExceptionCodeEnums.AUTHORIZATION_FAILED.getMessage());
        }

    }

    /**
     * ??????token
     */
    @Override
    public TokenResult refreshToken(RefreshTokenEnter enter) {
        if (null == enter || StringUtils.isBlank(enter.getRefreshToken())) {
            throw new FoundationException(ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getMessage());
        }
        String refreshToken = enter.getRefreshToken();
        Boolean flag = jedisCluster.exists(refreshToken);
        // ??????token???redis?????????,??????????????????,???????????????????????????
        if (!flag) {
            throw new FoundationException(ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getMessage());
        }

        // ????????????access_token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // ??????refresh_token???????????????
        Map<String, String> map = jedisCluster.hgetAll(refreshToken);
        map.put("token", token);

        // redis????????????access_token
        jedisCluster.hmset(token, map);
        jedisCluster.expire(token, new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());
        //jedisCluster.expire(token, new Long(RedisExpireEnum.MINUTES_1.getSeconds()).intValue());  // ????????????
        // ??????db
        PlaUser model = new PlaUser();
        Long userId = Long.valueOf(map.get("userId"));
        model.setId(userId);
        model.setLastLoginToken(token);
        model.setLastLoginTime(new Date());
        plaUserService.updateById(model);

        TokenResult result = new TokenResult();
        result.setToken(token);
        result.setRefreshToken(refreshToken);
        return result;
    }

    /**
     * ??????PIN
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult setPin(PinEnter enter) {
        Boolean flag = jedisCluster.exists(enter.getToken());
        if (!flag) {
            throw new FoundationException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(enter.getToken());
        if (map == null) {
            throw new FoundationException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        UserToken userToken = new UserToken();
        try {
            org.apache.commons.beanutils.BeanUtils.populate(userToken, map);
        } catch (Exception e) {
            log.error("checkToken Exception sessionMap:" + map, e);
        }

        PlaUser plaUser = plaUserService.getById(userToken.getUserId());
        if (null == plaUser) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        LambdaQueryWrapper<PlaUserPassword> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PlaUserPassword::getDr, Constant.DR_FALSE);
        wrapper.eq(PlaUserPassword::getLoginName, plaUser.getLoginName());
        wrapper.last("limit 1");
        PlaUserPassword password = plaUserPasswordService.getOne(wrapper);
        if (null == password) {
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        if (6 != enter.getFirstPin().length()) {
            throw new FoundationException(ExceptionCodeEnums.LENGTH_ERROR.getCode(), ExceptionCodeEnums.LENGTH_ERROR.getMessage());
        }
        if (!enter.getFirstPin().equals(enter.getConfirmPin())) {
            throw new FoundationException(ExceptionCodeEnums.PIN_TWO_INCONSISTENCIES.getCode(), ExceptionCodeEnums.PIN_TWO_INCONSISTENCIES.getMessage());
        }
        password.setDef1(enter.getFirstPin());
        plaUserPasswordService.updateById(password);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????PIN?????????????????????
     */
    @Override
    public BooleanResult checkPwd(StringEnter enter) {
        Boolean flag = jedisCluster.exists(enter.getToken());
        if (!flag) {
            throw new FoundationException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(enter.getToken());
        if (map == null) {
            throw new FoundationException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        UserToken userToken = new UserToken();
        try {
            org.apache.commons.beanutils.BeanUtils.populate(userToken, map);
        } catch (Exception e) {
            log.error("checkToken Exception sessionMap:" + map, e);
        }

        PlaUser plaUser = plaUserService.getById(userToken.getUserId());
        if (null == plaUser) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        LambdaQueryWrapper<PlaUserPassword> qw = new LambdaQueryWrapper<>();
        qw.eq(PlaUserPassword::getDr, Constant.DR_FALSE);
        qw.eq(PlaUserPassword::getLoginName, plaUser.getLoginName());
        qw.last("limit 1");
        PlaUserPassword password = plaUserPasswordService.getOne(qw);
        if (null == password) {
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        String pwd = DigestUtils.md5Hex(enter.getKeyword() + password.getSalt());
        if (StringUtils.equals(pwd, password.getPassword())) {
            return new BooleanResult(Boolean.TRUE);
        }
        return new BooleanResult(Boolean.FALSE);
    }

    /**
     * ?????????????????????????????????PIN
     */
    @Override
    public BooleanResult verifyPin(GeneralEnter enter) {
        Boolean flag = jedisCluster.exists(enter.getToken());
        if (!flag) {
            throw new FoundationException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(enter.getToken());
        if (map == null) {
            throw new FoundationException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        UserToken userToken = new UserToken();
        try {
            org.apache.commons.beanutils.BeanUtils.populate(userToken, map);
        } catch (Exception e) {
            log.error("checkToken Exception sessionMap:" + map, e);
        }

        PlaUser plaUser = plaUserService.getById(userToken.getUserId());
        if (null == plaUser) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        LambdaQueryWrapper<PlaUserPassword> qw = new LambdaQueryWrapper<>();
        qw.eq(PlaUserPassword::getDr, Constant.DR_FALSE);
        qw.eq(PlaUserPassword::getLoginName, plaUser.getLoginName());
        qw.last("limit 1");
        PlaUserPassword password = plaUserPasswordService.getOne(qw);
        if (null == password) {
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        if (null == password.getDef1()) {
            return new BooleanResult(Boolean.FALSE);
        }
        return new BooleanResult(Boolean.TRUE);
    }

    /**
     * ??????token
     *
     * @param enter
     * @param userDto
     * @return
     */
    @Override
    public UserToken setToken(LoginEnter enter, AccountsDto userDto) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // ??????refresh_token
        String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");

        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setRefreshToken(refreshToken);
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
        userToken.setDeptId(enter.getOpeDeptId() == null ? new Long("0") : enter.getOpeDeptId());
        try {
            Map<String, String> map = org.apache.commons.beanutils.BeanUtils.describe(userToken);
            log.info("??????map????????????????????? " + JSON.toJSONString(map));
            map.remove("requestId");
            jedisCluster.hmset(token, map);
            jedisCluster.expire(token, new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());
            //jedisCluster.expire(token, new Long(RedisExpireEnum.MINUTES_1.getSeconds()).intValue());  //????????????
            jedisCluster.hmset(refreshToken, map);
            jedisCluster.expire(refreshToken, new Long(RedisExpireEnum.WEEK_1.getSeconds()).intValue());
            //jedisCluster.expire(refreshToken, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());  // ????????????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult setPassword(SetPasswordEnter enter) {

        //???????????????
        if (StringUtils.isNotEmpty(enter.getConfirmPassword())) {
            enter.setConfirmPassword(SesStringUtils.stringTrim(enter.getConfirmPassword()));
        }
        if (StringUtils.isNotEmpty(enter.getNewPassword())) {
            enter.setNewPassword(SesStringUtils.stringTrim(enter.getNewPassword()));
        }

        //???????????????
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
         * ??????????????????????????????
         */
        if (!StringUtils.equals(enter.getNewPassword(), enter.getConfirmPassword())) {
            throw new FoundationException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(),
                    ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        GetUserEnter getUser = new GetUserEnter();
        getUser.setRequestId(enter.getRequestId());
        if (StringUtils.isNotBlank(enter.getToken())) {
            UserToken userToken = getUserToken(enter.getToken());
            if (null == userToken.getUserId() || 0 == userToken.getUserId()) {
                throw new FoundationException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            getUser.setUserId(userToken.getUserId());
            getUser.setAppId(enter.getAppId());
            getUser.setSystemId(enter.getSystemId());
        } else {
            /**
             * ??????????????????????????????
             */
            Map<String, String> hash = jedisCluster.hgetAll(enter.getRequestId());
            if (null == hash || hash.isEmpty()) {
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

        log.info("getUserLimitOne????????????:[{}]", getUser);
        PlaUser emailUser = userTokenMapper.getUserLimitOne(getUser);
        if (null == emailUser) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        QueryWrapper<PlaUserPassword> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUserPassword.COL_LOGIN_NAME, emailUser.getLoginName());
        wrapper.eq(PlaUserPassword.COL_DR, Constant.DR_FALSE);
        PlaUserPassword updatePassword = userPasswordMapper.selectOne(wrapper);

        updatePassword.setPassword(DigestUtils.md5Hex(enter.getConfirmPassword() + updatePassword.getSalt()));
        updatePassword.setUpdatedBy(emailUser.getId());
        updatePassword.setUpdatedTime(new Date());
        userPasswordMapper.updateById(updatePassword);

        //??????????????????????????????
        PlaUser user = new PlaUser();
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        user.setActivationTime(new Date());
        user.setUpdatedBy(emailUser.getId());
        user.setUpdatedTime(new Date());

        UpdateWrapper<PlaUser> userUpdate = new UpdateWrapper<>();
        userUpdate.eq(PlaUser.COL_DR, Constant.DR_FALSE);
        userUpdate.eq(PlaUser.COL_LOGIN_NAME, emailUser.getLoginName());
        userUpdate.eq(PlaUser.COL_STATUS, UserStatusEnum.INACTIVATED.getValue());
        userMapper.update(user, userUpdate);

        if (StringUtils.isNotBlank(emailUser.getLastLoginToken())) {
            // ????????????token,????????????
            jedisCluster.del(emailUser.getLastLoginToken());
            jedisCluster.del(enter.getRequestId());

        }
        // ?????????????????????????????????
        QueryWrapper<PlaTenant> plaTenantQueryWrapper = new QueryWrapper<>();
        plaTenantQueryWrapper.eq(PlaTenant.COL_DR, Constant.DR_FALSE);
        plaTenantQueryWrapper.eq(PlaTenant.COL_EMAIL, emailUser.getLoginName());
        PlaTenant plaTenant = plaTenantMapper.selectOne(plaTenantQueryWrapper);
        if (null != plaTenant) {
            plaTenant.setActivationTime(new Date());
            plaTenant.setUpdatedTime(new Date());
            plaTenantMapper.updateById(plaTenant);
        }

        //token ?????????????????????????????? ?????????????????? ?????? ??????????????????requestId ???????????????
        if (StringUtils.isBlank(enter.getToken())) {
            if (jedisCluster.exists(enter.getRequestId())) {
                jedisCluster.del(enter.getRequestId());
            }
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult chanagePassword(ChanagePasswordEnter enter) {

        //????????????????????????
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
        //????????????
        //???????????????
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

        //???????????????????????????
        if (!StringUtils.equals(enter.getNewPassword(), enter.getConfirmNewPassword())) {
            throw new FoundationException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        GetUserEnter getUser = new GetUserEnter();
        getUser.setAppId(enter.getAppId());
        getUser.setSystemId(enter.getSystemId());
        getUser.setEmail(enter.getEmail());
        PlaUser user = userTokenMapper.getUserLimitOne(getUser);
        if (null == user) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        QueryWrapper<PlaUserPassword> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUserPassword.COL_LOGIN_NAME, user.getLoginName());
        wrapper.eq(PlaUserPassword.COL_DR, Constant.DR_FALSE);
        PlaUserPassword updatePassword = userPasswordMapper.selectOne(wrapper);

        //???????????????
        String oldPsaaword = DigestUtils.md5Hex(enter.getOldPassword() + updatePassword.getSalt());
        if (!StringUtils.equals(oldPsaaword, updatePassword.getPassword())) {
            throw new FoundationException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        updatePassword.setPassword(DigestUtils.md5Hex(enter.getNewPassword() + updatePassword.getSalt()));
        updatePassword.setUpdatedBy(enter.getUserId());
        updatePassword.setUpdatedTime(new Date());
        userPasswordMapper.updateById(updatePassword);

        if (StringUtils.isNotBlank(user.getLastLoginToken())) {
            // ????????????token,????????????
            jedisCluster.del(user.getLastLoginToken());
        }

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult sendEmail(BaseSendMailEnter enter) {

        //???????????????
        if (StringUtils.isNotEmpty(enter.getMail())) {
            enter.setMail(SesStringUtils.stringTrim(enter.getMail()));
        }

        //???????????????
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

        if (null == limitOne) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        QueryWrapper<PlaUserPassword> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getMail());
        wrapper.eq(PlaUserPassword.COL_DR, Constant.DR_FALSE);
        wrapper.isNull(PlaUserPassword.COL_PASSWORD);
        Integer count = userPasswordMapper.selectCount(wrapper);

        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();

        if (0 == count) {
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
     * App ????????????????????? ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult verifyAccount(VerifyAccountEnter enter) {
        if (StringUtils.isBlank(enter.getPassword())) {
            throw new FoundationException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        //???????????????
        enter.setPassword(SesStringUtils.stringTrim(enter.getPassword()));

        PlaUser plaUser = plaUserMapper.selectById(enter.getUserId());
        if (null == plaUser) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        QueryWrapper<PlaUserPassword> plaUserPasswordQueryWrapper = new QueryWrapper<>();
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_LOGIN_NAME, plaUser.getLoginName());
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_DR, Constant.DR_FALSE);
        PlaUserPassword plaUserPassword = userPasswordMapper.selectOne(plaUserPasswordQueryWrapper);
        if (null == plaUserPassword) {
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
        if (null == map) {
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


    @Override
    public Integer totalUserCount(List<Integer> list) {
        QueryWrapper<PlaUser> qw = new QueryWrapper<>();
        qw.in(PlaUser.COL_USER_TYPE, list);
        int count = plaUserService.count(qw);
        return count;
    }


    @Override
    public Integer registerCount(List<Integer> list, String dateStr) {
        QueryWrapper<PlaUser> qw = new QueryWrapper<>();
        qw.in(PlaUser.COL_USER_TYPE, list);
        qw.like(PlaUser.COL_CREATED_TIME, dateStr);
        int count = plaUserService.count(qw);
        return count;
    }


    @Override
    public Integer activeCount(List<Integer> list, String dateStr) {
        QueryWrapper<PlaUser> qw = new QueryWrapper<>();
        qw.in(PlaUser.COL_USER_TYPE, list);
        qw.like(PlaUser.COL_LAST_LOGIN_TIME, dateStr);
        int count = plaUserService.count(qw);
        return count;
    }
}
