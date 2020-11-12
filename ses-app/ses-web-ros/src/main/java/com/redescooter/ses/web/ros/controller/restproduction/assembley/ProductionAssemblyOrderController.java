package com.redescooter.ses.web.ros.controller.restproduction.assembley;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName:ProductionAssemblyOrderController
 * @description: ProductionAssemblyOrderController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/12 16:32 
 */
@Api(tags = {"采购单"})
@CrossOrigin
@RestController
@RequestMapping(value = "/restproduction/assembly")
public class ProductionAssemblyOrderController {

    @Autowired
    private ProductionAssemblyOrderService productionAssemblyOrderService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "列表", response = ProductionAssemblyOrderListResult.class)
    public Response<PageResult<ProductionAssemblyOrderListResult>> list(@ModelAttribute @ApiParam("请求参数") ProductionAssemblyOrderListEnter enter) {
        return new Response<>(productionAssemblyOrderService.list(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = ProductionAssemblyOrderDetailResult.class)
    public Response<ProductionAssemblyOrderDetailResult> detail(@ModelAttribute @ApiParam("请求参数") AssemblyOrderDetailEnter enter) {
        return new Response<>(productionAssemblyOrderService.detail(enter));
    }

    @PostMapping(value = "/productPartDetail")
    @ApiOperation(value = "产品部件详情", response = PurchasDetailProductListResult.class)
    public Response<List<PurchasDetailProductListResult>> productPartDetail(@ModelAttribute @ApiParam("请求参数") AssemblyOrderDetailEnter enter) {
        return new Response<>(productionAssemblyOrderService.productPartDetail(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveAssemblyOrderEnter enter) {
        return new Response<>(productionAssemblyOrderService.save(enter));
    }

    @PostMapping(value = "/materialPreparation")
    @ApiOperation(value = "备料", response = GeneralResult.class)
    public Response<GeneralResult> materialPreparation(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.materialPreparation(enter));
    }

    @PostMapping(value = "/assembly")
    @ApiOperation(value = "组装", response = GeneralResult.class)
    public Response<GeneralResult> assembly(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.assembly(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.delete(enter));
    }

}
