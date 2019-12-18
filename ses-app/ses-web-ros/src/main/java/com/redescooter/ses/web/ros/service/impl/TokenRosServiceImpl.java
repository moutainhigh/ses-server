package com.redescooter.ses.web.ros.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.ros.account.AccountStatus;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.proxy.vo.mail.SendMailEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserMapper;
import com.redescooter.ses.web.ros.dao.base.OpeSysUserProfileMapper;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.TokenRosService;
import com.redescooter.ses.web.ros.vo.AddSysUserEnter;
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

        return null;
    }

    /**
     * 用户注销
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult logout(GeneralEnter enter) {
        return null;
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
    public GeneralResult sendCode(SendMailEnter enter) {
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

        if(opeSysUser.getLoginName().equals(Constant.ADMIN_USER_NAME)){
            throw new SesWebRosException(ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getCode(), ExceptionCodeEnums.INSUFFICIENT_PERMISSIONS.getMessage());
        }

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
        sysUser.setStatus(AccountStatus.NORMAL.getCode());
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
}
