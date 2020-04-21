package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcTrace;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeAssemblyQcTraceService extends IService<OpeAssemblyQcTrace>{


    int updateBatch(List<OpeAssemblyQcTrace> list);

    int batchInsert(List<OpeAssemblyQcTrace> list);

    int insertOrUpdate(OpeAssemblyQcTrace record);

    int insertOrUpdateSelective(OpeAssemblyQcTrace record);

}
