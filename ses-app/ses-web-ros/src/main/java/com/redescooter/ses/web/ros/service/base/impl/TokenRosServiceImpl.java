package com.redescooter.ses.web.ros.service.base.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.CacheConstants;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.GetAccountKeyResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.RefreshTokenEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.SendCodeMobileUserTaskEnter;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserProfileMapper;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysUserRoleService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.base.TokenRosService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.vo.account.AddSysUserEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class TokenRosServiceImpl implements TokenRosService {

    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private OpeSysUserMapper sysUserMapper;
    @Autowired
    private OpeSysUserProfileMapper sysUserProfileMapper;
    @Autowired
    private OpeSysUserRoleService sysUserRoleService;

    @Autowired
    private OpeSysUserService opeSysUserService;

    @DubboReference
    private IdAppService idAppService;
    @DubboReference
    private MailMultiTaskService mailMultiTaskService;
    @Value("${Request.privateKey}")
    private  String privateKey;

    @Autowired
    private StaffService staffService;

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public TokenResult login(LoginEnter enter) {

        //???????????????????????????
        enter.setLoginName(SesStringUtils.stringTrim(enter.getLoginName()));

        QueryWrapper<OpeSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(OpeSysUser.COL_DR, 0);
        wrapper.eq(OpeSysUser.COL_APP_ID, enter.getAppId());
        wrapper.eq(OpeSysUser.COL_SYSTEM_ID, enter.getSystemId());
        wrapper.eq(OpeSysUser.COL_DEF1,SysUserSourceEnum.SYSTEM.getValue());
        wrapper.last("limit 1");
        OpeSysUser sysUser = sysUserMapper.selectOne(wrapper);
        //??????????????????????????????????????????????????????????????????????????????
        if (sysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (!enter.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            List<OpeSysUserRole> roles = sysUserRoleService.list(new LambdaQueryWrapper<OpeSysUserRole>().eq(OpeSysUserRole::getUserId, sysUser.getId()));
            if (!CollUtil.isNotEmpty(roles)) {
                throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
            }
        }
        if(sysUser.getStatus().equals(SysUserStatusEnum.LOCK.getCode())){
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_DISABLED.getCode(), ExceptionCodeEnums.ACCOUNT_DISABLED.getMessage());
        }
        // ?????????????????????????????????  2020 7 17
        //????????????
        String decryptPassword = checkPassWord(enter);
        //??????MD5 ??????
        String password = DigestUtils.md5Hex(decryptPassword + sysUser.getSalt());
        String psdErrorKey = JedisConstant.LOGIN_PSD_ERROR_NUM + sysUser.getId();
        if(jedisCluster.exists(psdErrorKey)){
            Map<String, String> data = jedisCluster.hgetAll(psdErrorKey);
            String oldNum = data.get("num");
            if(Integer.parseInt(oldNum) >= 5){
                throw new SesWebRosException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getMessage());
            }
        }
        if (!password.equals(sysUser.getPassword())) {
            //????????????3????????????????????????????????????????????????5???????????????1??????
            Integer errorNum = passWordMistaken(psdErrorKey);
            if(errorNum < 3){
                throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }else if(errorNum == 3 || errorNum == 4){
                throw new SesWebRosException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getMessage());
            }else {
                throw new SesWebRosException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getMessage());
            }
        }
        //???????????????????????????????????????????????????????????????????????????????????????
        if(jedisCluster.exists(psdErrorKey)){
            jedisCluster.del(psdErrorKey);
        }
        TokenResult result = getTokenResult(enter, sysUser);
        // 2020 9 14 ?????? ?????????????????????????????????????????????????????????
        staffService.inintUserMsg(sysUser.getId());
        return result;
    }

    private TokenResult getTokenResult(LoginEnter enter, OpeSysUser sysUser) {
        //????????????
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.LOCK.getCode())) {
            throw new SesWebRosException(ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getCode(), ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getMessage());
        }
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.CANCEL.getCode())) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_CANCELLED.getCode(), ExceptionCodeEnums.ACCOUNT_CANCELLED.getMessage());
        }
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.EXPIRED.getCode())) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_EXPIRED.getCode(), ExceptionCodeEnums.ACCOUNT_EXPIRED.getMessage());
        }
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
        if(Strings.isNullOrEmpty(sysUser.getLastLoginToken()) && !sysUser.getLoginName().equals(Constant.ADMIN_USER_NAME)){
            // ?????????????????????token??????  ????????????????????? ??????????????? ??????????????????????????????????????????(???????????????????????????????????????)
            String key = JedisConstant.FIRST_LOGIN_RESET_PSD + sysUser.getId();
            Map<String,String> map = new HashMap<>();
            map.put("flag","1");
            jedisCluster.hmset(key, map);
        }
        //??????????????????,???????????????
        //  setAuth(userRole.getRoleId());

        sysUser.setLastLoginToken(userToken.getToken());
        sysUser.setRefreshToken(userToken.getRefreshToken());
        sysUser.setLastLoginTime(new Date(enter.getTimestamp()));
        sysUser.setLastLoginIp(enter.getClientIp());
        sysUser.setUpdatedBy(enter.getUserId());
        sysUser.setUpdatedTime(new Date());
        sysUserMapper.updateById(sysUser);

        TokenResult result = new TokenResult();
        result.setToken(userToken.getToken());
        result.setRefreshToken(userToken.getRefreshToken());
        result.setRequestId(enter.getRequestId());
//        result.setResetPsd(flag);
        return result;
    }


    @Override
    public GeneralResult emailLoginSendCode(LoginEnter enter) {
        // ??????????????????????????????????????????????????????
        OpeSysUser sysUser = getOpeSysUser(enter);
        // ????????????????????????  ?????????????????????  ???????????????
        String code = String.valueOf((int) ((Math.random()*9+1)*100000));
        String key = JedisConstant.EMAIL_LOGIN_CODE + enter.getLoginName();
        Map<String,String> map = new HashMap<>();
        map.put("code",code);
        jedisCluster.hmset(key, map);
        // ?????????????????????5??????
        jedisCluster.expire(key, Long.valueOf(RedisExpireEnum.MINUTES_5.getSeconds()).intValue());
        // TODO ??????????????????  ????????????????????????  ???????????????????????????????????????  (??????????????????)
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


    @Override
    public TokenResult emailLogin(LoginEnter enter) {
        if(Strings.isNullOrEmpty(enter.getCode())){
            throw new SesWebRosException(ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getCode(), ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getMessage());
        }
        String key = JedisConstant.EMAIL_LOGIN_CODE + enter.getLoginName();
        if(!jedisCluster.exists(key)){
            throw new SesWebRosException(ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getCode(), ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(key);
        String code = map.get("code");
        if(Strings.isNullOrEmpty(code)){
            throw new SesWebRosException(ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getCode(), ExceptionCodeEnums.EAMIL_CODE_TIME_OUT.getMessage());
        }
        if(!code.equals(enter.getCode())){
            throw new SesWebRosException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
        }
        // ????????? ???????????????????????????????????????????????? ???????????? ??????????????????????????????
        jedisCluster.del(key);
        // ???????????????????????????????????????
        OpeSysUser sysUser = getOpeSysUser(enter);
//        LoginEnter loginEnter = new LoginEnter();
//        BeanUtil.copyProperties(enter,loginEnter);
        TokenResult result =  getTokenResult(enter, sysUser);
        return result;
    }

    private OpeSysUser getOpeSysUser(LoginEnter enter) {
        if(enter.getLoginName().length() > 50 || enter.getLoginName().length() < 2){
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.EMAIL_IS_NOT_ILLEGAL.getMessage());
        }
        QueryWrapper<OpeSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(OpeSysUser.COL_DR, 0);
        wrapper.eq(OpeSysUser.COL_APP_ID, enter.getAppId());
        wrapper.eq(OpeSysUser.COL_SYSTEM_ID, enter.getSystemId());
        wrapper.eq(OpeSysUser.COL_DEF1, SysUserSourceEnum.SYSTEM.getValue());
        wrapper.last("limit 1");
        OpeSysUser sysUser = sysUserMapper.selectOne(wrapper);
        //??????????????????????????????????????????????????????????????????????????????
        if (sysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        return sysUser;
    }


    public Integer passWordMistaken(String key){
        Integer num = 1;
        Map<String,String> map = new HashMap<>();
        // ??????????????????????????????????????????
        if(jedisCluster.exists(key)){
            //???????????????????????????????????????????????????
            Map<String, String> data = jedisCluster.hgetAll(key);
            String oldNum = data.get("num");
            if(Integer.parseInt(oldNum) >= 5){
                return Integer.parseInt(oldNum);
            }
            num = Integer.parseInt(oldNum) + 1;
            if(num == 5){
                map.put("num",num.toString());
                jedisCluster.hmset(key, map);
                jedisCluster.expire(key, Long.valueOf(RedisExpireEnum.MINUTES_1.getSeconds()).intValue());
                return num;
            }
        }
        map.put("num",num.toString());
        jedisCluster.hmset(key, map);
        return num;
    }



    /**
     * Rsa ????????????
     *
     * @param enter
     * @return
     */
    private String checkPassWord(LoginEnter enter) {
        //???????????? ??????????????? ????????????????????????
        if (StringUtils.isBlank(enter.getPassword())){
            throw new SesWebRosException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(), ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
        }
        enter.setPassword(SesStringUtils.stringTrim(enter.getPassword()));
        String decryptPassword = "";
        try {
            decryptPassword = RsaUtils.decrypt(enter.getPassword(), privateKey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        return decryptPassword;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GetAccountKeyResult getAccountKey(GeneralEnter enter) {
        Map<String, String> stringStringMap = RsaUtils.generateRsaKey(RsaUtils.DEFAULT_RSA_KEY_SIZE);

        //????????????
        String key = new StringBuilder(enter.getRequestId()).append(":::").append(RsaUtils.PRIVATE_KEY).toString();
        jedisCluster.setex(key, (int) RedisExpireEnum.getSeconds(RedisExpireEnum.DAY_1.getTime()), stringStringMap.get(RsaUtils.PRIVATE_KEY));

        GetAccountKeyResult getAccountKeyResult = new GetAccountKeyResult();
        getAccountKeyResult.setPublicKey(stringStringMap.get(RsaUtils.PUBLIC_KEY));
        getAccountKeyResult.setRequestId(enter.getRequestId());
        return getAccountKeyResult;
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
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
                throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setNewPassword(newPassword);
            enter.setOldPassword(confirmPassword);
        }

        if (!StringUtils.equals(enter.getNewPassword(), enter.getOldPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(),
                    ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }

        GetUserEnter getUser = getGetUserEnter(enter);
        QueryWrapper<OpeSysUser> emailUser = new QueryWrapper<>();
        emailUser.eq(OpeSysUser.COL_LOGIN_NAME, getUser.getEmail());
        emailUser.eq(OpeSysUser.COL_APP_ID, getUser.getAppId());
        emailUser.eq(OpeSysUser.COL_SYSTEM_ID, getUser.getSystemId());
        emailUser.eq(OpeSysUser.COL_ID, getUser.getUserId());
        emailUser.last("limit 1");
        OpeSysUser opeSysUser = opeSysUserService.getOne(emailUser);
        if (opeSysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                    ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
        }

        opeSysUser.setPassword(DigestUtils.md5Hex(enter.getOldPassword() + opeSysUser.getSalt()));
        opeSysUser.setUpdatedBy(opeSysUser.getId());
        opeSysUser.setUpdatedTime(new Date());
        opeSysUserService.updateById(opeSysUser);

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
            if(Strings.isNullOrEmpty(code)){
                throw new SesWebRosException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            if (!StringUtils.equals(code, enter.getCode())) {
                throw new SesWebRosException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
            }
             // ??????????????????????????????
            UserToken userToken = getUserToken(enter.getToken());
            if (userToken.getUserId() == null || userToken.getUserId() == 0) {
                throw new FoundationException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            getUser.setUserId(userToken.getUserId());
            getUser.setAppId(enter.getAppId());
            getUser.setSystemId(enter.getSystemId());
        } else {
             // ??????????????????????????????
            Map<String, String> hash = jedisCluster.hgetAll(enter.getRequestId());
            if (hash == null || hash.isEmpty()) {
                throw new SesWebRosException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            if (!StringUtils.equals(hash.get("systemId"), enter.getSystemId())) {
                throw new SesWebRosException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            if (!StringUtils.equals(hash.get("appId"), enter.getAppId())) {
                throw new SesWebRosException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            getUser.setUserId(Long.parseLong(StringUtils.isBlank(hash.get("userId")) ? "0" : hash.get("userId")) == 0 ? null : Long.parseLong(hash.get("userId")));
            getUser.setEmail(StringUtils.isBlank(hash.get("email")) ? null : hash.get("email"));
            getUser.setAppId(StringUtils.isBlank(hash.get("appId")) ? null : hash.get("appId"));
            getUser.setSystemId(StringUtils.isBlank(hash.get("systemId")) ? null : hash.get("systemId"));
        }
        return getUser;
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
        if (!StringUtils.equals(userToken.getClientType(), enter.getClientType()) || !StringUtils.equals(userToken.getSystemId(), enter.getSystemId()) || !StringUtils.equals(userToken.getAppId(),
                enter.getAppId())) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        OpeSysUser opeSysUser = opeSysUserService.getById(userToken.getUserId());
        if (opeSysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
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
    public GeneralResult sendCode(BaseSendMailEnter enter) {
        return null;
    }

    /**
     * ??????ROS??????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult createRosUser(AddSysUserEnter enter) {
        String loginName = enter.getLoginName();
        String password = enter.getPassword();
        if (StringUtils.isBlank(loginName)) {
            throw new SesWebRosException(ExceptionCodeEnums.LOGIN_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LOGIN_NAME_CANNOT_EMPTY.getMessage());
        }

        QueryWrapper<OpeSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(OpeSysUser.COL_DEF1, SysUserSourceEnum.SYSTEM.getValue());

        if (sysUserMapper.selectCount(wrapper) > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
        if (StringUtils.isBlank(password)) {
            password = Constant.DEFAULT_PASSWORD;
        }

        OpeSysUser opeSysUser = buildSysUserSingle(enter, password);
        sysUserMapper.insert(opeSysUser);

        OpeSysUserProfile opeSysUserProfile = buildSysUserProfileSingle(opeSysUser, enter);
        sysUserProfileMapper.insert(opeSysUserProfile);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????ROS??????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult deleteRosUser(IdEnter enter) {

        OpeSysUser opeSysUser = sysUserMapper.selectById(enter.getId());

        if (opeSysUser.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
        }

        UpdateWrapper<OpeSysUserProfile> delete = new UpdateWrapper<>();
        delete.eq(OpeSysUserProfile.COL_SYS_USER_ID, opeSysUser.getId());
        sysUserProfileMapper.delete(delete);
        sysUserMapper.deleteById(enter.getId());

        return new GeneralResult(enter.getRequestId());
    }

  /**
   * ????????????????????????
   *
   * @param baseSendMailEnter
   * @return
   */
  @Override
  public GeneralResult sendForgetPasswordEmail(BaseSendMailEnter baseSendMailEnter) {

      if (Strings.isNullOrEmpty(baseSendMailEnter.getMail())) {
          throw new SesWebRosException(ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getMessage());
      }
      String decryptMail = null;
      if (StringUtils.isNotEmpty(baseSendMailEnter.getMail())) {
          try {
              //????????????
              decryptMail = RsaUtils.decrypt(baseSendMailEnter.getMail(), privateKey);
          } catch (Exception e) {
              throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
          }
          baseSendMailEnter.setMail(decryptMail);

          //??????????????????
          checkString(baseSendMailEnter.getMail(), 2, 50);
      }
      //??????????????????????????????
      OpeSysUser opeSysUser = opeSysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>().eq(OpeSysUser::getDef1, SysUserSourceEnum.SYSTEM.getValue()).eq(OpeSysUser::getLoginName,
              baseSendMailEnter.getMail()).last("limit 1"));
      if (null == opeSysUser) {
          throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
      }
      BaseMailTaskEnter enter = new BaseMailTaskEnter();
      enter.setName(baseSendMailEnter.getMail().substring(0, baseSendMailEnter.getMail().indexOf("@")));
      enter.setEvent(MailTemplateEventEnums.ROS_FORGET_PSD_SEND_MAIL.getEvent());
      enter.setMailSystemId(SystemIDEnums.REDE_SES.getSystemId());
      enter.setMailAppId(AppIDEnums.SES_ROS.getValue());
      enter.setToMail(baseSendMailEnter.getMail());
      enter.setRequestId(baseSendMailEnter.getRequestId());
      enter.setUserRequestId(baseSendMailEnter.getRequestId());
      enter.setToUserId(opeSysUser.getId());
      mailMultiTaskService.addSetPasswordWebUserTask(enter);
      return new GeneralResult(baseSendMailEnter.getRequestId());
  }

  private OpeSysUser buildSysUserSingle(AddSysUserEnter enter, String password) {
        OpeSysUser sysUser = new OpeSysUser();
        int salt = RandomUtils.nextInt(10000, 99999);
        String savePassword = DigestUtils.md5Hex(password + salt);
        sysUser.setId(idAppService.getId(SequenceName.OPE_SYS_USER));
        sysUser.setAppId(enter.getAppId());
        sysUser.setSystemId(enter.getSystemId());
        sysUser.setPassword(savePassword);
        sysUser.setSalt(String.valueOf(salt));
        sysUser.setStatus(SysUserStatusEnum.NORMAL.getCode());
        sysUser.setLoginName(enter.getLoginName());
        sysUser.setCreatedBy(enter.getUserId());
        sysUser.setCreatedTime(new Date());
        sysUser.setUpdatedBy(enter.getUserId());
        sysUser.setUpdatedTime(new Date());
        sysUser.setDef1(SysUserSourceEnum.SYSTEM.getValue());
        return sysUser;
    }

    private OpeSysUserProfile buildSysUserProfileSingle(OpeSysUser sysUser, AddSysUserEnter enter) {
        OpeSysUserProfile sysUserProfile = new OpeSysUserProfile();
        sysUserProfile.setId(idAppService.getId(SequenceName.OPE_SYS_USER_PROFILE));
        sysUserProfile.setSysUserId(sysUser.getId());
        if (sysUser.getLoginName().contains("@")) {
            String str = sysUser.getLoginName().substring(0, sysUser.getLoginName().indexOf("@"));
            sysUserProfile.setFirstName(str);
            sysUserProfile.setLastName(str);
            sysUserProfile.setFullName(str);
            sysUserProfile.setEmail(sysUser.getLoginName());
        } else {
            sysUserProfile.setFirstName(sysUser.getLoginName());
            sysUserProfile.setFirstName(sysUser.getLoginName());
            sysUserProfile.setLastName(sysUser.getLoginName());
            sysUserProfile.setFullName(sysUser.getLoginName());
        }
        sysUserProfile.setGender("male");
        sysUserProfile.setCreatedBy(enter.getUserId());
        sysUserProfile.setCreatedTime(new Date());
        sysUserProfile.setUpdatedBy(enter.getUserId());
        sysUserProfile.setUpdatedTime(new Date());
        return sysUserProfile;
    }

    private UserToken getUserToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(token);
        if (map == null) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
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
     * ??????token
     */
    @Override
    public TokenResult refreshToken(RefreshTokenEnter enter) {
        if (null == enter || StringUtils.isBlank(enter.getRefreshToken())) {
            throw new SesWebRosException(ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getMessage());
        }
        String refreshToken = enter.getRefreshToken();
        Boolean flag = jedisCluster.exists(refreshToken);
        // ??????token???redis?????????,??????????????????,???????????????????????????
        if (!flag) {
            throw new SesWebRosException(ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.REFRESH_TOKEN_NOT_EXIST.getMessage());
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
        OpeSysUser model = new OpeSysUser();
        Long userId = Long.valueOf(map.get("userId"));
        model.setId(userId);
        model.setLastLoginToken(token);
        model.setLastLoginTime(new Date());
        opeSysUserService.updateById(model);

        TokenResult result = new TokenResult();
        result.setToken(token);
        result.setRefreshToken(refreshToken);
        return result;
    }

    private UserToken setToken(LoginEnter enter, OpeSysUser user) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // ??????refresh_token
        String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");

        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setRefreshToken(refreshToken);
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

  private void checkString(String str, int min, int max) {
    if (StringUtils.isEmpty(str)) {
      throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
    }
    if (str.length() < min || str.length() > max) {
      throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
    }
  }

    private void setAuth(long roleId) {

        String key = new StringBuilder().append(roleId).append(":::").append(CacheConstants.MENU_DETAILS).toString();

        Boolean aBoolean = jedisCluster.exists(key);

    }

}
