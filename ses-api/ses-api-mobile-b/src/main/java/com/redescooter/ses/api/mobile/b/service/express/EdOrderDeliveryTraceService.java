package com.redescooter.ses.api.mobile.b.service.express;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderResult;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderTraceEnter;

import java.util.List;

public interface EdOrderDeliveryTraceService {

    /**
     * 批量订单记录
     *
     * @param enter
     * @return
     */
    void batchSaveExpressOrderTrace(List<BaseExpressOrderTraceEnter> enter);

    /**
     * 保存单个订单记录
     *
     * @param enter
     * @return
     */
    void saveExpressOrderTrace(BaseExpressOrderTraceEnter enter);

    /**
     * 查询订单记录
     *
     * @param enter
     * @return
     */
    BaseExpressOrderResult queryOrdertraceByOrderId(IdEnter enter);


}
