package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasQcTrace;

import java.util.List;

public interface OpePurchasQcTraceService extends IService<OpePurchasQcTrace>{


    int updateBatch(List<OpePurchasQcTrace> list);

    int batchInsert(List<OpePurchasQcTrace> list);

    int insertOrUpdate(OpePurchasQcTrace record);

    int insertOrUpdateSelective(OpePurchasQcTrace record);

}
