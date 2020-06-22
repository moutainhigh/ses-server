package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaAppVersionService extends IService<PlaAppVersion> {


    int updateBatch(List<PlaAppVersion> list);

    int batchInsert(List<PlaAppVersion> list);

    int insertOrUpdate(PlaAppVersion record);

    int insertOrUpdateSelective(PlaAppVersion record);

    int updateBatchSelective(List<PlaAppVersion> list);
}






