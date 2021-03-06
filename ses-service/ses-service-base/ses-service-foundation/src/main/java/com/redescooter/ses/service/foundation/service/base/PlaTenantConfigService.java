package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantConfig;

import java.util.List;

public interface PlaTenantConfigService extends IService<PlaTenantConfig> {


    int updateBatch(List<PlaTenantConfig> list);

    int batchInsert(List<PlaTenantConfig> list);

    int insertOrUpdate(PlaTenantConfig record);

    int insertOrUpdateSelective(PlaTenantConfig record);

    int updateBatchSelective(List<PlaTenantConfig> list);
}


