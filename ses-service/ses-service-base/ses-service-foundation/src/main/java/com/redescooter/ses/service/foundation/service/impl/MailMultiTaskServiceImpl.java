package com.redescooter.ses.service.foundation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.mail.MailTemplateEventEnum;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTaskStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.vo.account.FreezeWarnWebTaskEnter;
import com.redescooter.ses.api.foundation.vo.account.NnfreezeWarnWebTaskEnter;
import com.redescooter.ses.api.foundation.vo.account.RenewalWarnWebTaskEnter;
import com.redescooter.ses.api.foundation.vo.login.SetPasswordMobileUserTaskEnter;
import com.redescooter.ses.api.proxy.service.IMailService;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.base.PlaMailConfigMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaMailTaskMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaMailTemplateMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTask;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTemplate;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 11:46 上午
 * @ClassName: MailMultiTaskServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class MailMultiTaskServiceImpl implements MailMultiTaskService {

    @Autowired
    private PlaMailConfigMapper mailConfigMapper;
    @Autowired
    private PlaMailTaskMapper mailTaskMapper;
    @Autowired
    private PlaMailTemplateMapper mailTemplateMapper;
    @Autowired
    private IdAppService idSerService;
    @Autowired
    private JedisCluster jedisCluster;
    @Reference
    private IMailService iMailService;

    /**
     * Mobile激活邮件任务
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addActivateMobileUserTask(BaseMailTaskEnter enter) {

        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * Web激活邮件任务
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addActivateWebUserTask(BaseMailTaskEnter enter) {
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * Mobile设置密码邮件任务
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addSetPasswordMobileUserTask(SetPasswordMobileUserTaskEnter enter) {
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        String key = "verificationCode";
        if (!map.containsKey(key)) {
            map.put(key, enter.getCode());
        } else {
            if (map.get(key).isEmpty()) {
                map.put(key, enter.getCode());
            }
        }

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());

    }

    /**
     * Web设置密码邮件任务
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addSetPasswordWebUserTask(BaseMailTaskEnter enter) {
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * Mobile权限开通邮件任务
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addPermissionWarnMobileTask(BaseMailTaskEnter enter) {
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * WEB冻结通知邮件任务
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addFreezeWarnWebTask(FreezeWarnWebTaskEnter enter) {
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        String key = "freezeDate";
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(enter.getFreezeDate()));
        } else {
            if (map.get(key).isEmpty()) {
                map.put(key, String.valueOf(enter.getFreezeDate()));
            }
        }

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * WEB续约通知邮件任务
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addRenewalWarnWebTask(RenewalWarnWebTaskEnter enter) {
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        String key = "renewalDate";
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(enter.getRenewalDate()));
        } else {
            if (map.get(key).isEmpty()) {
                map.put(key, String.valueOf(enter.getRenewalDate()));
            }
        }

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * WEB解冻通知邮件任务
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addNnfreezeWarnWebTask(NnfreezeWarnWebTaskEnter enter) {
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        String key1 = "unfreezeStatDate";
        if (!map.containsKey(key1)) {
            map.put(key1, String.valueOf(enter.getUnfreezeStatDate()));
        } else {
            if (map.get(key1).isEmpty()) {
                map.put(key1, String.valueOf(enter.getUnfreezeStatDate()));
            }
        }
        String key2 = "unfreezeEndDate";
        if (!map.containsKey(key2)) {
            map.put(key2, String.valueOf(enter.getUnfreezeEndDate()));
        } else {
            if (map.get(key2).isEmpty()) {
                map.put(key2, String.valueOf(enter.getUnfreezeEndDate()));
            }
        }

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * WEB过期通知邮件任务
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addExpiredWarnWebTask(BaseMailTaskEnter enter) {
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 客户询价单尾款支付
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult addCustomerInquiryPayDepositTask(BaseMailTaskEnter enter) {
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }


  /**
   * 客户询价单尾款支付
   * @param enter 尾款
   * @return
   */
    @Override
    public GeneralResult addCustomerInquiryPayLastParagraphTask(BaseMailTaskEnter enter){

      PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        Map<String, String> map = getParameterMap(mailtemplate.getMailTemplateNo(), enter.getMailSystemId(), enter.getMailAppId(), enter.getUserRequestId(), enter.getName(), enter.getToUserId(), enter.getToMail());

        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult(enter.getRequestId());
    }

  /**
   * 支付
   *
   * @param enter@return
   */
  @Override
  public GeneralResult subscriptionPaySucceedSendmail(BaseMailTaskEnter enter) {
    //获取模板
    PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
    //将模板赋值
    List<PlaMailConfig> configList = getPayTemplateById(mailtemplate.getMailTemplateNo());
    Map<String, String> map = new HashMap();
    if (configList != null && configList.size() > 0) {
      map = configList.stream().collect(Collectors.toMap(PlaMailConfig::getParamKey, MailConfig -> MailConfig.getParamValue() == null ? "" : (MailConfig.getParamValue()), (a, b) -> b));
    }
    map.put("name", enter.getFullName());
    map.put("model", enter.getModel());
    //保存邮箱任务
    PlaMailTask mailTask = new PlaMailTask();
    mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
    mailTask.setSubject(mailtemplate.getSubject());
    mailTask.setParameter(JSON.toJSONString(map));
    mailTask.setContent(mailtemplate.getContent());

    mailTask = saveTask(mailTask, enter);
    //触发该邮件的发送
    runTaskById(mailTask.getId());

    return new GeneralResult(enter.getRequestId());
  }

    /**
     * 定金支付成功发送邮件
     *
     * @param enter@return
     */
    @Override
    public GeneralResult subscribeToEmailSuccessfully(BaseMailTaskEnter enter) {
        //获取模板
        PlaMailTemplate mailtemplate = getTemplateByEvent(enter.getEvent());
        //将模板赋值
        List<PlaMailConfig> configList = getPayTemplateById(mailtemplate.getMailTemplateNo());
        Map<String, String> map = new HashMap();
        if (configList != null && configList.size() > 0) {
            map = configList.stream().collect(Collectors.toMap(PlaMailConfig::getParamKey, MailConfig -> MailConfig.getParamValue() == null ? "" : (MailConfig.getParamValue()), (a, b) -> b));
        }
        map.put("name", enter.getName());
        //保存邮箱任务
        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(mailtemplate.getContent());

        mailTask = saveTask(mailTask, enter);
        //触发该邮件的发送
        runTaskById(mailTask.getId());
        return null;
    }

    /**
     * 多系统多维度添加邮件任务
     *
     * @return
     */
    @Override
    public GeneralResult addMultiMailTask(BaseMailTaskEnter enter) {
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(enter), Map.class);
        if (!map.containsKey("event") ||
                !map.containsKey("requestId") ||
                !map.containsKey("systemId") ||
                !map.containsKey("appId") ||
                !map.containsKey("name") ||
                !map.containsKey("userId") ||
                !map.containsKey("email")) {
            log.info("=========================================");
            log.info("=========任务添加失败，必须参数缺失==========");
            log.info("=========================================");
            return new GeneralResult();
        }

        String systemId = map.get("systemId");
        String appId = map.get("appId");
        String requestId = map.get("requestId");
        String email = map.get("email");
        Long userId = enter.getUserId();
        String name = map.get("name");

        PlaMailTemplate mailtemplate = getTemplateByEvent(map.get("event"));
        Map<String, String> mapParameter = getParameterMap(mailtemplate.getMailTemplateNo(), systemId, appId, requestId, name, userId, email);
        map.putAll(mapParameter == null ? new HashMap<>() : mapParameter);
        PlaMailTask mailTask = new PlaMailTask();
        mailTask.setId(idSerService.getId(SequenceName.PLA_MAIL_TASK));
        mailTask.setStatus(MailTaskStatusEnums.PENDING.getCode());
        mailTask.setMailTemplateNo(mailtemplate.getMailTemplateNo());
        mailTask.setSystemId(systemId);
        mailTask.setAppId(appId);
        mailTask.setRequestId(requestId);
        mailTask.setReceiveMail(map.get("email"));
        mailTask.setToUserId(userId);
        mailTask.setSubject(mailtemplate.getSubject());
        mailTask.setParameter(JSON.toJSONString(map));
        mailTask.setContent(getContent(map, mailtemplate));
        mailTask.setCreatedTime(new Date());
        mailTask.setUpdatedTime(new Date());
        mailTaskMapper.insert(mailTask);
        pullResdis(mailTask, mailtemplate.getExpire());

        return new GeneralResult();
    }


    /**
     * 执行所有任务
     *
     * @return
     */
    @Override
    public GeneralResult runAllTask() {
        QueryWrapper<PlaMailTask> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaMailTask.COL_STATUS, MailTaskStatusEnums.PENDING.getCode());
        List<PlaMailTask> mailTasks = mailTaskMapper.selectList(wrapper);

        //轮训发送，提高稳定性
        for (PlaMailTask mailTask : mailTasks) {
            Map<String, String> map = JSON.parseObject(mailTask.getParameter(), Map.class);
            iMailService.sendHtmlMail(mailTask.getReceiveMail(), mailTask.getSubject(), getContent(map, getTemplateByNo(mailTask.getMailTemplateNo())));
            mailTask.setStatus(MailTaskStatusEnums.SUCCESS.getCode());
        }
        if (mailTasks != null && mailTasks.size() > 0) {
            mailTaskMapper.updateBatch(mailTasks);
        }
        return new GeneralResult();
    }

    @Override
    public void runTaskById(long id) {

        PlaMailTask mailTask = mailTaskMapper.selectById(id);

        Optional.ofNullable(mailTask).ifPresent(email -> {
            Map<String, String> map = JSON.parseObject(email.getParameter(), Map.class);
            iMailService.sendHtmlMail(email.getReceiveMail(), email.getSubject(), getContent(map, getTemplateByNo(email.getMailTemplateNo())));
            email.setStatus(MailTaskStatusEnums.SUCCESS.getCode());
            email.setUpdatedTime(new Date());
            mailTaskMapper.updateById(email);
        });
    }

    private Map<String, String> getParameterMap(int mailTemplateNo, String systemId, String appId, String requestId, String name, Long userId, String email) {

        List<PlaMailConfig> configList = getTemplateById(mailTemplateNo, systemId, appId);
        Map<String, String> map = new HashMap();
        if (configList != null && configList.size() > 0) {
            map = configList.stream().collect(Collectors.toMap(PlaMailConfig::getParamKey, MailConfig -> MailConfig.getParamValue() == null ? "" : (MailConfig.getParamValue()), (a, b) -> b));
        }
        //默认必须有的参数
        map.put("requestId", requestId);
        map.put("name", name);
        map.put("userId", String.valueOf(userId));
        map.put("email", email);
        map.put("systemId", systemId);
        map.put("appId", appId);
        return map;
    }


    private PlaMailTemplate getTemplateByEvent(String event) {
        QueryWrapper<PlaMailTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaMailTemplate.COL_EVENT, event);
        return mailTemplateMapper.selectOne(wrapper);
    }

    private PlaMailTemplate getTemplateByNo(Integer templateNo) {
        QueryWrapper<PlaMailTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaMailTemplate.COL_MAIL_TEMPLATE_NO, templateNo);
        return mailTemplateMapper.selectOne(wrapper);
    }

    private List<PlaMailConfig> getTemplateById(Integer mailTemplateNo, String systemId, String appId) {
        QueryWrapper<PlaMailConfig> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaMailConfig.COL_MAIL_TEMPLATE_NO, mailTemplateNo);
        wrapper.eq(PlaMailConfig.COL_SYSTEM_ID, systemId);
        wrapper.eq(PlaMailConfig.COL_APP_ID, appId);
        return mailConfigMapper.selectList(wrapper);
    }
  private List<PlaMailConfig> getPayTemplateById(Integer mailTemplateNo) {
    QueryWrapper<PlaMailConfig> wrapper = new QueryWrapper<>();
    wrapper.eq(PlaMailConfig.COL_MAIL_TEMPLATE_NO, mailTemplateNo);
    return mailConfigMapper.selectList(wrapper);
  }

    private String getContent(Map map, PlaMailTemplate mailTemplate) {
        if (map == null) {
            map = new HashMap();
        }
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setCacheable(true);
        stringTemplateResolver.setTemplateMode(TemplateMode.HTML);
        springTemplateEngine.setTemplateResolver(stringTemplateResolver);

        Context context = new Context();
        context.setVariables(map);
        return springTemplateEngine.process(mailTemplate.getContent(), context);
    }

    /**
     * 保存邮件任务
     */
    private PlaMailTask saveTask(PlaMailTask mailTask, BaseMailTaskEnter enter) {
        mailTask.setId(idSerService.getId(SequenceName.PLA_MAIL_TASK));
        mailTask.setStatus(MailTaskStatusEnums.PENDING.getCode());
        mailTask.setSystemId(enter.getMailSystemId());
        mailTask.setAppId(enter.getMailAppId());
        mailTask.setRequestId(enter.getUserRequestId());
        mailTask.setReceiveMail(enter.getToMail());
        mailTask.setToUserId(enter.getToUserId());
        mailTask.setCreatedBy(enter.getUserId());
        mailTask.setUpdatedBy(enter.getUserId());
        mailTask.setCreatedTime(new Date());
        mailTask.setUpdatedTime(new Date());
        mailTaskMapper.insert(mailTask);
        return mailTask;
    }
    /**
     * 验证数据到缓存中
     *
     * @param mailTask
     * @param seconds
     * @return
     */
    private void pullResdis(PlaMailTask mailTask, int seconds) {
        String sendParameter = mailTask.getParameter();
        Map<String, String> map = JSONObject.parseObject(sendParameter, Map.class);
        //String key = new StringBuffer().append("activation:::").append(map.get("email")).append("systemId").toString();
        String key = map.get("requestId");
        jedisCluster.hmset(key, map);
        //默认为72小时
        jedisCluster.expire(key, seconds);
        //触发该邮件的发送
        runTaskById(mailTask.getId());

    }
}
