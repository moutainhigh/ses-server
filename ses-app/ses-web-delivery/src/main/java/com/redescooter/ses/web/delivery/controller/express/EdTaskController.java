package com.redescooter.ses.web.delivery.controller.express;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.service.express.TaskService;
import com.redescooter.ses.web.delivery.vo.task.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"快递Task 模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/ed/task", method = RequestMethod.POST)
public class EdTaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(taskService.countByStatus(enter));
    }

    @PostMapping(value = "/taskTimeCount")
    @ApiOperation(value = "任务时间统计", response = Map.class)
    public Response<Map<String, Integer>> taskTimeCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(taskService.taskTimeCount(enter));
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
    public Response<List<DriverListResult>> driverList(@ModelAttribute @ApiParam("请求参数") TaskDriverLsitEnter enter) {
        return new Response<>(taskService.driverList(enter));
    }

    @PostMapping(value = "/orderList")
    @ApiOperation(value = "小订单列表", response = OrderResult.class)
    public Response<PageResult<OrderResult>> orderList(@ModelAttribute @ApiParam("请求参数") OrderListEnter enter) {
        return new Response<>(taskService.orderList(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "生成大订单", response = SaveTaskEnter.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveTaskEnter enter) {
        return new Response<>(taskService.save(enter));
    }

    /**
     * 解决订单条件过滤时，异常
     * nested exception is java.lang.IndexOutOfBoundsException: Index: 256, Size: 256
     * 的处理。
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setAutoGrowNestedPaths(true);
        binder.setAutoGrowCollectionLimit(1024);
    }

}
