package com.redescooter.ses.mobile.client.controller.express;

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
@RequestMapping(value = "/ed/data", method = RequestMethod.POST)
public class EdStatisticalDataController {

    @Reference
    private StatisticalDataService statisticalDataService;

    @ApiOperation(value = "订单统计数据")
    @RequestMapping(value = "/mobileBEdDeliveryChart")
    public Response<MobileBDeliveryChartResult> mobileBEdDeliveryChart(@ModelAttribute DateTimeParmEnter enter) {
        return new Response<>(statisticalDataService.mobileBDeliveryChart(enter));
    }
}
