package com.redescooter.ses.api.foundation.service.base;


import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.ValidateCodeEnter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 10/10/2019 5:55 下午
 * @ClassName: MailMultiBaseTaskService
 * @Function: TODO
 */
public interface MailMultiBaseTaskService {

    /**
     * Mobile激活邮件任务
     *
     * @param enter
     * @return
     */
    GeneralResult addActivateMobileUserTask(BaseMailTaskEnter enter);

    /**
     * Web激活邮件任务
     *
     * @param enter
     * @return
     */
    GeneralResult addActivateWebUserTask(BaseMailTaskEnter enter);

    /**
     * Mobile设置密码邮件任务
     *
     * @param enter
     * @return
     */
    GeneralResult addSetPasswordMobileUserTask(ValidateCodeEnter enter);

    /**
     * Web设置密码邮件任务
     *
     * @param enter
     * @return
     */
    GeneralResult addSetPasswordWebUserTask(BaseMailTaskEnter enter);


    /**
     * Mobile账户提醒邮件任务
     *
     * @param enter
     * @return
     */
    GeneralResult addPermissionWarnMobileTask(BaseMailTaskEnter enter);

    /**
     * WEB冻结通知
     *
     * @param enter
     * @return
     */
    GeneralResult addFreezeWarnWebTask(DateTimeParmEnter enter);

    /**
     * WEB续约通知
     *
     * @param enter
     * @return
     */
    GeneralResult addRenewalWarnWebTask(DateTimeParmEnter enter);

    /**
     * WEB解冻通知
     *
     * @param enter
     * @return
     */
    GeneralResult addNnfreezeWarnWebTask(DateTimeParmEnter enter);

    /**
     * WEB过期通知
     *
     * @param enter
     * @return
     */
    GeneralResult addExpiredWarnWebTask(BaseMailTaskEnter enter);

    /**
     * 多系统多维度添加邮件任务
     *
     * @return
     */
    GeneralResult addMultiMailTask(String json);

    /**
     * 执行所有任务
     *
     * @return
     */
    GeneralResult runAllTask();
}
