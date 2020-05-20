package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.web.ros.vo.website.SignUpEnter;
import com.redescooter.ses.web.ros.vo.website.StorageEamilEnter;

/**
 * @ClassName:WebSiteService
 * @description: WebSiteService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 11:59
 */
public interface WebSiteTokenService {
    /**
     * 登录
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
     * 用户注册
     *
     * @param enter
     * @return
     */
    GeneralResult signUp(SignUpEnter enter);

    /**
     * 官网登录token 校验
     *
     * @param enter
     * @return
     */
    UserToken checkCustomerToken(GeneralEnter enter);


}
