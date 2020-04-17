package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchasTrace;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePurchasTraceService extends IService<OpePurchasTrace> {


    int updateBatch(List<OpePurchasTrace> list);

    int batchInsert(List<OpePurchasTrace> list);

    int insertOrUpdate(OpePurchasTrace record);

    int insertOrUpdateSelective(OpePurchasTrace record);

}


