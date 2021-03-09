package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchasBQc;

import java.util.List;

public interface OpePurchasBQcService extends IService<OpePurchasBQc> {


    int updateBatch(List<OpePurchasBQc> list);

    int batchInsert(List<OpePurchasBQc> list);

    int insertOrUpdate(OpePurchasBQc record);

    int insertOrUpdateSelective(OpePurchasBQc record);

}





