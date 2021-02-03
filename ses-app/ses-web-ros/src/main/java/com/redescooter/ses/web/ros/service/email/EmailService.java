package com.redescooter.ses.web.ros.service.email;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateResult;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailTemplateEnter;
import com.redescooter.ses.api.foundation.vo.mail.UpdateMailTemplateEnter;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/2/3 1:17 下午
 * @Description 邮件模板服务
 **/
public interface EmailService {

    /**
     * 保存更新邮件模板
     *
     * @param enter
     * @return
     */
    GeneralResult save(SaveMailTemplateEnter enter);

    /**
     * 更新邮件模板
     *
     * @param enter
     * @return
     */
    GeneralResult update(UpdateMailTemplateEnter enter);

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
    List<MailTemplateResult> getList(StringEnter enter);
}
