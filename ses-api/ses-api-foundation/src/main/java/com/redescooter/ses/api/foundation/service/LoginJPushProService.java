package com.redescooter.ses.api.foundation.service;


import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.message.LoginPushEnter;

/**
 * description: LoginJPushService
 * author: jerry.li
 * create: 2019-05-22 14:34
 */

public interface LoginJPushProService {

    /**
     * 极光用户关系绑定
     *
     * @param enter
     * @return
     */
    GeneralResult logInPush(LoginPushEnter enter);


    /**
     * 极光用户关系解绑
     *
     * @param enter
     * @return
     */
    GeneralResult logOutPush(LoginPushEnter enter);
}
