package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.GetAccountKeyResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.RefreshTokenEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.web.ros.vo.account.AddSysUserEnter;

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

    /*
     * @Author Aleks
     * @Description  邮箱加验证码登录给用户发邮件（邮件里面是验证码）
     * @Date  2020/7/20 19:23
     * @Param [enter]
     * @return
     **/
    GeneralResult emailLoginSendCode(LoginEnter enter);

    /**
     * @Author Aleks
     * @Description  邮箱加验证码登陆
     * @Date  2020/7/20 19:13
     * @Param [enter]
     * @return
     **/
    TokenResult emailLogin(LoginEnter enter);

    /**
     * 获取密钥
     *
     * @param enter
     * @return
     */
    GetAccountKeyResult getAccountKey(GeneralEnter enter);

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
    GeneralResult sendCode(BaseSendMailEnter enter);

    /**
     * 添加ROS用户
     *
     * @param enter
     * @return
     */
    GeneralResult createRosUser(AddSysUserEnter enter);

    /**
     * 删除ROS用户
     *
     * @param enter
     * @return
     */
    GeneralResult deleteRosUser(IdEnter enter);

    /**
     * 发送忘记密码邮箱
     *
     * @param enter
     * @return
     */
    GeneralResult sendForgetPasswordEmail(BaseSendMailEnter enter);

    /**
     * 刷新token
     * @param enter
     * @return
     */
    TokenResult refreshToken(RefreshTokenEnter enter);

}
