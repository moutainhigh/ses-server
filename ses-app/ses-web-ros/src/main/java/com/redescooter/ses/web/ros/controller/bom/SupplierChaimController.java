package com.redescooter.ses.web.ros.controller.bom;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.SupplierChaimRosService;
import com.redescooter.ses.web.ros.vo.bom.sales.PriceUnitResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceResult;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.EditProductPriceEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.ProductPriceChartResult;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.ScPriceEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.SupplierChaimListEnter;
import com.redescooter.ses.web.ros.vo.bom.supplierChaim.SupplierChaimListResult;
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
 * @ClassName:SupplierChaimController
 * @description: SupplierChaimController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/02 16:29
 */
@Api(tags = {"供应链模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/supplierchaim")
public class SupplierChaimController {

    @Autowired
    private SupplierChaimRosService supplierChaimRosService;

    @PostMapping(value = "/countByPartType")
    @ApiOperation(value = "配件类型", response = Map.class)
    public Response<Map<String, Integer>> scooterList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(supplierChaimRosService.countByPartType(enter));
    }

    @PostMapping(value = "/supplierChaimList")
    @ApiOperation(value = "供应链列表", response = SupplierChaimListResult.class)
    public Response<PageResult<SupplierChaimListResult>> supplierChaimList(@ModelAttribute @ApiParam("请求参数") SupplierChaimListEnter enter) {
        return new Response<>(supplierChaimRosService.supplierChaimList(enter));
    }

    @PostMapping(value = "/editProductPrice")
    @ApiOperation(value = "产品价格修改", response = GeneralResult.class)
    public Response<GeneralResult> editProductPrice(@ModelAttribute @ApiParam("请求参数") EditProductPriceEnter enter) {
        return new Response<>(supplierChaimRosService.editProductPrice(enter));
    }

    @PostMapping(value = "/productPriceDetail")
    @ApiOperation(value = "产品价格详情", response = SccPriceResult.class)
    public Response<SccPriceResult> productPriceDetail(@ModelAttribute @ApiParam("请求参数") ScPriceEnter enter) {
        return new Response<>(supplierChaimRosService.productPriceDetail(enter));
    }

    @PostMapping(value = "/currencyUnit")
    @ApiOperation(value = "货币单位列表", response = Map.class)
    public Response<List<PriceUnitResult>> currencyUnit(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(supplierChaimRosService.currencyUnit(enter));
    }

    @PostMapping(value = "/productPriceHistroyList")
    @ApiOperation(value = "产品价格历史列表", response = SccPriceResult.class)
    public Response<PageResult<SccPriceResult>> productPriceHistroyList(@ModelAttribute @ApiParam("请求参数") ProductPriceHistroyListEnter enter) {
        return new Response<>(supplierChaimRosService.productPriceHistroyList(enter));
    }

    @PostMapping(value = "/productPriceHistroyChart")
    @ApiOperation(value = "产品价格历史图表", response = ProductPriceChartResult.class)
    public Response<ProductPriceChartResult> productPriceHistroyChart(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(supplierChaimRosService.productPriceHistroyChart(enter));
    }

}
