package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;

import java.util.List;

public interface PlaMailConfigService extends IService<PlaMailConfig> {


    int updateBatch(List<PlaMailConfig> list);

    int batchInsert(List<PlaMailConfig> list);

    int insertOrUpdate(PlaMailConfig record);

    int insertOrUpdateSelective(PlaMailConfig record);

}
