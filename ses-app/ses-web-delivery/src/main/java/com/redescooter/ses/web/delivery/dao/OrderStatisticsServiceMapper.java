package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.vo.ScooterRideDataResult;
import com.redescooter.ses.web.delivery.vo.TopTenEnter;
import com.redescooter.ses.web.delivery.vo.TopTenResult;

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
}
