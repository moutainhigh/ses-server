package com.redescooter.ses.web.ros.controller.other.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IdsEnter;
import com.redescooter.ses.api.common.vo.base.IntResult;
import com.redescooter.ses.api.common.vo.base.MapResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.bom.BomRosService;
import com.redescooter.ses.web.ros.service.bom.PartsRosService;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;
import com.redescooter.ses.web.ros.vo.bom.SecResult;
import com.redescooter.ses.web.ros.vo.bom.parts.DeletePartResult;
import com.redescooter.ses.web.ros.vo.bom.parts.DetailsPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.EditSavePartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.HistoryPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartListEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartUnbindEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartsTypeResult;
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
 * @ClassName PartsController
 * @Author Jerry
 * @date 2020/02/26 16:45
 * @Description:
 */
@Api(tags = {"????????????"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/parts")
public class PartsController {

    @Autowired
    private PartsRosService partsRosService;

    @Autowired
    private BomRosService bomRosService;

    @PostMapping(value = "/synchronizeNum")
    @ApiOperation(value = "???????????????", response = IntResult.class)
    public Response<IntResult> synchronizeNum(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(partsRosService.synchronizeNum(enter));
    }

    @PostMapping(value = "/synchronize")
    @ApiOperation(value = "????????????", response = MapResult.class)
    public Response<GeneralResult> synchronize(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(partsRosService.synchronizeParts(enter));
    }

    @PostMapping(value = "/commonCountStatus")
    @ApiOperation(value = "????????????", response = MapResult.class)
    public Response<MapResult> commonCountStatus(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(partsRosService.commonCountStatus(enter));
    }

    @PostMapping(value = "/typeCount")
    @ApiOperation(value = "????????????", response = PartsTypeResult.class)
    public Response<List<PartsTypeResult>> typeCount(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(partsRosService.typeCount(enter));
    }

    @PostMapping(value = "/importParts")
    @ApiOperation(value = "????????????", response = ImportExcelPartsResult.class)
    public Response<ImportExcelPartsResult> importParts(@ModelAttribute @ApiParam("????????????") ImportPartsEnter enter) {
        return new Response<>(partsRosService.importParts(enter));
    }

    @PostMapping(value = "/iterations")
    @ApiOperation(value = "???????????????", response = DetailsPartsResult.class)
    public Response<List<DetailsPartsResult>> iterations(@ModelAttribute @ApiParam("????????????") StringEnter enter) {
        return new Response<>(partsRosService.iterations(enter));
    }

    @PostMapping(value = "/edits")
    @ApiOperation(value = "????????????", response = EditSavePartsEnter.class)
    public Response<GeneralResult> edits(@ModelAttribute @ApiParam("????????????") StringEnter enter) {
        return new Response<>(partsRosService.edits(enter));
    }

    @PostMapping(value = "/deletes")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    public Response<GeneralResult> deletes(@ModelAttribute @ApiParam("????????????") StringEnter enter) {
        return new Response<>(partsRosService.deletes(enter));
    }

    @PostMapping(value = "/queryPartBindProduct")
    @ApiOperation(value = "??????????????????????????????", response = DeletePartResult.class)
    public Response<List<DeletePartResult>> queryPartBindProduct(@ModelAttribute @ApiParam("????????????") StringEnter enter) {
        return new Response<>(partsRosService.queryPartBindProduct(enter));
    }

    @PostMapping(value = "/partUnbind")
    @ApiOperation(value = "??????????????????", response = GeneralResult.class)
    public Response<GeneralResult> partUnbind(@ModelAttribute @ApiParam("????????????") PartUnbindEnter enter) {
        return new Response<>(partsRosService.partUnbind(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "????????????", response = DetailsPartsResult.class)
    public Response<DetailsPartsResult> details(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(partsRosService.details(enter));
    }

    @PostMapping(value = "/historys")
    @ApiOperation(value = "????????????", response = HistoryPartsResult.class)
    public Response<HistoryPartsResult> historys(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(partsRosService.historys(enter));
    }

    @PostMapping(value = "/deleteHistorys")
    @ApiOperation(value = "??????????????????", response = GeneralResult.class)
    public Response<GeneralResult> deleteHistorys(@ModelAttribute @ApiParam("????????????") IdsEnter enter) {
        return new Response<>(partsRosService.deleteHistorys(enter));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "????????????", response = DetailsPartsResult.class)
    public Response<PageResult<DetailsPartsResult>> list(@ModelAttribute @ApiParam("????????????") PartListEnter page) {
        return new Response<>(partsRosService.list(page));
    }

    @PostMapping(value = "/secList")
    @ApiOperation(value = "????????????", response = SecResult.class)
    public Response<List<SecResult>> secList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(bomRosService.secList(enter));
    }

    @PostMapping(value = "/savePartsQcTemplate")
    @ApiOperation(value = "????????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> savePartsQcTemplate(@ModelAttribute @ApiParam("????????????") SaveQcTemplateEnter enter) {
        return new Response<>(bomRosService.savePartsDraftQcTemplate(enter));
    }

    @PostMapping(value = "/partsQcTemplateDetail")
    @ApiOperation(value = "????????????????????????", response = QcTemplateDetailResult.class)
    public Response<List<QcTemplateDetailResult>> partsQcTemplateDetail(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(bomRosService.partsQcTemplateDetail(enter));
    }

    @PostMapping(value = "/saveProductQcTemplate")
    @ApiOperation(value = "????????????????????????", response = GeneralResult.class)
    public Response<GeneralResult> saveProductQcTemplate(@ModelAttribute @ApiParam("????????????") SaveQcTemplateEnter enter) {
        return new Response<>(bomRosService.saveProductQcTemplate(enter));
    }

    @PostMapping(value = "/productQcTemplateDetail")
    @ApiOperation(value = "????????????????????????", response = QcTemplateDetailResult.class)
    public Response<List<QcTemplateDetailResult>> productQcTemplateDetail(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(bomRosService.productQcTemplateDetail(enter));
    }
}
