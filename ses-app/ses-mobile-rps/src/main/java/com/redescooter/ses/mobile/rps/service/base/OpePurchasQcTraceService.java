package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePurchasQcTrace;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePurchasQcTraceService extends IService<OpePurchasQcTrace> {


    int updateBatch(List<OpePurchasQcTrace> list);

    int batchInsert(List<OpePurchasQcTrace> list);

    int insertOrUpdate(OpePurchasQcTrace record);

    int insertOrUpdateSelective(OpePurchasQcTrace record);

}















