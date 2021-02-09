package com.redescooter.ses.web.ros.controller.restproduction.allocateorder;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.restproductionorder.allocateorder.AllocateOrderService;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.CancelOrderEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassNameAllocateOrderController
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:37
 * @Version V1.0
 **/
@Api(tags = {"调拨单模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/allocate/order")
public class AllocateOrderController {

    @Autowired
    private AllocateOrderService allocateOrderService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "调拨单列表", response = AllocateOrderListResult.class)
    public Response<PageResult<AllocateOrderListResult>> allocateList(@ModelAttribute @ApiParam("请求参数") AllocateOrderListEnter enter) {
        return new Response<>(allocateOrderService.allocateList(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "调拨单新增", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> allocateSave(@ModelAttribute @ApiParam("请求参数") AllocateOrderOrUpdateSaveEnter enter) {
        return new Response<>(allocateOrderService.allocateSave(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "调拨单编辑", response = GeneralResult.class)
    public Response<GeneralResult> allocateEdit(@ModelAttribute @ApiParam("请求参数") AllocateOrderOrUpdateSaveEnter enter) {
        return new Response<>(allocateOrderService.allocateEdit(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "调拨单详情", response = AllocateOrderDetailResult.class)
    public Response<AllocateOrderDetailResult> allocateDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateOrderService.allocateDetail(enter));
    }

    @PostMapping(value = "/confirmOrder")
    @ApiOperation(value = "调拨单立即下单", response = GeneralResult.class)
    public Response<GeneralResult> allocateConfirmOrder(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateOrderService.allocateConfirmOrder(enter));
    }

    @PostMapping(value = "/cancelOrder")
    @ApiOperation(value = "调拨单取消下单", response = GeneralResult.class)
    public Response<GeneralResult> allocateCancelOrder(@ModelAttribute @ApiParam("请求参数") CancelOrderEnter enter) {
        return new Response<>(allocateOrderService.allocateCancelOrder(enter));
    }

    @PostMapping(value = "/userData")
    @ApiOperation(value = "收货人、发货人下拉数据源接口", response = UserDataResult.class)
    public Response<List<UserDataResult>> userData(@ModelAttribute @ApiParam("请求参数") UserDataEnter enter) {
        return new Response<>(allocateOrderService.userData(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "调拨单删除", response = GeneralResult.class)
    public Response<GeneralResult> allocateDelete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(allocateOrderService.allocateDelete(enter));
    }

    @PostMapping(value = "/listCount")
    @ApiOperation(value = "列表统计", response = Map.class)
    public Response<Map<String, Integer>> listCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(allocateOrderService.listCount(enter));
    }

    @PostMapping(value = "/whData")
    @ApiOperation(value = "仓库下拉数据源接口", response = WhDataResult.class)
    public Response<List<WhDataResult>> whData(@ModelAttribute @ApiParam("请求参数") WhDataEnter enter) {
        return new Response<>(allocateOrderService.whData(enter));
    }

}
