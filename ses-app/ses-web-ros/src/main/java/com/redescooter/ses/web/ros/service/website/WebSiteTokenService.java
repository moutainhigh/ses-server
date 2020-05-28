package com.redescooter.ses.web.ros.service.website;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.web.ros.vo.website.SignUpEnter;

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


    /**
     * 邮件发送
     *
     * @param enter
     * @return
     */
    GeneralResult sendEmail(BaseSendMailEnter enter);




    /**
     * 官网上修改密码
     *
     * @param enter
     * @return
     */
    GeneralResult forgetPassword( WebResetPasswordEnter enter);


    /**
     * @Author Aleks
     * @Description //
     * @Date  2020/5/28 14:07
     * @Param [enter]
     * @return
     **/
    GeneralResult resetPassword( WebResetPasswordEnter enter);


    /**
     * 官网修改用户的信息
     *
     * @return
     * @Author Aleks
     * @Date 2020/5/26 15:31
     * @Param
     **/
    GeneralResult editCustomer(WebEditCustomerEnter enter);


}
