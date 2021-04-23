package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAllocateBTrace;

import java.util.List;

public interface OpeAllocateBTraceService extends IService<OpeAllocateBTrace>{


    int updateBatch(List<OpeAllocateBTrace> list);

    int batchInsert(List<OpeAllocateBTrace> list);

    int insertOrUpdate(OpeAllocateBTrace record);

    int insertOrUpdateSelective(OpeAllocateBTrace record);

}
