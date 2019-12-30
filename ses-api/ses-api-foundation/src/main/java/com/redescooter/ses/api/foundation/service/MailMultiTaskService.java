package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.FreezeWarnWebTaskEnter;
import com.redescooter.ses.api.foundation.vo.NnfreezeWarnWebTaskEnter;
import com.redescooter.ses.api.foundation.vo.RenewalWarnWebTaskEnter;
import com.redescooter.ses.api.foundation.vo.login.SetPasswordMobileUserTaskEnter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 10/10/2019 5:55 下午
 * @ClassName: MailMultiTaskService
 * @Function: TODO
 */
public interface MailMultiTaskService {

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
    GeneralResult addSetPasswordMobileUserTask(SetPasswordMobileUserTaskEnter enter);

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
    GeneralResult addFreezeWarnWebTask(FreezeWarnWebTaskEnter enter);

    /**
     * WEB续约通知
     *
     * @param enter
     * @return
     */
    GeneralResult addRenewalWarnWebTask(RenewalWarnWebTaskEnter enter);

    /**
     * WEB解冻通知
     *
     * @param enter
     * @return
     */
    GeneralResult addNnfreezeWarnWebTask(NnfreezeWarnWebTaskEnter enter);

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
    GeneralResult addMultiMailTask(String jsonStr);

    /**
     * 执行所有任务
     *
     * @return
     */
    GeneralResult runAllTask();
}
