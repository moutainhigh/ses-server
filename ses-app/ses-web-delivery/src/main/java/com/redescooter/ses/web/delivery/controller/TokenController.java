package com.redescooter.ses.web.delivery.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.account.ChanagePasswordEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 30/12/2019 10:55 上午
 * @ClassName: TokenController
 * @Function: TODO
 */

@Api(tags = {"公共鉴权"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sign/token", method = RequestMethod.POST)
public class TokenController {

    @Reference
    private UserTokenService userTokenService;

    @IgnoreLoginCheck
    @ApiOperation(value = "登录接口", response = LoginResult.class)
    @PostMapping(value = "/login")
    public Response<LoginResult> login(@ModelAttribute LoginEnter enter) {
        return new Response<>(userTokenService.login(enter));
    }

    @ApiOperation(value = "登出接口", response = GeneralResult.class)
    @PostMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute GeneralEnter enter) {
        return new Response<>(userTokenService.logout(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "邮件发送", response = GeneralResult.class)
    @PostMapping(value = "/sendEmail")
    public Response<GeneralResult> sendEmail(@ModelAttribute BaseSendMailEnter enter) {
        return new Response<>(userTokenService.sendEmail(enter));
    }
    
    @IgnoreLoginCheck
    @ApiOperation(value = "验证码登录", response = LoginResult.class)
    @PostMapping(value = "/loginByCode")
    public Response<LoginResult> loginByCode(@ModelAttribute LoginEnter enter) {
        return new Response<>(userTokenService.loginByCode(enter));
    }
    
    @IgnoreLoginCheck
    @ApiOperation(value = "验证码登录发送邮件", response = GeneralResult.class)
    @PostMapping(value = "/loginSendCode")
    public Response<GeneralResult> loginSendCode(@ModelAttribute LoginEnter enter) {
        return new Response<>(userTokenService.loginSendCode(enter));
    }
    
    
    @IgnoreLoginCheck
    @ApiOperation(value = "忘记密码", response = GeneralResult.class)
    @PostMapping(value = "/forgotPassword")
    public Response<GeneralResult> forgetPassword(@ModelAttribute SetPasswordEnter enter) {
        return new Response<>(userTokenService.setPassword(enter));
    }

    @ApiOperation(value = "修改密码", response = GeneralResult.class)
    @PostMapping(value = "/chanagePassword")
    public Response<GeneralResult> chanagePassword(@ModelAttribute ChanagePasswordEnter enter) {
        return new Response<>(userTokenService.chanagePassword(enter));
    }
}
