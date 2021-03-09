package com.redescooter.ses.service.scooter.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterActionTrace;

import java.util.List;

public interface ScoScooterActionTraceService extends IService<ScoScooterActionTrace>{


    int updateBatch(List<ScoScooterActionTrace> list);

    int batchInsert(List<ScoScooterActionTrace> list);

    int insertOrUpdate(ScoScooterActionTrace record);

    int insertOrUpdateSelective(ScoScooterActionTrace record);

}
