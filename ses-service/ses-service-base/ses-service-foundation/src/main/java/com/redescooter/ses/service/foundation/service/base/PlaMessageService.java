package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaMessage;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaMessageService extends IService<PlaMessage>{


    int updateBatch(List<PlaMessage> list);

    int batchInsert(List<PlaMessage> list);

    int insertOrUpdate(PlaMessage record);

    int insertOrUpdateSelective(PlaMessage record);

    int insertOrUpdateWithBLOBs(PlaMessage record);

}
