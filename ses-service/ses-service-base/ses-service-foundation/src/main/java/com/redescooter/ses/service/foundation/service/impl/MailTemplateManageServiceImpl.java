package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.proxy.mail.MailConfigStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.email.EmailListEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailTemplateManageService;
import com.redescooter.ses.api.foundation.vo.mail.*;
import com.redescooter.ses.service.foundation.config.FoundationAssert;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.base.PlaMailTemplateMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTemplate;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaMailConfigService;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:23 下午
 * @ClassName: MailTemplateManageServiceImpl
 * @Function: TODO
 */
@Slf4j
@DubboService
public class MailTemplateManageServiceImpl implements MailTemplateManageService {

    @Autowired
    private PlaMailTemplateMapper mailTemplateMapper;

    @Autowired
    private PlaMailConfigService plaMailConfigService;

    @Autowired
    private IdAppService idSerService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(UpdateMailTemplateEnter enter) {
        // 编号不可重复
        LambdaQueryWrapper<PlaMailTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PlaMailTemplate::getDr, 0);
        if (null != enter.getId()) {
            wrapper.ne(PlaMailTemplate::getId, enter.getId());
        }
        List<PlaMailTemplate> list = mailTemplateMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            Set<Integer> noSet = list.stream().map(PlaMailTemplate::getMailTemplateNo).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(noSet)) {
                if (noSet.contains(enter.getMailTemplateNo())) {
                    throw new FoundationException(ExceptionCodeEnums.NUMBER_NOT_REPEAT.getCode(), ExceptionCodeEnums.NUMBER_NOT_REPEAT.getMessage());
                }
            }
        }

        PlaMailTemplate mailTemplate = new PlaMailTemplate();
        BeanUtils.copyProperties(enter, mailTemplate);
        if (enter.getId() == null || enter.getId() == 0) {
            mailTemplate.setId(idSerService.getId(SequenceName.PLA_MAIL_TEMPLATE));
        }
        mailTemplate.setStatus(MailTemplateStatusEnums.NORMAL.getCode());
        mailTemplate.setCreatedBy(enter.getUserId());
        mailTemplate.setCreatedTime(new Date());
        mailTemplate.setUpdatedBy(enter.getUserId());
        mailTemplate.setUpdatedTime(new Date());
        mailTemplateMapper.insertOrUpdateSelective(mailTemplate);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 删除邮件模板
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult delete(IdEnter enter) {
        mailTemplateMapper.deleteById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<MailTemplateResult> getMailTemplateList(EmailListEnter enter) {
        List<PlaMailTemplate> list = mailTemplateMapper.getList(enter);
        MailTemplateResult result = null;
        List<MailTemplateResult> resultList = new ArrayList<>();
        for (PlaMailTemplate o : list) {
            result = new MailTemplateResult();
            BeanUtils.copyProperties(o, result);
            resultList.add(result);
        }
        return resultList;
    }

    /**
     * 保存或更新邮件模板参数
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveParameter(SaveMailConfigEnter enter) {

        PlaMailConfig config = new PlaMailConfig();

        if (enter.getId() == 0 || enter.getId() == null) {
            //新增
            config.setId(idSerService.getId(SequenceName.PLA_MAIL_CONFIG));
            config.setDr(Constant.DR_FALSE);
            config.setStatus(MailConfigStatusEnums.NORMAL.getCode());
            config.setMailTemplateNo(enter.getMailTemplateNo());
            config.setCreatedBy(enter.getUserId());
            config.setCreatedTime(new Date());
        } else {
            //更新
            config.setId(enter.getId());
        }
        if (StringUtils.isNoneBlank(
                String.valueOf(enter.getMailTemplateNo()),
                enter.getMailSystemId(),
                enter.getMailAppId(),
                enter.getParamKey(),
                enter.getParamValue())) {
            throw new FoundationException(ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getMessage());
        }

        config.setSystemId(enter.getMailSystemId());
        config.setAppId(enter.getMailAppId());
        config.setParamKey(enter.getParamKey());
        config.setParamValue(enter.getParamValue());
        config.setUpdatedBy(enter.getUserId());
        config.setUpdatedTime(new Date());
        plaMailConfigService.saveOrUpdate(config);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 删除邮件参数
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult deleteParameter(IdEnter enter) {

        plaMailConfigService.removeById(enter.getId());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 邮件模板参数列表
     *
     * @param enter
     * @return
     */
    @Override
    public MailTemplateConfigResult listParameter(QueryMailConfigEnter enter) {
        //参数封装
        MailTemplateConfigResult result = new MailTemplateConfigResult();
        MailTemplateSystemInfoResult systemInfo = new MailTemplateSystemInfoResult();
        List<MailTemplateSystemInfoResult> systemInfoList = new ArrayList<>();

        PlaMailTemplate mailTemplate = mailTemplateMapper.selectOne(new QueryWrapper<PlaMailTemplate>()
                .eq(PlaMailTemplate.COL_MAIL_TEMPLATE_NO, enter.getMailTemplateNo()));

        FoundationAssert.isNull(mailTemplate, ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getMessage());

        //保存邮件模板
        result.setId(mailTemplate.getId());
        result.setMailTemplateNo(mailTemplate.getMailTemplateNo());
        result.setEvent(mailTemplate.getEvent());
        result.setName(mailTemplate.getName());
        result.setSubject(mailTemplate.getSubject());

        List<PlaMailConfig> configs = plaMailConfigService.list(new QueryWrapper<PlaMailConfig>()
                .eq(PlaMailConfig.COL_MAIL_TEMPLATE_NO, enter.getMailTemplateNo()));

        if (configs.size() > 0) {

            for (PlaMailConfig config : configs) {
                if (systemInfoList.size() == 0) {
                    List<MailTemplateParameterInfoResult> parameterInfoList = new ArrayList<>();
                    //1.新建邮件模板所属系统
                    systemInfo = new MailTemplateSystemInfoResult();
                    //系统编码，格式：systemId::appId
                    systemInfo.setCode(new StringBuffer().append(config.getSystemId()).append("::").append(config.getAppId()).toString());
                    systemInfo.setSystemId(config.getSystemId());
                    systemInfo.setAppId(config.getAppId());

                    //2.创建系统邮件模板所属参数
                    MailTemplateParameterInfoResult parameterInfo = new MailTemplateParameterInfoResult();
                    parameterInfo.setId(config.getId());
                    parameterInfo.setParamKey(config.getParamKey());
                    parameterInfo.setParamValue(config.getParamValue());
                    parameterInfoList.add(parameterInfo);

                    systemInfo.setParameterInfoList(parameterInfoList);
                    systemInfoList.add(systemInfo);
                    result.setSystemInfoList(systemInfoList);
                } else {
                    for (int i = 0; i < systemInfoList.size(); i++) {
                        MailTemplateSystemInfoResult systemInfoResult = systemInfoList.get(i);
                        if (systemInfoResult.getCode().equals(new StringBuffer().append(config.getSystemId()).append("::").append(config.getAppId()).toString())) {
                            //2.1.创建系统邮件模板所属参数
                            MailTemplateParameterInfoResult newParameterInfo = new MailTemplateParameterInfoResult();
                            newParameterInfo.setId(config.getId());
                            newParameterInfo.setParamKey(config.getParamKey());
                            newParameterInfo.setParamValue(config.getParamValue());
                            systemInfoResult.getParameterInfoList().add(newParameterInfo);
                        } else {
                            List<MailTemplateParameterInfoResult> parameterInfoList = new ArrayList<>();
                            //1.新建邮件模板所属系统
                            systemInfo = new MailTemplateSystemInfoResult();
                            //系统编码，格式：systemId::appId
                            systemInfo.setCode(new StringBuffer().append(config.getSystemId()).append("::").append(config.getAppId()).toString());
                            systemInfo.setSystemId(config.getSystemId());
                            systemInfo.setAppId(config.getAppId());

                            //2.创建系统邮件模板所属参数
                            MailTemplateParameterInfoResult parameterInfo = new MailTemplateParameterInfoResult();
                            parameterInfo.setId(config.getId());
                            parameterInfo.setParamKey(config.getParamKey());
                            parameterInfo.setParamValue(config.getParamValue());
                            parameterInfoList.add(parameterInfo);

                            systemInfo.setParameterInfoList(parameterInfoList);
                        }
                        systemInfoList.add(systemInfo);
                        result.setSystemInfoList(systemInfoList);

                    }
                }
            }
        }

        return result;
    }

}
