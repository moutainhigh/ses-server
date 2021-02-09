package com.redescooter.ses.web.ros.service.email;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IntEnter;
import com.redescooter.ses.api.foundation.vo.mail.MailConfigOfTermResult;
import com.redescooter.ses.api.foundation.vo.mail.MailTemplateConfigResult;
import com.redescooter.ses.api.foundation.vo.mail.QueryMailConfigEnter;
import com.redescooter.ses.api.foundation.vo.mail.SaveMailConfigEnter;

import java.util.List;
import java.util.Map;

/**
 * @Author jerry
 * @Date 2021/2/8 2:45 下午
 * @Description 邮件参数服务
 **/
public interface EmailParameterService {

    /**
     * 保存或更新邮件模板参数
     *
     * @param enter
     * @return
     */
    GeneralResult save(SaveMailConfigEnter enter);

    /**
     * 删除邮件参数
     *
     * @param enter
     * @return
     */
    GeneralResult delete(IdEnter enter);

    /**
     * 邮件模板参数列表
     *
     * @param enter
     * @return
     */
    MailTemplateConfigResult list(QueryMailConfigEnter enter);

}
