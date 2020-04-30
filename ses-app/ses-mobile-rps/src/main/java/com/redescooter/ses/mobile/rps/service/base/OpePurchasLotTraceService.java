package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePurchasLotTrace;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchasLotTraceService extends IService<OpePurchasLotTrace> {


    int updateBatch(List<OpePurchasLotTrace> list);

    int updateBatchSelective(List<OpePurchasLotTrace> list);

}

