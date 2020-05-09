package com.redescooter.ses.service.foundation.service.base;

import java.util.List;

import com.redescooter.ses.service.foundation.dm.base.PlaSysConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlaSysConfigService extends IService<PlaSysConfig> {


    int updateBatch(List<PlaSysConfig> list);

    int batchInsert(List<PlaSysConfig> list);

    int insertOrUpdate(PlaSysConfig record);

    int insertOrUpdateSelective(PlaSysConfig record);

}
