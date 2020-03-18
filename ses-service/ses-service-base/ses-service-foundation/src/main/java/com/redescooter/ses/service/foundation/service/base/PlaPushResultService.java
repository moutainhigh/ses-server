package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaPushResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaPushResultService extends IService<PlaPushResult> {


    int updateBatch(List<PlaPushResult> list);

    int batchInsert(List<PlaPushResult> list);

    int insertOrUpdate(PlaPushResult record);

    int insertOrUpdateSelective(PlaPushResult record);

    int updateBatchSelective(List<PlaPushResult> list);
}

