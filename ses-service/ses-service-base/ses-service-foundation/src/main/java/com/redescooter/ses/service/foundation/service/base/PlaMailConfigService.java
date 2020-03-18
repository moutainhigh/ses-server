package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaMailConfigService extends IService<PlaMailConfig> {


    int updateBatch(List<PlaMailConfig> list);

    int batchInsert(List<PlaMailConfig> list);

    int insertOrUpdate(PlaMailConfig record);

    int insertOrUpdateSelective(PlaMailConfig record);

}
