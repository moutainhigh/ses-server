package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.ScooterPurchaseService;
import com.redescooter.ses.web.website.vo.product.ModelPriceResult;
import com.redescooter.ses.web.website.vo.product.ProductsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 13/1/2020 4:43 下午
 * @ClassName: ProductModelController
 * @Function: 车辆选购服务
 */
@Api(tags = {"Product Model Management"})
@CrossOrigin
@RestController
@RequestMapping(value = "/model")
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
    @PostMapping(value = "/list")
    @ApiOperation(value = "Scooter model list", response = ModelPriceResult.class)
    public Response<List<ModelPriceResult>> modelList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(scooterPurchaseService.modelPriceList(enter));
    }
    /**
     * 获取产品详情
     *
     *
     * @param enter
     * @return
     */
    @IgnoreLoginCheck
    @PostMapping(value = "/getProductDetails")
    @ApiOperation(value = "getProductDetails", response = ProductsResult.class)
    public Response<List<ProductsResult>> getProductDetails(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(scooterPurchaseService.getProductDetails(enter));
    }

}
