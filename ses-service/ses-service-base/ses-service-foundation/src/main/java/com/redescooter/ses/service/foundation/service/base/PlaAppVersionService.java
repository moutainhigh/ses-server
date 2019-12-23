package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaAppVersionService extends IService<PlaAppVersion>{


    int updateBatch(List<PlaAppVersion> list);

    int batchInsert(List<PlaAppVersion> list);

    int insertOrUpdate(PlaAppVersion record);

    int insertOrUpdateSelective(PlaAppVersion record);

}
