package com.redescooter.ses.web.ros.controller.product;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.qctemplete.ProductionQcTmepleteService;
import com.redescooter.ses.web.ros.service.restproduction.RosServProductionProductService;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.qctemplete.QcTempleteDetailEnter;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.restproduct.production.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(tags = {"生产组合产品"})
@CrossOrigin
@RestController
@RequestMapping(value = "/product/production/combination")
public class RosCombinationController {

    @Autowired
    private RosServProductionProductService rosServProductionProductService;

    @Autowired
    private ProductionQcTmepleteService productionQcTmepleteService;

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

    @PostMapping(value = "/combinationList")
    @ApiOperation(value = "组合列表", response = RosProductionCombinationListResult.class)
    public Response<PageResult<RosProductionCombinationListResult>> combinationList(@ModelAttribute @ApiParam("请求参数") RosProductionCombinationListEnter enter) {
        return new Response<>(rosServProductionProductService.combinationList(enter));
    }

    @PostMapping(value = "/checkEffectiveDate")
    @ApiOperation(value = "校验生效时间", response = BooleanResult.class)
    public Response<BooleanResult> checkEffectiveDate(@ModelAttribute @ApiParam("请求参数") RosProductionTimeParmEnter enter) {
        return new Response<>(rosServProductionProductService.checkEffectiveDate(enter));
    }

    @PostMapping(value = "/saveScooterImportExcel")
    @ApiOperation(value = "导入部件列表", response = ImportProductionProductResult.class)
    public Response<ImportProductionProductResult> saveScooterImportExcel(@ModelAttribute @ApiParam("请求参数") ImportPartsEnter enter) {
        return new Response<>(rosServProductionProductService.importProductionProduct(enter));
    }

    @PostMapping(value = "/secList")
    @ApiOperation(value = "部件区域列表", response = RosProductionSecResult.class)
    public Response<List<RosProductionSecResult>> secList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rosServProductionProductService.secList(enter));
    }

    @PostMapping(value = "/productionProductPartList")
    @ApiOperation(value = "保存组合部件列表", response = RosProductionProductPartListResult.class)
    public Response<PageResult<RosProductionProductPartListResult>> productionProductPartList(@ModelAttribute @ApiParam("请求参数") RosProductionProductPartListEnter enter) {
        return new Response<>(rosServProductionProductService.productionProductPartList(enter));
    }

    @PostMapping(value = "/rosSaveProductionProduct")
    @ApiOperation(value = "组合保存", response = BaseNameResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> rosSaveProductionProduct(@ModelAttribute @ApiParam("请求参数") RosSaveProductionProductEnter enter) {
        return new Response<>(rosServProductionProductService.rosSaveProductionProduct(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = RosProductionProductDetailResult.class)
    public Response<RosProductionProductDetailResult> detail(@ModelAttribute @ApiParam("请求参数") RosProuductionTypeEnter enter) {
        return new Response<>(rosServProductionProductService.detail(enter));
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

    @PostMapping(value = "/takeEffect")
    @ApiOperation(value = "生效", response = GeneralResult.class)
    public Response<GeneralResult> takeEffect(@ModelAttribute @ApiParam("请求参数") RosProuductionTypeEnter enter) {
        return new Response<>(rosServProductionProductService.takeEffect(enter));
    }

    @PostMapping(value = "/productionProductDisable")
    @ApiOperation(value = "产品禁用", response = GeneralResult.class)
    public Response<GeneralResult> productionProductDisable(@ModelAttribute @ApiParam("请求参数") RosProuductionTypeEnter enter) {
        return new Response<>(rosServProductionProductService.productionProductDisable(enter));
    }

    @GetMapping(value = "/bomExport")
    @ApiOperation(value = "部件导出", response = GeneralResult.class)
    public Response<GeneralResult> bomExport(@ApiParam("请求参数 id") Long id,@ApiParam("类型，4是整车，5是组装") Integer productionProductType,HttpServletResponse response) {
        return new Response(rosServProductionProductService.bomExport(id,productionProductType,response));
    }

    @PostMapping(value = "/checkProductN")
    @ApiOperation(value = "产品编号", response = BooleanResult.class)
    public Response<BooleanResult> checkProductN(@ModelAttribute @ApiParam("请求参数") CheckProductNEnter enter) {
        return new Response<>(rosServProductionProductService.checkProductN(enter));
    }

    @PostMapping(value = "/qcTempleteDetail")
    @ApiOperation(value = "质检模板详情", response = QcTemplateDetailResult.class)
    public Response<List<QcTemplateDetailResult>> qcTempleteDetail(@ModelAttribute @ApiParam("请求参数") QcTempleteDetailEnter enter) {
        return new Response<>(productionQcTmepleteService.detail(enter));
    }

    @PostMapping(value = "/qcTempleteSave")
    @ApiOperation(value = "保存质检模板", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> qcTempleteSave(@ModelAttribute @ApiParam("请求参数") SaveQcTemplateEnter enter) {
        return new Response<>(productionQcTmepleteService.save(enter));
    }

    @PostMapping(value = "/checkProductionInfo")
    @ApiOperation(value = "产品信息校验", response = GeneralResult.class)
    public Response<GeneralResult> checkProductionInfo(@ModelAttribute @ApiParam("请求参数") RosProuductionTypeEnter enter) {
        return new Response<>(rosServProductionProductService.checkProductionInfo(enter));
    }

}
