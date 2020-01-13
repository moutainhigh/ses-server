package com.redescooter.ses.web.delivery.controller;

import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.delivery.service.TenantSettingService;
import com.redescooter.ses.web.delivery.vo.PageBootTipResult;
import com.redescooter.ses.web.delivery.vo.TenantInforResult;
import com.redescooter.ses.web.delivery.vo.UpdateCustomerInfoEnter;
import com.redescooter.ses.web.delivery.vo.UpdateTenantConfigEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:TenantSettingController
 * @description: TenantSettingController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 15:01
 */
@Api(tags = {"店铺设置"})
@CrossOrigin
@RestController
@RequestMapping(value = "/setting", method = RequestMethod.POST)
public class TenantSettingController {

    @Autowired
    private TenantSettingService tenantSettingService;

    @PostMapping(value = "/tenantInfor")
    @ApiOperation(value = "租户信息", response = TenantInforResult.class)
    public Response<TenantInforResult> tenantInfor(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tenantSettingService.tenantInfor(enter));
    }

    @PostMapping(value = "/customerInfor")
    @ApiOperation(value = "客户信息", response = BaseCustomerResult.class)
    public Response<BaseCustomerResult> customerInfor(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tenantSettingService.customerInfor(enter));
    }

    @PostMapping(value = "/updateTenantConfig")
    @ApiOperation(value = "更新租户配置", response = GeneralResult.class)
    public Response<GeneralResult> updateTenantConfig(@ModelAttribute @ApiParam("请求参数") UpdateTenantConfigEnter enter) {
        return new Response<>(tenantSettingService.updateTenantConfig(enter));
    }

    @PostMapping(value = "/updateCustomerInfo")
    @ApiOperation(value = "更新客户信息", response = GeneralResult.class)
    public Response<GeneralResult> updateCustomerInfo(@ModelAttribute @ApiParam("请求参数") UpdateCustomerInfoEnter enter) {
        return new Response<>(tenantSettingService.updateCustomerInfo(enter));
    }

    @PostMapping(value = "/pageBootTip")
    @ApiOperation(value = "获取引导页信息", response = PageBootTipResult.class)
    public Response<PageBootTipResult> pageBootTip(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tenantSettingService.pageBootTip(enter));
    }

    @PostMapping(value = "/closePageBootTip")
    @ApiOperation(value = "更新引导页信息", response = GeneralResult.class)
    public Response<GeneralResult> closePageBootTip(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(tenantSettingService.closePageBootTip(enter));
    }


}
