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
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.SendCodeMobileUserTaskEnter;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.accountType.RsaUtils;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

    @Reference
    private IdAppService idAppService;
    @Reference
    private MailMultiTaskService mailMultiTaskService;
    @Value("${Request.privateKey}")
    private  String privateKey;

    @Autowired
    private StaffService staffService;

    /**
     * 用户登录
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public TokenResult login(LoginEnter enter) {

        //用户名密码去除空格
        enter.setLoginName(SesStringUtils.stringTrim(enter.getLoginName()));

        QueryWrapper<OpeSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(OpeSysUser.COL_DR, 0);
        wrapper.eq(OpeSysUser.COL_APP_ID, enter.getAppId());
        wrapper.eq(OpeSysUser.COL_SYSTEM_ID, enter.getSystemId());
        wrapper.eq(OpeSysUser.COL_DEF1,SysUserSourceEnum.SYSTEM.getValue());
        wrapper.last("limit 1");
        OpeSysUser sysUser = sysUserMapper.selectOne(wrapper);
        //用户名验证，及根据用户名未查到改用户，则该用户不存在
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
        // 把密码的校验放到这里来  2020 7 17
        //密码解密
        String decryptPassword = checkPassWord(enter);
        //密码MD5 加密
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
            //连续输错3次密码，出现图片验证码，连续输错5次账号冻结1分钟
            Integer errorNum = passWordMistaken(psdErrorKey);
            if(errorNum < 3){
                throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }else if(errorNum == 3 || errorNum == 4){
                throw new SesWebRosException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getMessage());
            }else {
                throw new SesWebRosException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getMessage());
            }
        }
        //密码校验通过之后，看看之前有没有输错过，有的话，从缓存清除
        if(jedisCluster.exists(psdErrorKey)){
            jedisCluster.del(psdErrorKey);
        }
        TokenResult result = getTokenResult(enter, sysUser);
        // 2020 9 14 追加 登陆成功之后，初始化用户的一些权限信息
        staffService.inintUserMsg(sysUser.getId());
        return result;
    }

    private TokenResult getTokenResult(LoginEnter enter, OpeSysUser sysUser) {
        //状态验证
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.LOCK.getCode())) {
            throw new SesWebRosException(ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getCode(), ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getMessage());
        }
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.CANCEL.getCode())) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_CANCELLED.getCode(), ExceptionCodeEnums.ACCOUNT_CANCELLED.getMessage());
        }
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.EXPIRED.getCode())) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_EXPIRED.getCode(), ExceptionCodeEnums.ACCOUNT_EXPIRED.getMessage());
        }
        //清除上次登录token信息
        if (StringUtils.isNotBlank(sysUser.getLastLoginToken())) {
            jedisCluster.del(sysUser.getLastLoginToken());

            //第一次登录成功后 替换私钥的key值
            jedisCluster.del(new StringBuilder(enter.getRequestId()).append(":::").append(RsaUtils.PRIVATE_KEY).toString());
            String key = new StringBuilder(enter.getLoginName()).append(":::").append("LOGIN").toString();
            jedisCluster.setex(key, (int) RedisExpireEnum.getSeconds(RedisExpireEnum.DAY_1.getTime()), key);
        }
        //将token及用户相关信息 放到Redis中
        UserToken userToken = setToken(enter, sysUser);
        boolean flag = false;
        if(Strings.isNullOrEmpty(sysUser.getLastLoginToken())){
            flag = true;
        }
        //获取用户角色,更新至缓存
        //  setAuth(userRole.getRoleId());

        sysUser.setLastLoginToken(userToken.getToken());
        sysUser.setLastLoginTime(new Date(enter.getTimestamp()));
        sysUser.setLastLoginIp(enter.getClientIp());
        sysUser.setUpdatedBy(enter.getUserId());
        sysUser.setUpdatedTime(new Date());
        sysUserMapper.updateById(sysUser);

        TokenResult result = new TokenResult();
        result.setToken(userToken.getToken());
        result.setRequestId(enter.getRequestId());
        result.setResetPsd(flag);
        return result;
    }


    @Override
    public GeneralResult emailLoginSendCode(LoginEnter enter) {
        // 先验证码输入的邮箱是否在系统中注册过
        OpeSysUser sysUser = getOpeSysUser(enter);
        // 生成随机的验证码  然后放在缓存里  再发给用户
        String code = String.valueOf((int) ((Math.random()*9+1)*100000));
        String key = JedisConstant.EMAIL_LOGIN_CODE + enter.getLoginName();
        Map<String,String> map = new HashMap<>();
        map.put("code",code);
        jedisCluster.hmset(key, map);
        // 缓存时间暂定位5分钟
        jedisCluster.expire(key, Long.valueOf(RedisExpireEnum.MINUTES_5.getSeconds()).intValue());
        // TODO 给用户发邮件  邮件里面是验证码  登陆的时候验证邮箱和验证码  (等待邮件模板)
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
        // 到这里 邮箱登陆的校验就差不多算是通过了 清除缓存 再下面就是登陆的逻辑
        jedisCluster.del(key);
        // 登陆还是按照原来的登陆逻辑
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
        //用户名验证，及根据用户名未查到改用户，则该用户不存在
        if (sysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        return sysUser;
    }


    public Integer passWordMistaken(String key){
        Integer num = 1;
        Map<String,String> map = new HashMap<>();
        // 先判断该用户有没有输错过密码
        if(jedisCluster.exists(key)){
            //缓存中能找到，说明已经输错过密码了
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
     * Rsa 解密校验
     *
     * @param enter
     * @return
     */
    private String checkPassWord(LoginEnter enter) {
        //密码解密 无法解密时 就是提示密码错误
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
     * 获取密钥
     *
     * @param enter
     * @return
     */
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
     * 更换密码
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult modifyPassword(ModifyPasswordEnter enter) {
        //密码去空格
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
            // 清除原有token,重新登录
            jedisCluster.del(opeSysUser.getLastLoginToken());
            jedisCluster.del(enter.getRequestId());
        }
        //token 为空为系统外设置密码 设置成功过后 清楚 缓存保证一个requestId 只能用一次
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
            // 数据校验
            String code = jedisCluster.get(enter.getRequestId());
            if(Strings.isNullOrEmpty(code)){
                throw new SesWebRosException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            if (!StringUtils.equals(code, enter.getCode())) {
                throw new SesWebRosException(ExceptionCodeEnums.CODE_IS_WRONG.getCode(), ExceptionCodeEnums.CODE_IS_WRONG.getMessage());
            }
             // 系统内部进行设置密码
            UserToken userToken = getUserToken(enter.getToken());
            if (userToken.getUserId() == null || userToken.getUserId() == 0) {
                throw new FoundationException(ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getCode(),
                        ExceptionCodeEnums.TOKEN_MESSAGE_IS_FALSE.getMessage());
            }
            getUser.setUserId(userToken.getUserId());
            getUser.setAppId(enter.getAppId());
            getUser.setSystemId(enter.getSystemId());
        } else {
             // 系统外部进行设置密码
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
     * 登陆token检查
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
     * 邮件发送
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult sendCode(BaseSendMailEnter enter) {
        return null;
    }

    /**
     * 添加ROS用户
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
     * 删除ROS用户
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
   * 发送忘记密码邮箱
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
              //邮箱解密
              decryptMail = RsaUtils.decrypt(baseSendMailEnter.getMail(), privateKey);
          } catch (Exception e) {
              throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
          }
          baseSendMailEnter.setMail(decryptMail);

          //邮箱长度校验
          checkString(baseSendMailEnter.getMail(), 2, 50);
      }
      //先判断邮箱是否存在、
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

    private UserToken setToken(LoginEnter enter, OpeSysUser user) {
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
