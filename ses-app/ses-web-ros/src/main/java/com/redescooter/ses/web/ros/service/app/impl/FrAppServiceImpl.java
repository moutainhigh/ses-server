package com.redescooter.ses.web.ros.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeExMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.enums.distributor.StatusEnum;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.app.FrAppService;
import com.redescooter.ses.web.ros.service.base.OpeWarehouseAccountService;
import com.redescooter.ses.web.ros.vo.app.AppLoginEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryDetailResult;
import com.redescooter.ses.web.ros.vo.app.InquiryListAppEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryListResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 13:57
 */
@Service
@Slf4j
public class FrAppServiceImpl implements FrAppService {

    @Autowired
    private OpeWarehouseAccountService opeWarehouseAccountService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;

    @Autowired
    private OpeCarDistributeExMapper opeCarDistributeExMapper;

    @Value("${Request.privateKey}")
    private String privateKey;

    /**
     * 登录
     */
    @Override
    public TokenResult login(AppLoginEnter enter) {
        LambdaQueryWrapper<OpeWarehouseAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeWarehouseAccount::getDr, Constant.DR_FALSE);
        qw.eq(OpeWarehouseAccount::getStatus, StatusEnum.ENABLE.getCode());
        qw.eq(OpeWarehouseAccount::getSystem, 1);
        qw.eq(OpeWarehouseAccount::getEmail, enter.getEmail());
        qw.last("limit 1");
        OpeWarehouseAccount account = opeWarehouseAccountService.getOne(qw);
        if (null == account) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        // 密码解密
        String decryptPassword;
        try {
            decryptPassword = RsaUtils.decrypt(enter.getPassword(), privateKey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        // 拿着解密后的密码再次md5加密,和db存储的md5加密的密码相比较
        String encryptPassword = DigestUtils.md5Hex(decryptPassword + account.getSalt());
        if (!StringUtils.equals(encryptPassword, account.getPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(account.getId());
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
            Map<String, String> map = BeanUtils.describe(userToken);
            map.remove("requestId");
            map.remove("refreshToken");
            map.remove("deptId");

            jedisCluster.hmset(token, map);
            jedisCluster.expire(token, new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());
        } catch (Exception ex) {
            log.error("设置token失败" + ex);
        }

        TokenResult result = new TokenResult();
        result.setToken(token);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 登出
     */
    @Override
    public GeneralResult logout(GeneralEnter enter) {
        String token = enter.getToken();
        jedisCluster.del(token);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 获得个人信息
     */
    @Override
    public OpeWarehouseAccount getUserInfo(GeneralEnter enter) {
        OpeWarehouseAccount account = opeWarehouseAccountService.getById(enter.getUserId());
        if (null == account) {
            throw new SesWebRosException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }
        return account;
    }

    /**
     * 询价单列表
     */
    @Override
    public PageResult<InquiryListResult> getList(InquiryListAppEnter enter) {
        int count = opeCarDistributeExMapper.getInquiryListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InquiryListResult> list = opeCarDistributeExMapper.getInquiryList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 询价单详情
     */
    @Override
    public InquiryDetailResult getDetail(IdEnter enter) {
        return null;
    }


}
