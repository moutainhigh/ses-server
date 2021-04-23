package com.redescooter.ses.web.website.controller.business;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.ScooterPurchaseService;
import com.redescooter.ses.web.website.vo.parts.PartsDetailsResult;
import com.redescooter.ses.web.website.vo.product.ModelPriceResult;
import com.redescooter.ses.web.website.vo.product.ProductPartsDetailsResult;
import com.redescooter.ses.web.website.vo.product.ProductsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 13/1/2020 4:43 下午
 * @ClassName: ProductModelController
 * @Function: 车辆选购服务
 */
@Api(tags = {"Scooter Purchase"})
@CrossOrigin
@RestController
@RequestMapping(value = "/scooter/purchase")
public class ScooterPurchaseController {

    @Autowired
    private ScooterPurchaseService scooterPurchaseService;

    /**
     * 车型列表
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/modelist")
    @ApiOperation(value = "车型列表", response = ModelPriceResult.class)
    public Response<List<ModelPriceResult>> modelList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(scooterPurchaseService.modelAndPriceList(enter));
    }

    /**
     * 获取产品详情
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/detailsByModel")
    @ApiOperation(value = "获取产品详情", response = ProductsResult.class)
    public Response<List<ProductsResult>> detailsByModel(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(scooterPurchaseService.getProductDetailByModel(enter));
    }

    /**
     * 获取配件列表
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/getpartslist")
    @ApiOperation(value = "获取配件列表", response = PartsDetailsResult.class)
    public Response<List<PartsDetailsResult>> getPartsList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(scooterPurchaseService.getPartsList(enter));
    }

    /**
     * 获取车辆电池配置列表
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/getscooterbatterys")
    @ApiOperation(value = "获取车辆电池配置列表", response = ProductPartsDetailsResult.class)
    public Response<List<ProductPartsDetailsResult>> getScooterBatterys(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(scooterPurchaseService.getScooterBatterysByProductId(enter));
    }

}
