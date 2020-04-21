package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAssembiyOrderTrace;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeAssembiyOrderTraceService extends IService<OpeAssembiyOrderTrace>{


    int updateBatch(List<OpeAssembiyOrderTrace> list);

    int batchInsert(List<OpeAssembiyOrderTrace> list);

    int insertOrUpdate(OpeAssembiyOrderTrace record);

    int insertOrUpdateSelective(OpeAssembiyOrderTrace record);

}
