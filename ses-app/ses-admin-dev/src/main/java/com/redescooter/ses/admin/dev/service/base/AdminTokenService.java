package com.redescooter.ses.admin.dev.service.base;

import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;

/**
 * @ClassNameAdminTokenService
 * @Description
 * @Author Aleks
 * @Date2020/12/3 10:00
 * @Version V1.0
 **/
public interface AdminTokenService {

    /*
     * @Author Aleks
     * @Description  用户登录
     * @Date  2020/12/3 11:30
     * @Param [enter]
     * @return
     **/
    TokenResult login(LoginEnter enter);
}
