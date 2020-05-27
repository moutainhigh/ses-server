package com.redescooter.ses.web.ros.service.website.impl;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import com.redescooter.ses.web.ros.vo.website.SignUpEnter;
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

/**
 * @ClassName:WebsiteServiceImpl
 * @description: WebsiteServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 12:00
 */
@Slf4j
@Service
public class WebsiteTokenServiceImpl implements WebSiteTokenService {

  @Autowired
  private OpeCustomerService opeCustomerService;

  @Autowired
  private JedisCluster jedisCluster;

  @Reference
  private IdAppService idAppService;

  @Autowired
  private OpeCustomerMapper opeCustomerMapper;

  @Reference
  private MailMultiTaskService mailMultiTaskService;

  /**
   * 登录
   *
   * @param enter
   * @return
   */
  @Transactional
  @Override
  public TokenResult login(LoginEnter enter) {
    //入参对象去空格
    SesStringUtils.objStringTrim(enter);

    //用户校验
    QueryWrapper<OpeCustomer> opeCustomerQueryWrapper = new QueryWrapper<>();
    opeCustomerQueryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getLoginName());
    opeCustomerQueryWrapper.isNotNull(OpeCustomer.COL_PASSWORD);
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
    opeCustomer.setLastLoginToken(userToken.getToken());
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

    //入参对象去空格
    SesStringUtils.objStringTrim(enter);

    if (StringUtils.isEmpty(enter.getPassword())){
      throw new SesWebRosException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(),ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
    }

    //用户校验
    QueryWrapper<OpeCustomer> opeCustomerQueryWrapper = new QueryWrapper<>();
    opeCustomerQueryWrapper.eq(OpeCustomer.COL_EMAIL, enter.getEmail());
    opeCustomerQueryWrapper.eq(OpeCustomer.COL_CUSTOMER_SOURCE, CustomerSourceEnum.WEBSITE.getValue());
    OpeCustomer opeCustomer = opeCustomerService.getOne(opeCustomerQueryWrapper);
    if (opeCustomer != null) {
      throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
    }
    //密码校验
    int salt = RandomUtils.nextInt(10000, 99999);
    String password = DigestUtils.md5Hex(enter.getPassword() + salt);
    OpeCustomer saveCustomer = new OpeCustomer();
    saveCustomer.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
    saveCustomer.setDr(0);
    saveCustomer.setTenantId(enter.getTenantId());
    saveCustomer.setTimeZone(enter.getTimeZone());

    saveCustomer.setCustomerFirstName(enter.getFirstName());
    saveCustomer.setCustomerLastName(enter.getLastName());
    saveCustomer.setCustomerFullName(new StringBuilder(enter.getFirstName()).append(" ").append(enter.getLastName()).toString());
    saveCustomer.setEmail(enter.getEmail());
    saveCustomer.setPassword(password);
    saveCustomer.setSalt(String.valueOf(salt));
    saveCustomer.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
    saveCustomer.setStatus(CustomerStatusEnum.PREDESTINATE_CUSTOMER.getValue());
    saveCustomer.setUpdatedBy(enter.getUserId());
    saveCustomer.setUpdatedTime(new Date());
    saveCustomer.setCreatedBy(enter.getUserId());
    saveCustomer.setCreatedTime(new Date());
    saveCustomer.setAccountFlag("0");
    opeCustomerService.save(saveCustomer);
    return new GeneralResult(enter.getRequestId());
  }

  /**
   * 官网登录token 校验
   *
   * @param enter
   * @return
   */
  @Override
  public UserToken  checkCustomerToken(GeneralEnter enter) {
    UserToken userToken = getUserToken(enter.getToken());
    if (!StringUtils.equals(userToken.getClientType(), enter.getClientType()) ||
            !StringUtils.equals(userToken.getSystemId(), enter.getSystemId()) ||
            !StringUtils.equals(userToken.getAppId(),
      enter.getAppId())) {
      throw new SesWebRosException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
    }
    return userToken;
  }

  @Override
  public GeneralResult sendEmail(BaseSendMailEnter baseSendMailEnter) {
    String name = baseSendMailEnter.getMail().substring(0, baseSendMailEnter.getMail().indexOf("@"));
    BaseMailTaskEnter enter = new BaseMailTaskEnter();
    enter.setName(name);
    enter.setEvent(MailTemplateEventEnums.FORGET_PSD_SEND_MAIL.getName());
    enter.setSystemId(SystemIDEnums.REDE_SES.getValue());
    enter.setAppId(AppIDEnums.SES_ROS.getSystemId());
//    enter.setToMail(baseSendMailEnter.getMail());
    enter.setEmail(baseSendMailEnter.getMail());
    enter.setRequestId("0");
    enter.setUserId(0L);
    mailMultiTaskService.addMultiMailTask(JSON.toJSONString(enter));
//    mailMultiTaskService.subscriptionPaySucceedSendmail(enter);
    return new GeneralResult(baseSendMailEnter.getRequestId());
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


  @Override
  public GeneralResult resetPassword(WebResetPasswordEnter enter) {
    OpeCustomer customer = opeCustomerMapper.selectById(enter.getUserId());
    if(customer == null){
      throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode());
    }
    int salt = RandomUtils.nextInt(10000, 99999);
    String newPassword = DigestUtils.md5Hex(enter.getNewPassword() + salt);
    customer.setPassword(newPassword);
    customer.setSalt(String.valueOf(salt));
    opeCustomerMapper.updateById(customer);
    return new GeneralResult(enter.getRequestId());
  }


    @Override
    public GeneralResult editCustomer(WebEditCustomerEnter enter) {
      OpeCustomer customer = opeCustomerMapper.selectById(enter.getUserId());
      if(customer == null){
        throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode());
      }
      customer.setAddress(enter.getAddress());
      customer.setTelephone(enter.getTelephone());
      customer.setCustomerFirstName(enter.getFirstName());
      customer.setCustomerLastName(enter.getLastName());
      customer.setCustomerFullName(customer.getContactFirstName()+" "+customer.getCustomerLastName());
      opeCustomerMapper.updateById(customer);
      return new GeneralResult(enter.getRequestId());
    }


}
