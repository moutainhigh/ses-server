package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.mobile.b.service.StatisticalDataService;
import com.redescooter.ses.api.mobile.b.vo.MobileBDeliveryChartResult;
import com.redescooter.ses.api.mobile.b.vo.MobileBScooterChartResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @IgnoreLoginCheck
    @ApiOperation(value = "车辆统计数据")
    @RequestMapping(value = "/mobileBScooter")
    public Response<MobileBScooterChartResult> mobileBScooterChart(@ModelAttribute GeneralEnter enter) {
        enter.setUserId(1071493L);
        enter.setTenantId(0L);
        return new Response<>(statisticalDataService.mobileBScooterChart(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "订单统计数据")
    @RequestMapping(value = "/mobileBDelivery")
    public Response<MobileBDeliveryChartResult> mobileBDeliveryChart(@ModelAttribute GeneralEnter enter) {
        enter.setUserId(1071493L);
        enter.setTenantId(0L);
        return new Response<>(statisticalDataService.mobileBDeliveryChart(enter));
    }
}
