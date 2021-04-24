package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.web.website.vo.login.SiteResetPassword;
import com.redescooter.ses.web.website.vo.login.SiteSetPasswordEnter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 2:54 上午
 * @ClassName: TokenRepairService
 * @Function: TODO
 */
public interface TokenWebsiteService {
    /**
     * 用户创建
     *
     * @param enter
     * @return
     */
    GeneralResult signUp(LoginEnter enter);


    /**
     * 获取账号公钥
     *
     * @param enter
     * @return
     */
    GetAccountKeyResult getAccountKey(GeneralEnter enter);


    /**
     * 用户登录
     *
     * @param enter
     * @return
     */
    TokenResult login(LoginEnter enter);

    /**
     * 用户登出
     *
     * @param enter
     * @return
     */
    GeneralResult logout(GeneralEnter enter);

    /**
     * 登陆token检查
     *
     * @param enter
     * @return
     */
    UserToken checkToken(GeneralEnter enter);

    /**
     * 设置密码
     *
     * @param enter
     * @return
     */
    GeneralResult setPassword(SiteSetPasswordEnter enter);

    /**
     * 发送重置密码邮件
     *
     * @param enter
     * @return
     */
    GeneralResult forgetPasswordEmail(BaseSendMailEnter enter);


    /**
     * 修改密码
     *
     * @param enter
     * @return
     */
    GeneralResult resetPassword(SiteResetPassword enter);


    /**
     * 邮件订阅
     *
     * @param enter
     * @return
     */
    GeneralResult emailSubscribe(CheckEmailEnter enter);

    /**
     * 刷新token
     */
    TokenResult refreshToken(RefreshTokenEnter enter);

}
