package com.redescooter.ses.mobile.rps.controller.inwhorder;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.inwhorder.InWhOrderService;
import com.redescooter.ses.mobile.rps.vo.inwhorder.*;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QueryProductDetailParamDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.UpdatePartsQcQtyParamDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 入库单接口管理
 * @author assert
 * @date 2021/1/15 16:17
 */
@Api(tags = {"入库单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/in/wh/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class InWarehouseOrderController {

    @Resource
    private InWhOrderService inWhOrderService;


    /**
     * 入库单类型数量统计
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.Integer,java.lang.Integer>>
     * @author assert
     * @date 2021/1/15
    */
    @ApiOperation(value = "入库单类型数量统计")
    @PostMapping(value = "/countByOrderType")
    public Response<Map<Integer, Integer>> getInWarehouseOrderTypeCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(inWhOrderService.getInWarehouseOrderTypeCount(enter));
    }

    /**
     * 入库类型数量统计
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.Integer,java.lang.Integer>>
     * @author assert
     * @date 2021/1/15
    */
    @ApiOperation(value = "入库类型数量统计")
    @PostMapping(value = "/countByType")
    public Response<Map<Integer, Integer>> getInWarehouseTypeCount(@ModelAttribute CountByOrderTypeParamDTO paramDTO) {
        return new Response<>(inWhOrderService.getInWarehouseTypeCount(paramDTO));
    }

    /**
     * 入库单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO>>
     * @author assert
     * @date 2021/1/15
    */
    @ApiOperation(value = "入库单列表查询")
    @PostMapping(value = "/list")
    public Response<PageResult<QueryInWhOrderResultDTO>> getInWarehouseOrderList(@ModelAttribute QueryInWhOrderParamDTO paramDTO) {
        return new Response<>(inWhOrderService.getInWarehouseOrderList(paramDTO));
    }

    /**
     * 入库单开始质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/15
    */
    @ApiOperation(value = "入库单开始质检")
    @PostMapping(value = "/qc")
    public Response<GeneralResult> startQc(@ModelAttribute IdEnter enter) {
        return new Response<>(inWhOrderService.startQc(enter));
    }

    /**
     * 根据id查询入库单详情
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO>
     * @author assert
     * @date 2021/1/15
    */
    @ApiOperation(value = "入库单详情", notes = "根据id查询入库单详情")
    @PostMapping(value = "/detail")
    public Response<InWhOrderDetailDTO> getInWarehouseOrderDetailById(@ModelAttribute IdEnter enter) {
        return new Response<>(inWhOrderService.getInWarehouseOrderDetailById(enter));
    }

    /**
     * 根据productId查询入库单产品详情(1.0.0版本没有这个接口)
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDetailDTO>
     * @author assert
     * @date 2021/1/19
     */
    @ApiOperation(value = "入库单产品详情(1.0.0版本没有这个接口)", notes = "根据productId查询入库单产品详情")
    @PostMapping(value = "/productDetail")
    public Response<InWhOrderProductDetailDTO> getProductDetailByProductId(@ModelAttribute QueryProductDetailParamDTO paramDTO) {
        return new Response<>(inWhOrderService.getProductDetailByProductId(paramDTO));
    }

    /**
     * 修改部件质检数量
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/18
    */
    @ApiOperation(value = "修改部件质检数量", notes = "修改部件质检数量(用于无码部件使用)")
    @PostMapping(value = "/updatePartsQcQty")
    public Response<GeneralResult> updatePartsQcQty(@ModelAttribute UpdatePartsQcQtyParamDTO paramDTO) {
        return new Response<>(inWhOrderService.updatePartsQcQty(paramDTO));
    }

    /**
     * 完成质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/18
    */
    @ApiOperation(value = "完成质检")
    @PostMapping(value = "/completeQc")
    public Response<GeneralResult> completeQc(@ModelAttribute IdEnter enter) {
        return new Response<>(inWhOrderService.completeQc(enter));
    }

    /**
     * 确认入库
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.inwhorder.ConfirmStorageResultDTO>
     * @author assert
     * @date 2021/1/19
    */
    @ApiOperation(value = "确认入库")
    @PostMapping(value = "/confirmStorage")
    public Response<ConfirmStorageResultDTO> confirmStorage(@ModelAttribute ConfirmStorageParamDTO paramDTO) {
        return new Response<>(inWhOrderService.confirmStorage(paramDTO));
    }

}
