package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateOfTermEnter;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateOfTermResult;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailTemplateEnter;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:08 下午
 * @ClassName: MailConfigManageService
 * @Function: TODO
 */
public interface MailTemplateManageService {

    GeneralResult save(SaveMailTemplateEnter enter);

    List<MailTemplateOfTermResult> getMailTemplateOfTerm(MailTemplateOfTermEnter enter);
}
