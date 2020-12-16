package com.redescooter.ses.admin.dev.controller;

import com.redescooter.ses.api.common.service.OrderCountService;
import com.redescooter.ses.api.common.vo.count.OrderCountEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.count.OrderCountResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameDataCountController
 * @Description
 * @Author Aleks
 * @Date2020/12/3 14:50
 * @Version V1.0
 **/
@Api(tags = {"首页Dashboard数据统计控制器"})
@CrossOrigin
@RestController
@RequestMapping(value = "/data/count")
public class DataCountController {


    @Reference
    private OrderCountService orderCountService;

    @PostMapping(value = "/orderCount")
    @ApiOperation(value = "订单的统计", response = OrderCountResult.class)
    public Response<List<OrderCountResult>> workOrderSave(@ModelAttribute @ApiParam("请求参数") OrderCountEnter enter) {
        return new Response<>(orderCountService.orderCount(enter));
    }

}
