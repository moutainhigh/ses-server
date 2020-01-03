package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryDetailResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListEnter;
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
     * 状态统计
     *
     * @return
     */
    List<CountByStatusResult> countByStatus(DeliveryListEnter enter);

    /**
     * 列表
     *
     * @return
     */
    List<DeliveryDetailResult> deliveryList(DeliveryListEnter enter);

    /**
     * 拒绝的订单 统计
     *
     * @param userId
     * @param status
     * @return
     */
    int refuseDelivery(@Param("userId") Long userId, @Param("status") String status);
}
