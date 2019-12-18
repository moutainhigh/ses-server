package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.proxy.vo.mail.SendMailEnter;

/**
 * 功能描述: TOKE服务
 *
 * @auther: jerry
 * @date: 2019-05-28 14:10
 */
public interface TokenRosService {

    /**
     * 用户登录
     *
     * @param enter
     * @return
     */
    TokenResult login(LoginEnter enter);

    /**
     * 用户注销
     *
     * @param enter
     * @return
     */
    GeneralResult logout(GeneralEnter enter);

    /**
     * 更换密码
     *
     * @param enter
     * @return
     */

    GeneralResult modifyPassword(ModifyPasswordEnter enter);

    /**
     * 登陆token检查
     *
     * @param enter
     * @return
     */
    UserToken checkToken(GeneralEnter enter);

    /**
     * 邮件发送
     *
     * @param enter
     * @return
     */
    GeneralResult sendCode(SendMailEnter enter);
}
