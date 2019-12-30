package com.redescooter.ses.web.delivery.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.ChanagePasswordEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginResult;
import com.redescooter.ses.api.proxy.vo.mail.SendMailEnter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 30/12/2019 10:55 上午
 * @ClassName: TokenController
 * @Function: TODO
 */

@Api(tags = {"SaaS-Sign"})
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
    @ApiOperation(value = "发送验证邮件接口", response = GeneralResult.class)
    @PostMapping(value = "/sendEmail")
    public Response<GeneralResult> sendEmail(@ModelAttribute SendMailEnter enter) {
        return new Response<>();
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "忘记密码", response = GeneralResult.class)
    @PostMapping(value = "/forgetPassword")
    public Response<GeneralResult> forgetPassword(@ModelAttribute SetPasswordEnter enter) {
        return new Response<>();
    }

    @ApiOperation(value = "修改密码", response = GeneralResult.class)
    @PostMapping(value = "/chanagePassword")
    public Response<GeneralResult> chanagePassword(@ModelAttribute ChanagePasswordEnter enter) {
        return new Response<>();
    }
}
