package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.MailTemplateManageService;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateResult;
import com.redescooter.ses.api.foundation.vo.mail.QueryMailTemplateEnter;
import com.redescooter.ses.api.foundation.vo.mail.UpdateMailTemplateEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.base.PlaMailTemplateMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTemplate;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private IdAppService idSerService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(UpdateMailTemplateEnter enter) {

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
    public List<MailTemplateResult> getMailTemplateList(QueryMailTemplateEnter enter) {

        QueryWrapper<PlaMailTemplate> wrapper = new QueryWrapper<>();

        String event = enter.getEvent();
        Integer mailTemplateNo = enter.getMailTemplateNo();
        String status = enter.getStatus();
        String name = enter.getName();
        if (StringUtils.isNotBlank(event)) {
            wrapper.eq(PlaMailTemplate.COL_EVENT, event);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq(PlaMailTemplate.COL_STATUS, status);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq(PlaMailTemplate.COL_NAME, name);
        }
        if (mailTemplateNo != null) {
            wrapper.eq(PlaMailTemplate.COL_MAIL_TEMPLATE_NO, mailTemplateNo);
        }

        List<PlaMailTemplate> mailTemplates = mailTemplateMapper.selectList(wrapper);
        MailTemplateResult result = null;
        List<MailTemplateResult> resultList = new ArrayList<>();
        for (PlaMailTemplate mailTemplate : mailTemplates) {
            result = new MailTemplateResult();
            BeanUtils.copyProperties(mailTemplate, result);
            resultList.add(result);
        }
        return resultList;
    }
}
