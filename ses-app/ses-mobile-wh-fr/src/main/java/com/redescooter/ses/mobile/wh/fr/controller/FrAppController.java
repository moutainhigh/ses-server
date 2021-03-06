package com.redescooter.ses.mobile.wh.fr.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWarehouseAccount;
import com.redescooter.ses.mobile.wh.fr.service.app.FrAppService;
import com.redescooter.ses.mobile.wh.fr.vo.AppLoginEnter;
import com.redescooter.ses.mobile.wh.fr.vo.BindLicensePlateEnter;
import com.redescooter.ses.mobile.wh.fr.vo.BindVinEnter;
import com.redescooter.ses.mobile.wh.fr.vo.CustomerIdEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InputBatteryEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InputScooterEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryDetailEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryDetailResult;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryListAppEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 获得个人信息
     */
    @PostMapping("/info")
    @ApiOperation(value = "获得个人信息", notes = "获得个人信息")
    public Response<OpeWarehouseAccount> getUserInfo(@ModelAttribute GeneralEnter enter) {
        return new Response<>(frAppService.getUserInfo(enter));
    }

    /**
     * 检索数据下拉列表
     */
    @PostMapping("/data")
    @ApiOperation(value = "检索数据下拉列表", notes = "检索数据下拉列表")
    public Response<List<String>> getDataList(@ModelAttribute StringEnter enter) {
        return new Response<>(frAppService.getDataList(enter));
    }

    /**
     * 询价单列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "询价单列表", notes = "询价单列表")
    public Response<PageResult<InquiryListResult>> getList(@ModelAttribute InquiryListAppEnter enter) {
        return new Response<>(frAppService.getList(enter));
    }

    /**
     * 询价单详情
     */
    @PostMapping("/detail")
    @ApiOperation(value = "询价单详情", notes = "询价单详情")
    public Response<InquiryDetailResult> getDetail(@ModelAttribute InquiryDetailEnter enter) {
        return new Response<>(frAppService.getDetail(enter));
    }

    /**
     * 绑定VIN
     */
    @PostMapping("/bind")
    @ApiOperation(value = "绑定VIN", notes = "绑定VIN")
    public Response<GeneralResult> bindVin(@ModelAttribute BindVinEnter enter) {
        return new Response<>(frAppService.bindVin(enter));
    }

    /**
     * 绑定车牌
     */
    @PostMapping("/plate")
    @ApiOperation(value = "绑定车牌", notes = "绑定车牌")
    public Response<GeneralResult> bindLicensePlate(@ModelAttribute BindLicensePlateEnter enter) {
        return new Response<>(frAppService.bindLicensePlate(enter));
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
    public Response<GeneralResult> inputBattery(@ModelAttribute InputBatteryEnter enter) {
        return new Response<>(frAppService.inputBattery(enter));
    }

    /**
     * 设置软体
     */
    @PostMapping("/set")
    @ApiOperation(value = "设置软体", notes = "设置软体")
    public Response<GeneralResult> setScooterModel(@ModelAttribute CustomerIdEnter enter) {
        return new Response<>(frAppService.setScooterModel(enter));
    }

}
