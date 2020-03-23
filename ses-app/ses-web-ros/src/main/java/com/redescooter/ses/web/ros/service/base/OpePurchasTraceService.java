package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpePurchasTrace;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchasTraceService extends IService<OpePurchasTrace> {


    int updateBatch(List<OpePurchasTrace> list);

    int batchInsert(List<OpePurchasTrace> list);

    int insertOrUpdate(OpePurchasTrace record);

    int insertOrUpdateSelective(OpePurchasTrace record);

}
