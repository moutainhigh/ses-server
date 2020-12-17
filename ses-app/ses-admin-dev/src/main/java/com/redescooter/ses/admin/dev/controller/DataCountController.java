package com.redescooter.ses.admin.dev.controller;

import com.redescooter.ses.admin.dev.service.datacount.DataCountService;
import com.redescooter.ses.api.common.service.OrderCountService;
import com.redescooter.ses.api.common.vo.count.*;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.service.count.AppDownLoadCountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DataCountService dataCountService;

    @Reference
    private OrderCountService orderCountService;

    @Reference
    private AppDownLoadCountService appDownLoadCountService;



    @PostMapping(value = "/orderCount")
    @ApiOperation(value = "订单的统计", response = OrderCountResult.class)
    public Response<List<OrderCountResult>> workOrderSave(@ModelAttribute @ApiParam("请求参数") OrderCountEnter enter) {
        return new Response<>(orderCountService.orderCount(enter));
    }


    @PostMapping(value = "/appDownLoadCount")
    @ApiOperation(value = "app下载量统计", response = AppDownLoadCountResult.class)
    public Response<List<AppDownLoadCountResult>> appDownLoadCount(@ModelAttribute @ApiParam("请求参数") OrderCountEnter enter) {
        return new Response<>(appDownLoadCountService.appDownLoadCount(enter));
    }


    @PostMapping(value = "/customerCount")
    @ApiOperation(value = "客户统计", response = CustomerCountResult.class)
    public Response<List<CustomerCountResult>> customerCount(@ModelAttribute @ApiParam("请求参数") CustomerCountEnter enter) {
        return new Response<>(dataCountService.customerCount(enter));
    }


    @PostMapping(value = "/scooterCount")
    @ApiOperation(value = "车辆销售统计", response = ScooterCountResult.class)
    public Response<ScooterCountResult> scooterCount(@ModelAttribute @ApiParam("请求参数") ScooterCountEnter enter) {
        return new Response<>(dataCountService.scooterCount(enter));
    }


}
