package com.redescooter.ses.mobile.rps.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.mobile.rps.service.TokenRpsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述:
 *
 * @param:
 * @auther: jerry
 * @date: 2019-05-28 13:57
 */

@Api(tags = {"RPS-Sign"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sign/token")
public class TokenController {

    @Autowired
    private TokenRpsService tokenRpsService;

    @IgnoreLoginCheck
    @ApiOperation(value = "登录", response = TokenResult.class)
    @PostMapping(value = "/login")
    public Response<TokenResult> login(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(tokenRpsService.login(enter));
    }

    @ApiOperation(value = "登出", response = GeneralResult.class)
    @PostMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tokenRpsService.logout(enter));
    }

    @ApiOperation(value = "修改密码", response = GeneralResult.class)
    @PostMapping(value = "/chanage/{code}")
    public Response<GeneralResult> chanagePassword(@PathVariable("code") String code, @ModelAttribute @ApiParam("请求参数") ModifyPasswordEnter enter) {
        return new Response<>(tokenRpsService.modifyPassword(enter));
    }


    @IgnoreLoginCheck
    @ApiOperation(value = "RPS登录", response = TokenResult.class)
    @PostMapping(value = "/rpsLogin")
    public Response<TokenResult> rpsLogin(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(tokenRpsService.rpsLogin(enter));
    }
}
