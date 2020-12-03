package com.redescooter.ses.admin.dev.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.admin.dev.dm.AdmSysUser;
import com.redescooter.ses.admin.dev.exception.ExceptionCodeEnums;
import com.redescooter.ses.admin.dev.exception.SesAdminDevException;
import com.redescooter.ses.admin.dev.service.base.AdmSysUserService;
import com.redescooter.ses.admin.dev.service.base.AdminTokenService;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.accountType.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Value("${Request.privateKey}")
    private  String privateKey;

    @Transactional
    @Override
    public TokenResult login(LoginEnter enter) {

        //用户名密码去除空格
        enter.setLoginName(SesStringUtils.stringTrim(enter.getLoginName()));

        QueryWrapper<AdmSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(AdmSysUser.COL_LOGIN_NAME, enter.getLoginName());
        wrapper.eq(AdmSysUser.COL_APP_ID, enter.getAppId());
        wrapper.eq(AdmSysUser.COL_SYSTEM_ID, enter.getSystemId());
        wrapper.eq(AdmSysUser.COL_DEF1, SysUserSourceEnum.SYSTEM.getValue());
        wrapper.last("limit 1");
        AdmSysUser sysUser = admSysUserService.getOne(wrapper);
        //用户名验证，及根据用户名未查到改用户，则该用户不存在
        if (sysUser == null) {
            throw new SesAdminDevException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
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
                throw new SesAdminDevException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getMessage());
            }
        }
        if (!password.equals(sysUser.getPassword())) {
            //连续输错3次密码，出现图片验证码，连续输错5次账号冻结1分钟
            Integer errorNum = passWordMistaken(psdErrorKey);
            if(errorNum < 3){
                throw new SesAdminDevException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }else if(errorNum == 3 || errorNum == 4){
                throw new SesAdminDevException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NEED_CODE.getMessage());
            }else {
                throw new SesAdminDevException(ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getCode(), ExceptionCodeEnums.LOGIN_PSD_ERROER_NUM_MANY.getMessage());
            }
        }
        //密码校验通过之后，看看之前有没有输错过，有的话，从缓存清除
        if(jedisCluster.exists(psdErrorKey)){
            jedisCluster.del(psdErrorKey);
        }
        TokenResult result = getTokenResult(enter, sysUser);
        return result;
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


    private TokenResult getTokenResult(LoginEnter enter, AdmSysUser sysUser) {
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
        if(Strings.isNullOrEmpty(sysUser.getLastLoginToken()) && !sysUser.getLoginName().equals(Constant.ADMIN_USER_NAME)){
            // 如果上次登陆的token为空  则需要重置密码 放在缓存里 在获取用户信息那个接口返回去(系统自动生成的账号排除在外)
            String key = JedisConstant.FIRST_LOGIN_RESET_PSD + sysUser.getId();
            Map<String,String> map = new HashMap<>();
            map.put("flag","1");
            jedisCluster.hmset(key, map);
        }
        //获取用户角色,更新至缓存
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
