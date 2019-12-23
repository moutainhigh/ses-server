package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaMailConfigService extends IService<PlaMailConfig>{


    int updateBatch(List<PlaMailConfig> list);

    int batchInsert(List<PlaMailConfig> list);

    int insertOrUpdate(PlaMailConfig record);

    int insertOrUpdateSelective(PlaMailConfig record);

}
