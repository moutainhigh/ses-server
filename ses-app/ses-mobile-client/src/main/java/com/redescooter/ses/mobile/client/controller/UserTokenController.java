package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.RefreshTokenEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.service.LoginJPushProService;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.vo.account.VerifyAccountEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginConfirmEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginResult;
import com.redescooter.ses.api.foundation.vo.message.LoginPushEnter;
import com.redescooter.ses.api.foundation.vo.message.PinEnter;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.mobile.client.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @DubboReference
    private UserTokenService userTokenService;

    @DubboReference
    private LoginJPushProService loginJPushProService;

    @DubboReference
    private UserProfileService userProfileService;

    @IgnoreLoginCheck
    @ApiOperation(value = "登入接口", response = LoginResult.class)
    @RequestMapping(value = "/login")
    public Response<LoginResult> login(@ModelAttribute LoginEnter enter) {
        return new Response<>(userTokenService.login(enter));
    }

    @ApiOperation(value = "登出注销", response = GeneralResult.class)
    @RequestMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute GeneralEnter enter) {
        userTokenService.logout(enter);
        // 用户退出app时,通过emq给车发送一个关锁的指令
        userProfileService.sendLockInstructionByEMQ(enter);
        return new Response<>();
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "登入确认", response = LoginResult.class)
    @RequestMapping(value = "/login/confirm/{confirmRequestId}")
    public Response<LoginResult> loginConfirm(@PathVariable("confirmRequestId") String confirmRequestId, LoginConfirmEnter enter) {
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

    @ApiOperation(value = "app 系统内验证手机号类型账户", notes = "app 系统内验证手机号类型账户")
    @RequestMapping(value = "/verifyAccount")
    public Response<GeneralResult> verifyAccount(VerifyAccountEnter enter) {
        return new Response<>(userTokenService.verifyAccount(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "刷新token", response = TokenResult.class)
    @PostMapping(value = "/refresh")
    public Response<TokenResult> refreshToken(@ModelAttribute RefreshTokenEnter enter) {
        return new Response<>(userTokenService.refreshToken(enter));
    }

    @ApiOperation(value = "登录后验证是否有设置过PIN", notes = "登录后验证是否有设置过PIN")
    @PostMapping(value = "/verifyPin")
    public Response<BooleanResult> verifyPin(@ModelAttribute GeneralEnter enter){
        return new Response<>(userTokenService.verifyPin(enter));
    }

    @ApiOperation(value = "设置PIN", notes = "设置PIN")
    @PostMapping(value = "/setPin")
    public Response<GeneralResult> setPin(@ModelAttribute PinEnter enter){
        return new Response<>(userTokenService.setPin(enter));
    }

    @ApiOperation(value = "修改PIN时校验登录密码", notes = "修改PIN时校验登录密码")
    @PostMapping(value = "/checkPwd")
    public Response<BooleanResult> checkPwd(@ModelAttribute StringEnter enter) {
        return new Response<>(userTokenService.checkPwd(enter));
    }

}


