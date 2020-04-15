package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.api.common.vo.base.*;
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
}