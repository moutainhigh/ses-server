package com.redescooter.ses.web.delivery.service.express.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderResult;
import com.redescooter.ses.api.common.vo.edorder.BaseExpressOrderTraceEnter;
import com.redescooter.ses.web.delivery.dm.CorExpressOrderTrace;
import com.redescooter.ses.web.delivery.service.base.CorExpressOrderTraceService;
import com.redescooter.ses.web.delivery.service.express.EdOrderTraceService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private CorExpressOrderTraceService corExpressOrderTraceService;

    /**
     * 批量订单记录
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void batchSaveExpressOrderTrace(List<BaseExpressOrderTraceEnter> enter) {
        List<CorExpressOrderTrace> corExpressOrderTraceList=new ArrayList<>();
        if (CollectionUtils.isNotEmpty(enter)) {
            enter.forEach(item -> {
                CorExpressOrderTrace corExpressOrderTrace = new CorExpressOrderTrace();
                BeanUtils.copyProperties(item, corExpressOrderTrace);
                corExpressOrderTraceList.add(corExpressOrderTrace);

            });
            corExpressOrderTraceService.batchInsert(corExpressOrderTraceList);
        }
    }

    /**
     * 保存单个订单记录
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult saveExpressOrderTrace(BaseExpressOrderTraceEnter enter) {
        CorExpressOrderTrace corExpressOrderTrace = new CorExpressOrderTrace();
        BeanUtils.copyProperties(enter, corExpressOrderTrace);
        corExpressOrderTraceService.insertOrUpdate(corExpressOrderTrace);
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
        CorExpressOrderTrace corExpressOrderTrace = corExpressOrderTraceService.getById(enter.getId());
        BaseExpressOrderResult baseExpressOrderResult=new BaseExpressOrderResult();
        BeanUtils.copyProperties(corExpressOrderTrace,baseExpressOrderResult);
        return baseExpressOrderResult;
    }
}
