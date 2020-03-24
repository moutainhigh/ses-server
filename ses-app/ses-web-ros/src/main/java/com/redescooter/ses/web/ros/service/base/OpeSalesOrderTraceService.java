package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSalesOrderTrace;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSalesOrderTraceService extends IService<OpeSalesOrderTrace> {


    int updateBatch(List<OpeSalesOrderTrace> list);

    int batchInsert(List<OpeSalesOrderTrace> list);

    int insertOrUpdate(OpeSalesOrderTrace record);

    int insertOrUpdateSelective(OpeSalesOrderTrace record);

}
