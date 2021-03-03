package com.redescooter.ses.web.ros.controller.other.bom;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.bom.SalseRosService;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.*;
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
 * @ClassName:SalesAfterController
 * @description: SalesAfterController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/04 14:38
 */
@Api(tags = {"售后产品管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/sales/after")
public class SalesAfterProductController {

    @Autowired
    private SalseRosService salseRosService;

    @PostMapping(value = "/getType")
    @ApiOperation(value = "筛选类型", response = String.class)
    public Response<List<ProductGetTypeResult>> getType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(salseRosService.getAfterProductType(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "售后产品列表", response = ProductListResult.class)
    public Response<PageResult<ProductListResult>> afterSaleList(@ModelAttribute @ApiParam("请求参数") ProductListEnter enter) {
        return new Response<>(salseRosService.afterSaleList(enter));
    }

    @PostMapping(value = "/item")
    @ApiOperation(value = "产品子条目", response = ProductListResult.class)
    public Response<List<SubentryProductResult>> items(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salseRosService.items(enter));
    }

    @PostMapping(value = "/editPrice")
    @ApiOperation(value = "售后产品价格修改", response = String.class)
    public Response<GeneralResult> editAfterProductPrice(@ModelAttribute @ApiParam("请求参数") SccPriceEnter enter) {
        return new Response<>(salseRosService.editAfterProductPrice(enter));
    }

    @PostMapping(value = "/priceDetail")
    @ApiOperation(value = "售后产品价格详情", response = SccPriceResult.class)
    public Response<SccPriceResult> afterProductPriceDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salseRosService.priceDetail(enter));
    }

    @PostMapping(value = "/priceHistroyList")
    @ApiOperation(value = "售后产品价格历史列表", response = SccPriceResult.class)
    public Response<PageResult<SccPriceResult>> afterProductPriceList(@ModelAttribute @ApiParam("请求参数") ProductPriceHistroyListEnter enter) {
        return new Response<>(salseRosService.priceList(enter));
    }

}
