package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.LoginJPushProService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.login.LoginConfirmEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginResult;
import com.redescooter.ses.api.foundation.vo.message.LoginPushEnter;
import com.redescooter.ses.mobile.client.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 21/11/2019 4:38 下午
 * @ClassName: UserTokenController
 * @Function: TODO
 */
@Slf4j
@Api(tags = "登入登出")
@CrossOrigin
@RestController
@RequestMapping(value = "/sign/token", method = RequestMethod.POST)
public class UserTokenController {

    @Autowired
    private TokenService tokenService;
    @Reference
    private UserTokenService userTokenService;
    @Reference
    private LoginJPushProService loginJPushProService;


    @IgnoreLoginCheck
    @ApiOperation(value = "登入接口", response = LoginResult.class)
    @RequestMapping(value = "/login")
    public Response<LoginResult> login(@ModelAttribute LoginEnter enter) {
        return new Response<>(userTokenService.login(enter));
    }

    @ApiOperation(value = "登出注销", response = GeneralResult.class)
    @RequestMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute GeneralEnter enter) {
        return new Response<>(userTokenService.logout(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "登入确认", response = LoginResult.class)
    @RequestMapping(value = "/login/confirm/{confirmRequestId}")
    public Response<LoginResult> loginConfirm(@PathVariable("confirmRequestId") String confirmRequestId,
                                              LoginConfirmEnter enter) {
        enter.setConfirmRequestId(confirmRequestId);
        return new Response<>(userTokenService.loginConfirm(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "发送验证码", response = GeneralResult.class)
    @RequestMapping(value = "/sendCode")
    public Response<GeneralResult> sendCode(@ModelAttribute BaseSendMailEnter enter) {
        return new Response<>(tokenService.sendCode(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "设置密码", response = GeneralResult.class)
    @RequestMapping(value = "/setPassword/{requestId}")
    public Response<GeneralResult> sendCode(@PathVariable("requestId") String requestId, @ModelAttribute SetPasswordEnter enter) {
        enter.setRequestId(requestId);
        return new Response<>(tokenService.setPassword(enter));
    }

    @ApiOperation(value = "极光注册id与用户id进行绑定", notes = "极光注册id与用户id进行绑定")
    @RequestMapping(value = "/logInPush")
    public Response<GeneralResult> logInPush(LoginPushEnter enter) {
        return new Response<>(loginJPushProService.logInPush(enter));
    }

    @ApiOperation(value = "极光注册id与用户id进行解绑", notes = "极光注册id与用户id进行解绑")
    @RequestMapping(value = "/logOutPush")
    public Response<GeneralResult> logOutPush(LoginPushEnter enter) {

        return new Response<>(loginJPushProService.logOutPush(enter));
    }
}


