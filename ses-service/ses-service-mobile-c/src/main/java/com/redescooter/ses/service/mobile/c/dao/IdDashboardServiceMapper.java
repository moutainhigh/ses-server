package com.redescooter.ses.service.mobile.c.dao;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.mobile.c.vo.ScooterChartResult;

/**
 * @ClassName:IdDashboardService
 * @description: IdDashboardService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/21 14:59
 */
public interface IdDashboardServiceMapper {

    /**
     * @desc: 车辆统计
     * @param: enter
     * @retrn: ScooterChartResult
     * @auther: alex
     * @date: 2020/2/21 15:00
     * @Version: APP 1.2
     */
    List<ScooterChartResult> scooterChart(DateTimeParmEnter enter);
}
