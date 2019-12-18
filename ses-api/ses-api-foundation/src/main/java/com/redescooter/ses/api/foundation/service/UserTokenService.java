package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.ValidateCodeEnter;
import com.redescooter.ses.api.foundation.vo.login.*;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;

import java.util.List;

public interface UserTokenService {

    /**
     * 登陆token检查
     *
     * @param enter
     * @return
     */
    UserToken checkToken(GeneralEnter enter);

    /**
     * 用户登录
     *
     * @param enter
     * @return
     */
    LoginResult login(LoginEnter enter);

    /**
     * 确认登录
     *
     * @param enter
     * @return
     */
    LoginResult loginConfirm(LoginConfirmEnter enter);

    /**
     * 用户注销
     *
     * @param enter
     * @return
     */
    GeneralResult logout(GeneralEnter enter);

    /**
     * 根据邮箱及账户类型获取账户信息
     *
     * @param enter
     * @return
     */
    UserToken getUserByEmailType(GetUserEnter enter);

    /**
     * 根据租户id锁定所有账户
     *
     * @param list
     * @return
     */
    GeneralResult lockBySaaSAccount(List<Long> list);

    /**
     * 解锁SaaS账户
     *
     * @param list
     * @return
     */
    GeneralResult UnlockBySaaSAccount(List<Long> list);

    /**
     * 验证验证码
     *
     * @param enter
     * @return
     */
    GeneralResult validateCode(ValidateCodeEnter enter);

    /**
     * 员工离职 账户禁用、token 清除
     *
     * @return
     */
    GeneralResult accountDisabled(GeneralEnter enter);

}
