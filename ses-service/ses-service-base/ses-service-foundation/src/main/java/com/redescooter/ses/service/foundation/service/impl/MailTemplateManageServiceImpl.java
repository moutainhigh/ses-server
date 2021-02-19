package com.redescooter.ses.service.foundation.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
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
                enter.getMailAppId(),
                enter.getParamKey(),
                enter.getParamValue())) {
            throw new FoundationException(ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getMessage());
        }

        config.setSystemId(AppIDEnums.getSystemId(enter.getMailAppId()));
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

        //系统集合
        List<MailTemplateSystemInfoResult> systemInfoList = new ArrayList<>();

        //获取邮件模板
        PlaMailTemplate mailTemplate = mailTemplateMapper.selectOne(new QueryWrapper<PlaMailTemplate>()
                .eq(PlaMailTemplate.COL_MAIL_TEMPLATE_NO, enter.getMailTemplateNo()));

        FoundationAssert.isNull(mailTemplate, ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getMessage());

        //保存邮件模板主体信息
        result.setId(mailTemplate.getId());
        result.setMailTemplateNo(mailTemplate.getMailTemplateNo());
        result.setEvent(mailTemplate.getEvent());
        result.setName(mailTemplate.getName());
        result.setSubject(mailTemplate.getSubject());

        //根据邮件编号，查询对应参数列表
        List<PlaMailConfig> list = plaMailConfigService.list(new QueryWrapper<PlaMailConfig>()
                .eq(PlaMailConfig.COL_MAIL_TEMPLATE_NO, enter.getMailTemplateNo()));

        FoundationAssert.isNull(list, ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getMessage());

        //根据系统id和应用id分组
        Map<String, List<PlaMailConfig>> mapGroup = list.stream().collect(Collectors.groupingBy(plaMailConfig -> plaMailConfig.getSystemId() + "::" + plaMailConfig.getAppId()));

        if (!mapGroup.isEmpty()) {
            //结果封装
            mapGroup.forEach((k, v) -> {
                //邮件所属系统
                MailTemplateSystemInfoResult systemInfo = new MailTemplateSystemInfoResult();
                systemInfo.setCode(k);
                systemInfo.setSystemId(v.get(0).getSystemId());
                systemInfo.setAppId(v.get(0).getAppId());
                systemInfo.setRequestId(enter.getRequestId());
                //装在系统集合
                List<MailTemplateParameterInfoResult> parameterInfoList = new ArrayList<>();
                v.forEach(p -> {
                    MailTemplateParameterInfoResult infoResult = new MailTemplateParameterInfoResult();
                    infoResult.setId(p.getId());
                    infoResult.setParamKey(p.getParamKey());
                    infoResult.setParamValue(p.getParamValue());
                    infoResult.setRequestId(enter.getRequestId());
                    parameterInfoList.add(infoResult);
                });
                systemInfo.setParameterInfoList(parameterInfoList);
                systemInfoList.add(systemInfo);
            });
        }
        result.setSystemInfoList(systemInfoList);
        result.setRequestId(enter.getRequestId());
        return result;
    }

}
