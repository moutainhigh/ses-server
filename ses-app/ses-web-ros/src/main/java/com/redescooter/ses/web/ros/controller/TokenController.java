package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.ModifyPasswordEnter;
import com.redescooter.ses.web.ros.service.base.TokenRosService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.vo.account.AddSysUserEnter;
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

@Api(tags = {"ROS-Sign"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sign/token")
public class TokenController {

    @Autowired
    private TokenRosService tokenRosService;
    
    @Autowired
    private SellsyService sellsyService;
    
    @IgnoreLoginCheck
    @ApiOperation(value = "获取密钥", response = GetAccountKeyResult.class)
    @PostMapping(value = "/getAccountKey")
    public Response<GetAccountKeyResult> getAccountKey(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tokenRosService.getAccountKey(enter));
    }


    @IgnoreLoginCheck
    @ApiOperation(value = "登录", response = TokenResult.class)
    @PostMapping(value = "/login")
    public Response<TokenResult> login(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(tokenRosService.login(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "邮箱加验证码登录给用户发邮件（邮件里面是验证码）", response = GeneralResult.class)
    @PostMapping(value = "/emailLoginSendCode")
    public Response<GeneralResult> emailLoginSendCode(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(tokenRosService.emailLoginSendCode(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "邮箱加验证码登录", response = TokenResult.class)
    @PostMapping(value = "/emailLogin")
    public Response<TokenResult> emailLogin(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(tokenRosService.emailLogin(enter));
    }


    @ApiOperation(value = "登出", response = GeneralResult.class)
    @PostMapping(value = "/logout")
    public Response<GeneralResult> logout(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tokenRosService.logout(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "修改密码", response = GeneralResult.class)
    @PostMapping(value = "/chanage")
    public Response<GeneralResult> chanagePassword(@ModelAttribute @ApiParam("请求参数") ModifyPasswordEnter enter) {
        return new Response<>(tokenRosService.modifyPassword(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "添加ROS用户", response = GeneralResult.class)
    @PostMapping(value = "/createRosUser")
    public Response<GeneralResult> createRosUser(@ModelAttribute @ApiParam("请求参数") AddSysUserEnter enter) {
        return new Response<>(tokenRosService.createRosUser(enter));
    }

    @ApiOperation(value = "删除ROS用户", response = GeneralResult.class)
    @PostMapping(value = "/deleteRosUser")
    public Response<GeneralResult> deleteRosUser(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(tokenRosService.deleteRosUser(enter));
    }

    @ApiOperation(value = "发送验证码", response = GeneralResult.class)
    @PostMapping(value = "/sendCode")
    public Response<GeneralResult> sendCode(@ModelAttribute @ApiParam("请求参数") BaseSendMailEnter enter) {
        return new Response<>(tokenRosService.sendCode(enter));
    }
    @IgnoreLoginCheck
    @ApiOperation(value = "发送邮箱", response = BooleanResult.class)
    @PostMapping(value = "/sendForgetPasswordEmail")
    public Response<GeneralResult> sendForgetPasswordEmail(@ModelAttribute @ApiParam("请求参数") BaseSendMailEnter enter) {
      return new Response<>(tokenRosService.sendForgetPasswordEmail(enter));
    }
    
    @IgnoreLoginCheck
    @ApiOperation(value = "12321", response = BooleanResult.class)
    @PostMapping(value = "/1234")
    public Response<GeneralResult> test(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        //sellsyService.test();
        return new Response<>();
    }
    
}
