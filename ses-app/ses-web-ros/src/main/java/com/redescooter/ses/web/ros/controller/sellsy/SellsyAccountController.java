package com.redescooter.ses.web.ros.controller.sellsy;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.SellsyAccountSettingService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.account.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"ROS-Sellsy-Account"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sellsy/account")
public class SellsyAccountController {

    @Autowired
    private SellsyAccountSettingService sellsyAccountSettingService;

    @IgnoreLoginCheck
    @ApiOperation(value = "查询货币单位", response = SellsyCurrencyResult.class)
    @PostMapping(value = "/queryCurrencyList")
    public Response<List<SellsyCurrencyResult>> queryCurrencyList() {
        return new Response<>(sellsyAccountSettingService.queryCurrencyList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "货币详情", response = SellsyCurrencyResult.class)
    @PostMapping(value = "/queryCurrencyOne")
    public Response<SellsyCurrencyResult> queryCurrencyOne(@ModelAttribute @ApiParam("请求参数") SellsyIdEnter enter) {
        return new Response<>(sellsyAccountSettingService.queryCurrencyOne(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "布局列表", response = SellsyLayoutResult.class)
    @PostMapping(value = "/queryDocLayoutList")
    public Response<List<SellsyLayoutResult>> queryDocLayoutList() {
        return new Response<>(sellsyAccountSettingService.queryDocLayoutList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "支付日期列表", response = SellsyPayDateResult.class)
    @PostMapping(value = "/queryPayDateList")
    public Response<List<SellsyPayDateResult>> queryPayDateList() {
        return new Response<>(sellsyAccountSettingService.queryPayDateList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "税率方式（HT、HTT）", response = SellsyRateCategoryResult.class)
    @PostMapping(value = "/queryRateCategoryList")
    public Response<List<SellsyRateCategoryResult>> queryRateCategoryList() {
        return new Response<>(sellsyAccountSettingService.queryRateCategoryList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "税率方式详情（HT、HTT）", response = SellsyRateCategoryResult.class)
    @PostMapping(value = "/queryateCategoryOne")
    public Response<SellsyRateCategoryResult>
        queryateCategoryOne(@ModelAttribute @ApiParam("请求参数") SellsyIdEnter enter) {
        return new Response<>(sellsyAccountSettingService.queryateCategoryOne(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "查询翻译语言", response = SellsyTranslationLanguageResult.class)
    @PostMapping(value = "/queryTranslationLanguages")
    public Response<List<SellsyTranslationLanguageResult>> queryTranslationLanguages() {
        return new Response<>(sellsyAccountSettingService.queryTranslationLanguages());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "查询地址列表", response = SellsyAddressResult.class)
    @PostMapping(value = "/queryAddressList")
    public Response<List<SellsyAddressResult>> queryAddressList() {
        return new Response<>(sellsyAccountSettingService.queryAddressList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "地址详情", response = SellsyAddressResult.class)
    @PostMapping(value = "/queryAddressOne")
    public Response<SellsyAddressResult> queryAddressOne(@ModelAttribute @ApiParam("请求参数") SellsyIdEnter enter) {
        return new Response<>(sellsyAccountSettingService.queryAddressOne(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "税率列表", response = SellsyTaxeResult.class)
    @PostMapping(value = "/queryTaxeList")
    public Response<List<SellsyTaxeResult>> queryTaxeList() {
        return new Response<>(sellsyAccountSettingService.queryTaxeList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "税率详情", response = SellsyTaxeResult.class)
    @PostMapping(value = "/queryTaxeOne")
    public Response<SellsyTaxeResult> queryTaxeOne(@ModelAttribute @ApiParam("请求参数") SellsyIdEnter enter) {
        return new Response<>(sellsyAccountSettingService.queryTaxeOne(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "物流公司列表", response = SellsyShippingResult.class)
    @PostMapping(value = "/queryShippingList")
    public Response<List<SellsyShippingResult>> queryShippingList() {
        return new Response<>(sellsyAccountSettingService.queryShippingList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "物流公司详情", response = SellsyShippingResult.class)
    @PostMapping(value = "/queryShippingOne")
    public Response<SellsyShippingResult> queryShippingOne(@ModelAttribute @ApiParam("请求参数") SellsyIdEnter enter) {
        return new Response<>(sellsyAccountSettingService.queryShippingOne(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "计量单位列表", response = SellsyUnitResult.class)
    @PostMapping(value = "/queryUnitList")
    public Response<List<SellsyUnitResult>> queryUnitList() {
        return new Response<>(sellsyAccountSettingService.queryUnitList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "计量单位详情", response = SellsyUnitResult.class)
    @PostMapping(value = "/queryUnitOne")
    public Response<SellsyUnitResult> queryUnitOne(@ModelAttribute @ApiParam("请求参数") SellsyIdEnter enter) {
        return new Response<>(sellsyAccountSettingService.queryUnitOne(enter));
    }

}
