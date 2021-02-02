package com.redescooter.ses.mobile.rps.controller.qc;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.qc.QcOrderService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QueryProductDetailParamDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.SaveQcResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 质检单接口管理
 * @author assert
 * @date 2021/1/21 9:43
 */
@Api(tags = {"质检单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/qc/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class QcOrderController {

    @Resource
    private QcOrderService qcOrderService;


    /**
     * 质检单类型数量统计
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.Integer,java.lang.Integer>>
     * @author assert
     * @date 2021/1/15
     */
    @ApiOperation(value = "质检单类型数量统计")
    @PostMapping(value = "/countByOrderType")
    public Response<Map<Integer, Integer>> getQcOrderTypeCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(qcOrderService.getQcOrderTypeCount(enter));
    }

    /**
     * 质检类型数量统计
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.Integer,java.lang.Integer>>
     * @author assert
     * @date 2021/1/15
     */
    @ApiOperation(value = "质检类型数量统计")
    @PostMapping(value = "/countByType")
    public Response<Map<Integer, Integer>> getQcTypeCount(@ModelAttribute CountByOrderTypeParamDTO paramDTO) {
        return new Response<>(qcOrderService.getQcTypeCount(paramDTO));
    }

    /**
     * 质检单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.qc.QueryQcOrderResultDTO>
     * @author assert
     * @date 2021/1/25
    */
    @ApiOperation(value = "质检单列表查询")
    @PostMapping(value = "/list")
    public Response<PageResult<QueryQcOrderResultDTO>> getQcOrderList(@ModelAttribute QueryQcOrderParamDTO paramDTO) {
        return new Response<>(qcOrderService.getQcOrderList(paramDTO));
    }

    /**
     * 质检单开始质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/25
    */
    @ApiOperation(value = "开始质检")
    @PostMapping(value = "/startQc")
    public Response<GeneralResult> startQc(@ModelAttribute IdEnter enter) {
        return new Response<>(qcOrderService.startQc(enter));
    }

    /**
     * 根据id查询质检单详情
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.qc.QcOrderDetailDTO>
     * @author assert
     * @date 2021/1/25
    */
    @ApiOperation(value = "质检单详情", notes = "根据id查询质检单详情")
    @PostMapping(value = "/detail")
    public Response<QcOrderDetailDTO> getQcOrderDetailById(@ModelAttribute IdEnter enter) {
        return new Response<>(qcOrderService.getQcOrderDetailById(enter));
    }

    /**
     * 查询质检单产品详情
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.qc.QcOrderProductDetailDTO>
     * @author assert
     * @date 2021/1/26
    */
    @ApiOperation(value = "质检单产品详情")
    @PostMapping(value = "/productDetail")
    public Response<QcOrderProductDetailDTO> getProductDetailByProductId(@ModelAttribute QueryProductDetailParamDTO paramDTO) {
        return new Response<>(qcOrderService.getProductDetailByProductId(paramDTO));
    }

    /**
     * 根据产品id查询产品质检模板信息
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateResultDTO>
     * @author assert
     * @date 2021/1/6
     */
    @ApiOperation(value = "质检单质检模板", notes = "根据产品id查询产品质检模板信息")
    @PostMapping(value = "/qcTemplate")
    public Response<QueryQcTemplateResultDTO> getQcTemplateByIdAndType(@ModelAttribute QueryQcTemplateParamDTO paramDTO) {
        return new Response<>(qcOrderService.getQcTemplateByIdAndType(paramDTO));
    }

    /**
     * 保存质检结果
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO>
     * @author assert
     * @date 2021/1/4
     */
    @ApiOperation(value = "保存质检结果")
    @PostMapping(value = "/saveQcResult")
    public Response<SaveScanCodeResultDTO> saveQcResult(@ModelAttribute SaveQcResultParamDTO paramDTO) {
        return new Response<>(qcOrderService.saveQcResult(paramDTO));
    }

    /**
     * 完成质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/25
    */
    @ApiOperation(value = "完成质检")
    @PostMapping(value = "/completeQc")
    public Response<GeneralResult> completeQc(@ModelAttribute IdEnter enter) {
        return new Response<>(qcOrderService.completeQc(enter));
    }

}
