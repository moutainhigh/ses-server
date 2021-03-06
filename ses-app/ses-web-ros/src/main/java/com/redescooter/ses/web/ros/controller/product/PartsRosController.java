package com.redescooter.ses.web.ros.controller.product;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;
import com.redescooter.ses.web.ros.service.qctemplete.ProductionQcTmepleteService;
import com.redescooter.ses.web.ros.service.restproduction.PartsRosService;
import com.redescooter.ses.web.ros.vo.bom.QcTemplateDetailResult;
import com.redescooter.ses.web.ros.vo.bom.SaveQcTemplateEnter;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportExcelPartsResult;
import com.redescooter.ses.web.ros.vo.bom.parts.ImportPartsEnter;
import com.redescooter.ses.web.ros.vo.qctemplete.QcTempleteDetailEnter;
import com.redescooter.ses.web.ros.vo.restproduct.*;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffDataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
@RequestMapping(value = "/product/production/parts")
public class PartsRosController {

    @Autowired
    private PartsRosService partsRosService;

    @Autowired
    private ProductionQcTmepleteService productionQcTmepleteService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增部件", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        // 可能是保存并发布
        return new Response<>(partsRosService.partsSave(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除部件", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") RosPartsBatchOpEnter enter) {
        return new Response<>(partsRosService.partsDelete(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑部件", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        return new Response<>(partsRosService.partsEdit(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "部件列表", response = GeneralResult.class)
    public Response<PageResult<RosPartsListResult>> list(@ModelAttribute @ApiParam("请求参数") RosPartsListEnter enter) {
        return new Response<>(partsRosService.partsList(enter));
    }

    @PostMapping(value = "/announ")
    @ApiOperation(value = "发布部件", response = GeneralResult.class)
    public Response<GeneralResult> announ(@ModelAttribute @ApiParam("请求参数") DraftAnnounEnter enter) {
        return new Response<>(partsRosService.partsAnnoun(enter));
    }

    @PostMapping(value = "/importParts")
    @ApiOperation(value = "表格导入", response = ImportExcelPartsResult.class)
    public Response<List<OpeProductionPartsDraft>> importParts(@ModelAttribute @ApiParam("请求参数") ImportPartsEnter enter) {
        return new Response<>(partsRosService.importParts(enter));
    }

    @PostMapping(value = "/announUser")
    @ApiOperation(value = "发布人下拉数据源接口", response = StaffDataResult.class)
    public Response<List<StaffDataResult>> principal(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(partsRosService.announUser(enter.getTenantId()));
    }

    @PostMapping(value = "/checkAnnounUserSafeCode")
    @ApiOperation(value = "校验发布人的安全码", response = Boolean.class)
    public Response<BooleanResult> principal(@ModelAttribute @ApiParam("请求参数") RosCheckAnnounSafeCodeEnter enter) {
        return new Response<>(partsRosService.checkAnnounUserSafeCode(enter));
    }

    @PostMapping(value = "/partsCopy")
    @ApiOperation(value = "复制部件", response = GeneralResult.class)
    public Response<GeneralResult> partsCopy(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsRosService.partsCopy(enter));
    }

    @PostMapping(value = "/partsDisable")
    @ApiOperation(value = "禁用部件", response = GeneralResult.class)
    public Response<GeneralResult> partsDisable(@ModelAttribute @ApiParam("请求参数") RosPartsBatchOpEnter enter) {
        return new Response<>(partsRosService.partsDisable(enter));
    }

    @PostMapping(value = "/listCount")
    @ApiOperation(value = "列表统计", response = GeneralResult.class)
    public Response<Map<String,Integer>> listCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(partsRosService.listCount(enter));
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出", response = GeneralResult.class)
    public Response<GeneralResult> partsExport(@ApiParam("请求参数 id") String id, HttpServletResponse response) {
        return new Response(partsRosService.partsExport(id,response));
    }

    @PostMapping(value = "/saveAnnounCheck")
    @ApiOperation(value = "保存发布校验（校验当前的数据有没有重复的）", response = GeneralResult.class)
    public Response<List<RosRepeatResult>> saveAnnounCheck(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        return new Response(partsRosService.saveAnnounCheck(enter));
    }

    @PostMapping(value = "/saveAnnounCheck2")
    @ApiOperation(value = "保存发布校验（校验有没有跟真实数据重复）", response = GeneralResult.class)
    public Response<List<RosRepeatResult>> saveAnnounCheck2(@ModelAttribute @ApiParam("请求参数") StringEnter enter) {
        return new Response(partsRosService.saveAnnounCheck2(enter));
    }

    @PostMapping(value = "/partsDisableCheck")
    @ApiOperation(value = "禁用部件的校验", response = GeneralResult.class)
    public Response<List<RosRepeatResult>> partsDisableCheck(@ModelAttribute @ApiParam("请求参数") RosPartsBatchOpEnter enter) {
        return new Response<>(partsRosService.partsDisableCheck(enter));
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
}
