package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateTrace;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeAllocateTraceService extends IService<OpeAllocateTrace> {


    int updateBatch(List<OpeAllocateTrace> list);

    int batchInsert(List<OpeAllocateTrace> list);

    int insertOrUpdate(OpeAllocateTrace record);

    int insertOrUpdateSelective(OpeAllocateTrace record);

}

