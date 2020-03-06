package com.redescooter.ses.web.ros.controller.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.bom.BomRosService;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.SecResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationDetailResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListResult;
import com.redescooter.ses.web.ros.vo.bom.combination.DeletePartEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.SaveCombinationEnter;
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
 * @ClassName:testController
 * @description: testController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/27 16:52
 */
@Api(tags = {"组合管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/combination")
public class CombinationController {

    @Autowired
    private BomRosService bomRosService;

    @PostMapping(value = "/secList")
    @ApiOperation(value = "部品区域", response = SecResult.class)
    public Response<List<SecResult>> secList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(bomRosService.secList(enter));
    }

    @PostMapping(value = "/combinationList")
    @ApiOperation(value ="组合列表", response = CombinationListResult.class)
    public Response<PageResult<CombinationListResult>> combinationList(@ModelAttribute @ApiParam("请求参数") CombinationListEnter enter) {
        return new Response<>(bomRosService.combinationList(enter));
    }

    @PostMapping(value = "/combinationListPartList")
    @ApiOperation(value ="组合列表中的部品列表", response = QueryPartListResult.class)
    public Response<List<QueryPartListResult>> combinationListPartList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(bomRosService.combinationListPartList(enter));
    }

    @PostMapping(value = "/combinationDetail")
    @ApiOperation(value ="组合详情", response = CombinationDetailResult.class)
    public Response<CombinationDetailResult> combinationDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(bomRosService.combinationDetail(enter));
    }

    @PostMapping(value = "/deleteCombinationPart")
    @ApiOperation(value ="组合部品删除", response = GeneralResult.class)
    public Response<GeneralResult> deleteCombinationPart(@ModelAttribute @ApiParam("请求参数") DeletePartEnter enter) {
        return new Response<>(bomRosService.deleteCombinationPart(enter));
    }

    @PostMapping(value = "/deleteCombination")
    @ApiOperation(value ="组合删除", response = GeneralResult.class)
    public Response<GeneralResult> deleteCombination(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(bomRosService.deleteCombination(enter));
    }

    @PostMapping(value = "/saveCombination")
    @ApiOperation(value ="组合保存", response = GeneralResult.class)
    public Response<GeneralResult> saveCombination(@ModelAttribute @ApiParam("请求参数") SaveCombinationEnter enter) {
        return new Response<>(bomRosService.saveCombination(enter));
    }
}
