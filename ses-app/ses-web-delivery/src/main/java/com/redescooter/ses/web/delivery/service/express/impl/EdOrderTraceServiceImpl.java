package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderEnter;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderResult;
import com.redescooter.ses.web.delivery.service.express.EdOrderTraceService;
import com.redescooter.ses.web.delivery.vo.task.SaveExpressOrderTraceEnter;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @ClassName:EdOrderTraceServiceImpl
 * @description: EdOrderTraceServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/18 13:39
 */
@Service
public class EdOrderTraceServiceImpl implements EdOrderTraceService {

    /**
     * 批量订单记录
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult batchSaveExpressOrderTrace(List<BaseExpressOrderEnter> enter) {
        return null;
    }

    /**
     * 保存单个订单记录
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveExpressOrderTrace(SaveExpressOrderTraceEnter enter) {
        return null;
    }

    /**
     * 查询订单记录
     *
     * @param enter
     * @return
     */
    @Override
    public BaseExpressOrderResult queryOrdertraceByOrderId(IdEnter enter) {
        return null;
    }
}
