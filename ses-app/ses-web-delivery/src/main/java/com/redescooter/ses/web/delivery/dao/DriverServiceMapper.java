package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 11:28 上午
 * @ClassName: DriverServiceMapper
 * @Function: TODO
 */
public interface DriverServiceMapper {

    /**
     * 司机列表总条数
     *
     * @param page
     * @return
     */
    int listCount(ListDriverPage page);

    /**
     * 车辆列表
     *
     * @param page
     * @return
     */
    List<ListDriverResult> list(ListDriverPage page);

    /**
     * 司机状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countStatus(GeneralEnter enter);

    /**
     * 司机已配送的订单状态
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> driverDeliveryCountByStatus(IdEnter enter);

    /**
     * 司机历史订单
     *
     * @param enter
     * @return
     */
    int deliveryHistroyCount(DeliveryHistroyEnter enter);

    /**
     * 司机历史订单
     *
     * @param enter
     * @return
     */
    List<DeliveryHistroyResult> deliveryHistroyList(DeliveryHistroyEnter enter);

    /**
     * 司机车辆已归还分配表
     *
     * @param enter
     * @return
     */
    int driverScooterUsedHistroyCount(DriverScooterHistroyEnter enter);

    /**
     * 司机车辆使用中分配表
     *
     * @param enter
     * @return
     */
    int driverScooterUsingHistroyCount(DriverScooterHistroyEnter enter);


    /**
     * 司机车辆已归还分配表
     *
     * @param enter
     * @return
     */
    List<DriverScooterHistoryResult> driverScooterUsedHistroyList(DriverScooterHistroyEnter enter);

    /**
     * 司机车辆使用中分配表
     *
     * @param enter
     * @return
     */
    List<DriverScooterHistoryResult> driverScooterUsingHistroyList(DriverScooterHistroyEnter enter);


    /**
     * 今日Today（单位为小时，显示今日配送数据）
     *
     * @param enter
     * @return
     */
    List<DeliveryChartResult> driverDeliveryChartToday(DeliveryChartDto enter);

    /**
     * 近七日<7Day（单位为日，显示近7天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
     * @param enter
     * @return
     */
    List<DeliveryChartResult> driverDeliveryChart7Day(DeliveryChartDto enter);

    /**
     * 近30日<30Day（单位为日，显示近30天配送数据，点击某一日可查看该日数据，并且时间筛选变更为返回）
     * @param enter
     * @return
     */
    List<DeliveryChartResult> driverDeliveryChart30Day(DeliveryChartDto enter);

    /**
     * 年Year（单位为月，显示截止至今所有数据，点击某一月可查看该月数据，点击某一日可查看该日数据
     * @param enter
     * @return
     */
    List<DeliveryChartResult> driverDeliveryChart365Day(DeliveryChartDto enter);

    BigDecimal queryScooterMileage(@Param("enter") IdEnter enter, @Param("beginTime") Date beginTime);
}
