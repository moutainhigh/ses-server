package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAllocateTrace;

import java.util.List;

public interface OpeAllocateTraceService extends IService<OpeAllocateTrace> {


    int updateBatch(List<OpeAllocateTrace> list);

    int batchInsert(List<OpeAllocateTrace> list);

    int insertOrUpdate(OpeAllocateTrace record);

    int insertOrUpdateSelective(OpeAllocateTrace record);

}

