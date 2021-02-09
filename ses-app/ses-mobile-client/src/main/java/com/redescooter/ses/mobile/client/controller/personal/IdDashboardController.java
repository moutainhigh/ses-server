package com.redescooter.ses.mobile.client.controller.personal;

import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.mobile.c.service.IdDashboardService;
import com.redescooter.ses.api.mobile.c.vo.AllScooterChartResult;
import com.redescooter.ses.api.mobile.c.vo.ScooterChartResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:IdDashboardController
 * @description: IdDashboardController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 11:25
 */
@Slf4j
@Api(tags = {"数据统计模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/id", method = RequestMethod.POST)
public class IdDashboardController {

    @DubboReference
    private IdDashboardService idDashboardService;

    @ApiOperation(value = "骑行数据总计",response = ScooterChartResult.class)
    @RequestMapping(value = "/allScooterChart")
    public Response<ScooterChartResult> allScooterChart(@ModelAttribute GeneralEnter enter) {
        return new Response<>(idDashboardService.allScooterChart(enter));
    }

    @ApiOperation(value = "骑行数据图标",response = AllScooterChartResult.class)
    @RequestMapping(value = "/scooterChart")
    public Response<AllScooterChartResult> scooterChart(@ModelAttribute DateTimeParmEnter enter) {
        return new Response<>(idDashboardService.scooterChart(enter));
    }

}
