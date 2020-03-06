package com.redescooter.ses.web.ros.controller.bom;

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
import java.util.Map;

/**
 * @ClassName:SalesController
 * @description: SalesController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/04 10:02
 */
@Api(tags = {"销售产品管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/sales/product")
public class SalesController {

    @Autowired
    private SalseRosService salseRosService;

    @PostMapping(value = "/countByServiceType")
    @ApiOperation(value = "数量统计", response = MapResult.class)
    public Response<Map<String, Integer>> countByServiceType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(salseRosService.countByServiceType(enter));
    }

    @PostMapping(value = "/productList")
    @ApiOperation(value = "产品列表", response = ProductListResult.class)
    public Response<PageResult<ProductListResult>> productList(@ModelAttribute @ApiParam("请求参数") ProductListEnter enter) {
        return new Response<>(salseRosService.productList(enter));
    }

    @PostMapping(value = "/afterSaleList")
    @ApiOperation(value = "售后产品列表", response = ProductListResult.class)
    public Response<PageResult<ProductListResult>> afterSaleList(@ModelAttribute @ApiParam("请求参数") ProductListEnter enter) {
        return new Response<>(salseRosService.afterSaleList(enter));
    }

    @PostMapping(value = "/salesServiceList")
    @ApiOperation(value = "增值服务", response = SalesServiceResult.class)
    public Response<PageResult<SalesServiceResult>> salesServiceList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(salseRosService.salesServiceList(enter));
    }

    @PostMapping(value = "/productTypeList")
    @ApiOperation(value = "产品类型", response = String.class)
    public Response<List<String>> productTypeList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(salseRosService.productTypeList(enter));
    }

    @PostMapping(value = "/editPrice")
    @ApiOperation(value = "产品价格修改", response = String.class)
    public Response<GeneralResult> editSalesProductPrice(@ModelAttribute @ApiParam("请求参数") SccPriceEnter enter) {
        return new Response<>(salseRosService.editSalesProductPrice(enter));
    }

    @PostMapping(value = "/priceDetail")
    @ApiOperation(value = "销售产品价格详情", response = SccPriceResult.class)
    public Response<SccPriceResult> salesProductPriceDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salseRosService.priceDetail(enter));
    }

    @PostMapping(value = "/priceHistroyList")
    @ApiOperation(value = "销售产品价格历史列表", response = SccPriceResult.class)
    public Response<PageResult<SccPriceResult>> salesProductPriceList(@ModelAttribute @ApiParam("请求参数") ProductPriceHistroyListEnter enter) {
        return new Response<>(salseRosService.priceList(enter));
    }
}
