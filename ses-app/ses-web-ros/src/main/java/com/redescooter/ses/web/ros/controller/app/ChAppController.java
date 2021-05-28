package com.redescooter.ses.web.ros.controller.app;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.service.app.ChAppService;
import com.redescooter.ses.web.ros.vo.app.AppLoginEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/28 10:37
 */
@Api(value = "中国仓库控制器", tags = "中国仓库控制器")
@CrossOrigin
@RestController
@RequestMapping("/app/ch")
public class ChAppController {

    @Autowired
    private ChAppService chAppService;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    @IgnoreLoginCheck
    public Response<TokenResult> login(@ModelAttribute AppLoginEnter enter) {
        return new Response<>(chAppService.login(enter));
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    @ApiOperation(value = "登出", notes = "登出")
    @IgnoreLoginCheck
    public Response<GeneralResult> logout(@ModelAttribute GeneralEnter enter) {
        return new Response<>(chAppService.logout(enter));
    }

    /**
     * 获得个人信息
     */
    @PostMapping("/info")
    @ApiOperation(value = "获得个人信息", notes = "获得个人信息")
    @IgnoreLoginCheck
    public Response<OpeWarehouseAccount> getUserInfo(@ModelAttribute GeneralEnter enter) {
        return new Response<>(chAppService.getUserInfo(enter));
    }









}
