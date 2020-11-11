package com.redescooter.ses.web.ros.controller.restproduction.proudctionpurchas;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.OutboundOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SaveProductionPurchasEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName:PurchasController
 * @description: PurchasController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 11:59 
 */
@Api(tags = {"采购单"})
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
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveProductionPurchasEnter enter) {
        return new Response<>(productionPurchasService.save(enter));
    }

}
