package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePurchasQcTrace;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePurchasQcTraceService extends IService<OpePurchasQcTrace>{


    int updateBatch(List<OpePurchasQcTrace> list);

    int batchInsert(List<OpePurchasQcTrace> list);

    int insertOrUpdate(OpePurchasQcTrace record);

    int insertOrUpdateSelective(OpePurchasQcTrace record);

}
