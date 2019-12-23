package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTask;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaMailTaskService extends IService<PlaMailTask>{


    int updateBatch(List<PlaMailTask> list);

    int batchInsert(List<PlaMailTask> list);

    int insertOrUpdate(PlaMailTask record);

    int insertOrUpdateSelective(PlaMailTask record);

    int insertOrUpdateWithBLOBs(PlaMailTask record);

}
