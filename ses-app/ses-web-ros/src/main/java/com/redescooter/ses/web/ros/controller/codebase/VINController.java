package com.redescooter.ses.web.ros.controller.codebase;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.web.ros.service.codebase.VINService;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.codebase.SpecificatResult;
import com.redescooter.ses.web.ros.vo.codebase.VINDetailResult;
import com.redescooter.ses.web.ros.vo.codebase.VINListEnter;
import com.redescooter.ses.web.ros.vo.codebase.VINListResult;
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
 * @Description VIN管理控制器
 * @Author Chris
 * @Date 2021/5/14 9:45
 */
@Api(value = "VIN管理控制器", tags = "VIN管理控制器")
@CrossOrigin
@RestController
@RequestMapping("/codebase/vin")
public class VINController {

    @Autowired
    private VINService vinService;

    /**
     * 车型数据源
     */
    @PostMapping("/data")
    @ApiOperation(value = "车型数据源", notes = "车型数据源")
    public Response<List<SpecificatResult>> getSpecificatTypeData(@ModelAttribute GeneralEnter enter) {
        return new Response<>(vinService.getSpecificatTypeData(enter));
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "VIN列表", notes = "VIN列表")
    public Response<PageResult<VINListResult>> getList(@ModelAttribute VINListEnter enter) {
        return new Response<>(vinService.getList(enter));
    }

    /**
     * 详情
     */
    @PostMapping("/detail")
    @ApiOperation(value = "VIN详情", notes = "VIN详情")
    public Response<VINDetailResult> getDetail(@ModelAttribute StringEnter enter) {
        return new Response<>(vinService.getDetail(enter));
    }

    /**
     * 导出
     */
    @PostMapping("/export")
    @ApiOperation(value = "VIN导出", notes = "VIN导出")
    public Response<GeneralResult> export(@ModelAttribute VINListEnter enter) {
        return new Response<>(vinService.export(enter));
    }

    /**
     * @Title: saveScooterImportExcel
     * @Description: //
     * @Param: [enter]
     * @Return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @Date: 2021/6/11 4:44 下午
     * @Author: Charles
     */
    @PostMapping(value = "/importVin")
    @ApiOperation(value = "导入VIN")
    public Response<GeneralResult> saveScooterImportExcel(@ModelAttribute @ApiParam("请求参数") ImportPartsEnter enter) {
        return new Response<>(vinService.importVin(enter));
    }

}
