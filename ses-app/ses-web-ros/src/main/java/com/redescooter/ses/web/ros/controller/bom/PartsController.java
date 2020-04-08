package com.redescooter.ses.web.ros.controller.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.MapResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.PartsRosService;
import com.redescooter.ses.web.ros.service.bom.BomRosService;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;
import com.redescooter.ses.web.ros.vo.bom.SecResult;
import com.redescooter.ses.web.ros.vo.bom.parts.DetailsPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.EditSavePartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.HistoryPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.PartListEnter;
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
@Api(tags = {"部件管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/parts")
public class PartsController {

    @Autowired
    private PartsRosService partsRosService;

    @Autowired
    private BomRosService bomRosService;

    @PostMapping(value = "/commonCountStatus")
    @ApiOperation(value = "数量统计", response = MapResult.class)
    public Response<MapResult> commonCountStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(partsRosService.commonCountStatus(enter));
    }

    @PostMapping(value = "/typeCount")
    @ApiOperation(value = "类型列表", response = PartsTypeResult.class)
    public Response<List<PartsTypeResult>> typeCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(partsRosService.typeCount(enter));
    }

    @PostMapping(value = "/importParts")
    @ApiOperation(value = "表格导入", response = ImportExcelPartsResult.class)
    public Response<ImportExcelPartsResult> importParts(@ModelAttribute @ApiParam("请求参数") ImportPartsEnter enter) {
        return new Response<>(partsRosService.importParts(enter));
    }

    @PostMapping(value = "/iterations")
    @ApiOperation(value = "部品号迭代", response = DetailsPartsResult.class)
    public Response<List<DetailsPartsResult>> iterations(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        return new Response<>(partsRosService.iterations(enter));
    }

    @PostMapping(value = "/edits")
    @ApiOperation(value = "批量编辑", response = EditSavePartsEnter.class)
    public Response<GeneralResult> edits(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        return new Response<>(partsRosService.edits(enter));
    }

    @PostMapping(value = "/deletes")
    @ApiOperation(value = "批量删除", response = IdEnter.class)
    public Response<GeneralResult> deletes(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        return new Response<>(partsRosService.deletes(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "详情展示", response = DetailsPartsResult.class)
    public Response<DetailsPartsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsRosService.details(enter));
    }

    @PostMapping(value = "/historys")
    @ApiOperation(value = "历史记录", response = HistoryPartsResult.class)
    public Response<HistoryPartsResult> historys(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsRosService.historys(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "列表展示", response = DetailsPartsResult.class)
    public Response<PageResult<DetailsPartsResult>> list(@ModelAttribute @ApiParam("请求参数") PartListEnter page) {
        return new Response<>(partsRosService.list(page));
    }

    @PostMapping(value = "/secList")
    @ApiOperation(value = "区域列表", response = SecResult.class)
    public Response<List<SecResult>> secList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(bomRosService.secList(enter));
    }

    @PostMapping(value = "/savePartsQcTemplate")
    @ApiOperation(value = "部件质检模板", response = GeneralResult.class)
    public Response<GeneralResult> savePartsQcTemplate(@ModelAttribute @ApiParam("请求参数") SaveQcTemplateEnter enter) {
        return new Response<>(bomRosService.savePartsQcTemplate(enter));
    }
}
