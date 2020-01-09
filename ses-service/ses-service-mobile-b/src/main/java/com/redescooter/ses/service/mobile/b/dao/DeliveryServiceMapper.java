package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.vo.DeliveryDetailResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListEnter;
import com.redescooter.ses.api.mobile.b.vo.MonthlyDeliveryChartResult;
import com.redescooter.ses.service.mobile.b.dm.base.CorTenantScooter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:DeliveryService
 * @description: DeliveryService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 14:45
 */
public interface DeliveryServiceMapper {
    /**
     * 查询进行中订单状态统计
     *
     * @return
     */
    List<CountByStatusResult> doingCountByStatus(DeliveryListEnter enter);


    /**
     * 查询司机订单所有状态
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> allDriverDeliveryStatusCount(GeneralEnter enter);

    /**
     * 列表
     *
     * @return
     */
    List<DeliveryDetailResult> deliveryList(DeliveryListEnter enter);


    /**
     * 司机订单图表展示
     *
     * @param enter
     * @return
     */
    List<MonthlyDeliveryChartResult> mobileBDeliveryChart(DateTimeParmEnter enter);

    /**
     * 拒绝的订单 统计
     *
     * @param userId
     * @param status
     * @return
     */
    int refuseDelivery(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 查询车辆数据
     *
     * @param enter
     * @return
     */
    CorTenantScooter queryTenantScooterByUserId(DeliveryListEnter enter);
}
