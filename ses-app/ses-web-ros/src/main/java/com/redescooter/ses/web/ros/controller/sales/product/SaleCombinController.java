package com.redescooter.ses.web.ros.controller.sales.product;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproduction.SaleCombinService;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameSaleScooterService
 * @Description
 * @Author Aleks
 * @Date2020/10/20 17:02
 * @Version V1.0
 **/
@Api(tags = {"销售组装件"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sales/combin")
public class SaleCombinController {

    @Autowired
    private SaleCombinService saleCombinService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增销售组装件", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaleCombinSaveOrUpdateEnter enter) {
        return new Response<>(saleCombinService.saveSaleCombin(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "销售组装件列表", response = SaleScooterListResult.class)
    public Response<PageResult<SaleCombinListResult>> list(@ModelAttribute @ApiParam("请求参数") SaleListEnter enter) {
        return new Response(saleCombinService.saleCombinList(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑销售组装件", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") SaleCombinSaveOrUpdateEnter enter) {
        return new Response<>(saleCombinService.editSaleCombin(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除销售组装件", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(saleCombinService.deleteSaleCombin(enter));
    }

    @PostMapping(value = "/editSaleStatus")
    @ApiOperation(value = "销售状态的编辑", response = GeneralResult.class)
    public Response<GeneralResult> editSaleCombinStatus(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(saleCombinService.editSaleCombinStatus(enter));
    }

    @PostMapping(value = "/combinNameData")
    @ApiOperation(value = "销售组装件名称下拉接口", response = CombinNameData.class)
    public Response<List<CombinNameData>> combinNameData(@ModelAttribute @ApiParam("请求参数") CombinNameEnter enter) {
        return new Response<>(saleCombinService.combinNameData(enter));
    }

    @PostMapping(value = "/bomNoData")
    @ApiOperation(value = "销售组装件编号下拉接口", response = BomNameData.class)
    public Response<List<BomNameData>> bomNoData(@ModelAttribute @ApiParam("请求参数") BomNoEnter enter) {
        return new Response<>(saleCombinService.bomNoData(enter));
    }

}
