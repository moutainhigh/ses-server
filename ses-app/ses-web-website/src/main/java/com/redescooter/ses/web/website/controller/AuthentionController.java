package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.GetAccountKeyResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.web.website.service.TokenWebsiteService;
import com.redescooter.ses.web.website.vo.login.RefreshTokenEnter;
import com.redescooter.ses.web.website.vo.login.SiteResetPassword;
import com.redescooter.ses.web.website.vo.login.SiteSetPasswordEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jerry
 * @Date 2021/1/7 2:27 上午
 * @Description 登录服务
 **/
@Api(tags = {"Authention"})
@CrossOrigin
@RestController
@RequestMapping(value = "/token/auth")
public class AuthentionController {

    @Autowired
    private TokenWebsiteService tokenWebsiteService;

    /**
     * 获取加密公钥
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/publicSecret")
    @ApiOperation(value = "Get public key", response = GetAccountKeyResult.class)
    public Response<GetAccountKeyResult> publicSecret(GeneralEnter enter) {
        return new Response(tokenWebsiteService.getAccountKey(enter));
    }

    /**
     * 登录
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @LogAnnotation
    @PostMapping(value = "/login")
    @ApiOperation(value = "login", response = TokenResult.class)
    public Response<TokenResult> login(@ModelAttribute @ApiParam("Parameter") LoginEnter enter) {
        return new Response<>(tokenWebsiteService.login(enter));
    }

    /**
     * 登出
     *
     * @param enter
     * @return
     */
    @ApiOperation(value = "logout", response = GeneralResult.class)
    @PostMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(tokenWebsiteService.logout(enter));
    }

    /**
     * 忘记密码
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @ApiOperation(value = "Forget Password Operation", response = GeneralResult.class)
    @PostMapping(value = "/forgetPassword")
    public Response<GeneralResult> forgetPassword(@ModelAttribute @ApiParam("Parameter") BaseSendMailEnter enter) {
        return new Response<>(tokenWebsiteService.forgetPasswordEmail(enter));
    }

    /**
     * 设置密码
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @ApiOperation(value = "Set password", response = GeneralResult.class)
    @PostMapping(value = "/setPassword")
    public Response<GeneralResult> setPassword(@ModelAttribute @ApiParam("Parameter") SiteSetPasswordEnter enter) {
        return new Response<>(tokenWebsiteService.setPassword(enter));
    }

    /**
     * 重置密码
     *
     * @param enter
     * @return
     */
    @WebsiteSignIn
    @PostMapping(value = "/resetPassword")
    @ApiOperation(value = "Reset Password", response = GeneralResult.class)
    public Response<GeneralResult> resetPassword(@ModelAttribute @ApiParam("Parameter") SiteResetPassword enter) {
        return new Response<>(tokenWebsiteService.resetPassword(enter));
    }

    /**
     * 刷新token
     */
    @ApiOperation(value = "刷新token", response = GeneralResult.class)
    @PostMapping(value = "/refresh")
    public Response<TokenResult> refreshToken(@ModelAttribute RefreshTokenEnter enter) {
        return new Response<>(tokenWebsiteService.refreshToken(enter));
    }

}
