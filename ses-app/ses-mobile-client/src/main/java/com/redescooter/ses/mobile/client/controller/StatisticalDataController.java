package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.mobile.b.service.StatisticalDataService;
import com.redescooter.ses.api.mobile.b.vo.AllMobileBScooterChartResult;
import com.redescooter.ses.api.mobile.b.vo.MobileBDeliveryChartResult;
import com.redescooter.ses.api.mobile.b.vo.MobileBScooterChartResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:StatisticalDataController
 * @description: StatisticalDataController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/02 10:35
 */
@Slf4j
@Api(tags = {"数据统计模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/data", method = RequestMethod.POST)
public class StatisticalDataController {

    @Reference
    private StatisticalDataService statisticalDataService;

    @ApiOperation(value = "司机骑行总统计")
    @RequestMapping(value = "/mobileBAllScooterChart")
    public Response<MobileBScooterChartResult> mobileBAllScooterChart(@ModelAttribute GeneralEnter enter) {
        return new Response<>(statisticalDataService.mobileBAllScooterChart(enter));
    }

    @ApiOperation(value = "车辆统计数据")
    @RequestMapping(value = "/mobileBScooter")
    public Response<AllMobileBScooterChartResult> mobileBScooterChart(@ModelAttribute DateTimeParmEnter enter) {
        return new Response<>(statisticalDataService.mobileBScooterChart(enter));
    }

    @ApiOperation(value = "订单统计数据")
    @RequestMapping(value = "/mobileBDelivery")
//    @IgnoreLoginCheck
    public Response<MobileBDeliveryChartResult> mobileBDeliveryChart(@ModelAttribute DateTimeParmEnter enter) {
        return new Response<>(statisticalDataService.mobileBDeliveryChart(enter));
    }

    @ApiOperation(value = "配送单状态统计")
    @RequestMapping(value = "/mobileBDeliveryStatusCount")
    public Response<Map<String, Integer>> mobileBDeliveryStatusCount(@ModelAttribute GeneralEnter enter) {
        return new Response<>(statisticalDataService.allDriverDeliveryStatusCount(enter));
    }

}
