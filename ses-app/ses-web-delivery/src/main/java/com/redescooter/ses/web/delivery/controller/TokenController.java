package com.redescooter.ses.web.delivery.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.RefreshTokenEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderSaveOrUpdateEnter;
import com.redescooter.ses.api.foundation.service.base.UserTokenService;
import com.redescooter.ses.api.foundation.service.workorder.WorkOrderService;
import com.redescooter.ses.api.foundation.vo.account.ChanagePasswordEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.login.LoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @DubboReference
    private UserTokenService userTokenService;

    @DubboReference
    private WorkOrderService workOrderService;

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

    @PostMapping(value = "/workOrderSave")
    @ApiOperation(value = "工单新增", response = GeneralResult.class)
    public Response<GeneralResult> workOrderSave(@ModelAttribute @ApiParam("请求参数") WorkOrderSaveOrUpdateEnter enter) {
        return new Response(workOrderService.workOrderSave(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "刷新token", response = TokenResult.class)
    @PostMapping(value = "/refresh")
    public Response<TokenResult> refreshToken(@ModelAttribute RefreshTokenEnter enter) {
        return new Response<>(userTokenService.refreshToken(enter));
    }

}
