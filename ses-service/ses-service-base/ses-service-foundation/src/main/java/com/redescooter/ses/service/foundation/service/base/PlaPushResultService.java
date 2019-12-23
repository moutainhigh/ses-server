package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaPushResult;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaPushResultService extends IService<PlaPushResult>{


    int updateBatch(List<PlaPushResult> list);

    int batchInsert(List<PlaPushResult> list);

    int insertOrUpdate(PlaPushResult record);

    int insertOrUpdateSelective(PlaPushResult record);

}
