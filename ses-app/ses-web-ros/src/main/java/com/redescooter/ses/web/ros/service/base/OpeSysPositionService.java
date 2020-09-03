package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysPosition;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSysPositionService extends IService<OpeSysPosition> {


    int updateBatch(List<OpeSysPosition> list);

    int updateBatchSelective(List<OpeSysPosition> list);

    int batchInsert(List<OpeSysPosition> list);

    int insertOrUpdate(OpeSysPosition record);

    int insertOrUpdateSelective(OpeSysPosition record);

}

