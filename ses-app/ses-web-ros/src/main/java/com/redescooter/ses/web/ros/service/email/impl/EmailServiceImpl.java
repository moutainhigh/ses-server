package com.redescooter.ses.web.ros.service.email.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.MailTemplateManageService;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateResult;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailTemplateEnter;
import com.redescooter.ses.api.foundation.vo.mail.UpdateMailTemplateEnter;
import com.redescooter.ses.web.ros.service.email.EmailService;
import com.redescooter.ses.web.ros.vo.email.EmailListEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/2/3 1:19 下午
 * @Description 邮件模板实现类
 **/
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @DubboReference
    private MailTemplateManageService mailTemplateManageService;

    /**
     * 保存更新邮件模板
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult save(SaveMailTemplateEnter enter) {

        UpdateMailTemplateEnter saveVO = new UpdateMailTemplateEnter();
        BeanUtils.copyProperties(enter, saveVO);

        return mailTemplateManageService.save(saveVO);
    }

    /**
     * 更新邮件模板
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult update(UpdateMailTemplateEnter enter) {
        return mailTemplateManageService.save(enter);
    }

    /**
     * 删除邮件模板
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResult delete(IdEnter enter) {
        return mailTemplateManageService.delete(enter);
    }

    /**
     * 邮件模板列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<MailTemplateResult> getList(EmailListEnter enter) {
        log.info("邮件模板列表的入参是:[{}]", enter);
        com.redescooter.ses.api.common.vo.email.EmailListEnter param = new com.redescooter.ses.api.common.vo.email.EmailListEnter();
        BeanUtils.copyProperties(enter, param);
        return mailTemplateManageService.getMailTemplateList(param);
    }
}
