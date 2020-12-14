package com.redescooter.ses.web.ros.controller.restproduct;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproduction.SalePartsService;
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
@Api(tags = {"销售部件"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sale/parts")
public class SalePartsController {

    
    @Autowired
    private SalePartsService salePartsService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增销售部件", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SalePartsSaveOrUpdateEnter enter) {
        return new Response<>(salePartsService.saveSaleParts(enter));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "销售部件列表", response = SaleScooterListResult.class)
    public Response<PageResult<SalePartsListResult>> list(@ModelAttribute @ApiParam("请求参数") SaleListEnter enter) {
        return new Response(salePartsService.salePartsList(enter));
    }


    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑销售部件", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") SalePartsSaveOrUpdateEnter enter) {
        return new Response<>(salePartsService.editSaleParts(enter));
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除销售部件", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salePartsService.deleteSaleParts(enter));
    }


    @PostMapping(value = "/editSaleStatus")
    @ApiOperation(value = "销售状态的编辑", response = GeneralResult.class)
    public Response<GeneralResult> editSalePartsStatus(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salePartsService.editSalePartsStatus(enter));
    }


    @PostMapping(value = "/partsNameData")
    @ApiOperation(value = "销售部件名称下拉接口", response = PartsNameData.class)
    public Response<List<PartsNameData>> partsNameData(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(salePartsService.partsNameData(enter));
    }


    @PostMapping(value = "/bomNoData")
    @ApiOperation(value = "销售部件编号下拉接口", response = PartsNoData.class)
    public Response<List<PartsNoData>> bomNoData(@ModelAttribute @ApiParam("请求参数") PartsNoEnter enter) {
        return new Response<>(salePartsService.partsNoData(enter));
    }

}
