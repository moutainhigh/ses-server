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
 * @Date: 21/11/2019 4:38 ??????
 * @ClassName: UserTokenController
 * @Function: TODO
 */
@Slf4j
@Api(tags = "????????????")
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
    @ApiOperation(value = "????????????", response = LoginResult.class)
    @RequestMapping(value = "/login")
    public Response<LoginResult> login(@ModelAttribute LoginEnter enter) {
        return new Response<>(userTokenService.login(enter));
    }

    @ApiOperation(value = "????????????", response = GeneralResult.class)
    @RequestMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute GeneralEnter enter) {
        userTokenService.logout(enter);
        // ????????????app???,??????emq?????????????????????????????????
        userProfileService.sendLockInstructionByEMQ(enter);
        return new Response<>();
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "????????????", response = LoginResult.class)
    @RequestMapping(value = "/login/confirm/{confirmRequestId}")
    public Response<LoginResult> loginConfirm(@PathVariable("confirmRequestId") String confirmRequestId, LoginConfirmEnter enter) {
        enter.setConfirmRequestId(confirmRequestId);
        return new Response<>(userTokenService.loginConfirm(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "???????????????", response = GeneralResult.class)
    @RequestMapping(value = "/sendCode")
    public Response<GeneralResult> sendCode(@ModelAttribute BaseSendMailEnter enter) {
        return new Response<>(tokenService.sendCode(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    @RequestMapping(value = "/setPassword/{requestId}")
    public Response<GeneralResult> sendCode(@PathVariable("requestId") String requestId, @ModelAttribute SetPasswordEnter enter) {
        enter.setRequestId(requestId);
        return new Response<>(tokenService.setPassword(enter));
    }

    @ApiOperation(value = "????????????id?????????id????????????", notes = "????????????id?????????id????????????")
    @RequestMapping(value = "/logInPush")
    public Response<GeneralResult> logInPush(LoginPushEnter enter) {
        return new Response<>(loginJPushProService.logInPush(enter));
    }

    @ApiOperation(value = "????????????id?????????id????????????", notes = "????????????id?????????id????????????")
    @RequestMapping(value = "/logOutPush")
    public Response<GeneralResult> logOutPush(LoginPushEnter enter) {

        return new Response<>(loginJPushProService.logOutPush(enter));
    }

    @ApiOperation(value = "app ????????????????????????????????????", notes = "app ????????????????????????????????????")
    @RequestMapping(value = "/verifyAccount")
    public Response<GeneralResult> verifyAccount(VerifyAccountEnter enter) {
        return new Response<>(userTokenService.verifyAccount(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "??????token", response = TokenResult.class)
    @PostMapping(value = "/refresh")
    public Response<TokenResult> refreshToken(@ModelAttribute RefreshTokenEnter enter) {
        return new Response<>(userTokenService.refreshToken(enter));
    }

    @ApiOperation(value = "?????????????????????????????????PIN", notes = "?????????????????????????????????PIN")
    @PostMapping(value = "/verifyPin")
    public Response<BooleanResult> verifyPin(@ModelAttribute GeneralEnter enter){
        return new Response<>(userTokenService.verifyPin(enter));
    }

    @ApiOperation(value = "??????PIN", notes = "??????PIN")
    @PostMapping(value = "/setPin")
    public Response<GeneralResult> setPin(@ModelAttribute PinEnter enter){
        return new Response<>(userTokenService.setPin(enter));
    }

    @ApiOperation(value = "??????PIN?????????????????????", notes = "??????PIN?????????????????????")
    @PostMapping(value = "/checkPwd")
    public Response<BooleanResult> checkPwd(@ModelAttribute StringEnter enter) {
        return new Response<>(userTokenService.checkPwd(enter));
    }

}


