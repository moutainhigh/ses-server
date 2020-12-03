package com.redescooter.ses.admin.dev.controller;

import com.redescooter.ses.admin.dev.service.base.AdminTokenService;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
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
        return new Response<>();
    }





}
