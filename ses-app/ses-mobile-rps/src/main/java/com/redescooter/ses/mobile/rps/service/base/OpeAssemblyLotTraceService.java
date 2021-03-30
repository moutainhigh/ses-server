package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyLotTrace;

import java.util.List;

public interface OpeAssemblyLotTraceService extends IService<OpeAssemblyLotTrace> {


    int updateBatch(List<OpeAssemblyLotTrace> list);

    int updateBatchSelective(List<OpeAssemblyLotTrace> list);

    int batchInsert(List<OpeAssemblyLotTrace> list);

    int insertOrUpdate(OpeAssemblyLotTrace record);

    int insertOrUpdateSelective(OpeAssemblyLotTrace record);

}

