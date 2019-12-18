package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.service.UserTokenService;
import com.redescooter.ses.api.foundation.vo.login.UserLoginConfirmEnter;
import com.redescooter.ses.api.foundation.vo.login.UserLoginEnter;
import com.redescooter.ses.api.foundation.vo.login.UserLoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
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
@RequestMapping(value = "/user/token", method = RequestMethod.POST)
public class UserTokenController {

    @Reference
    private UserTokenService userTokenService;

    @IgnoreLoginCheck
    @ApiOperation(value = "登入确认")
    @RequestMapping(value = "/login/confirm/{confirmRequestId}")
    public Response<UserLoginResult> loginConfirm(@PathVariable("confirmRequestId") String confirmRequestId, UserLoginConfirmEnter enter) {
        log.info("多用户登录RequestId==={}",confirmRequestId);
        return new Response<>(userTokenService.loginConfirm(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "登入接口")
    @RequestMapping(value = "/login")
    public Response<UserLoginResult> login(@ModelAttribute UserLoginEnter enter) {
        return new Response<>(userTokenService.login(enter));
    }

    @ApiOperation(value = "登出注销")
    @RequestMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute GeneralEnter enter) {
        return new Response<>(userTokenService.logout(enter));
    }


}


