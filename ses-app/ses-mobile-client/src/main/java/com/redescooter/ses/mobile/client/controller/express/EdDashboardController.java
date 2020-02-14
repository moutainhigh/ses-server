package com.redescooter.ses.mobile.client.controller.express;

import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.mobile.b.service.express.EdDashboardService;
import com.redescooter.ses.api.mobile.b.vo.express.EdMobileBExpressOrderChartResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api(tags = {"快递统计模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/ed/dashboard", method = RequestMethod.POST)
public class EdDashboardController {

    @Reference
    private EdDashboardService edDashboardService;

    @PostMapping(value = "/allCountByStatus")
    @ApiOperation(value = "订单状态", response = Map.class)
    public Response<Map<String,Integer>> allCountByStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(edDashboardService.allCountByStatus(enter));
    }

    @PostMapping(value = "/expressOrderChart")
    @ApiOperation(value = "订单统计图表", response = EdMobileBExpressOrderChartResult.class)
    public Response<EdMobileBExpressOrderChartResult> mobileBExpressOrderChart(@ModelAttribute @ApiParam("请求参数") DateTimeParmEnter enter) {
        return new Response<>(edDashboardService.mobileBExpressOrderChart(enter));
    }

}
