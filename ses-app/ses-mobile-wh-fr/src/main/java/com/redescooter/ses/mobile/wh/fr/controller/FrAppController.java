package com.redescooter.ses.mobile.wh.fr.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.common.vo.node.InputBatteryEnter;
import com.redescooter.ses.api.common.vo.node.InputScooterEnter;
import com.redescooter.ses.api.common.vo.node.InquiryDetailResult;
import com.redescooter.ses.api.common.vo.node.InquiryListAppEnter;
import com.redescooter.ses.api.common.vo.node.InquiryListResult;
import com.redescooter.ses.api.common.vo.node.SetModelEnter;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWarehouseAccount;
import com.redescooter.ses.mobile.wh.fr.service.app.FrAppService;
import com.redescooter.ses.mobile.wh.fr.vo.AppLoginEnter;
import com.redescooter.ses.api.common.vo.node.BindVinEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 13:55
 */
@Api(value = "法国仓库分车控制器", tags = "法国仓库分车控制器")
@CrossOrigin
@RestController
@RequestMapping("/app/fr")
public class FrAppController {

    @Autowired
    private FrAppService frAppService;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    @IgnoreLoginCheck
    public Response<TokenResult> login(@ModelAttribute AppLoginEnter enter) {
        return new Response<>(frAppService.login(enter));
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    @ApiOperation(value = "登出", notes = "登出")
    public Response<GeneralResult> logout(@ModelAttribute GeneralEnter enter) {
        return new Response<>(frAppService.logout(enter));
    }

    /**
     * 注册时输入邮箱校验是否可用
     */
    @PostMapping("/check")
    @ApiOperation(value = "注册时输入邮箱校验是否可用", notes = "注册时输入邮箱校验是否可用")
    @IgnoreLoginCheck
    public Response<BooleanResult> checkEmail(@ModelAttribute StringEnter enter) {
        return new Response<>(frAppService.checkEmail(enter));
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "注册")
    @IgnoreLoginCheck
    public Response<GeneralResult> register(@ModelAttribute AppLoginEnter enter) {
        return new Response<>(frAppService.register(enter));
    }

    /**
     * 获得个人信息
     */
    @PostMapping("/info")
    @ApiOperation(value = "获得个人信息", notes = "获得个人信息")
    public Response<OpeWarehouseAccount> getUserInfo(@ModelAttribute GeneralEnter enter) {
        return new Response<>(frAppService.getUserInfo(enter));
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "列表", notes = "列表")
    public Response<PageResult<InquiryListResult>> getList(@ModelAttribute InquiryListAppEnter enter) {
        return new Response<>(frAppService.getList(enter));
    }

    /**
     * 详情
     */
    @PostMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public Response<InquiryDetailResult> getDetail(@ModelAttribute IdEnter enter) {
        return new Response<>(frAppService.getDetail(enter));
    }

    /**
     * 根据rsn带出其他6个码
     */
    @PostMapping("/other")
    @ApiOperation(value = "根据rsn带出其他6个码", notes = "根据rsn带出其他6个码")
    public Response<Map<String, String>> getOtherCode(@ModelAttribute StringEnter enter) {
        return new Response<>(frAppService.getOtherCode(enter));
    }

    /**
     * 录入车辆
     */
    @PostMapping("/scooter")
    @ApiOperation(value = "录入车辆", notes = "录入车辆")
    public Response<GeneralResult> inputScooter(@ModelAttribute InputScooterEnter enter) {
        return new Response<>(frAppService.inputScooter(enter));
    }

    /**
     * 录入电池
     */
    @PostMapping("/battery")
    @ApiOperation(value = "录入电池", notes = "录入电池")
    public Response<List<String>> inputBattery(@ModelAttribute InputBatteryEnter enter) {
        return new Response<>(frAppService.inputBattery(enter));
    }

    /**
     * 设置软体
     */
    @PostMapping("/set")
    @ApiOperation(value = "设置软体", notes = "设置软体")
    public Response<GeneralResult> setScooterModel(@ModelAttribute SetModelEnter enter) {
        return new Response<>(frAppService.setScooterModel(enter));
    }

    /**
     * 录入vin
     */
    @PostMapping("/bind")
    @ApiOperation(value = "绑定VIN", notes = "绑定VIN")
    public Response<GeneralResult> bindVin(@ModelAttribute BindVinEnter enter) {
        return new Response<>(frAppService.bindVin(enter));
    }

}
