package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.vo.*;

import java.util.List;

/**
 * @ClassName:OrderStatisticsServiceMapper
 * @description: OrderStatisticsServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 12:07
 */
public interface OrderStatisticsServiceMapper {

    /**
     * topTen
     *
     * @param enter
     * @return
     */
    List<TopTenResult> topTen(TopTenEnter enter);

    /**
     * 车辆统计数据
     *
     * @param enter
     * @return
     */
    ScooterRideDataResult scooterRideData(GeneralEnter enter);

    /**
     * 订单状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> todayCountByStatus(GeneralEnter enter);

    /**
     * 今日Today（单位为小时，显示今日配送数据）
     * @param enter
     * @return
     */
    List<DeliveryChartResult> deliveryChartToday(DeliveryChartDto enter);

    /**
     * 近七日<7Day（单位为日，显示近7天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
     * @param enter
     * @return
     */
    List<DeliveryChartResult> deliveryChart7Day(DeliveryChartDto enter);

    /**
     * 近30日<30Day（单位为日，显示近30天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
     * @param enter
     * @return
     */
    List<DeliveryChartResult> deliveryChart30Day(DeliveryChartDto enter);

    /**
     * 年Year（单位为月，显示截止至今所有数据，点击某一月可查看该月数据，点击某一日可查看该日数据
     * @param enter
     * @return
     */
    List<DeliveryChartResult> deliveryChart365Day(DeliveryChartDto enter);

    /**
     * 所有订单状态统计
     * @param enter
     * @return
     */
    List<CountByStatusResult> deliveryCountByStatus(GeneralEnter enter);



    /**
     * 今日Today（单位为小时，显示今日配送数据）
     * @param enter
     * @return
     */
    List<DeliveryChartResult> eDDliveryChartToday(DeliveryChartDto enter);

    /**
     * 近七日<7Day（单位为日，显示近7天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
     * @param enter
     * @return
     */
    List<DeliveryChartResult> eDDeliveryChart7Day(DeliveryChartDto enter);

    /**
     * 近30日<30Day（单位为日，显示近30天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
     * @param enter
     * @return
     */
    List<DeliveryChartResult> eDDeliveryChart30Day(DeliveryChartDto enter);

    /**
     * 年Year（单位为月，显示截止至今所有数据，点击某一月可查看该月数据，点击某一日可查看该日数据
     * @param enter
     * @return
     */
    List<DeliveryChartResult> eDDeliveryChart365Day(DeliveryChartDto enter);

    /**
     * 所有订单状态统计
     * @param enter
     * @return
     */
    List<CountByStatusResult> eDDeliveryCountByStatus(GeneralEnter enter);
}
