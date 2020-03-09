package com.redescooter.ses.web.ros.service.base.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserProfileMapper;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.TokenRosService;
import com.redescooter.ses.web.ros.vo.account.AddSysUserEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
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

    @Reference
    private IdAppService idAppService;

    /**
     * 用户登录
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public TokenResult login(LoginEnter enter) {

        QueryWrapper<OpeSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(OpeSysUser.COL_DR, 0);
        wrapper.eq(OpeSysUser.COL_APP_ID, enter.getAppId());
        wrapper.eq(OpeSysUser.COL_SYSTEM_ID, enter.getSystemId());

        OpeSysUser sysUser = sysUserMapper.selectOne(wrapper);
        //用户名验证，及根据用户名未查到改用户，则该用户不存在
        if (sysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
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
        String password = DigestUtils.md5Hex(enter.getPassword() + sysUser.getSalt());

        if (!password.equals(sysUser.getPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        //清除上次登录token信息
        if (StringUtils.isNotBlank(sysUser.getLastLoginToken())) {
            jedisCluster.del(sysUser.getLastLoginToken());
        }

        //将token及用户相关信息 放到Redis中
        UserToken userToken = setToken(enter, sysUser);

        sysUser.setLastLoginToken(userToken.getToken());
        sysUser.setLastLoginTime(new Date(enter.getTimestamp()));
        sysUser.setLastLoginIp(enter.getClientIp());
        sysUser.setUpdatedBy(enter.getUserId());
        sysUser.setUpdatedTime(new Date());

        UpdateWrapper<OpeSysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", sysUser.getId());
        sysUserMapper.update(sysUser, updateWrapper);

        TokenResult result = new TokenResult();
        result.setToken(userToken.getToken());
        result.setRequestId(enter.getRequestId());
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
     * 更换密码
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult modifyPassword(ModifyPasswordEnter enter) {
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
        if (!StringUtils.equals(userToken.getClientType(), enter.getClientType()) || !StringUtils.equals(userToken.getSystemId(), enter.getSystemId()) || !StringUtils.equals(userToken.getAppId(),
                enter.getAppId())) {
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
        sysUserProfile.setRole("staff");
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
        } catch (IllegalAccessException e) {
            log.error("checkToken IllegalAccessException sessionMap:" + map, e);
        } catch (InvocationTargetException e) {
            log.error("checkToken IllegalAccessException sessionMap:" + map, e);
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
}
