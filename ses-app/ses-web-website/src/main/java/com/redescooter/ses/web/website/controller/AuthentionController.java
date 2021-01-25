package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.web.website.service.TokenWebsiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "login", response = TokenResult.class)
    @PostMapping(value = "/login")
    @LogAnnotation
    public Response<TokenResult> login(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
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
    public Response<GeneralResult> logout(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tokenWebsiteService.logout(enter));
    }

    /**
     * 忘记密码
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @ApiOperation(value = "Forget the password", response = GeneralResult.class)
    @PostMapping(value = "/forgetPassword")
    public Response<GeneralResult> forgetPassword(@ModelAttribute @ApiParam("请求参数") BaseSendMailEnter enter) {
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
    public Response<GeneralResult> setPassword(@ModelAttribute @ApiParam("请求参数") ModifyPasswordEnter enter) {
        return new Response<>(tokenWebsiteService.setPassword(enter));
    }

    /**
     * 修改密码
     *
     * @param enter
     * @return
     */
    @WebsiteSignIn
    @PostMapping(value = "/editPassword")
    @ApiOperation(value = "Edit Password", response = GeneralResult.class)
    public Response<GeneralResult> editPassword(@ModelAttribute @ApiParam("Parameter") ModifyPasswordEnter enter) {
        return new Response<>(tokenWebsiteService.editPassword(enter));
    }
}
