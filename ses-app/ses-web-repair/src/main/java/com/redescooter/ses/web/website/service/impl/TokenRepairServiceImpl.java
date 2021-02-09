package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.web.website.service.TokenRepairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 2:59 上午
 * @ClassName: TokenRepairServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class TokenRepairServiceImpl implements TokenRepairService {
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
     * @param enter
     * @desc: 修改密码
     * @param: enter
     * @return: generresult
     * @auther: alex
     * @date: 2019/7/24 16:52
     * @Version: 1.1
     */
    @Override
    public GeneralResult modifyPassword(ModifyPasswordEnter enter) {
        return null;
    }

    /**
     * 发送重置密码邮件
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult sendCode(BaseSendMailEnter enter) {
        return null;
    }
}
