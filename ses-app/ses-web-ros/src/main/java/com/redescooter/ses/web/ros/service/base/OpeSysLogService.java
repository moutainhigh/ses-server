package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysLog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSysLogService extends IService<OpeSysLog> {


    int updateBatch(List<OpeSysLog> list);

    int updateBatchSelective(List<OpeSysLog> list);

    int batchInsert(List<OpeSysLog> list);

    int insertOrUpdate(OpeSysLog record);

    int insertOrUpdateSelective(OpeSysLog record);

}


