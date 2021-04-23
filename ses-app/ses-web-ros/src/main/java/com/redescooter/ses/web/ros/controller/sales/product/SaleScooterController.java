package com.redescooter.ses.web.ros.controller.sales.product;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.service.restproduction.SaleScooterService;
import com.redescooter.ses.web.ros.service.specificat.ColorService;
import com.redescooter.ses.web.ros.service.specificat.SpecificatTypeService;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterListResult;
import com.redescooter.ses.web.ros.vo.restproduct.SaleScooterSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.specificat.ColorDataResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeDataResult;
import com.redescooter.ses.web.ros.vo.specificat.SpecificatTypeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassNameSaleScooterService
 * @Description
 * @Author Aleks
 * @Date2020/10/12 17:02
 * @Version V1.0
 **/
@Api(tags = {"销售车辆"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sales/scooter")
public class SaleScooterController {

    @Autowired
    private SaleScooterService saleScooterService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SpecificatTypeService specificatTypeService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增销售车辆", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaleScooterSaveOrUpdateEnter enter) {
        return new Response<>(saleScooterService.saveSaleScooter(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑销售车辆", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") SaleScooterSaveOrUpdateEnter enter) {
        return new Response<>(saleScooterService.editSaleScooter(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除销售车辆", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(saleScooterService.deleteSaleScooter(enter));
    }

    @PostMapping(value = "/editSaleStatus")
    @ApiOperation(value = "销售状态的编辑", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> editSaleScooterStatus(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(saleScooterService.editSaleScooterStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "销售车辆列表", response = SaleScooterListResult.class)
    public Response<PageResult<SaleScooterListResult>> list(@ModelAttribute @ApiParam("请求参数") SaleScooterListEnter enter) {
        return new Response(saleScooterService.saleScooterList(enter));
    }

    @PostMapping(value = "/specificatTypeData")
    @ApiOperation(value = "规格类型下拉数据源接口", response = SpecificatTypeDataResult.class)
    public Response<List<SpecificatTypeDataResult>> specificatTypeData(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(specificatTypeService.specificatTypeData(enter));
    }

    @PostMapping(value = "/colorData")
    @ApiOperation(value = "颜色下拉数据源接口", response = ColorDataResult.class)
    public Response<List<ColorDataResult>> colorData(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(colorService.colorData(enter));
    }

    @PostMapping(value = "/listCount")
    @ApiOperation(value = "列表统计", response = GeneralResult.class)
    public Response<Map<String, Integer>> listCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(saleScooterService.listCount(enter));
    }

    /**
     *规格列表
     */
    @PostMapping(value = "/specificatTypeDataList")
    @ApiOperation(value = "规格列表", response = SpecificatTypeDataResult.class)
    public Response<List<SpecificatTypeResult>> specificatTypeDataList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(saleScooterService.specificatTypeDataList(enter));
    }

    /**
     *规格和颜色的二级联动
     */
    @PostMapping(value = "/SpecificationsColorLinkage")
    @ApiOperation(value = "规格和颜色的二级联动", response = GeneralResult.class)
    public Response<List<ColorDataResult>> SpecificationsColorLinkage(@ModelAttribute @ApiParam("请求参数") IdResult idResult) {
        return new Response(saleScooterService.SpecificationsColorLinkage(idResult.getId()));
    }

}
