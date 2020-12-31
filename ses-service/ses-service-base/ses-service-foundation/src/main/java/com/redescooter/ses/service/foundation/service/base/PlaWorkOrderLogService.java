package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaWorkOrderLog;

import java.util.List;

public interface PlaWorkOrderLogService extends IService<PlaWorkOrderLog> {


    int updateBatch(List<PlaWorkOrderLog> list);

    int updateBatchSelective(List<PlaWorkOrderLog> list);

    int batchInsert(List<PlaWorkOrderLog> list);

    int insertOrUpdate(PlaWorkOrderLog record);

    int insertOrUpdateSelective(PlaWorkOrderLog record);

}
