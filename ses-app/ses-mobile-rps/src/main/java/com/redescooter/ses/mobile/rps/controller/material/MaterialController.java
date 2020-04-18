package com.redescooter.ses.mobile.rps.controller.material;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.mobile.rps.service.material.MaterialService;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcDetailEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcListResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcTemplateDetailResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.ReturnedCompletedEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.SaveMaterialQcEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.SaveMaterialQcResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName:MaterialController
 * @description: MaterialController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 10:11
 */
@Log4j2
@Api(tags = {"来料质检模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping(value = "/countByStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countByStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(materialService.countByStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "来料质检列表", response = MaterialQcListResult.class)
    public Response<PageResult<MaterialQcListResult>> list(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(materialService.list(enter));
    }

    @PostMapping(value = "/failList")
    @ApiOperation(value = "来料质检失败列表", response = MaterialQcListResult.class)
    public Response<PageResult<MaterialQcListResult>> failList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(materialService.failList(enter));
    }

    @PostMapping(value = "/returnedCompleted")
    @ApiOperation(value = "退货并完成", response = GeneralResult.class)
    public Response<GeneralResult> returnedCompleted(@ModelAttribute @ApiParam("请求参数") ReturnedCompletedEnter enter) {
        return new Response<>(materialService.returnedCompleted(enter));
    }

    @PostMapping(value = "/continueQc")
    @ApiOperation(value = "继续质检", response = GeneralResult.class)
    public Response<GeneralResult> continueQc(@ModelAttribute @ApiParam("请求参数") ReturnedCompletedEnter enter) {
        return new Response<>(materialService.continueQc(enter));
    }


    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = MaterialDetailResult.class)
    public Response<PageResult<MaterialDetailResult>> materialQcDetail(@ModelAttribute @ApiParam("请求参数") MaterialQcDetailEnter enter) {
        return new Response<>(materialService.materialQcDetail(enter));
    }

    @PostMapping(value = "/failDetail")
    @ApiOperation(value = "质检失败详情", response = MaterialDetailResult.class)
    public Response<PageResult<MaterialDetailResult>> materialQcFailDetail(@ModelAttribute @ApiParam("请求参数") MaterialQcDetailEnter enter) {
        return new Response<>(materialService.materialQcFailDetail(enter));
    }

    @PostMapping(value = "/materialQcTemplate")
    @ApiOperation(value = "查询质检模板", response = MaterialQcTemplateDetailResult.class)
    public Response<MaterialQcTemplateDetailResult> MaterialQcTemplate(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(materialService.MaterialQcTemplate(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存来料质检结果", response = SaveMaterialQcResult.class)
    public Response<SaveMaterialQcResult> saveMaterialQc(@ModelAttribute @ApiParam("请求参数") SaveMaterialQcEnter enter) {
        return new Response<>(materialService.saveMaterialQc(enter));
    }
}
