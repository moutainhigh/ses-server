package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
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
@Api(tags = {"Login"})
@CrossOrigin
@RestController
@RequestMapping(value = "/token/auth")
public class LoginController {

    @Autowired
    private TokenWebsiteService tokenWebsiteService;

    @IgnoreLoginCheck
    @ApiOperation(value = "login", response = TokenResult.class)
    @PostMapping(value = "/login")
    @LogAnnotation
    public Response<TokenResult> login(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(tokenWebsiteService.login(enter));
    }

    @ApiOperation(value = "logout", response = GeneralResult.class)
    @PostMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tokenWebsiteService.logout(enter));
    }
}
