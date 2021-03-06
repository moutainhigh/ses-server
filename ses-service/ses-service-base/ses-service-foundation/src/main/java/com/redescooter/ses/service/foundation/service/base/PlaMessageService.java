package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaMessage;

import java.util.List;

public interface PlaMessageService extends IService<PlaMessage> {


    int updateBatch(List<PlaMessage> list);

    int batchInsert(List<PlaMessage> list);

    int insertOrUpdate(PlaMessage record);

    int insertOrUpdateSelective(PlaMessage record);

    int insertOrUpdateWithBLOBs(PlaMessage record);

    int updateBatchSelective(List<PlaMessage> list);
}


