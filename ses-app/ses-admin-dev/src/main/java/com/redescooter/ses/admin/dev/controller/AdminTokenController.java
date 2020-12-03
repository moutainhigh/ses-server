package com.redescooter.ses.admin.dev.controller;

import com.redescooter.ses.admin.dev.service.base.AdminTokenService;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameAdminTokenController
 * @Description
 * @Author Aleks
 * @Date2020/12/3 9:46
 * @Version V1.0
 **/
@Api(tags = {"OMS登陆相关控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/admin/dev")
public class AdminTokenController {

    @Autowired
    private AdminTokenService adminTokenService;


    @IgnoreLoginCheck
    @ApiOperation(value = "登录", response = TokenResult.class)
    @PostMapping(value = "/login")
    @LogAnnotation
    public Response<TokenResult> login(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(adminTokenService.login(enter));
    }


    @IgnoreLoginCheck
    @ApiOperation(value = "邮箱加验证码登录给用户发邮件（邮件里面是验证码）", response = GeneralResult.class)
    @PostMapping(value = "/emailLoginSendCode")
    public Response<GeneralResult> emailLoginSendCode(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(adminTokenService.emailLoginSendCode(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "邮箱加验证码登录", response = TokenResult.class)
    @PostMapping(value = "/emailLogin")
    public Response<TokenResult> emailLogin(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(adminTokenService.emailLogin(enter));
    }


    @IgnoreLoginCheck
    @ApiOperation(value = "修改密码", response = GeneralResult.class)
    @PostMapping(value = "/chanage")
    public Response<GeneralResult> chanagePassword(@ModelAttribute @ApiParam("请求参数") ModifyPasswordEnter enter) {
        return new Response<>(adminTokenService.modifyPassword(enter));
    }


    @ApiOperation(value = "登出", response = GeneralResult.class)
    @PostMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(adminTokenService.logout(enter));
    }


}
