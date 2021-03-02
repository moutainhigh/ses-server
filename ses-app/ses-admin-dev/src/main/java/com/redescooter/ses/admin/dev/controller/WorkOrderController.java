package com.redescooter.ses.admin.dev.controller;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.workorder.StatusFlowEnter;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderDetailResult;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderListEnter;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderListResult;
import com.redescooter.ses.api.common.vo.workorder.WorkOrderSaveOrUpdateEnter;
import com.redescooter.ses.api.common.vo.workorder.workOrderReplyEnter;
import com.redescooter.ses.api.foundation.service.workorder.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassNameWordOrderController
 * @Description
 * @Author Aleks
 * @Date2020/12/3 16:28
 * @Version V1.0
 **/
@Api(tags = {"工单控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/work/order")
public class WorkOrderController {

    @DubboReference
    private WorkOrderService workOrderService;

    @PostMapping(value = "/workOrderList")
    @ApiOperation(value = "工单列表", response = WorkOrderListResult.class)
    public Response<PageResult<WorkOrderListResult>> workOrderList(@ModelAttribute @ApiParam("请求参数") WorkOrderListEnter enter) {
        return new Response<>(workOrderService.workOrderList(enter));
    }

    @PostMapping(value = "/workOrderSave")
    @ApiOperation(value = "工单新增", response = GeneralResult.class)
    public Response<GeneralResult> workOrderSave(@ModelAttribute @ApiParam("请求参数") WorkOrderSaveOrUpdateEnter enter) {
        return new Response<>(workOrderService.workOrderSave(enter));
    }

//    @PostMapping(value = "/workOrderUpdate")
//    @ApiOperation(value = "工单编辑", response = GeneralResult.class)
//    public Response<GeneralResult> workOrderUpdate(@ModelAttribute @ApiParam("请求参数") WorkOrderSaveOrUpdateEnter enter) {
//        return new Response(workOrderService.workOrderUpdate(enter));
//    }

    @PostMapping(value = "/workOrderDelete")
    @ApiOperation(value = "工单删除", response = GeneralResult.class)
    public Response<GeneralResult> workOrderDelete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(workOrderService.workOrderDelete(enter));
    }

    @PostMapping(value = "/workOrderDetail")
    @ApiOperation(value = "工单详情", response = WorkOrderDetailResult.class)
    public Response<WorkOrderDetailResult> workOrderDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(workOrderService.workOrderDetail(enter));
    }

    @PostMapping(value = "/workOrderStatusFlow")
    @ApiOperation(value = "工单状态流转(只能往当前状态的后面的状态跳  随便跳)", response = GeneralResult.class)
    public Response<GeneralResult> workOrderStatusFlow(@ModelAttribute @ApiParam("请求参数") StatusFlowEnter enter) {
        return new Response<>(workOrderService.workOrderStatusFlow(enter));
    }

    @PostMapping(value = "/workOrderReply")
    @ApiOperation(value = "工单回复", response = GeneralResult.class)
    public Response<GeneralResult> workOrderReply(@ModelAttribute @ApiParam("请求参数") workOrderReplyEnter enter) {
        return new Response<>(workOrderService.workOrderReply(enter));
    }

}
