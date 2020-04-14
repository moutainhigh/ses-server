package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateBTrace;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAllocateBTraceService extends IService<OpeAllocateBTrace> {


    int updateBatch(List<OpeAllocateBTrace> list);

    int batchInsert(List<OpeAllocateBTrace> list);

    int insertOrUpdate(OpeAllocateBTrace record);

    int insertOrUpdateSelective(OpeAllocateBTrace record);

}

