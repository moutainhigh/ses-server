package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaSysConfig;

import java.util.List;

public interface PlaSysConfigService extends IService<PlaSysConfig> {


    int updateBatch(List<PlaSysConfig> list);

    int batchInsert(List<PlaSysConfig> list);

    int insertOrUpdate(PlaSysConfig record);

    int insertOrUpdateSelective(PlaSysConfig record);

}
