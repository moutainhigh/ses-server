package com.redescooter.ses.web.ros.service.email.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IntEnter;
import com.redescooter.ses.api.foundation.service.MailTemplateManageService;
import com.redescooter.ses.api.foundation.vo.mail.MailConfigOfTermResult;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailConfigEnter;
import com.redescooter.ses.web.ros.service.email.EmailParameterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 邮件模板参数列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<MailConfigOfTermResult> list(IntEnter enter) {
        return mailTemplateManageService.list(enter);
    }
}
