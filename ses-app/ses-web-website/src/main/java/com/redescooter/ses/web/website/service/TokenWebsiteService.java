package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;

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
    GeneralResult setPassword(ModifyPasswordEnter enter);

    /**
     * 发送重置密码邮件
     *
     * @param enter
     * @return
     */
    GeneralResult forgetPasswordEmail(BaseSendMailEnter enter);
}
