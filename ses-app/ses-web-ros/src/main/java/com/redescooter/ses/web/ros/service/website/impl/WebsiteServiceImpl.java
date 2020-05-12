package com.redescooter.ses.web.ros.service.website.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.website.WebSiteService;
import com.redescooter.ses.web.ros.vo.website.SignUpEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
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

/**
 * @ClassName:WebsiteServiceImpl
 * @description: WebsiteServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 12:00
 */
@Slf4j
@Service
public class WebsiteServiceImpl implements WebSiteService {

    @Autowired
    private OpeCustomerService opeCustomerService;


    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 登录
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public TokenResult login(LoginEnter enter) {
        //登录信息 去空格
        SesStringUtils.stringTrim(enter.getLoginName());
        SesStringUtils.stringTrim(enter.getPassword());

        //用户校验
        QueryWrapper<OpeCustomer> opeCustomerQueryWrapper = new QueryWrapper<>();
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getLoginName());
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_CUSTOMER_SOURCE, CustomerSourceEnum.WEBSITE.getValue());
        OpeCustomer opeCustomer = opeCustomerService.getOne(opeCustomerQueryWrapper);
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        //密码校验
        String password = DigestUtils.md5Hex(enter.getPassword() + opeCustomer.getSalt());
        if (!StringUtils.equals(password, opeCustomer.getPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        //清楚上次的token
        if (StringUtils.isNotEmpty(opeCustomer.getLastLoginToken())) {
            jedisCluster.del(opeCustomer.getLastLoginToken());
        }
        //设置token
        UserToken userToken = setToken(enter, opeCustomer);

        //登录信息更新
        opeCustomer.setLastLoginToken(enter.getToken());
        opeCustomer.setUpdatedBy(enter.getUserId());
        opeCustomer.setUpdatedTime(new Date());
        opeCustomerService.updateById(opeCustomer);

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
    @Transactional
    @Override
    public GeneralResult logout(GeneralEnter enter) {
        String token = enter.getToken();
        jedisCluster.del(token);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 用户注册
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult signUp(SignUpEnter enter) {

        return null;
    }

    /**
     * 官网登录token 校验
     *
     * @param enter
     * @return
     */
    @Override
    public UserToken checkCustomerToken(GeneralEnter enter) {
        return null;
    }

    /**
     * 设置登录的token
     *
     * @param enter
     * @param opeCustomer
     * @return
     */
    private UserToken setToken(LoginEnter enter, OpeCustomer opeCustomer) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(opeCustomer.getId());
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
