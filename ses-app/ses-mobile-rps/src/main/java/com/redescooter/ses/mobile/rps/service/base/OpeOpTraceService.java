package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpeOpTrace;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOpTraceService extends IService<OpeOpTrace> {


    int updateBatch(List<OpeOpTrace> list);

    int batchInsert(List<OpeOpTrace> list);

    int insertOrUpdate(OpeOpTrace record);

    int insertOrUpdateSelective(OpeOpTrace record);

}
