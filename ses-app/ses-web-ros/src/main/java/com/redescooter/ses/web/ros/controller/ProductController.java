package com.redescooter.ses.web.ros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.BomRosService;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.SecResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationDetailResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListResult;
import com.redescooter.ses.web.ros.vo.bom.combination.DeletePartEnter;
import com.redescooter.ses.web.ros.vo.bom.combination.SaveCombinationEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.SaveScooterEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterDetailResult;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListEnter;
import com.redescooter.ses.web.ros.vo.bom.scooter.ScooterListResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName:ProductController
 * @description: ProductController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/27 13:26
 */
@Api(tags = {"产品定义"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/product")
public class ProductController {

    @Autowired
    private BomRosService bomRosService;

    @PostMapping(value = "/scooterList")
    @ApiOperation(value = "车辆列表", response = ScooterListResult.class)
    public Response<PageResult<ScooterListResult>> scooterList(@ModelAttribute @ApiParam("请求参数") ScooterListEnter enter) {
        return new Response<>(bomRosService.scooterList(enter));
    }

    @PostMapping(value = "/saveScooter")
    @ApiOperation(value = "整车保存", response = GeneralResult.class)
    public Response<GeneralResult> saveScooter(@ModelAttribute @ApiParam("请求参数") SaveScooterEnter enter) {
        return new Response<>(bomRosService.saveScooter(enter));
    }

    @PostMapping(value = "/secList")
    @ApiOperation(value = "部品区域", response = SecResult.class)
    public Response<List<SecResult>> secList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(bomRosService.secList(enter));
    }

    @PostMapping(value = "/partList")
    @ApiOperation(value = "部品列表", response = QueryPartListResult.class)
    public Response<PageResult<QueryPartListResult>> partList(@ModelAttribute @ApiParam("请求参数") QueryPartListEnter enter) {
        return new Response<>(bomRosService.partList(enter));
    }

    @PostMapping(value = "/scooterDetail")
    @ApiOperation(value ="整车详情", response = ScooterDetailResult.class)
    public Response<ScooterDetailResult> scooterDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(bomRosService.scooterDetail(enter));
    }

    @PostMapping(value = "/deleteScooterPart")
    @ApiOperation(value ="整车部品删除", response = GeneralResult.class)
    public Response<GeneralResult> deleteScooterPart(@ModelAttribute @ApiParam("请求参数") DeletePartEnter enter) {
        return new Response<>(bomRosService.deleteScooterPart(enter));
    }

    @PostMapping(value = "/deleteScooter")
    @ApiOperation(value ="整车删除", response = GeneralResult.class)
    public Response<GeneralResult> deleteScooter(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(bomRosService.deleteScooter(enter));
    }

    @PostMapping(value = "/combinationList")
    @ApiOperation(value ="套餐列表", response = CombinationListResult.class)
    public Response<PageResult<CombinationListResult>> combinationList(@ModelAttribute @ApiParam("请求参数") CombinationListEnter enter) {
        return new Response<>(bomRosService.combinationList(enter));
    }

    @PostMapping(value = "/combinationListPartList")
    @ApiOperation(value ="套餐列表中的部品列表", response = QueryPartListResult.class)
    public Response<List<QueryPartListResult>> combinationListPartList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(bomRosService.combinationListPartList(enter));
    }

    @PostMapping(value = "/combinationDetail")
    @ApiOperation(value ="套餐详情", response = CombinationDetailResult.class)
    public Response<CombinationDetailResult> combinationDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(bomRosService.combinationDetail(enter));
    }

    @PostMapping(value = "/deleteCombinationPart")
    @ApiOperation(value ="套餐部品删除", response = GeneralResult.class)
    public Response<GeneralResult> deleteCombinationPart(@ModelAttribute @ApiParam("请求参数") DeletePartEnter enter) {
        return new Response<>(bomRosService.deleteCombinationPart(enter));
    }

    @PostMapping(value = "/deleteCombination")
    @ApiOperation(value ="套餐删除", response = GeneralResult.class)
    public Response<GeneralResult> deleteCombination(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(bomRosService.deleteCombination(enter));
    }

    @PostMapping(value = "/saveCombination")
    @ApiOperation(value ="套餐保存", response = GeneralResult.class)
    public Response<GeneralResult> saveCombination(@ModelAttribute @ApiParam("请求参数") SaveCombinationEnter enter) {
        return new Response<>(bomRosService.saveCombination(enter));
    }

}
