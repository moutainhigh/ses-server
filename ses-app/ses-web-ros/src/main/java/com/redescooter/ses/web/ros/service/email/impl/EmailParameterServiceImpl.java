package com.redescooter.ses.web.ros.service.email.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.service.MailTemplateManageService;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateConfigResult;
import com.redescooter.ses.api.foundation.vo.mail.QueryMailConfigEnter;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailConfigEnter;
import com.redescooter.ses.web.ros.service.email.EmailParameterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @Author jerry
 * @Date 2021/2/8 2:46 下午
 * @Description 邮件参数服务实现类
 **/
@Slf4j
@Service
public class EmailParameterServiceImpl implements EmailParameterService {

    @DubboReference
    private MailTemplateManageService mailTemplateManageService;

    /**
     * 保存或更新邮件模板参数
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SaveMailConfigEnter enter) {
        return mailTemplateManageService.saveParameter(enter);
    }

    /**
     * 删除邮件参数
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult delete(IdEnter enter) {
        return mailTemplateManageService.deleteParameter(enter);
    }

    /**
     * 邮件模板参数列表
     *
     * @param enter
     * @return
     */
    @Override
    public MailTemplateConfigResult list(QueryMailConfigEnter enter) {
        return mailTemplateManageService.listParameter(enter);
    }
}
