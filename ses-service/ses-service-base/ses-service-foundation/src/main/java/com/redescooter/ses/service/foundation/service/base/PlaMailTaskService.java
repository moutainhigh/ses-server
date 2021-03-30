package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTask;

import java.util.List;

public interface PlaMailTaskService extends IService<PlaMailTask> {


    int updateBatch(List<PlaMailTask> list);

    int batchInsert(List<PlaMailTask> list);

    int insertOrUpdate(PlaMailTask record);

    int insertOrUpdateSelective(PlaMailTask record);

    int insertOrUpdateWithBLOBs(PlaMailTask record);

}
