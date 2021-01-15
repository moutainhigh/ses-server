package com.redescooter.ses.mobile.rps.controller.inwhorder;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.inwhorder.InWhOrderService;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO;
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

}
