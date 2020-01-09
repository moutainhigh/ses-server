package com.redescooter.ses.web.delivery.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:OrderStatisticsService
 * @description: OrderStatisticsService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 11:43
 */
public interface OrderStatisticsService {
    /**
     * 今日订单状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> todayCountByStatus(GeneralEnter enter);

    /**
     * 司机排行榜
     *
     * @param enter
     * @return
     */
    List<TopTenResult> topTen(TopTenEnter enter);

    /**
     * 车辆骑行数据
     *
     * @param enter
     * @return
     */
    ScooterRideDataResult scooterRideData(GeneralEnter enter);

    /**
     * dashboard 地图
     *
     * @param enter
     * @return
     */
    MapResult map(GeneralEnter enter);

    /**
     * 仪表盘订单柱状图
     *
     * @param enter
     * @return
     */
    DeliveryChartListResult deliveryChartList(DeliveryChartEnter enter);

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> deliveryCountByStatus(GeneralEnter enter);


}
