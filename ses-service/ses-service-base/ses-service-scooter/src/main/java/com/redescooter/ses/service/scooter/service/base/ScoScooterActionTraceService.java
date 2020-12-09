package com.redescooter.ses.service.scooter.service.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterActionTrace;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ScoScooterActionTraceService extends IService<ScoScooterActionTrace>{


    int updateBatch(List<ScoScooterActionTrace> list);

    int batchInsert(List<ScoScooterActionTrace> list);

    int insertOrUpdate(ScoScooterActionTrace record);

    int insertOrUpdateSelective(ScoScooterActionTrace record);

}
