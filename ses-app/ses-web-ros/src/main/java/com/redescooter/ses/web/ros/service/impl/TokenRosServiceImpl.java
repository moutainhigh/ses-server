package com.redescooter.ses.web.ros.service.impl;


import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.api.proxy.vo.mail.SendMailEnter;
import com.redescooter.ses.web.ros.service.TokenRosService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

@Slf4j
@Service
public class TokenRosServiceImpl implements TokenRosService {


    /**
     * 用户登录
     *
     * @param enter
     * @return
     */
    @Override
    public TokenResult login(LoginEnter enter) {
        return null;
    }

    /**
     * 用户注销
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult logout(GeneralEnter enter) {
        return null;
    }

    /**
     * 更换密码
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult modifyPassword(ModifyPasswordEnter enter) {
        return null;
    }

    /**
     * 登陆token检查
     *
     * @param enter
     * @return
     */
    @Override
    public UserToken checkToken(GeneralEnter enter) {
        return null;
    }

    /**
     * 邮件发送
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult sendCode(SendMailEnter enter) {
        return null;
    }
}
