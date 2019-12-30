package com.redescooter.ses.service.mobile.b.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.vo.DeliveryDetailResult;

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
    List<CountByStatusResult> countByStatus(GeneralEnter enter);

    /**
     * 列表
     *
     * @return
     */
    List<DeliveryDetailResult> deliveryList(GeneralEnter enter);
}
