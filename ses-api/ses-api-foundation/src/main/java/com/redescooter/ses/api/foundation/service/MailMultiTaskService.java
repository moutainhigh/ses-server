package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.account.FreezeWarnWebTaskEnter;
import com.redescooter.ses.api.foundation.vo.account.NnfreezeWarnWebTaskEnter;
import com.redescooter.ses.api.foundation.vo.account.RenewalWarnWebTaskEnter;
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
     * 客户询价单定金支付
     *
     * @param enter
     * @return
     */
    GeneralResult addCustomerInquiryPayDepositTask(BaseMailTaskEnter enter);

    /**
     * 客户询价单尾款支付
     * @param enter 尾款
     * @return
     */
    GeneralResult addCustomerInquiryPayLastParagraphTask(BaseMailTaskEnter enter);


  /**
   * 定金支付成功发送邮件
   * @param
   * @return
   */
  GeneralResult subscriptionPaySucceedSendmail( BaseMailTaskEnter enter);

    /**
     * 订阅广告发送邮件
     * @param
     * @return
     */
    GeneralResult subscribeToEmailSuccessfully( BaseMailTaskEnter enter);

    /**
     * 多系统多维度添加邮件任务
     subscriptionsubscriptionsubscriptionsubscription     * @return
     */
    GeneralResult addMultiMailTask(BaseMailTaskEnter enter);

    /**
     * 执行所有任务
     *
     * @return
     */
    GeneralResult runAllTask();

    /**
     * 执行指定邮件任务
     *
     * @param id
     * @return
     */
    void runTaskById(long id);



}
