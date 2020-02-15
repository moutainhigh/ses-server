package com.redescooter.ses.web.delivery.controller.express;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.delivery.service.express.EdDasboardService;
import com.redescooter.ses.web.delivery.vo.ScooterRideDataResult;
import com.redescooter.ses.web.delivery.vo.TopTenEnter;
import com.redescooter.ses.web.delivery.vo.TopTenResult;
import com.redescooter.ses.web.delivery.vo.edorder.ExpressOrderMapResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/2/2 15:54
 *  @version：V 1.2
 *  @Description: EdDashboardController
 */

@Api(tags = {"快递仪表统计"})
@CrossOrigin
@RestController
@RequestMapping(value = "/ed/dashboard", method = RequestMethod.POST)
public class EdDashboardController {
    @Reference
    private EdDasboardService edDasboardService;

    @PostMapping(value = "/countByStatus")
    @ApiOperation(value = "今天订单状态统计", response = Map.class)
    public Response<Map<String, Integer>> todayCountByStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(edDasboardService.todayCountByStatus(enter));
    }

    @PostMapping(value = "/topTen")
    @ApiOperation(value = "司机排行榜", response = TopTenResult.class)
    public Response<List<TopTenResult>> topTen(@ModelAttribute @ApiParam("请求参数") TopTenEnter enter) {
        return new Response<>(edDasboardService.topTen(enter));
    }

    @PostMapping(value = "/scooterRideData")
    @ApiOperation(value = "车辆数据", response = TopTenResult.class)
    public Response<ScooterRideDataResult> scooterRideData(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(edDasboardService.scooterRideData(enter));
    }

    @PostMapping(value = "/map")
    @ApiOperation(value = "地图", response = ExpressOrderMapResult.class)
    public Response<ExpressOrderMapResult> map(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(edDasboardService.map(enter));
    }

}
