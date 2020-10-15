package com.redescooter.ses.web.ros.controller.restproduct;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproduction.RosServProductionProductService;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.restproduct.production.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"生产车辆产品"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/scooter")
public class RosScooterController {

    @Autowired
    private RosServProductionProductService rosServProductionProductService;

    @PostMapping(value = "/countByType")
    @ApiOperation(value = "产品类型统计", response = Map.class)
    public Response<Map<Integer, Integer>> countByType(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rosServProductionProductService.countByType(enter));
    }

    @PostMapping(value = "/groupList")
    @ApiOperation(value = "分组列表", response = BaseNameResult.class)
    public Response<List<BaseNameResult>> groupList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rosServProductionProductService.groupList(enter));
    }

    @PostMapping(value = "/colorList")
    @ApiOperation(value = "颜色列表", response = BaseNameResult.class)
    public Response<List<BaseNameResult>> colorList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rosServProductionProductService.colorList(enter));
    }

    @PostMapping(value = "/scooterList")
    @ApiOperation(value = "车辆列表", response = RosProductionScooterListResult.class)
    public Response<PageResult<RosProductionScooterListResult>>
        scooterList(@ModelAttribute @ApiParam("请求参数") RosProductionScooterListEnter enter) {
        return new Response<>(rosServProductionProductService.scooterList(enter));
    }

    @PostMapping(value = "/checkEffectiveDate")
    @ApiOperation(value = "校验生效时间", response = BooleanResult.class)
    public Response<BooleanResult>
        checkEffectiveDate(@ModelAttribute @ApiParam("请求参数") RosProductionTimeParmEnter enter) {
        return new Response<>(rosServProductionProductService.checkEffectiveDate(enter));
    }

    @PostMapping(value = "/saveScooterImportExcel")
    @ApiOperation(value = "导入部件列表", response = ImportProductionProductResult.class)
    public Response<ImportProductionProductResult>
        saveScooterImportExcel(@ModelAttribute @ApiParam("请求参数") ImportPartsEnter enter) {
        return new Response<>(rosServProductionProductService.importProductionProduct(enter));
    }

    @PostMapping(value = "/secList")
    @ApiOperation(value = "部件区域列表", response = RosProductionSecResult.class)
    public Response<List<RosProductionSecResult>> secList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rosServProductionProductService.secList(enter));
    }

    @PostMapping(value = "/productionProductPartList")
    @ApiOperation(value = "保存车辆部件列表", response = RosProductionProductPartListResult.class)
    public Response<PageResult<RosProductionProductPartListResult>>
        productionProductPartList(@ModelAttribute @ApiParam("请求参数") RosProductionProductPartListEnter enter) {
        return new Response<>(rosServProductionProductService.productionProductPartList(enter));
    }

    @PostMapping(value = "/rosSaveProductionProduct")
    @ApiOperation(value = "车辆保存", response = BaseNameResult.class)
    public Response<GeneralResult>
        rosSaveProductionProduct(@ModelAttribute @ApiParam("请求参数") RosSaveProductionProductEnter enter) {
        return new Response<>(rosServProductionProductService.rosSaveProductionProduct(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = RosProductionProductDetailResult.class)
    public Response<RosProductionProductDetailResult>
        detail(@ModelAttribute @ApiParam("请求参数") RosProuductionTypeEnter enter) {
        return new Response<>(rosServProductionProductService.detail(enter));
    }

    @PostMapping(value = "/takeEffect")
    @ApiOperation(value = "生效", response = GeneralResult.class)
    public Response<GeneralResult> takeEffect(@ModelAttribute @ApiParam("请求参数") RosProuductionTypeEnter enter) {
        return new Response<>(rosServProductionProductService.takeEffect(enter));
    }

    @PostMapping(value = "/productionProductDisable")
    @ApiOperation(value = "产品禁用", response = GeneralResult.class)
    public Response<GeneralResult>
        productionProductDisable(@ModelAttribute @ApiParam("请求参数") RosProuductionTypeEnter enter) {
        return new Response<>(rosServProductionProductService.productionProductDisable(enter));
    }

    @PostMapping(value = "/release")
    @ApiOperation(value = "发布", response = GeneralResult.class)
    public Response<GeneralResult> release(@ModelAttribute @ApiParam("请求参数") RosProductionProductReleaseEnter enter) {
        return new Response<>(rosServProductionProductService.release(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") RosProuductionTypeEnter enter) {
        return new Response<>(rosServProductionProductService.delete(enter));
    }

    @PostMapping(value = "/checkProductN")
    @ApiOperation(value = "产品编号", response = BooleanResult.class)
    public Response<BooleanResult> checkProductN(@ModelAttribute @ApiParam("请求参数") CheckProductNEnter enter) {
        return new Response<>(rosServProductionProductService.checkProductN(enter));
    }

    @PostMapping(value = "/checkProductionInfo")
    @ApiOperation(value = "产品信息校验", response = GeneralResult.class)
    public Response<GeneralResult>
        checkProductionInfo(@ModelAttribute @ApiParam("请求参数") RosProuductionTypeEnter enter) {
        return new Response<>(rosServProductionProductService.checkProductionInfo(enter));
    }
}
