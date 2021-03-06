package com.redescooter.ses.service.foundation.service.impl;

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
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateConfigResult;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateParameterInfoResult;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateResult;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateSystemInfoResult;
import com.redescooter.ses.api.foundation.vo.mail.QueryMailConfigEnter;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailConfigEnter;
import com.redescooter.ses.api.foundation.vo.mail.UpdateMailTemplateEnter;
import com.redescooter.ses.service.foundation.config.FoundationAssert;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.base.PlaMailTemplateMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTemplate;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaMailConfigService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.ValidatorUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:23 ??????
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

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(UpdateMailTemplateEnter enter) {
        if (null != enter) {
            // ?????????????????????
            if (null != enter.getMailTemplateNo()) {
                if (!ValidatorUtil.isNumber(String.valueOf(enter.getMailTemplateNo()))) {
                    throw new FoundationException(ExceptionCodeEnums.TEMPLATE_PARAM_NO_IS_NOT_NUMBER.getCode(), ExceptionCodeEnums.TEMPLATE_PARAM_NO_IS_NOT_NUMBER.getMessage());
                }
            }
            if (StringUtils.isNotBlank(enter.getName()) || StringUtils.isNotBlank(enter.getEvent()) || StringUtils.isNotBlank(enter.getSubject()) || StringUtils.isNotBlank(enter.getMemo())) {
                if (enter.getName().length() > 64 || enter.getEvent().length() > 64 || enter.getSubject().length() > 255 || enter.getMemo().length() > 64) {
                    throw new FoundationException(ExceptionCodeEnums.LENGTH_IS_TOO_LONG.getCode(), ExceptionCodeEnums.LENGTH_IS_TOO_LONG.getMessage());
                }
            }
        }

        // ??????????????????
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
        if (null == enter.getId() || 0 == enter.getId()) {
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
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
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
     * ?????????????????????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveParameter(SaveMailConfigEnter enter) {
        if (null != enter) {
            if (StringUtils.isNotBlank(enter.getParamKey())) {
                if (enter.getParamKey().length() > 64) {
                    throw new FoundationException(ExceptionCodeEnums.LENGTH_IS_TOO_LONG.getCode(), ExceptionCodeEnums.LENGTH_IS_TOO_LONG.getMessage());
                }
            }
        }

        PlaMailConfig config = new PlaMailConfig();

        if (null == enter.getId() || 0 == enter.getId()) {
            //??????
            config.setId(idSerService.getId(SequenceName.PLA_MAIL_CONFIG));
            config.setDr(Constant.DR_FALSE);
            config.setStatus(MailConfigStatusEnums.NORMAL.getCode());
            config.setMailTemplateNo(enter.getMailTemplateNo());
            config.setCreatedBy(enter.getUserId());
            config.setCreatedTime(new Date());
        } else {
            //??????
            PlaMailTemplate plaMailTemplate = mailTemplateMapper.selectOne(new QueryWrapper<PlaMailTemplate>().eq(PlaMailTemplate.COL_MAIL_TEMPLATE_NO, enter.getMailTemplateNo()));
            if (null == plaMailTemplate) {
                throw new FoundationException(ExceptionCodeEnums.TEMPLATE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TEMPLATE_IS_NOT_EXIST.getMessage());
            }
            config = plaMailConfigService.getById(enter.getId());
            if (null == config) {
                throw new FoundationException(ExceptionCodeEnums.TEMPLATE_PARAM_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TEMPLATE_PARAM_IS_NOT_EXIST.getMessage());
            }
        }
        if (StringUtils.isAnyBlank(
                String.valueOf(enter.getMailTemplateNo()),
                enter.getMailAppId(),
                enter.getParamKey(),
                enter.getParamValue())) {
            throw new FoundationException(ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getMessage());
        }
        if (null == AppIDEnums.checkAppId(enter.getMailAppId())) {
            throw new FoundationException(ExceptionCodeEnums.APPID_IS_NOT_MATCH.getCode(), ExceptionCodeEnums.APPID_IS_NOT_MATCH.getMessage());
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
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult deleteParameter(IdEnter enter) {

        plaMailConfigService.removeById(enter.getId());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public MailTemplateConfigResult listParameter(QueryMailConfigEnter enter) {
        //????????????
        MailTemplateConfigResult result = new MailTemplateConfigResult();

        //????????????
        List<MailTemplateSystemInfoResult> systemInfoList = new ArrayList<>();

        //??????????????????
        PlaMailTemplate mailTemplate = mailTemplateMapper.selectOne(new QueryWrapper<PlaMailTemplate>()
                .eq(PlaMailTemplate.COL_MAIL_TEMPLATE_NO, enter.getMailTemplateNo()));

        FoundationAssert.isNull(mailTemplate, ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.VERSION_IS_NOT_EXIST.getMessage());

        //??????????????????????????????
        result.setId(mailTemplate.getId());
        result.setMailTemplateNo(mailTemplate.getMailTemplateNo());
        result.setEvent(mailTemplate.getEvent());
        result.setName(mailTemplate.getName());
        result.setSubject(mailTemplate.getSubject());

        //?????????????????????????????????????????????
        List<PlaMailConfig> list = plaMailConfigService.list(new QueryWrapper<PlaMailConfig>()
                .eq(PlaMailConfig.COL_MAIL_TEMPLATE_NO, enter.getMailTemplateNo()).orderByAsc(PlaMailConfig.COL_CREATED_TIME));

        FoundationAssert.isNull(list, ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PARAMETER_IS_NOT_EXIST.getMessage());

        //????????????id?????????id??????,?????????????????????????????????
        Map<String, List<PlaMailConfig>> mapGroup = list.stream().collect(Collectors.groupingBy(plaMailConfig -> plaMailConfig.getSystemId() + "::" + plaMailConfig.getAppId(), LinkedHashMap::new, Collectors.toList()));

        if (!mapGroup.isEmpty()) {
            //????????????
            mapGroup.forEach((k, v) -> {
                //??????????????????
                MailTemplateSystemInfoResult systemInfo = new MailTemplateSystemInfoResult();
                systemInfo.setCode(k);
                systemInfo.setSystemId(v.get(0).getSystemId());
                systemInfo.setAppId(v.get(0).getAppId());
                systemInfo.setRequestId(enter.getRequestId());
                //??????????????????
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
