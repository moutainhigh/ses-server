package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaSysSequence;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaSysSequenceService extends IService<PlaSysSequence> {


    int updateBatch(List<PlaSysSequence> list);

    int batchInsert(List<PlaSysSequence> list);

    int insertOrUpdate(PlaSysSequence record);

    int insertOrUpdateSelective(PlaSysSequence record);

}
