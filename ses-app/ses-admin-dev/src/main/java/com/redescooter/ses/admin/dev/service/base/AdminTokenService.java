package com.redescooter.ses.admin.dev.service.base;

import com.redescooter.ses.admin.dev.vo.user.UserInfoResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;

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


    /*
     * @Author Aleks
     * @Description  邮箱加验证码登录给用户发邮件（邮件里面是验证码）
     * @Date  2020/12/3 13:52
     * @Param [enter]
     * @return
     **/
    GeneralResult emailLoginSendCode(LoginEnter enter);


    /**
     * @Author Aleks
     * @Description 邮箱加验证码登陆
     * @Date  2020/12/3 14:02
     * @Param [enter]
     * @return
     **/
    TokenResult emailLogin(LoginEnter enter);


    /**
     * @Author Aleks
     * @Description  退出登录
     * @Date  2020/12/3 14:07
     * @Param [enter]
     * @return
     **/
    GeneralResult logout(GeneralEnter enter);


    /**
     * @Author Aleks
     * @Description
     * @Date  2020/12/3 14:10
     * @Param [enter]
     * @return
     **/
    GeneralResult modifyPassword(ModifyPasswordEnter enter);

    /**
     * @Author Aleks
     * @Description  获取登陆用户的信息
     * @Date  2020/12/4 11:50
     * @Param [enter]
     * @return
     **/
    UserInfoResult userInfo(GeneralEnter enter);
}
