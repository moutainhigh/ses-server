package com.redescooter.ses.web.delivery.service.express;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderTraceEnter;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderResult;
import com.redescooter.ses.web.delivery.vo.task.SaveExpressOrderTraceEnter;

import java.util.List;

/**
 * @ClassName:EdOrderTrace
 * @description: EdOrderTrace
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/18 11:59
 */
public interface EdOrderTraceService {
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
    GeneralResult saveExpressOrderTrace(SaveExpressOrderTraceEnter enter);

    /**
     * 查询订单记录
     *
     * @param enter
     * @return
     */
    BaseExpressOrderResult queryOrdertraceByOrderId(IdEnter enter);
}

