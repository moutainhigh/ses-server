package com.redescooter.ses.web.ros.controller.other.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.common.CountryCityResult;
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

import java.util.List;

/**
 * @ClassName:Website
 * @description: Website
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 11:58
 */
@Log4j2
@CrossOrigin
@RestController
@RequestMapping(value = "/website")
@Api(tags = {"Official Website Authentication"})
public class WebsiteTokenController {

    @Autowired
    private WebSiteTokenService webSiteService;

    @Autowired
    private MondayService mondayService;

    @IgnoreLoginCheck
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录", response = TokenResult.class)
    public Response<TokenResult> login(@ModelAttribute @ApiParam("Parameter") LoginEnter enter) {
        return new Response<>(webSiteService.login(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/logout")
    @ApiOperation(value = "登出", response = GeneralResult.class)
    public Response<GeneralResult> logout(@ModelAttribute @ApiParam("Parameter") GeneralEnter enter) {
        return new Response<>(webSiteService.logout(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/signUp")
    @ApiOperation(value = "注册", response = GeneralResult.class)
    public Response<GeneralResult> signUp(@ModelAttribute @ApiParam("Parameter") SignUpEnter enter) {
        return new Response<>(webSiteService.signUp(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/sendEmail")
    @ApiOperation(value = "发送邮件", response = GeneralResult.class)
    public Response<GeneralResult> sendEmail(@ModelAttribute BaseSendMailEnter enter) {
        return new Response<>(webSiteService.sendEmail(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/forgetPassword")
    @ApiOperation(value = "忘记密码", response = GeneralResult.class)
    public Response<GeneralResult> forgetPassword(@ModelAttribute @ApiParam("Parameter") WebResetPasswordEnter enter) {
        return new Response<>(webSiteService.forgetPassword(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/resetPassword")
    @ApiOperation(value = "修改密码", response = GeneralResult.class)
    public Response<GeneralResult> resetPassword(@ModelAttribute @ApiParam("Parameter") WebResetPasswordEnter enter) {
        return new Response<>(webSiteService.resetPassword(enter));
    }

    @WebsiteSignIn
    @PostMapping(value = "/editCustomer")
    @ApiOperation(value = "修改客户信息", response = GeneralResult.class)
    public Response<GeneralResult> editCustomer(@ModelAttribute @ApiParam("Parameter") WebEditCustomerEnter enter) {
        return new Response<>(webSiteService.editCustomer(enter));
    }

    @IgnoreLoginCheck
    @PostMapping("/countryAndCity")
    @ApiOperation(value = "获得国家和城市", notes = "这个接口的前端只被调用一次，数据被保存到缓存中")
    public Response<List<CountryCityResult>> countryAndCity() {
        return new Response<>(webSiteService.countryAndCity());
    }

    @IgnoreLoginCheck
    @PostMapping("/countryCityPostCode")
    @ApiOperation(value = "获取邮编")
    public Response<List<CountryCityResult>> countryCityPostCode(@ModelAttribute @ApiParam("Parameter") CityNameEnter cityNameEnter) {
        return new Response<>(webSiteService.countryCityPostCode(cityNameEnter));
    }

}


