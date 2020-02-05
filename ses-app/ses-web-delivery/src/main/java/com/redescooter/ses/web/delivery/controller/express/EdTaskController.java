package com.redescooter.ses.web.delivery.controller.express;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.service.express.TaskService;
import com.redescooter.ses.web.delivery.vo.task.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"快递Task 模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/rd/task", method = RequestMethod.POST)
public class EdTaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(taskService.countByStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "task列表", response = TaskResult.class)
    public Response<PageResult<TaskResult>> list(@ModelAttribute @ApiParam("请求参数") TaskListEnter enter) {
        return new Response<>(taskService.list(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "详情", response = TaskResult.class)
    public Response<TaskResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(taskService.detail(enter));
    }

    @PostMapping(value = "/detailOrderList")
    @ApiOperation(value = "task详情中的order列表", response = OrderResult.class)
    public Response<List<OrderResult>> detailOrderList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(taskService.detailOrderList(enter));
    }

    @PostMapping(value = "/driverList")
    @ApiOperation(value = "司机列表", response = DriverListResult.class)
    public Response<DriverListResult> driverList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(taskService.driverList(enter));
    }

    @PostMapping(value = "/orderList")
    @ApiOperation(value = "小订单列表", response = OrderResult.class)
    public Response<PageResult<OrderResult>> orderList(@ModelAttribute @ApiParam("请求参数") OrderListEnter enter) {
        return new Response<>(taskService.orderList(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "生成大订单", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveTaskEnter enter) {
        return new Response<>(taskService.save(enter));
    }
}
