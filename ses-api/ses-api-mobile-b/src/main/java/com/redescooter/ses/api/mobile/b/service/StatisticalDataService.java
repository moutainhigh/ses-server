package com.redescooter.ses.api.mobile.b.service;

import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.vo.AllMobileBScooterChartResult;
import com.redescooter.ses.api.mobile.b.vo.MobileBDeliveryChartResult;
import com.redescooter.ses.api.mobile.b.vo.MobileBScooterChartResult;
import com.redescooter.ses.api.mobile.b.vo.SaveDeliveryStatEnter;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:StatisticalDataService
 * @description: StatisticalDataService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 16:51
 */
public interface StatisticalDataService {
    /**
     * 保存司机骑行数据
     *
     * @param enter
     * @return
     */
    void saveDriverRideStat(List<SaveDeliveryStatEnter> enter);

    /**
     * 车辆统计数据
     *
     * @param enter
     * @return
     */
    void saveScooterRideStat(List<SaveDeliveryStatEnter> enter);

    /**
     * 订单数据图表统计
     *
     * @param enter
     * @return
     */
    MobileBDeliveryChartResult mobileBDeliveryChart(DateTimeParmEnter enter);

    /**
     * 订单数据图表统计
     *
     * @param enter
     * @return
     */
    MobileBDeliveryChartResult mobileBEdDeliveryChart(DateTimeParmEnter enter);

    /**
     * 车辆图表数据统计
     *
     * @param enter
     * @return
     */
    AllMobileBScooterChartResult mobileBScooterChart(DateTimeParmEnter enter);

    /**
     * 司机全部订单统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> allDriverDeliveryStatusCount(GeneralEnter enter);

    /**
     * 司机骑行总统计
     *
     * @param enter
     * @return
     */
    MobileBScooterChartResult mobileBAllScooterChart(GeneralEnter enter);

}
