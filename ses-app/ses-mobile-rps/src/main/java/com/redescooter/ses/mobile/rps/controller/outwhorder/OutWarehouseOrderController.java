package com.redescooter.ses.mobile.rps.controller.outwhorder;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.outwhorder.OutWarehouseOrderService;
import com.redescooter.ses.mobile.rps.vo.outwhorder.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 出库单接口管理
 * @author assert
 * @date 2021/1/4 16:19
 */
@Api(tags = {"出库单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/out/wh/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OutWarehouseOrderController {

    @Resource
    private OutWarehouseOrderService outWarehouseOrderService;


    /**
     * 出库单类型数量统计
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.Integer,java.lang.Integer>>
     * @author assert
     * @date 2021/1/4
    */
    @ApiOperation(value = "出库单类型数量统计")
    @PostMapping(value = "/countByOrderType")
    public Response<Map<Integer, Integer>> getOutWarehouseOrderTypeCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(outWarehouseOrderService.getOutWarehouseOrderTypeCount(enter));
    }

    /**
     * 出库类型数量统计
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.Integer,java.lang.Integer>>
     * @author assert
     * @date 2021/1/4
    */
    @ApiOperation(value = "出库类型数量统计")
    @PostMapping(value = "/countByType")
    public Response<Map<Integer, Integer>> getOutWarehouseTypeCount(@ModelAttribute CountByOrderTypeParamDTO paramDTO) {
        return new Response<>(outWarehouseOrderService.getOutWarehouseTypeCount(paramDTO));
    }

    /**
     * 出库单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.outwhorder.QueryOutWarehouseOrderResultDTO>
     * @author assert
     * @date 2021/1/4
    */
    @ApiOperation(value = "出库单列表查询")
    @PostMapping(value = "/list")
    public Response<PageResult<QueryOutWarehouseOrderResultDTO>> getOutWarehouseOrderList(@ModelAttribute QueryOutWarehouseOrderParamDTO paramDTO) {
        return new Response<>(outWarehouseOrderService.getOutWarehouseOrderList(paramDTO));
    }

    /**
     * 出库单开始质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/4
    */
    @ApiOperation(value = "开始质检")
    @PostMapping(value = "/qc")
    public Response<GeneralResult> startQc(@ModelAttribute IdEnter enter) {
        return new Response<>(outWarehouseOrderService.startQc(enter));
    }

    /**
     * 根据id查询出库单详情
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderDetailDTO>
     * @author assert
     * @date 2021/1/4
    */
    @ApiOperation(value = "出库单详情", notes = "根据id查询出库单详情")
    @PostMapping(value = "/detail")
    public Response<OutWarehouseOrderDetailDTO> getOutWarehouseOrderDetailById(@ModelAttribute IdEnter enter) {
        return new Response<>(outWarehouseOrderService.getOutWarehouseOrderDetailById(enter));
    }

    /**
     * 根据id查询出库单产品详情
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO>
     * @author assert
     * @date 2021/1/5
    */
    @ApiOperation(value = "出库单产品详情", notes = "根据id查询出库单产品详情")
    @PostMapping(value = "/productDetail")
    public Response<OutWhOrderProductDetailDTO> getOutWhOrderProductDetailByProductId(@ModelAttribute QueryProductDetailParamDTO paramDTO) {
        return new Response<>(outWarehouseOrderService.getOutWhOrderProductDetailByProductId(paramDTO));
    }

    /**
     * 保存质检结果(订单出库)
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/4
    */
    @ApiOperation(value = "保存质检结果", notes = "保存质检结果(订单出库)")
    @PostMapping(value = "/saveQcResult")
    public Response<GeneralResult> saveQcResult(@ModelAttribute SaveQcResultParamDTO paramDTO) {
        return new Response<>(outWarehouseOrderService.saveQcResult(paramDTO));
    }

}
