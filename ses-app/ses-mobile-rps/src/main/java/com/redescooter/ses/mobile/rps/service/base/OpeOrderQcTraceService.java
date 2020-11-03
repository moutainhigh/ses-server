package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeOrderQcTrace;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeOrderQcTraceService extends IService<OpeOrderQcTrace>{


    int updateBatch(List<OpeOrderQcTrace> list);

    int batchInsert(List<OpeOrderQcTrace> list);

    int insertOrUpdate(OpeOrderQcTrace record);

    int insertOrUpdateSelective(OpeOrderQcTrace record);

}
