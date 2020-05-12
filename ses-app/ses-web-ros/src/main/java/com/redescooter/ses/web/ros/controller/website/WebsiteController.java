package com.redescooter.ses.web.ros.controller.website;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.service.website.WebSiteService;
import com.redescooter.ses.web.ros.vo.customer.AccountListEnter;
import com.redescooter.ses.web.ros.vo.customer.AccountListResult;
import com.redescooter.ses.web.ros.vo.website.SignUpEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:Website
 * @description: Website
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 11:58
 */
@Log4j2
@Api(tags = {"官网模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/website")
public class WebsiteController {

    @Autowired
    private WebSiteService webSiteService;

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

}
