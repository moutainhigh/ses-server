package com.redescooter.ses.service.scooter.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterStatus;

import java.util.List;

public interface ScoScooterStatusService extends IService<ScoScooterStatus>{


    int updateBatch(List<ScoScooterStatus> list);

    int batchInsert(List<ScoScooterStatus> list);

    int insertOrUpdate(ScoScooterStatus record);

    int insertOrUpdateSelective(ScoScooterStatus record);

}
