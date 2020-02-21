package com.redescooter.ses.api.mobile.c.service;

import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.c.vo.AllScooterChartResult;
import com.redescooter.ses.api.mobile.c.vo.ScooterChartResult;

/**
 * @ClassName:IdDashboardService
 * @description: IdDashboardService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 14:44
 */
public interface IdDashboardService {
    /**
     * @desc: 所有车辆 骑行数据
     * @param: enter
     * @retrn: ScooterChartResult
     * @auther: alex
     * @date: 2020/2/21 14:31
     * @Version: APP 1.2
     */
    ScooterChartResult allScooterChart(GeneralEnter enter);

    /**
     * @desc: 车辆骑行数据
     * @param: enter
     * @retrn: AllScooterChartResult
     * @auther: alex
     * @date: 2020/2/21 14:34
     * @Version: APP 1.2
     */
    AllScooterChartResult scooterChart(DateTimeParmEnter enter);
}
