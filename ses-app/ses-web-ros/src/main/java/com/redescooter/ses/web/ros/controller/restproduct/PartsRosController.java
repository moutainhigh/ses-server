package com.redescooter.ses.web.ros.controller.restproduct;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproduction.PartsRosService;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosCheckAnnounSafeCode;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListResult;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsSaveOrUpdateEnter;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffDataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNamePartsRosController
 * @Description
 * @Author Aleks
 * @Date2020/9/23 17:22
 * @Version V1.0
 **/
@Api(tags = {"部件控制层"})
@CrossOrigin
@RestController
@RequestMapping(value = "/production/parts")
public class PartsRosController {

    @Autowired
    private PartsRosService partsRosService;


    @PostMapping(value = "/save")
    @ApiOperation(value = "新增部件", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") List<RosPartsSaveOrUpdateEnter> enter) {
        // 可能是保存并发布
        return new Response<>(partsRosService.partsSave(enter));
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除部件", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsRosService.partsDelete(enter));
    }


    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑部件", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") List<RosPartsSaveOrUpdateEnter> enter) {
        return new Response<>(partsRosService.partsEdit(enter));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "部件列表", response = GeneralResult.class)
    public Response<PageResult<RosPartsListResult>> list(@ModelAttribute @ApiParam("请求参数") RosPartsListEnter enter) {
        return new Response<>(partsRosService.partsList(enter));
    }


    @PostMapping(value = "/announ")
    @ApiOperation(value = "发布部件", response = GeneralResult.class)
    public Response<GeneralResult> announ(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsRosService.partsAnnoun(enter));
    }


    @PostMapping(value = "/importParts")
    @ApiOperation(value = "表格导入", response = ImportExcelPartsResult.class)
    public Response<ImportExcelPartsResult> importParts(@ModelAttribute @ApiParam("请求参数") ImportPartsEnter enter) {
        return new Response<>(partsRosService.importParts(enter));
    }


    @PostMapping(value = "/announUser")
    @ApiOperation(value = "发布人下拉数据源接口", response = StaffDataResult.class)
    public Response<List<StaffDataResult>> principal(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(partsRosService.announUser(enter.getTenantId()));
    }


    @PostMapping(value = "/checkAnnounUserSafeCode")
    @ApiOperation(value = "校验发布人的安全码", response = Boolean.class)
    public Response<Boolean> principal(@ModelAttribute @ApiParam("请求参数") RosCheckAnnounSafeCode enter) {
        return new Response<>(partsRosService.checkAnnounUserSafeCode(enter));
    }


    @PostMapping(value = "/partsCopy")
    @ApiOperation(value = "复制部件", response = GeneralResult.class)
    public Response<GeneralResult> partsCopy(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsRosService.partsCopy(enter));
    }


    @PostMapping(value = "/partsDisable")
    @ApiOperation(value = "禁用部件", response = GeneralResult.class)
    public Response<GeneralResult> partsDisable(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsRosService.partsDisable(enter));
    }
}
