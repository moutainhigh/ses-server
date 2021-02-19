package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailConfigStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.service.MailConfigManageService;
import com.redescooter.ses.api.foundation.vo.mail.MailConfigOfTermEnter;
import com.redescooter.ses.api.foundation.vo.mail.MailConfigOfTermResult;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailConfigEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.base.PlaMailConfigMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:41 下午
 * @ClassName: MailConfigManageServiceImpl
 * @Function: TODO
 */
@Slf4j
@DubboService
public class MailConfigManageServiceImpl implements MailConfigManageService {

    @Autowired
    private PlaMailConfigMapper mailConfigMapper;

    @DubboReference
    private IdAppService idSerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult save(SaveMailConfigEnter enter) {

        PlaMailConfig mailConfig = new PlaMailConfig();
        BeanUtils.copyProperties(enter, mailConfig);

        if (enter.getId() == null || enter.getId() == 0) {
            mailConfig.setId(idSerService.getId(SequenceName.PLA_MAIL_CONFIG));
        }
        mailConfig.setStatus(MailConfigStatusEnums.NORMAL.getCode());
        mailConfig.setAppId(enter.getMailAppId());
        mailConfig.setSystemId(AppIDEnums.getSystemId(enter.getMailAppId()));
        mailConfig.setCreatedBy(enter.getUserId());
        mailConfig.setCreatedTime(new Date());
        mailConfig.setUpdatedBy(enter.getUserId());
        mailConfig.setUpdatedTime(new Date());

        mailConfigMapper.insertOrUpdateSelective(mailConfig);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<MailConfigOfTermResult> getMailConfigOfTerm(MailConfigOfTermEnter enter) {

        QueryWrapper<PlaMailConfig> wrapper = new QueryWrapper<>();

        String key = enter.getKey();
        Integer mailTemplateNo = enter.getMailTemplateNo();
        String status = enter.getStatus();

        if (StringUtils.isNotBlank(key)) {
            wrapper.eq(PlaMailConfig.COL_PARAM_KEY, key);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq(PlaMailConfig.COL_STATUS, status);
        }
        if (mailTemplateNo != null) {
            wrapper.eq(PlaMailConfig.COL_MAIL_TEMPLATE_NO, mailTemplateNo);

        }
        List<PlaMailConfig> configList = mailConfigMapper.selectList(wrapper);
        MailConfigOfTermResult result = null;
        List<MailConfigOfTermResult> resultList = new ArrayList<>();
        for (PlaMailConfig mailConfig : configList) {
            result = new MailConfigOfTermResult();
            BeanUtils.copyProperties(mailConfig, result);
            resultList.add(result);
        }
        return resultList;
    }
}
