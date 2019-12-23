package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaI18nConfig;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaI18nConfigService extends IService<PlaI18nConfig>{


    int updateBatch(List<PlaI18nConfig> list);

    int batchInsert(List<PlaI18nConfig> list);

    int insertOrUpdate(PlaI18nConfig record);

    int insertOrUpdateSelective(PlaI18nConfig record);

}
