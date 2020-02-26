package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.PartsRosService;
import com.redescooter.ses.web.ros.vo.bom.parts.*;
import com.redescooter.ses.web.ros.vo.factory.FactoryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName PartsController
 * @Author Jerry
 * @date 2020/02/26 16:45
 * @Description:
 */
@Api(tags = {"零部件管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/parts")
public class PartsController {

    @Autowired
    private PartsRosService partsRosService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/importParts")
    @ApiOperation(value = "零部件表格导入", response = GeneralResult.class)
    public Response<ImportExcelPartsResult> importParts(@ModelAttribute @ApiParam("请求参数") ImportPartsEnter enter) {
        return new Response<>(partsRosService.importParts(enter));
    }

    @PostMapping(value = "/adds")
    @ApiOperation(value = "批量添加零部件", response = GeneralResult.class)
    public Response<GeneralResult> adds(@ModelAttribute @ApiParam("请求参数") List<AddPartsEnter> enters) {
        return new Response<>(partsRosService.adds(enters));
    }

    @PostMapping(value = "/edits")
    @ApiOperation(value = "批量编辑零部件", response = GeneralResult.class)
    public Response<GeneralResult> edits(@ModelAttribute @ApiParam("请求参数") List<EditPartsEnter> enter) {
        return new Response<>(partsRosService.edits(enter));
    }

    @PostMapping(value = "/deletes")
    @ApiOperation(value = "批量删除零部件", response = GeneralResult.class)
    public Response<GeneralResult> deletes(@ModelAttribute @ApiParam("请求参数") List<IdEnter> enters) {
        return new Response<>(partsRosService.deletes(enters));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "零部件详情", response = FactoryResult.class)
    public Response<DetailsPartsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsRosService.details(enter));
    }

    @PostMapping(value = "/history")
    @ApiOperation(value = "零部件历史", response = FactoryResult.class)
    public Response<HistoryPartsResult> history(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsRosService.history(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "零部件列表", response = FactoryResult.class)
    public Response<PageResult<DetailsPartsResult>> list(@ModelAttribute @ApiParam("请求参数") PartListEnter page) {
        return new Response<>(partsRosService.list(page));

    }
}
