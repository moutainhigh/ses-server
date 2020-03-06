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
 * @description: SalesProductController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/04 10:02
 */
@Api(tags = {"销售产品管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/sales/product")
public class SalesProductController {

    @Autowired
    private SalseRosService salseRosService;

    @PostMapping(value = "/commonCountStatus")
    @ApiOperation(value = "公共统计", response = String.class)
    public Response<Map<String, Integer>> commonCountStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(salseRosService.commonCountStatus(enter));
    }

    @PostMapping(value = "/getType")
    @ApiOperation(value = "筛选类型", response = String.class)
    public Response<List<ProductGetTypeResult>> getType(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(salseRosService.getProductType(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "产品列表", response = ProductListResult.class)
    public Response<PageResult<ProductListResult>> list(@ModelAttribute @ApiParam("请求参数") ProductListEnter enter) {
        return new Response<>(salseRosService.productList(enter));
    }

    @PostMapping(value = "/item")
    @ApiOperation(value = "产品子条目", response = ProductListResult.class)
    public Response<List<ProductSubentryResult>> items(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salseRosService.items(enter));
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
