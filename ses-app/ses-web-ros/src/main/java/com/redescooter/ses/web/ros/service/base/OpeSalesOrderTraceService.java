package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSalesOrderTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeSalesOrderTraceService extends IService<OpeSalesOrderTrace> {


    int updateBatch(List<OpeSalesOrderTrace> list);

    int batchInsert(List<OpeSalesOrderTrace> list);

    int insertOrUpdate(OpeSalesOrderTrace record);

    int insertOrUpdateSelective(OpeSalesOrderTrace record);

}
