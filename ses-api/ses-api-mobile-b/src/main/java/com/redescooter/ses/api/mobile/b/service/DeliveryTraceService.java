package com.redescooter.ses.api.mobile.b.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.delivery.BaseDeliveryEnter;
import com.redescooter.ses.api.mobile.b.vo.DeliveryTraceDetailResult;
import com.redescooter.ses.api.mobile.b.vo.SaveDeliveryTraceEnter;

import java.util.List;

/**
 * @ClassName:DeliveryTraceService
 * @description: DeliveryTraceService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 16:07
 */
public interface DeliveryTraceService {
    /**
     * 保存订单节点
     *
     * @param enter
     * @return
     */
    GeneralResult saveDeliveryTrace(List<SaveDeliveryTraceEnter<BaseDeliveryEnter>> enter);

    /**
     * 查询订单节点
     *
     * @param enter
     * @return
     */
    List<DeliveryTraceDetailResult> detail(IdEnter enter);
}
