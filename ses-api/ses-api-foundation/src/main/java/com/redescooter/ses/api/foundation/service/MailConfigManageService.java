package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.mail.MailConfigOfTermEnter;
import com.redescooter.ses.api.foundation.vo.mail.MailConfigOfTermResult;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailConfigEnter;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:08 下午
 * @ClassName: MailConfigManageService
 * @Function: TODO
 */
public interface MailConfigManageService {

    GeneralResult save(SaveMailConfigEnter enter);

    List<MailConfigOfTermResult> getMailConfigOfTerm(MailConfigOfTermEnter enter);
}
