package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IntEnter;
import com.redescooter.ses.api.common.vo.email.EmailListEnter;
import com.redescooter.ses.api.foundation.vo.mail.MailConfigOfTermResult;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateResult;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailConfigEnter;
import com.redescooter.ses.api.foundation.vo.mail.UpdateMailTemplateEnter;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:08 下午
 * @ClassName: MailConfigManageService
 * @Function: TODO
 */
public interface MailTemplateManageService {

    /**
     * 保存更新邮件模板
     *
     * @param enter
     * @return
     */
    GeneralResult save(UpdateMailTemplateEnter enter);

    /**
     * 删除邮件模板
     *
     * @param enter
     * @return
     */
    GeneralResult delete(IdEnter enter);

    /**
     * 邮件模板列表
     *
     * @param enter
     * @return
     */
    List<MailTemplateResult> getMailTemplateList(EmailListEnter enter);

    /**
     * 保存或更新邮件模板参数
     *
     * @param enter
     * @return
     */
    GeneralResult saveParameter(SaveMailConfigEnter enter);

    /**
     * 邮件模板参数列表
     *
     * @param enter
     * @return
     */
    List<MailConfigOfTermResult> list(IntEnter enter);
}
