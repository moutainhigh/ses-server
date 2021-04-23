package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeOrderQcTrace;

import java.util.List;

public interface OpeOrderQcTraceService extends IService<OpeOrderQcTrace> {


    int updateBatch(List<OpeOrderQcTrace> list);

    int batchInsert(List<OpeOrderQcTrace> list);

    int insertOrUpdate(OpeOrderQcTrace record);

    int insertOrUpdateSelective(OpeOrderQcTrace record);

}


