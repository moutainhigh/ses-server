package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.api.proxy.vo.mail.SendMailEnter;
import com.redescooter.ses.web.ros.service.TokenRosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:
 *
 * @param:
 * @auther: jerry
 * @date: 2019-05-28 13:57
 */

@Api(tags = {"Sign-In"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sign/token")
public class TokenController {

    @Autowired
    private TokenRosService tokenRosService;

    @IgnoreLoginCheck
    @ApiOperation(value = "登录接口", response = LoginEnter.class)
    @PostMapping(value = "/login")
    public Response<TokenResult> login(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(tokenRosService.login(enter));
    }

    @ApiOperation(value = "登出接口", response = GeneralResult.class)
    @PostMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tokenRosService.logout(enter));
    }

    @ApiOperation(value = "修改密码接口", response = GeneralResult.class)
    @PostMapping(value = "/chanage/{code}")
    public Response<GeneralResult> chanagePassword(@PathVariable("code") String code, @ModelAttribute @ApiParam("请求参数") ModifyPasswordEnter enter) {
        return new Response<>(tokenRosService.modifyPassword(enter));
    }

    @ApiOperation(value = "发送验证邮件接口", response = GeneralResult.class)
    @PostMapping(value = "/sendCode")
    public Response<GeneralResult> sendCode(@ModelAttribute @ApiParam("请求参数") SendMailEnter enter) {
        return new Response<>(tokenRosService.sendCode(enter));
    }

}
