package com.redescooter.ses.mobile.rps.controller.entrustorder;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.service.entrustorder.EntrustOrderService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 委托单接口管理
 * @author assert
 * @date 2020/12/30 18:02
 */
@Api(tags = {"委托单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/entrust/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntrustOrderController {

    @Resource
    private EntrustOrderService entrustOrderService;


    /**
     * 委托单类型数量统计
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.Map<java.lang.Integer,java.lang.Integer>>
     * @author assert
     * @date 2020/12/30
    */
    @ApiOperation(value = "委托单类型数量统计", notes = "委托单类型说明：1车辆 2组装件 3部件")
    @PostMapping(value = "/countByType")
    public Response<Map<Integer, Integer>> getEntrustOrderTypeCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(entrustOrderService.getEntrustOrderTypeCount(enter));
    }

    /**
     * 委托单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.entrustorder.QueryEntrustOrderResultDTO>
     * @author assert
     * @date 2021/1/4
    */
    @ApiOperation(value = "委托单列表查询")
    @PostMapping(value = "/list")
    public Response<PageResult<QueryEntrustOrderResultDTO>> getEntrustOrderList(@ModelAttribute QueryEntrustOrderParamDTO paramDTO) {
        return new Response<>(entrustOrderService.getEntrustOrderList(paramDTO));
    }

    /**
     * 根据id查询委托单详情
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderDetailDTO>
     * @author assert
     * @date 2021/1/4
    */
    @ApiOperation(value = "委托单详情", notes = "根据id查询委托单详情")
    @PostMapping(value = "/detail")
    public Response<EntrustOrderDetailDTO> getEntrustOrderDetailById(@ModelAttribute IdEnter enter) {
        return new Response<>(entrustOrderService.getEntrustOrderDetailById(enter));
    }

    /**
     * 委托单发货
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.IdEnter>
     * @author assert
     * @date 2021/1/4
    */
    @ApiOperation(value = "委托单发货")
    @PostMapping(value = "/deliver")
    public Response<GeneralResult> entrustOrderDeliver(@ModelAttribute EntrustOrderDeliverParamDTO paramDTO) {
        return new Response<>(entrustOrderService.entrustOrderDeliver(paramDTO));
    }

    /**
     * 保存委托单产品扫码结果
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2021/1/7
    */
    @ApiOperation(value = "保存委托单产品扫码结果")
    @PostMapping(value = "/saveScanCodeResult")
    public Response<GeneralResult> saveScanCodeResult(@ModelAttribute SaveScanCodeResultParamDTO paramDTO) {
        return new Response<>(entrustOrderService.saveScanCodeResult(paramDTO));
    }

}
