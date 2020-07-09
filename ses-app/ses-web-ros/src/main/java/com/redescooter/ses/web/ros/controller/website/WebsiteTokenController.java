package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import com.redescooter.ses.web.ros.vo.website.SignUpEnter;
import com.redescooter.ses.web.ros.vo.website.WebEditCustomerEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName:Website
 * @description: Website
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 11:58
 */
@Log4j2
@Api(tags = {"官网登录模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/website")
public class WebsiteTokenController {

    @Autowired
    private WebSiteTokenService webSiteService;

    @Autowired
    private MondayService mondayService;

    @IgnoreLoginCheck
    @PostMapping(value = "/login")
    @ApiOperation(value = "官网登录", response = TokenResult.class)
    public Response<TokenResult> login(@ModelAttribute @ApiParam("请求参数") LoginEnter enter) {
        return new Response<>(webSiteService.login(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/logout")
    @ApiOperation(value = "官网登出", response = GeneralResult.class)
    public Response<GeneralResult> logout(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(webSiteService.logout(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/signUp")
    @ApiOperation(value = "官网注册", response = GeneralResult.class)
    public Response<GeneralResult> signUp(@ModelAttribute @ApiParam("请求参数") SignUpEnter enter) {
        return new Response<>(webSiteService.signUp(enter));
    }


    @IgnoreLoginCheck
    @ApiOperation(value = "邮件发送", response = GeneralResult.class)
    @PostMapping(value = "/sendEmail")
    public Response<GeneralResult> sendEmail(@ModelAttribute BaseSendMailEnter enter) {
        return new Response<>(webSiteService.sendEmail(enter));
    }


    @IgnoreLoginCheck
    @PostMapping(value = "/forgetPassword")
    @ApiOperation(value = "官网上面忘记密码", response = GeneralResult.class)
    public Response<GeneralResult> forgetPassword(@ModelAttribute @ApiParam("请求参数") WebResetPasswordEnter enter) {
        return new Response<>(webSiteService.forgetPassword(enter));
    }


    @WebsiteSignIn
    @PostMapping(value = "/resetPassword")
    @ApiOperation(value = "官网上面修改密码", response = GeneralResult.class)
    public Response<GeneralResult> resetPassword(@ModelAttribute @ApiParam("请求参数") WebResetPasswordEnter enter) {
        return new Response<>(webSiteService.resetPassword(enter));
    }


    @WebsiteSignIn
    @PostMapping(value = "/editCustomer")
    @ApiOperation(value = "官网上面修改用户信息", response = GeneralResult.class)
    public Response<GeneralResult> editCustomer(@ModelAttribute @ApiParam("请求参数") WebEditCustomerEnter enter) {
        return new Response<>(webSiteService.editCustomer(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/getMondayData")
    @ApiOperation(value = "获取Monday数据", response = GeneralResult.class)
    public Response<GeneralResult> getMondayData(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(mondayService.getMondayData(enter));
    }

}
