package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasingTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePurchasingTraceService extends IService<OpePurchasingTrace> {


    int updateBatch(List<OpePurchasingTrace> list);

    int batchInsert(List<OpePurchasingTrace> list);

    int insertOrUpdate(OpePurchasingTrace record);

    int insertOrUpdateSelective(OpePurchasingTrace record);

}

