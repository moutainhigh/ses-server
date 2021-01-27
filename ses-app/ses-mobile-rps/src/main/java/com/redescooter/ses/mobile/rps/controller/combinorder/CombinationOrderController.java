package com.redescooter.ses.mobile.rps.controller.combinorder;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.combinorder.CombinationOrderService;
import com.redescooter.ses.mobile.rps.vo.combinorder.*;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 组装单接口管理
 * @author assert
 * @date 2021/1/26 18:50
 */
@Api(tags = {"组装单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/combination/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class CombinationOrderController {

    @Resource
    private CombinationOrderService combinationOrderService;


    /**
     * 组装单类型数量统计
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.Integer,java.lang.Integer>>
     * @author assert
     * @date 2021/1/27
    */
    @ApiOperation(value = "组装单类型数量统计")
    @PostMapping(value = "/countByOrderType")
    public Response<Map<Integer, Integer>> getCombinationOrderTypeCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(combinationOrderService.getCombinationOrderTypeCount(enter));
    }

    /**
     * 组装单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.combinorder.QueryCombinationOrderResultDTO>
     * @author assert
     * @date 2021/1/27
    */
    @ApiOperation(value = "组装单列表查询")
    @PostMapping(value = "/list")
    public Response<PageResult<QueryCombinationOrderResultDTO>> getCombinationOrderList(@ModelAttribute QueryCombinationOrderParamDTO paramDTO) {
        return new Response<>(combinationOrderService.getCombinationOrderList(paramDTO));
    }

    /**
     * 组装单开始组装
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/27
    */
    @ApiOperation(value = "组装单开始组装")
    @PostMapping(value = "/startCombination")
    public Response<GeneralResult> startCombination(@ModelAttribute IdEnter enter) {
        return new Response<>(combinationOrderService.startCombination(enter));
    }

    /**
     * 根据id查询组装单详情
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.combinorder.CombinationOrderDetailDTO>
     * @author assert
     * @date 2021/1/27
    */
    @ApiOperation(value = "组装单详情", notes = "根据id查询组装单详情")
    @PostMapping(value = "/detail")
    public Response<CombinationOrderDetailDTO> getCombinationOrderDetailById(@ModelAttribute IdEnter enter) {
        return new Response<>(combinationOrderService.getCombinationOrderDetailById(enter));
    }

    /**
     * 查询组装单产品部件列表
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDetailDTO>
     * @author assert
     * @date 2021/1/27
    */
    @ApiOperation(value = "查询组装单产品部件列表")
    @PostMapping(value = "/partsList")
    public Response<CombinationListDetailDTO> getCombinationOrderPartsList(@ModelAttribute QueryCombinationPartsListParamDTO paramDTO) {
        return new Response<>(combinationOrderService.getCombinationOrderPartsList(paramDTO));
    }

    /**
     * 保存组装单产品部件扫码信息
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/27
    */
    @ApiOperation(value = "保存扫码结果", notes = "保存组装单产品部件扫码信息")
    @PostMapping(value = "/saveScanCodeResult")
    public Response<GeneralResult> saveScanCodeResult(@ModelAttribute SaveScanCodeResultParamDTO paramDTO) {
        return new Response<>(combinationOrderService.saveScanCodeResult(paramDTO));
    }

    /**
     * 完成组装
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO>
     * @author assert
     * @date 2021/1/27
    */
    @ApiOperation(value = "完成组装")
    @PostMapping(value = "/completeCombination")
    public Response<SaveScanCodeResultDTO> completeCombination(@ModelAttribute QueryCombinationPartsListParamDTO paramDTO) {
        return new Response<>(combinationOrderService.completeCombination(paramDTO));
    }

    /**
     * 组装单提交质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/27
    */
    @ApiOperation(value = "组装单提交质检")
    @PostMapping(value = "/submitQc")
    public Response<GeneralResult> submitQc(@ModelAttribute IdEnter enter) {
        return new Response<>(combinationOrderService.submitQc(enter));
    }

}
