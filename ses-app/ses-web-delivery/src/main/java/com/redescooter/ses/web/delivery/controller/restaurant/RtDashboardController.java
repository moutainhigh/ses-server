package com.redescooter.ses.web.delivery.controller.restaurant;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.delivery.service.RtDashboardService;
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
 * @description: RtDashboardController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 14:01
 */
@Api(tags = {"仪表统计"})
@CrossOrigin
@RestController
@RequestMapping(value = "/rt/dashboard", method = RequestMethod.POST)
public class RtDashboardController {

    @Reference
    private RtDashboardService rtDashboardService;

    @PostMapping(value = "/countByStatus")
    @ApiOperation(value = "今天订单状态统计", response = Map.class)
    public Response<Map<String, Integer>> todayCountByStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rtDashboardService.todayCountByStatus(enter));
    }

    @PostMapping(value = "/topTen")
    @ApiOperation(value = "司机排行榜", response = TopTenResult.class)
    public Response<List<TopTenResult>> topTen(@ModelAttribute @ApiParam("请求参数") TopTenEnter enter) {
        return new Response<>(rtDashboardService.topTen(enter));
    }

    @PostMapping(value = "/scooterRideData")
    @ApiOperation(value = "车辆数据", response = TopTenResult.class)
    public Response<ScooterRideDataResult> scooterRideData(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rtDashboardService.scooterRideData(enter));
    }

    @PostMapping(value = "/map")
    @ApiOperation(value = "地图", response = MapResult.class)
    public Response<MapResult> map(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rtDashboardService.map(enter));
    }

    @PostMapping(value = "/deliveryChartList")
    @ApiOperation(value = "仪表盘订单柱状图", response = DeliveryChartResult.class)
    public Response<DeliveryChartListResult> deliveryChartList(@ModelAttribute @ApiParam("请求参数") DeliveryChartEnter enter) {
        return new Response<>(rtDashboardService.deliveryChartList(enter));
    }

    @PostMapping(value = "/deliveryCountByStatus")
    @ApiOperation(value = "总订单状态统计", response = Map.class)
    public Response<Map<String, Integer>> deliveryCountByStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rtDashboardService.deliveryCountByStatus(enter));
    }
}
