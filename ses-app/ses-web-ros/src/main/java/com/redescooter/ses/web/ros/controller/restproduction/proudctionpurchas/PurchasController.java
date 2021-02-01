package com.redescooter.ses.web.ros.controller.restproduction.proudctionpurchas;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.CancelOrderEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasPartListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasPartListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SaveProductionPurchasEnter;
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
 * @ClassName:PurchasController
 * @description: PurchasController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 11:59
 */
@Api(tags = {"生产采购单"})
@CrossOrigin
@RestController
@RequestMapping(value = "/restproduction/purchas")
public class PurchasController {

    @Autowired
    private ProductionPurchasService productionPurchasService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "列表", response = ProductionPurchasListResult.class)
    public Response<PageResult<ProductionPurchasListResult>> list(@ModelAttribute @ApiParam("请求参数") ProductionPurchasListEnter enter) {
        return new Response<>(productionPurchasService.list(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = ProductionPurchasDetailResult.class)
    public Response<ProductionPurchasDetailResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productionPurchasService.detail(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存订单", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveProductionPurchasEnter enter) {
        return new Response<>(productionPurchasService.save(enter));
    }

    @PostMapping(value = "/purchasPartList")
    @ApiOperation(value = "可采购的产品列表", response = GeneralResult.class)
    public Response<PageResult<PurchasPartListResult>> sapurchasPartListve(@ModelAttribute @ApiParam("请求参数") PurchasPartListEnter enter) {
        return new Response<>(productionPurchasService.purchasPartList(enter));
    }

    @PostMapping(value = "/supplierList")
    @ApiOperation(value = "供应商列表", response = SupplierListResult.class)
    public Response<List<SupplierListResult>> supplierList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(productionPurchasService.supplierList(enter));
    }

    @PostMapping(value = "/close")
    @ApiOperation(value = "关闭订单", response = GeneralResult.class)
    public Response<GeneralResult> close(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productionPurchasService.close(enter));
    }

    @PostMapping(value = "/bookOrder")
    @ApiOperation(value = "下单", response = GeneralResult.class)
    public Response<GeneralResult> bookOrder(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productionPurchasService.bookOrder(enter));
    }

    @PostMapping(value = "/cancel")
    @ApiOperation(value = "取消", response = SupplierListResult.class)
    public Response<GeneralResult> cancel(@ModelAttribute @ApiParam("请求参数") CancelOrderEnter enter) {
        return new Response<>(productionPurchasService.cancel(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除", response = SupplierListResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productionPurchasService.delete(enter));
    }

    /**
     * 生成组装单的质检单(提供给rps使用)
     */
    @PostMapping(value = "/qcByCombin")
    @ApiOperation(value = "生成组装单的质检单(提供给rps使用)", tags = "生成组装单的质检单(提供给rps使用)")
    public Response<GeneralResult> generatorQcOrderByCombin(@ModelAttribute IdEnter enter) {
        return new Response<>(productionPurchasService.generatorQcOrderByCombin(enter));
    }

    /**
     * 生成出库单的质检单(提供给rps使用)
     */
    @PostMapping(value = "/qcByOutBound")
    @ApiOperation(value = "生成出库单的质检单(提供给rps使用)", tags = "生成出库单的质检单(提供给rps使用)")
    public Response<GeneralResult> generatorQcOrderByOutBound(@ModelAttribute IdEnter enter) {
        return new Response<>(productionPurchasService.generatorQcOrderByOutBound(enter));
    }

    /**
     * 确认到货
     */
    @PostMapping(value = "/arrived")
    @ApiOperation(value = "确认到货", tags = "确认到货")
    public Response<GeneralResult> arrived(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productionPurchasService.arrived(enter));
    }

}
