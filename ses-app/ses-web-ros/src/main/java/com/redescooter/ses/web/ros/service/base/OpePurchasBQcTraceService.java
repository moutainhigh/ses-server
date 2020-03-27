package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasBQcTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpePurchasBQcTraceService extends IService<OpePurchasBQcTrace> {


    int updateBatch(List<OpePurchasBQcTrace> list);

    int batchInsert(List<OpePurchasBQcTrace> list);

    int insertOrUpdate(OpePurchasBQcTrace record);

    int insertOrUpdateSelective(OpePurchasBQcTrace record);

}


