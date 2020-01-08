package com.redescooter.ses.web.delivery.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.delivery.service.OrderStatisticsService;
import com.redescooter.ses.web.delivery.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:OrderStatisticsController
 * @description: OrderStatisticsController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 14:01
 */
@Api(tags = {"订单统计模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/order/statistics", method = RequestMethod.POST)
public class OrderStatisticsController {

    @Reference
    private OrderStatisticsService orderStatisticsService;

    @PostMapping(value = "/countByStatus")
    @ApiOperation(value = "今天订单状态统计", response = Map.class)
    public Response<Map<String, Integer>> todayCountByStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(orderStatisticsService.todayCountByStatus(enter));
    }

    @PostMapping(value = "/topTen")
    @ApiOperation(value = "司机排行榜", response = TopTenResult.class)
    public Response<List<TopTenResult>> topTen(@ModelAttribute @ApiParam("请求参数") TopTenEnter enter) {
        return new Response<>(orderStatisticsService.topTen(enter));
    }

    @PostMapping(value = "/scooterRideData")
    @ApiOperation(value = "车辆数据", response = TopTenResult.class)
    public Response<ScooterRideDataResult> scooterRideData(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(orderStatisticsService.scooterRideData(enter));
    }

    @PostMapping(value = "/map")
    @ApiOperation(value = "地图", response = MapResult.class)
    public Response<MapResult> map(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(orderStatisticsService.map(enter));
    }

    @PostMapping(value = "/deliveryChartList")
    @ApiOperation(value = "仪表盘订单柱状图", response = DeliveryChartResult.class)
    public Response<DeliveryChartListResult> deliveryChartList(@ModelAttribute @ApiParam("请求参数") DeliveryChartEnter enter) {
        return new Response<>(orderStatisticsService.deliveryChartList(enter));
    }
}
