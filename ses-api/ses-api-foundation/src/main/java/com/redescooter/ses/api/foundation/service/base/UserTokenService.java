package com.redescooter.ses.api.foundation.service.base;

import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.common.vo.base.ValidateCodeEnter;
import com.redescooter.ses.api.foundation.vo.account.ChanagePasswordEnter;
import com.redescooter.ses.api.foundation.vo.login.AccountsDto;
import com.redescooter.ses.api.foundation.vo.login.LoginConfirmEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginResult;
import com.redescooter.ses.api.foundation.vo.user.GetUserEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;

import java.util.List;

public interface UserTokenService {

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
     * 登陆token检查
     *
     * @param enter
     * @return
     */
    UserToken checkToken(GeneralEnter enter);

    /**
     * 验证验证码
     *
     * @param enter
     * @return
     */
    GeneralResult validateCode(ValidateCodeEnter<AccountsDto> enter);

    /**
     * 实际登录逻辑
     *
     * @param user
     * @param enter
     * @return
     */
    LoginResult signIn(AccountsDto user, LoginEnter enter);

    /**
     * 默认PC端验证用户，不支持账号通用， 一个邮箱只能开设一种类型的用户
     *
     * @param enter
     * @return
     */
    AccountsDto checkDefaultUser(LoginEnter enter);

    /**
     * APP用户验证，2B/2C账号通用
     *
     * @param enter
     * @return
     */
    List<AccountsDto> checkAppUser(LoginEnter enter);

    /**
     * 权限校验
     *
     * @param dto
     */
    void chectPermission(AccountsDto dto);

    /**
     * 设置token
     *
     * @param enter
     * @param userDto
     * @return
     */
    UserToken setToken(LoginEnter enter, AccountsDto userDto);

    /**
     * 设置密码
     *
     * @param enter
     * @return
     */
    GeneralResult setPassword(SetPasswordEnter enter);

    /**
     * 系统内部设置密码
     *
     * @param enter
     * @return
     */
    GeneralResult chanagePassword(ChanagePasswordEnter enter);

    /**
     * 邮件发送
     *
     * @param enter
     * @return
     */
    GeneralResult sendEmail(BaseSendMailEnter enter);

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
     * 员工离职 账户禁用、token 清除
     *
     * @return
     */
    GeneralResult accountDisabled(GeneralEnter enter);

    /**
     * 获取APP用户信息
     *
     * @param enter
     * @return
     */
    List<UserToken> getAppUser(GetUserEnter enter);

}
