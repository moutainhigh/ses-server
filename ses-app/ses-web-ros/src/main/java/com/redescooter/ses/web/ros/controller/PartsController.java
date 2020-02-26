package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.BomRosService;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.factory.FactoryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private BomRosService bomRosService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/importParts")
    @ApiOperation(value = "零部件表格导入", response = GeneralResult.class)
    public Response<ImportExcelPartsResult> importParts(@ModelAttribute @ApiParam("请求参数") ImportPartsEnter enter) {
        return new Response<>(bomRosService.importParts(enter));
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "添加零部件", response = GeneralResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑零部件", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除零部件", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "零部件详情", response = FactoryResult.class)
    public Response<GeneralResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/history")
    @ApiOperation(value = "零部件历史", response = FactoryResult.class)
    public Response<GeneralResult> history(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "零部件列表", response = FactoryResult.class)
    public Response<PageResult<GeneralResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter page) {
        return new Response<>();
    }
}
