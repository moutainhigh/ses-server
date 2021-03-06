package com.redescooter.ses.mobile.rps.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redescooter.ses.api.common.constant.CacheConstants;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.mobile.rps.dao.base.OpeSysUserMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeSysUserProfileMapper;
import com.redescooter.ses.mobile.rps.dm.OpeSysRpsUser;
import com.redescooter.ses.mobile.rps.dm.OpeSysUser;
import com.redescooter.ses.mobile.rps.dm.OpeSysUserRole;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.TokenRpsService;
import com.redescooter.ses.mobile.rps.service.base.OpeSysRpsUserService;
import com.redescooter.ses.mobile.rps.service.base.OpeSysUserRoleService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.SesStringUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class TokenRpsServiceImpl implements TokenRpsService {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private OpeSysUserMapper sysUserMapper;

    @Autowired
    private OpeSysUserProfileMapper sysUserProfileMapper;

    @Autowired
    private OpeSysUserRoleService sysUserRoleService;

    @Autowired
    private OpeSysRpsUserService opeSysRpsUserService;

    @DubboReference
    private IdAppService idAppService;

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
        enter.setPassword(SesStringUtils.stringTrim(enter.getPassword()));

        QueryWrapper<OpeSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(OpeSysUser.COL_DR, 0);
        //todo ??????????????????????????? ????????????????????????
//        wrapper.eq(OpeSysUser.COL_APP_ID, enter.getAppId());
//        wrapper.eq(OpeSysUser.COL_SYSTEM_ID, enter.getSystemId());
        wrapper.eq(OpeSysUser.COL_APP_ID, 4);
        wrapper.eq(OpeSysUser.COL_SYSTEM_ID, "REDE_SES");
        wrapper.eq(OpeSysUser.COL_DEF1, SysUserSourceEnum.SYSTEM.getValue());

        OpeSysUser sysUser = sysUserMapper.selectOne(wrapper);
        //??????????????????????????????????????????????????????????????????????????????
        if (sysUser == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (!enter.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            List<OpeSysUserRole> roles = sysUserRoleService.list(new LambdaQueryWrapper<OpeSysUserRole>().eq(OpeSysUserRole::getUserId, sysUser.getId()));
            if (!CollUtil.isNotEmpty(roles)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
            }
        }
        //????????????
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.LOCK.getCode())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getCode(), ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getMessage());
        }
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.CANCEL.getCode())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ACCOUNT_CANCELLED.getCode(), ExceptionCodeEnums.ACCOUNT_CANCELLED.getMessage());
        }
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.EXPIRED.getCode())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ACCOUNT_EXPIRED.getCode(), ExceptionCodeEnums.ACCOUNT_EXPIRED.getMessage());
        }
        String password = DigestUtils.md5Hex(enter.getPassword() + sysUser.getSalt());

        if (!password.equals(sysUser.getPassword())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        //??????????????????token??????
        if (StringUtils.isNotBlank(sysUser.getLastLoginToken())) {
            jedisCluster.del(sysUser.getLastLoginToken());
        }
        //???token????????????????????? ??????Redis???
        UserToken userToken = setToken(enter, sysUser);
        //??????????????????,???????????????
        //  setAuth(userRole.getRoleId());

        sysUser.setLastLoginToken(userToken.getToken());
        sysUser.setLastLoginTime(new Date(enter.getTimestamp()));
        sysUser.setLastLoginIp(enter.getClientIp());
        sysUser.setUpdatedBy(enter.getUserId());
        sysUser.setUpdatedTime(new Date());

        UpdateWrapper<OpeSysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(OpeSysUser.COL_ID, sysUser.getId());
        sysUserMapper.update(sysUser, updateWrapper);

        TokenResult result = new TokenResult();
        result.setToken(userToken.getToken());
        result.setRequestId(enter.getRequestId());
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult modifyPassword(ModifyPasswordEnter enter) {
        return null;
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
            throw new SesMobileRpsException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
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


    private UserToken getUserToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(token);
        if (map == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
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
        userToken.setDeptId(user.getDeptId() == null ? 0L : user.getDeptId());
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

    private void setAuth(long roleId) {
        String key = new StringBuilder().append(roleId).append(":::").append(CacheConstants.MENU_DETAILS).toString();
        Boolean aBoolean = jedisCluster.exists(key);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public TokenResult rpsLogin(LoginEnter enter) {
        //???????????????????????????
        enter.setLoginName(SesStringUtils.stringTrim(enter.getLoginName()));
        enter.setPassword(SesStringUtils.stringTrim(enter.getPassword()));

        QueryWrapper<OpeSysRpsUser> wrapper = new QueryWrapper<>();
        wrapper.eq(OpeSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.last("limit 1");
        OpeSysRpsUser sysUser = opeSysRpsUserService.getOne(wrapper);
        //??????????????????????????????????????????????????????????????????????????????
        if (sysUser == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //????????????
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.LOCK.getCode())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getCode(), ExceptionCodeEnums.THE_ACCOUNT_HAS_BEEN_FROZEN.getMessage());
        }
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.CANCEL.getCode())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ACCOUNT_CANCELLED.getCode(), ExceptionCodeEnums.ACCOUNT_CANCELLED.getMessage());
        }
        if (StringUtils.equals(sysUser.getStatus(), SysUserStatusEnum.EXPIRED.getCode())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ACCOUNT_EXPIRED.getCode(), ExceptionCodeEnums.ACCOUNT_EXPIRED.getMessage());
        }
        String password = DigestUtils.md5Hex(enter.getPassword() + sysUser.getSalt());

        if (!password.equals(sysUser.getPassword())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        //??????????????????token??????
        if (StringUtils.isNotBlank(sysUser.getLastLoginToken())) {
            jedisCluster.del(sysUser.getLastLoginToken());
        }
        //???token????????????????????? ??????Redis???
        OpeSysUser user = new OpeSysUser();
        org.springframework.beans.BeanUtils.copyProperties(sysUser, user);
        UserToken userToken = setToken(enter, user);
        //??????????????????,???????????????
        //  setAuth(userRole.getRoleId());

        sysUser.setLastLoginToken(userToken.getToken());
        sysUser.setLastLoginTime(new Date(enter.getTimestamp()));
        sysUser.setLastLoginIp(enter.getClientIp());
        sysUser.setUpdatedBy(enter.getUserId());
        sysUser.setUpdatedTime(new Date());

        opeSysRpsUserService.saveOrUpdate(sysUser);
        TokenResult result = new TokenResult();
        result.setToken(userToken.getToken());
        result.setRequestId(enter.getRequestId());
        return result;
    }
}
