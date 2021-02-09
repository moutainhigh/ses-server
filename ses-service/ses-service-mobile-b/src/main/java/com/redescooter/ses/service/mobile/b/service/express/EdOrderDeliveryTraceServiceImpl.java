package com.redescooter.ses.service.mobile.b.service.express;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderResult;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderTraceEnter;
import com.redescooter.ses.api.mobile.b.service.express.EdOrderDeliveryTraceService;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTrace;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderTraceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DubboService
public class EdOrderDeliveryTraceServiceImpl implements EdOrderDeliveryTraceService {

    @Autowired
    private CorExpressOrderTraceService corExpressOrderTraceService;

    /**
     * 批量订单记录
     *
     * @param enter
     * @return
     */
    @Override
    public void batchSaveExpressOrderTrace(List<BaseExpressOrderTraceEnter> enter) {
        List<CorExpressOrderTrace> corExpressOrderTraceList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(enter)) {
            enter.forEach(item -> {
                CorExpressOrderTrace corExpressOrderTrace = new CorExpressOrderTrace();
                BeanUtils.copyProperties(item, corExpressOrderTrace);
                corExpressOrderTraceList.add(corExpressOrderTrace);

            });
            corExpressOrderTraceService.saveBatch(corExpressOrderTraceList);
        }
    }

    /**
     * 保存单个订单记录
     *
     * @param enter
     * @return
     */
    @Override
    public void saveExpressOrderTrace(BaseExpressOrderTraceEnter enter) {
        CorExpressOrderTrace corExpressOrderTrace = new CorExpressOrderTrace();
        BeanUtils.copyProperties(enter, corExpressOrderTrace);
        corExpressOrderTraceService.save(corExpressOrderTrace);
    }

    /**
     * 查询订单记录
     *
     * @param enter
     * @return
     */
    @Override
    public BaseExpressOrderResult queryOrdertraceByOrderId(IdEnter enter) {
        CorExpressOrderTrace corExpressOrderTrace = corExpressOrderTraceService.getById(enter.getId());
        BaseExpressOrderResult baseExpressOrderResult = new BaseExpressOrderResult();
        BeanUtils.copyProperties(corExpressOrderTrace, baseExpressOrderResult);
        return baseExpressOrderResult;
    }
}
