package com.redescooter.ses.web.ros.controller.production.assembley;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.restproductionorder.assembly.ProductionAssemblyOrderService;
import com.redescooter.ses.web.ros.vo.restproduct.BomNameData;
import com.redescooter.ses.web.ros.vo.restproduct.BomNoEnter;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameData;
import com.redescooter.ses.web.ros.vo.restproduct.CombinNameEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.AssemblyOrderDetailEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.SaveAssemblyOrderEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatGroupDataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:ProductionAssemblyOrderController
 * @description: ProductionAssemblyOrderController
 * @author: Alex
 * @Version???1.3
 * @create: 2020/11/12 16:32
 */
@Api(tags = {"?????????"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/restproduction/assembly")
public class ProductionAssemblyOrderController {

    @Autowired
    private ProductionAssemblyOrderService productionAssemblyOrderService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "????????????", response = Map.class)
    public Response<Map<Integer, Integer>> countByType(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(productionAssemblyOrderService.countByType(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "??????", response = ProductionAssemblyOrderListResult.class)
    public Response<PageResult<ProductionAssemblyOrderListResult>> list(@ModelAttribute @ApiParam("????????????") ProductionAssemblyOrderListEnter enter) {
        return new Response<>(productionAssemblyOrderService.list(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "??????", response = ProductionAssemblyOrderDetailResult.class)
    public Response<ProductionAssemblyOrderDetailResult> detail(@ModelAttribute @ApiParam("????????????") AssemblyOrderDetailEnter enter) {
        return new Response<>(productionAssemblyOrderService.detail(enter));
    }

    @PostMapping(value = "/productPartDetail")
    @ApiOperation(value = "??????????????????", response = PurchasDetailProductListResult.class)
    public Response<List<PurchasDetailProductListResult>> productPartDetail(@ModelAttribute @ApiParam("????????????") AssemblyOrderDetailEnter enter) {
        return new Response<>(productionAssemblyOrderService.productPartDetail(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "??????", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("????????????") SaveAssemblyOrderEnter enter) {
        return new Response<>(productionAssemblyOrderService.save(enter));
    }

    @PostMapping(value = "/materialPreparation")
    @ApiOperation(value = "??????", response = GeneralResult.class)
    public Response<GeneralResult> materialPreparation(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.materialPreparation(enter));
    }

    @PostMapping(value = "/assembly")
    @ApiOperation(value = "??????", response = GeneralResult.class)
    public Response<GeneralResult> assembly(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.assembly(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "??????", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.delete(enter));
    }

    @PostMapping(value = "/scooterGroupData")
    @ApiOperation(value = "??????????????????", response = SpecificatGroupDataResult.class)
    public Response<List<SpecificatGroupDataResult>> scooterGroupData(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(productionAssemblyOrderService.scooterGroupData(enter));
    }

    @PostMapping(value = "/colorData")
    @ApiOperation(value = "??????????????????", response = ColorDataResult.class)
    public Response<List<ColorDataResult>> colorData(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.colorData(enter));
    }

    @PostMapping(value = "/combinNameData")
    @ApiOperation(value = "?????????????????????", response = CombinNameData.class)
    public Response<List<CombinNameData>> combinNameData(@ModelAttribute @ApiParam("????????????") CombinNameEnter enter) {
        return new Response<>(productionAssemblyOrderService.combinNameData(enter));
    }

    @PostMapping(value = "/bomNoData")
    @ApiOperation(value = "????????????????????????????????????", response = BomNameData.class)
    public Response<List<BomNameData>> bomNoData(@ModelAttribute @ApiParam("????????????") BomNoEnter enter) {
        return new Response<>(productionAssemblyOrderService.bomNoData(enter));
    }

    @IgnoreLoginCheck
    @GetMapping(value = "/export")
    @ApiOperation(value = "?????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> export(@ApiParam("id") Long id, HttpServletResponse response) {
        return new Response<>(productionAssemblyOrderService.export(id, response));
    }

    //************************  ???????????????RPS??????????????? *****************************

    @PostMapping(value = "/startCombin")
    @ApiOperation(value = "??????RPS?????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> startCombin(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.startCombin(enter));
    }

    @PostMapping(value = "/endCombin")
    @ApiOperation(value = "??????RPS?????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> endCombin(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.endCombin(enter));
    }

    @PostMapping(value = "/startQc")
    @ApiOperation(value = "??????RPS????????????????????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> startQc(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.startQc(enter));
    }

    @PostMapping(value = "/endQc")
    @ApiOperation(value = "??????RPS????????????????????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> endQc(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(productionAssemblyOrderService.endQc(enter));
    }

}
