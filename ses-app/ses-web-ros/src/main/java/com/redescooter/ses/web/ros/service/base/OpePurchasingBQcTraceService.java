package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasingBQcTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePurchasingBQcTraceService extends IService<OpePurchasingBQcTrace> {


    int updateBatch(List<OpePurchasingBQcTrace> list);

    int batchInsert(List<OpePurchasingBQcTrace> list);

    int insertOrUpdate(OpePurchasingBQcTrace record);

    int insertOrUpdateSelective(OpePurchasingBQcTrace record);

}

